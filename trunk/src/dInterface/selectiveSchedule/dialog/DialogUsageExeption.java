/*
 * Created on 16 juin 2005
 *
 */
package dInterface.selectiveSchedule.dialog;

/**
 * Exception lancée lorsque que l'utilisateur effectue une manoeuvre
 * interdite dans un dialogue.
 * 
 * @author Pascal
 *
 */
public class DialogUsageExeption extends Exception {

    /**
     * 
     */
    public DialogUsageExeption() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     */
    public DialogUsageExeption(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param cause
     */
    public DialogUsageExeption(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     * @param cause
     */
    public DialogUsageExeption(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

}
