package org.tictac.mouseTrap.dInterface;

public class ToolsModCmd implements Command {
	private MyApplication frame;
	
	public ToolsModCmd (MyApplication  frm){
		frame=frm;		
	}
	public void execute(){
		new ToolsModDlg(frame);
	}
}
