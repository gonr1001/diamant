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

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTabbedPane;

import dInterface.DApplication;
import dInterface.dUtil.DXTools;

import dInternal.dData.Resource;
import dInternal.dData.SetOfResources;
import dInternal.dData.StandardReportData;
import dInternal.dUtil.DXValue;

import dResources.DConst;

public class ReportDlg extends JDialog implements ActionListener{

  private DApplication _dApplic = null;
  private JDialog _jd = this;
  private JTabbedPane _tabbedPane;
  private StandardReportData _srd;
  private String[] _buttonsNames = {DConst.BUT_OK, DConst.BUT_OPTIONS, DConst.BUT_CANCEL};
  private SetOfResources _resources;

  public ReportDlg(DApplication dApplic) {
    super(dApplic.getJFrame(), DConst.REPORT_DLG_TITLE, true);
    _dApplic = dApplic;
    jbInit();
    setLocationRelativeTo(dApplic.getJFrame());
    setVisible(true);
  }//end constructor

  /**
   * Doalog initialization
   */
  private void jbInit(){
    Dimension dialogDim = new Dimension(1000,600);
    Dimension tabbedPaneDim = new Dimension((int)dialogDim.getWidth()-50, (int)dialogDim.getHeight()-70);
    Dimension tabDim = tabbedPaneDim;//new Dimension((int)dialogDim.getWidth()-50, (int)dialogDim.getHeight()-70);
    getContentPane().setLayout(new BorderLayout());
    setSize(dialogDim);
    setResizable(true);
    //the tabbedPane
    _tabbedPane = new JTabbedPane();
    _tabbedPane.setPreferredSize(tabbedPaneDim);
    _tabbedPane.addTab(DConst.REPORT_DLG_TAB1, createTabPanel(tabbedPaneDim, "Clicker sur le button Options pour d�finir les champs du rapport"));
    _tabbedPane.addTab(DConst.REPORT_DLG_TAB2, createTabPanel(tabbedPaneDim, ""));
    _tabbedPane.addTab(DConst.REPORT_DLG_TAB3, createTabPanel(tabbedPaneDim, ""));
    //adding the elements to the dialog
    getContentPane().add(_tabbedPane, BorderLayout.NORTH);
    getContentPane().add(DXTools.buttonsPanel(this, _buttonsNames), BorderLayout.SOUTH);
  }

  /**
   * Builds a panel contained into a tab of a tabbedPanel. This panel contains
   * a JTextArea for displaying a String
   * @param dim The panel dimension
   * @param message The first message to be displayed into the text area
   * @return the tabPanel
   */
  private JPanel createTabPanel(Dimension dim, String message){
    JTextArea jta = new JTextArea(message);
    JPanel panel = new JPanel(new BorderLayout());
    panel.setPreferredSize(dim);
    JScrollPane scrollPane = new JScrollPane();
    scrollPane.getViewport().setLayout(new BorderLayout());
    scrollPane.getViewport().add(jta, BorderLayout.CENTER);
    panel.add(scrollPane, BorderLayout.CENTER);
    return panel;
  }



  /**
   * Gets the data report format from the setOfResourcesSet, gets the data report
   * from the methos getReportData and Builds the report into the tabbedPnel
   * @param selectedResources
   */
  public void setReport(SetOfResources selectedResources){
    if (_tabbedPane.getSelectedIndex() == -1)
      return;
    if (selectedResources.size() <= 0)
      return;
    int mainFieldKey = 0;
    int[] otherFieldsKeys = new int[selectedResources.size()-1];
    int[] fieldLengths = new int[selectedResources.size()];
    String[] fieldsNames = new String[selectedResources.size()];
    Resource res;
    for (int i = 0; i < selectedResources.size(); i++){
      res = selectedResources.getResourceAt(i);
      if (i == 0)
        mainFieldKey = (int)((DXValue)res.getAttach()).getIntValue();
      else
        otherFieldsKeys[i-1] = (int)((DXValue)res.getAttach()).getIntValue();;
      fieldsNames[i] = selectedResources.getResourceAt(i).getID();
      fieldLengths[i] = Integer.parseInt(((DXValue)selectedResources.getResourceAt(i).getAttach()).getStringValue());
      ((DXValue)res.getAttach()).setBooleanValue(true);
      _resources = selectedResources;
    }//end for
    String reportData = getReportData(_tabbedPane.getSelectedIndex(), mainFieldKey, otherFieldsKeys);
    JScrollPane scrollPanel = (JScrollPane)((JPanel)_tabbedPane.getSelectedComponent()).getComponent(0);
    JTextArea jta = (JTextArea)scrollPanel.getViewport().getComponent(0);
    jta.setFont(DConst.JLISTS_FONT);
    jta.setText(reportData);
    buildReport(fieldsNames, fieldLengths, reportData);
  }//end method

