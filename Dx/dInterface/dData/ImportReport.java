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
import dInterface.dTimeTable.SaveAsTxtDlg;
import dInterface.dUtil.DXTools;

import dInternal.dData.Resource;
import dInternal.dData.SetOfResources;
import dInternal.dData.StandardReportData;
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