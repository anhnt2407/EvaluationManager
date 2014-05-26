package br.cin.ufpe.evaluationManager.resend;

import br.cin.ufpe.evaluationManager.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author avld
 */
public abstract class ResendRunnable<T> implements Runnable
{
    private List<T> list;
    private Resource resource;
    
    public ResendRunnable( Resource r )
    {
        this.resource = r;
        this.list    = new ArrayList<>();
    }
    
    public void add( T obj )
    {
        list.add( obj );
    }

    public Resource getResource()
    {
        return resource;
    }
    
    @Override
    public void run()
    {
        for( T obj : list )
        {
            list.remove( obj );
            resend( obj );
        }
    }
    
    public abstract void resend( T obj );
}
