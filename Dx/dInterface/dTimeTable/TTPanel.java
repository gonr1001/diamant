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
//import java.awt.Color;
import javax.swing.JLabel;
import java.util.*;
import java.awt.*;
import javax.swing.BorderFactory;
import javax.swing.border.*;
import java.awt.event.*;

import dInterface.DToolBar;

import dInternal.DModel;
import dInternal.dData.Resource;
import dInternal.dTimeTable.*;


public class TTPanel extends JScrollPane {
  private DModel _dm;
  //private int MINHEIGHT = 60;
  private int HHEIGHT =  24; // timeTable.nbDays * MINWIDTH;
  private int VWIDTH =  36; // timeTable.nbDays * MINWIDTH;
  private int UWIDTH =  100; // timeTable.nbDays * MINWIDTH;
  private int UHEIGHT =  60;// (timeTable.getLatest() - timeTable.getEarliest()) * MINHEIGHT;
  private int MinWidth=80;
  private int MinHeight=80;
  private int LASTHOUR=8;

  private int _periodLenght;
  private MouseListener _mouseListener;
  private PeriodPanel _lastActivPpanel=null;

  public TTPanel(DModel dm) {
    super();
    _dm = dm;
    if(_dm.getTTStructure()!=null){
      initTTPanel();
    }
  }

  private void initTTPanel() {
    _periodLenght= _dm.getTTStructure().getPeriodLenght();
    Cycle cycle =_dm.getTTStructure().getCurrentCycle();
    MinWidth = cycle.getNumberOfDays() * UWIDTH;
    Period lastPeriod = _dm.getTTStructure().getLastPeriod(cycle);
    LASTHOUR=lastPeriod.getEndHour(_periodLenght)[0];
    if(lastPeriod.getEndHour(_periodLenght)[1]!=0)
      LASTHOUR++;
    System.out.println("last Hour: "+ LASTHOUR+":"+lastPeriod.getEndHour(_periodLenght)[1]);//debug
    MinHeight = (LASTHOUR-_dm.getTTStructure().getFirstPeriod(cycle).getBeginHour()[0]) * UHEIGHT;

    Point point=new Point(0,0);
    point = getViewport().getViewPosition();//getViewport().getsc
    setColumnHeaderView(createColumnHeader());
    setRowHeaderView(createRowHeader());
    setViewportView(createViewPort());
    getViewport().setViewPosition(point);
    manageActions();
  }

