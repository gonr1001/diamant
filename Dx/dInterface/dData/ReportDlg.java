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
import dInterface.ProgressBar;
import dInterface.dTimeTable.SaveAsDlg;
import dInterface.dUtil.DXTools;

import dInternal.dData.Resource;
import dInternal.dData.SetOfResources;
import dInternal.dData.StandardReportData;
import dInternal.dUtil.DXValue;

import dResources.DConst;

public class ReportDlg extends JDialog implements ActionListener, ChangeListener{
  /* ADJUST_HEIGHT is needed to ajdust the screenSize
  * minus the barSize (the value is a guess) at the bottom */
  private final static int ADJUST_HEIGHT = 100;
  /* ADJUST_WIDTH is needed to ajdust the screenSize
  * minus border pixels (the value is a guess) at each side of the screen */
  private final static int ADJUST_WIDTH = 24;
  //private String[] _threeButtonsNames = {DConst.BUT_SAVE_AS, DConst.BUT_OPTIONS, DConst.BUT_CLOSE};
  //private String[] _TwoButtonsNames = {DConst.BUT_SAVE_AS, DConst.BUT_CLOSE};
  private String[] _buttonsNames = {DConst.BUT_SAVE_AS, DConst.BUT_OPTIONS, DConst.BUT_CLOSE};
  private String[] _tabsNames = {DConst.REPORT_DLG_TAB1, DConst.REPORT_DLG_TAB2, DConst.REPORT_DLG_TAB3};

  //private String[] _tabsNames = {DConst.REPORT_DLG_TAB1, DConst.REPORT_DLG_TAB2, DConst.REPORT_DLG_TAB3};
  private DApplication _dApplic = null;
  private JDialog _jd = this;
  private JTabbedPane _tabbedPane;
  private StandardReportData _srd;
  private String _reportData;

  private SetOfResources[] _resources;

  public ReportDlg(DApplication dApplic) {
    super(dApplic.getJFrame(), DConst.REPORT_DLG_TITLE, true);
    _dApplic = dApplic;
    //ProgressBar pBar= new ProgressBar("Génération de rapports en cours",_dApplic);
    //pBar.execute();
    _dApplic.getDMediator().getCurrentDoc().setCursor(Cursor.WAIT_CURSOR,_dApplic.getJFrame());
    _srd = new StandardReportData(_dApplic.getDMediator().getCurrentDoc().getDM());
    _dApplic.getDMediator().getCurrentDoc().setCursor(Cursor.DEFAULT_CURSOR,_dApplic.getJFrame());
    System.out.println("Génération de rapports terminé");
    initReportDlg();
    _resources = new SetOfResources[_tabbedPane.getComponentCount()];
    setLocationRelativeTo(dApplic.getJFrame());
    setVisible(true);

  }//end constructor

  /**
   * Dialog initialization
   */
  private void initReportDlg(){

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension dialogDim = new Dimension(new Dimension(screenSize.width - ADJUST_WIDTH,
                                screenSize.height - ADJUST_HEIGHT));
    Dimension tabbedPaneDim = new Dimension((int)dialogDim.getWidth()-10, (int)dialogDim.getHeight()-60);
    Dimension tabDim = new Dimension((int)tabbedPaneDim.getWidth()-10, (int)tabbedPaneDim.getHeight()-10);
    getContentPane().setLayout(new BorderLayout());
    setSize(dialogDim);
    setResizable(false);
    //the tabbedPane
    _tabbedPane = new JTabbedPane();
    _tabbedPane.setPreferredSize(tabbedPaneDim);
    _tabbedPane.addChangeListener(this);
    for(int i = 0; i < _tabsNames.length; i++)
       _tabbedPane.addTab(_tabsNames[i], createTabPanel(tabbedPaneDim, DConst.REPORT_DLG_TAB_MESS));

    _tabbedPane.addChangeListener(this);
    getContentPane().add(_tabbedPane, BorderLayout.CENTER);
    //getContentPane().add(buttonsPanel(this, _buttonsNames), BorderLayout.SOUTH);
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
 // private JPanel createTabPanel(ReportDlg rd,Dimension dim, String message){
    JTextArea jta = new JTextArea(message);
    JPanel panel = new JPanel(new BorderLayout());
    panel.setPreferredSize(dim);
    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setPreferredSize(new Dimension((int)dim.getWidth(), (int)dim.getHeight()-20));
    scrollPane.getViewport().setView(jta);
    panel.add(scrollPane);
    return panel;
  }
/*
  private JPanel createTabPanelTwo(Dimension dim, String message){
    JTextArea jta = new JTextArea(message);
    JPanel panel = new JPanel();
    panel.setPreferredSize(dim);
    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setPreferredSize(new Dimension((int)dim.getWidth(), (int)dim.getHeight()-20));
    scrollPane.getViewport().setView(jta);
    panel.add(scrollPane);
    return panel;
  }*/
/*  private JPanel createTabPanelThree(Dimension dim, String message){
  JTextArea jta = new JTextArea(message);
  JPanel panel = new JPanel();
  panel.setPreferredSize(dim);
  JScrollPane scrollPane = new JScrollPane();
  scrollPane.setPreferredSize(new Dimension((int)dim.getWidth(), (int)dim.getHeight()-20));
  scrollPane.getViewport().setView(jta);
  panel.add(scrollPane);
  return panel;
  }
*/
  /**
   * build import report
   */
  public void setImportReport(){
    JScrollPane scrollPanel = (JScrollPane)((JPanel)_tabbedPane.getSelectedComponent()).getComponent(0);
    JTextArea jta = (JTextArea)scrollPanel.getViewport().getComponent(0);
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
  }

