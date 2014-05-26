package br.cin.ufpe.evaluationManager.service;

import br.cin.ufpe.evaluationManager.model.EvaluationConf;

/**
 * 
 * 
 * @author avld
 */
public interface EvaluatorService extends ClientService
{
    public void application ( EvaluationConf conf );
    public void network     ( EvaluationConf conf );
}
