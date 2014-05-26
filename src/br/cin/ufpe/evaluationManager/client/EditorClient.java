package br.cin.ufpe.evaluationManager.client;

import br.cin.ufpe.evaluationManager.resend.EditorResendRunnable;
import br.cin.ufpe.evaluationManager.model.EvaluationConf;
import br.cin.ufpe.evaluationManager.model.EvaluationStatus;
import br.cin.ufpe.evaluationManager.remote.EvaluationProtocol;
import br.cin.ufpe.evaluationManager.service.EditorService;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author avld
 */
public class EditorClient implements EditorService
{
    private int counter;
    private Socket socket;
    private ObjectOutputStream output;
    private ScheduledExecutorService service;
    private EditorResendRunnable resend;
    
    public EditorClient( Socket socket ) throws IOException
    {
        this.socket = socket;
        this.output = new ObjectOutputStream( socket.getOutputStream() );
        
        this.counter = 1;
        this.resend  = new EditorResendRunnable( this );
        
        this.service = Executors.newSingleThreadScheduledExecutor();
        this.service.scheduleAtFixedRate( resend , 1 , 1 , TimeUnit.MINUTES );
    }
    
    @Override
    public void created( EvaluationConf conf )
    {
        EvaluationProtocol p = new EvaluationProtocol();
        p.setId( counter++ );
        p.setOperation( "created" );
        p.addParam( conf );
        
        try
        {
            synchronized( output )
            {
                output.writeObject( p );
                output.flush();
                //output.reset();
            }
        }
        catch (IOException ex)
        {
            resend.add( conf , EditorResendRunnable.TYPE_CREATED );
        }
    }
    
    @Override
    public void finished( EvaluationConf conf )
    {
        EvaluationProtocol p = new EvaluationProtocol();
        p.setId( counter++ );
        p.setOperation( "finished" );
        p.addParam( conf );
        
        try
        {
            synchronized( output )
            {
                output.writeObject( p );
                output.flush();
                //output.reset();
            }
        }
        catch (IOException ex)
        {
            resend.add( conf , EditorResendRunnable.TYPE_FINISHED );
        }
    }

    @Override
    public void inProgress( EvaluationConf conf , EvaluationStatus status )
    {
        EvaluationProtocol p = new EvaluationProtocol();
        p.setId( counter++ );
        p.setOperation( "inProgress" );
        p.addParam( conf );
        p.addParam( status );
        
        try
        {
            synchronized( output )
            {
                output.writeObject( p );
                output.flush();
                //output.reset();
            }
        }
        catch ( IOException ex )
        {
            // do nothing
        }
    }
    
    public boolean isThisSocket( Socket socket )
    {
        return socket.getPort() == this.socket.getPort();
    }
    
    public boolean isOK()
    {
        return !socket.isClosed();
    }
}
