package dInterface.dAffectation;

/**
 * <p>Title: Diamant</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author unascribed
 * @version 1.0
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

import dInterface.DApplication;

public class ActivityDlg extends JDialog implements ActionListener{

  private DApplication _dApplic;


  public ActivityDlg(DApplication dApplic) {
    super(dApplic.getJFrame(),"Message");
     _dApplic = dApplic;
  }

  public void actionPerformed(ActionEvent e){

  }


}