package br.cin.ufpe.evaluationManager.skeleton;

import br.cin.ufpe.evaluationManager.remote.EvaluationProtocol;
import br.cin.ufpe.evaluationManager.service.EvaluatorService;
import java.net.Socket;

/**
 *
 * @author avld
 */
public class EvaluatorSkeleton extends Skeleton
{
    private EvaluatorService service;
    
    public EvaluatorSkeleton( Socket socket , EvaluatorService service ) throws Exception
    {
        super( socket );
        this.service = service;
    }
    
    @Override
    public void process( EvaluationProtocol p ) throws Exception
    {
        if( "application".equalsIgnoreCase( p.getOperation() ) )
        {
            long   id    = (Long) p.getParams().get( 0 );
            String model = (String) p.getParams().get( 1 );
            
            service.application( id , model );
        }
        else if( "network".equalsIgnoreCase( p.getOperation() ) )
        {
            long   id    = (Long) p.getParams().get( 0 );
            String model = (String) p.getParams().get( 1 );
            
            service.network( id , model );
        }
    }
    
}
