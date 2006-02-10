package dInterface;

/**
 *
 * Title: DToolBar
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
 * @since JDK1.3
 */

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import dConstants.DConst;
import dInterface.dTimeTable.PeriodPanel;
import dInterface.dUtil.DXJComboBox;
import dInternal.DResource;
import dInternal.dTimeTable.Cycle;
import dInternal.dTimeTable.Day;
import dInternal.dTimeTable.Period;
import dInternal.dTimeTable.Sequence;
import dInternal.dTimeTable.TTStructure;

import dInternal.dUtil.DXToolsMethods;
import eLib.exit.dialog.InformationDlg;

//-------------------------------------------
/**
 *
 * DToolBar is a class used to display aad handle two toolbars with buttons
 *  and JComboBoxes.
 *  One Toolbar is used to make changes in day and the other on periods
 *
 */
public class DToolBar extends JToolBar  implements Observer { // ActionListener
  private DApplication _dApplic;
  private static final String _toolBarNames [] = {DConst.TB_DAYS, DConst.TB_PER};
  private JComboBox _toolBarSelector;
  private DXJComboBox _daySelector, _dayNameSelector, _periodSelector, _periodTypeSelector;
  private boolean _comboBoxStatus = true;
  private JButton _sameLine, _sameColumn;
  private JTextField _setNumberOfDays;
  private JLabel _lSetNumberOfDays, _lDaySelector, _lDayNameSelector,
                 _lPeriodIndicator, _lPeriodTypeSelector;
  private TTStructure _tts;

  //-------------------------------------------
  public DToolBar(DApplication dApplic) {
    _dApplic= dApplic;
    init();
    actionManager();
    setEnabledToolbar(false);
  }//end constructor


  private void init(){
    int c = 2;
    // the labels in the bar
    _lSetNumberOfDays = new JLabel("Nombre de jours ");
    _lDaySelector = new JLabel("Jour courant ");
    _lDayNameSelector = new JLabel("Nom du jour ");

    _lPeriodIndicator = new JLabel("Index Période ");
    _lPeriodTypeSelector = new JLabel("Priorité Période ");

    //JComboBox toolBarSelector initialisation
    _toolBarSelector = new JComboBox(_toolBarNames);
    _toolBarSelector.setPreferredSize(new Dimension(200, DConst.NPT11* c ));
    _toolBarSelector.setMaximumSize(new Dimension(200, DConst.NPT11 * c));
    add(_toolBarSelector);

    // textField set number of days in ttable Structure
    _setNumberOfDays = new JTextField();
    _setNumberOfDays.setMaximumSize(new Dimension(30, DConst.NPT11 * c));


    //JComboBox daySelector initialisation
    String [] daySelector = {"1","2","3","4","5","6","7"};
    _daySelector = new DXJComboBox(daySelector);
    _daySelector.setPreferredSize(new Dimension(50,DConst.NPT11 * c));
    _daySelector.setMaximumSize(new Dimension(50,DConst.NPT11 * c));

    //JComboBox dayNameSelector initialisation
    _dayNameSelector = new DXJComboBox(TTStructure._weekTable);
    _dayNameSelector.setPreferredSize(new Dimension(50,DConst.NPT11 * c));
    _dayNameSelector.setMaximumSize(new Dimension(50,DConst.NPT11 * c));

    //JComboBox periodSelector initialisation
    String [] periodSelector = {"1","2","3","4","5","6","7"};
    _periodSelector = new DXJComboBox(periodSelector); 
    _periodSelector.setPreferredSize(new Dimension(50,DConst.NPT11 * c));
    _periodSelector.setMaximumSize(new Dimension(50,DConst.NPT11 * c));
    //_periodSelector.setEditable(true);


    String [] periodTypes = {"B","N","Z"};
    _periodTypeSelector = new DXJComboBox(periodTypes);
    _periodTypeSelector.setPreferredSize(new Dimension(100, DConst.NPT11 * c));
    _periodTypeSelector.setMaximumSize(new Dimension(100, DConst.NPT11 * c));


    //The JButton Objects initialisation
    _sameLine = new JButton("Toute la ligne");
    _sameColumn = new JButton("Toute la journée");
    //JComboBox periodTypeSelector initialisation
    addBarOne(); addBarTwo();
    _toolBarSelector.setSelectedIndex(0);
    selectBar(0);// index 0
  }

  public void setComboBoxStatus(boolean status){
    _comboBoxStatus = status;
  }

