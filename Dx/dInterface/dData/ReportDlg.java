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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTabbedPane;
import javax.swing.text.JTextComponent;


import dInterface.DApplication;
import dInterface.dUtil.DXTools;

import dInternal.dData.SetOfResources;
import dInternal.dData.StandardReportData;

import dResources.DConst;

public class ReportDlg extends JDialog implements ActionListener{

  private DApplication _dApplic = null;
  private JDialog _jd = this;
  private JPanel _buttonsPanel;
  private JTabbedPane _tabbedPane;
  private StandardReportData _srd;
  private String[] _buttonsNames = {DConst.BUT_OK, "OPTIONS", DConst.BUT_CANCEL};

  public ReportDlg(DApplication dApplic) {
    super(dApplic.getJFrame(), "Rapports", true);
    _dApplic = dApplic;
    jbInit();
    setLocationRelativeTo(dApplic.getJFrame());
    setVisible(true);
  }//end constructor

  private void jbInit(){
    Dimension dialogDim = new Dimension(600,600);
    Dimension tabbedPaneDim = new Dimension((int)dialogDim.getWidth()-50, (int)dialogDim.getHeight()-60);
    Dimension tabDim = new Dimension((int)dialogDim.getWidth()-50, (int)dialogDim.getHeight()-60);
    getContentPane().setLayout(new BorderLayout());
    setSize(dialogDim);
    setResizable(true);
    //the tabbedPane
    _tabbedPane = new JTabbedPane();
    _tabbedPane.setPreferredSize(tabbedPaneDim);
    _tabbedPane.addTab("Activities Report", createTabPanel(tabbedPaneDim, ""));
    _tabbedPane.addTab("Students Report", createTabPanel(tabbedPaneDim, ""));
    //the buttons panel
    _buttonsPanel = DXTools.buttonsPanel(this, _buttonsNames);
    //_buttonsPanel = DXTools.buttonsPanel2(this, _buttonsNames, tabbedPaneDim);
    getContentPane().add(_tabbedPane, BorderLayout.NORTH);
    getContentPane().add(_buttonsPanel, BorderLayout.SOUTH);
  }

  /**
   * Builds a panel contained into a tab of a tabbedPanel. This panel contains
   * a JTextArea for displaying a String
   * @param dim The panel dimension
   * @param reportData The String to be displayed in this panel
   * @return
   */
  private JPanel createTabPanel(Dimension dim, String reportData){
    JTextArea jta = new JTextArea(reportData);
    JPanel panel = new JPanel(new BorderLayout());
    panel.setPreferredSize(dim);
    JScrollPane scrollPane = new JScrollPane();
    scrollPane.getViewport().setLayout(new BorderLayout());
    scrollPane.getViewport().add(jta, BorderLayout.CENTER);
    panel.add(scrollPane, BorderLayout.CENTER);
    return panel;
  }

  /**
   *
   * @param mainFieldKey
   * @param otherFieldsKeys
   */
/*
  public void setReport(int mainFieldKey, int[] otherFieldsKeys){
    if (_tabbedPane.getSelectedIndex() == -1)
      return;
    String reportData = getReportData(_tabbedPane.getSelectedIndex(), mainFieldKey, otherFieldsKeys);
    JScrollPane scrollPanel = (JScrollPane)((JPanel)_tabbedPane.getSelectedComponent()).getComponent(0);
    JTextArea jta = (JTextArea)scrollPanel.getViewport().getComponent(0);
    jta.setFont(DConst.JLISTS_FONT);
    jta.setText(reportData);
  }
  */

  public void setReport(SetOfResources selectedResources){
    if (_tabbedPane.getSelectedIndex() == -1)
      return;
    if (selectedResources.size() <= 0)
      return;
    int mainFieldKey = 0;
    int[] otherFieldsKeys = new int[selectedResources.size()-1];
    String[] fieldsNames = new String[selectedResources.size()];
    int[] fieldLengths = new int[selectedResources.size()];
    for (int i = 0; i < selectedResources.size(); i++){
      if (i == 0)
        mainFieldKey = (int) selectedResources.getResourceAt(i).getKey()-1;
      else
        otherFieldsKeys[i-1] = (int) selectedResources.getResourceAt(i).getKey()-1;
      fieldsNames[i] = selectedResources.getResourceAt(i).getID();
      fieldLengths[i] = Integer.parseInt(selectedResources.getResourceAt(i).getAttach().getAssociateLength());
    }//end for
    String reportData = getReportData(_tabbedPane.getSelectedIndex(), mainFieldKey, otherFieldsKeys);
    JScrollPane scrollPanel = (JScrollPane)((JPanel)_tabbedPane.getSelectedComponent()).getComponent(0);
    JTextArea jta = (JTextArea)scrollPanel.getViewport().getComponent(0);
    jta.setFont(DConst.JLISTS_FONT);
    jta.setText(reportData);
    buildReport(fieldsNames, fieldLengths, reportData);
  }

  private void buildReport(String[] fieldsNames, int[] fieldsLengths, String reportData){
    StringTokenizer strLines = new StringTokenizer(reportData, DConst.CR_LF);
    StringTokenizer strFields;
    String fields;
    String currentField;
    String resultLine = "";
    String blanks = "                               ";
    int strLinesLength, strFieldsLength;
    JScrollPane scrollPanel = (JScrollPane)((JPanel)_tabbedPane.getSelectedComponent()).getComponent(0);
    JTextArea jta = (JTextArea)scrollPanel.getViewport().getComponent(0);
    jta.setFont(DConst.JLISTS_FONT);
    jta.setText("");
    for (int k = 0; k < fieldsNames.length; k++){
      currentField = fieldsNames[k] + blanks;
      currentField = currentField.substring(0, fieldsLengths[k]);
      resultLine = resultLine + currentField;
    }
    resultLine = resultLine + DConst.CR_LF;
    jta.setText(resultLine);
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
        resultLine = resultLine + currentField;
      }//end internal for
      resultLine = resultLine + DConst.CR_LF;
      jta.append(resultLine);
    }//end external for
    //System.out.println("str.nextToken() "+str.nextToken());
  }

  public void actionPerformed(ActionEvent e){
    String command = e.getActionCommand();
    //If buttons OPTIONS
    if (command.equals("OPTIONS"))
        new ReportOptionsDlg(_dApplic, _jd, 0);
  }

  private String getReportData(int reportIndex, int mainFieldKey, int[] otherFieldsKeys){
    _srd = new StandardReportData(_dApplic.getDMediator().getCurrentDoc().getDM());
    String dataReport = "";
    switch (reportIndex){
      case 0 :
        dataReport = _srd.getActivitiesReport(mainFieldKey, otherFieldsKeys);
        break;
      case 1 :
        dataReport = _srd.getStudentsReport(mainFieldKey, otherFieldsKeys);
        break;
    }
    return dataReport;
  }


}//end class