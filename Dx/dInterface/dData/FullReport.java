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


public class FullReport extends ViewReport implements ActionListener {
//String[] _buttonsNames = {DConst.BUT_SAVE_AS, DConst.BUT_OPTIONS, DConst.BUT_CLOSE};
  public FullReport(ReportsDlg parentDlg, DApplication dApplic, Dimension dim) {
    super(parentDlg, dApplic, dim);
    String [] strArray ={DConst.BUT_OPTIONS};
    _allOptionsVec = buildAllOptionsVector();
    Vector options = getOptions(_allOptionsVec);
    _rightVec = _dApplic.getPreferences().getSelectedOptionsInFullReport();
    _elements = options.size() - _rightVec.size();
    options = merge (options, _rightVec);
    int car = 0;
    if (_rightVec.size() == 0 )
      _jTextArea.setText("Choisir options");
    else {
      car = indexElementIn ((String)_rightVec.get(0), _allOptionsVec);
         int [] cdr = buildNext(_rightVec, _allOptionsVec);
      _jTextArea.setText((_parentDlg.getStandardReportData()).getActivitiesReport(car, cdr));
    }

  }

/*
  * The option must be sorted by alphabetical order in the field String (second param)

  * token number 0= activity name, 1= type name, 2= section name, 3= unity name, 4= duration of the activity
  * 5= day number where activity is assign, 6= day name where activity is assign
  * 7= begin hour of the activity, 8= end hour of the activity, 9= instructor name
  * 10= room name
  * */
  private Vector buildAllOptionsVector() {
    Vector v = new Vector();
    v.add(new FieldRecord(Integer.parseInt(DConst.R_ACTIVITY_NAME_L), DConst.R_ACTIVITY_NAME));
    v.add(new FieldRecord(Integer.parseInt(DConst.R_TYPE_NAME_L), DConst.R_TYPE_NAME));
    v.add(new FieldRecord(Integer.parseInt(DConst.R_SECTION_NAME_L), DConst.R_SECTION_NAME));
    v.add(new FieldRecord(Integer.parseInt(DConst.R_UNITY_NAME_L), DConst.R_UNITY_NAME));
    v.add(new FieldRecord(Integer.parseInt(DConst.R_DURATION_L), DConst.R_DURATION));
    v.add(new FieldRecord(Integer.parseInt(DConst.R_DAY_NUMBER_L), DConst.R_DAY_NUMBER));
    v.add(new FieldRecord(Integer.parseInt(DConst.R_DAY_NAME_L), DConst.R_DAY_NAME));
    v.add(new FieldRecord(Integer.parseInt(DConst.R_ACTIVITY_BEGIN_HOUR_L), DConst.R_ACTIVITY_BEGIN_HOUR));
    v.add(new FieldRecord(Integer.parseInt(DConst.R_ACTIVITY_END_HOUR_L), DConst.R_ACTIVITY_END_HOUR));
    v.add(new FieldRecord(Integer.parseInt(DConst.R_INSTRUCTOR_NAME_L), DConst.R_INSTRUCTOR_NAME));
    v.add(new FieldRecord(Integer.parseInt(DConst.R_ROOM_NAME_L), DConst.R_ROOM_NAME));

    return v;
  }

  private Vector getOptions(Vector opt) {
  Vector v = new Vector();
  for (int i=0; i< opt.size(); i++)
    v.add(((FieldRecord) opt.get(i))._str);

  return v;
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
     new ReportOptionsDlg(_dApplic,
                            _parentDlg,
                            _rightVec,
                            _elements);
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


}
