package dInterface;

/**
 * Title: ToolBar $Revision: 1.28 $  $Date: 2003-08-28 00:23:02 $
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
 * @author  $Author: ysyam $
 * @since JDK1.3
 */



import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.lang.Exception;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import dInterface.dUtil.DXJComboBox;

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
  private static final String _toolBarNames [] = {DConst.TB_DAYS, DConst.TB_PER};
  private JComboBox _toolBarSelector, _daySelector, _periodSelector;
  private DXJComboBox _dayNameSelector, _periodTypeSelector;
  private boolean _comboBoxStatus = true;
  private JButton _sameLine, _sameColumn;
  private JTextField _setNumberOfDays;
  private JLabel _lSetNumberOfDays, _lDaySelector, _lDayNameSelector, _lPeriodIndicator, _lPeriodTypeSelector;
  private JToolBar.Separator _jtbSep [];
  private String _error = "";
  private TTStructure _tts;

  //-------------------------------------------
  public DToolBar(DApplication dApplic) {
    _dApplic= dApplic;
    init();
    actionManager();
    setEnabledToolbar(false);
  }//end constructor


  private void init(){
    _lSetNumberOfDays = new JLabel("Nombre de jours ");
    _lDaySelector = new JLabel("Jour courrant ");
    _lDayNameSelector = new JLabel("Nom du jour ");
    _lPeriodIndicator = new JLabel("Index Période ");
    _lPeriodTypeSelector = new JLabel("Priorité Période ");

    //The JButton Objects initialisation
    _sameLine = new JButton("Toute la ligne");
    _sameColumn = new JButton("Toute la journée");

    //textField objects initialisation
    _setNumberOfDays = new JTextField();
    _setNumberOfDays.setMaximumSize(new Dimension(30, DConst.NPT11 * 2));

    //JComboBox toolBarSelector initialisation
    _toolBarSelector = new JComboBox(_toolBarNames);
    int c = 2;
    _toolBarSelector.setPreferredSize(new Dimension(200, DConst.NPT11* c ));
    _toolBarSelector.setMaximumSize(new Dimension(200, DConst.NPT11 * c));
    add(_toolBarSelector);

    //JComboBox daySelector initialisation
    String [] amountDays = {"1","2","3","4","5","6","7"};
    _daySelector = new JComboBox(amountDays);
    _daySelector.setPreferredSize(new Dimension(50,DConst.NPT11 * c));
    _daySelector.setMaximumSize(new Dimension(50,DConst.NPT11 * c));

    //JComboBox dayNameSelector initialisation
    _dayNameSelector = new DXJComboBox(TTStructure._weekTable);
    _dayNameSelector.setPreferredSize(new Dimension(50,DConst.NPT11 * c));
    _dayNameSelector.setMaximumSize(new Dimension(50,DConst.NPT11 * c));

    //JComboBox periodIndicator initialisation
    String [] periodIndexes = {"1","2","3","4","5","6","7"};
    _periodSelector = new JComboBox(periodIndexes);
    _periodSelector.setPreferredSize(new Dimension(50,DConst.NPT11 * c));
    _periodSelector.setMaximumSize(new Dimension(50,DConst.NPT11 * c));
    _periodSelector.setEditable(true);

    //JComboBox periodTypeSelector initialisation
    String [] periodTypes = {"B","N","Z"};
    _periodTypeSelector = new DXJComboBox(periodTypes);
    _periodTypeSelector.setPreferredSize(new Dimension(100, DConst.NPT11 * c));
    _periodTypeSelector.setMaximumSize(new Dimension(100, DConst.NPT11 * c));

    _toolBarSelector.setSelectedIndex(0);
    addBarOne();// index 0
  }

  public void setComboBoxStatus(boolean status){
    _comboBoxStatus=status;
  }

  /**
   *
   * */
  private void actionManager(){
    _toolBarSelector.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        int i = _toolBarSelector.getSelectedIndex();
        selectBar(i);
      }//end actionPerformed
    });//end addActionListener

    //*** Actions for the elements of the bar one
    _setNumberOfDays.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String nbDays=_setNumberOfDays.getText();
        if (!DXToolsMethods.isIntValue(nbDays)){
          new FatalProblemDlg(_dApplic.getJFrame(),"Valeur eronnée");
          _setNumberOfDays.setText(Integer.toString(_tts.getCurrentCycle().getNumberOfDays()));
        }else{
          //int add= Integer.parseInt(nbDays);
          //_tts.getCurrentCycle().addDays(add);
          if(Integer.parseInt(nbDays)>0)
            selectAddRemoveDays(Integer.parseInt(nbDays));
          else
            new FatalProblemDlg(_dApplic.getJFrame(),"Valeur eronnée");
          //Treat event
          _dApplic.getDMediator().getCurrentDoc().getDM().getTTStructure().sendEvent();//  _tts.sendEvent();
          setToolBarOne();

        }
        //System.out.println("Number of days: "+nbDays);
        //add or remove a day in a cycle
        //Cycle cycle= _tts.getCurrentCycle();

      }//end actionPerformed
    });//end addActionListener

    //ActionListener al = new ActionListener();
    _daySelector.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        int item = _daySelector.getSelectedIndex();
        if(item!=-1){
          Resource resc= _tts.getCurrentCycle().getSetOfDays().getResourceAt(item);
          _tts.getCurrentCycle().setCurrentDayIndex(item);
         /* ActionListener [] al =_dayNameSelector.getActionListeners();
          for (int i = 0; i <al.length; i++) {
            _dayNameSelector.removeActionListener(al[i]);
          }*/
          _dayNameSelector.disableActionListener();
          _dayNameSelector.setSelectedItem(resc.getID());
          _dayNameSelector.enableActionListener();
          /*for (int i = 0; i <al.length; i++) {
            _dayNameSelector.addActionListener(al[i]);
          }*/
        }
      }//end actionPerformed
    });//end addActionListener

    _dayNameSelector.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        int index= _tts.getCurrentCycle().getCurrentDayIndex();
        Resource resc= _tts.getCurrentCycle().getSetOfDays().getResourceAt(index);
        resc.setID((String)_dayNameSelector.getSelectedItem());
        System.out.println("DToolbar.dayNameSelector");//debug
        //_dApplic.getDMediator().getCurrentDoc().setModified();
        //Treat event
        _dApplic.getDMediator().getCurrentDoc().getDM().getTTStructure().sendEvent();
        //setToolBar(_tts);
      }//end actionPerformed
    });//end addActionListener




    //*** Actions for the elements of the bar two
    _periodSelector.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String item= (String)_periodSelector.getSelectedItem();
        setPeriodSelector(item);
      }//end actionPerformed
    });//end addActionListener

    _periodTypeSelector.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String item= (String)_periodSelector.getSelectedItem();
        PeriodPanel ppanel= _dApplic.getDMediator().getCurrentDoc().getTTPanel(
            ).getPeriodPanel(Integer.parseInt(item) );
        Period period;
        period= _tts.getPeriod(_tts.getCurrentCycle(), ppanel.getPeriodRef()[0],
                               ppanel.getPeriodRef()[1],ppanel.getPeriodRef()[2]);
        period.setPriority(_periodTypeSelector.getSelectedIndex());
        if(_comboBoxStatus){
          //_dApplic.getDMediator().getCurrentDoc().setModified();
          _dApplic.getDMediator().getCurrentDoc().getDM().getTTStructure().sendEvent();
        }

      }//end actionPerformed
    });//end addActionListener


    _sameLine.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String item= (String)_periodSelector.getSelectedItem();
        PeriodPanel ppanel= _dApplic.getDMediator().getCurrentDoc().getTTPanel(
            ).getPeriodPanel(Integer.parseInt(item) );
        Cycle cycle = _tts.getCurrentCycle();
        Period per;
        for(int i = 0; i < cycle.getSetOfDays().size(); i++){
          per = _tts.getPeriod(cycle, i, ppanel.getPeriodRef()[1], ppanel.getPeriodRef()[2]);
          per.setPriority(_periodTypeSelector.getSelectedIndex());
        }
        //_dApplic.getDMediator().getCurrentDoc().setModified();
        _dApplic.getDMediator().getCurrentDoc().getDM().getTTStructure().sendEvent();
      }//end actionPerformed
    });//end addActionListener


    _sameColumn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String item= (String)_periodSelector.getSelectedItem();
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
            per.setPriority(_periodTypeSelector.getSelectedIndex());
          }
        }
        //_dApplic.getDMediator().getCurrentDoc().setModified();
        _dApplic.getDMediator().getCurrentDoc().getDM().getTTStructure().sendEvent();

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

        _periodSelector.setSelectedItem(Integer.toString(ppanel.getPanelRefNo()));
        period= _tts.getPeriod(_tts.getCurrentCycle(), ppanel.getPeriodRef()[0],
                               ppanel.getPeriodRef()[1],ppanel.getPeriodRef()[2]);
        _periodTypeSelector.disableActionListener();
        _periodTypeSelector.setSelectedItem(_tts._priorityTable[period.getPriority()]);
        _periodTypeSelector.enableActionListener();
      }else{
        new FatalProblemDlg(_dApplic.getJFrame(),"Période non trouvée");
        _periodSelector.setSelectedIndex(0);
      }// end if(ppanel!=null)
    }else{// end if(DXToolsMethods.isIntValue(item))
      new FatalProblemDlg(_dApplic.getJFrame(),"Valeur eronnée");
      _periodSelector.setSelectedIndex(0);
    }
  }


  /**
  *
   */
  public void setToolBars(TTStructure ttStruct){
    _tts= ttStruct;
    setToolBarOne();
    setToolBarTwo();
  }

  /**
   *
   * */
  private void selectBar(int choice){
    switch (choice){
         case 0: addBarOne();
           _toolBarSelector.setSelectedIndex(0);
           break;
         case 1: addBarTwo();
           _toolBarSelector.setSelectedIndex(1);
           break;
        }// end switch
  }

  /**
  *
   */
  public void setToolBarOne(){
    int nbDays = _tts.getCurrentCycle().getNumberOfDays(); //getNumberOfDays(ttStruct.getCurrentCycle());
    _setNumberOfDays.setText(Integer.toString(nbDays));
    String [] amountDays= new String[nbDays];
    String [] nameDays= new String[nbDays];
    Resource resc;
    _daySelector.removeAllItems();
    for (int i=0; i< nbDays; i++){
      resc= _tts.getCurrentCycle().getSetOfDays().getResourceAt(i);
      amountDays[i]=Integer.toString((int)resc.getKey());
      _daySelector.addItem(amountDays[i]);
    }
    //System.out.println("Day selector size: "+daySelector.getItemCount());//debug
    _daySelector.setSelectedIndex(0);
    //rgr resc= _tts.getCurrentCycle().getSetOfDays().getResourceAt(0);
    //_dayNameSelector.setSelectedItem(resc.getID()); //rgr
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
    _periodSelector.removeAllItems();
    for (int i=0; i< ttPanel.getComponentCount(); i++){
      PeriodPanel ppanel= (PeriodPanel)ttPanel.getComponent(i);
      _periodSelector.addItem(Integer.toString(ppanel.getPanelRefNo()));
    }// end for (int i=0; i< ttPanel.getComponentCount(); i++)

    _periodTypeSelector.removeAllItems();
    for (int i=0; i< _tts._priorityTable.length; i++)
      _periodTypeSelector.addItem(_tts._priorityTable[i]);
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
    add(_lSetNumberOfDays);
    add(_setNumberOfDays);
    addSeparator();
    add(_lDaySelector);
    add(_daySelector);
    addSeparator();
    add(_lDayNameSelector);
    add(_dayNameSelector);
    repaint();
  }//end methode

  //-------------------------------------------
  private void addBarTwo() {
    removeBar();
    addSeparator();
    add(_lPeriodIndicator);
    add(_periodSelector);
    addSeparator();
    add(_lPeriodTypeSelector);
    add(_periodTypeSelector);
    addSeparator();
    add(_sameColumn);
    addSeparator();
    add(_sameLine);
    repaint();
  }//end addBarTwo()

  //-------------------------------------------

  private void removeBar() {
    removeAll();
    add(_toolBarSelector);
    repaint();
  }//end removeBar()

  /**
   * */
  private void selectAddRemoveDays(int nbDays){
    int signe= nbDays-_tts.getCurrentCycle().getNumberOfDays();
    if (signe>0){
      if (ConfirmDlg.showMessage(_dApplic,"Voulez-vous ajouter "+ signe + " jour(s)")== ConfirmDlg.OK_OPTION){
        _tts.getCurrentCycle().addDays(signe);
        //_dApplic.getDMediator().getCurrentDoc().setModified();
      }
    }else{// else  if (signe>0)
      if(signe<0){
        if (ConfirmDlg.showMessage(_dApplic,"Voulez-vous supprimer "+ (-signe) + " jour(s)")== ConfirmDlg.OK_OPTION){
          _tts.getCurrentCycle().removeDays(-signe);
          //_dApplic.getDMediator().getCurrentDoc().setModified();
        }
      }// end if(signe<0)

    }// end else  if (signe>0)
  }

  public void changeInTTStructure(TTStructureEvent  e) {
     //System.out.println("Toolbar change In TTSturtutre and Update TTpanel");
     //_dApplic.getDMediator().getCurrentDoc().getTTPanel().updateTTPanel(_tts);
    }// end actionPerformed


} // end classe
