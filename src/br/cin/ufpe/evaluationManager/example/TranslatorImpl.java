package br.cin.ufpe.evaluationManager.example;

import br.cin.ufpe.evaluationManager.client.ManagerClient;
import br.cin.ufpe.evaluationManager.model.EvaluationConf;
import br.cin.ufpe.evaluationManager.service.TranslatorService;
import java.util.Properties;

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
        
        client = new ManagerClient( this );
        client.conect( "127.0.0.1" , 6789 );
    }
    
    @Override
    public void application( EvaluationConf conf )
    {
        System.out.println( "[TRANSLATOR] translate a application." );
        
        try
        {
            Thread.sleep( 1000 );
        }
        catch ( InterruptedException ex )
        {
            // do nothing
        }
        
        Properties p = new Properties();
        p.setProperty( "evaluate_id" , conf.getEvaluateId() + ""              );
        p.setProperty( "code"        , EvaluationConf.CODE_MODELLDED_APP + "" );
        
        client.modelled( p );
    }

    @Override
    public void network( EvaluationConf conf )
    {
        System.out.println( "[TRANSLATOR] translate a network." );
        
        try
        {
            Thread.sleep( 1000 );
        }
        catch ( InterruptedException ex )
        {
            // do nothing
        }
        
        Properties p = new Properties();
        p.setProperty( "evaluate_id" , conf.getEvaluateId() + ""              );
        p.setProperty( "code"        , EvaluationConf.CODE_MODELLDED_NET + "" );
        
        client.modelled( p );
    }
    
}
