package br.cin.ufpe.evaluationManager.resend;

import br.cin.ufpe.evaluationManager.client.EditorClient;
import br.cin.ufpe.evaluationManager.model.EvaluationConf;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author avld
 */
public class EditorResendRunnable implements Runnable
{
    public static final int TYPE_CREATED     = 0;
    public static final int TYPE_FINISHED    = 1;
    public static final int TYPE_IN_PROGRESS = 2;
    
    private Map<EvaluationConf,Integer> map;
    private EditorClient client;
    
    public EditorResendRunnable( EditorClient client )
    {
        this.client = client;
    }

    public void add( EvaluationConf obj , int type )
    {
        map.put( obj , type );
    }

    @Override
    public void run()
    {
        for( Entry<EvaluationConf,Integer> entry : map.entrySet() )
        {
            map.remove( entry );
            
            switch( entry.getValue() )
            {
                case TYPE_CREATED  : client.created( entry.getKey() ); break ;
                case TYPE_FINISHED : client.finished( entry.getKey() ); break ;
            }
        }
    }
    
}
