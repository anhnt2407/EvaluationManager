package br.cin.ufpe.evaluationManager.model;

import java.io.Serializable;

/**
 *
 * @author avld
 */
public class ApplicationRequest implements Serializable
{
    private long evaluateId;        // evaluate ID
    private String path;            // application path
    private String name;            // model name
    private String function;        // model a function
    
    public ApplicationRequest()
    {
        // do nothing
    }
    
    public ApplicationRequest( EvaluationConf conf )
    {
        this.evaluateId = conf.getEvaluateId();
        this.path       = conf.getConfiguration().get( "application_path"       );
        this.name       = conf.getConfiguration().get( "application_model_name" );
        this.function   = conf.getConfiguration().get( "application_function"   );
    }

    public long getEvaluateId()
    {
        return evaluateId;
    }

    public void setEvaluateId( long evaluateId )
    {
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

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getFunction()
    {
        return function;
    }

    public void setFunction( String function )
    {
        this.function = function;
    }
    
}
