package dInterface.dData;
/**
 *
 * Title: DefFileToImportDlg $Revision: 1.13 $  $Date: 2007-11-30 21:40:12 $
 * Description: DefFileToImportDlg is created by DefFileToImportCmd
 *
 *
 * Copyright (c) 2001 by rgr.
 * All rights reserved.
 *
 *
 * This software is the confidential and proprietary information
 * of rgr. ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with rgr.
 *
 * @version $Revision: 1.13 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dConstants.DConst;
import dInterface.DApplication;
import dResources.DFileFilter;
import eLib.exit.dialog.DxExceptionDlg;
import eLib.exit.dialog.InformationDlg;
import eLib.exit.txt.ByteOutputFile;


/**
 *
 * DefFileToImportDlg is a class used to show a dialog
 *
 */

public class DefFilesToImportDlg extends JDialog
implements ActionListener{
	
	private final String CR_LF = "\r\n";
	
	private JTextField _tfActivities;
	private JButton _butActivities;
	private JTextField _tfStudents;
	private JButton _butStudents;
	private JTextField _tfInstructors;
	private JButton _butInstructors;
	private JTextField _tfRooms;
	private JButton _butRooms;
	
	
	/**
	 * the constructor will displays the dialog
	 *
	 * @param DApplication dApplic gives the parent of the dialog
	 * @param str       the title of the window dialog
	 * @since           JDK1.3
	 */
	DApplication _dApplic;
	
	public DefFilesToImportDlg(DApplication dApplic) {
		super(dApplic.getJFrame(),DConst.DEF_F_TD);
		_dApplic = dApplic;
		initDlg();
	} // end constructor
	
	
	private void initDlg() {
		// center Panel
		JPanel centerPanel = new JPanel(new GridLayout(4, 0));
		
		// panel Activities
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(), DConst.DEF_F_D1) );
		_tfActivities = new JTextField();
		_tfActivities.setPreferredSize(new Dimension(330,22));
		_butActivities = new JButton(DConst.BUT_BROWSE);
		_butActivities.addActionListener(this);
		panel.add(_tfActivities);
		panel.add(_butActivities);
		
		centerPanel.add(panel, null);
		
		// panel Students
		panel = new JPanel();
		panel.setBorder( BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(), DConst.DEF_F_D2) );
		_tfStudents = new JTextField();
		_tfStudents.setPreferredSize(new Dimension(330,22));
		_butStudents = new JButton(DConst.BUT_BROWSE);
		_butStudents.addActionListener( this );
		panel.add(_tfStudents);
		panel.add(_butStudents);
		centerPanel.add(panel, null);
		
		// panel Teachers
		panel = new JPanel();
		panel.setBorder( BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(), DConst.DEF_F_D3 ) );
		_tfInstructors = new JTextField();
		_tfInstructors.setPreferredSize(new Dimension(330,22));
		_butInstructors = new JButton(DConst.BUT_BROWSE);
		_butInstructors.addActionListener( this );
		panel.add(_tfInstructors);
		panel.add(_butInstructors);
		centerPanel.add(panel, null);
		
		// panel Rooms
		panel = new JPanel();
		panel.setBorder( BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(), DConst.DEF_F_D4) );
		_tfRooms = new JTextField();
		_tfRooms.setPreferredSize(new Dimension(330,22));
		_butRooms = new JButton(DConst.BUT_BROWSE);
		_butRooms.addActionListener( this );
		panel.add(_tfRooms);
		panel.add(_butRooms);
		centerPanel.add(panel, null);
		
		this.getContentPane().add(centerPanel, BorderLayout.CENTER);
		
		//bootom Panel
		JPanel butPanel = new JPanel();
		JButton butOk = new JButton(DConst.BUT_OK );
		butOk.addActionListener( this );
		butPanel.add(butOk, null);
		JButton butCancel = new JButton(DConst.BUT_CANCEL );
		butCancel.addActionListener( this );
		butPanel.add(butCancel, null);
		getContentPane().add(butPanel, BorderLayout.SOUTH);
		
		pack();
		setLocationRelativeTo(_dApplic.getJFrame());
		setVisible(true);
	} // end initDlg
	
	
	/**
	 * Responds to action events
	 */
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		if (command.equals(DConst.BUT_OK)) {
			if (_tfActivities.getText().length() == 0 ||
					_tfStudents.getText().length() == 0 ||
					_tfInstructors.getText().length() == 0 ||
					_tfRooms.getText().length() == 0)
				new InformationDlg(_dApplic.getJFrame(), DConst.DEF_F_D5 + DConst.DEF_F_D6);
			else {
				saveInFile();
				dispose();
			} //end (_tfActivities.getText().length() == 0 || ...
			
		} else if (command.equals(DConst.BUT_CANCEL )) {
			dispose();
		} else if (command.equals(DConst.BUT_BROWSE )) { // Browse
			JFileChooser fc = new JFileChooser(_dApplic.getCurrentDir());
			fc.setMultiSelectionEnabled( false );
			fc.setFileFilter( new DFileFilter( new String[] {DConst.SIG, DConst.TXT},
					DConst.SIG_FILE ) );
			// Display the file chooser in a dialog			
			String message;
			if (event.getSource() == _butActivities )
				message = DConst.DEF_F_D1;
			else if (event.getSource() == _butStudents )
				message = DConst.DEF_F_D2;
			else if (event.getSource() == _butInstructors )
				message = DConst.DEF_F_D3;
			else
				message = DConst.DEF_F_D4;
			Dimension d = fc.getPreferredSize();
			fc.setPreferredSize(new Dimension((int)d.getWidth()+100, (int)d.getHeight()));
			int returnVal = fc.showDialog(this, message);
			// If the file chooser exited sucessfully,
			// and a file was selected, continue
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				if (event.getSource() == _butActivities)
					_tfActivities.setText(fc.getSelectedFile().getAbsolutePath());
				else if (event.getSource() == _butStudents )
					_tfStudents.setText(fc.getSelectedFile().getAbsolutePath());
				else if (event.getSource() == _butInstructors )
					_tfInstructors.setText(fc.getSelectedFile().getAbsolutePath());
				else if (event.getSource() == _butRooms )
					_tfRooms.setText(fc.getSelectedFile().getAbsolutePath());
				_dApplic.setCurrentDir(fc.getSelectedFile().getAbsolutePath());
			}
		}
	}
	
	private void saveInFile(){//DApplication dApplic, String str) {
		JFileChooser fc = new JFileChooser(_dApplic.getCurrentDir());
		fc.setFileFilter( new DFileFilter ( new String[] {DConst.DIM},
				DConst.DIM_FILE ) );
		// Display the file chooser in a dialog
		int returnVal = fc.showSaveDialog(_dApplic.getJFrame());
		
		// If the file chooser exited sucessfully,
		// and a file was selected, continue
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			// Save the file name
			String fil = fc.getSelectedFile().getAbsolutePath();
			if ( !fil.endsWith(DConst.DOT_DIM) )
				fil = fil.concat(DConst.DOT_DIM);
			saveFile(fil);
			_dApplic.setCurrentDir(fc.getSelectedFile().getAbsolutePath());
			
			new InformationDlg(_dApplic.getJFrame(), DConst.DEF_F_D7 + fil);
		}
	}
	
	private void saveFile(String str) {
		try {
			ByteOutputFile bof = new ByteOutputFile(str);
			String thefiles;
			thefiles = _tfInstructors.getText()+ CR_LF;
			thefiles += _tfRooms.getText()+ CR_LF;
			thefiles += _tfActivities.getText()+ CR_LF;
			thefiles += _tfStudents.getText();
			bof.writeFileFromBytes(thefiles.getBytes());
			bof.close();
		} catch(Exception iofe) {
			new DxExceptionDlg(_dApplic.getJFrame(),iofe.getMessage(),iofe);
			iofe.printStackTrace();
			System.exit(31);
		} // end catch
		
	}
} /* end class DefFileToImportDlg */







