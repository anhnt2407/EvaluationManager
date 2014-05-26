package br.cin.ufpe.evaluationManager.resend;

import br.cin.ufpe.evaluationManager.Resource;
import java.util.Properties;

/**
 *
 * @author avld
 */
public class EvaluatedResendRunnable extends ResendRunnable<Properties>
{
    
    public EvaluatedResendRunnable( Resource r )
    {
        super( r );
    }

    @Override
    public void resend( Properties properties )
    {
        getResource().getEvaluatedAction().action( properties );
    }
    
}