  /**
   *
   * */
  private void manageActions(){
    //JPanel ttPanel= (JPanel)this.getViewport().getComponent(0);
    /*
     * Mouse listener for this Panel
     */
     _mouseListener = new MouseAdapter() {

      public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 1) {
          PeriodPanel perpanel= (PeriodPanel)e.getSource();
          if(_lastActivPpanel!=null)
            _lastActivPpanel.setPanelBackGroundColor(0);
          _dm.getDApplication().getToolBar().setcomboBoxStatus(false);
          _dm.getDApplication().getToolBar().setPeriodSelector(Integer.toString(perpanel.getPanelRefNo()));
          _dm.getDApplication().getToolBar().selectBar(1);
           perpanel.setPanelBackGroundColor(1);
           _dm.getDApplication().getToolBar().setcomboBoxStatus(true);
          _lastActivPpanel=perpanel;
         System.out.println("Un clic sur la periode: "+ perpanel.getPanelRefNo()+" Contains: "
                            +_dm.getTTStructure().getPeriod(_dm.getTTStructure(
                            ).getCurrentCycle(),perpanel.getPeriodRef()[0],perpanel.getPeriodRef()[1],perpanel.getPeriodRef()[2]).toString());//debug
        }
      }
    };
  }

  public void updateTTPanel(TTStructure ttp){
    initTTPanel();
  }

  private JPanel createColumnHeader() {
    JPanel panel = new JPanel(new GridLayout(1, 0));
    Cycle cycle =_dm.getTTStructure().getCurrentCycle();
    for (int i = 0; i < cycle.getSetOfDays().size() ; i++){
      Resource day = cycle.getSetOfDays().getResourceAt(i);
      panel.add(new JLabel("J " + (i + 1) + " : "+ day.getID(), JLabel.CENTER));
    }
    panel.setPreferredSize(new Dimension(UWIDTH *cycle.getSetOfDays().size(), HHEIGHT));
    panel.setBorder(BorderFactory.createEtchedBorder());
    return panel;
  }

  private JPanel createRowHeader() {
    JPanel panel = new JPanel(new GridLayout(0,1));
    Cycle cycle =_dm.getTTStructure().getCurrentCycle();
    Day day = _dm.getTTStructure().getCurrentCycle().getCurrentDay();
    int numbOfSequences = day.getSetOfSequences().size();
    JLabel label, label1;
    Period firstPer= _dm.getTTStructure().getFirstPeriod(cycle);
    for (int i = firstPer.getBeginHour()[0]; i < LASTHOUR; i++) {
      label = new JLabel(Integer.toString(i) + ":00");
      //System.out.println("TTPanel Row header viewlabel.getText()" + label.getText());//Debug
      label.setVerticalAlignment(JLabel.TOP);
      panel.setBorder(BorderFactory.createEtchedBorder());
      panel.add(label);
     }
     panel.setPreferredSize(new Dimension(35, MinHeight));
     return panel;
  }

  private JPanel createViewPort() {
    Cycle cycle =_dm.getTTStructure().getCurrentCycle();//.getCycle(_dm.getCurrentCycle());
    GridBagLayout gridbag =new GridBagLayout();
    JPanel panel =  new JPanel( gridbag );
    panel.setBackground(SystemColor.window);
    panel.setPreferredSize(new Dimension(MinWidth, MinHeight));
    setPreferredSize(new Dimension(panel.getPreferredSize().width + 40,
                                   panel.getPreferredSize().height + 50));
    int nbDays,nbSeq,nbPerADay;
    nbDays = cycle.getSetOfDays().size();
    System.out.println("nb of Days: " + nbDays);//Debug
    gridbag.columnWeights = new double [nbDays];
    gridbag.columnWidths = new int [nbDays];
    for (int i = 0; i < nbDays; i++) {
      gridbag.columnWeights[i] = 1;
      gridbag.columnWidths[i] = UWIDTH;
    }
    nbPerADay = LASTHOUR-_dm.getTTStructure().getFirstPeriod(cycle).getBeginHour()[0];
    gridbag.rowWeights = new double [nbPerADay];
    gridbag.rowHeights = new int [nbPerADay];
    for (int i = 0; i < nbPerADay; i++) {
      gridbag.rowWeights[i] = 1;
      gridbag.rowHeights[i] = UHEIGHT;
    }
    PeriodPanel periodPanel = null;
    GridBagConstraints c = null;
    nbSeq = _dm.getTTStructure().getMaxNumberOfSeqs(cycle);
    int count = 1;
    for (int i = 0; i < nbDays ; i++ ) {
      Day day= _dm.getTTStructure().getDay(cycle, i+1);
      for(int j = 0; j < nbSeq; j ++) {
        Sequence sequence= _dm.getTTStructure().getSequence(day,j+1);
        for(int k = 0; k < sequence.getSetOfPeriods().size(); k ++) {
          Period period= _dm.getTTStructure().getPeriod(sequence,k+1);
          periodPanel = new PeriodPanel(count,i,j,k);
          periodPanel.addMouseListener(_mouseListener);
          periodPanel.createPanel(period,UWIDTH, UHEIGHT);
          count++;
          c = new GridBagConstraints();
          c.fill = GridBagConstraints.BOTH;
          c.gridx = i;
          c.gridy = (period.getBeginHour()[0] - _dm.getTTStructure().getFirstPeriod(cycle).getBeginHour()[0]);//period.getEndHour(_periodLenght)[0];

          if ( period.getEndHour(_periodLenght)[1] == 0 ){
            c.gridheight = period.getEndHour(_periodLenght)[0]- period.getBeginHour()[0];
            c.insets = new Insets( period.getBeginHour()[1], 0, 0, 0 );
          } else {
            c.gridheight = period.getEndHour(_periodLenght)[0] + 1 - period.getBeginHour()[0];
            c.insets = new Insets( period.getBeginHour()[1], 0,(UHEIGHT- period.getEndHour(_periodLenght)[1]), 0 );
          }
          gridbag.setConstraints(periodPanel, c);
          panel.add(periodPanel, c);
        }//end for k
      } // end for j
    }//end for i
     return panel;
  }

  /**
   * @param int the period panel reference
   * */
  public PeriodPanel getPeriodPanel(int ppRef){
    JPanel thePanel= (JPanel)getViewport().getComponent(0);
    for (int i=0; i< thePanel.getComponentCount(); i++){
      PeriodPanel ppanel= (PeriodPanel)thePanel.getComponent(i);
      if(ppanel.getPanelRefNo()==ppRef)
        return ppanel;
    }
    return null;
  }

}



