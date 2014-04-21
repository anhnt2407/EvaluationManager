package br.cin.ufpe.evaluationManager;

import br.cin.ufpe.evaluationManager.client.EditorClient;
import br.cin.ufpe.evaluationManager.client.EvaluatorClient;
import br.cin.ufpe.evaluationManager.client.TranslatorClient;
import br.cin.ufpe.evaluationManager.model.EvaluationConf;
import br.cin.ufpe.evaluationManager.service.ManagerService;
import java.net.Socket;

/**
 *
 * @author avld
 */
public class EvaluationManager implements ManagerService
{
    private static EvaluationManager instance;
    
    private Resource        resource;
    private AddAction       addAction;
    private ModelledAction  modelledAction;
    private EvaluatedAction evaluatedAction;
    
    private EvaluationManager()
    {
        resource  = new Resource();
        
        addAction       = new AddAction( resource );
        modelledAction  = new ModelledAction( resource );
        evaluatedAction = new EvaluatedAction( resource );
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
                return true;
            case ManagerService.TYPE_EVALUATOR  :
                resource
                        .getEvaluatorList()
                        .add( new EvaluatorClient( socket ) ); 
                return true;
            case ManagerService.TYPE_TRANSLATOR :
                resource
                        .getTranslatorList()
                        .add( new TranslatorClient(socket ) ); 
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
        try
        {
            addAction.action( conf );
        }
        catch( Exception err )
        {
            System.err.println( "[ADD][ERROR] " + err.getMessage() + ". [TRY AGAIN]" );
            add( conf );
        }
    }

    @Override
    public void modelled( long evaluateId , int type )
    {
        try
        {
            modelledAction.action( evaluateId , type );
        }
        catch( Exception err )
        {
            System.err.println( "[MODELLED][ERROR] " + err.getMessage() + ". [TRY AGAIN]" );
            modelled( evaluateId , type );
        }
    }

    @Override
    public void evaluated( long evaluateId , int type )
    {
        try
        {
            evaluatedAction.action( evaluateId , type );
        }
        catch( Exception err )
        {
            System.err.println( "[EVALUATED][ERROR] " + err.getMessage() + ". [TRY AGAIN]" );
            evaluated( evaluateId , type );
        }
    }
    
}
