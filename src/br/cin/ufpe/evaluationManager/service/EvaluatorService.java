package br.cin.ufpe.evaluationManager.service;

/**
 * 
 * 
 * @author avld
 */
public interface EvaluatorService
{
    public void application ( long id , String modelFilePath ) throws Exception;
    public void network     ( long id , String modelFilePath ) throws Exception;
}
