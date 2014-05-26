package br.cin.ufpe.evaluationManager.skeleton;

import br.cin.ufpe.evaluationManager.model.EvaluationConf;
import br.cin.ufpe.evaluationManager.model.EvaluationStatus;
import br.cin.ufpe.evaluationManager.remote.EvaluationProtocol;
import br.cin.ufpe.evaluationManager.service.EditorService;
import java.net.Socket;

/**
 *
 * @author avld
 */
public class EditorSkeleton extends ReconnectSkeleton
{
    private EditorService service;
    
    public EditorSkeleton( Socket socket , EditorService service )
    {
        super( socket );
        this.service = service;
    }
    
    @Override
    public void process( EvaluationProtocol p ) throws Exception
    {
        if( "finished".equalsIgnoreCase( p.getOperation() ) )
        {
            EvaluationConf conf = (EvaluationConf) p.getParams().get( 0 );
            service.finished( conf );
        }
        else if( "inProgress".equalsIgnoreCase( p.getOperation() ) )
        {
            EvaluationConf conf     = (EvaluationConf)   p.getParams().get( 0 );
            EvaluationStatus status = (EvaluationStatus) p.getParams().get( 1 );
            
            service.inProgress( conf , status );
        }
    }

}
