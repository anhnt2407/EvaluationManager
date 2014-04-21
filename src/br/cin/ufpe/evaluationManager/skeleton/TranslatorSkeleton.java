package br.cin.ufpe.evaluationManager.skeleton;

import br.cin.ufpe.evaluationManager.model.ApplicationRequest;
import br.cin.ufpe.evaluationManager.model.NetworkRequest;
import br.cin.ufpe.evaluationManager.remote.EvaluationProtocol;
import br.cin.ufpe.evaluationManager.service.TranslatorService;
import java.net.Socket;

/**
 *
 * @author avld
 */
public class TranslatorSkeleton extends Skeleton
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
            ApplicationRequest req = (ApplicationRequest) p.getParams().get( 0 );
            service.application( req );
        }
        else if( "network".equalsIgnoreCase( p.getOperation() ) )
        {
            NetworkRequest req = (NetworkRequest) p.getParams().get( 0 );
            service.network( req );
        }
    }
    
}
