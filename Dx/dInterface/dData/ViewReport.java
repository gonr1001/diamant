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


public abstract class ViewReport  extends JPanel implements ActionListener {

  ReportsDlg _parentDlg;
  JScrollPane _scrollPane;
  DApplication _dApplic;
  JTextArea _jTextArea;
  JPanel _buttonsPanel;
  Vector _allOptionsVec;
  Vector _rightVec;
  Vector _options;
  int _elements;

  protected class FieldRecord {
    int _n;  //size of String used to format
    String _str; // the name of the column
    FieldRecord(int n, String str){
      _n = n; _str = str;
    }
  }

  public ViewReport(ReportsDlg parentDlg, DApplication dApplic, Dimension dim) {
    _parentDlg = parentDlg;
    _dApplic = dApplic;
    _allOptionsVec = new Vector();
    _jTextArea = new JTextArea();
    this.setLayout(new BorderLayout());
    //setImportReport(jta);
    this.setPreferredSize(dim);
    _scrollPane = new JScrollPane();
    _scrollPane.setPreferredSize(new Dimension((int)dim.getWidth(), (int)dim.getHeight()-20));
    _scrollPane.getViewport().setView(_jTextArea);
    this.add(_scrollPane,BorderLayout.CENTER);
    String[] _buttonsNames = {DConst.BUT_SAVE_AS, DConst.BUT_OPTIONS, DConst.BUT_CLOSE};
    _buttonsPanel = DXTools.buttonsPanel(this, _buttonsNames);
    this.add(_buttonsPanel, BorderLayout.SOUTH);
  }

  protected void disableButtons(JPanel buttons, String [] strArray){
    String str= "";
    for (int i = 0; i < buttons.getComponentCount(); i++) {
      ((JButton)buttons.getComponent(i)).setEnabled(true);
    }
    if (strArray != null) {
      for (int i = 0; i < strArray.length; i++) {
        str = strArray[i];
        for(int j = 0 ; j < buttons.getComponentCount(); j++) {
          if (((JButton)buttons.getComponent(j)).getActionCommand().equals(str))
            ((JButton)buttons.getComponent(j)).setEnabled(false);
        }
      }
    }
  }

  protected void dispose() {
    //dispose();
    _parentDlg.dispose();
  }

  protected Vector buildExternalOptions(Vector opt, Vector  right) {
    Vector res =  new Vector();
    for (int i = 0; i < right.size(); i++){
      opt.remove(right.get(i));
    }

    for (int i = 0; i < opt.size(); i++){
      res.add(opt.get(i));
    }

    res = DXTools.sortVector(res);

    for (int i = 0; i < right.size(); i++){
      res.add(right.get(i));
    }
    return res;
  }

  protected int indexElementIn(String str, Vector v) {
    int index = -1;

    for(int i = 0; i < v.size(); i++) {
      if ( ((FieldRecord)v.get(i))._str.compareTo(str)==0)
        return i;
    }
    return index;
  }

  protected int [] buildOtherFields(Vector v, Vector allOpt) {
    int [] a =  new int [v.size()-1];

    for (int i = 1; i < v.size(); i++)
      a [i-1] = indexElementIn((String)v.get(i),allOpt);
    return a;
  }

  public abstract void doSave(Vector rigth);
  public abstract void actionPerformed(ActionEvent e);

  public int [] fieldsLengths(Vector right,  Vector allOpt){
    int [] a = new int [right.size()];
    for(int i= 0; i< right.size(); i++)
      a [i] = ((FieldRecord)allOpt.get(indexElementIn((String)right.get(i),allOpt)))._n;
    return a;
  }

  /**
 * Builds a report with the format defined by the parameters
 * @param fieldsNames The first line of the report
 * @param fieldsLengths The spaces allowed for the fields
 * @param reportData The data report
 */
public void buildReport(Object[] fieldsNames, int[] fieldsLengths, String[][][] subFields, String reportData){
  StringTokenizer strLines = new StringTokenizer(reportData, DConst.CR_LF);
  StringTokenizer strFields;
  String fields;
  String currentField;
  String resultLine = "";
  String blanks = "                                                                          ";
  String underLine = "";
  int strLinesLength, strFieldsLength;
  //JScrollPane scrollPanel = (JScrollPane)((JPanel)_tabbedPane.getSelectedComponent()).getComponent(0);
  //JTextArea jta = (JTextArea)scrollPanel.getViewport().getComponent(0);
  _jTextArea.setFont(DConst.JLISTS_FONT);
  _jTextArea.setText("");
  for (int k = 0; k < fieldsNames.length; k++){
    currentField = (String)fieldsNames[k] + blanks;
    currentField = currentField.substring(0, fieldsLengths[k]);
    currentField = currentField + "|  ";
    resultLine = resultLine + currentField;
  }
  for (int k = 0; k < resultLine.length()-2; k++)
    underLine = underLine + "-";
  resultLine = resultLine + DConst.CR_LF;
  underLine = underLine + DConst.CR_LF;
  _jTextArea.setText(resultLine);
  _jTextArea.append(underLine);
  strLinesLength = strLines.countTokens();
  for(int i = 0; i < strLinesLength; i++){
    fields = strLines.nextToken();
    strFields = new StringTokenizer(fields, ";");
    strFieldsLength = strFields.countTokens();
    resultLine = "";
    for(int j = 0; j < strFieldsLength; j++){
      currentField = strFields.nextToken();
      if(subFields !=null) {
      if(subFields[j] != null){
        //System.out.println("subFields.length " + subFields.length);
        //System.out.println("subFields.length "+subFields[j].length);
        for(int k = 0; k < subFields[j].length; k++){
          if (currentField.equals(subFields[j][k][0])){
            currentField = subFields[j][k][1];
            break;
          }//end internal if
        }//end internal for
      }//end external if
      }
      currentField = currentField + blanks;
      currentField = currentField.substring(0, fieldsLengths[j]);
      currentField = currentField + "|  ";
      resultLine = resultLine + currentField;
    }//end internal for
    resultLine = resultLine + DConst.CR_LF;
    _jTextArea.append(resultLine);
  }//end external for
  _jTextArea.setCaretPosition(0);
  }
}
