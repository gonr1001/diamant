package org.tictac.mouseTrap.dInterface;

import javax.swing.*;

public class CmdMenuItem extends JMenuItem implements CommandHolder {
	protected Command menuCommand;
	protected JFrame frame;

	public CmdMenuItem( String name, JFrame fr) {
		super( name);
		frame = fr;
  	}

  	public void setCommand(Command comd) {
		menuCommand = comd;
  	}
	
  	public Command getCommand() {
		return menuCommand;
	}
}


