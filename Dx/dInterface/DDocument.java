/**
 *
 * Title: DDocument $Revision: 1.45 $  $Date: 2003-07-07 17:35:10 $
 * Description: DDocument is a class used to
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
 * @version $Revision: 1.45 $
 * @author  $Author: ysyam $
 * @since JDK1.3
 */
package dInterface;


import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import java.io.*;
import java.awt.Dimension;

import javax.swing.event.*;
import javax.swing.*;
import javax.swing.JDesktopPane;


import dInternal.DModel;
import dInternal.dData.Status;
//import dInternal.TTParameters;
import dInternal.DModelEvent;
import dInternal.DModelListener;
import dInternal.dTimeTable.TTStructure;
import dInternal.dTimeTable.TTStructureListener;
import dInternal.dTimeTable.TTStructureEvent;
import dResources.DConst;
import java.util.StringTokenizer;
import dInterface.dTimeTable.TTPanel;

import dInterface.dTimeTable.CloseCmd;

import com.iLib.gDialog.FatalProblemDlg;
//debug

public class DDocument  implements ActionListener, DModelListener, TTStructureListener{
  private DApplication _dApplic;
  private JInternalFrame _jif;
  private String _documentName;
  private TTPanel _ttPanel;
  private boolean _modified;
  private DModel _dm;
  private JPanel _statusPanel;
  private String _version;
  JLabel _nbModif, _nbBlocs,  _nbCStu, _nbCInstr, _nbCRoom;


  //for a new timetable and a open timetable
  public DDocument(DApplication dApplic,String TTName, String fileName, int type) {
    _dApplic = dApplic;
    _dm = new DModel(_dApplic, fileName, type);
    //if(_dm.getError().length()==0){
      addTTListener(_dm.getTTStructure());
      _dm.getTTStructure().addTTStructureListener(this);
      buidDocument(TTName);
    //}
  } // end constructor DDocument()


  //for new timetable Structure
   public DDocument(DApplication dApplic, String fullPath, int type, boolean onlyStruc) {
     _dApplic = dApplic;
 //    TTStructure ttStruct = new TTStructure();
     // to  be arranged
 //    ttStruct.loadTTStructure(fullPath);
     _dm = new DModel(_dApplic, fullPath, type, onlyStruc);
     _dm.getTTStructure().addTTStructureListener(this);
     buidDocument(fullPath);
     //dApplic.getToolBar().setToolBars(ttStruct);
    // _modified=true;
  } // end constructor DDocument()

  //for open timetable structure
  //-------------------------------------------
  public DDocument(DApplication dApplic, String title, String fileName, boolean partial) {
    _dApplic = dApplic;
    TTStructure ttStruct = new TTStructure();
    ttStruct.loadTTStructure(fileName);
    _dm = new DModel(_dApplic,_documentName, 0);
    // read TTstructure
    // TTStructure ttStruct = new TTStructure();
    // read TTstructure
    buidDocument(title);
    _modified=true;
  } // end constructor DDocument()

  public final JInternalFrame getJIF() {
    return _jif;
  } // end getJIF

  //   public TTPanel getTTPanel() {
 //    return _ttPanel;
 //   } // end getJIF
  //-------------------------------------------
  public final String getDocumentName() {
    return _documentName;
  } // end getDocumentName

  //-------------------------------------------
  public final void setDocumentName(String name) {
    _documentName = name;
    _jif.setTitle(name);
  } // end setDocumentName
    //-------------------------------------------
  /**
   * */
  public void addTTListener(TTStructure ttStruct){
    ttStruct.addTTStructureListener(this);
  }

  public String getError(){
    return _dm.getError();
  }
    //-------------------------------------------
    public void setModified(){
      _modified = true;
    } // end setModified
    //-------------------------------------------
    public void noModified(){
      _modified = false;
    } // end setModified
    //-------------------------------------------
    public boolean isModified(){
        return _dm.getModified();
    } // end getModified
    //-------------------------------------------


    public DModel getDM(){
        return _dm;
    } //end getDModel

    public TTPanel getTTPanel(){
      return _ttPanel;
    }

    public TTStructure getTTStructure() {
      return _dm.getTTStructure();
    } // end getJIF

 //   public TTPanel getTTPanel() {
  //    return _ttPanel;
 //   } // end getJIF
    //-------------------------------------------

