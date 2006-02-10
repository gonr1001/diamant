package dInterface.dAffectation;

import dConstants.DConst;
import dInterface.Command;
import dInterface.DApplication;
import dInternal.dOptimization.RoomAssignmentAlgo;
import eLib.exit.dialog.InformationDlg;

/**
 * @author Yannick S
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class RoomAffectCmd implements Command {
	/**
	 * 
	 */
	public RoomAffectCmd() {
		super();
	}
	
	public void execute(DApplication dApplic) {
		new RoomAssignmentAlgo(dApplic.getDModel());
		new InformationDlg(dApplic.getJFrame(), DConst.ROOM_ASSIGN_MESSAGE);
	}
}
