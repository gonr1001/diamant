package dInterface.dAffectation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import dConstants.DConst;
import dInterface.DApplication;
import dInterface.dUtil.ButtonsPanel;
import dInterface.dUtil.TwoButtonsPanel;
import dInternal.DModel;
import dInternal.dDataTxt.RoomAttach;


/**
 * Dialog used to set disponibilities for the rooms.  The user must select
 * the corresponding room in the combo list and click on a period to
 * select/deselect it.  A selected period (pressed) means that the room
 * is available at that period.
 *
 * User must be warned that changes made in this dialog box is only used by
 * DIAMANT and so does not represent what data is actually in the central
 * database.  In fact, as long as a difference exists between both sets of data,
 * user must be warned of this difference in the import/export report...
 *
 *
 */

public class RoomsAvailabilityDlg  extends JDialog
                                implements ActionListener,
                                           ItemListener {
  private DApplication _dApplic;
  private int nbPer;
  private int nbDay;
  private String[] day;
  //private String[] _buttonsNames = {DConst.BUT_OK, DConst.BUT_APPLY, DConst.BUT_CANCEL};
  private  String[] time;

  private ButtonsPanel _applyPanel;
  private JPanel chooserPanel = new JPanel();
  private JPanel centerPanel;
  //JButton butApply;
  private JComboBox chooser;
  private Vector _posVect;
  //private int nbPerParJour;
  private boolean _modified = false;
  //private DModel _dm;
  private RoomAttach  _currentRoom;
  private int [][] _currentAvailbility;


  /**
   * Default constructor.
   *
   * @param owner The component on which the dialog will be displayed.
   * @param doc The active document.  Used to access the dictionnaries.
   */
  public RoomsAvailabilityDlg(DApplication dApplic){
    super(dApplic.getJFrame(), DConst.ROOMS_DLG_TITLE, false);
    _dApplic = dApplic;
    if (_dApplic.getDMediator().getCurrentDoc() == null)
      return;
    //_dm = _dApplic.getDMediator().getCurrentDoc().getDM();
    time= _dApplic.getDMediator().getCurrentDoc().getDM().getTTStructure().getCurrentCycle().getHourOfPeriodsADay();
    nbDay= _dApplic.getDMediator().getCurrentDoc().getDM().getTTStructure().getNumberOfActiveDays();
    day = new String[nbDay];
    for(int i=0; i< nbDay; i++)
      day[i]= _dApplic.getDMediator().getCurrentDoc().getDM().getTTStructure().getWeekTable()[i];
    nbPer= _dApplic.getDMediator().getCurrentDoc().getDM().getTTStructure().getCurrentCycle().getMaxNumberOfPeriodsADay();
    try {
      initialize();
      pack();
      setLocationRelativeTo(_dApplic.getJFrame());
      setVisible(true);
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  } // end RoomsAvailabiliyDlg

  /**
   * Component's initialisation and placement.
   */
  private void initialize() throws Exception {
    //chooser Panel
    //creates the JComboBox with the list of all rooms
    chooser = new JComboBox(_dApplic.getDMediator().getCurrentDoc().getDM().getSetOfRooms().getNamesVector(1));
    chooser.addItemListener( this );
    chooserPanel.add(chooser, null);
    this.getContentPane().add(chooserPanel, BorderLayout.NORTH);

    //gridPanel
    String sel = (String)chooser.getSelectedItem();
    _currentRoom = (RoomAttach)_dApplic.getDMediator().getCurrentDoc().getDM().getSetOfRooms().getResource(sel).getAttach();
    centerPanel = makeGridPanel(_currentRoom);
    this.getContentPane().add(centerPanel, BorderLayout.CENTER );

    //_applyPanel
    String [] a ={DConst.BUT_APPLY, DConst.BUT_CLOSE};
    _applyPanel = new TwoButtonsPanel(this, a);
    //Setting the button APPLY disable
    _applyPanel.setFirstDisable();
    this.getContentPane().add(_applyPanel, BorderLayout.SOUTH);
  } // end  jbInit()

  public void actionPerformed( ActionEvent event) {
    String command = event.getActionCommand();
    if (command.equals(DConst.BUT_CLOSE)) {  // close
      dispose();
    } else if (command.equals(DConst.BUT_APPLY)) {  // apply
      _currentRoom.setAvailability(_currentAvailbility);
      _modified = false;     
      _dApplic.getDMediator().getCurrentDoc().getDM().sendEvent(this);
      _applyPanel.setFirstDisable();
      //DModel dm =_dApplic.getDMediator().getCurrentDoc().getDM();
    // if a button of the grid has been pressed
    } else if ( _posVect.indexOf(event.getSource() ) > -1 ) {
      int index = _posVect.indexOf(event.getSource());
      int day = index / nbPer;
      int per = index % nbPer;
      if ( ((JToggleButton)_posVect.get(index)).isSelected() ){
        _currentAvailbility [day][per] = 1;
      }
      else{
        _currentAvailbility [day][per] = 5;
      }
      _modified = true;
      _applyPanel.setFirstEnable();
    }
  }

  /**
   * combobox item selected
   */
  public void itemStateChanged( ItemEvent event ) {
	_applyPanel.setFirstDisable();
    if ( event.getStateChange() == ItemEvent.SELECTED ) {
      Object source = event.getSource();
      if (source.equals( chooser ) ) {
        getContentPane().remove(centerPanel);
        String sel = (String)chooser.getSelectedItem();
        _currentRoom = (RoomAttach)_dApplic.getDMediator().getCurrentDoc().getDM().getSetOfRooms().getResource(sel).getAttach();
        centerPanel = makeGridPanel(_currentRoom);
        getContentPane().add(centerPanel, BorderLayout.CENTER);
        pack();
      }
    }
  }// end itemStateChangeed


  /**
   * Creates the grid of button.  The button is pressed if the room
   * is free at that period, depressed if not.
   *
   * @param room the room for which the grid is constructed.
   */
  private JPanel makeGridPanel(RoomAttach room) {
    JPanel gridPanel = new JPanel();
    gridPanel.setLayout(new GridLayout(nbPer + 1, nbDay + 1));
    gridPanel.setBorder(BorderFactory.createTitledBorder(DConst.AVAILABILITIES));
    _posVect = new Vector();
    _posVect.setSize((nbPer +1 )*(nbDay+1));
    gridPanel.add(new JLabel("")); // top left corner
    for (int i = 0; i < day.length; i++)
      //first line :  name of days
      gridPanel.add(new JLabel(day[i], JLabel.CENTER));

    /*
    *Attention : J'ai caché ça en attandant l'implémentation de la disponibilité
    *de locaux
    */
    _currentAvailbility = _currentRoom.getMatrixAvailability();
    //Debbug init
    /*_currentAvailbility = new int [5][14];
    int [] dayAval = new int [14];
    for (int i = 0; i < _currentAvailbility.length; i++){
      for (int j = 0; j < _currentAvailbility[i].length; j++){
        if (j == i)
          dayAval[j] = 1;
        else
          dayAval[j] = 5;
      }
      _currentAvailbility[i] = dayAval;
    }
    */
    //Debbug end

    for (int j = 0; j < nbPer; j++) {
      // first column : the time of the period

      gridPanel.add(new JLabel(time[j], JLabel.RIGHT));
      // create a button for each day for the period
      //System.out.println(" RoomDialog NbDays: "+nbDay+"   NbPerDays: "+nbPer); //DEBUG
      for (int i = 0; i < nbDay; i++) {
        JToggleButton tBut = new JToggleButton();
        tBut.setSelected(_currentAvailbility[i][j] == 1 );
        tBut.addActionListener( this );
        tBut.setPreferredSize(new Dimension(50,12));
        gridPanel.add(tBut);//, null);
        _posVect.setElementAt(tBut, (i * nbPer) + j);
      }
    }
    return gridPanel;
  }

} /* end RoomAvailabilityDlg */