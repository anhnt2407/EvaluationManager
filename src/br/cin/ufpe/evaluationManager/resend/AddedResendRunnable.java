package br.cin.ufpe.evaluationManager.resend;

import br.cin.ufpe.evaluationManager.Resource;
import br.cin.ufpe.evaluationManager.model.EvaluationConf;

/**
 *
 * @author avld
 */
public class AddedResendRunnable extends ResendRunnable<EvaluationConf>
{
    
    public AddedResendRunnable( Resource r )
    {
        super( r );
    }

    @Override
    public void resend( EvaluationConf conf )
    {
        try
        {
            getResource().getAddAction().sendToTranslator( conf );
        }
        catch( Exception err )
        {
            getResource().getAddedResendRunnable().add( conf );
        }
    }
    
}
