package dInterface;

/**
 * Title: ToolBar $Revision: 1.6 $  $Date: 2003-06-11 08:07:07 $
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

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;

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
  private static final String _strArray [] = {"Jours", "Periods"};
  JPanel _jp = new JPanel();
  JComboBox setToolBar, daysList;
  JButton addDay, removeDay, loPriority, noPriority, hiPriority, sameLine, sameColumn, doIt;
  JTextField setDays, dayName, periodIndicator;
  JLabel lDaysList, lSetDays, lDayName;


  //-------------------------------------------
  public DToolBar(DApplication dApplic) {
    _dApplic = dApplic ;


    //The JLabel Objects initialisation
    lSetDays = new JLabel("Nomber of Days ");
    lDaysList = new JLabel("Choise day ");
    lDayName = new JLabel("Name of day ");

    //The JButton Objects initialisation
    doIt = new JButton("Apply");
    addDay = new JButton("Add day");
    removeDay = new JButton("Remove day");
    loPriority = new JButton("Low priority");
    loPriority.setBackground(Color.cyan);

    noPriority = new JButton("Normal priority");
    noPriority.setBackground(Color.yellow);

    hiPriority = new JButton("High priority");
    hiPriority.setBackground(Color.red);
    sameLine = new JButton("Apply to all line");
    sameColumn = new JButton("Apply to all column");


    //The textField objects initialisation
    dayName = new JTextField();
    dayName.setMaximumSize(new Dimension(50, DConst.NPT11 * 2));

    periodIndicator = new JTextField("Actual Period");
    periodIndicator.setMaximumSize(new Dimension(100, DConst.NPT11 * 2));

    setDays = new JTextField();
    setDays.setMaximumSize(new Dimension(30, DConst.NPT11 * 2));

    //JComboBox setDays initialisation
    String [] amountDays = {"1","2","3","4","5","6","7","8","9","10"};
    daysList = new JComboBox(amountDays);
    daysList.setPreferredSize(new Dimension(50,DConst.NPT11 * 2));
    daysList.setMaximumSize(new Dimension(50,DConst.NPT11 * 2));
    daysList.setEditable(true);

    //JComboBox setToolBar initialisation
    setToolBar = new JComboBox(_strArray);
    setToolBar.setPreferredSize(new Dimension(200,DConst.NPT11* 2));
    setToolBar.setMaximumSize(new Dimension(200,DConst.NPT11 * 3));
    setToolBar.setSelectedIndex(0);

    add(setToolBar);

    setToolBar.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
      JComboBox cb = (JComboBox)e.getSource();
      int  i = cb.getSelectedIndex();
      switch (i){
        case 0: addBarOne(); break;
        case 1: addBarTwo(); break;
      }// end switch

    }
   });
    //System.out.println("Tool Bar Constructor");  // debug
/*
    // Create the first button.
    String dir = System.getProperty("user.dir");

        if (dir.endsWith("build")) {
    dir = dir.substring(0, dir.lastIndexOf("build"));
    } else{
    System.out.println(System.getProperty("user.dir"));
    }*/


/*  setToolBar.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                  showText("Button 1 has been clicked");
              }
        });
*/



   addDay.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                TTStructure tts = _dApplic.getDMediator().getCurrentDoc().getDM().getTTStructure();
                tts.setNumberOfDays(1);
                tts.modification();
              }
        });

  //     String command = e.getActionCommand();
  /*  if (command.equals( BUT02 )) {  // cancel
      //"Enseignants --> Bouton Annuler pressé\n"
      dispose();
    } else if (command.equals( BUT01 )) {  // OK
   //   _ddv._jFrame._log.append("Enseignants --> Bouton OK pressé\n");
       _currentInstr.setAvailability(_currentAvailbility);
        _dm.incrementModification();
      modified = false;
      butApply.setEnabled(false);
      dispose();
    } else*/// if (command.equals("Apply" )) {  // apply
    /*  "Enseignants --> Bouton Appliquer pressé\n");*/
      //tts.s
     // tts.modification();
      //modified = false;
      //butApply.setEnabled( false );
    // if a button of the grid has been pressed
    //}
    // }
    //

