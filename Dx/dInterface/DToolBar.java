package dInterface;

/**
 * Title: ToolBar $Revision: 1.17 $  $Date: 2003-07-04 10:34:19 $
 * Description: ToolBar is a class used to display a
 *               toolbar with buttons
 *
 *
 * Copyright (c) 2001 by rgr.
 * All rights reserved.
 *
 *
 * This software is the confidential and proprietary information
 * of rgr-fdl. ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with rgr-fdl.
 *
 * @version $Version$
 * @author  $Author: rgr $
 * @since JDK1.3
 */



import java.awt.*;
import java.awt.event.*;
import java.lang.Exception;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import dInternal.dTimeTable.TTStructure;
import dInternal.dTimeTable.Cycle;
import dInternal.dTimeTable.Day;
import dInternal.dTimeTable.Sequence;
import dInternal.dTimeTable.Period;
import dInternal.dTimeTable.TTStructureListener;
import dInternal.dTimeTable.TTStructureEvent;
import dInternal.dData.Resource;
import dInternal.dUtil.DXToolsMethods;

import dAux.ConfirmDlg;
import dInterface.DApplication;
import dInterface.dTimeTable.PeriodPanel;

import dResources.DConst;

import com.iLib.gDialog.FatalProblemDlg;

//-------------------------------------------
/**
 *
 * ToolBar is a class used to display a toolbar with buttons
 *
 */
public class DToolBar extends JToolBar implements TTStructureListener{// implements ActionListener{
  private DApplication _dApplic;
  private static final String _toolBarNames [] = {"Jours", "Periods"};
  JComboBox toolBarSelector, daySelector, dayNameSelector, periodSelector, periodTypeSelector;
  private boolean _comboBoxStatus = true;
  JButton sameLine, sameColumn;
  JTextField setNumberOfDays;
  JLabel lSetNumberOfDays, lDaySelector, lDayNameSelector, lPeriodIndicator, lPeriodTypeSelector;
  JToolBar.Separator jtbSep [];
  String _error = "";
  TTStructure _tts;
  //String [] _dayNames = {"Lu","Ma","Me","Je","Ve","Sa","Di"};



  //-------------------------------------------
  public DToolBar(DApplication dApp) {
    //The JLabel Objects initialisation
    _dApplic= dApp;
    init();
    actionManager();

    setEnabledToolbar(false);
  }//end constructor

  public void setcomboBoxStatus(boolean status){
    _comboBoxStatus=status;
  }