  /**
   * build import report
   */
  public void setConflictReport(){
    JScrollPane scrollPanel = (JScrollPane)((JPanel)_tabbedPane.getSelectedComponent()).getComponent(0);
    JTextArea jta = (JTextArea)scrollPanel.getViewport().getComponent(0);
    jta.setFont(DConst.JLISTS_FONT);
    jta.setText("Rapport de conflits");
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
    String[][][] subFields = new String[selectedResources.size()][][];
    Resource res;
    for (int i = 0; i < selectedResources.size(); i++){
      res = selectedResources.getResourceAt(i);
      if (i == 0)
        mainFieldKey = (int)((DXValue)res.getAttach()).getIntValue();
      else
        otherFieldsKeys[i-1] = (int)((DXValue)res.getAttach()).getIntValue();
      fieldsNames[i] = selectedResources.getResourceAt(i).getID();
      fieldLengths[i] = Integer.parseInt(((DXValue)selectedResources.getResourceAt(i).getAttach()).getStringValue());
      subFields[i] = (String[][])(((DXValue)selectedResources.getResourceAt(i).getAttach()).getObjectValue());
      ((DXValue)res.getAttach()).setBooleanValue(true);
      _resources[_tabbedPane.getSelectedIndex()] = selectedResources;
    }//end for
    _reportData = getReportData(_tabbedPane.getSelectedIndex(), mainFieldKey, otherFieldsKeys);
    JScrollPane scrollPanel = (JScrollPane)((JPanel)_tabbedPane.getSelectedComponent()).getComponent(0);
    JTextArea jta = (JTextArea)scrollPanel.getViewport().getComponent(0);
    jta.setFont(DConst.JLISTS_FONT);
    jta.setText(_reportData);
    //buildReport(fieldsNames, fieldLengths, reportData);
    buildReport(fieldsNames, fieldLengths, subFields, _reportData);
  }//end method

