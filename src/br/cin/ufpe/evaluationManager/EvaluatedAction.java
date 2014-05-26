package br.cin.ufpe.evaluationManager;

import br.cin.ufpe.evaluationManager.client.EditorClient;
import br.cin.ufpe.evaluationManager.client.TranslatorClient;
import br.cin.ufpe.evaluationManager.model.EvaluationConf;
import java.util.Properties;

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
   
   public void action( Properties properties )
   {
       long evaluateId = Long.parseLong  ( properties.getProperty( "evaluate_id" , "-1" ) );
       int code        = Integer.parseInt( properties.getProperty( "code"        , "-1" ) );
       
       System.out.println( "[MANAGER][EVALUATED] evaluated ID: " + evaluateId 
                                           + " | code: " + code );
       
       // ------------ recupera as informacoes sobre a avaliacao
       EvaluationConf conf = resource.getRepository().get( evaluateId );
       resource.getRepository().setCode( evaluateId , code );
       
       try
       {
           sendToApplication( code , properties , conf );
       }
       catch( Exception err )
       {
           System.err.println( "[EVALUATED][ERROR] " + err.getMessage() );
           resource.getEvaluatedResendRunnable().add( properties );
       }
   }
   
   private void sendToApplication( int code , Properties propeties , EvaluationConf conf ) throws Exception
   {
       if( code == EvaluationConf.CODE_EVALUATED_APP )
       {
           conf.setCode( EvaluationConf.CODE_EVALUATED_APP );
           
           String energy = propeties.getProperty( "application_energy" );       // consumo de energia
           conf.getConfiguration().put( "application_energy" , energy  );
           
           String time = propeties.getProperty( "application_time" );           // tempo de execucao
           conf.getConfiguration().put( "application_time" , time  );
           
           String packet = propeties.getProperty( "application_packet" );       // N° de pacotes criados
           conf.getConfiguration().put( "application_packet" , packet  );
           
           //TODO: adicionar as novas propriedades no Repositorio
           
           if( conf.getConfiguration().containsKey( "both_evaluate" ) )
           {
               TranslatorClient client = resource.selectTranslator();
               client.network( conf );
           }
           else if( conf.getConfiguration().containsKey( "sensibility_evaluate" ) )
           {
               try
               {
                   SensibilityAction action = new SensibilityAction( resource );
                   action.evaluated( conf );
               }
               catch( Exception err )
               {
                   System.err.println( "[EVALUATED-SENSIBILITY][ERROR] " + err.getMessage() );
                   System.err.println( "THIS ERROR SHOULD SEND TO EDITOR!" );
               }
           }
           else
           {
               finished( conf );
           }
       }
       else
       {
          finished( conf );
       }
   }
   
   private void finished( EvaluationConf conf ) throws Exception
   {
       EditorClient client = resource.selectEditor();
       client.finished( conf );
   }
   
}
