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
    }
    
    @Override
    public void created( EvaluationConf conf ) throws Exception
    {
        System.out.println( "[EDITOR] Created : " + conf.getEvaluateId() );
    }
    
    @Override
    public void finished( EvaluationConf conf ) throws Exception
    {
        System.out.println( "[EDITOR] Finished : " + conf.getEvaluateId() );
        
        Thread.sleep( 1000 );
        client.add( conf );
    }

    @Override
    public void inProgress( EvaluationConf conf , EvaluationStatus status ) throws Exception
    {
        
    }

    public ManagerClient getClient()
    {
        return client;
    }

    public void setClient( ManagerClient client )
    {
        this.client = client;
    }
    
}
