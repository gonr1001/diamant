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
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import dConstants.DConst;
import dInterface.DApplication;
import dInterface.dUtil.DXTools;
import dInternal.dUtil.DXToolsMethods;
import dInternal.dUtil.DXValue;


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
      if ( ((FieldRecord)((DXValue)v.get(i)).getObjectValue())._str.compareTo(str)==0)
        return ((DXValue)v.get(i)).getIntValue();
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

  protected int [] fieldsLengths(Vector right,  Vector allOpt){
    int [] a = new int [right.size()];

    for(int i = 0; i < right.size(); i++) {
      for(int j = 0; j < allOpt.size(); j++){
        if(((FieldRecord)((DXValue)allOpt.get(j)).getObjectValue())._str.compareTo((String) right.get(i)) == 0) {
          a [i] = ( ((FieldRecord)((DXValue)allOpt.get(j)).getObjectValue())._n);
          break;
        }
      }
    }

    return a;
  }


  protected Vector getOptions(Vector opt) {
    Vector v = new Vector();
    for (int i=0; i< opt.size(); i++)
      //v.add(((FieldRecord) opt.get(i))._str);
      v.add((((FieldRecord)((DXValue) opt.get(i)).getObjectValue())._str));
    return v;
  }
  /**
   * Builds a report with the format defined by the parameters
   * @param fieldsNames The first line of the report
   * @param fieldsLengths The spaces allowed for the fields
   * @param reportData The data report
   */
  public void buildReport(Object[] fieldsNames, int[] fieldsLengths, String reportData){
    StringTokenizer strLines = new StringTokenizer(reportData, DConst.CR_LF);
    StringTokenizer strFields;
    String fields;
    String currentField;
    String resultLine = "";
    //String blanks = "                                                                          ";
    String underLine = "";
    int strLinesLength, strFieldsLength;

    _jTextArea.setFont(DConst.JLISTS_FONT);
    _jTextArea.setText("");
    //do header
    for (int k = 0; k < fieldsNames.length; k++){
      currentField = adjustSize((String)fieldsNames[k],fieldsLengths[k]);
      //currentField = currentField.substring(0, fieldsLengths[k]);
      currentField = currentField + "|  ";
      resultLine += currentField;
    }
    //do frame for header
    for (int k = 0; k < resultLine.length()-2; k++){
      underLine = underLine + "-";
    }
    resultLine += DConst.CR_LF;
    underLine +=  DConst.CR_LF;

    //start the text with header + frame for header
    _jTextArea.setText(resultLine);
    _jTextArea.append(underLine);

    // number of lines
    strLinesLength = strLines.countTokens();

    for(int i = 0; i < strLinesLength; i++){
      fields = strLines.nextToken();
      strFields = new StringTokenizer(fields, ";");
      strFieldsLength = strFields.countTokens();
      resultLine = "";
      for(int j = 0; j < strFieldsLength; j++){
        currentField = strFields.nextToken();

        if(DXToolsMethods.countTokens(currentField,",") >1)
          currentField = buildSpecialLine(resultLine.length(),fieldsLengths[j], currentField);
        else {
          currentField =DXToolsMethods.getToken(currentField, ",", 0);
          currentField = adjustSize(currentField,fieldsLengths[j]);
          currentField = currentField + "|  ";
        }

        resultLine += currentField;
      }//end internal for
      resultLine += DConst.CR_LF;
      _jTextArea.append(resultLine);
    }//end external for
    _jTextArea.setCaretPosition(0);
  }

  /**
   *
   * @param i
   * @param currentField
   * @return
   */
  private String buildSpecialLine(int blankSizeBefore, int size, String currentField) {
    String result="";
    int nbToken =DXToolsMethods.countTokens(currentField, ",");
    result+= adjustSize(DXToolsMethods.getToken(currentField,",",0),size)+ "|  "+DConst.CR_LF;
    for (int i=1; i< nbToken-1; i++){
      result+= adjustSize("", blankSizeBefore) + adjustSize(DXToolsMethods.getToken(currentField,",",i),size)+ "|  "+DConst.CR_LF;
    }
    result+= adjustSize("", blankSizeBefore) + adjustSize(DXToolsMethods.getToken(currentField,",",nbToken-1),size)+ "|  ";
    return result;
  }

  /**
   *
   * @param str the string to adjust
   * @param size
   * @param toEnd
   * @return
   */
  private String adjustSize(String str, int size) {
    String blanks =" ";
    for (int i=0; i< 10; i++)
        blanks +="             ";
    str += blanks;
    return str= str.substring(0,size);
  }

}


