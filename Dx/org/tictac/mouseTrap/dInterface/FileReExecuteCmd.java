package org.tictac.mouseTrap.dInterface;

import org.tictac.mouseTrap.dModel.*;

public class FileReExecuteCmd implements Command{
	private MyApplication frame;
	private String fileName="";
	
	public FileReExecuteCmd (MyApplication  frm){
		frame=frm;		
	}	
	
	public FileReExecuteCmd (MyApplication  frm, String file){
		frame=frm;		
		fileName=file;	
	}
		
	public void execute(){
		if (fileName.compareTo("")==0){
			FileOpenDlg fo=new FileOpenDlg(frame);
			//String fileName=fo.getFile();
		}
		// rgd: verificar que seleccione un archivo de trace
		if (fileName.compareTo("")!=0){ 
			ExecuteMT reex = new ExecuteMT(fileName);
			reex.execute(); 
		}
	}
		
}
//import javax.swing.*;
//import org.tictac.main.*;
//import org.tictac.main.dInternal.*;

//int result = JOptionPane.showConfirmDialog(null,
//			   "Voulez-vous réexecuter?", 
//			   "Réexecuter ?",
//			   JOptionPane.YES_NO_OPTION);
//if (result == JOptionPane.YES_OPTION) {
