/**
 *
 * Title: ReportsDlg $Revision: 1.15 $  $Date: 2005-04-19 20:37:44 $
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
 * @version $Revision: 1.15 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 *
 * Our convention is that: It's necessary to indicate explicitly
 * all Exceptions that a method can throw.
 * All Exceptions must be handled explicitly.
 */


/**
 * Description: ReportsDlg is a class used to
 *
 */

package dInterface.dData;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JTabbedPane;

import dConstants.DConst;
import dInterface.DApplication;
//import dInterface.ProgressBar;
import dInternal.dData.DStandardReportData;

public class ReportsDlg extends JDialog {// implements ActionListener{
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
  private DStandardReportData _srd;
  //private String _reportData;

  public ReportsDlg(DApplication dApplic) {
    super(dApplic.getJFrame(), DConst.REPORT_DLG_TITLE, true);
    _dApplic = dApplic;
    //ProgressBar pBar= new ProgressBar("Génération de rapports en cours",_dApplic);
    
    //_resources = new SetOfResources[1];
    _dApplic.setCursorWait();
    //pBar.execute();
    _srd = new DStandardReportData(_dApplic.getDMediator().getCurrentDoc().getDM());
    _dApplic.setCursorDefault();
    //System.out.println("Génération de rapports terminé");
    //pBar.close();
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
    //Dimension tabDim = new Dimension((int)tabbedPaneDim.getWidth()-10,
    //                                 (int)tabbedPaneDim.getHeight()-10);
    getContentPane().setLayout(new BorderLayout());
    setSize(dialogDim);
    setResizable(false);

    _tabbedPane = new JTabbedPane();
    _tabbedPane.setPreferredSize(tabbedPaneDim);

    _tabbedPane.addTab(_tabsNames[0], new FullReport(this, _dApplic, tabbedPaneDim));
    _tabbedPane.addTab(_tabsNames[1], new ConflictReport(this, _dApplic, tabbedPaneDim));
    if (_dApplic.getDMediator().getCurrentDoc().getDM().getImportDone()) {
      _tabbedPane.addTab(_tabsNames[2], new ImportReport(this, _dApplic, tabbedPaneDim)) ;
    }
    if (_dApplic.getDMediator().getCurrentDoc().getDM().getMergeDone()) {
        _tabbedPane.addTab(_tabsNames[2], new MergeReport(this, _dApplic, tabbedPaneDim)) ;
      }
    getContentPane().add(_tabbedPane, BorderLayout.CENTER);
  }

  public DStandardReportData getStandardReportData() {
    return _srd;
  }

  //public void actionPerformed(ActionEvent e){
  //}

} /* end ReportsDlg */