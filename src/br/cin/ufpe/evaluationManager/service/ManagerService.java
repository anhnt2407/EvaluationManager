package br.cin.ufpe.evaluationManager.service;

import br.cin.ufpe.evaluationManager.model.EvaluationConf;

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
    
    public void add      ( EvaluationConf conf )         throws Exception;
    public void modelled ( long evaluateId , int type ) throws Exception;
    public void evaluated( long evaluateId , int type ) throws Exception;
//    public EvaluateConf       get ( int evaluateId );
//    public List<EvaluateConf> list( int userId , int projectId );
//    public List<EvaluateConf> list( int userId );
}