  /**
   * Builds a report with the format defined by the parameters
   * @param fieldsNames The first line of the report
   * @param fieldsLengths The spaces allowed for the fields
   * @param reportData The data report
   */
  private void buildReport(String[] fieldsNames, int[] fieldsLengths, String reportData){
    StringTokenizer strLines = new StringTokenizer(reportData, DConst.CR_LF);
    StringTokenizer strFields;
    String fields;
    String currentField;
    String resultLine = "";
    String blanks = "                               ";
    String underLine = "";
    int strLinesLength, strFieldsLength;
    JScrollPane scrollPanel = (JScrollPane)((JPanel)_tabbedPane.getSelectedComponent()).getComponent(0);
    JTextArea jta = (JTextArea)scrollPanel.getViewport().getComponent(0);
    jta.setFont(DConst.JLISTS_FONT);
    jta.setText("");
    for (int k = 0; k < fieldsNames.length; k++){
      currentField = fieldsNames[k] + blanks;
      currentField = currentField.substring(0, fieldsLengths[k]);
      currentField = currentField + "|  ";
      resultLine = resultLine + currentField;
    }
    for (int k = 0; k < resultLine.length()-2; k++)
      underLine = underLine + "-";
    resultLine = resultLine + DConst.CR_LF;
    underLine = underLine + DConst.CR_LF;
    jta.setText(resultLine);
    jta.append(underLine);
    strLinesLength = strLines.countTokens();
    for(int i = 0; i < strLinesLength; i++){
      fields = strLines.nextToken();
      strFields = new StringTokenizer(fields, ";");
      strFieldsLength = strFields.countTokens();
      resultLine = "";
      for(int j = 0; j < strFieldsLength; j++){
        currentField = strFields.nextToken();
        currentField = currentField + blanks;
        currentField = currentField.substring(0, fieldsLengths[j]);
        currentField = currentField + "|  ";
        resultLine = resultLine + currentField;
      }//end internal for
      resultLine = resultLine + DConst.CR_LF;
      jta.append(resultLine);
    }//end external for
  }

  public void actionPerformed(ActionEvent e){
    String command = e.getActionCommand();
    //If "Option" button
    if (e.getSource().equals(((JPanel)this.getContentPane().getComponent(1)).getComponent(1)))
        new ReportOptionsDlg(_dApplic, _jd, _resources, _tabbedPane.getSelectedIndex());
    //If "Cancel" button
    if (e.getSource().equals(((JPanel)this.getContentPane().getComponent(1)).getComponent(2)))
      dispose();
  }//end method


  /**
   * Gets the data report with regard to the type of required report
   * @param reportIndex The index of the required report
   * @param mainFieldKey the firs index to build the report
   * @param otherFieldsKeys the other index to build the report
   * @return data report
   */
  private String getReportData(int reportIndex, int mainFieldKey, int[] otherFieldsKeys){
    _srd = new StandardReportData(_dApplic.getDMediator().getCurrentDoc().getDM());
    String dataReport = "";
    switch (reportIndex){
      case 0 :
        dataReport = _srd.getActivitiesReport(mainFieldKey, otherFieldsKeys);
        break;
      case 1 :
        dataReport = _srd.getConflictsReport(mainFieldKey, otherFieldsKeys);
        break;
    }//end switch
    return dataReport;
  }//end method

}//end class