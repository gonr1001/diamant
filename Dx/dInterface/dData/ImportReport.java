package dInterface.dData;

/**
 * <p>Title: Proto</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author unascribed
 * @version 1.0
 */
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.Cursor;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.StringTokenizer;
import java.util.Vector;
import java.awt.Toolkit;


import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTabbedPane;

import dInterface.DApplication;
import dInternal.DModel;
import dInternal.dData.SetOfStates;
import dInternal.dData.State;
import dInterface.ProgressBar;
import dInterface.dTimeTable.SaveAsDlg;
import dInterface.dUtil.DXTools;

import dInternal.dData.Resource;
import dInternal.dData.SetOfResources;
import dInternal.dData.StandardReportData;
import dInternal.dUtil.DXValue;

import dResources.DConst;


public class ImportReport extends ViewReport implements ActionListener {
//String[] _buttonsNames = {DConst.BUT_SAVE_AS, DConst.BUT_OPTIONS, DConst.BUT_CLOSE};
  public ImportReport(ReportsDlg parentDlg, DApplication dApplic, Dimension dim) {
    super(parentDlg, dApplic, dim);
    String [] strArray ={DConst.BUT_OPTIONS};
    disableButtons(_buttonsPanel, strArray);
    setImportReport(_jTextArea);
  }


  public void setImportReport(JTextArea jta){
    jta.setFont(DConst.JLISTS_FONT);
    jta.setText("Rapport d'importation");
    jta.append(DConst.CR_LF+"---------------------------------------------------"+DConst.CR_LF);

    // enseignants
    jta.append(DConst.CR_LF+"------------------ENSEIGNANTS----------------------"+DConst.CR_LF);
    Vector setOfErrors= _dApplic.getDMediator().getCurrentDoc().getDM().
                        getSetOfImportErrors().selectIDValue("2");
    for (int i=0; i< setOfErrors.size(); i++)
      jta.append( ((DXValue)((Resource)setOfErrors.get(i)).getAttach()).getStringValue()+DConst.CR_LF);

    //locaux
    jta.append(DConst.CR_LF+"------------------LOCAUX----------------------"+DConst.CR_LF);
    setOfErrors= _dApplic.getDMediator().getCurrentDoc().getDM().
                 getSetOfImportErrors().selectIDValue("3");
    for (int i=0; i< setOfErrors.size(); i++) {
      jta.append( ((DXValue)((Resource)setOfErrors.get(i)).getAttach()).getStringValue()+DConst.CR_LF);
    }
    //etudiants
    jta.append(DConst.CR_LF+"------------------ETUDIANTS----------------------"+DConst.CR_LF);
    setOfErrors= _dApplic.getDMediator().getCurrentDoc().getDM().
                 getSetOfImportErrors().selectIDValue("1");
    for (int i=0; i< setOfErrors.size(); i++)
      jta.append( ((DXValue)((Resource)setOfErrors.get(i)).getAttach()).getStringValue()+DConst.CR_LF);
    //buildReport(fieldsNames, fieldLengths, subFields, "Rapport d'importation");
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
        new SaveAsDlg(_dApplic, data);
    }//end if (e.getActionCommand().equals(_buttonsNames[0]))

  }//end method
  public void doSave(Vector rigth) {
     _dApplic.getPreferences().setSelectedOptionsInFullReport(rigth);
     _dApplic.getPreferences().save();
     _rightVec = rigth;
   }

}