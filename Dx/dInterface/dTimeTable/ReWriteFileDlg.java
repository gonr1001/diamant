package dInterface.dTimeTable;

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

import dResources.DConst;
import dInterface.DApplication;

public class ReWriteFileDlg extends JDialog{
  private int _returnVal;
  private JOptionPane _jop;

  public ReWriteFileDlg(DApplication dApplic, String fileName) {
    _jop = new JOptionPane();
    String message = "Le fichier " + fileName + " exists dèja. Voulez-vous le reemplacer ?";
    _returnVal = _jop.showConfirmDialog(dApplic.getJFrame(), message);
/*
    if (_returnVal == jop.OK_OPTION){

    }
    if (_returnVal == jop.NO_OPTION){

    }
    if (_returnVal == jop.CANCEL_OPTION || _returnVal == jop.CLOSED_OPTION){

    }

    System.out.println("jop.OK_OPTION " + jop.OK_OPTION);

    System.out.println("jop.getValue() " + jop.getValue());
    */
  }

  public int getReturnedVal(){
    return _returnVal;
  }

  public JOptionPane getJOptionPane(){
    return _jop;
  }

}