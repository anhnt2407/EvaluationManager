package br.cin.ufpe.evaluationManager.client;

import br.cin.ufpe.evaluationManager.model.EvaluationConf;
import br.cin.ufpe.evaluationManager.model.EvaluationStatus;
import br.cin.ufpe.evaluationManager.remote.EvaluationProtocol;
import br.cin.ufpe.evaluationManager.service.EditorService;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author avld
 */
public class EditorClient implements EditorService
{
    private int counter;
    private Socket socket;
    private ObjectOutputStream output;
    
    public EditorClient( Socket socket ) throws IOException
    {
        this.counter = 1;
        
        this.socket = socket;
        this.output = new ObjectOutputStream( socket.getOutputStream() );
    }
    
    @Override
    public void created( EvaluationConf conf ) throws Exception
    {
        EvaluationProtocol p = new EvaluationProtocol();
        p.setId( counter++ );
        p.setOperation( "created" );
        p.addParam( conf );
        
        output.writeObject( p );
    }
    
    @Override
    public void finished( EvaluationConf conf ) throws Exception
    {
        EvaluationProtocol p = new EvaluationProtocol();
        p.setId( counter++ );
        p.setOperation( "finished" );
        p.addParam( conf );
        
        output.writeObject( p );
    }

    @Override
    public void inProgress( EvaluationConf conf , EvaluationStatus status ) throws Exception
    {
        EvaluationProtocol p = new EvaluationProtocol();
        p.setId( counter++ );
        p.setOperation( "inProgress" );
        p.addParam( conf );
        p.addParam( status );
        
        output.writeObject( p );
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
