package br.cin.ufpe.evaluationManager;

import br.cin.ufpe.evaluationManager.client.EvaluatorClient;
import br.cin.ufpe.evaluationManager.model.EvaluationConf;

/**
 * 
 * 
 * @author avld
 */
public class ModelledAction
{
    private Resource resource;
    
    public ModelledAction( Resource resource )
    {
        this.resource = resource;
    }
    
    public void action( long evaluateId , int code ) throws Exception
    {
        System.out.println( "[MANAGER][MODELLED] evaluated ID: " + evaluateId + " | code: " + code );
        
        // ------------ recupera as informacoes sobre a avaliacao
        EvaluationConf conf = resource.getRepository().get( evaluateId );
        
        // ------------ repassa para o avaliador
        EvaluatorClient client = resource.selectEvaluator();
        
        if( code == EvaluationConf.CODE_MODELLDED_APP ) // 0 = application , 1 = network
        {
            String name = conf.getConfiguration().get( "application_model_name" );
            client.application( evaluateId , name );
        }
        else
        {
            String name = conf.getConfiguration().get( "network_model_name" );
            client.network( evaluateId , name );
        }
    }
    
}
