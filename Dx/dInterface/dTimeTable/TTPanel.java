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
import dInternal.dTimeTable.TTStructure;
import dInternal.dTimeTable.Period;

public class TTPanel extends JScrollPane {
  private DModel _dm;
  private int UWIDTH =  75; // timeTable.nbDays * MINWIDTH;
  private int UHEIGHT =  50;// (timeTable.getLatest() - timeTable.getEarliest()) * MINHEIGHT;

  private int MINWIDTH =  500; // timeTable.nbDays * MINWIDTH;
  private int MINHEIGHT =  600;// (timeTable.getLatest() - timeTable.getEarliest()) * MINHEIGHT;
  private JLabel _x;
  public TTPanel(DModel dm) {
    super();
    _dm = dm;
    initTTPanel();
  }

  private void initTTPanel() {
    setColumnHeaderView(createColumnHeader());
    setRowHeaderView(createRowHeader());
    setViewportView(createViewPort());
  }

  public void updateTTPanel(TTStructure ttp){
    _x.setText("Change done");
  }

  private JPanel createColumnHeader() {
    JPanel panel = new JPanel(new GridLayout(1, 0));

    for (int i = 0; i < _dm.getTTStructure().getColumn() ; i++){
      //panel.add(new JLabel("Jour " + (i + 1) + " : "+ _dmgetTTStructure.getDayID(i), JLabel.CENTER));
      panel.add(new JLabel("Jour " + (i + 1) + " : "+ "lun", JLabel.CENTER));
    }
    panel.setPreferredSize(new Dimension(UWIDTH, UHEIGHT/2));

    panel.setBorder(BorderFactory.createEtchedBorder());
    return panel;
  }

  private JPanel createRowHeader() {
    JPanel panel = new JPanel(new GridLayout(0,1));

    JLabel label;
    for (int i = 0;/*timeTable.getEarliest()*/ i < _dm.getTTStructure().getRow() /*timeTable.getLatest()*/; i++) {
      label = new JLabel(Integer.toString(i) + ":00");
      label.setVerticalAlignment(JLabel.TOP);
      panel.setBorder(BorderFactory.createEtchedBorder());
      panel.add(label);
    }

    panel.setPreferredSize(new Dimension(UWIDTH/2, UHEIGHT *_dm.getTTStructure().getRow() ));  //size for the vertical panel
    return panel;
  }

