package dInterface;

/**
 * Title: ToolBar $Revision: 1.11 $  $Date: 2003-06-13 17:02:09 $
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
import dInternal.dData.Resource;
import dInternal.dUtil.DXToolsMethods;

import dInterface.DApplication;

import dResources.DConst;

import com.iLib.gDialog.FatalProblemDlg;

//-------------------------------------------
/**
 *
 * ToolBar is a class used to display a toolbar with buttons
 *
 */
public class DToolBar extends JToolBar {// implements ActionListener{
  private DApplication _dApplic;
  private static final String _toolBarNames [] = {"Jours", "Periods"};
  JComboBox toolBarSelector, daySelector, dayNameSelector, periodSelector, periodTypeSelector;
  JButton stdDays, addDay, removeDay, sameLine, sameColumn;
  JTextField setNumberOfDays;
  JLabel lSetNumberOfDays, lDaySelector, lDayNameSelector, lPeriodIndicator, lPeriodTypeSelector;
  JToolBar.Separator jtbSep [];
  String _error="";
  TTStructure _tts;
  //String [] _dayNames = {"Lu","Ma","Me","Je","Ve","Sa","Di"};



  //-------------------------------------------
  public DToolBar(DApplication dApp) {
    //The JLabel Objects initialisation
    _dApplic= dApp;
    jbInit();
    actionManager();
    setEnabledToolbar(false);
  }//end constructor

  /**
   *
   * */
  private void actionManager(){
    toolBarSelector.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        //JComboBox cb = (JComboBox)e.getSource();
        //int  i = cb.getSelectedIndex();
        int i = toolBarSelector.getSelectedIndex();
         System.out.println("ToolBar selector: "+i);//debug
        switch (i){
          case 0: addBarOne(); break;
          case 1: addBarTwo(); break;
        }// end switch
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

          //test
          _dApplic.getDMediator().getCurrentDoc().getTTPanel().updateTTPanel(_tts);
          setToolBar(_tts);

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
          dayNameSelector.setSelectedItem(resc.getID());
        }
      }//end actionPerformed
    });//end addActionListener

    dayNameSelector.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {

      }//end actionPerformed
    });//end addActionListener

    stdDays.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {

      }//end actionPerformed
    });//end addActionListener

    addDay.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {

      }//end actionPerformed
    });//end addActionListener

    removeDay.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {

      }//end actionPerformed
    });//end addActionListener


    //*** Actions for the elements of the bar two

    periodSelector.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {

      }//end actionPerformed
    });//end addActionListener

    periodTypeSelector.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {

      }//end actionPerformed
    });//end addActionListener


    sameLine.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {

      }//end actionPerformed
    });//end addActionListener


    sameColumn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {

      }//end actionPerformed
    });//end addActionListener
  }

  private void jbInit(){
    lSetNumberOfDays = new JLabel("Nombre de jours ");
    lDaySelector = new JLabel("Jour courrant ");
    lDayNameSelector = new JLabel("Nom du jour ");
    lPeriodIndicator = new JLabel("Index Période ");
    lPeriodTypeSelector = new JLabel("Type Période ");

    //The JButton Objects initialisation
    stdDays = new JButton("Standard");
    addDay = new JButton("Ajouter");
    removeDay = new JButton("Supprimer");

    sameLine = new JButton("Toute la journée");
    sameColumn = new JButton("Toute la ligne");


    //textField objects initialisation
    setNumberOfDays = new JTextField();
    setNumberOfDays.setMaximumSize(new Dimension(30, DConst.NPT11 * 2));

    //JComboBox toolBarSelector initialisation
    toolBarSelector = new JComboBox(_toolBarNames);
    toolBarSelector.setPreferredSize(new Dimension(200,DConst.NPT11* 2));
    toolBarSelector.setMaximumSize(new Dimension(200,DConst.NPT11 * 3));
    //toolBarSelector.setEnabled(false);
    add(toolBarSelector);

    //JComboBox daySelector initialisation
    //String [] amountDays = {"1","2","3","4","5","6","7"};//debug
    daySelector = new JComboBox();
    daySelector.setPreferredSize(new Dimension(50,DConst.NPT11 * 2));
    daySelector.setMaximumSize(new Dimension(50,DConst.NPT11 * 2));
    //daySelector.setEditable(true);
    //System.out.println("Day selector size: "+daySelector.getComponentCount());//debug

    //JComboBox dayNameSelector initialisation
    dayNameSelector = new JComboBox(TTStructure._weekTable);
    dayNameSelector.setPreferredSize(new Dimension(50,DConst.NPT11 * 2));
    dayNameSelector.setMaximumSize(new Dimension(50,DConst.NPT11 * 2));
    dayNameSelector.setEditable(true);

    //JComboBox periodIndicator initialisation
    //String [] periodIndexes = {"1","2","3","4","5","6","7"};
    periodSelector = new JComboBox(new String[1]);
    periodSelector.setPreferredSize(new Dimension(50,DConst.NPT11 * 2));
    periodSelector.setMaximumSize(new Dimension(50,DConst.NPT11 * 2));
    periodSelector.setEditable(true);

    //JComboBox periodTypeSelector initialisation
    //String [] periodTypes = {"Base Priorité","Normal","Null"};
    periodTypeSelector = new JComboBox(new String[1]);
    periodTypeSelector.setPreferredSize(new Dimension(100,DConst.NPT11 * 2));
    periodTypeSelector.setMaximumSize(new Dimension(100,DConst.NPT11 * 2));
    periodTypeSelector.setEditable(true);

    toolBarSelector.setSelectedIndex(0);
    addBarOne();// index 0
  }

  /**
  *
   */
  public void setToolBar(TTStructure ttStruct){
    _tts= ttStruct;
    int nbDays = ttStruct.getCurrentCycle().getNumberOfDays(); //getNumberOfDays(ttStruct.getCurrentCycle());
    setNumberOfDays.setText(Integer.toString(nbDays));
    String [] amountDays= new String[nbDays];
    String [] nameDays= new String[nbDays];
    Resource resc;
    daySelector.removeAllItems();
    for (int i=0; i< nbDays; i++){
      resc= ttStruct.getCurrentCycle().getSetOfDays().getResourceAt(i);
      amountDays[i]=Integer.toString((int)resc.getKey());
      daySelector.addItem(amountDays[i]);
    }
    System.out.println("Day selector size: "+daySelector.getItemCount());//debug
    daySelector.setSelectedIndex(0);
    resc= ttStruct.getCurrentCycle().getSetOfDays().getResourceAt(0);
    dayNameSelector.setSelectedItem(resc.getID());
    //System.out.println("Day selected index: "+daySelector.getSelectedIndex());//debug

    //
    setEnabledToolbar(true);
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
    add(stdDays);
    addSeparator();
    add(addDay);
    addSeparator();
    add(removeDay);

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
    add(sameLine);
    addSeparator();
    add(sameColumn);

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
      _tts.getCurrentCycle().addDays(signe);
    }else{// else  if (signe>0)
      if(signe<0){
        _tts.getCurrentCycle().removeDays(-signe);
      }// end if(signe<0)

    }// end else  if (signe>0)
  }


} // end classe
