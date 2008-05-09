/*
 * Created on 16 juin 2005
 *
 */
package dInterface.selectiveSchedule.filters;

/**
 * Exception lancée si on tente d'utiliser le même nom plus d'une
 * fois.
 * 
 * @author Pascal
 *
 */
public class FilterSetNameException extends Exception {

    /**
     * 
     */
    public FilterSetNameException() {
        super();
    }

    /**
     * @param message
     */
    public FilterSetNameException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public FilterSetNameException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public FilterSetNameException(String message, Throwable cause) {
        super(message, cause);
    }

}
