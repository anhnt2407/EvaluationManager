package br.cin.ufpe.evaluationManager.service;

import br.cin.ufpe.evaluationManager.model.EvaluationConf;
import br.cin.ufpe.evaluationManager.model.EvaluationStatus;

/**
 *
 * @author avld
 */
public interface EditorService extends ClientService
{
    public void created    ( EvaluationConf conf );
    public void finished   ( EvaluationConf conf );
    public void inProgress ( EvaluationConf conf , EvaluationStatus status );
}
