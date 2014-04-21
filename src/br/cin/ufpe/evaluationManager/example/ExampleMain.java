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
        EditorImpl editor         = new EditorImpl();
        TranslatorImpl translator = new TranslatorImpl();
        EvaluatorImpl evaluator   = new EvaluatorImpl();
        
        System.out.println( "[MAIN] every was started." );
        
        ManagerClient client = new ManagerClient( "127.0.0.1" , 6789 , editor );
        editor.setClient( client );
        
        EvaluationConf conf = new EvaluationConf();
        conf.setUserId( 1 );
        conf.setProjectId( 1 );
        conf.getConfiguration().put( "application_evaluate"   , "true"      );
        conf.getConfiguration().put( "application_model_name" , "model.cpn" );
        conf.getConfiguration().put( "application_path"       , "./"        );
        conf.getConfiguration().put( "application_function"   , "teste"     );
        
        client.add( conf );
    }
    
}
