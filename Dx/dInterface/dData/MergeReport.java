/**
*
* Title: ImportReport $Revision: 1.5 $  $Date: 2005-04-19 20:37:43 $
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
* @version $Revision: 1.5 $
* @author  $Author: gonzrubi $
* @since JDK1.3
*
* Our convention is that: It's necessary to indicate explicitly
* all Exceptions that a method can throw.
* All Exceptions must be handled explicitly.
*/


/**
* Description: ImportReport is a class used to
*
*/
package dInterface.dData;




import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.JTextArea;

import dConstants.DConst;
import dInterface.DApplication;
import dInterface.dTimeTable.SaveAsTxtDlg;
import dInternal.DSetOfResources;
import dInternal.DValue;


public class MergeReport extends ViewReport implements ActionListener {

 public MergeReport(ReportsDlg parentDlg, DApplication dApplic, Dimension dim) {
   super(parentDlg, dApplic, dim);
   String [] strArray ={DConst.BUT_OPTIONS};
   disableButtons(_buttonsPanel, strArray);
   setImportReport(_jTextArea);
 }


 /**
  *
  * @param jta
  */
 public void setImportReport(JTextArea jta){
   JTextArea jtaDeleted = new JTextArea("");
   JTextArea jtaAdded = new JTextArea("");
   JTextArea jtaChanged = new JTextArea("");
   JTextArea jtaUnchanged = new JTextArea("");
   jta.setFont(DConst.JLISTS_FONT);
   jta.setText("Rapport d'importation selective");
   jta.append(DConst.CR_LF + DConst.SEPARATOR + DConst.CR_LF);


   jtaDeleted.append(DConst.CR_LF + DConst.DELETED_ELEMENT + DConst.CR_LF);
   jtaAdded.append(DConst.CR_LF + DConst.ADDED_ELEMENT + DConst.CR_LF);
   jtaChanged.append(DConst.CR_LF + DConst.CHANGED_ELEMENT + DConst.CR_LF);
   jtaUnchanged.append(DConst.CR_LF + DConst.UNCHANGED_ELEMENT + DConst.CR_LF);
   
   
   DSetOfResources setOfImportSelErrors= _dApplic.getDMediator().getCurrentDoc().getDM().getSetOfImportSelErrors();
   for (int i=0; i< setOfImportSelErrors.size(); i++){

     if(setOfImportSelErrors.getResourceAt(i).getID().equalsIgnoreCase("1"))
     	jtaDeleted.append(((DValue)(setOfImportSelErrors.getResourceAt(i)).getAttach()).getStringValue()+DConst.CR_LF);

     else if(setOfImportSelErrors.getResourceAt(i).getID().equalsIgnoreCase("2"))
     	jtaAdded.append(((DValue)(setOfImportSelErrors.getResourceAt(i)).getAttach()).getStringValue()+DConst.CR_LF);
     else if(setOfImportSelErrors.getResourceAt(i).getID().equalsIgnoreCase("3"))
     	jtaChanged.append(((DValue)(setOfImportSelErrors.getResourceAt(i)).getAttach()).getStringValue()+DConst.CR_LF);
     else if(setOfImportSelErrors.getResourceAt(i).getID().equalsIgnoreCase("4"))
     	jtaUnchanged.append(((DValue)(setOfImportSelErrors.getResourceAt(i)).getAttach()).getStringValue()+DConst.CR_LF);

   }
   jta.append(jtaDeleted.getText());
   jta.append(jtaAdded.getText());
   jta.append(jtaChanged.getText());
   jta.append(jtaUnchanged.getText());
   jta.setCaretPosition(0);
 }

 public void actionPerformed(ActionEvent e){
   //String command = e.getActionCommand();
   //if "Option" button
   if (e.getActionCommand().equals(DConst.BUT_OPTIONS)){
   	//
   }
     // it is disabled
   //if "Close" button
   if (e.getActionCommand().equals(DConst.BUT_CLOSE))
      //System.out.println("_buttonsNames[2]");
     dispose();
   //if "Save as" button
   if (e.getActionCommand().equals(DConst.BUT_SAVE_AS)){
      //System.out.println("_buttonsNames[0]");
       Date date = new Date();
       SimpleDateFormat sdf = new SimpleDateFormat("EEEE-MMMM-dd-yyyy:kk:mm");

       JTextArea jta = (JTextArea)_scrollPane.getViewport().getComponent(0);
       String data = "***** " +
                     DConst.REPORT +
                     " " +
                     DConst.TO_LEFT +
                     DConst.REPORT_DLG_TAB3 +
                     DConst.TO_RIGHT + "          ";
       data +=  DConst.REPORT_PRODUCED_AT +
                " " +
                sdf.format(date) +
                " *****" +
                DConst.CR_LF + DConst.CR_LF;
       data +=  jta.getText();
       new SaveAsTxtDlg(_dApplic, data);
   }//end if (e.getActionCommand().equals(_buttonsNames[0]))

 }//end method
 public void doSave(Vector rigth) {
    _dApplic.getPreferences().setSelectedOptionsInFullReport(rigth);
    _dApplic.getPreferences().save();
    _rightVec = rigth;
  }

}