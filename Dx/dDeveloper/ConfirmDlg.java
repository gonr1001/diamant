package dAux;

/**
 * <p>Title: Diamant</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author rgr, ysyam, alexander
 * @version 1.0
 */

import javax.swing.JDialog;
import javax.swing.JOptionPane;


import dInterface.DApplication;

public class ConfirmDlg extends JDialog{
  //private int _returnVal;
  public static final int OK_OPTION=0;
  public static final int NO_OPTION=1;
  public static final int CANCEL_OPTION=2;

  public ConfirmDlg() {

  }

  /**
   *
   * */
  public static int showMessage(DApplication dApplic, String message){
    return (new JOptionPane()).showConfirmDialog(dApplic.getJFrame(), message);
  }




}