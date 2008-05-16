/*
 * Created on 16 juin 2005
 *
 */
package dInterface.selectiveSchedule.dialog;

/**
 * Exception lancée pour indiquer que l'on tente de créer un ensemble
 * vide (ce qui n'est pas permis)
 * 
 * @author Pascal
 *
 */
public class EmptySetException extends Exception {

    /**
     * 
     */
    public EmptySetException() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     */
    public EmptySetException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param cause
     */
    public EmptySetException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     * @param cause
     */
    public EmptySetException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

}
