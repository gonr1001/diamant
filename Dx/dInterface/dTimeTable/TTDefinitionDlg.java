package dInterface.dTimeTable;

/**
 * <p>Title: miniDia</p>
 * <p>Description: exam timetable construction with Condition Pattern</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author rgr
 * @version 1.0
 */


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JComponent;
import javax.swing.border.*;
import java.awt.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.util.*;

import dInterface.CmdButton;



import javax.swing.BorderFactory;
import dInternal.DModelListener;
import dInternal.DModelEvent;
import dInterface.DApplication;

//import diamant002.utilities.NumberTextField;
//import diamant002.dInternal.DPeriod;



/**
 * Used to create either a new exam time table or a new cycle time table.
 * It asks the user the info needed to create a vector of periods used later
 * on to place activities.
 *
 * @author 	David Vallee
 * @version 	%I%, %G%
 * @since 1.3
 */


class TTDefinitionDlg extends JDialog
                   implements ActionListener,
                              ItemListener,
                              FocusListener,
                              DModelListener{

  final static int CYCLE = 0;
  final static int EXAM = 1;

  final static String MES00 = "Grille Horaire --> ";
  final static String MES01 = "Nombre de jours :";
  final static String MES02 = "Nombre de périodes par jour:";
  final static String MES03 = "Début :";
  final static String MES04 = "Fin :";
  final static String MES05 = "Durée : ";
  final static String MES06 = "Période";
  final static String MES07 = "Vous devez entrer l'heure dans le\nformat suivant: hh:mm .";
  final static String MES08 = "Attention";
  final static String MES09 = "Certaines périodes ne sont pas affectées.\n" +
                              "Continuer ne sauvegardera pas les changements " +
                              "effectués\nVoulez-vous continuer ?";
  final static String MES10 = "Il y a un conflit à la période ";
  final static String MES11 = "L'heure du début de la période doit précéder l'heure de fin.";
  final static String BUT00 = "OK";
  final static String BUT01 = "Annuler";
  final static String BUT02 = "Appliquer aux autres jours";
  final static String BUT03 = "Appliquer";
  final static String CBI00 = "Normale";
  final static String CBI01 = "Basse";
  final static String CBI02 = "Nulle";

  JPanel butPanel = new JPanel();
  JButton butAppliquer;
  JButton butCancel;
  JButton butOk;
  JButton butAllDays;
  JPanel panelC;
  JPanel perPanel;
  JPanel gridPanel;
  JPanel infoPanel;
  JPanel TTPanel;
  JTextField nbDays;
  JTextField nbPer;
  JTextField begin;
  JTextField end;
  JLabel label1;
  JLabel label2;
  JLabel time;
  GridBagLayout gridbag = new GridBagLayout();
  GridBagConstraints c;
  JComboBox priority;

  private int lastPressed = -1;
  private int type;
  private GregorianCalendar tBegin;
  private GregorianCalendar tEnd;
  private int days;
  private int periods;
  private Vector butVect;    // JButton
  private Vector periodVect;    // DPeriod
  //private DDocument _dd;

  private DApplication _dApplic;
  //private JFrame _jFrame;
  //private DMediator _mediator;
  private int [] _a = new int [2];

  // used at itemStateChanged so that the method is not called from ActionEvent
  // but only when the user selects it.
  private boolean inActionPerformed = false;


  /**
   * Default constructor.  Use this constructor to start a TimeTable
   * withiout a list of periods already constructed.
   *
   * @param owner The compinent on which the dialog will be displayed.
   * @param doc The active document.  Used to access the dictionnaries.
   * @param type Specify the type of time table to be created.  Use
   * either CYCLE or EXAM.
   */
  public TTDefinitionDlg(DApplication dApplic, String str) {
    super(dApplic.getJFrame(), str, true);
    _dApplic = dApplic;

    //_doc = doc;


    this.type = type;
    try {
      if ( type == CYCLE ) {
        //days = _doc._timeTable.nbDays;
        //periods = _doc._timeTable.nbPeriodPerDay;
      }
      else if ( type == EXAM ) {
        days = 10;
        periods = 2;
      }
      jbInit();
      pack();
      setLocationRelativeTo(_dApplic.getJFrame());
      setVisible(true);
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Modify constructor.  Use this constructor to modify a time table
   * already created.
   *
   * @param owner The compinent on which the dialog will be displayed.
   * @param doc The active document.  Used to access the dictionnaries.
   * @param type Specify the type of time table to be created.  Use
   * either CYCLE or EXAM.
   */
  public TTDefinitionDlg(DApplication applic, String str, String s) {
    super(applic.getJFrame(), str, true);
    //_doc = doc;
    //_jFrame =owner;
/*    if (TT.isCycle)
      type = CYCLE;
    else
      type = EXAM;
    try {
      days = TT.nbDays;
      periods = TT.nbPeriodPerDay;
      jbInit();
      initialiseWithTimeTable(TT);
      pack();
      setLocationRelativeTo(owner);
      setVisible(true);
    }
    catch(Exception e) {
      e.printStackTrace();
    }*/
  }

  private void jbInit() throws Exception {
    //setTitle( MES00 ) ; //_doc._projectName);
    setResizable(false);

    _dApplic.getDMediator().getCurrentDoc().getDM().addDModelListener(this);
    panelC = new JPanel(gridbag);
    //infoPanel
    infoPanel = new JPanel(new GridLayout(0, 1));
    JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    JLabel label = new JLabel( MES01 );
    nbDays = new JTextField();
    nbDays.setText(Integer.toString(days));
    nbDays.setPreferredSize(new Dimension(50, 20));
    nbDays.addFocusListener(this);
    nbDays.addActionListener(this);
    panel.add(label);
    panel.add(nbDays);
    infoPanel.add(panel);
    panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    label = new JLabel( MES02 );
    nbPer = new JTextField();
    nbPer.setText(Integer.toString(periods));
    nbPer.setPreferredSize(new Dimension(50, 20));
    nbPer.addFocusListener(this);
    nbPer.addActionListener(this);
    panel.add(label);
    panel.add(nbPer);
    infoPanel.add(panel);
    butAllDays = new JButton( BUT02 );
    butAllDays.setEnabled(false);
    butAllDays.addActionListener(this);
    infoPanel.add(butAllDays);
    nbDays.setNextFocusableComponent(nbPer);
    nbPer.setNextFocusableComponent(nbDays);
    //perPanel
    perPanel = new JPanel(new GridLayout(0, 1));
    perPanel.setBorder(new TitledBorder(new EtchedBorder(), MES06));
    ((TitledBorder)perPanel.getBorder()).setTitleColor(Color.gray);
    panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    label1 = new JLabel( MES03 );
    label1.setEnabled(false);
    begin = new JTextField( );
    begin.addFocusListener( this );
    begin.addActionListener(this);
    begin.setPreferredSize(new Dimension(50, 20));
    begin.setEnabled(false);
    panel.add(label1);
    panel.add(begin);
    perPanel.add(panel);
    time = new JLabel( MES05);
    time.setEnabled(false);
    perPanel.add(time);
    panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    label2 = new JLabel( MES04 );
    label2.setEnabled(false);
    end = new JTextField( );
    end.addFocusListener( this );
    end.addActionListener(this);
    end.setPreferredSize(new Dimension(50, 20));
    end.setEnabled(false);
    panel.add(label2);
    panel.add(end);
    perPanel.add(panel);
    panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    String priorityList[] = {CBI00, CBI01, CBI02};
    priority = new JComboBox(priorityList);
    priority.setPreferredSize(new Dimension(75, 20));
    priority.setEnabled(false);
    priority.addItemListener(this);
    panel.add(priority);
    perPanel.add(panel);

    //gridPanel
    gridPanel = new JPanel();
    gridPanel.setBorder( BorderFactory.createCompoundBorder(
                         BorderFactory.createEtchedBorder(),
                         BorderFactory.createEmptyBorder(5,5,5,5)) );

    /***
     * TTPanel est la grille qu'on peut voir dans la boite de dialogue de diamant
     */

    TTPanel = updateTTPanel();
    gridPanel.add(TTPanel);

    /***
     * Voici une petite grille de JTextFields
     */
    for (int numberOfCells = 0; numberOfCells <= 10; numberOfCells++){
        gridPanel.add(new JTextField("cellule"));
    }

    c = new GridBagConstraints();
    c.weightx = 1;
    c.gridwidth = GridBagConstraints.RELATIVE;
    gridbag.setConstraints(infoPanel, c);
    panelC.add(infoPanel);

    c.gridwidth = GridBagConstraints.REMAINDER;
    c.weightx = 0;
    gridbag.setConstraints(perPanel, c);
    panelC.add(perPanel);

    c.gridwidth = GridBagConstraints.REMAINDER;
    c.weighty = 1;
    c.fill = GridBagConstraints.HORIZONTAL;
    gridbag.setConstraints(gridPanel, c);
    panelC.add(gridPanel);


    this.getContentPane().add(panelC, BorderLayout.CENTER);

    butOk = new JButton( BUT00 );
    butOk.addActionListener( this );
    butCancel = new JButton( BUT01 );
    butCancel.addActionListener( this );
    butAppliquer = new JButton( BUT03 );
    CmdButton butAppliquer = new CmdButton(BUT03);
    butAppliquer.setCommand(new AppInTTCmd( _dApplic.getDMediator().getCurrentDoc()));
    butAppliquer.addActionListener( _dApplic.getDMediator().getCurrentDoc());
    butAppliquer.addActionListener(this);

    butPanel.add(butOk, null);
    butPanel.add(butAppliquer, null);
    butPanel.add(butCancel, null);
    this.getContentPane().add(butPanel, BorderLayout.SOUTH);
  }

  /*
   * Called when we want to update the TTPanel.  This
   * event can occur either at construction time
   * or when either the number of days or the number
   * of periods has changed
   *
   * @return a grid panel corresponding to the data
   * entered in both nbDays and nbPer JTextFields
   */
  private JPanel updateTTPanel() {
    int cols = days;
    int rows = periods;
    if (cols == 0) cols = 1;
    if (rows == 0) rows = 1;
    JPanel panel = new JPanel(new GridLayout(rows, cols, 0, 0));

    /* initialise data fields */
    butVect = new Vector();
    periodVect = new Vector();
    for (int k = 0; k < cols * rows; k++) {
      butVect.add(null);
      periodVect.add(null);
    }
    lastPressed = -1;

    for (int i = 0; i < rows; i++)
      for (int j = 0; j < cols; j++){
        JButton but = new JButton(Integer.toString((j*rows+i)+1));
        but.setBackground(Color.gray);
        but.setPreferredSize(new Dimension(60, 20));
        but.addActionListener(this);
        panel.add(but);

        butVect.setElementAt(but, (j*rows)+i);
    }

    return panel;
  }

  /**
   * process button events
   */
  public void actionPerformed(ActionEvent e){
    String command = e.getActionCommand();

    inActionPerformed = true;

    if (command.equals( BUT03 )) {
      _a[0]= 2;
      _a[1]= 2;
      setTitle( "hello" + _a[0] + _a[1]);
      _dApplic.getDMediator().getCurrentDoc().getDM().setParameters(_a);
    }

    if (command.equals( BUT00 )) {  // Ok
      int i = 0;
      while (i < periodVect.size() && periodVect.get(i) != null )
        i++;
      System.out.println("DNewTTDialog i:"+i+" periodVect size: "+periodVect.size());//debug
      //debut debug yan
      /*int h =0;
      while (h < periodVect.size() && periodVect.get(h) != null ){
        System.out.println("Periode :"+h+" non NULL");//debug
        h++;
      }*/
      // fin debug yan
      if (i < periodVect.size()) {
        int retval = JOptionPane.showConfirmDialog(this, MES09, "",
                     JOptionPane.YES_NO_OPTION);
    if (retval == JOptionPane.YES_OPTION) {
      dispose();
        }
      }
     /* else{
       // purgePeriods();
        System.out.println("DNewTTdailog  periodVect size: "+periodVect.size());//debug
        if (type == CYCLE)
          _doc.createCycleTT(periodVect, days, periods);
        else if (type == EXAM)
          _doc.createExamTT(periodVect, days, periods);
          dispose();
      }*/
    }
    else if (command.equals( BUT01 )) {  // Cancel
      dispose();
      //_doc.standardTT=1;
      //_doc.close();
      //_jFrame.closePartial();
    }
    else if (command.equals( BUT02 )) {  // All Days
   /*   DPeriod source = (DPeriod)periodVect.get(lastPressed);
      System.out.println("DNewTTdailog  lastPressed: "+lastPressed+"  NbPeriodPerDay: "+periods);//debug
      int posInDay = (lastPressed%periods)+1;
      System.out.println("DNewTTdailog Pos in Day: "+posInDay);//debug
      for (int i = 0; i < days; i++) {
        DPeriod copy = new DPeriod( i, posInDay, source.timeBegin,
                                    source.timeEnd, source.priority,
                                    i*periods + posInDay);
        if( setPeriod(i*periods + posInDay-1, copy))
            return;

      }*/
    }
    else if ( e.getSource() == nbDays || e.getSource() == nbPer ||
              e.getSource() == begin || e.getSource() == end ) {
      Component comp = ((JComponent)e.getSource()).getNextFocusableComponent();
      if (comp != null )
        comp.requestFocus();
    }
    // grid button pressed
    else if ( butVect.indexOf(e.getSource() ) > -1 ) {
      int index = butVect.indexOf(e.getSource());
      /* first we save info entered for the last
         button, if applicable*/
      if ( lastPressed > -1 && tBegin != null && tEnd != null) {
        int posInDay1 = (lastPressed+1)%periods;
       /* DPeriod lastPeriod = new DPeriod( lastPressed/(periods),
                                   posInDay1,
                                   tBegin,
                                   tEnd,
                                   priority.getSelectedIndex(),
                                   lastPressed);
        setPeriod(lastPressed, lastPeriod);*/
      }

      /* we then set the fields for the selected
         period to their value if already given
         or to blank for a non-specified period */
 /*     DPeriod newPeriod = (DPeriod)periodVect.get(index);
      if (newPeriod != null) {
        tBegin = newPeriod.timeBegin;
        begin.setText(tBegin.get(Calendar.HOUR_OF_DAY) +
        ":" + (tBegin.get(Calendar.MINUTE) < 10 ?
            ("0" + tBegin.get(Calendar.MINUTE)) :
            ("" + tBegin.get(Calendar.MINUTE))) );

        tEnd = newPeriod.timeEnd;
        end.setText( tEnd.get(Calendar.HOUR_OF_DAY) +
        ":" + (tEnd.get(Calendar.MINUTE) < 10 ?
            ("0" + tEnd.get(Calendar.MINUTE)) :
            ("" + tEnd.get(Calendar.MINUTE))) );

        GregorianCalendar temp = (GregorianCalendar)newPeriod.timeEnd.clone();
        temp.add(Calendar.HOUR_OF_DAY, - newPeriod.timeBegin.get(Calendar.HOUR_OF_DAY));
        temp.add(Calendar.MINUTE, - newPeriod.timeBegin.get(Calendar.MINUTE));
        int tempHour = temp.get(Calendar.HOUR_OF_DAY);
        int tempMin = temp.get(Calendar.MINUTE);
        time.setText( MES05 +
                     ((tempHour) == 0 ? "" : (Integer.toString(tempHour) + "h "))+
                     ((tempMin == 0) ? "" : (Integer.toString(tempMin) + "min")) );
        priority.setSelectedIndex(newPeriod.priority);
        butAllDays.setEnabled(true);
      }
      else {
        begin.setText("");
        end.setText("");
        time.setText(MES05);
        priority.setSelectedIndex(0);
        tBegin = null;
        tEnd = null;
        butAllDays.setEnabled(false);
      }
      ((TitledBorder)perPanel.getBorder()).setTitle( MES06 + ": " +
                               Integer.toString(index + 1));
      if (lastPressed == -1){
        ((TitledBorder)perPanel.getBorder()).setTitleColor(Color.black);
        label1.setEnabled(true);
        label2.setEnabled(true);
        time.setEnabled(true);
        begin.setEnabled(true);
        end.setEnabled(true);
        priority.setEnabled(true);
      }*/
      repaint();
      ((JComponent)e.getSource()).requestFocus();
      lastPressed = index;
    }
    inActionPerformed = false;
  }

  /**
   * ItemListener for the JComboBox
   */
  public void itemStateChanged( ItemEvent e ) {
    if( e.getStateChange() == e.SELECTED && !inActionPerformed )
    {
      Object source = e.getSource();
      System.out.println("DNewTTDialog priorite: "+priority.getSelectedIndex()+" lastPressed: "+lastPressed);//debug
      if ( source.equals( priority ) ) {
       //// ((DPeriod)periodVect.get(lastPressed)).priority = priority.getSelectedIndex();
        ///setPeriod( lastPressed, (DPeriod)periodVect.get(lastPressed) );
      }
    }
  }
  /**
   * FocusListener for the textFields begin and End
   */
  public void focusLost(FocusEvent e) {
    // nbJours ou nbPer changed
    if (!e.isTemporary()) {
      if((e.getComponent() == nbDays || e.getComponent() == nbPer) &&
         (Integer.parseInt(nbDays.getText()) != days ||
         Integer.parseInt(nbPer.getText()) != periods) ) {

        days = Integer.parseInt(nbDays.getText());
        if (days > 35) {
          days = 35;
          nbDays.setText(Integer.toString(days));
        }
        periods = Integer.parseInt(nbPer.getText());
        if (periods > 30) {
          periods = 30;
          nbPer.setText(Integer.toString(periods));
        }
        panelC.remove(gridPanel);
        gridPanel.removeAll();
        TTPanel = updateTTPanel();
        gridPanel.add(TTPanel);
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.weighty = 1;
        gridbag.setConstraints(gridPanel, c);
        panelC.add(gridPanel);
        repaint();
        pack();
      }
      /* Adjust time according to begin and end time entered */
      else if (e.getComponent() == begin || e.getComponent() == end) {
        String command = ((JTextField)e.getComponent()).getText();
    StringTokenizer st = new StringTokenizer(command, ":");
        int hour = 0;
    int minute = 0;
        try{
          hour = Integer.parseInt(st.nextToken());
      minute = Integer.parseInt(st.nextToken());
        }
    catch (Exception nfe) {
      JOptionPane.showMessageDialog( this, MES07, MES08,
                                     JOptionPane.WARNING_MESSAGE);
      ((JTextField)e.getSource()).setText("");
          e.getComponent().requestFocus();
        }
    if (e.getSource() == begin) {
      tBegin = new GregorianCalendar();
      tBegin.set(0,0,0,hour,minute);
    }
    else {
      tEnd = new GregorianCalendar();
      tEnd.set(0,0,0,hour,minute);
    }

    if (tEnd != null && tBegin != null && tBegin.before(tEnd)) {
      GregorianCalendar temp = (GregorianCalendar)tEnd.clone();
      temp.add(Calendar.HOUR_OF_DAY, - tBegin.get(Calendar.HOUR_OF_DAY));
          temp.add(Calendar.MINUTE, - tBegin.get(Calendar.MINUTE));
      int tempHour = temp.get(Calendar.HOUR_OF_DAY);
          int tempMin = temp.get(Calendar.MINUTE);
      time.setText( MES05 +
                 ((tempHour) == 0 ? "" : (Integer.toString(tempHour) + "h "))+
             ((tempMin == 0) ? "" : (Integer.toString(tempMin) + "min")) );
      }
      else
        time.setText(MES05);
        }
     }
  }

  /*
   * returns the color corresponding to the priority given
   */
  private Color getColor(int i) {
    Color color;
    switch (i) {
      case 0 :
        color = Color.green;
        break;
      case 1 :
        color = Color.yellow;
        break;
      default :
        color = Color.red;
    }
    return color;
  }

  /*
   * place the period and verifies it isn't in conflict with any other
   */
  /*
  private boolean setPeriod(int position, DPeriod item) {
    boolean returnValue = true;
    if ( ( position == 0 ||periodVect.get(position-1)==null ||
         ((DPeriod)periodVect.get(position-1)).day != item.day ||
         !((DPeriod)periodVect.get(position-1)).timeEnd.after(item.timeBegin) )
         &&
         ( position == periodVect.size() - 1 ||
         periodVect.get(position+1)==null ||
         ((DPeriod)periodVect.get(position+1)).day != item.day ||
         !((DPeriod)periodVect.get(position+1)).timeBegin.before(item.timeEnd)))
    {
      if (item.timeBegin.before(item.timeEnd)) {
        periodVect.setElementAt(item, position);

        TTPanel.getComponent(position%(periods) *
                             (days) +
                             position/(periods))
                             .setBackground(getColor(item.priority));
        returnValue = false;
      }
      else
        JOptionPane.showMessageDialog(this, MES11);
    }
    else
      JOptionPane.showMessageDialog(this, MES10+Integer.toString(position+1)+".");
    return returnValue;
  }

  private void purgePeriods() {
    Vector newVect = new Vector();
    Vector realnewVect = new Vector();
    int sect = 0;
    for (int i =0; i < periodVect.size(); i++){

      if (((DPeriod)periodVect.get(i)).priority != 2){
        ((DPeriod)periodVect.get(i)).refNo = sect;
        ((DPeriod)periodVect.get(i)).etat = true;
         newVect.addElement(periodVect.get(i));
         realnewVect.addElement(new Integer (sect));
          sect++;
      } else{
        ((DPeriod)periodVect.get(i)).refNo = sect;
        ((DPeriod)periodVect.get(i)).etat = false;
         newVect.addElement(periodVect.get(i));
         realnewVect.addElement(new Integer (sect));
          sect++;
      }
        //realnewVect.addElement(null);

    }
    //System.out.println("DNewTTdailog purge period newVect size: "+newVect.size());//debug
    newVect.trimToSize();
    realnewVect.trimToSize();
    _doc._realTimeTable = (Vector)realnewVect.clone();
    periodVect = (Vector)newVect.clone();
  }
*/
/*  private void initialiseWithTimeTable(DTimeTable TT) {
    for (int i = 0; i < TT.size(); i++) {
      periodVect.setElementAt(TT.get(i), i);
      TTPanel.getComponent(i%(periods) *
                             (days) +
                             i/(periods))
                             .setBackground( getColor( ((DPeriod)TT.get(i)).priority ) );
    }
  }*/

  // have to write this fonction so that the class can
  // implement the listener
  public void focusGained(FocusEvent e) {}

  public void changeInDModel(DModelEvent e) {
    setTitle( "Myhello" + _a[0] + _a[1]);
    //repaintDlg(_mediator.getCurrentDoc().getTTParameters());

  }

}
