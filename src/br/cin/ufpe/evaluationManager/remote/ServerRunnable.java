package br.cin.ufpe.evaluationManager.remote;

import br.cin.ufpe.evaluationManager.skeleton.ManagerSkeleton;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

/**
 * This class open and listen a port.
 * When it receives a packet, it send to ConnectionRunnable.
 * 
 * @author avld
 */
public class ServerRunnable implements Runnable
{
    private boolean execute;
    private ServerSocket server;
    private ExecutorService threadPool;
    
    public ServerRunnable( int port , ExecutorService threadPool ) throws Exception
    {
        server  = new ServerSocket( port );
        execute = false;
        
        this.threadPool = threadPool;
    }

    @Override
    public void run()
    {
        System.out.println( "Server was started..." );
        execute = true;
        
        while( execute )
        {
            try
            {
                execute();
            }
            catch( Exception err )
            {
                err.printStackTrace();
                System.err.println( "---------" );
            }
        }
        
        System.out.println( "Server was stopped..." );
    }
    
    private void execute() throws Exception
    {
        Socket socket = server.accept();
        ManagerSkeleton skeleton = new ManagerSkeleton( socket );

        threadPool.submit( skeleton );
    }
    
    public boolean isExecute()
    {
        return execute;
    }
    
    public void stop() throws Exception
    {
        execute = false;
        server.close();
    }
    
}
