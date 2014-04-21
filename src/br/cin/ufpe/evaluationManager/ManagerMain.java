package br.cin.ufpe.evaluationManager;

import br.cin.ufpe.evaluationManager.remote.ManagerServer;

/**
 *
 * @author avld
 */
public class ManagerMain
{
    
    public static void main( String arg[] ) throws Exception
    {
        ManagerServer server = new ManagerServer();
        server.start();
    }
    
}
