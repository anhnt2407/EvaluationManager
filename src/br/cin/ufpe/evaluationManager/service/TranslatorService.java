package br.cin.ufpe.evaluationManager.service;

import br.cin.ufpe.evaluationManager.model.ApplicationRequest;
import br.cin.ufpe.evaluationManager.model.NetworkRequest;

/**
 *
 * @author avld
 */
public interface TranslatorService
{
    public void application( ApplicationRequest conf ) throws Exception;
    public void network( NetworkRequest conf ) throws Exception;
}