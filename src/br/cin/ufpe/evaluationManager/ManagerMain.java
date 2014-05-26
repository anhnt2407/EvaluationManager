package br.cin.ufpe.evaluationManager;

import br.cin.ufpe.evaluationManager.remote.ManagerServer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 *
 * @author avld
 */
public class ManagerMain
{
    private static final String CONF_FILENAME = "./configuration.ini";
    private static File file;
    
    public static void main( String arg[] ) throws Exception
    {
        file = new File( CONF_FILENAME );
        if( !file.exists() )
        {
            file.createNewFile();
        }
        
        Properties properties = open();
        setDefaultValues( properties );
        
        ManagerServer server = new ManagerServer();
        server.start();
    }

    private static Properties open() throws Exception
    {
        Properties properties = new Properties();
        
        FileInputStream input = new FileInputStream( CONF_FILENAME );
        properties.load( input );
        input.close();
        
        return properties;
    }
    
    private static void setDefaultValues( Properties p ) throws Exception
    {
        String port  = p.getProperty( "port"         , ManagerServer.PORT + "" );
        String path  = p.getProperty( "project_path" , EvaluationManager.PROJECT_PATH );
        
        // -------------------------------- SET THE PROPERTIES
        p.setProperty( "port" , port );
        p.setProperty( "project_path"     , path  );
        
        // -------------------------------- SET THE CONFIGURATION
        ManagerServer.PORT             = Integer.parseInt( port );
        EvaluationManager.PROJECT_PATH = path;
        
        // -------------------------------- SAVE THE PROPERTIES
        FileOutputStream output = new FileOutputStream( CONF_FILENAME );
        p.store( output , "" );
        output.close();
    }
    
}
