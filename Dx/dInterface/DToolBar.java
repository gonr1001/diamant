package dInterface;

/**
 * Title: ToolBar $Revision: 1.2 $  $Date: 2003-06-09 16:46:58 $
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
//import javax.swing.plaf.basic.BasicComboBoxRenderer;

import javax.swing.JToolBar;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import dResources.DConst;


/**
 *
 * ToolBar is a class used to display a toolbar with buttons
 *
 */

public class DToolBar extends JToolBar {
  private DApplication _dApplic;

  public DToolBar(DApplication dApplic) {
    _dApplic =dApplic ;
    //System.out.println("Tool Bar Constructor");  // debug
/*
    // Create the first button.
    String dir = System.getProperty("user.dir");

        if (dir.endsWith("build")) {
    dir = dir.substring(0, dir.lastIndexOf("build"));
    } else{
    System.out.println(System.getProperty("user.dir"));
    }*/
    String StrArray [] = {"Jours", "Periods"};

    JPanel jp = new JPanel();
    JComboBox jcb = new JComboBox(StrArray);
    jcb.setPreferredSize(new Dimension(200,DConst.NPT11));
    jcb.setMaximumSize(new Dimension(200,DConst.NPT11 * 2));
    //BasicComboBoxRenderer cbr = new BasicComboBoxRenderer();
    //cbr.setPreferredSize(new Dimension(200,10));
    //cbr.setMaximumSize(new Dimension(200,10));
    //jcb.setRenderer(cbr);*/
    jp.add(jcb);
    add(jp);
    addSeparator();
/*
    jcb.addActionListener(new ActionListener() {
     public void actionPerformed(ActionEvent e) {
       JComboBox cb = (JComboBox)e.getSource();
       int  i =cb.getSelectedIndex();
       switch (i){
         case 0: barOne(); break;
         case 1: barTwo(); break;
       }
     }
    });*/

  // Create the first button.

    CmdButton select = new CmdButton( "AddText" );
    add(select);
    select.setCommand(new SelectCmd());
    select.addActionListener(_dApplic);
    addSeparator();

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

      this.setFloatable(true);
  }

      private void barOne() {
        CmdButton select = new CmdButton( "barOne" );
add(select);
select.setCommand(new SelectCmd());
select.addActionListener(_dApplic);
    addSeparator();
      }
      private void barTwo() {
        CmdButton select = new CmdButton( "barTwo" );
add(select);
select.setCommand(new SelectCmd());
select.addActionListener(_dApplic);
    addSeparator();
      }













} // end classe
