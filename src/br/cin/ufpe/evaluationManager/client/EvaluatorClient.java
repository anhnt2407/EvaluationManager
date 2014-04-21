package br.cin.ufpe.evaluationManager.client;

import br.cin.ufpe.evaluationManager.remote.EvaluationProtocol;
import br.cin.ufpe.evaluationManager.service.EvaluatorService;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author avld
 */
public class EvaluatorClient implements EvaluatorService
{
    private int counter;
    private Socket socket;
    private ObjectOutputStream output;
    
    public EvaluatorClient( Socket socket ) throws IOException
    {
        this.counter = 1;
        
        this.socket = socket;
        this.output = new ObjectOutputStream( socket.getOutputStream() );
    }
    
    @Override
    public void application( long id , String modelFilePath ) throws Exception
    {
        EvaluationProtocol p = new EvaluationProtocol();
        p.setId( counter++ );
        p.setOperation( "application" );
        p.addParam( id );
        p.addParam( modelFilePath );
        
        output.writeObject( p );
    }

    @Override
    public void network( long id , String modelFilePath ) throws Exception
    {
        EvaluationProtocol p = new EvaluationProtocol();
        p.setId( counter++ );
        p.setOperation( "network" );
        p.addParam( id );
        p.addParam( modelFilePath );
        
        output.writeObject( p );
    }
    
    public boolean isThisSocket( Socket socket )
    {
        return socket.getPort() == this.socket.getPort();
    }
    
}
