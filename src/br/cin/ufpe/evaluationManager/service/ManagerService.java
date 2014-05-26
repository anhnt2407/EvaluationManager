package br.cin.ufpe.evaluationManager.service;

import br.cin.ufpe.evaluationManager.model.EvaluationConf;
import java.util.Properties;

/**
 * 
 * 
 * @author avld
 */
public interface ManagerService
{
    public static final int TYPE_EDITOR     = 0;
    public static final int TYPE_TRANSLATOR = 1;
    public static final int TYPE_EVALUATOR  = 2;
    
    public static final int TYPE_MODELLED_APP  = 0;
    public static final int TYPE_MODELLED_NET  = 1;
    
    public static final int TYPE_EVALUATED_APP  = 0;
    public static final int TYPE_EVALUATED_NET  = 1;
    
    // ------------------- //
    
    public void add      ( EvaluationConf conf )  ;
    public void modelled ( Properties properties );
    public void evaluated( Properties properties );
//    public EvaluateConf       get ( int evaluateId );
//    public List<EvaluateConf> list( int userId , int projectId );
//    public List<EvaluateConf> list( int userId );
}
