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

public class ReportsDlg extends JDialog implements ActionListener, ChangeListener{
  /* ADJUST_HEIGHT is needed to ajdust the screenSize
  * minus the barSize (the value is a guess) at the bottom */
  private final static int ADJUST_HEIGHT = 100;
  /* ADJUST_WIDTH is needed to ajdust the screenSize
  * minus border pixels (the value is a guess) at each side of the screen */
  private final static int ADJUST_WIDTH = 24;

  private String[] _tabsNames = {DConst.REPORT_DLG_TAB1, DConst.REPORT_DLG_TAB2, DConst.REPORT_DLG_TAB3};
  private DApplication _dApplic = null;
  private JDialog _jd = this;
  private JTabbedPane _tabbedPane;
  private StandardReportData _srd;
  private String _reportData;

  //private SetOfResources[] _resources;

  public ReportsDlg(DApplication dApplic) {
    super(dApplic.getJFrame(), DConst.REPORT_DLG_TITLE, true);
    _dApplic = dApplic;
    //ProgressBar pBar= new ProgressBar("Génération de rapports en cours",_dApplic);
    //pBar.execute();
    //_resources = new SetOfResources[1];
    _dApplic.getDMediator().getCurrentDoc().setCursor(Cursor.WAIT_CURSOR,_dApplic.getJFrame());
    _srd = new StandardReportData(_dApplic.getDMediator().getCurrentDoc().getDM());
    _dApplic.getDMediator().getCurrentDoc().setCursor(Cursor.DEFAULT_CURSOR,_dApplic.getJFrame());
    //System.out.println("Génération de rapports terminé");
    initReportDlg();
    //_resources = new SetOfResources[_tabbedPane.getComponentCount()];
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
  Dimension tabbedPaneDim = new Dimension((int)dialogDim.getWidth()-10,
                                          (int)dialogDim.getHeight()-60);
  Dimension tabDim = new Dimension((int)tabbedPaneDim.getWidth()-10,
                                   (int)tabbedPaneDim.getHeight()-10);
  getContentPane().setLayout(new BorderLayout());
  setSize(dialogDim);
  setResizable(false);
  //the tabbedPane
  _tabbedPane = new JTabbedPane();
  _tabbedPane.setPreferredSize(tabbedPaneDim);
  _tabbedPane.addChangeListener(this);
  //for(int i = 0; i < _tabsNames.length; i++)
  _tabbedPane.addTab(_tabsNames[0], new FullReport(this, _dApplic,tabbedPaneDim));
     _tabbedPane.addTab(_tabsNames[1], new ConflictReport(this, _dApplic,tabbedPaneDim));
     _tabbedPane.addTab(_tabsNames[2], new ImportReport(this, _dApplic, tabbedPaneDim)) ;
                        //createTabPanel(tabbedPaneDim, DConst.REPORT_DLG_TAB_MESS));

  _tabbedPane.addChangeListener(this);
  getContentPane().add(_tabbedPane, BorderLayout.CENTER);
  //getContentPane().add(DXTools.buttonsPanel(this, _buttonsNames), BorderLayout.SOUTH);

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
   private void fillVector(Vector v) {

     v.add(DConst.R_ACTIVITY_NAME);
     v.add(DConst.R_TYPE_NAME);
     v.add(DConst.R_SECTION_NAME);
     v.add(DConst.R_UNITY_NAME);
     v.add(DConst.R_DURATION);
     v.add(DConst.R_DAY_NUMBER);
     v.add(DConst.R_DAY_NAME);
     v.add(DConst.R_ACTIVITY_BEGIN_HOUR);
     v.add(DConst.R_ACTIVITY_END_HOUR);
     v.add(DConst.R_INSTRUCTOR_NAME);
     v.add(DConst.R_ROOM_NAME);

   }

   public StandardReportData getStandardReportData() {
     return _srd;
   }
   public void actionPerformed(ActionEvent e){
   }
   public void stateChanged(ChangeEvent ce) {
   }
/*   Vector v = new Vector();
fillVector(v);

    new ReportOptionsDlg(_dApplic, this, v, 4);*/
}