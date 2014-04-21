package br.cin.ufpe.evaluationManager.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author avld
 */
public class EvaluationStatus implements Serializable
{
    private long   evaluateId;    //identificador da avaliacao
    private Date   date;          //tempo
    private String status;        //texto do status
    
    public EvaluationStatus()
    {
        
    }
    
    public EvaluationStatus( long id , String status )
    {
        this.evaluateId = id         ;
        this.status     = status     ;
        this.date       = new Date() ;
    }

    public long getEvaluateId() {
        return evaluateId;
    }

    public void setEvaluateId(long evaluateId) {
        this.evaluateId = evaluateId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