    public JPanel initStatusPanel(){
      JPanel panel = new JPanel();
      _nbModif = new JLabel( "Modifications " + _dm.getStatus().getModif() );
      _nbBlocs = new JLabel(DConst.BLOCS + _dm.getStatus().getModif() +" / " + _dm.getStatus().getModif() );
      _nbCInstr = new JLabel("    +CON02+ nbCftIns");
      _nbCInstr.setForeground(Color.red);
      _nbCRoom = new JLabel("    +CON03+ nbCftRoom");
      _nbCRoom.setForeground(Color.blue);
      _nbCStu = new JLabel("    +CON01+nbCftStud");
      _nbCStu.setForeground(Color.magenta);
      panel.add(_nbModif);
      panel.add(_nbBlocs);
      panel.add(_nbCInstr);
      panel.add(_nbCRoom);
      panel.add(_nbCStu);
      return panel;
    } // initBottomPanel



    public void updateStatusPanel() {
      _nbModif.setText( "Modifications " + _dm.getStatus().getModif() );
      _nbBlocs.setText(DConst.BLOCS + _dm.getStatus().getModif() +" / " + _dm.getStatus().getModif() );
     _nbCInstr.setText("    +CON02+ nbCftIns");
     _nbCInstr.setForeground(Color.red);
     _nbCRoom.setText("    +CON03+ nbCftRoom");
     _nbCRoom.setForeground(Color.blue);
     _nbCStu.setText("    +CON01+nbCftStud");
      _nbCStu.setForeground(Color.magenta);
     }



/*    public void updateTTPanel(){
      _ttPanel.setText("Change done");
    } // initBottomPanel*/

     public void actionPerformed(ActionEvent  e) {
       if (e.getSource() instanceof CommandHolder) {
         ((CommandHolder) e.getSource()).getCommand().execute(_dApplic);
       }
       else {
         System.out.println("I do not know what to do, please help me (Action Performed)");
       }// end if ... else
     }// end actionPerformed

    public void changeInDModel(DModelEvent  e) {
      this.updateStatusPanel();
      System.out.println("Update TTPanel in DDocument changeInDModel");//debug
      _ttPanel.updateTTPanel(_dm.getTTStructure());

    }// end actionPerformed

    public void changeInTTStructure(TTStructureEvent  e) {
      if (_modified){
        System.out.println("Update TTPanel in DDocument changeInTTStructure");//debug
        this.updateStatusPanel();
        _ttPanel.updateTTPanel(_dm.getTTStructure());
      }
    }// end actionPerformed

  private void  buidDocument(String title){
    //     System.out.println("check token method : "+ (new StringTokenizer("    ")).countTokens());// debug
    /* MIN_HEIGHT is needed to ajdust the minimum
    * height of the _jif */
    final int MIN_HEIGHT = 512;
    /* MIN_WIDTH is needed to ajdust the minimum
    * width of the _jif */
    final int MIN_WIDTH = 512;
    /* MIN_HEIGHT is needed to ajdust the minimum
    * height of the _jif */
    final int MAX_HEIGHT = 1024;
    /* MIN_WIDTH is needed to ajdust the minimum
    * width of the _jif */
    final int MAX_WIDTH = 1024;
    _documentName = title;
    _jif = new JInternalFrame(_documentName, true, true, true, true);
    _jif.setDefaultCloseOperation(_jif.DO_NOTHING_ON_CLOSE);
    _jif.addInternalFrameListener( new InternalFrameAdapter() {
      public void internalFrameClosing( InternalFrameEvent e ) {
        new CloseCmd().execute(_dApplic);
      }
    } );
    //_bottomLablel = new JLabel("hello");
    _statusPanel = initStatusPanel();
    _jif.getContentPane().add(_statusPanel, BorderLayout.SOUTH);
    _ttPanel = new TTPanel(_dm);
    _dm.addDModelListener(this);
    _modified = false;

    _jif.setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
    _jif.setPreferredSize(new Dimension(MAX_WIDTH, MAX_HEIGHT));
    _jif.getContentPane().add(_ttPanel, BorderLayout.CENTER);
    _jif.pack();
    _dApplic.getDesktop().add(_jif, new Integer(1));
    _jif.setVisible(true);
    //to comment if work with jifs
    try {
      _jif.setMaximum(true);  //This line allows the scrollbars of the TTPanel
                              // to be present when the _jif is resized
    }
    catch (java.beans.PropertyVetoException pve) {
      new FatalProblemDlg("I was in DDocument trying to make steMaximum!!!" );
      System.exit(52);
      pve.printStackTrace();
    }
    //comment until here
  }

  /***/
  public String getVersion(){
    return _version;
  }

  /**
   * */
  public void setVersion(String version){
    _version=version;
  }
  /*
  * a revoir
  */
  public void close(){
    _jif.dispose();
    _jif = null;
    _documentName = "";
    _dm = null;
    _ttPanel = null;
    _statusPanel = null;
    _nbModif = null;//, _nbBlocs,  _nbCStu, _nbCInstr, _nbCRoom;


  }

} /* end DDocument class */
