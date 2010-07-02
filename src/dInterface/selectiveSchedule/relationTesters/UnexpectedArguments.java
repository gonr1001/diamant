/*
 * Created on 29 mai 2005
 *
 */
package dInterface.selectiveSchedule.relationTesters;

/**
 * Exception lanc�e si on ne passe pas les bons types d'arguments �
 * une impl�mentation de RelationTester.
 *
 * XXXX GS: Peut-�tre qu'il y a redondance, car la v�rification est
 * d�j� effectu�e par le SelectiveScheduleManager
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
