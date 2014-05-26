package br.cin.ufpe.evaluationManager.model;

/**
 *
 * @author avld
 */
public class NetworkRequest
{
    private long evaluateId;
    private long userId;
    
    private String path;
    private String name;
    private boolean reliability;
    
    public NetworkRequest()
    {
        reliability = true;
        name        = "wsn_model.cpn";
    }

    public NetworkRequest( EvaluationConf conf )
    {
        this.evaluateId  = conf.getEvaluateId();
        
        String reliabilityStr = conf.getConfiguration().get( "network_reliability" );
        this.reliability      = Boolean.parseBoolean( reliabilityStr );
        
        this.path        = conf.getConfiguration().get( "network_file_name"  );
        this.name        = conf.getConfiguration().get( "network_model_name" );
    }
    
    public long getEvaluateId() {
        return evaluateId;
    }

    public void setEvaluateId(long evaluateId) {
        this.evaluateId = evaluateId;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath( String path )
    {
        this.path = path;
    }

    public boolean isReliability()
    {
        return reliability;
    }

    public void setReliability( boolean reliability )
    {
        this.reliability = reliability;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public long getUserId()
    {
        return userId;
    }

    public void setUserId( long userId )
    {
        this.userId = userId;
    }
    
}
