package br.cin.ufpe.evaluationManager.remote;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author avld
 */
public class EvaluationProtocol implements Serializable
{
    private int id;
    private String operation;
    private List<Object> params;
    
    public EvaluationProtocol()
    {
        params = new LinkedList<>();
    }

    public int getId()
    {
        return id;
    }

    public void setId( int id )
    {
        this.id = id;
    }

    public String getOperation()
    {
        return operation;
    }

    public void setOperation( String operation )
    {
        this.operation = operation;
    }

    public List<Object> getParams()
    {
        return params;
    }

    public void setParams( List<Object> params )
    {
        this.params = params;
    }
    
    public void addParam( Object obj )
    {
        this.params.add( obj );
    }
    
}
