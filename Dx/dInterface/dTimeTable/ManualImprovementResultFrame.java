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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.DefaultDesktopManager;
import javax.swing.JDesktopPane;

import dInterface.dUtil.DXTools;
import dInterface.DToolBar;
import dInterface.DApplication;
import dInterface.DMenuBar;
import dResources.DConst;
import dInternal.dTimeTable.TTStructure;
import dInternal.dTimeTable.Period;
import dInternal.dUtil.DXToolsMethods;

public class ManualImprovementResultFrame extends JFrame implements ActionListener{

  //private JInternalFrame _jif;
  private TTPane _ttPane;
  private TTStructure _ttStruct;
  private DToolBar _toolBar;
  //private JFrame _jFrame;
  //private JDialog _jd;


  private String FRAMENAME="Amélioration Manuelle";
  private int INITIALPOSITION=25;
  /**
   * constructor
   */
  public ManualImprovementResultFrame(JDialog jd,TTStructure tts, DToolBar toolbar,
                                      String eventName, boolean simple) {
    super();
    _ttStruct= tts;
    _toolBar= toolbar;
    createFrame( eventName, simple);
    //jd.setLocationRelativeTo(this);
    //setLocationRelativeTo(jd);
  }

  /**
   *
   * @return
   */
  public TTStructure getTTS(){
    return _ttStruct;
  }

  /**
   *
   * @return
   */
  /*public JFrame getJFrame(){
    return _jFrame;
  }*/

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
  protected JInternalFrame  buildInternalFrame(boolean simple){
    JInternalFrame jif;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameDim = new Dimension(700,650);
   //_documentName = title;
   jif = new JInternalFrame(FRAMENAME, true, true, true, true);
   //jif.addInternalFrameListener(this);
   jif.setDefaultCloseOperation(jif.DO_NOTHING_ON_CLOSE);
   jif.setTitle(FRAMENAME);
    //_simpleView= simpleView;
    //jif.addInternalFrameListener(this);
   jif.setMinimumSize(frameDim);
   jif.setPreferredSize(frameDim);

   if (simple)
     _ttPane = new SimpleTTPane(_ttStruct,_toolBar,false, jif.getSize());
   else
     _ttPane = new DetailedTTPane(_ttStruct,_toolBar,false, jif.getSize());

   jif.getContentPane().add(_ttPane.getPane(), BorderLayout.CENTER);
   jif.pack();
   jif.setVisible(true);
   return jif;
  } // end buidDocument

  /**
   *
   * @param str
   * @return
   */
  protected void createFrame( String eventName, boolean simple) {
    setTitle(eventName);
    JPanel panel = new JPanel(new BorderLayout(0,0));
    setContentPane(panel);
    DMenuBar dMenuBar = new DMenuBar(this,1);
    setJMenuBar(dMenuBar);
    JDesktopPane jDesktopPane = new JDesktopPane();
    jDesktopPane.setOpaque(false);
    jDesktopPane.setDesktopManager(new DefaultDesktopManager());
    panel.add(jDesktopPane,BorderLayout.CENTER);
    //setContentPane(buildInternalFrame(simple));
    getContentPane().add(buildInternalFrame(simple));
    //getContentPane().add(jDesktopPane, BorderLayout.CENTER);
    //getContentPane().add(jDesktopPane, BorderLayout.CENTER);
    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    addWindowListener(new WindowAdapter() {
     public void windowClosing( WindowEvent e ) {
       dispose();
     }
      });
        pack();
        show();
      //doLayout();
      setLocation(INITIALPOSITION,INITIALPOSITION);
      setVisible(true);
    } //end createUI

    /**
     *
     */
    protected void setColorOfPanel(int dayIndex, int seqIndex, int perIndex, int duration, boolean isAssign){
      for (int i=0; i< ((JPanel)_ttPane.getViewport().getComponent(0)).getComponentCount(); i++){
        PeriodPanel perPanel= (PeriodPanel)((JPanel)_ttPane.getViewport().getComponent(0)).getComponent(i);
        Period period= _ttStruct.getCurrentCycle().getPeriodByIndex( perPanel.getPeriodRef()[0],
            perPanel.getPeriodRef()[1],perPanel.getPeriodRef()[2]);
        int[] ppKey={};
        if((dayIndex==perPanel.getPeriodRef()[0]) &&
           (seqIndex==perPanel.getPeriodRef()[1]) &&
           (perIndex<=perPanel.getPeriodRef()[2])&&
           (perPanel.getPeriodRef()[2]<= (perIndex+duration-1)) &&
           (isAssign)) {
          perPanel.setPanelColor(4);
        } else{
          if((period.getNbInstConflict()+period.getNbRoomConflict()+period.getNbStudConflict())!=0){
            perPanel.setPanelColor(3);
          }// end if((period.getNbInstConflict()+period.getNbRoomConfli
        }

      }// end for (int i=0; i< ((JPanel)_ttPanel.getViewport().
    }




}