package org.tictac.mouseTrap.dInterface;

import javax.swing.*;


public class FileExitCmd implements Command{
	private MyApplication frame;
	
	public FileExitCmd (){
		
	}
	
	public void execute(){
		int result = JOptionPane.showConfirmDialog(null,
					   "Voulez-vous sortir?", 
					   "Sortir Réexecute ?",
					   JOptionPane.YES_NO_OPTION);
		if (result == JOptionPane.YES_OPTION) {
			System.exit(0); 
		}
	}									
}

