package dInterface.dAffectation;

import dInterface.Command;
import dInterface.DApplication;
import dInterface.dUtil.ConflictDlg;

/**
 * @author Yannick S
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ConflictOptionCmd implements Command {
	/**
	 * 
	 */
	public ConflictOptionCmd() {
		super();
		
	}
	
	public void execute(DApplication dApplic) {
		new ConflictDlg(dApplic);
	}
}
