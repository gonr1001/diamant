package dInterface.dAffectation;

/**
 * <p>Title: Diamant</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author unascribed
 * @version 1.0
 */

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Dimension;

import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import dAux.DoNothingCmd;
import dAux.DoNothingDlg;
import dInterface.DApplication;
import dInternal.dData.Activity;
import dInternal.dData.Resource;

public class EditActivityDlg extends JDialog implements ActionListener{

  Resource res = null;
  DApplication _dApplic = null;
  String _currentActivity;

  public EditActivityDlg(JDialog activityDialog, DApplication dApplic, String currentActivity) {
    super(activityDialog, "Éditer activité");
    setLocationRelativeTo(activityDialog);
    _dApplic = dApplic;
    _currentActivity = currentActivity;
    //Resource res = _dApplic.getDMediator().getCurrentDoc().getDM().getSetOfActivities().getResource(_currentActivity);
    //Activity act = (Activity)(res.getAttach());

    jbInit();

  }

  private void jbInit(){

    JPanel jp = new JPanel();
    jp.setPreferredSize(new Dimension(100,100));
    JTabbedPane tabbedPane = new JTabbedPane();
    /*for(int i = 0; i < activityEvents.size(); i++){
      tabbedPane.addTab((String)activityEvents.get(i), panel());
    }
*/
    //System.out.println(tabbedPane.getComponentAt(0).toString());

    getContentPane().add(tabbedPane);//add(new JLabel("JLabel"));
    //setSize(300,300);
    pack();
    setResizable(false);
    setVisible(true);
  }

  public void actionPerformed(ActionEvent e){

  }

  public JPanel panel(){
    String [] comboIni = {"1", "2", "3"};
    JPanel panel = new JPanel(new GridLayout(9,2));
    //panel.setPreferredSize(new Dimension(200,200));

    JLabel duration, day, hour, room, instructor1, instructor2, instructor3;
    JComboBox  cbDuration, cbDay, cbHour, cbRoom, cbInstructor1, cbInstructor2, cbInstructor3;
    JButton place, fix, apply, close;

    duration = new JLabel("Durée");
    day = new JLabel("Jour");
    hour = new JLabel("Heure");
    room = new JLabel("Local");
    instructor1 = new JLabel("Instructor1");
    instructor2 = new JLabel("Instructor2");
    instructor3 = new JLabel("Instructor3");
    cbDuration = new JComboBox(comboIni);
    cbDay = new JComboBox(comboIni);
    cbHour = new JComboBox(comboIni);
    cbRoom = new JComboBox(comboIni);
    cbInstructor1 = new JComboBox(comboIni);
    cbInstructor2 = new JComboBox(comboIni);
    cbInstructor3 = new JComboBox(comboIni);
    place = new JButton("Placer");
    fix = new JButton("Figer");
    apply = new JButton("Apliquer");
    close = new JButton("Fermer");
    panel.add(day);
    panel.add(cbDay);
    panel.add(duration);
    panel.add(cbDuration);
    panel.add(room);
    panel.add(cbRoom);
    panel.add(instructor1);
    panel.add(cbInstructor1);
    panel.add(instructor2);
    panel.add(cbInstructor2);
    panel.add(instructor3);
    panel.add(cbInstructor3);
    panel.add(place);
    panel.add(fix);
    panel.add(apply);
    panel.add(close);
    return panel;
  }
}