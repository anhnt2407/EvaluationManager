package br.cin.ufpe.evaluationManager.example;

import br.cin.ufpe.evaluationManager.client.ManagerClient;
import br.cin.ufpe.evaluationManager.model.EvaluationConf;
import br.cin.ufpe.evaluationManager.service.EvaluatorService;

/**
 *
 * @author avld
 */
public class EvaluatorImpl implements EvaluatorService
{
    private ManagerClient client;
    
    public EvaluatorImpl() throws Exception
    {
        System.out.println( "[EVALUATOR] Started." );
        client = new ManagerClient( "127.0.0.1" , 6789 , this );
    }

    @Override
    public void application( long id , String modelFilePath ) throws Exception
    {
        System.out.println( "[EVALUATOR] evaluate a application at "+ modelFilePath +"." );
        
        Thread.sleep( 1000 );
        client.evaluated( id , EvaluationConf.CODE_EVALUATED_APP );
    }

    @Override
    public void network( long id , String modelFilePath ) throws Exception
    {
        System.out.println( "[EVALUATOR] evaluate a network at "+ modelFilePath +"." );
        
        Thread.sleep( 1000 );
        client.evaluated( id , EvaluationConf.CODE_EVALUATED_NET );
    }
    
}
