package org.tictac.mouseTrap.dInterface;

import javax.swing.JFileChooser;


public class FileOpenDlg  { 
	private MyApplication frame;
	public FileOpenDlg (MyApplication fr){
		frame=fr;
	}

	public String getFile() {
		String file="";
		
		JFileChooser fc = new JFileChooser("");
		fc.setMultiSelectionEnabled( false );
		int returnVal = fc.showOpenDialog(frame.getDesk());
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fc.getSelectedFile().getAbsolutePath();
			if ( !file.endsWith(".txt") )
			file = file.concat(".txt");
			System.out.println("mCurrentFile"); 			   
		}
		return file;
	}	
}

