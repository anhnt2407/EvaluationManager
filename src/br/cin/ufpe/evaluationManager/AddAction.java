package br.cin.ufpe.evaluationManager;

import br.cin.ufpe.evaluationManager.client.TranslatorClient;
import br.cin.ufpe.evaluationManager.model.ApplicationRequest;
import br.cin.ufpe.evaluationManager.model.EvaluationConf;
import br.cin.ufpe.evaluationManager.model.EvaluationStatus;
import br.cin.ufpe.evaluationManager.model.NetworkRequest;

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
    
    public synchronized void action( EvaluationConf conf ) throws Exception
    {
        //---------- salva no repositorio
        long id = resource.getRepository().add( conf );
        
        //---------- cria um novo status
        resource.getRepository().add( new EvaluationStatus( id , "Created." ) );
        
        //---------- seleciona um tradutor
        TranslatorClient client = resource.selectTranslator();
        
        //---------- envia a requisicao para o tradutor
        if( conf.getConfiguration().containsKey( "application_evaluate" )
                || conf.getConfiguration().containsKey( "both_evaluate" ) )
        {
            client.application( new ApplicationRequest( conf ) );
        }
        else
        {
            client.network( new NetworkRequest( conf ) );
        }
        
        resource.selectEditor().created( conf );
    }
    
}
