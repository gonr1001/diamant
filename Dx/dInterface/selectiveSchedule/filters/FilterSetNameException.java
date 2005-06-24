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
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     */
    public FilterSetNameException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param cause
     */
    public FilterSetNameException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     * @param cause
     */
    public FilterSetNameException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

}
