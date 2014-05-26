package br.cin.ufpe.evaluationManager;

import br.cin.ufpe.evaluationManager.client.EditorClient;
import br.cin.ufpe.evaluationManager.client.EvaluatorClient;
import br.cin.ufpe.evaluationManager.client.TranslatorClient;
import br.cin.ufpe.evaluationManager.repository.CacheRepository;
import br.cin.ufpe.evaluationManager.repository.Repository;
import br.cin.ufpe.evaluationManager.resend.AddedResendRunnable;
import br.cin.ufpe.evaluationManager.resend.EvaluatedResendRunnable;
import br.cin.ufpe.evaluationManager.resend.ModelledResendRunnable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author avld
 */
public class Resource
{
    private List<EditorClient>     editorList;
    private List<TranslatorClient> translatorList;
    private List<EvaluatorClient>  evaluatorList;
    
    private int editorId;
    private int translatorId;
    private int evaluatorId;
    
    private Repository repository;
    
    private AddAction       addAction       ;
    private ModelledAction  modelledAction  ;
    private EvaluatedAction evaluatedAction ;
    
    private ScheduledExecutorService service                 ;
    private AddedResendRunnable      addedResendRunnable     ;
    private ModelledResendRunnable   modelledResendRunnable  ;
    private EvaluatedResendRunnable  evaluatedResendRunnable ;
    
    public Resource()
    {
        editorList     = new ArrayList<>();
        translatorList = new ArrayList<>();
        evaluatorList  = new ArrayList<>();
    
        editorId       = 0;
        translatorId   = 0;
        evaluatorId    = 0;
        
        repository     = new CacheRepository();
        
        addAction       = new AddAction( this );
        modelledAction  = new ModelledAction( this );
        evaluatedAction = new EvaluatedAction( this );
        
        // --------------------------
        
        addedResendRunnable     = new AddedResendRunnable    ( this );
        modelledResendRunnable  = new ModelledResendRunnable ( this );
        evaluatedResendRunnable = new EvaluatedResendRunnable( this );
        
        // --------------------------
        
        service = Executors.newScheduledThreadPool( 3 );
        service.scheduleAtFixedRate( addedResendRunnable     , 1 , 1 , TimeUnit.MINUTES );
        service.scheduleAtFixedRate( modelledResendRunnable  , 1 , 1 , TimeUnit.MINUTES );
        service.scheduleAtFixedRate( evaluatedResendRunnable , 1 , 1 , TimeUnit.MINUTES );
    }

    // ----------------------
    // ---------------------- SELECT
    // ----------------------
    
    public synchronized EditorClient selectEditor() throws Exception
    {
        if( editorId >= editorList.size() )
        {
            editorId = 0;
        }
        
        if( editorList.isEmpty() )
        {
            throw new Exception( "There is no registered editor." );
        }
        
        System.out.println( "[EDITOR] selected: " + editorId + " | size: " + editorList.size() );
        EditorClient client = editorList.get( editorId++ );
        // TODO: remover caso não esteja OK!
        
        return client;
    }

    public synchronized TranslatorClient selectTranslator() throws Exception
    {
        if( translatorId >= translatorList.size() )
        {
            translatorId = 0;
        }
        
        if( translatorList.isEmpty() )
        {
            throw new Exception( "There is no registered translator." );
        }
        
        System.out.println( "[TRANSLATOR] selected: " + translatorId + " | size: " + translatorList.size() );
        TranslatorClient client = translatorList.get( translatorId++ );
        // TODO: remover caso não esteja OK!
        
        return client;
    }

    public synchronized EvaluatorClient selectEvaluator() throws Exception
    {
        if( evaluatorId >= evaluatorList.size() )
        {
            evaluatorId = 0;
        }
        
        if( evaluatorList.isEmpty() )
        {
            throw new Exception( "There is no registered evaluator." );
        }
        
        System.out.println( "[EVALUATOR] selected: " + evaluatorId + " | size: " + evaluatorList.size() );
        EvaluatorClient client = evaluatorList.get( evaluatorId++ );
        // TODO: remover caso não esteja OK!
        
        return client;
    }

    // ----------------------
    // ---------------------- GET
    // ----------------------
    
    public Repository getRepository()
    {
        return repository;
    }

    public List<EditorClient> getEditorList()
    {
        return editorList;
    }

    public List<TranslatorClient> getTranslatorList()
    {
        return translatorList;
    }

    public List<EvaluatorClient> getEvaluatorList()
    {
        return evaluatorList;
    }
    
    // ----------------------
    // ---------------------- REMOVE
    // ----------------------
    
    public synchronized  void removeEditor( Socket socket )
    {
        int selected = -1;
        
        for( int i = 0 ; i < editorList.size() ; i++ )
        {
             EditorClient client = editorList.get( i );
            
            if( client.isThisSocket( socket ) )
            {
                System.out.println( "encontrou um editor [PORTA: "+ socket.getPort() +"]!" );
                selected = i;
                break ;
            }
        }
        
        editorList.remove( selected );
    }
    
    public synchronized  void removeTranslator( Socket socket )
    {
        int selected = -1;
        
        for( int i = 0 ; i < translatorList.size() ; i++ )
        {
             TranslatorClient client = translatorList.get( i );
            
            if( client.isThisSocket( socket ) )
            {
                System.out.println( "encontrou um translator [PORTA: "+ socket.getPort() +"]!" );
                selected = i;
                break ;
            }
        }
        
        translatorList.remove( selected );
    }
    
    public synchronized  void removeEvaluator( Socket socket )
    {
        int selected = -1;
        
        for( int i = 0 ; i < evaluatorList.size() ; i++ )
        {
             EvaluatorClient client = evaluatorList.get( i );
            
            if( client.isThisSocket( socket ) )
            {
                System.out.println( "encontrou um evaluator [PORTA: "+ socket.getPort() +"]!" );
                selected = i;
                break ;
            }
        }
        
        evaluatorList.remove( selected );
    }
    
    // ----------------------
    // ---------------------- ACTIONS
    // ----------------------
    
    public AddAction getAddAction()
    {
        return addAction;
    }

    public ModelledAction getModelledAction()
    {
        return modelledAction;
    }

    public EvaluatedAction getEvaluatedAction()
    {
        return evaluatedAction;
    }
    
    // ----------------------
    // ---------------------- RUNNABLE
    // ----------------------
    
    public AddedResendRunnable getAddedResendRunnable()
    {
        return addedResendRunnable;
    }

    public ModelledResendRunnable getModelledResendRunnable()
    {
        return modelledResendRunnable;
    }

    public EvaluatedResendRunnable getEvaluatedResendRunnable()
    {
        return evaluatedResendRunnable;
    }
    
}
