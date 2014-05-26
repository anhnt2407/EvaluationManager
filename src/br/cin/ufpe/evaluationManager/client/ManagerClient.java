package br.cin.ufpe.evaluationManager.client;

import br.cin.ufpe.evaluationManager.resend.ClientResendRunnable;
import br.cin.ufpe.evaluationManager.model.EvaluationConf;
import br.cin.ufpe.evaluationManager.remote.EvaluationProtocol;
import br.cin.ufpe.evaluationManager.service.ClientService;
import br.cin.ufpe.evaluationManager.service.EditorService;
import br.cin.ufpe.evaluationManager.service.EvaluatorService;
import br.cin.ufpe.evaluationManager.service.ManagerService;
import br.cin.ufpe.evaluationManager.service.TranslatorService;
import br.cin.ufpe.evaluationManager.skeleton.EditorSkeleton;
import br.cin.ufpe.evaluationManager.skeleton.EvaluatorSkeleton;
import br.cin.ufpe.evaluationManager.skeleton.ReconnectSkeleton;
import br.cin.ufpe.evaluationManager.skeleton.TranslatorSkeleton;
import java.net.Socket;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 
 * 
 * @author avld
 */
public class ManagerClient implements ManagerService
{
    private Socket             socket   ;
    private ReconnectSkeleton  skeleton ;
    private ExecutorService    fixedPool;
    
    private ClientResendRunnable   resend;
    private ScheduledExecutorService schedulerPool;
    
    private int type;
    
    public ManagerClient( EditorService s ) throws Exception
    {
        init( s , TYPE_EDITOR );
    }
    
    public ManagerClient( TranslatorService s ) throws Exception
    {
        init( s , TYPE_TRANSLATOR );
    }
    
    public ManagerClient( EvaluatorService s ) throws Exception
    {
        init( s , TYPE_EVALUATOR );
    }
    
    private void init( ClientService s , int type )
    {
        resend = new ClientResendRunnable( this );
        resend.setService( s );
        
        this.type = type;
        
        fixedPool = Executors.newSingleThreadExecutor();
        
        schedulerPool = Executors.newScheduledThreadPool( 1 );
        schedulerPool.scheduleAtFixedRate( resend , 1 , 1 , TimeUnit.MINUTES );
    }
    
    public void conect( String ip , int port ) throws Exception
    {
        socket = new Socket( ip , port );
        
        ClientService service = resend.getService();
        
        if( service instanceof EditorService )
        {
            skeleton = new EditorSkeleton    ( socket , (EditorService) service     );
        }
        else if( service instanceof TranslatorService )
        {
            skeleton = new TranslatorSkeleton( socket , (TranslatorService) service );
        }
        else
        {
            skeleton = new EvaluatorSkeleton ( socket , (EvaluatorService) service  );
        }
        
        fixedPool.submit( skeleton );
        
        skeleton.getEvent().setIp  ( ip   );
        skeleton.getEvent().setPort( port );
        skeleton.getEvent().setType( type );
        
        skeleton.setEvent ( resend );
        
        skeleton.connected( type   );
    }
    
    // -------------------
    // -------------------
    // -------------------
    
    @Override
    public void add( EvaluationConf conf )
    {
        try
        {
            EvaluationProtocol p = new EvaluationProtocol();
            p.setId( 1 );
            p.setOperation( "add" );
            p.addParam( conf );

            synchronized( skeleton.getOutput() )
            {
                skeleton.getOutput().writeObject( p );
                skeleton.getOutput().flush();
                //skeleton.getOutput().reset();
            }
        }
        catch( Exception err )
        {
            System.err.println( "[MANAGER_CLIENT][ERROR] " + err.getMessage() );
            resend.add( conf , ClientResendRunnable.TYPE_ADD );
        }
    }

    @Override
    public void modelled( Properties properties )
    {
        try
        {
            EvaluationProtocol p = new EvaluationProtocol();
            p.setId( 1 );
            p.setOperation( "modelled" );
            p.addParam( properties );

            synchronized( skeleton.getOutput() )
            {
                skeleton.getOutput().writeObject( p );
                skeleton.getOutput().flush();
                //skeleton.getOutput().reset();
            }
        }
        catch( Exception err )
        {
            System.err.println( "[MANAGER_CLIENT][ERROR] " + err.getMessage() );
            resend.add( properties , ClientResendRunnable.TYPE_MODELLED );
        }
    }

    @Override
    public void evaluated( Properties properties )
    {
        try
        {
            EvaluationProtocol p = new EvaluationProtocol();
            p.setId( 1 );
            p.setOperation( "evaluated" );
            p.addParam( properties );

            synchronized( skeleton.getOutput() )
            {
                skeleton.getOutput().writeObject( p );
                skeleton.getOutput().flush();
                //skeleton.getOutput().reset();
            }
        }
        catch( Exception err )
        {
            System.err.println( "[MANAGER_CLIENT][ERROR] " + err.getMessage() );
            resend.add( properties , ClientResendRunnable.TYPE_EVALUATED );
        }
    }
    
    // -------------------
    // -------------------
    // -------------------
    
    @Override
    public void finalize()
    {
        fixedPool.shutdownNow();
        fixedPool = null;
        
        schedulerPool.shutdownNow();
        schedulerPool = null;
        
        skeleton.desconnected();
        skeleton = null;
        
        resend = null;
        
        try                 { socket.close(); }
        catch(Exception err){ /*do nothing*/  }
        finally             { socket = null;  }
    }
    
}
