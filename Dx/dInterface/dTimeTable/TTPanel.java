package dInterface.dTimeTable;

/**
 * <p>Title: miniDia</p>
 * <p>Description: exam timetable construction with Condition Pattern</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author rgr
 * @version 1.0
 */

import java.awt.Dimension;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.util.*;
import java.awt.*;
import javax.swing.BorderFactory;
import javax.swing.border.*;
import java.awt.event.*;

import dInternal.DModel;
import dInternal.dData.Resource;
import dInternal.dTimeTable.*;

import dInternal.dTimeTable.Period;

public class TTPanel extends JScrollPane {
  private DModel _dm;
  //private int MINHEIGHT = 60;
  private int HHEIGHT =  24; // timeTable.nbDays * MINWIDTH;
  private int VWIDTH =  36; // timeTable.nbDays * MINWIDTH;
  private int UWIDTH =  100; // timeTable.nbDays * MINWIDTH;
  private int UHEIGHT =  60;// (timeTable.getLatest() - timeTable.getEarliest()) * MINHEIGHT;

  private int _periodLenght;


//  private int MINWIDTH =  500; // timeTable.nbDays * MINWIDTH;
//  private int MINHEIGHT =  600;// (timeTable.getLatest() - timeTable.getEarliest()) * MINHEIGHT;

  public TTPanel(DModel dm) {
    super();
    _dm = dm;
    _periodLenght= _dm.getTTStructure().getPeriodLenght();
    UHEIGHT= _dm.getTTStructure().getPeriodLenght();
    initTTPanel();
  }

  private void initTTPanel() {
    setColumnHeaderView(createColumnHeader());
    setRowHeaderView(createRowHeader());
    setViewportView(createViewPort());
  }

  public void updateTTPanel(TTStructure ttp){
    //
  }

  private JPanel createColumnHeader() {
    JPanel panel = new JPanel(new GridLayout(1, 0));
    Cycle cycle =_dm.getTTStructure().getCycle(_dm.getCurrentCycle());
    for (int i = 0; i < cycle.getSetOfDays().size() ; i++){
      Resource day = cycle.getSetOfDays().getResourceAt(i);
      panel.add(new JLabel("J " + (i + 1) + " : "+ day.getID(), JLabel.CENTER));
      //panel.add(new JLabel("Jour " + (i + 1) + " : "+ "lun", JLabel.CENTER));
    }
    panel.setPreferredSize(new Dimension(UWIDTH *cycle.getSetOfDays().size(), HHEIGHT));

    panel.setBorder(BorderFactory.createEtchedBorder());
    return panel;
  }

  private JPanel createRowHeader() {
    JPanel panel = new JPanel(new GridLayout(0,1));
    Cycle cycle =_dm.getTTStructure().getCycle(_dm.getCurrentCycle());
    Day day = _dm.getTTStructure().getDay(cycle,1);
    int numbOfSequences = day.getSetOfSequences().size();
    JLabel label;
    Period firstPer= _dm.getTTStructure().getFirstPeriod(cycle);
    Period lastPer= _dm.getTTStructure().getLastPeriod(cycle);
    for (int i = firstPer.getBeginHour()[0]; i < lastPer.getEndHour(_periodLenght)[0]; i++) {
      label = new JLabel(Integer.toString(i) + ":00");
      label.setVerticalAlignment(JLabel.TOP);
      panel.setBorder(BorderFactory.createEtchedBorder());
      panel.add(label);
    }
    int minHeight = (_dm.getTTStructure().getLastPeriod(cycle).getEndHour(_periodLenght)[0]
                     - _dm.getTTStructure().getFirstPeriod(cycle).getBeginHour()[0]) * UHEIGHT;
    panel.setPreferredSize(new Dimension(35, minHeight));
    return panel;
  }