  /**
   *
   * */
  private void actionManager(){
    _toolBarSelector.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
      	e.toString();
        int i = _toolBarSelector.getSelectedIndex();
        selectBar(i);
      }//end actionPerformed
    });//end addActionListener

    //*** Actions for the elements of the bar one
    // * _setNumberOfDays
    _setNumberOfDays.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
      	e.toString();
        String nbDays=_setNumberOfDays.getText();
        if (!DXToolsMethods.isIntValue(nbDays)){
          new InformationDlg(_dApplic.getJFrame(),"Valeur eronnée");
          _setNumberOfDays.setText(Integer.toString(_tts.getCurrentCycle().getNumberOfDays()));
        } else{
          if(Integer.parseInt(nbDays) > 0)
            selectAddRemoveDays(Integer.parseInt(nbDays));
          else
            new InformationDlg(_dApplic.getJFrame(),"Valeur eronnée");
          //Treat event
          //_dApplic.getDModel().getTTStructure().sendEvent();
          _dApplic.getDModel().changeInDModelByToolBar(this);
          setToolBarOne();
          setToolBarTwo();
        }
        //System.out.println("Number of days: "+nbDays);
      }//end actionPerformed
    });//end addActionListener


    // * _daySelector
    _daySelector.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
      	e.toString();
        int item = _daySelector.getSelectedIndex();
        //if( item != -1 ){
          DResource resc= _tts.getCurrentCycle().getSetOfDays().getResourceAt(item);
          _tts.getCurrentCycle().setCurrentDayIndex(item);

          _dayNameSelector.disableActionListeners();
          _dayNameSelector.setSelectedItem(resc.getID());
          _dayNameSelector.enableActionListeners();
        //}
      }//end actionPerformed
    });//end addActionListener

    // * _dayNameSelector
    _dayNameSelector.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
      	e.toString();
        int index= _tts.getCurrentCycle().getCurrentDayIndex();
        DResource resc= _tts.getCurrentCycle().getSetOfDays().getResourceAt(index);
        resc.setID((String)_dayNameSelector.getSelectedItem());
        //System.out.println("DToolbar.dayNameSelector");//debug
        //Treat event
        _dApplic.getDModel().changeInDModelByToolBar(this);
      }//end actionPerformed
    });//end addActionListener




    //*** Actions for the elements of the bar two
    _periodSelector.addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
       // int item = _periodSelector.getSelectedIndex();
       // if(item!=-1){
      	e.toString();
        String str= (String)_periodSelector.getSelectedItem();
        setPeriodSelector(str);
       // }
      }//end actionPerformed
    });//end addActionListener

    _periodTypeSelector.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
      	e.toString();
        String item= (String)_periodSelector.getSelectedItem();
        PeriodPanel ppanel= _dApplic.getDMediator().getCurrentDoc().getTTPane(
                                ).getPeriodPanel(Integer.parseInt(item) );
        Period period;
        period = _tts.getCurrentCycle().getPeriodByIndex( ppanel.getPeriodRef()[0],
                                                         ppanel.getPeriodRef()[1],
                                                         ppanel.getPeriodRef()[2]);
        period.setPriority(_periodTypeSelector.getSelectedIndex());
        if(_comboBoxStatus){

        	_dApplic.getDModel().changeInDModelByToolBar(this);
        }

      }//end actionPerformed
    });//end addActionListener

    _sameLine.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
      	e.toString();
        String item= (String)_periodSelector.getSelectedItem();
        PeriodPanel ppanel= _dApplic.getDMediator().getCurrentDoc().getTTPane(
            ).getPeriodPanel(Integer.parseInt(item) );
        Cycle cycle = _tts.getCurrentCycle();
        Period period;
        for(int i = 0; i < cycle.getSetOfDays().size(); i++){
          period = cycle.getPeriodByIndex( i, ppanel.getPeriodRef()[1], ppanel.getPeriodRef()[2]);
          period.setPriority(_periodTypeSelector.getSelectedIndex());
        }

        _dApplic.getDModel().changeInDModelByToolBar(this);
      }//end actionPerformed
    });//end addActionListener

    _sameColumn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
      	e.toString();
        String item= (String)_periodSelector.getSelectedItem();
        PeriodPanel ppanel= _dApplic.getDMediator().getCurrentDoc().getTTPane(
            ).getPeriodPanel(Integer.parseInt(item) );
        int dayIndex = ppanel.getPeriodRef()[0];
        Day day = _tts.getCurrentCycle().getDayByIndex(dayIndex);
        Sequence seq;
        Period period;
        for (int i = 0; i < day.getSetOfSequences().size(); i++){
          seq = day.getSequenceByIndex(i);
          for (int j = 0; j < seq.getSetOfPeriods().size(); j++ ){
            period = seq.getPeriodByIndex(j);
            period.setPriority(_periodTypeSelector.getSelectedIndex());
          }
        }

        _dApplic.getDModel().changeInDModelByToolBar(this);

      }//end actionPerformed
    });//end addActionListener
  }

  /**
   *
   * */
  public void setPeriodSelector(String item){
    if(DXToolsMethods.isIntValue(item)){
      PeriodPanel ppanel= _dApplic.getDMediator().getCurrentDoc().getTTPane(
          ).getPeriodPanel(Integer.parseInt(item) );
      Period period;
      if(ppanel!=null){

        _periodSelector.setSelectedItem(Integer.toString(ppanel.getPanelRefNo()));
        period= _tts.getCurrentCycle().getPeriodByIndex( ppanel.getPeriodRef()[0],
                               ppanel.getPeriodRef()[1],ppanel.getPeriodRef()[2]);
        _periodTypeSelector.disableActionListeners();
        _periodTypeSelector.setSelectedItem(TTStructure._priorityTable[period.getPriority()]);
        _periodTypeSelector.enableActionListeners();
      }else{
        new InformationDlg(_dApplic.getJFrame(),"Période non trouvée");
        _periodSelector.setSelectedIndex(0);
      }// end if(ppanel!=null)
    }else{// end if(DXToolsMethods.isIntValue(item))
      new InformationDlg(_dApplic.getJFrame(),"Valeur eronnée");
      _periodSelector.setSelectedIndex(0);
    }
  }


  /**
  *
   */
  public void setToolBars(TTStructure ttStruct){
    _tts= ttStruct;
    setToolBarOne();
    // lgd: bug 101
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
    _daySelector.disableActionListeners();
    int nbDays = _tts.getCurrentCycle().getNumberOfDays();
    _setNumberOfDays.setText(Integer.toString(nbDays));

    String [] days = new String[nbDays];
    //String [] nameDays= new String[nbDays];
    _daySelector.removeAllItems();

    DResource resc;
    for (int i=0; i< nbDays; i++){
      resc= _tts.getCurrentCycle().getSetOfDays().getResourceAt(i);
      days[i]=Integer.toString((int)resc.getKey());
      _daySelector.addItem(days[i]);
    }
    //System.out.println("Day selector size: "+daySelector.getItemCount());//debug
    _daySelector.setSelectedIndex(0);
    //rgr resc= _tts.getCurrentCycle().getSetOfDays().getResourceAt(0);
    //_dayNameSelector.setSelectedItem(resc.getID()); //rgr
    //System.out.println("Day selected index: "+daySelector.getSelectedIndex());//debug
    _daySelector.enableActionListeners();
    setEnabledToolbar(true);
  }

  /**
  *
   */
	  public void setToolBarTwo(){
	    _periodSelector.disableActionListeners();
	    _comboBoxStatus=false;
	    JPanel thePane= (JPanel)_dApplic.getDMediator().getCurrentDoc().getTTPane(
	        ).getViewport().getComponent(0);
	    //int nbOfPeriods= ttPanel.getComponentCount();
	    _periodSelector.removeAllItems();
	    for (int i=0; i< thePane.getComponentCount(); i++){
	      PeriodPanel ppanel= (PeriodPanel)thePane.getComponent(i);
	      if(ppanel.getPanelRefNo()!=0){
	        _periodSelector.addItem(Integer.toString(ppanel.getPanelRefNo()));
	      }
	    }// end for (int i=0; i< ttPanel.getComponentCount(); i++)
		_periodTypeSelector.disableActionListeners();
	    _periodTypeSelector.removeAllItems();

	    for (int i=0; i< TTStructure._priorityTable.length; i++)
	      _periodTypeSelector.addItem(TTStructure._priorityTable[i]);
	    //System.out.println("Nb of viewPorts: "+ttPanel.getComponentCount());//debug
	    _periodTypeSelector.enableActionListeners();
	    _comboBoxStatus=true;
	    _periodSelector.enableActionListeners();
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
      if (JOptionPane.showConfirmDialog(_dApplic.getJFrame(),"Voulez-vous ajouter "+ signe + " jour(s)") == JOptionPane.OK_OPTION){
        _tts.getCurrentCycle().addDays(signe);

      }
    }else{// else  if (signe>0)
      if(signe<0){
        if (JOptionPane.showConfirmDialog(_dApplic.getJFrame(),"Voulez-vous supprimer "+ (-signe) + " jour(s)")== JOptionPane.OK_OPTION){
          _tts.getCurrentCycle().removeDays(-signe);

        }
      }// end if(signe<0)

    }// end else  if (signe>0)
  }

//  public void actionPerformed(ActionEvent e) {

 // }

/*  public void changeInTTStructure(TTStructureEvent  e) {
  	e.toString();
     //System.out.println("Toolbar change In TTSturtutre and Update TTpanel");
     //_dApplic.getDMediator().getCurrentDoc().getTTPanel().updateTTPanel(_tts);
    }// end actionPerformed
*/


  public void update(Observable o, Object arg) {
  	_dApplic.getDMediator().getCurrentDoc().update(o, arg); 	
  }


} // end classe
