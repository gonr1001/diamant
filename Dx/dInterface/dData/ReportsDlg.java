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

public class ReportsDlg extends JDialog implements ActionListener{
  /* ADJUST_HEIGHT is needed to ajdust the screenSize
  * minus the barSize (the value is a guess) at the bottom */
  private final static int ADJUST_HEIGHT = 100;
  /* ADJUST_WIDTH is needed to ajdust the screenSize
  * minus border pixels (the value is a guess) at each side of the screen */
  private final static int ADJUST_WIDTH = 24;

  private String[] _tabsNames = {DConst.REPORT_DLG_TAB1,
                                 DConst.REPORT_DLG_TAB2,
                                 DConst.REPORT_DLG_TAB3};
  private DApplication _dApplic;
  private JTabbedPane _tabbedPane;
  private StandardReportData _srd;
  private String _reportData;

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

    _tabbedPane.addTab(_tabsNames[0], new FullReport(this, _dApplic, tabbedPaneDim));
    _tabbedPane.addTab(_tabsNames[1], new ConflictReport(this, _dApplic, tabbedPaneDim));
    _tabbedPane.addTab(_tabsNames[2], new ImportReport(this, _dApplic, tabbedPaneDim)) ;

    getContentPane().add(_tabbedPane, BorderLayout.CENTER);


  }

  public StandardReportData getStandardReportData() {
    return _srd;
  }
  public void actionPerformed(ActionEvent e){
  }

} /* end ReportsDlg */