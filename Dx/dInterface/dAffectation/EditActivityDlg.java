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
import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Dimension;

import java.util.Vector;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import dAux.DoNothingCmd;
import dAux.DoNothingDlg;
import dResources.DConst;
import dInterface.DApplication;
import dInternal.dData.*;
import dInternal.dData.Resource;

public class EditActivityDlg extends JDialog implements ActionListener{

  private DApplication _dApplic;
  private String _currentActivity;
  private String _DURATION= "Durée:";
  private String _DAY= "Jour:";
  private String _HOUR="Heure de début:";
  private String _INSTRUCTOR= "Enseignant:";
  private String _ROOM= "Local:";
  private Vector _unities= new Vector(1);

  JPanel _centerPanel;
  JPanel _bottomPanel;
  JButton _jButtonApply, _jButtonClose;

  /**
   * Constructor
   * @param activityDialog The parent dialog of this dialog
   * @param dApplic The application
   * @param currentActivity The ativiti choiced in the activityDialog
   */
  public EditActivityDlg(JDialog dialog, DApplication dApplic, String currentActivity) {
    super(dialog, "Éditer activité");
    setLocationRelativeTo(dialog);
    _dApplic = dApplic;
    _currentActivity = currentActivity;
    jbInit();
  }

  /**
   * Initialize the dialog
   */
  private void jbInit(){
    _bottomPanel = new JPanel();
    _jButtonApply = new JButton( DConst.BUT_APPLY );
    _jButtonApply.setPreferredSize(new Dimension(80, 22));
    _jButtonApply.addActionListener(this);
    _jButtonApply.setEnabled(false);
    _jButtonClose = new JButton( DConst.BUT_CLOSE );
    _jButtonClose.setPreferredSize(new Dimension(75, 22));
    _jButtonClose.addActionListener(this);
    _bottomPanel.add(_jButtonApply);
    _bottomPanel.add(_jButtonClose);
    getContentPane().add(_bottomPanel, BorderLayout.SOUTH);
    JTabbedPane tabbedPane = new JTabbedPane();
    tabbedPane.addTab(_currentActivity, CreateUnityPanel());
    getContentPane().add(tabbedPane, BorderLayout.CENTER);
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension unitySize = new Dimension(280, 260 );
    setBounds((screenSize.width - unitySize.width) / 2, (screenSize.height -
        unitySize.height) / 2, unitySize.width, unitySize.height);
    setResizable(false);
    setVisible(true);
  }

  public void actionPerformed(ActionEvent e){

  }

  /**
   * Builds a panel to be placed in a tab of the tabbedPane
   * @return a JPanel to be placed in a tab of the tabbedPane
   */
  public JPanel CreateUnityPanel(){
    String [] comboIni = {"08:30", "19:00", "3"};
    JPanel centerPanel = new JPanel(new GridLayout(0,1));
    JPanel panel = new JPanel();
    JLabel duration, day, hour, room, instructor;
    JComboBox  cbDuration, cbDay, cbHour, cbRoom, cbInstructor;
    JButton place, fix;
    duration = new JLabel(_DURATION);
    day = new JLabel(_DAY);
    hour = new JLabel(_HOUR);
    room = new JLabel(_ROOM);
    instructor = new JLabel(_INSTRUCTOR);
    cbDuration = new JComboBox(comboIni);
    cbDay = new JComboBox(comboIni);
    cbHour = new JComboBox(comboIni);
    cbRoom = new JComboBox(comboIni);
    cbInstructor = new JComboBox(comboIni);
    place = new JButton(DConst.BUT_PLACE);
    fix = new JButton(DConst.BUT_FIGE);
    // duration
    panel.add(duration);
    panel.add(cbDuration);
    centerPanel.add(panel);
    // day and hour
    panel = new JPanel();
    panel.add(day);
    panel.add(cbDay);
    panel.add(hour);
    panel.add(cbHour);
    centerPanel.add(panel);
    // room
    panel = new JPanel();
    panel.add(room);
    panel.add(cbRoom);
    centerPanel.add(panel);
    // instructor
    panel = new JPanel();
    panel.add(instructor);
    panel.add(cbInstructor);
    centerPanel.add(panel);
    // bottom
    JPanel buttomPanel = new JPanel();
    buttomPanel.add(place);
    buttomPanel.add(fix);
    centerPanel.add(panel);
    centerPanel.add(buttomPanel);
    return centerPanel;
  }
}