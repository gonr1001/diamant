package dInterface.dData;

/**
 * <p>Title: Proto</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author unascribed
 * @version 1.0
 */
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.Cursor;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.StringTokenizer;
import java.util.Vector;
import java.awt.Toolkit;


import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTabbedPane;

import dInterface.DApplication;
import dInternal.DModel;
import dInternal.dData.SetOfStates;
import dInternal.dData.State;
import dInterface.ProgressBar;
import dInterface.dTimeTable.SaveAsDlg;
import dInterface.dUtil.DXTools;

import dInternal.dData.Resource;
import dInternal.dData.SetOfResources;
import dInternal.dData.StandardReportData;
import dInternal.dUtil.DXValue;

import dResources.DConst;


public class ViewReport  extends JPanel implements ActionListener {

  ReportsDlg _parentDlg;
  JScrollPane _scrollPane;
  DApplication _dApplic;
  JTextArea _jTextArea;
  JPanel _buttonsPanel;
  Vector _allOptionsVec;
  Vector _rightVec ;
  int _elements;

  protected class FieldRecord {
   int _n;
   String _str;
   FieldRecord(int n, String str){
     _n = n; _str = str;
   }
  }

  public ViewReport(ReportsDlg parentDlg, DApplication dApplic, Dimension dim) {
    //super(new BorderLayout());
    _parentDlg = parentDlg;
    _dApplic = dApplic;
    _allOptionsVec = new Vector();
    _jTextArea = new JTextArea();
    this.setLayout(new BorderLayout());
    //setImportReport(jta);
    this.setPreferredSize(dim);
    _scrollPane = new JScrollPane();
    _scrollPane.setPreferredSize(new Dimension((int)dim.getWidth(), (int)dim.getHeight()-20));
    _scrollPane.getViewport().setView(_jTextArea);
    this.add(_scrollPane,BorderLayout.CENTER);
    String[] _buttonsNames = {DConst.BUT_SAVE_AS, DConst.BUT_OPTIONS, DConst.BUT_CLOSE};
    _buttonsPanel = DXTools.buttonsPanel(this, _buttonsNames);
    this.add(_buttonsPanel, BorderLayout.SOUTH);
  }

  protected void disableButtons(JPanel buttons, String [] strArray){
    String str= "";
    for (int i = 0; i < buttons.getComponentCount(); i++) {
      ((JButton)buttons.getComponent(i)).setEnabled(true);
    }
    if (strArray != null) {
      for (int i = 0; i < strArray.length; i++) {
        str = strArray[i];
        for(int j = 0 ; j < buttons.getComponentCount(); j++) {
          if (((JButton)buttons.getComponent(j)).getActionCommand().equals(str))
             ((JButton)buttons.getComponent(j)).setEnabled(false);
        }
      }
   }
  }

  public void dispose() {
    _parentDlg.dispose();
  }

  protected Vector merge(Vector opt, Vector  right) {
    Vector res =  new Vector();
    for (int i = 0; i < right.size(); i++){
      opt.remove(right.get(i));
    }

    for (int i = 0; i < opt.size(); i++){
      res.add(opt.get(i));
    }

    DXTools.sortVector(res);

    for (int i = 0; i < right.size(); i++){
      res.add(right.get(i));
    }
    return res;
  }

  protected int indexElementIn(String str, Vector v) {
    int index = -1;

    for(int i = 0; i < v.size(); i++) {
      if ( ((FieldRecord)v.get(i))._str.compareTo(str)==0)
        return i + 1;
    }
    return index;
  }
  protected int [] buildNext(Vector v, Vector allOpt) {
    int [] a =  new int [v.size()];

    for (int i = 1; i < v.size(); i++)
      a [i] = indexElementIn((String)v.get(i),allOpt);
    return a;
  }
  public void actionPerformed(ActionEvent e){ }
}

