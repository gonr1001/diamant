package dInterface;

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

import dInternal.TTParameters;

public class TTPanel extends JScrollPane {
  //DDocument _dd;
  private JLabel _x;
  public TTPanel() {//DDocument dd) {
    super();
    //_dd = dd;
    initTTPanel();
  }

  private void initTTPanel() {
    int minWidth =  500; // timeTable.nbDays * MINWIDTH;
int minHeight =  600;// (timeTable.getLatest() - timeTable.getEarliest()) * MINHEIGHT;
// create column header
JPanel panel = new JPanel(new GridLayout(1, 0));
//panel = confDialog();
//panel.setPreferredSize(new Dimension(minWidth, 20));
//setColumnHeaderView(panel);
//panel = new JPanel(new GridLayout(1, 0));
int nbds=0;
//for (int j=0;j< _ddv._constraint._jour.length; j++)
 // if (_ddv._constraint._jour[j]==1)
 //   nbds++;
for (int x = 0; x < 5 ; x++){
  panel.add(new JLabel("Jour " + (x + 1) + " : "+ "lundi", JLabel.CENTER));
}
//panel.setPreferredSize(new Dimension(500, 20));
panel.setBorder(BorderFactory.createEtchedBorder());
setColumnHeaderView(panel);
// create row header
panel = new JPanel(new GridLayout(0,1));
JLabel label;
for (int y = 0;/*timeTable.getEarliest()*/ y < 10 /*timeTable.getLatest()*/; y++) {
  label = new JLabel(Integer.toString(y) + ":00");
  label.setVerticalAlignment(JLabel.TOP);
  panel.setBorder(BorderFactory.createEtchedBorder());
    panel.add(label);
}
panel.setPreferredSize(new Dimension(35, minHeight));
setRowHeaderView(panel);

GridBagLayout gridbag =new GridBagLayout();
panel = new JPanel( gridbag );
panel.setBackground(SystemColor.window);
panel.setPreferredSize(new Dimension(20, 20));
setPreferredSize(new Dimension(panel.getPreferredSize().width + 40,
                               panel.getPreferredSize().height + 50));
int nbCols = 5; //timeTable.nbDays;
gridbag.columnWeights = new double [nbCols];
gridbag.columnWidths = new int [nbCols];
for (int i = 0; i < nbCols; i++) {
  gridbag.columnWeights[i] = 1;
  gridbag.columnWidths[i] = 30;
}
int nbRows = 14;
gridbag.rowWeights = new double [nbRows];
gridbag.rowHeights = new int [nbRows];
for (int i = 0; i < nbRows; i++) {
  gridbag.rowWeights[i] = 1;
  gridbag.rowHeights[i] = 20;
    }
_x = new JLabel ("hello");
panel.add(_x);
this.setViewportView(panel);
  }

  public void updateTTPanel(TTParameters ttp){
    _x.setText("Change done");
  }


}