package br.cin.ufpe.evaluationManager.client;

import br.cin.ufpe.evaluationManager.model.EvaluationConf;
import br.cin.ufpe.evaluationManager.remote.EvaluationProtocol;
import br.cin.ufpe.evaluationManager.service.EditorService;
import br.cin.ufpe.evaluationManager.service.EvaluatorService;
import br.cin.ufpe.evaluationManager.service.ManagerService;
import br.cin.ufpe.evaluationManager.service.TranslatorService;
import br.cin.ufpe.evaluationManager.skeleton.EditorSkeleton;
import br.cin.ufpe.evaluationManager.skeleton.EvaluatorSkeleton;
import br.cin.ufpe.evaluationManager.skeleton.Skeleton;
import br.cin.ufpe.evaluationManager.skeleton.TranslatorSkeleton;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * 
 * 
 * @author avld
 */
public class ManagerClient implements ManagerService
{
    private Socket             socket   ;
    private ObjectOutputStream output   ;
    private Skeleton           skeleton ;
    private Thread             thread   ;
    
    public ManagerClient( String ip , int port , EditorService s ) throws Exception
    {
        socket   = new Socket( ip , port );
        output   = new ObjectOutputStream( socket.getOutputStream() );
        
        skeleton = new EditorSkeleton( socket , s );
        thread   = new Thread( skeleton );
        thread.start();
        
        connected( ManagerService.TYPE_EDITOR );
    }
    
    public ManagerClient( String ip , int port , TranslatorService s ) throws Exception
    {
        socket   = new Socket( ip , port );
        output   = new ObjectOutputStream( socket.getOutputStream() );
        
        skeleton = new TranslatorSkeleton( socket , s );
        thread   = new Thread( skeleton );
        thread.start();
        
        connected( ManagerService.TYPE_TRANSLATOR );
    }
    
    public ManagerClient( String ip , int port , EvaluatorService s ) throws Exception
    {
        socket   = new Socket( ip , port );
        output   = new ObjectOutputStream( socket.getOutputStream() );
        
        skeleton = new EvaluatorSkeleton( socket , s );
        thread   = new Thread( skeleton );
        thread.start();
        
        connected( ManagerService.TYPE_EVALUATOR );
    }
    
    // -------------------
    
    private void connected( int type ) throws Exception
    {
        EvaluationProtocol p = new EvaluationProtocol();
        p.setId( 1 );
        p.setOperation( "connected" );
        p.addParam( type );
        
        output.writeObject( p );
    }
    
    @Override
    public void add( EvaluationConf conf ) throws Exception
    {
        EvaluationProtocol p = new EvaluationProtocol();
        p.setId( 1 );
        p.setOperation( "add" );
        p.addParam( conf );
        
        output.writeObject( p );
    }

    @Override
    public void modelled( long evaluateId , int code ) throws Exception
    {
        EvaluationProtocol p = new EvaluationProtocol();
        p.setId( 1 );
        p.setOperation( "modelled" );
        p.addParam( evaluateId );
        p.addParam( code );
        
        output.writeObject( p );
    }

    @Override
    public void evaluated( long evaluateId , int code ) throws Exception
    {
        EvaluationProtocol p = new EvaluationProtocol();
        p.setId( 1 );
        p.setOperation( "evaluated" );
        p.addParam( evaluateId );
        p.addParam( code );
        
        output.writeObject( p );
    }
    
}
