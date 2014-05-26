package br.cin.ufpe.evaluationManager.example;

import br.cin.ufpe.evaluationManager.client.ManagerClient;
import br.cin.ufpe.evaluationManager.model.EvaluationConf;
import br.cin.ufpe.evaluationManager.model.EvaluationStatus;
import br.cin.ufpe.evaluationManager.service.EditorService;

/**
 *
 * @author avld
 */
public class EditorImpl implements EditorService
{
    private ManagerClient client;
    
    public EditorImpl() throws Exception
    {
        System.out.println( "[EDITOR] Started." );
        
        client = new ManagerClient( this );
        client.conect( "127.0.0.1" , 6789 );
    }
    
    @Override
    public void created( EvaluationConf conf )
    {
        System.out.println( "[EDITOR] Created : " + conf.getEvaluateId() );
    }
    
    @Override
    public void finished( EvaluationConf conf )
    {
        System.out.println( "[EDITOR] Finished : " + conf.getEvaluateId() );
        
        try
        {
            Thread.sleep( 1000 );
        }
        catch ( InterruptedException ex )
        {
            
        }
        
        client.add( conf );
    }

    @Override
    public void inProgress( EvaluationConf conf , EvaluationStatus status )
    {
        
    }

    public ManagerClient getClient()
    {
        return client;
    }

}
