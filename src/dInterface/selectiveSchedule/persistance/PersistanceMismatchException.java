/*
 * Created on 22 juin 2005
 *
 */
package dInterface.selectiveSchedule.persistance;

/**
 * Exception lanc�e si un ou des �l�ment(s) d'un ensemble
 * n'appara�ssent plus dans l'ex�cution courante de Diamant.
 * 
 * @author Pascal
 *
 */
@SuppressWarnings("serial")
public class PersistanceMismatchException extends Exception {

    /**
     * 
     */
    public PersistanceMismatchException() {
        super();
    }

    /**
     * @param message
     */
    public PersistanceMismatchException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param cause
     */
    public PersistanceMismatchException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     * @param cause
     */
    public PersistanceMismatchException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

}
