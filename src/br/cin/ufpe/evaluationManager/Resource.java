package br.cin.ufpe.evaluationManager;

import br.cin.ufpe.evaluationManager.client.EditorClient;
import br.cin.ufpe.evaluationManager.client.EvaluatorClient;
import br.cin.ufpe.evaluationManager.client.TranslatorClient;
import br.cin.ufpe.evaluationManager.repository.CacheRepository;
import br.cin.ufpe.evaluationManager.repository.Repository;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

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
    
    public Resource()
    {
        editorList     = new ArrayList<>();
        translatorList = new ArrayList<>();
        evaluatorList  = new ArrayList<>();
    
        editorId       = 0;
        translatorId   = 0;
        evaluatorId    = 0;
        
        repository     = new CacheRepository();
    }

    // ----------------------
    // ---------------------- SELECT
    // ----------------------
    
    public synchronized EditorClient selectEditor()
    {
        if( editorId >= editorList.size() )
        {
            editorId = 0;
        }
        
        System.out.println( "[EDITOR] selected: " + editorId + " | size: " + editorList.size() );
        EditorClient client = editorList.get( editorId++ );
        // TODO: remover caso não esteja OK!
        
        return client;
    }

    public synchronized TranslatorClient selectTranslator()
    {
        if( translatorId >= translatorList.size() )
        {
            translatorId = 0;
        }
        
        System.out.println( "[TRANSLATOR] selected: " + translatorId + " | size: " + translatorList.size() );
        TranslatorClient client = translatorList.get( translatorId++ );
        // TODO: remover caso não esteja OK!
        
        return client;
    }

    public synchronized EvaluatorClient selectEvaluator()
    {
        if( evaluatorId >= evaluatorList.size() )
        {
            evaluatorId = 0;
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
    
}
