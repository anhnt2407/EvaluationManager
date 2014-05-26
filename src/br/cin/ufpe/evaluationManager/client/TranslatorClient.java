package br.cin.ufpe.evaluationManager.client;

import br.cin.ufpe.evaluationManager.model.EvaluationConf;
import br.cin.ufpe.evaluationManager.remote.EvaluationProtocol;
import br.cin.ufpe.evaluationManager.resend.TranslatorResendRunnable;
import br.cin.ufpe.evaluationManager.service.TranslatorService;
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
public class TranslatorClient implements TranslatorService
{
    private int counter;
    private Socket socket;
    private ObjectOutputStream output;
    
    private ScheduledExecutorService service;
    private TranslatorResendRunnable resend;
    
    public TranslatorClient( Socket socket ) throws IOException
    {
        this.socket = socket;
        this.output = new ObjectOutputStream( socket.getOutputStream() );
        
        this.counter = 1;
        this.resend  = new TranslatorResendRunnable( this );
        
        this.service = Executors.newSingleThreadScheduledExecutor();
        this.service.scheduleAtFixedRate( resend , 1 , 1 , TimeUnit.MINUTES );
    }
    
    @Override
    public void application( EvaluationConf conf )
    {
        EvaluationProtocol p = new EvaluationProtocol();
        p.setId( counter++ );
        p.setOperation( "application" );
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
            resend.add( conf , TranslatorResendRunnable.TYPE_APP );
        }
    }

    @Override
    public void network( EvaluationConf conf )
    {
        EvaluationProtocol p = new EvaluationProtocol();
        p.setId( counter++ );
        p.setOperation( "network" );
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
            resend.add( conf , TranslatorResendRunnable.TYPE_NET );
        }
    }
    
    public boolean isThisSocket( Socket socket )
    {
        return socket.getPort() == this.socket.getPort();
    }
    
}
