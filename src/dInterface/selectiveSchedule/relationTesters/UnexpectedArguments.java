/*
 * Created on 29 mai 2005
 *
 */
package dInterface.selectiveSchedule.relationTesters;

/**
 * Exception lancée si on ne passe pas les bons types d'arguments à
 * une implémentation de RelationTester.
 *
 * XXXX GS: Peut-être qu'il y a redondance, car la vérification est
 * déjà effectuée par le SelectiveScheduleManager
 * 
 * @author Pascal
 *
 */
@SuppressWarnings("serial")
public class UnexpectedArguments extends Exception {

    /**
     * 
     */
    public UnexpectedArguments() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     */
    public UnexpectedArguments(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param cause
     */
    public UnexpectedArguments(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     * @param cause
     */
    public UnexpectedArguments(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }
}
