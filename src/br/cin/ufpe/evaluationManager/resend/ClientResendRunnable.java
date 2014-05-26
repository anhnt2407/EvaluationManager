package br.cin.ufpe.evaluationManager.resend;

import br.cin.ufpe.evaluationManager.client.ManagerClient;
import br.cin.ufpe.evaluationManager.model.EvaluationConf;
import br.cin.ufpe.evaluationManager.service.ClientService;
import br.cin.ufpe.evaluationManager.skeleton.ReconnectEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

/**
 *
 * @author avld
 */
public class ClientResendRunnable extends ReconnectEvent implements Runnable
{
    public static final int TYPE_ADD       = 0;
    public static final int TYPE_MODELLED  = 1;
    public static final int TYPE_EVALUATED = 2;
    
    protected ManagerClient        client ;
    private   Map<Object,Integer>  store  ;
    
    private ClientService  service   ;
    private boolean        reconnect ;
    
    public ClientResendRunnable( ManagerClient client )
    {
        this.client    = client ;
        this.store     = new HashMap<>() ;
        this.reconnect = false;
    }
    
    public void add( Object obj , int type )
    {
        store.put( obj , type );
    }

    public ClientService getService()
    {
        return service;
    }
    
    public void setService( ClientService client )
    {
        this.service = client;
    }
    
    // --------------------
    // -------------------- FUNCTION
    // --------------------
    
    @Override
    public void run()
    {
        if( reconnect )
        {
            reconnect();
        }
        else
        {
            resend();
        }
    }

    @Override
    public void reconnect()
    {
        reconnect = true;
        
        try
        {
            System.out.println( "Servidor: " + getIp() + ":" + getPort() );
            
            client.conect( getIp() , getPort() );
            reconnect = false;
        }
        catch( Exception err )
        {
            System.err.println( "[RECONECT][ERROR] " + err.getMessage() );
            reconnect = true;
        }
    }
    
    private void resend()
    {
        for( Entry<Object,Integer> obj : store.entrySet() )
        {
            try
            {
                store.remove( obj );
                
                switch( obj.getValue() )
                {
                    case TYPE_ADD       :
                        client.add       ( (EvaluationConf) obj.getKey() );
                        break ;
                    case TYPE_MODELLED  :
                        client.modelled  ( (Properties) obj.getKey() ); 
                        break ;
                    case TYPE_EVALUATED :
                        client.evaluated ( (Properties) obj.getKey() );
                        break ;
                }
            }
            catch( Exception err )
            {
                System.err.println( "[RESEND][ERROR] " + err.getMessage() );
            }
        }   
    }
    
}