  private JPanel createViewPort() {
    GridBagLayout gridbag =new GridBagLayout();
    JPanel panel =  new JPanel( gridbag );
    panel.setBackground(SystemColor.window);
    Cycle cycle =_dm.getTTStructure().getCycle(_dm.getCurrentCycle());
    int nbDays,nbSeq,nbPerADay;
    nbDays = cycle.getSetOfDays().size(); //timeTable.nbDays;
    gridbag.columnWeights = new double [nbDays];
    gridbag.columnWidths = new int [nbDays];
    for (int i = 0; i < nbDays; i++) {
      gridbag.columnWeights[i] = 1;
      gridbag.columnWidths[i] =  UWIDTH;
    }
    nbPerADay = _dm.getTTStructure().getMaxNumberOfPeriodsADay(cycle);
    gridbag.rowWeights = new double [nbPerADay];
    gridbag.rowHeights = new int [nbPerADay];
    for (int i = 0; i < nbPerADay; i++) {
      gridbag.rowWeights[i] = 1;
      gridbag.rowHeights[i] = UHEIGHT;
    }
    PeriodPanel periodPanel = null;
    GridBagConstraints c = null;
    nbSeq = _dm.getTTStructure().getMaxNumberOfSeqs(cycle);
    int count = 0;
    for (int i = 0; i < nbDays ; i++ ) {
      Day day= _dm.getTTStructure().getDay(cycle, i+1);
      for(int j = 0; j < nbSeq; j ++) {
        Sequence sequence= _dm.getTTStructure().getSequence(day,j+1);
        for(int k = 0; k < sequence.getSetOfPeriods().size(); k ++) {
          Period period= _dm.getTTStructure().getPeriod(sequence,k+1);
          periodPanel = new PeriodPanel(period, count, UWIDTH, UHEIGHT);
          count++;
          c = new GridBagConstraints();
          c.fill = GridBagConstraints.BOTH;
          c.gridx = i;
          c.gridy = period.getBeginHour()[0] - _dm.getTTStructure().getFirstPeriod(cycle).getBeginHour()[0];//period.getEndHour(_periodLenght)[0];
          if ( period.getEndHour(_periodLenght)[1] == 0 ){
            c.gridheight = period.getEndHour(_periodLenght)[0] - period.getBeginHour()[0];
            c.insets = new Insets( period.getBeginHour()[1]*UHEIGHT/_periodLenght, 0, 0, 0 );

          } else {
            c.gridheight = period.getEndHour(_periodLenght)[0] + 1 - period.getBeginHour()[0];
            c.insets = new Insets( period.getBeginHour()[1]*UHEIGHT/_periodLenght, 0,
                                   (_periodLenght- period.getBeginHour()[1]*UHEIGHT/_periodLenght), 0 );

          }
          //c.insets = new Insets(10, 0, 0, 0 );
          setPerPanelColor(periodPanel, period.getPriority());
          gridbag.setConstraints(periodPanel, c);
          panel.add(periodPanel, c);
        }//end for k
      } // end for j
    }//end for i
     return panel;
  }

  /**
   *
   * */
  private void setPerPanelColor(PeriodPanel perPanel, int priority){
    Color color= Color.GRAY;
    switch(priority){
      case 0: color= Color.LIGHT_GRAY;
        break;
      case 1: color= Color.GRAY;
        break;
      case 2: color= Color.RED;
        break;
    }
    for (int i=0; i< perPanel.getComponentCount(); i++){
      perPanel.getComponent(i).setBackground(color);
    }

  }
}

