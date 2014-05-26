package br.cin.ufpe.evaluationManager.skeleton;

import br.cin.ufpe.evaluationManager.model.EvaluationConf;
import br.cin.ufpe.evaluationManager.remote.EvaluationProtocol;
import br.cin.ufpe.evaluationManager.service.TranslatorService;
import java.net.Socket;

/**
 *
 * @author avld
 */
public class TranslatorSkeleton extends ReconnectSkeleton
{
    private TranslatorService service;
    
    public TranslatorSkeleton( Socket socket , TranslatorService service ) throws Exception
    {
        super( socket );
        this.service = service;
    }
    
    @Override
    public void process( EvaluationProtocol p ) throws Exception
    {
        if( "application".equalsIgnoreCase( p.getOperation() ) )
        {
            EvaluationConf conf = (EvaluationConf) p.getParams().get( 0 );
            service.application( conf );
        }
        else if( "network".equalsIgnoreCase( p.getOperation() ) )
        {
            EvaluationConf conf = (EvaluationConf) p.getParams().get( 0 );
            service.network( conf );
        }
    }

}
