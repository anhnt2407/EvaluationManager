package br.cin.ufpe.evaluationManager.client;

import br.cin.ufpe.evaluationManager.model.ApplicationRequest;
import br.cin.ufpe.evaluationManager.model.NetworkRequest;
import br.cin.ufpe.evaluationManager.remote.EvaluationProtocol;
import br.cin.ufpe.evaluationManager.service.TranslatorService;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author avld
 */
public class TranslatorClient implements TranslatorService
{
    private int counter;
    private Socket socket;
    private ObjectOutputStream output;
    
    public TranslatorClient( Socket socket ) throws IOException
    {
        this.counter = 1;
        
        this.socket = socket;
        this.output = new ObjectOutputStream( socket.getOutputStream() );
    }
    
    @Override
    public void application( ApplicationRequest conf ) throws Exception
    {
        EvaluationProtocol p = new EvaluationProtocol();
        p.setId( counter++ );
        p.setOperation( "application" );
        p.addParam( conf );
        
        output.writeObject( p );
    }

    @Override
    public void network( NetworkRequest conf ) throws Exception
    {
        EvaluationProtocol p = new EvaluationProtocol();
        p.setId( counter++ );
        p.setOperation( "network" );
        p.addParam( conf );
        
        output.writeObject( p );
    }
    
    public boolean isThisSocket( Socket socket )
    {
        return socket.getPort() == this.socket.getPort();
    }
    
}
