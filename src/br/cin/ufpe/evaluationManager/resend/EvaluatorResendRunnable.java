package br.cin.ufpe.evaluationManager.resend;

import br.cin.ufpe.evaluationManager.client.EvaluatorClient;
import br.cin.ufpe.evaluationManager.model.EvaluationConf;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author avld
 */
public class EvaluatorResendRunnable implements Runnable
{
    public static final int TYPE_APP = 0;
    public static final int TYPE_NET = 1;
    
    private Map<EvaluationConf,Integer> map;
    private EvaluatorClient client;
    
    public EvaluatorResendRunnable( EvaluatorClient client )
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
                case TYPE_APP : client.application(entry.getKey() ); break ;
                case TYPE_NET : client.network( entry.getKey() ); break ;
            }
        }
    }
    
}
