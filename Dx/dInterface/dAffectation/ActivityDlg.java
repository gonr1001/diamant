package dInterface.dAffectation;

/**
 * <p>Title: Diamant</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author unascribed
 * @version 1.0
 */

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;

import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import dAux.DoNothingCmd;
import dAux.DoNothingDlg;
import dInterface.DApplication;

public class ActivityDlg extends JDialog implements ActionListener{

  private DApplication _dApplic;
  private Vector noVisibleVec, visibleVec;
  private JPanel _leftJp, _rigthJp ;
  private BorderLayout _bly;
  private JList noVisibleList;
  private JList visibleList;


  public ActivityDlg(DApplication dApplic) {
     _dApplic = dApplic;
     //super(_dApplic.getJFrame(),"Act");
     //new DoNothingDlg(dApplic,"Nothing");
     jbInit();
  }

  public void actionPerformed(ActionEvent e){

  }

  /**
   *
   */

  public void jbInit(){
    _bly = new BorderLayout();
    //left panel
    noVisibleVec = new Vector();
    noVisibleVec.add("1");
    noVisibleList = new JList(noVisibleVec);
    noVisibleList.setPreferredSize(new Dimension(190,340));
    _leftJp = new JPanel(new BorderLayout());
    _leftJp.add(new JLabel("NVis"), BorderLayout.NORTH);
    _leftJp.add(noVisibleList, BorderLayout.SOUTH);

    //right panel
    visibleVec = new Vector();
    visibleVec.add("2");
    visibleList = new JList(visibleVec);
    visibleList.setPreferredSize(new Dimension(190,340));
    _rigthJp = new JPanel(new BorderLayout());
    _rigthJp.add(new JLabel("Vis"),BorderLayout.NORTH);
    _rigthJp.add(visibleList, BorderLayout.SOUTH);

    setSize(400,400);
    getContentPane().add(_leftJp, _bly.WEST);
    getContentPane().add(_rigthJp, _bly.EAST);
    setVisible(true);
  }


}