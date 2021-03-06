package br.cin.ufpe.evaluationManager.skeleton;

/**
 * 
 * 
 * @author avld
 */
public abstract class ReconnectEvent
{
    private String ip ;
    private int port  ;
    private int type  ;
    
    public ReconnectEvent()
    {
        // do nothing
    }

    public String getIp()
    {
        return ip;
    }

    public void setIp( String ip )
    {
        this.ip = ip;
    }

    public int getPort()
    {
        return port;
    }

    public void setPort( int port )
    {
        this.port = port;
    }

    public int getType()
    {
        return type;
    }

    public void setType( int type )
    {
        this.type = type;
    }
    
    public abstract void reconnect();
}
