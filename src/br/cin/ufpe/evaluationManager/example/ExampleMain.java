package br.cin.ufpe.evaluationManager.example;

import br.cin.ufpe.evaluationManager.client.ManagerClient;
import br.cin.ufpe.evaluationManager.model.EvaluationConf;

/**
 *
 * @author avld
 */
public class ExampleMain
{
    
    public static void main( String arg[] ) throws Exception
    {
        //EditorImpl editor         = new EditorImpl();
        TranslatorImpl translator = new TranslatorImpl();
        EvaluatorImpl evaluator   = new EvaluatorImpl();
        
        System.out.println( "[MAIN] every was started." );
        
        //sensibilityAnalysis( editor.getClient() );
        
        System.out.println( "[MAIN] send a packet." );
    }
    
    private static void application( ManagerClient client )
    {
        EvaluationConf conf = new EvaluationConf();
        conf.setUserId( 1 );
        
        conf.getConfiguration().put( "application_evaluate"   , "true"                   );
        conf.getConfiguration().put( "application_model_name" , "/Blink/application.cpn" );
        conf.getConfiguration().put( "application_path"       , "/Blink/BlinkAppC.nc"    );
        conf.getConfiguration().put( "application_function"   , "Boot.booted"            );
        
        client.add( conf );
    }
    
    private static void network( ManagerClient client )
    {
        EvaluationConf conf = new EvaluationConf();
        conf.setUserId( 1 );
        
        conf.getConfiguration().put( "network_evaluate"   , "true"      );
        conf.getConfiguration().put( "network_reliability", "true"      );
        conf.getConfiguration().put( "network_model_name" , "/Blink/topology.cpn" );
        conf.getConfiguration().put( "network_path"       , "/Blink/topology.wsn" );
        
        conf.getConfiguration().put( "criteria_stop"      , "FND" );
        conf.getConfiguration().put( "criteria_stop_value", ""    );
        
        client.add( conf );
    }
    
    private static void both( ManagerClient client )
    {
        EvaluationConf conf = new EvaluationConf();
        conf.setUserId( 1 );
        
        conf.getConfiguration().put( "both_evaluate"   , "true"        );
        
        // APPLICATION CONF
        conf.getConfiguration().put( "application_model_name" , "/Blink/application.cpn" );
        conf.getConfiguration().put( "application_path"       , "/Blink/BlinkAppC.nc"    );
        conf.getConfiguration().put( "application_function"   , "Boot.booted" );
        
        // NETWORK CONF
        conf.getConfiguration().put( "network_reliability", "true"      );
        conf.getConfiguration().put( "network_model_name" , "/Blink/topology.cpn" );
        conf.getConfiguration().put( "network_path"       , "/Blink/topology.wsn" );
        
        conf.getConfiguration().put( "criteria_stop"      , "HND" );
        conf.getConfiguration().put( "criteria_stop_value", ""    );
        
        client.add( conf );
    }
    
    private static void sensibilityAnalysis( ManagerClient client )
    {
        EvaluationConf conf = new EvaluationConf();
        conf.setUserId( 1 );
        
        conf.getConfiguration().put( "sensibility_evaluate" , "true"                   );
        conf.getConfiguration().put( "sensibility_path"     , "/Blink/sensibility.xml" );
        conf.getConfiguration().put( "sensibility_result"   , "/Blink/sensibility/"    );
        
        String resultPath  = "/Blink/sensibility_results/" 
                            + System.currentTimeMillis() 
                            + "/";
        
        client.add( conf );
    }
    
}
