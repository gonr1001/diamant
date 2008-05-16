/*
 * Created on 16 juin 2005
 *
 */
package dInterface.selectiveSchedule.dialog;

/**
 * Exception lancée si une erreure de programmation survient et qu'un
 * dialogue se retrouve dans un état indéterminé.
 * 
 * @author Pascal
 *
 */
public class UnknownDlgStateException extends Exception {

    /**
     * 
     */
    public UnknownDlgStateException() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     */
    public UnknownDlgStateException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param cause
     */
    public UnknownDlgStateException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     * @param cause
     */
    public UnknownDlgStateException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

}