  /**
   *
   * */
  private void actionManager(){
    toolBarSelector.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        //JComboBox cb = (JComboBox)e.getSource();
        //int  i = cb.getSelectedIndex();
        int i = toolBarSelector.getSelectedIndex();
         System.out.println("ToolBar selector: " + i);//debug
         selectBar(i);
        /*switch (i){
          case 0: addBarOne(); break;
          case 1: addBarTwo(); break;
        }// end switch
        */
      }//end actionPerformed
    });//end addActionListener


    //*** Actions for the elements of the bar one

    setNumberOfDays.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String nbDays=setNumberOfDays.getText();
        if (!DXToolsMethods.isIntValue(nbDays)){
          new FatalProblemDlg(_dApplic.getJFrame(),"Bad value");
          setNumberOfDays.setText(Integer.toString(_tts.getCurrentCycle().getNumberOfDays()));
        }else{
          //int add= Integer.parseInt(nbDays);
          //_tts.getCurrentCycle().addDays(add);
          if(Integer.parseInt(nbDays)>0)
            selectAddRemoveDays(Integer.parseInt(nbDays));
          else
            new FatalProblemDlg(_dApplic.getJFrame(),"Bad value");
          //Treat event
          _tts.sendEvent();
          setToolBarOne();

        }
        System.out.println("Number of days: "+nbDays);
        //add or remove a day in a cycle
        //Cycle cycle= _tts.getCurrentCycle();

      }//end actionPerformed
    });//end addActionListener

      daySelector.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        int item = daySelector.getSelectedIndex();
        if(item!=-1){
          Resource resc= _tts.getCurrentCycle().getSetOfDays().getResourceAt(item);
          _tts.getCurrentCycle().setCurrentDayIndex(item);
          dayNameSelector.setSelectedItem(resc.getID());
        }
      }//end actionPerformed
    });//end addActionListener

    dayNameSelector.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        int index= _tts.getCurrentCycle().getCurrentDayIndex();
        Resource resc= _tts.getCurrentCycle().getSetOfDays().getResourceAt(index);
        resc.setID((String)dayNameSelector.getSelectedItem());
        //Treat event
          _tts.sendEvent();
        //setToolBar(_tts);
      }//end actionPerformed
    });//end addActionListener




    //*** Actions for the elements of the bar two

    periodSelector.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
         String item= (String)periodSelector.getSelectedItem();
         setPeriodSelector(item);

      }//end actionPerformed
    });//end addActionListener

    periodTypeSelector.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        //System.out.println("Action event: "+e.META_MASK);//

        String item= (String)periodSelector.getSelectedItem();
        PeriodPanel ppanel= _dApplic.getDMediator().getCurrentDoc().getTTPanel(
            ).getPeriodPanel(Integer.parseInt(item) );
        Period period;
        period= _tts.getPeriod(_tts.getCurrentCycle(), ppanel.getPeriodRef()[0],
                               ppanel.getPeriodRef()[1],ppanel.getPeriodRef()[2]);
        period.setPriority(periodTypeSelector.getSelectedIndex());
        if(_comboBoxStatus){
          _tts.sendEvent();
        }

      }//end actionPerformed
    });//end addActionListener


    sameLine.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String item= (String)periodSelector.getSelectedItem();
        PeriodPanel ppanel= _dApplic.getDMediator().getCurrentDoc().getTTPanel(
              ).getPeriodPanel(Integer.parseInt(item) );
        Cycle cycle = _tts.getCurrentCycle();
        Period per;
        for(int i = 0; i < cycle.getSetOfDays().size(); i++){
          per = _tts.getPeriod(cycle, i, ppanel.getPeriodRef()[1], ppanel.getPeriodRef()[2]);
          per.setPriority(periodTypeSelector.getSelectedIndex());
        }
        _tts.sendEvent();
      }//end actionPerformed
    });//end addActionListener


    sameColumn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String item= (String)periodSelector.getSelectedItem();
        PeriodPanel ppanel= _dApplic.getDMediator().getCurrentDoc().getTTPanel(
              ).getPeriodPanel(Integer.parseInt(item) );
        int dayIndex = ppanel.getPeriodRef()[0];
        Day day = _tts.getCurrentCycle().getDay(dayIndex);
        Sequence seq;
        Period per;
        for (int i = 0; i < day.getSetOfSequences().size(); i++){
          seq = day.getSequence(i);
          for (int j = 0; j < seq.getSetOfPeriods().size(); j++ ){
            per = seq.getPeriod(j);
            per.setPriority(periodTypeSelector.getSelectedIndex());
          }
        }
        _tts.sendEvent();

      }//end actionPerformed
    });//end addActionListener
  }

  /**
   *
   * */
  public void setPeriodSelector(String item){
    if(DXToolsMethods.isIntValue(item)){
      PeriodPanel ppanel= _dApplic.getDMediator().getCurrentDoc().getTTPanel(
          ).getPeriodPanel(Integer.parseInt(item) );
      Period period;
      if(ppanel!=null){

        periodSelector.setSelectedItem(Integer.toString(ppanel.getPanelRefNo()));
        period= _tts.getPeriod(_tts.getCurrentCycle(), ppanel.getPeriodRef()[0],
                               ppanel.getPeriodRef()[1],ppanel.getPeriodRef()[2]);
        periodTypeSelector.setSelectedItem(_tts._priorityTable[period.getPriority()]);
      }else{
        new FatalProblemDlg(_dApplic.getJFrame(),"Période non trouvée");
        periodSelector.setSelectedIndex(0);
      }// end if(ppanel!=null)
    }else{// end if(DXToolsMethods.isIntValue(item))
      new FatalProblemDlg(_dApplic.getJFrame(),"Bad value");
      periodSelector.setSelectedIndex(0);
    }
  }

  private void init(){
    lSetNumberOfDays = new JLabel("Nombre de jours ");
    lDaySelector = new JLabel("Jour courrant ");
    lDayNameSelector = new JLabel("Nom du jour ");
    lPeriodIndicator = new JLabel("Index Période ");
    lPeriodTypeSelector = new JLabel("Priorité Période ");

    //The JButton Objects initialisation
    sameLine = new JButton("Toute la ligne");
    sameColumn = new JButton("Toute la journée");


    //textField objects initialisation
    setNumberOfDays = new JTextField();
    setNumberOfDays.setMaximumSize(new Dimension(30, DConst.NPT11 * 2));

    //JComboBox toolBarSelector initialisation
    toolBarSelector = new JComboBox(_toolBarNames);
    toolBarSelector.setPreferredSize(new Dimension(200, DConst.NPT11* 2));
    toolBarSelector.setMaximumSize(new Dimension(200, DConst.NPT11 * 3));
    //toolBarSelector.updateUI();
    //toolBarSelector.setEnabled(false);
    add(toolBarSelector);

    //JComboBox daySelector initialisation
    String [] amountDays = {"1","2","3","4","5","6","7"};//debug
    daySelector = new JComboBox(amountDays);
    daySelector.setPreferredSize(new Dimension(50,DConst.NPT11 * 2));
    daySelector.setMaximumSize(new Dimension(50,DConst.NPT11 * 2));
    //daySelector.setEditable(true);
    //System.out.println("Day selector size: "+daySelector.getComponentCount());//debug

    //JComboBox dayNameSelector initialisation
    dayNameSelector = new JComboBox(TTStructure._weekTable);
    dayNameSelector.setPreferredSize(new Dimension(50,DConst.NPT11 * 2));
    dayNameSelector.setMaximumSize(new Dimension(50,DConst.NPT11 * 2));
    //dayNameSelector.setEditable(true);

    //JComboBox periodIndicator initialisation
    String [] periodIndexes = {"1","2","3","4","5","6","7"};
    periodSelector = new JComboBox(periodIndexes);
    periodSelector.setPreferredSize(new Dimension(50,DConst.NPT11 * 2));
    periodSelector.setMaximumSize(new Dimension(50,DConst.NPT11 * 2));
    periodSelector.setEditable(true);

    //JComboBox periodTypeSelector initialisation
    String [] periodTypes = {"Base Priorité","Normal","Null"};
    periodTypeSelector = new JComboBox(periodTypes);
    periodTypeSelector.setPreferredSize(new Dimension(100, DConst.NPT11 * 2));
    periodTypeSelector.setMaximumSize(new Dimension(100, DConst.NPT11 * 2));
    //periodTypeSelector.setEditable(true);

    toolBarSelector.setSelectedIndex(0);
    addBarOne();// index 0
  }

  /**
  *
   */
  public void setToolBars(TTStructure ttStruct){
    _tts= ttStruct;
    /***
    * test ttstruc even
    * */
    _tts.addTTStructureListener(this);
    setToolBarOne();
    setToolBarTwo();
  }

  /**
   *
   * */
  public void selectBar(int choice){
    switch (choice){
         case 0: addBarOne();
           //setToolBarOne();
           toolBarSelector.setSelectedIndex(0);
           break;
         case 1: addBarTwo();
           //setToolBarTwo();
           toolBarSelector.setSelectedIndex(1);
           break;
        }// end switch
  }

  /**
  *
   */
  public void setToolBarOne(){
    int nbDays = _tts.getCurrentCycle().getNumberOfDays(); //getNumberOfDays(ttStruct.getCurrentCycle());
    setNumberOfDays.setText(Integer.toString(nbDays));
    String [] amountDays= new String[nbDays];
    String [] nameDays= new String[nbDays];
    Resource resc;
    daySelector.removeAllItems();
    for (int i=0; i< nbDays; i++){
      resc= _tts.getCurrentCycle().getSetOfDays().getResourceAt(i);
      amountDays[i]=Integer.toString((int)resc.getKey());
      daySelector.addItem(amountDays[i]);
    }
    System.out.println("Day selector size: "+daySelector.getItemCount());//debug
    daySelector.setSelectedIndex(0);
    resc= _tts.getCurrentCycle().getSetOfDays().getResourceAt(0);
    dayNameSelector.setSelectedItem(resc.getID());
    //System.out.println("Day selected index: "+daySelector.getSelectedIndex());//debug

    //
    setEnabledToolbar(true);
  }

  /**
  *
   */
  public void setToolBarTwo(){
    _comboBoxStatus=false;
    JPanel ttPanel= (JPanel)_dApplic.getDMediator().getCurrentDoc().getTTPanel(
        ).getViewport().getComponent(0);
    //int nbOfPeriods= ttPanel.getComponentCount();
    periodSelector.removeAllItems();
    for (int i=0; i< ttPanel.getComponentCount(); i++){
      PeriodPanel ppanel= (PeriodPanel)ttPanel.getComponent(i);
      periodSelector.addItem(Integer.toString(ppanel.getPanelRefNo()));
    }// end for (int i=0; i< ttPanel.getComponentCount(); i++)

    periodTypeSelector.removeAllItems();
    for (int i=0; i< _tts._priorityTable.length; i++)
      periodTypeSelector.addItem(_tts._priorityTable[i]);
    //System.out.println("Nb of viewPorts: "+ttPanel.getComponentCount());//debug
    _comboBoxStatus=true;
  }

  /**
   *
   * */
  public void setEnabledToolbar(boolean state){
    for (int i=0; i< getComponentCount(); i++)
      getComponentAtIndex(i).setEnabled(state);
  }

  //-------------------------------------------
  private void addBarOne() {
    removeBar();
    addSeparator();
    add(lSetNumberOfDays);
    add(setNumberOfDays);
    addSeparator();
    add(lDaySelector);
    add(daySelector);
    addSeparator();
    add(lDayNameSelector);
    add(dayNameSelector);

    repaint();
  }//end methode

  //-------------------------------------------
  private void addBarTwo() {
    //removeBarOne();
    removeBar();
    addSeparator();
    add(lPeriodIndicator);
    add(periodSelector);
    addSeparator();
    add(lPeriodTypeSelector);
    add(periodTypeSelector);
    addSeparator();
    add(sameColumn);
    addSeparator();
    add(sameLine);

    repaint();
  }//end addBarTwo()

  //-------------------------------------------

  private void removeBar() {
    removeAll();
    add(toolBarSelector);

    repaint();
  }//end removeBarTwo()

  /**
   * */
  private void selectAddRemoveDays(int nbDays){
    int signe= nbDays-_tts.getCurrentCycle().getNumberOfDays();
    if (signe>0){
      if (ConfirmDlg.showMessage(_dApplic,"Voulez-vous ajouter "+ signe + " jour(s)")== ConfirmDlg.OK_OPTION)
        _tts.getCurrentCycle().addDays(signe);
    }else{// else  if (signe>0)
      if(signe<0){
        if (ConfirmDlg.showMessage(_dApplic,"Voulez-vous supprimer "+ (-signe) + " jour(s)")== ConfirmDlg.OK_OPTION)
          _tts.getCurrentCycle().removeDays(-signe);
      }// end if(signe<0)

    }// end else  if (signe>0)
  }

  public void changeInTTStructure(TTStructureEvent  e) {
     //System.out.println("Toolbar change In TTSturtutre and Update TTpanel");
     //_dApplic.getDMediator().getCurrentDoc().getTTPanel().updateTTPanel(_tts);
    }// end actionPerformed


} // end classe
