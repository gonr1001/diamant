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
import dInternal.dDataTxt.SetOfStates;
import dInternal.dDataTxt.State;
import dInterface.ProgressBar;
import dInterface.dTimeTable.SaveAsTxtDlg;
import dInterface.dUtil.DXTools;

import dInternal.dDataTxt.Resource;
import dInternal.dDataTxt.SetOfResources;
import dInternal.dDataTxt.StandardReportData;
import dInternal.dUtil.DXValue;

import dConstants.DConst;

public class ConflictReport extends ViewReport implements ActionListener {

  public ConflictReport(ReportsDlg parentDlg, DApplication dApplic, Dimension dim) {
    super(parentDlg, dApplic, dim);
    _allOptionsVec = buildAllOptionsVector();
    _options = getOptions(_allOptionsVec);
    _rightVec = _dApplic.getPreferences().getSelectedOptionsInConflictReport();

    showReport();


  }

  private void showReport() {
    _elements = _options.size() - _rightVec.size();
    _options = buildExternalOptions(_options, _rightVec);
    int firstField = 0;
   if (_rightVec.size() == 0 )
     _jTextArea.setText("Choisir options");
   else {
     firstField = indexElementIn ((String)_rightVec.get(0), _allOptionsVec);
     int [] othersFields = buildOtherFields(_rightVec, _allOptionsVec);
     _jTextArea.setText(
         (_parentDlg.getStandardReportData()).getConflictsReport(firstField, othersFields)
         );
     buildReport(_rightVec.toArray(),
                 fieldsLengths(_rightVec,_allOptionsVec),
                 (_parentDlg.getStandardReportData()).getConflictsReport(firstField, othersFields));
      _jTextArea.setCaretPosition(0);
    }
  }
/*
  *_conflictsReport is a string where each line contains more informations separated
  * by a ";" separator
  * token number 0= day number, 1= day name, 2= sequence Id, 3= period id, 4= period begin hour
  * 5= first event in conflict, 6= event in conflict with the first,
  * 7= number of conflicts, 8= type of conflicts
  * 9= conflict description
  * */
  private Vector buildAllOptionsVector() {
    Vector v = new Vector();
    /*v.add(new FieldRecord(Integer.parseInt(DConst.R_DAY_NUMBER_L), DConst.R_DAY_NUMBER));
    v.add(new FieldRecord(Integer.parseInt(DConst.R_DAY_NAME_L), DConst.R_DAY_NAME));
    v.add(new FieldRecord(Integer.parseInt(DConst.R_SEQUENCE_ID_L), DConst.R_SEQUENCE_ID));
    v.add(new FieldRecord(Integer.parseInt(DConst.R_UNITY_NAME_L), ""));
    v.add(new FieldRecord(Integer.parseInt(DConst.R_PERIOD_BEGIN_H_L), DConst.R_PERIOD_BEGIN_H));
    v.add(new FieldRecord(Integer.parseInt(DConst.R_EVENT1_ID_L), DConst.R_EVENT1_ID));
    v.add(new FieldRecord(Integer.parseInt(DConst.R_EVENT2_ID_L), DConst.R_EVENT2_ID));
    v.add(new FieldRecord(Integer.parseInt(DConst.R_NUMBER_OF_CONFLICTS_L), DConst.R_NUMBER_OF_CONFLICTS));
    v.add(new FieldRecord(Integer.parseInt(DConst.R_TYPE_OF_CONFLICT_L), DConst.R_TYPE_OF_CONFLICT));
    v.add(new FieldRecord(Integer.parseInt(DConst.R_ELEMENTS_IN_CONFLICT_L), DConst.R_ELEMENTS_IN_CONFLICT));
*/
       v.add(new DXValue(0,
              new FieldRecord(Integer.parseInt(DConst.R_DAY_NUMBER_L), DConst.R_DAY_NUMBER)));
        v.add(new DXValue(1,
              new FieldRecord(Integer.parseInt(DConst.R_DAY_NAME_L), DConst.R_DAY_NAME)));
        v.add(new DXValue(2,
              new FieldRecord(Integer.parseInt(DConst.R_SEQUENCE_ID_L), DConst.R_SEQUENCE_ID)));
        //v.add(new DXValue(3,
         //     new FieldRecord(Integer.parseInt(DConst.R_UNITY_NAME_L), DConst.R_UNITY_NAME)));
        v.add(new DXValue(4,
              new FieldRecord(Integer.parseInt(DConst.R_PERIOD_BEGIN_H_L), DConst.R_PERIOD_BEGIN)));
        v.add(new DXValue(5,
        new FieldRecord(Integer.parseInt(DConst.R_EVENT1_ID_L), DConst.R_EVENT1_ID)));
        v.add(new DXValue(6,
            new FieldRecord(Integer.parseInt(DConst.R_EVENT2_ID_L), DConst.R_EVENT2_ID)));
        v.add(new DXValue(7,
         new FieldRecord(Integer.parseInt(DConst.R_NUMBER_OF_CONFLICTS_L), DConst.R_NUMBER_OF_CONFLICTS)));
        v.add(new DXValue(8,
        new FieldRecord(Integer.parseInt(DConst.R_TYPE_OF_CONFLICT_L), DConst.R_TYPE_OF_CONFLICT)));
    v.add(new DXValue(9,
    new FieldRecord(Integer.parseInt(DConst.R_ELEMENTS_IN_CONFLICT_L), DConst.R_ELEMENTS_IN_CONFLICT)));

    return v;
  }

/*  private Vector getOptions(Vector opt) {
    Vector v = new Vector();
    for (int i=0; i< opt.size(); i++)
      //v.add(((FieldRecord) opt.get(i))._str);
      v.add((((FieldRecord)((DXValue) opt.get(i)).getObjectValue())._str));
    return v;
  }*/

  public void actionPerformed(ActionEvent e){
    String command = e.getActionCommand();
    //if "Option" button
    if (e.getActionCommand().equals(DConst.BUT_OPTIONS))
      new ReportOptionsDlg(_dApplic,
                           this,
                           _options,
                           _elements);
    //_elements = _options.size() - _rightVec.size();
    //_options = buildExternalOptions(_options, _rightVec);
    showReport();
    //if "Close" button
    if (e.getActionCommand().equals(DConst.BUT_CLOSE))
      //_dApplic.getPreferences().setSelectedOptionsInConflictReport(_rightVec);
      // _dApplic.getPreferences().save();
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
    _dApplic.getPreferences().setSelectedOptionsInConflictReport(rigth);
    _dApplic.getPreferences().save();
    _rightVec = rigth;
  }
}
