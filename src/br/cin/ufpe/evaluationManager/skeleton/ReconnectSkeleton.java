package br.cin.ufpe.evaluationManager.skeleton;

import br.cin.ufpe.evaluationManager.remote.EvaluationProtocol;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author avld
 */
public abstract class ReconnectSkeleton extends Skeleton
{
    private ObjectOutputStream output ;
    private ReconnectEvent     event  ;
    
    public ReconnectSkeleton( Socket socket )
    {
        super( socket );
        
        event = new DefaultReconnectEvent();
        setIpAndPort();
    }

    public ReconnectEvent getEvent()
    {
        return event;
    }

    public void setEvent( ReconnectEvent event )
    {
        String ip = this.event.getIp();
        int port  = this.event.getPort();
        int type  = this.event.getType();
        
        this.event = event;
        
        if( event == null )
        {
            this.event = new DefaultReconnectEvent();
        }
        
        this.event.setIp  ( ip   );
        this.event.setPort( port );
        this.event.setType( type );
    }

    @Override
    public void setSocket( Socket socket )
    {
        super.setSocket( socket );
        setIpAndPort();
    }
    
    private void setIpAndPort()
    {
        String remote_ip = getSocket().getRemoteSocketAddress().toString();
        remote_ip = remote_ip.substring( 1 );

        String part[] = remote_ip.split( ":" );
        String ip     = part[0];
        int    port   = Integer.parseInt( part[1] );
        
        event.setIp  ( ip );
        event.setPort( port );
    }
    
    public void connected() throws Exception
    {
        connected( event.getType() );
    }
    
    public void connected( int type ) throws Exception
    {
        event.setType( type );
        output = new ObjectOutputStream( getSocket().getOutputStream() );
        
        EvaluationProtocol p = new EvaluationProtocol();
        p.setId( 1 );
        p.setOperation( "connected" );
        p.addParam( type );
        
        synchronized( output )
        {
            output.writeObject( p );
            output.flush();
            output.reset();
        }
    }
    
    public ObjectOutputStream getOutput()
    {
        return output;
    }
    
    @Override
    public void desconnected()
    {
        event.reconnect();
    }
    
}
