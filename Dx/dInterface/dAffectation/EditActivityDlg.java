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
import dInternal.dUtil.DXToolsMethods;

public class EditActivityDlg extends JDialog implements ActionListener{

  private DApplication _dApplic;
  private String _currentActivity;
  private int _currentActivityIndex=0;
  private String _DURATION= "Durée:";
  private String _DAY= "Jour:";
  private String _HOUR="Heure de début:";
  private String _INSTRUCTOR= "Enseignant:";
  private String _ROOM= "Local:";
  //private String _DIALOGMESSAGE= "Affectation d'évenement";
  Vector _unities = new Vector();

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
    super(dialog, "Affectation d'évenement");
    setLocationRelativeTo(dialog);
    _dApplic = dApplic;
    _currentActivity = currentActivity;
    buildUnitiesVector();
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
    tabbedPane.addTab(((Resource)_unities.get(0)).getID(), CreateUnityPanel(0));
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
  public JPanel CreateUnityPanel(int index){
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

  /**
   *Return vector of resource. each resource represent an event
   */
  private void buildUnitiesVector(){
    int nbTokens= DXToolsMethods.countTokens(this._currentActivity, ".");
     //Vector unities= new Vector(1);
    //System.out.println("CounTokens: "+nbTokens);// debug
    String actID= DXToolsMethods.getToken(_currentActivity,".",0);
    String typID= DXToolsMethods.getToken(_currentActivity,".",1);
    if(typID.length()!=0){
      String secID= DXToolsMethods.getToken(_currentActivity,".",2);
      if(secID.length()!=0){
        String unitID= DXToolsMethods.getToken(_currentActivity,".",3);
        if(unitID.length()!=0){
          _unities.add(_dApplic.getDMediator().getCurrentDoc().getDM().getSetOfEvents().
                      getResource(_currentActivity));

        }else{// else unitID.length()!=0

        }// end else unitID.length()!=0
      }else{// else if(secID.length()!=0)

      }// end else if(secID.length()!=0)
    }else{// else if(typID.length()!=0)

    }// end else if(typID.length()!=0)
  }

}// end class