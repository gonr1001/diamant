package dInterface;

/**
 * Title: ToolBar $Revision: 1.7 $  $Date: 2003-06-12 10:22:08 $
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
 * @author  $Author: alexj $
 * @since JDK1.3
 */



import java.awt.*;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JToolBar.Separator;


import dInternal.dTimeTable.TTStructure;

import dResources.DConst;
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



  //-------------------------------------------
  public DToolBar(DApplication dApplic) {
    _dApplic = dApplic ;
    //The JLabel Objects initialisation
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



    add(toolBarSelector);

    //JComboBox daySelector initialisation
    String [] amountDays = {"1","2","3","4","5","6","7"};
    daySelector = new JComboBox(amountDays);
    daySelector.setPreferredSize(new Dimension(50,DConst.NPT11 * 2));
    daySelector.setMaximumSize(new Dimension(50,DConst.NPT11 * 2));
    daySelector.setEditable(true);

    //JComboBox dayNameSelector initialisation
    String [] dayNames = {"--","LU","MA","ME","JE","VE","SA","DI"};
    dayNameSelector = new JComboBox(dayNames);
    dayNameSelector.setPreferredSize(new Dimension(50,DConst.NPT11 * 2));
    dayNameSelector.setMaximumSize(new Dimension(50,DConst.NPT11 * 2));
    dayNameSelector.setEditable(true);

    //JComboBox periodIndicator initialisation
    String [] periodIndexes = {"1","2","3","4","5","6","7"};
    periodSelector = new JComboBox(periodIndexes);
    periodSelector.setPreferredSize(new Dimension(50,DConst.NPT11 * 2));
    periodSelector.setMaximumSize(new Dimension(50,DConst.NPT11 * 2));
    periodSelector.setEditable(true);

    //JComboBox periodTypeSelector initialisation
    String [] periodTypes = {"Base Priorité","Normal","Null"};
    periodTypeSelector = new JComboBox(periodTypes);
    periodTypeSelector.setPreferredSize(new Dimension(100,DConst.NPT11 * 2));
    periodTypeSelector.setMaximumSize(new Dimension(100,DConst.NPT11 * 2));
    periodTypeSelector.setEditable(true);

    toolBarSelector.setSelectedIndex(0);
    addBarOne();// index 0

    toolBarSelector.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        //JComboBox cb = (JComboBox)e.getSource();
        //int  i = cb.getSelectedIndex();
        int i = toolBarSelector.getSelectedIndex();
        System.out.println("index i " + i);
        switch (i){
          case 0: addBarOne(); break;
          case 1: addBarTwo(); break;
        }// end switch
      }//end actionPerformed
    });//end addActionListener


    //*** Actions for the elements of the bar one

    setNumberOfDays.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {

      }//end actionPerformed
    });//end addActionListener

    setNumberOfDays.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {

      }//end actionPerformed
    });//end addActionListener


    daySelector.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {

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


  }//end constructor

  //-------------------------------------------
  private void addBarOne() {
    //removeBarTwo();
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
  private void removeBarOne() {
    remove(lSetNumberOfDays);
    remove(setNumberOfDays);
    remove(lDaySelector);
    remove(daySelector);
    remove(lDayNameSelector);
    remove(dayNameSelector);
    remove(stdDays);
    remove(addDay);
    remove(removeDay);

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
  private void removeBarTwo() {
    remove(lPeriodIndicator);
    remove(periodSelector);
    remove(lPeriodTypeSelector);
    remove(periodTypeSelector);
    remove(sameLine);
    remove(sameColumn);

    repaint();
  }//end removeBarTwo()

  private void removeBar() {
    removeAll();
    add(toolBarSelector);

    repaint();
  }//end removeBarTwo()


} // end classe
