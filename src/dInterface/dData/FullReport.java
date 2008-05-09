/**
 *
 * Title: FullReport $Revision: 1.20 $  $Date: 2007-12-10 01:59:12 $
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
 * @version $Revision: 1.20 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 *
 * Our convention is that: It's necessary to indicate explicitly
 * all Exceptions that a method can throw.
 * All Exceptions must be handled explicitly.
 */


/**
 * Description: FullReport is a class used to
 *
 */package dInterface.dData;



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
import dInternal.DValue;


public class FullReport extends ViewReport implements ActionListener {

  public FullReport(ReportsDlg parentDlg, DApplication dApplic, Dimension dim) {
    super(parentDlg, dApplic, dim);
    _allOptionsVec = buildAllOptionsVector();
    _options = getOptions(_allOptionsVec);
    _rightVec = _dApplic.getDxPreferences().getSelectedOptionsInFullReport();
    showReport();
  } //FullReport

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
         (_parentDlg.getStandardReportData()).getActivitiesReport(firstField, othersFields)
         );
     
     buildReport(_rightVec.toArray(),
                 fieldsLengths(_rightVec,_allOptionsVec),
                 (_parentDlg.getStandardReportData()).getActivitiesReport(firstField, othersFields));
      _jTextArea.setCaretPosition(0);
    }
   
  }
/*
  * The option must be sorted by alphabetical order in the field String (second param)

  * token number 0= activity name, 1= type name, 2= section name, 3= unity name, 4= duration of the activity
  * 5= day number where activity is assign, 6= day name where activity is assign
  * 7= begin hour of the activity, 8= end hour of the activity, 9= instructor name
  * 10= room name
  * */
  private Vector<DValue> buildAllOptionsVector() {
    Vector <DValue> v = new Vector<DValue>();
    v.add(new DValue(0,
                      new FieldRecord(Integer.parseInt(DConst.R_ACTIVITY_NAME_L), DConst.R_ACTIVITY_NAME)));
    v.add(new DValue(1,
                      new FieldRecord(Integer.parseInt(DConst.R_TYPE_NAME_L), DConst.R_TYPE_NAME)));
    v.add(new DValue(2,
                      new FieldRecord(Integer.parseInt(DConst.R_SECTION_NAME_L), DConst.R_SECTION_NAME)));
    v.add(new DValue(3,
                      new FieldRecord(Integer.parseInt(DConst.R_UNITY_NAME_L), DConst.R_UNITY_NAME)));
    v.add(new DValue(4,
                      new FieldRecord(Integer.parseInt(DConst.R_DURATION_L), DConst.R_TIME_LENGTH)));
    v.add(new DValue(5,
                      new FieldRecord(Integer.parseInt(DConst.R_DAY_NUMBER_L), DConst.R_DAY_NUMBER)));
    v.add(new DValue(6,
                      new FieldRecord(Integer.parseInt(DConst.R_DAY_NAME_L), DConst.R_DAY_NAME)));
    v.add(new DValue(7,
                      new FieldRecord(Integer.parseInt(DConst.R_ACTIVITY_BEGIN_HOUR_L), DConst.R_ACTIVITY_BEGIN_HOUR)));
    v.add(new DValue(8,
                      new FieldRecord(Integer.parseInt(DConst.R_ACTIVITY_END_HOUR_L), DConst.R_ACTIVITY_END_HOUR)));
    v.add(new DValue(9,
                      new FieldRecord(Integer.parseInt(DConst.R_INSTRUCTOR_NAME_L), DConst.R_INSTRUCTOR_NAME)));
    v.add(new DValue(10,
                      new FieldRecord(Integer.parseInt(DConst.R_ROOM_NAME_L), DConst.R_ROOM_NAME)));
    v.add(new DValue(11,
                      new FieldRecord(Integer.parseInt(DConst.R_STUDENT_SIZE_L), DConst.R_STUDENT_SIZE_NAME)));
    return v;
  }

  

  public void actionPerformed(ActionEvent e){
    //String command = e.getActionCommand();
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
                    DConst.REPORT_DLG_TAB1 +
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

  public void doSave(Vector <String> rigth) {
    _dApplic.getDxPreferences().setSelectedOptionsInFullReport(rigth);
    _dApplic.getDxPreferences().save();
    _rightVec = rigth;
  }
}