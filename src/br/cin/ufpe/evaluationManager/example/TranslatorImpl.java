package br.cin.ufpe.evaluationManager.example;

import br.cin.ufpe.evaluationManager.client.ManagerClient;
import br.cin.ufpe.evaluationManager.model.ApplicationRequest;
import br.cin.ufpe.evaluationManager.model.EvaluationConf;
import br.cin.ufpe.evaluationManager.model.NetworkRequest;
import br.cin.ufpe.evaluationManager.service.TranslatorService;

/**
 *
 * @author avld
 */
public class TranslatorImpl implements TranslatorService
{
    private ManagerClient client;
    
    public TranslatorImpl() throws Exception
    {
        System.out.println( "[TRANSLATOR] Started." );
        client = new ManagerClient( "127.0.0.1" , 6789 , this );
    }
    
    @Override
    public void application( ApplicationRequest conf ) throws Exception
    {
        System.out.println( "[TRANSLATOR] translate a application." );
        
        Thread.sleep( 1000 );
        client.modelled( conf.getEvaluateId() , EvaluationConf.CODE_MODELLDED_APP );
    }

    @Override
    public void network( NetworkRequest conf ) throws Exception
    {
        System.out.println( "[TRANSLATOR] translate a network." );
        
        Thread.sleep( 1000 );
        client.modelled( conf.getEvaluateId() , EvaluationConf.CODE_MODELLDED_NET );
    }
    
}
