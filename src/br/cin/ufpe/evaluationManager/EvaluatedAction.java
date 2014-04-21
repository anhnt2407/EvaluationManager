package br.cin.ufpe.evaluationManager;

import br.cin.ufpe.evaluationManager.client.EditorClient;
import br.cin.ufpe.evaluationManager.client.TranslatorClient;
import br.cin.ufpe.evaluationManager.model.EvaluationConf;
import br.cin.ufpe.evaluationManager.model.NetworkRequest;

/**
 * 
 * 
 * @author avld
 */
public class EvaluatedAction
{
   private Resource resource;
   
   public EvaluatedAction( Resource resource )
   {
       this.resource = resource;
   }
   
   public void action( long evaluateId , int code ) throws Exception
   {
       System.out.println( "[MANAGER][EVALUATED] evaluated ID: " + evaluateId + " | code: " + code );
       
       // ------------ recupera as informacoes sobre a avaliacao
       EvaluationConf conf = resource.getRepository().get( evaluateId );
       resource.getRepository().setCode( evaluateId , code );
        
       if( code == EvaluationConf.CODE_EVALUATED_APP
               && conf.getConfiguration().containsKey( "both_evaluate" ) )
       {
           // capturar as informacoes do consumo de energia da aplicacao
           
           TranslatorClient client = resource.selectTranslator();
           client.network( new NetworkRequest( conf ) );
       }
       else if( code == EvaluationConf.CODE_EVALUATED_APP
               && conf.getConfiguration().containsKey( "sensibility_evaluate" ) )
       {
           // TODO: criar cenarios
       }
       else
       {
           EditorClient client = resource.selectEditor();
           client.finished( conf );
       }
   }
   
}
