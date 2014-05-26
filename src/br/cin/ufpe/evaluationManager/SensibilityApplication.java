/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.cin.ufpe.evaluationManager;

import br.cin.ufpe.evaluationManager.client.TranslatorClient;
import br.cin.ufpe.evaluationManager.model.EvaluationConf;
import br.cin.ufpe.sensibility.file.SensibilityFile;
import br.cin.ufpe.sensibility.model.Application;
import br.cin.ufpe.sensibility.model.ConfigurationLayer;
import br.cin.ufpe.sensibility.model.Sensibility;
import java.util.HashMap;

/**
 *
 * @author avld
 */
public class SensibilityApplication
{
    private SensibilityAction action;
    
    public SensibilityApplication( SensibilityAction action )
    {
        this.action = action;
    }
    
    public void createConfigurationLayer( EvaluationConf conf ) throws Exception
    {
        String app_time   = conf.getConfiguration().get( "application_packet" );
        String app_energy = conf.getConfiguration().get( "application_energy" );
        
        // ------------ CREATE CONFIGURATION LAYER
        ConfigurationLayer layer = new ConfigurationLayer();
        layer.setName( "application_model" );
        
        layer.setConfMap( new HashMap<String, String>() );
        layer.getConfMap().put( "sendTo" , "1" );
        
        layer.setVarMap ( new HashMap<String, String>()  );
        layer.getVarMap().put( "app_time"   , app_time   );
        layer.getVarMap().put( "app_energy" , app_energy );
        
        // ------------ ADD IN SENSIBILITY FILE
        synchronized ( this )
        {
            String path = action.projectPath 
                    + action.resultPath 
                    + "sensibility.xml";
            
            Sensibility s = SensibilityFile.open( path );   // abre
            s.getApplicationLayer().add( layer );           // adiciona
            SensibilityFile.save( path , s );               // salva
        }
    }
    
    /**
     * Indica que uma aplicação deve ser avaliada
     * 
     * @throws Exception    caso aconteca algum problema na comunicacao
     */
    public void createdAndSendAllApplication()
    {
        for( Application app : action.sensibility.getApplication() )
        {
            String model = action.resultPath + "/app_" + app.getId() + ".cpn";
            
            createSubEvaluationForApplication( app.getPath() 
                                             , model
                                             , app.getFunction() );
        }
    }
    
    private void createSubEvaluationForApplication( String appPath , String modelPath , String function )
    {
        // ---------------- SET
        String parent = action.evaluateId + "";
        
        // ---------------- CREATE EVALUATION
        EvaluationConf subConf = new EvaluationConf();
        subConf.setUserId( action.userId               );
        subConf.setCode  ( EvaluationConf.CODE_CREATED );
        
        subConf.getConfiguration().put( "application_evaluate"   , "true"   );
        subConf.getConfiguration().put( "application_model_name" , modelPath);
        subConf.getConfiguration().put( "application_path"       , appPath  );
        subConf.getConfiguration().put( "application_function"   , function );
        
        // MORE CONFIGURATION!
        subConf.getConfiguration().put( "parent_id"        , parent );
        subConf.getConfiguration().put( "sensibility_path"        , action.filePath   );
        subConf.getConfiguration().put( "sensibility_result_path" , action.resultPath );
        
        // ---------------- REPOSITORY
        action.resource.getRepository().add( subConf );
        
        // ---------------- SEND TO TRANSLATOR
        try
        {
            TranslatorClient client = action.resource.selectTranslator();
            client.network( subConf );
        }
        catch( Exception err )
        {
            System.err.println( "[SENSIBILITY-APP][ERROR] " + err.getMessage() );
            action.resource.getAddedResendRunnable().add( subConf );
        }
    }
    
}
