package dInterface.dAffectation;

import dConstants.DConst;
import dInterface.Command;
import dInterface.DApplication;

/**
 * @author Yannick S
 *
 * Cette classe est la commande qui permet d'executer le dialogue
 * EventsDlg
 */
public class EventAffectCmd implements Command {
	/**
	 * 
	 */
	public EventAffectCmd() {
		super();
		// TODO Auto-generated constructor stub
	}
	/* (non-Javadoc)
	 * @see dInterface.Command#execute(dInterface.DApplication)
	 */
	public void execute(DApplication dApplic) {
		new EventsDlg(dApplic,DConst.EVENTS_DLG_TITLE, true);
	}
}
