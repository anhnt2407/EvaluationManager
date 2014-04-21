package br.cin.ufpe.evaluationManager.service;

import br.cin.ufpe.evaluationManager.model.EvaluationConf;
import br.cin.ufpe.evaluationManager.model.EvaluationStatus;

/**
 *
 * @author avld
 */
public interface EditorService
{
    public void created    ( EvaluationConf conf ) throws Exception;
    public void finished   ( EvaluationConf conf ) throws Exception;
    public void inProgress ( EvaluationConf conf , EvaluationStatus status ) throws Exception;
}
