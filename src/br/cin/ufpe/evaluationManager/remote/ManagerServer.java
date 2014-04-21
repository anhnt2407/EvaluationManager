package br.cin.ufpe.evaluationManager.remote;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Start and Stop a server.
 * 
 * @author avld
 */
public class ManagerServer
{
    public static final int PORT = 6789;
    
    public int port;
    private ServerRunnable server;
    private ExecutorService threadPool;
    
    public ManagerServer()
    {
        port       = PORT;
        threadPool = Executors.newCachedThreadPool();
    }
    
    public void start() throws Exception
    {
        server = new ServerRunnable( port , threadPool );
        threadPool.submit( server );
    }
    
    public void stop() throws Exception
    {
        if( server != null )
        {
            server.stop();
        }
    }
    
    // -------------------------
    // -------------------------
    // -------------------------
    
    public int getPort()
    {
        return port;
    }

    public void setPort( int port )
    {
        this.port = port;
    }
    
}
