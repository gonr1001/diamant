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
import dResources.DConst;
import dInternal.dTimeTable.TTStructure;
import dInternal.dTimeTable.Period;
import dInternal.dUtil.DXToolsMethods;

public class ManualImprovementResultFrame extends InternalFrameAdapter implements ActionListener{

  //private JInternalFrame _jif;
  private TTPanel _ttPanel;
  private TTStructure _ttStruct;
  private DToolBar _toolBar;
  //private JFrame _jFrame;


  private String FRAMENAME="Amélioration Manuelle";
  private int INITIALPOSITION=25;
  /**
   * constructor
   */
  public ManualImprovementResultFrame(TTStructure ttStruct,DToolBar toolBar) {
    //super(new JFrame(), "Amélioration Manuelle", true);
    _ttStruct= ttStruct;
    _toolBar= toolBar;
    //this.createFrame(true,"ADM1111");
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
    Dimension frameDim = new Dimension(700,650);
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
  protected JFrame createFrame(boolean simple, String eventName ) {
    JFrame jFrame= new JFrame(FRAMENAME);
    jFrame.setDefaultCloseOperation(jFrame.DO_NOTHING_ON_CLOSE);
   /*jFrame.addWindowListener(new WindowAdapter() {
     public void windowClosing( WindowEvent e ) {
       closeApplic(e);
     }
      });*/
    JPanel panel = new JPanel(new BorderLayout(0,0));
    JDesktopPane jDesktopPane = new JDesktopPane();
    jDesktopPane.setOpaque(false);
    jDesktopPane.setDesktopManager(new DefaultDesktopManager());
    panel.add(jDesktopPane,BorderLayout.CENTER);
    jFrame.setLocation(INITIALPOSITION,INITIALPOSITION);
    jFrame.setContentPane(panel);
    jFrame.getContentPane().add(buildInternalFrame(simple,eventName), BorderLayout.CENTER);
    jFrame.pack();
    jFrame.setVisible(true);
    jFrame.setEnabled(true);
    return jFrame;
    } //end createUI

    /**
     *
     */
    protected void setColorOfPanel(int dayIndex, int seqIndex, int perIndex, int duration){
      for (int i=0; i< ((JPanel)_ttPanel.getViewport().getComponent(0)).getComponentCount(); i++){
        PeriodPanel perPanel= (PeriodPanel)((JPanel)_ttPanel.getViewport().getComponent(0)).getComponent(i);
        Period period= _ttStruct.getCurrentCycle().getPeriodByIndex( perPanel.getPeriodRef()[0],
            perPanel.getPeriodRef()[1],perPanel.getPeriodRef()[2]);
        int[] ppKey={};
        if((dayIndex==perPanel.getPeriodRef()[0]) && (seqIndex==perPanel.getPeriodRef()[1]) && (perIndex<=perPanel.getPeriodRef()[2])&& (perPanel.getPeriodRef()[2]<= (perIndex+duration-1))){
          perPanel.setPanelColor(4);
        } else{
          if((period.getNbInstConflict()+period.getNbRoomConflict()+period.getNbStudConflict())!=0){
            perPanel.setPanelColor(3);
          }// end if((period.getNbInstConflict()+period.getNbRoomConfli
        }

      }// end for (int i=0; i< ((JPanel)_ttPanel.getViewport().
    }




}