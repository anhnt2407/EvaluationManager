package br.cin.ufpe.evaluationManager.resend;

import br.cin.ufpe.evaluationManager.Resource;
import java.util.Properties;

/**
 *
 * @author avld
 */
public class ModelledResendRunnable extends ResendRunnable<Properties>
{
    
    public ModelledResendRunnable( Resource resource )
    {
        super( resource );
    }

    @Override
    public void resend( Properties obj )
    {
        getResource().getModelledAction().action( obj );
    }
    
}
