package br.cin.ufpe.evaluationManager;

import br.cin.ufpe.evaluationManager.client.EvaluatorClient;
import br.cin.ufpe.evaluationManager.model.EvaluationConf;
import java.util.Properties;

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
    
    public void action( Properties properties )
    {
        long evaluateId = Long.parseLong  ( properties.getProperty( "evaluate_id" , "-1" ) );
        int code        = Integer.parseInt( properties.getProperty( "code" , "-1" )        );
        
        System.out.println( "[MANAGER][MODELLED] evaluated ID: " + evaluateId 
                                            + " | code: " + code );
        
        // ------------ recupera as informacoes sobre a avaliacao
        EvaluationConf conf = resource.getRepository().get( evaluateId );
        
        // ------------ repassa para o avaliador
        try
        {
            sendToEvaluator( code , conf );
        }
        catch( Exception err )
        {
            System.err.println( "[MODELLED][ERROR] " + err.getMessage() );
            resource.getModelledResendRunnable().add( properties );
        }
    }
    
    public void sendToEvaluator( int code , EvaluationConf conf ) throws Exception
    {
        if( code == EvaluationConf.CODE_MODELLDED_APP )
        {
            EvaluatorClient client = resource.selectEvaluator();
            client.application( conf );
        }
        else
        {
            EvaluatorClient client = resource.selectEvaluator();
            client.network( conf );
        }
    }
    
}
