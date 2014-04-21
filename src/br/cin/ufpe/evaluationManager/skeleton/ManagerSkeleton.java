package br.cin.ufpe.evaluationManager.skeleton;

import br.cin.ufpe.evaluationManager.EvaluationManager;
import br.cin.ufpe.evaluationManager.model.EvaluationConf;
import br.cin.ufpe.evaluationManager.remote.EvaluationProtocol;
import java.net.Socket;

/**
 *
 * @author avld
 */
public class ManagerSkeleton extends Skeleton
{
    private EvaluationManager service;
    private boolean itIsOK;
    private int type;
    
    public ManagerSkeleton( Socket socket ) throws Exception
    {
        super( socket );
        
        this.service = EvaluationManager.getInstance();
        this.itIsOK  = false;
    }
    
    @Override
    public void process( EvaluationProtocol p ) throws Exception
    {
        if( "connected".equalsIgnoreCase( p.getOperation() ) )
        {
            type   = (Integer) p.getParams().get( 0 );

            itIsOK = service.connected( type , getSocket() );
        }
        else if( !itIsOK )
        {
            return ;
        }
        else if( "add".equalsIgnoreCase( p.getOperation() ) )
        {
            EvaluationConf conf = (EvaluationConf) p.getParams().get( 0 );
            
            service.add( conf );
        }
        else if( "modelled".equalsIgnoreCase( p.getOperation() ) )
        {
            long id   = (Long)    p.getParams().get( 0 );
            int  code = (Integer) p.getParams().get( 1 );
            
            service.modelled( id , code );
        }
        else if( "evaluated".equalsIgnoreCase( p.getOperation() ) )
        {
            long id   = (Long)    p.getParams().get( 0 );
            int  code = (Integer) p.getParams().get( 1 );
            
            service.evaluated( id , code );
        }
    }
    
    @Override
    public void desconnected()
    {
        service.desconnected( type , getSocket() );
    }
}
