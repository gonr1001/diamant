package dInterface.dData;
/**
 *
 * Title: DefFileToImportDlg $Revision: 1.1 $  $Date: 2003-05-20 16:23:15 $
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
 * @version $Revision: 1.1 $
 * @author  $Author: alexj $
 * @since JDK1.3
 */

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.JDialog;
import javax.swing.JFrame;
import java.awt.GridLayout;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.iLib.gIO.ByteOutputFile;
import com.iLib.gException.IOFileException;
import com.iLib.gDialog.FatalProblemDlg;
import dInterface.DApplication;

import dResources.DConst;
import dResources.DFileFilter;


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

   public DefFilesToImportDlg(DApplication dApplic, String str) {
     super(dApplic.getJFrame(),str);
     _dApplic = dApplic;
     initDlg();
   } // end constructor

/*
   public int showDlg(){
     pack();
     setLocationRelativeTo(_dApplic.getJFrame());
     setVisible(true);
     return 1;//new String();
  }
*/
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
        JOptionPane.showMessageDialog(this,
                     DConst.DEF_F_D5 + DConst.DEF_F_D6,
                     DConst.PROBLEM,
                     JOptionPane.WARNING_MESSAGE);
      else {
        saveInFile();
        dispose();
      } //end (_tfActivities.getText().length() == 0 || ...

    } else if (command.equals(DConst.BUT_CANCEL )) {
      dispose();
    } else if (command.equals(DConst.BUT_BROWSE )) { // Browse
      JFileChooser fc = new JFileChooser(_dApplic.getCurrentDir());
      fc.setMultiSelectionEnabled( false );
      fc.setFileFilter( new DFileFilter( new String[] {"sig", "txt"},
          "Data Files (*.sig)" ) );
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
      _dApplic.setCurrentDir(fc.getSelectedFile().getPath());
    }
  }
  }

  private void saveInFile(){//DApplication dApplic, String str) {
    JFileChooser fc = new JFileChooser(_dApplic.getCurrentDir());//_projectPath);
    fc.setFileFilter( new DFileFilter ( new String[] {"dim"},
                "Diamant file (*.dim)" ) );
    // Display the file chooser in a dialog
    int returnVal = fc.showSaveDialog(_dApplic.getJFrame());

    // If the file chooser exited sucessfully,
    // and a file was selected, continue
    if (returnVal == JFileChooser.APPROVE_OPTION) {
    // Save the file name
    String fil = fc.getSelectedFile().getAbsolutePath();
    if ( !fil.endsWith(".dim") )
      fil = fil.concat(".dim");
    saveFile(fil);
    _dApplic.setCurrentDir(fc.getSelectedFile().getPath());
    JOptionPane.showMessageDialog(this, "Sauvegardé dans: "+ fil,
      "Fichier d'importation Auto", JOptionPane.INFORMATION_MESSAGE);
    }
  }

  private void saveFile(String str) {
  try {
    ByteOutputFile bof = new ByteOutputFile(str);
    String thefiles = _tfActivities.getText()+ CR_LF;
    thefiles += _tfStudents.getText()+ CR_LF;
    thefiles += _tfInstructors.getText()+ CR_LF;
    thefiles += _tfRooms.getText();
    bof.writeFile(thefiles.getBytes());
    bof.close();
  } catch(IOFileException iofe) {
     new FatalProblemDlg(_dApplic.getJFrame(),
              iofe + "\n I was in DefFilesToImportDlg.saveFile");
              System.out.println(iofe);
              iofe.printStackTrace();
              System.exit(31);
      } // end catch

    }
} /* end class DefFileToImportDlg */







