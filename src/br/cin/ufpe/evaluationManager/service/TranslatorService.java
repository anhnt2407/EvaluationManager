package br.cin.ufpe.evaluationManager.service;

import br.cin.ufpe.evaluationManager.model.EvaluationConf;

/**
 *
 * @author avld
 */
public interface TranslatorService extends ClientService
{
    public void application( EvaluationConf conf );
    public void network    ( EvaluationConf conf );
}