/*
public class DTTPanel extends JScrollPane{

  final static int MINWIDTH = 100;
  final static int MINHEIGHT = 60;
  final static int CONFLICT = 0;
  final static int LIST = 1;
  DTimeTable timeTable;
  private Vector perVect;
  private DDocumentView _ddv;
  private int view;
  GridBagLayout gridbag = new GridBagLayout();
  final static String MES00 = "Blocs (Placés / Total) : ";
  final static String MES01 = "Étudiants : ";
  final static String MES02 = "Enseignants : ";
  final static String MES03 = "Locaux : ";
  final static String TITLE = "Conflits";


  public DTTPanel(DTimeTable tt, DDocumentView ddv, int viewType) {

  }

  private void jbInit() throws Exception {


    MouseListener mouseListener = new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
          if (view == CONFLICT) {
            int index = perVect.indexOf(e.getComponent());
            index = trueIndex(index);// debug
            DPeriodPanel dpp = new DPeriodPanel((DPeriod)timeTable.get(index), e.getComponent().getBounds(), _ddv);
          }
          else {
            int index = perVect.indexOf(e.getComponent().getParent().getParent().getParent());
            index = trueIndex(index);// debug
            DBloc bloc;
            if ( _ddv._timeTable.isCycle )
              bloc = _ddv._dicActivities.getBloc( (String)((PerPanel)perVect.get(index)).list.getSelectedValue() );
            else
              bloc = _ddv._dicActivities.getEBloc( (String)((PerPanel)perVect.get(index)).list.getSelectedValue() );
            DSubActivity subAct = _ddv._dicActivities.getSA(bloc.courseName);//bloc.ancestor;
            DSubActPanel dap = new DSubActPanel(subAct, _ddv, bloc);
            ((PerPanel)perVect.get(index)).list.setSelectedIndex(-1);
          }
        }
      }
    };


    // create column header

    // create row header

   // create ViewPort
    panel = new JPanel(gridbag);
    panel.setBackground(SystemColor.window);
    panel.setPreferredSize(new Dimension(minWidth, minHeight));
    setPreferredSize(new Dimension(panel.getPreferredSize().width + 40,
                                   panel.getPreferredSize().height + 50));
    int nbCols = timeTable.nbDays;
    gridbag.columnWeights = new double [nbCols];
    gridbag.columnWidths = new int [nbCols];
    for (int i = 0; i < nbCols; i++) {
      gridbag.columnWeights[i] = 1;
      gridbag.columnWidths[i] = MINWIDTH;
    }
    int nbRows = timeTable.getLatest() - timeTable.getEarliest();
    gridbag.rowWeights = new double [nbRows];
    gridbag.rowHeights = new int [nbRows];
    for (int i = 0; i < nbRows; i++) {
      gridbag.rowWeights[i] = 1;
      gridbag.rowHeights[i] = MINHEIGHT;
    }
    //System.out.println("DTTPanel timeTable size :"+ timeTable.size());//debug
    for (int i = 0; i < timeTable.size(); i++ )
    if (((DPeriod)timeTable.get(i)).etat){
      DPeriod per = (DPeriod)timeTable.get(i);
      //if (per != null)//debug
      //  System.out.println("DTTPanel period :"+ i);//debug
      PerPanel period = new PerPanel(_ddv, per, view, i);
      GridBagConstraints c = new GridBagConstraints();
      c.fill = GridBagConstraints.BOTH;
      c.gridx = per.day;
      c.gridy = per.timeBegin.get(Calendar.HOUR_OF_DAY) - timeTable.getEarliest();
      if ( per.timeEnd.get(Calendar.MINUTE) == 0 ){
        // nbCells = hfin - hdebut
        c.gridheight = per.timeEnd.get(Calendar.HOUR_OF_DAY) -
                per.timeBegin.get(Calendar.HOUR_OF_DAY);
        c.insets = new Insets( per.timeBegin.get(Calendar.MINUTE)*MINHEIGHT/60,
                0, 0, 0 );
      } else {
        // nbCells = (hfin + 1) - hdebut
        c.gridheight = per.timeEnd.get(Calendar.HOUR_OF_DAY) + 1 -
                per.timeBegin.get(Calendar.HOUR_OF_DAY);
        c.insets = new Insets( per.timeBegin.get(Calendar.MINUTE)*MINHEIGHT/60,
                0, (60 - per.timeEnd.get(Calendar.MINUTE))*MINHEIGHT/60, 0);
      }
      //}
      gridbag.setConstraints(period, c);
      panel.add(period, c);
      perVect.add(period);
      if (view == CONFLICT)
        period.addMouseListener(mouseListener);
      else {
        period.list.addMouseListener(mouseListener);
        ((JScrollPane)period.getComponent(0)).setPreferredSize( new Dimension(
                MINWIDTH - 5, MINHEIGHT * c.gridheight - 12 - c.insets.bottom -
                c.insets.top) );
      }
    }
    this.setViewportView(panel);
  }



  // Get true index in timetable with disable period

   int trueIndex(int ind){
    int inc=0, res=0;
    while(res <= ind){
      if(((DPeriod)timeTable.get(inc)).etat == true)
        res++;
      inc++;
    }
    return inc-1;
   }



//   * Called when information in a period needs to be update.  Usually, you must
//   * call this method twice, once for the activity which was removed and once
//   * for the period in which it was added...

  public void updatePeriod(int noRefPer) {
    _ddv._doc.getDDocumentView(_ddv);
    _ddv._doc.updateDEngineUI();
    //DPeriod period = timeTable.getPeriod(noRefPer);
    DPeriod period = (DPeriod)timeTable.get(noRefPer);
    int indice =0;
    int _nbPerAct =0;
    for (int i=0; i<timeTable.size(); i++ )
      if (((DPeriod)timeTable.get(i)).etat)
        _nbPerAct++;
    Integer temp;
    while (indice <(period.size()-1))
        if(((Integer)period.get(indice+1)).intValue() < ((Integer)period.get(indice)).intValue()){
          temp = (Integer)period.get(indice);
          period.set(indice, (Integer)period.get(indice+1));
          period.set(indice+1, temp);
          indice = 0;
        }else
          indice++;

    int position = timeTable.getPosition(noRefPer);
    //System.out.println("position : "+position);//debug
    if (position< _nbPerAct){
    if (view == CONFLICT ) {
      ((PerPanel)perVect.get(position)).nbAct.setText( Integer.toString(period.size()) );
      ((PerPanel)perVect.get(position)).nbFixed.setText("("+ Integer.toString(period.getNbFixed(_ddv._doc.engine))+ ")");// a corriger
      ((PerPanel)perVect.get(position)).cTeach.setText(Integer.toString(period.getInstrCfl()));
      ((PerPanel)perVect.get(position)).cRoom.setText(Integer.toString(period.getRoomsCfl()));
      ((PerPanel)perVect.get(position)).cStu.setText(Integer.toString(period.getStudCfl()));
      ((PerPanel)perVect.get(position)).repaint();
    }
    else {
      String actNames[] = new String [period.size()];
      if ( _ddv._timeTable.isCycle ) {
        for (int i = 0; i < period.size(); i++ )
          actNames[i] = _ddv._dicActivities.getBloc( ((Integer)period.get(i)).intValue() ).courseName;
      } else {
        for (int i = 0; i < period.size(); i++ )
          actNames[i] = _ddv._dicActivities.getEBloc( ((Integer)period.get(i)).intValue() ).courseName;
      }
      ((PerPanel)perVect.get(position)).list.setListData(actNames);
      ((PerPanel)perVect.get(position)).repaint();
    }// end else
    }//end if (position< _nbPerAct)
  }
}
*/
class PeriodPanel extends JPanel{

