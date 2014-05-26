package br.cin.ufpe.evaluationManager;

import br.cin.ufpe.evaluationManager.client.EditorClient;
import br.cin.ufpe.evaluationManager.model.EvaluationConf;
import br.cin.ufpe.sensibility.file.SensibilityFile;
import br.cin.ufpe.sensibility.model.Sensibility;
import br.cin.ufpe.sensibility.validate.SensibilityValidate;
import java.io.File;

/**
 *
 * @author avld
 */
public class SensibilityAction
{
    protected Resource resource;
    
    protected long evaluateId;
    protected long userId;
    
    protected String projectPath;
    protected String resultPath;
    protected String filePath;
    protected boolean reliability;
    protected Sensibility sensibility;
    
    private SensibilityApplication app;
    private SensibilityNetwork     net;
    
    public SensibilityAction( Resource resource )
    {
        this.resource = resource;
        
        this.app = new SensibilityApplication( this );
        this.net = new SensibilityNetwork( this );
    }
    
    public void add( EvaluationConf conf ) throws Exception
    {
        setPaths( conf );
        
        //verificar se tem aplicacao para avaliar antes da rede
        if( !sensibility.getApplication().isEmpty() )
        {
            app.createdAndSendAllApplication();
        }
        else
        {
            net.createAndSendAllScenarios( conf );
        }
    }
    
    public void evaluated( EvaluationConf conf ) throws Exception
    {
        boolean application = conf.getConfiguration().containsKey( "application_evaluate" );
        String parentIdStr = conf.getConfiguration().get( "parent_id" );
        Long evaluateId    = Long.parseLong( parentIdStr );
        
        // cria um modelo Configuration Layer
        if( application )
        {
            setPaths( conf );
            app.createConfigurationLayer( conf );
        }
        
        // VERIFICA SE TODOS OS SEUS FILHOS FORAM FINALIZADOS
        EvaluationConf parentConf = resource.getRepository().get( evaluateId );
        for( long id : parentConf.getChildren() )
        {
            EvaluationConf childrenConf = resource.getRepository().get( id );
            if( childrenConf.getCode() != EvaluationConf.CODE_EVALUATED_NET )
            {
                return ;
            }
        }
        
        if( application )
        {
            setPaths( parentConf );
            net.createAndSendAllScenarios( parentConf );
        }
        else
        {
            parentConf.setCode( EvaluationConf.CODE_EVALUATED_NET );

            EditorClient client = resource.selectEditor();
            client.finished( parentConf );
        }
    }
    
    private void setPaths( EvaluationConf conf ) throws Exception
    {
        String file_path   = conf.getConfiguration().get( "sensibility_path"        );
        String result_path = conf.getConfiguration().get( "sensibility_result_path" );

        projectPath = EvaluationManager.PROJECT_PATH + "/" + conf.getUserId() + "/";
        filePath    = file_path;
        
        // ------------- SAVE IN ANOTHER PLACE
        File f = new File( projectPath + "/" + result_path );
        
        if( !f.exists() )
        {
            f.mkdirs();
        }
        
        String file_real_path = projectPath + "/" + result_path + "/sensibility.xml";
        f = new File( file_real_path );
        
        if( !f.exists() )
        {
            sensibility = SensibilityFile.open( projectPath + file_path );
            SensibilityFile.save( file_path , sensibility );
        }
        else
        {
            sensibility = SensibilityFile.open( file_real_path );
        }
        
        SensibilityValidate validate = new SensibilityValidate( sensibility );
        validate.validate();
        
        file_real_path = null;
        f = null;
    }
    
}
