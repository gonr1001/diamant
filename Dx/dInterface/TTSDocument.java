/**
 *
 * Title: DDocument $Revision: 1.2 $  $Date: 2003-06-13 17:02:09 $
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
 * @version $Revision: 1.2 $
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

public class TTSDocument extends AbstractDocument implements ActionListener, DModelListener, TTStructureListener{

  //for new
 public TTSDocument(DApplication dApplic, String title, TTStructure ttStruct) {
   super(dApplic, title);

   //_dm = new DModel(_dApplic, ttStruct);
   ttStruct.addTTStructureListener(this);
   //buidDocument();
  // _modified=true;
} // end constructor DDocument()

//for open
//-------------------------------------------
public TTSDocument(DApplication dApplic, String title) {
   super(dApplic, title);

  //_dm = new DModel(_dApplic,_documentName);
  //// read TTstructure
  // TTStructure ttStruct = new TTStructure();
  // read TTstructure
  //buidDocument();
 // _modified=true;
  } // end constructor DDocument()


  public void actionPerformed(ActionEvent  e) {
if (e.getSource() instanceof CommandHolder) {
 ((CommandHolder) e.getSource()).getCommand().execute(_dApplic);
}
else {
System.out.println("I do not know what to do, please help me (Action Performed)");
}// end if ... else
}// end actionPerformed

public void changeInDModel(DModelEvent  e) {
 // this.updateStatusPanel();
  //_ttPanel.updateTTPanel(_dm.getTTStructure());
}// end actionPerformed

public void changeInTTStructure(TTStructureEvent  e) {
  System.out.println("change In TTSturtutr");
  //this.updateStatusPanel();
  //_ttPanel.updateTTPanel(_dm.getTTStructure());
    }// end actionPerformed
}