/*
   // fir BarOne
   setToolBar.addActionListener(new ActionListener() {
     public void actionPerformed(ActionEvent e) {
       TTStructure tts = _dApplic.getDMediator().getCurrentDoc().getDM().getTTStructure();
       String command = e.getActionCommand();
  /*  if (command.equals( BUT02 )) {  // cancel
      //"Enseignants --> Bouton Annuler pressé\n"
      dispose();
    } else if (command.equals( BUT01 )) {  // OK
   //   _ddv._jFrame._log.append("Enseignants --> Bouton OK pressé\n");
       _currentInstr.setAvailability(_currentAvailbility);
        _dm.incrementModification();
      modified = false;
      butApply.setEnabled(false);
      dispose();
    } else*//* if (command.equals("Apply" )) {  // apply
    /*  "Enseignants --> Bouton Appliquer pressé\n");*/
      //tts.s
     // tts.modification();
      //modified = false;
      //butApply.setEnabled( false );
    // if a button of the grid has been pressed
    //}
    // }
    //});*/

/*
    //Create the first button.
    CmdButton select = new CmdButton( "AddText" );
    add(select);
    select.setCommand(new SelectCmd());
    select.addActionListener(_dApplic);
    addSeparator();
*/

/*
    // Do the same for the second button
    CmdButton resource = new CmdButton(_appFrame, dir + "/images/resource.gif", "Ressource" );
    add(resource);
    resource.setCommand(new ResourceCmd(_appFrame, _med, _stMgr));
    resource.addActionListener(appFrame);
    addSeparator();

    // Do the same for the third button
    CmdButton inductorRC = new CmdButton(_appFrame, dir +"/images/inductorRC.gif","InductorRC" );
    add(inductorRC);
    inductorRC.setCommand(new InductorRCCmd(_appFrame, _med, _stMgr));
    inductorRC.addActionListener(_appFrame);
    addSeparator();

    // Do the same for the fourth button
    CmdButton consumer = new CmdButton(_appFrame, dir + "/images/consumer.gif","Consommateur" );
    add(consumer);
    consumer.setCommand(new ConsumerCmd(_appFrame, _med, _stMgr));
    consumer.addActionListener(_appFrame);
    addSeparator();

    // Do the same for the fifth button
    CmdButton inductorCP = new CmdButton(_appFrame, dir + "/images/inductorCP.gif","InductorCP" );
    add(inductorCP);
    inductorCP.setCommand(new InductorCPCmd(_appFrame, _med, _stMgr));
    inductorCP.addActionListener(_appFrame);
    addSeparator();

      CmdButton producer = new CmdButton(_appFrame, dir + "/images/producer.gif","Producer" );
      add(producer);
      producer.setCommand(new ProducerCmd(_appFrame,_med,_stMgr));
      producer.addActionListener(_appFrame);
      addSeparator();*/

      //this.setFloatable(true);
  }

  //-------------------------------------------
  private void addBarOne() {
    removeBarTwo();
    add(lSetDays);
    add(setDays);
    add(lDaysList);
    add(daysList);
    add(lDayName);
    add(dayName);
    add(addDay);
    add(removeDay);
    add(doIt);

    repaint();
  }//end methode

  //-------------------------------------------
  private void removeBarOne() {
    remove(lSetDays);
    remove(setDays);
    remove(lDaysList);
    remove(daysList);
    remove(lDayName);
    remove(dayName);
    remove(addDay);
    remove(removeDay);
    remove(doIt);

    repaint();
  }//end methode

  //-------------------------------------------
  private void addBarTwo() {
    removeBarOne();
    add(periodIndicator);
    add(loPriority);
    add(noPriority);
    add(hiPriority);
    add(doIt);
    add(sameLine);
    add(sameColumn);

    repaint();
  }//end methode

  //-------------------------------------------
  private void removeBarTwo() {
    remove(periodIndicator);
    remove(loPriority);
    remove(noPriority);
    remove(hiPriority);
    remove(sameLine);
    remove(sameColumn);
    remove(doIt);

    repaint();
  }//end methode













} // end classe
