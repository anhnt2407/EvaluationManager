package br.cin.ufpe.evaluationManager;

import br.cin.ufpe.evaluationManager.client.TranslatorClient;
import br.cin.ufpe.evaluationManager.model.EvaluationConf;
import br.cin.ufpe.evaluationManager.model.EvaluationStatus;

/**
 *
 * @author avld
 */
public class AddAction
{
    private Resource resource;
    
    public AddAction( Resource resource )
    {
        this.resource = resource;
    }
    
    public synchronized void action( EvaluationConf conf )
    {
        //---------- salva no repositorio
        long id = resource.getRepository().add( conf );
        
        //---------- cria um novo status
        resource.getRepository().add( new EvaluationStatus( id , "Created." ) );
        
        //---------- envia a requisicao para o tradutor
        try
        {
            sendToTranslator( conf );
        }
        catch( Exception err )
        {
            resource.getAddedResendRunnable().add( conf );
        }
        
        try
        {
            resource.selectEditor().created( conf );
        }
        catch( Exception err )
        {
            System.err.println( "[ADD][ERROR] " + err.getMessage() );
        }
    }
    
    public void sendToTranslator( EvaluationConf conf ) throws Exception
    {
        if( conf.getConfiguration().containsKey( "application_evaluate" )
                || conf.getConfiguration().containsKey( "both_evaluate" ) )
        {
            TranslatorClient client = resource.selectTranslator();      //seleciona
            client.application( conf );                                 //envia
        }
        else if ( conf.getConfiguration().containsKey( "network_evaluate" ) )
        {
            TranslatorClient client = resource.selectTranslator();      //seleciona
            client.network( conf );                                     //envia
        }
        else if( conf.getConfiguration().containsKey( "sensibility_evaluate" ) )
        {
            try
            {
                SensibilityAction action = new SensibilityAction( resource );
                action.add( conf );
            }
            catch( Exception err )
            {
                System.err.println( "[ADD][ERROR] " + err.getMessage() );
                System.err.println( "THIS ERROR SHOULD SEND TO EDITOR!" );
            }
        }
    }
    
}
