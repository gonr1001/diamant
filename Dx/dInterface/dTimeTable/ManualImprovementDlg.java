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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Dimension;

import dInterface.dUtil.DXTools;
import dResources.DConst;

public class ManualImprovementDlg extends JDialog implements ActionListener{

  boolean _simpleView;

  /**
   * constructor
   */
  public ManualImprovementDlg(boolean simpleView) {
    super(new JFrame(), "Amélioration Manuelle", true);
    _simpleView= simpleView;
    jbInit();
    setVisible(true);
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
   */
  private void jbInit(){
    Dimension dialogDim = new Dimension(600,600);
    //Dimension tabbedPaneDim = new Dimension((int)dialogDim.getWidth()-50, (int)dialogDim.getHeight()-60);
    //Dimension tabDim = new Dimension((int)dialogDim.getWidth()-50, (int)dialogDim.getHeight()-60);
    getContentPane().setLayout(new BorderLayout());
    setSize(dialogDim);
    setResizable(true);
    String[] buttonsNames = {DConst.BUT_OK};
    JPanel buttonsPane = DXTools.buttonsPanel(this, buttonsNames);
    //_buttonsPanel = DXTools.buttonsPanel2(this, _buttonsNames, tabbedPaneDim);
    //getContentPane().add(_tabbedPane, BorderLayout.NORTH);
    getContentPane().add(buttonsPane, BorderLayout.SOUTH);
  }
}