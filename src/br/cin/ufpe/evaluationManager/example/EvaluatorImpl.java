package br.cin.ufpe.evaluationManager.example;

import br.cin.ufpe.evaluationManager.client.ManagerClient;
import br.cin.ufpe.evaluationManager.model.EvaluationConf;
import br.cin.ufpe.evaluationManager.service.EvaluatorService;
import java.util.Properties;

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
        
        client = new ManagerClient( this );
        client.conect( "127.0.0.1" , 6789 );
    }

    @Override
    public void application( EvaluationConf conf )
    {
        System.out.println( "[EVALUATOR] evaluate a application at "+ conf.getEvaluateId() +"." );
        
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
        p.setProperty( "code"        , EvaluationConf.CODE_EVALUATED_APP + "" );
        
        p.setProperty( "application_energy" , "0.00" );  // consumo de energia
        p.setProperty( "application_time"   , "0.00" );  // tempo de execucao
        p.setProperty( "application_packet" , "0.00" );  // N° de pacotes criados
        
        client.evaluated( p );
    }

    @Override
    public void network( EvaluationConf conf )
    {
        System.out.println( "[EVALUATOR] evaluate a network at "+ conf.getEvaluateId() +"." );
        
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
        p.setProperty( "code"        , EvaluationConf.CODE_EVALUATED_NET + "" );
        
        p.setProperty( "network_time"   , "0.00" );  // tempo de execucao
        
        client.evaluated( p );
    }
    
}