  /**
   * Builds a report with the format defined by the parameters
   * @param fieldsNames The first line of the report
   * @param fieldsLengths The spaces allowed for the fields
   * @param reportData The data report
   */
  private void buildReport(String[] fieldsNames, int[] fieldsLengths, String[][][] subFields, String reportData){
    StringTokenizer strLines = new StringTokenizer(reportData, DConst.CR_LF);
    StringTokenizer strFields;
    String fields;
    String currentField;
    String resultLine = "";
    String blanks = "                                                                          ";
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
        currentField = currentField + blanks;
        currentField = currentField.substring(0, fieldsLengths[j]);
        currentField = currentField + "|  ";
        resultLine = resultLine + currentField;
      }//end internal for
      resultLine = resultLine + DConst.CR_LF;
      jta.append(resultLine);
    }//end external for
    jta.setCaretPosition(0);
  }

  /**
   *
   * @param ce
   */
  public void stateChanged( ChangeEvent ce) {
    System.out.println(((JTabbedPane)ce.getSource()).getSelectedIndex());//debug
     if(((JTabbedPane)ce.getSource()).getSelectedIndex()==2 ||
         ((JTabbedPane)ce.getSource()).getSelectedIndex()==1){
       String [] strArray = {DConst.BUT_OPTIONS};
       //buttonDisable((JPanel)  ((JPanel)this.getContentPane().getComponent(1)), strArray);
       ((JPanel)getContentPane().getComponent(1)).getComponent(1).setEnabled(false);
       if(((JTabbedPane)ce.getSource()).getSelectedIndex()==2)
         setImportReport();
       else
         setConflictReport();
     }else{// else if(((JTabbedPane)ce.getSource()).getSelectedIndex()==2)
        //System.out.println("gcCount" +((JPanel)this.getContentPane()).getComponentCount());//debug
        if (((JPanel)this.getContentPane()).getComponentCount() > 0)
           //buttonDisable((JPanel)  ((JPanel)this.getContentPane().getComponent(1)), null);
        ((JPanel)getContentPane().getComponent(1)).getComponent(1).setEnabled(true);
     }// end if(((JTabbedPane)ce.getSource()).getSelectedIndex()==2)
   }

   /**
    *
    * @param e
    */
  public void actionPerformed(ActionEvent e){
    String command = e.getActionCommand();
    //if "Option" button
    if (e.getSource().equals(((JPanel)this.getContentPane().getComponent(1)).getComponent(1)))
        new ReportOptionsDlg(_dApplic, _jd, _resources[_tabbedPane.getSelectedIndex()], _tabbedPane.getSelectedIndex());
    //if "Close" button
    if (e.getSource().equals(((JPanel)this.getContentPane().getComponent(1)).getComponent(2)))
      dispose();
    //if "Save as" button
    if (e.getSource().equals(((JPanel)this.getContentPane().getComponent(1)).getComponent(0))){
      if (_reportData != null){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE-MMMM-dd-yyyy:kk:mm");
        JScrollPane scrollPanel = (JScrollPane)((JPanel)_tabbedPane.getSelectedComponent()).getComponent(0);
        JTextArea jta = (JTextArea)scrollPanel.getViewport().getComponent(0);
        String data = "***** " + DConst.REPORT + " " + DConst.TO_LEFT + _tabbedPane.getTitleAt(_tabbedPane.getSelectedIndex()) + DConst.TO_RIGHT + " ";
        data = data + DConst.REPORT_PRODUCED_AT + " " + sdf.format(date) + " *****" + DConst.CR_LF + DConst.CR_LF;
        data = data + jta.getText();
        new SaveAsDlg(_dApplic, data);
      }//end internal if
    }//end if (e.getSource().equals(((JPanel)this.getContentPane().getComponent(1)).getComponent(0)))
  }//end method


  /**
   * Gets the data report with regard to the type of required report
   * @param reportIndex The index of the required report
   * @param mainFieldKey the firs index to build the report
   * @param otherFieldsKeys the other index to build the report
   * @return data report
   */
  private String getReportData(int reportIndex, int mainFieldKey, int[] otherFieldsKeys){
    //_srd = new StandardReportData(_dApplic.getDMediator().getCurrentDoc().getDM());
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
  /**
   * Creates a panel of buttons to be placed at the bottom of a Dialog.
   * This method adds the ActionListener for each button
   * @param parentDialog The dialog where this panel is placed
   * @param buttonsNames An array of names of buttons
   * @return panel
   */
  private JPanel buttonsPanel(ActionListener parentDialog, String [] buttonsNames){
    JPanel panel = new JPanel();
    JButton button;
    for(int i = 0; i< buttonsNames.length; i++){
      button = new JButton(buttonsNames[i]);
      button.setActionCommand(buttonsNames[i]);
      button.addActionListener(parentDialog);
      panel.add(button) ;
    }
    return panel;
 }//end method

 private void buttonDisable(JPanel panel, String [] strArray){
   String str= "";
   for (int i = 0; i < panel.getComponentCount(); i++) {
     ((JButton)panel.getComponent(i)).setEnabled(true);
   }
   if (strArray != null) {
     for (int i = 0; i < strArray.length; i++) {
       str = strArray[i];
       for(int j = 0 ; j < panel.getComponentCount(); j++) {
         if (((JButton)panel.getComponent(j)).getActionCommand().equals(str))
            ((JButton)panel.getComponent(j)).setEnabled(false);
       }
     }
   }
 }//end method
}//end class