  public PeriodPanel(Period period,int c, int w, int h) {
    setLayout(new GridLayout(2,1));
    setPreferredSize(new Dimension(w, h));
    setBorder(new BevelBorder(BevelBorder.RAISED));
    JPanel topPanel = new JPanel();
    JPanel bottomPanel = new JPanel();
    int _nb = c +1 ;
    String _pernb = "      Période ";
    JLabel _per = new JLabel (" Période "+ _nb + "         ");
    JLabel nbAct = new JLabel( "1" );//Integer.toString(period.size()) );

    topPanel.add(_per);
    topPanel.add(nbAct);
//topPanel.add(nbFixed);
//bottomPanel.add(cTeach);
//bottomPanel.add(cRoom);
//bottomPanel.add(cStu);
//this.add(extratop);
    add(topPanel);
     add(bottomPanel);
     //if(period.getPriority()==1)
      // this.setBackground(Color.BLUE);

  }
}


/*
  JLabel nbAct, nbFixed, cTeach, cRoom, cStu, _per;
  JList list;

  PeriodPanel(DDocumentView ddv, DPeriod period, int view, int noPeriod) {

    if (view == DTTPanel.CONFLICT) {
      this.setLayout(new GridLayout(2,1));
      setPreferredSize(new Dimension(DTTPanel.MINWIDTH, DTTPanel.MINHEIGHT));
      setBorder(new BevelBorder(BevelBorder.RAISED));
      JPanel topPanel = new JPanel();
      JPanel bottomPanel = new JPanel();
      int _nb = period.refNo +1;
      String _pernb = "      Période ";
      _per = new JLabel (" Période "+ _nb+"         ");
      nbAct = new JLabel( Integer.toString(period.size()) );
      //System.out.println("periode number: "+_nb+"Period Size: "+ period.size());//debug
      ddv._doc.initDEngineUI();
      nbFixed = new JLabel("("+ Integer.toString(period.getNbFixed(ddv._doc.engine))+ ")");
      cTeach = new JLabel(Integer.toString(period.getInstrCfl()));
      cTeach.setForeground(Color.red);
      cRoom = new JLabel(Integer.toString(period.getRoomsCfl()));
      cRoom.setForeground(Color.blue);
      cStu = new JLabel(Integer.toString(period.getStudCfl()));
      cStu.setForeground(Color.magenta);
      topPanel.add(_per);
      topPanel.add(nbAct);
      topPanel.add(nbFixed);
      bottomPanel.add(cTeach);
      bottomPanel.add(cRoom);
      bottomPanel.add(cStu);
      //this.add(extratop);
      this.add(topPanel);
      this.add(bottomPanel);
    }*/

