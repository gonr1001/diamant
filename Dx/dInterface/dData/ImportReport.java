/**
 *
 * Title: ImportReport $Revision: 1.7 $  $Date: 2004-05-12 19:51:16 $
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
 * @version $Revision: 1.7 $
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




import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;


import javax.swing.JTextArea;


import dInterface.DApplication;
import dInterface.dTimeTable.SaveAsTxtDlg;


import dInternal.dData.Resource;
import dInternal.dData.SetOfResources;
import dInternal.dUtil.DXValue;

import dResources.DConst;


public class ImportReport extends ViewReport implements ActionListener {

  public ImportReport(ReportsDlg parentDlg, DApplication dApplic, Dimension dim) {
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
    JTextArea jtaInst= new JTextArea("");
    JTextArea jtaRooms= new JTextArea("");
    JTextArea jtaStud= new JTextArea("");
    jta.setFont(DConst.JLISTS_FONT);
    jta.setText("Rapport d'importation");
    jta.append(DConst.CR_LF+"---------------------------------------------------"+DConst.CR_LF);

    // enseignants
    jtaInst.append(DConst.CR_LF+"------------------ENSEIGNANTS----------------------"+DConst.CR_LF);
    //locaux
    jtaRooms.append(DConst.CR_LF+"------------------LOCAUX----------------------"+DConst.CR_LF);
    //etudiants
    jtaStud.append(DConst.CR_LF+"------------------ETUDIANTS----------------------"+DConst.CR_LF);
    SetOfResources setOfImportErrors= _dApplic.getDMediator().getCurrentDoc().getDM().getSetOfImportErrors();
    for (int i=0; i< setOfImportErrors.size(); i++){
      // Etudiants
      if(setOfImportErrors.getResourceAt(i).getID().equalsIgnoreCase("1"))
        jtaStud.append(((DXValue)((Resource)setOfImportErrors.getResourceAt(i)).getAttach()).getStringValue()+DConst.CR_LF);
      // Enseignants
      else if(setOfImportErrors.getResourceAt(i).getID().equalsIgnoreCase("2"))
        jtaInst.append(((DXValue)((Resource)setOfImportErrors.getResourceAt(i)).getAttach()).getStringValue()+DConst.CR_LF);
      // Locaux
      else if(setOfImportErrors.getResourceAt(i).getID().equalsIgnoreCase("3"))
        jtaRooms.append(((DXValue)((Resource)setOfImportErrors.getResourceAt(i)).getAttach()).getStringValue()+DConst.CR_LF);

    }
    jta.append(jtaInst.getText());
    jta.append(jtaRooms.getText());
    jta.append(jtaStud.getText());
    jta.setCaretPosition(0);
  }

  public void actionPerformed(ActionEvent e){
    String command = e.getActionCommand();
    //if "Option" button
    if (e.getActionCommand().equals(DConst.BUT_OPTIONS))
       ; // it is disabled
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
                      DConst.TO_RIGHT + " ";
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