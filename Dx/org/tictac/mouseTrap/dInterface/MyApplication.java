package org.tictac.mouseTrap.dInterface;

import java.awt.event.*;
import javax.swing.*;
import java.io.File;

public class MyApplication extends JFrame implements ActionListener{ 
	private static int width = 500; 
	private static int height = 400; 
	private static int x = 200;
	private static int y = 200; 
	private JMenuBar menuBar;
	private JDesktopPane desk; 
	private Object []_params={"",""};
	
  	public MyApplication(String title) {
		super(title);
		this.setBounds(x,y,width, height); 
		desk = new JDesktopPane();
		this.setContentPane(desk);
		menuBar = createMenuBar(); 
		this.setJMenuBar(menuBar);
 	}
//	****************************************************************	 
	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();		
		JMenu file = createFileMenu();
		menuBar.add(file); // Menu File
		JMenu tool = createToolMenu();
		menuBar.add(tool); // Menu Outils
		JMenu test = createTestMenu();
		menuBar.add(test); // Menu Test
		return menuBar; 
	}
//	****************************************************************  
	public void actionPerformed( ActionEvent e) {
		CommandHolder obj = (CommandHolder)e.getSource();
		//System.out.println("action Performed -"+obj.getCommand());
		obj.getCommand().execute();
	}
//	****************************************************************
	public JMenu createFileMenu() { 	
		CmdMenuItem newcommand;	
		//	File menu 
		JMenu menu = new JMenu("Fichier");  
		//	Reexecuter
		newcommand = new CmdMenuItem("Réexecuter",this);
		newcommand.setCommand(new FileReExecuteCmd());
		newcommand.addActionListener(this);
		menu.add(newcommand); 
		//	Sortir 
		newcommand = new CmdMenuItem("Sortir",this); 
		newcommand.setCommand(new FileExitCmd ());
		newcommand.addActionListener(this);
		menu.add(newcommand);
		return menu; 
	}
//	****************************************************************
	public JMenu createToolMenu() {
		CmdMenuItem newcommand; 
		JMenu outils = new JMenu("Outils");
		//	Reexecuter
		newcommand = new CmdMenuItem("Modifications",this);
		newcommand.setCommand(new ToolsModCmd(this));
		newcommand.addActionListener(this);
		outils.add(newcommand);
		
		return outils; 
	} 
//	****************************************************************
	public JMenu createTestMenu() { 	
		CmdMenuItem newcommand;	
		//	File menu 
		JMenu menu = new JMenu("Test");  
		//	Reexecuter
		newcommand = new CmdMenuItem("Réexecuter",this);
		newcommand.setCommand(new FileReExecuteCmd( "."+File.separator+"trace"+File.separator+"trace.log" ));
		newcommand.addActionListener(this);
		menu.add(newcommand); 
		return menu; 
	}
	
	public JFrame getJFrame() {
		return this;
	}
	
	public JDesktopPane getDesk(){
		return desk;
	}
	
	public void setParams(Object[] temp){
		_params=temp;
	}
	
	public Object[] getParams(){
		return _params;
	}
}
