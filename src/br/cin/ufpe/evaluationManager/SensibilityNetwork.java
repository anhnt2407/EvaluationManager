package br.cin.ufpe.evaluationManager;

import br.cin.ufpe.evaluationManager.client.TranslatorClient;
import br.cin.ufpe.evaluationManager.model.EvaluationConf;
import br.cin.ufpe.sensibility.ScenarioCreator;
import br.cin.ufpe.sensibility.ScenarioTopology;
import br.cin.ufpe.sensibility.file.ScenarioFile;
import br.cin.ufpe.sensibility.model.Scenario;
import br.cin.ufpe.sensibility.validate.SensibilityValidate;
import br.cin.ufpe.wsn2cpn.Topology;
import br.cin.ufpe.wsn2cpn.WsnFile;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 
 * @author avld
 */
public class SensibilityNetwork
{
    private SensibilityAction action;
    
    public SensibilityNetwork( SensibilityAction action )
    {
        this.action = action;
    }
    
    /**
     * Cria todos os cenarios, gera novos EvaluationConf e repassa-os para
     * os tradutores e avaliadores.
     * 
     * @throws Exception        caso ocorra um erro ao enviar os cenarios
     */
    public void createAndSendAllScenarios( EvaluationConf conf ) throws Exception
    {
        ScenarioCreator creator = new ScenarioCreator( action.sensibility );
        List<Scenario> list = creator.createAllScenarioPossible();
        
        List<Long> children = new ArrayList<>();
        for( Scenario scenario : list )
        {
            long id = createScenario( scenario );
            children.add( id );
        }
        
        conf.getChildren().addAll( children );
    }
    
    /**
     * Cria uma cenario e um EvaluationConf
     * 
     * @param scenario      cenario a ser criado
     * @throws Exception    caso ocorra um erro na comunicacao da rede
     */
    private long createScenario( Scenario scenario ) throws Exception
    {
        ScenarioTopology topologyCreator = new ScenarioTopology( scenario );
        
        // TODO: avalia a dependabilidade quando os nos ainda não foram avaliados
        Topology result = topologyCreator.createManyTopologies();
        
        String scenarioPath = action.resultPath + "Scenario_" + scenario.getId() + "/";
        String topologyPath = scenarioPath + "topology.wsn";
        
        File f = new File( action.projectPath + scenarioPath );
        f.mkdirs();
        f = null;
        
        WsnFile.save( action.projectPath + topologyPath , result );    // save topology
        ScenarioFile.save( scenarioPath + "scenario.xml" , scenario ); // save scenario
        
        return createSubEvaluationForNetwork( scenarioPath , topologyPath );
    }
    
    /**
     * Cria e envia o EvaluationConf de um cenario
     * 
     * @param result        diretorio principal para salvar os resultados
     * @param path          caminho para encontrar o arquivo da topologia
     * @throws Exception 
     */
    private long createSubEvaluationForNetwork( String result , String path )
    {
        // ---------------- SET
        String parent = action.evaluateId + "";
        String model  = path.replace( ".wsn" , ".cpn" );
        
        // ---------------- CREATE EVALUATION
        EvaluationConf subConf = new EvaluationConf();
        subConf.setUserId( action.userId               );
        subConf.setCode  ( EvaluationConf.CODE_CREATED );
        
        subConf.getConfiguration().put( "network_evaluate"   , "true" );
        subConf.getConfiguration().put( "network_model_name" , model  );
        subConf.getConfiguration().put( "network_path"       , path   );
        
        // MORE CONFIGURATION!
        subConf.getConfiguration().put( "parent_id"          , parent );
        subConf.getConfiguration().put( "scenarios_path"     , result );
        subConf.getConfiguration().put( "sensibility_path"       , action.filePath   );
        subConf.getConfiguration().put( "sensibility_result_path", action.resultPath );
        
        if( action.reliability )
        {
            subConf.getConfiguration().put( "network_reliability" , "true" );
        }
        
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
            System.err.println( "[SENSIBILITY-NET][ERROR] " + err.getMessage() );
            action.resource.getAddedResendRunnable().add( subConf );
        }
        
        return subConf.getEvaluateId();
    }
    
    /*
---------------------------------- SENSIBILITY
/Blink/sensibility.xml
/Blink/sensibility_results/${time}/
/Blink/sensibility_results/${time}/Scenario_${number}/
/Blink/sensibility_results/${time}/Scenario_${number}/Step_${number}.wsn
    */
}
