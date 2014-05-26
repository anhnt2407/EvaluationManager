package br.cin.ufpe.evaluationManager;

import br.cin.ufpe.evaluationManager.client.EditorClient;
import br.cin.ufpe.evaluationManager.client.EvaluatorClient;
import br.cin.ufpe.evaluationManager.client.TranslatorClient;
import br.cin.ufpe.evaluationManager.model.EvaluationConf;
import br.cin.ufpe.evaluationManager.service.ManagerService;
import java.net.Socket;
import java.util.Properties;

/**
 *
 * @author avld
 */
public class EvaluationManager implements ManagerService
{
    public static String PROJECT_PATH = "/opt/idea4wsn/";
    
    private static EvaluationManager instance;
    
    private Resource        resource        ;
    
    private EvaluationManager()
    {
        resource  = new Resource();
    }
    
    // -------------- SINGLETON
    
    public static EvaluationManager getInstance()
    {
        if( instance == null )
        {
            instance = new EvaluationManager();
        }
        
        return instance;
    }
    
    // --------------
    
    public boolean connected( int type , Socket socket ) throws Exception
    {
        switch( type )
        {
            case ManagerService.TYPE_EDITOR     :
                resource
                        .getEditorList()
                        .add( new EditorClient( socket ) );
                System.out.println( "[CONNECTED] adicionou um editor." );
                return true;
            case ManagerService.TYPE_EVALUATOR  :
                resource
                        .getEvaluatorList()
                        .add( new EvaluatorClient( socket ) ); 
                System.out.println( "[CONNECTED] adicionou um avaliador." );
                return true;
            case ManagerService.TYPE_TRANSLATOR :
                resource
                        .getTranslatorList()
                        .add( new TranslatorClient(socket ) ); 
                System.out.println( "[CONNECTED] adicionou um tradutor." );
                return true;
        }
        
        return false;
    }
    
    public void desconnected( int type , Socket socket )
    {
        System.out.println( "Removendo uma conexao..." );
        
        switch( type )
        {
            case ManagerService.TYPE_EDITOR     : resource.removeEditor( socket );     break ;
            case ManagerService.TYPE_EVALUATOR  : resource.removeEvaluator( socket );  break ;
            case ManagerService.TYPE_TRANSLATOR : resource.removeTranslator( socket ); break ;
        }
    }
    
    // --------------
    
    @Override
    public void add( EvaluationConf conf )
    {
        resource.getAddAction().action( conf );
    }

    @Override
    public void modelled( Properties properties )
    {
        resource.getModelledAction().action( properties );
    }

    @Override
    public void evaluated( Properties properties )
    {
        resource.getEvaluatedAction().action( properties );
    }
    
}