  private JPanel createViewPort() {
    GridBagLayout gridbag =new GridBagLayout();
    JPanel panel =  new JPanel( gridbag );
    panel.setBackground(SystemColor.window);
    //panel.setPreferredSize(new Dimension(200, 200));
   // setPreferredSize(new Dimension(panel.getPreferredSize().width + 40,
    //                               panel.getPreferredSize().height + 50));
    int nbCols = _dm.getTTStructure().getColumn(); //timeTable.nbDays;
    gridbag.columnWeights = new double [nbCols];
    gridbag.columnWidths = new int [nbCols];
    for (int i = 0; i < nbCols; i++) {
      gridbag.columnWeights[i] = 1;
      gridbag.columnWidths[i] = 30;
    }
    int nbRows = _dm.getTTStructure().getRow();
    gridbag.rowWeights = new double [nbRows];
    gridbag.rowHeights = new int [nbRows];
    for (int i = 0; i < nbRows; i++) {
      gridbag.rowWeights[i] = 1;
      gridbag.rowHeights[i] = 20;
    }
    //_x = new JLabel ("hello");
    //panel.add(_x);
    //////////////
    /*
  //System.out.println("DTTPanel timeTable size :"+ timeTable.size());//debug
    */
    PeriodPanel period = null;
    GridBagConstraints c = null;
    int count = 0;
    for (int i = 0; i < _dm.getTTStructure().getColumn() ; i++ ) {
      for(int j = 0; j < _dm.getTTStructure().getRow(); j ++) {
        Period per = _dm.getTTStructure().getPeriod();
        //if (per != null)//debug
        //  System.out.println("DTTPanel period :"+ i);//debug
        period = new PeriodPanel(count);//_ddv, per, view, i);
        count++;
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = i;
        c.gridy = j ; //per.timeBegin.get(Calendar.HOUR_OF_DAY) - timeTable.getEarliest();
      //if ( per.timeEnd.get(Calendar.MINUTE) == 0 ){
        // nbCells = hfin - hdebut
        c.gridheight = 1;
            //per.timeEnd.get(Calendar.HOUR_OF_DAY) -
            //    per.timeBegin.get(Calendar.HOUR_OF_DAY);
       // c.insets = new Insets( per.timeBegin.get(Calendar.MINUTE)*MINHEIGHT/60,
         //       0, 0, 0 );
        c.insets = new Insets(0, 0, 0, 0 );
        gridbag.setConstraints(period, c);
        panel.add(period, c);
      } // end for
      }//end for
      /*} else {
        // nbCells = (hfin + 1) - hdebut
        c.gridheight = per.timeEnd.get(Calendar.HOUR_OF_DAY) + 1 -
                per.timeBegin.get(Calendar.HOUR_OF_DAY);
        c.insets = new Insets( per.timeBegin.get(Calendar.MINUTE)*MINHEIGHT/60,
                0, (60 - per.timeEnd.get(Calendar.MINUTE))*MINHEIGHT/60, 0);
      } */
      //}

      //perVect.add(period);
      //if (view == CONFLICT)
        //period.addMouseListener(mouseListener);
     /* else {
        period.list.addMouseListener(mouseListener);
        ((JScrollPane)period.getComponent(0)).setPreferredSize( new Dimension(
                MINWIDTH - 5, MINHEIGHT * c.gridheight - 12 - c.insets.bottom -
                c.insets.top) );
      }*/

  //}
    return panel;
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
    try{
      _ddv = ddv;
      timeTable = tt;
      perVect = new Vector();
      view = viewType;
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
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

    int minWidth = timeTable.nbDays * MINWIDTH;
    int minHeight = (timeTable.getLatest() - timeTable.getEarliest()) * MINHEIGHT;
    // create column header
    JPanel panel = new JPanel(new GridLayout(1, 0));
    //panel = confDialog();
    //panel.setPreferredSize(new Dimension(minWidth, 20));
    //setColumnHeaderView(panel);
    //panel = new JPanel(new GridLayout(1, 0));
    int nbds=0;
    for (int j=0;j< _ddv._constraint._jour.length; j++)
      if (_ddv._constraint._jour[j]==1)
        nbds++;
    for (int x = 0; x < timeTable.nbDays; x++){
      panel.add(new JLabel("Jour " + Integer.toString(x + 1)+" : "+ _ddv._constraint._nameDays[x%nbds].substring(0,3), JLabel.CENTER));
    }
    panel.setPreferredSize(new Dimension(minWidth, 20));
    setColumnHeaderView(panel);
    // create row header
    panel = new JPanel(new GridLayout(0,1));
    JLabel label;
    for (int y = timeTable.getEarliest(); y < timeTable.getLatest(); y++) {
      label = new JLabel(Integer.toString(y) + ":00");
      label.setVerticalAlignment(JLabel.TOP);
        panel.add(label);
    }
    panel.setPreferredSize(new Dimension(35, minHeight));
    setRowHeaderView(panel);

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
  private int UWIDTH =  75; // timeTable.nbDays * MINWIDTH;
  private int UHEIGHT =  50;// (timeTable.getLatest() - timeTable.getEarliest()) * MINHEIGHT;

  public PeriodPanel(int c) {
    setLayout(new GridLayout(2,1));
    setPreferredSize(new Dimension(UWIDTH, UHEIGHT));
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
    }
    else {
      setPreferredSize(new Dimension(DTTPanel.MINWIDTH, DTTPanel.MINHEIGHT));
      setBorder(new BevelBorder(BevelBorder.RAISED));
      String actNames[] = new String [period.size()];
      if ( ddv._timeTable.isCycle ) {
        for (int i = 0; i < period.size(); i++ )
          actNames[i] = ddv._dicActivities.getBloc( ((Integer)period.get(i)).intValue() ).courseName;
      } else {
        for (int i = 0; i < period.size(); i++ )
          actNames[i] = ddv._dicActivities.getEBloc( ((Integer)period.get(i)).intValue() ).courseName;
      }
      list = new JList(actNames);
      list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      list.addFocusListener(new FocusAdapter(){
        public void focusLost(FocusEvent e) {
          list.clearSelection();
        }
      });
      JScrollPane scroll = new JScrollPane( list,
              JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
              JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
     this.add(scroll);
    }
  } */
}
