package br.cin.ufpe.evaluationManager.repository;

import br.cin.ufpe.evaluationManager.model.EvaluationConf;
import br.cin.ufpe.evaluationManager.model.EvaluationStatus;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author avld
 */
public class CacheRepository implements Repository
{
    private Map<Long,EvaluationConf> cache;
    private long counter;
    
    public CacheRepository()
    {
        cache   = new HashMap<>();
        counter = 1;
    }
    
    @Override
    public long add( EvaluationConf conf )
    {
        conf.setEvaluateId( counter++ );
        cache.put( conf.getEvaluateId() , conf );
        
        return conf.getEvaluateId();
    }

    @Override
    public void add( EvaluationStatus status )
    {
        get( status.getEvaluateId() ).getStatus().add( status );
    }

    @Override
    public void setCode( long id , int code )
    {
        get( id ).setCode( code );
    }

    @Override
    public EvaluationConf get( long id )
    {
        return cache.get( id );
    }

    @Override
    public List<EvaluationConf> list( long userId )
    {
        List<EvaluationConf> list = new ArrayList<>();
        for( EvaluationConf conf : cache.values() )
        {
            if( conf.getUserId() == userId )
            {
                list.add( conf );
            }
        }
        
        return list;
    }

    @Override
    public List<EvaluationConf> list( long userId , long projectId )
    {
        List<EvaluationConf> list = new ArrayList<>();
        for( EvaluationConf conf : cache.values() )
        {
            if( conf.getUserId() == userId 
                    && conf.getProjectId() == projectId )
            {
                list.add( conf );
            }
        }
        
        return list;
    }
    
}
