package dInterface.dTimeTable;

/**
 * <p>Title: Proto</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author unascribed
 * @version 1.0
 */

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.DefaultDesktopManager;
import javax.swing.JDesktopPane;

import dInterface.dUtil.DXTools;
import dInterface.DToolBar;
import dInterface.DApplication;
import dResources.DConst;
import dInternal.dTimeTable.TTStructure;

public class ManualImprovementDlg extends InternalFrameAdapter implements ActionListener{

  //private JInternalFrame _jif;
  private TTPanel _ttPanel;
  private TTStructure _ttStruct;
  private DToolBar _toolBar;


  private String FRAMENAME="Amélioration Manuelle";
  private int ZERO=0;
  /**
   * constructor
   */
  public ManualImprovementDlg(DApplication dApplic) {
    //super(new JFrame(), "Amélioration Manuelle", true);
    _ttStruct= dApplic.getDMediator().getCurrentDoc().getDM().getTTStructure();
    _toolBar= dApplic.getToolBar();
    this.createFrame(FRAMENAME,false,"ADM1111");
    //setVisible(true);
  }

  /**
   *
   * @param e
   */
  public void actionPerformed(ActionEvent e){
    String command = e.getActionCommand();
  }



  /**
   *
   * @param simple
   */
  private JInternalFrame  buildInternalFrame(boolean simple, String eventName){
    JInternalFrame jif;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameDim = new Dimension(600,600);
   //_documentName = title;
   jif = new JInternalFrame(eventName, true, true, true, true);
   jif.addInternalFrameListener(this);
   jif.setDefaultCloseOperation(jif.DO_NOTHING_ON_CLOSE);
   jif.setTitle(eventName);
    //_simpleView= simpleView;
    jif.addInternalFrameListener(this);
   jif.setMinimumSize(frameDim);
   jif.setPreferredSize(frameDim);

   if (simple)
     _ttPanel = new SimpleTTPanel(_ttStruct,_toolBar);
   else
     _ttPanel = new DetailedTTPanel(_ttStruct,_toolBar);
   _ttPanel.getPeriodPanel(1).setPanelColor(3);
   jif.getContentPane().add(_ttPanel.getPanel(), BorderLayout.CENTER);
   jif.pack();
   jif.setVisible(true);
   return jif;
  } // end buidDocument

  /**
   *
   * @param str
   * @return
   */
  private void createFrame(String str, boolean simple, String eventName ) {
    JFrame jFrame= new JFrame(FRAMENAME);
    JPanel panel = new JPanel(new BorderLayout(0,0));
    JDesktopPane jDesktopPane = new JDesktopPane();
    jDesktopPane.setOpaque(false);
    jDesktopPane.setDesktopManager(new DefaultDesktopManager());
    panel.add(jDesktopPane,BorderLayout.CENTER);
    jFrame.setLocation(ZERO,ZERO);
    jFrame.setContentPane(panel);
    jFrame.getContentPane().add(this.buildInternalFrame(simple,eventName), BorderLayout.CENTER);
    jFrame.pack();
    jFrame.setVisible(true);
    //return jFrame;
    } //end createUI


}