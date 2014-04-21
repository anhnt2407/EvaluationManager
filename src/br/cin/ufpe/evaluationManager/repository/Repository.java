package br.cin.ufpe.evaluationManager.repository;

import br.cin.ufpe.evaluationManager.model.EvaluationConf;
import br.cin.ufpe.evaluationManager.model.EvaluationStatus;
import java.util.List;

/**
 * 
 * 
 * @author avld
 */
public interface Repository
{
    public long add( EvaluationConf conf   );
    public void add( EvaluationStatus conf );
    
    public void setCode( long id , int code );
    
    public EvaluationConf get( long id );
    
    public List<EvaluationConf> list( long userId );
    public List<EvaluationConf> list( long userId , long projectId );
}