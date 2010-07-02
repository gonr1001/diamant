/*
 * Created on 16 juin 2005
 *
 */
package dInterface.selectiveSchedule.dialog;

/**
 * Exception lancée si un problème au niveau des fichiers survient
 * lors d'une manipulation de la persistance
 * 
 * @author Pascal
 *
 */
@SuppressWarnings("serial")
public class SelectiveScheduleFileIOException extends Exception {

    /**
     * 
     */
    public SelectiveScheduleFileIOException() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     */
    public SelectiveScheduleFileIOException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param cause
     */
    public SelectiveScheduleFileIOException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     * @param cause
     */
    public SelectiveScheduleFileIOException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

}
