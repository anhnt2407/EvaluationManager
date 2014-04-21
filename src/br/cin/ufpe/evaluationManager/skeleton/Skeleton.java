package br.cin.ufpe.evaluationManager.skeleton;

import br.cin.ufpe.evaluationManager.remote.EvaluationProtocol;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 *
 * @author avld
 */
public abstract class Skeleton implements Runnable
{
    private Socket socket;
    private ObjectInputStream input;
    
    public Skeleton( Socket socket ) throws Exception
    {
        this.socket = socket;
    }

    @Override
    public void run()
    {
        try
        {
            input  = new ObjectInputStream( socket.getInputStream() );
            
            while( !socket.isClosed() )
            {
                EvaluationProtocol p = (EvaluationProtocol) input.readObject();
                process( p );
            }
        }
        catch( Exception err )
        {
            System.err.println( "[SKELETON][ERROR] " + err.getMessage() );
        }
        
        try
        {
            input.close();
            socket.close();
        }
        catch( Exception err )
        {
            System.err.println( "[SKELETON][ERROR] " + err.getMessage() );
        }
        
        desconnected();
        System.out.println( "Conexão fechada [PORTA: "+ socket.getPort() +"]." );
    }
    
    public abstract void process( EvaluationProtocol p ) throws Exception;

    public void desconnected()
    {
        // do nothing
    }
    
    public Socket getSocket()
    {
        return socket;
    }

    public ObjectInputStream getInput()
    {
        return input;
    }
    
}
