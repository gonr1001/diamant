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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import dInterface.DApplication;
import dInterface.dUtil.DXTools;

import dResources.DConst;

public class ReportDlg extends JDialog implements ActionListener{

  private DApplication _dApplic = null;
  private JDialog _jd = this;
  private JPanel _buttonsPanel;
  private String[] _buttonsNames = {DConst.BUT_OK, "OPTIONS", DConst.BUT_CANCEL};

  public ReportDlg(DApplication dApplic) {
    super(dApplic.getJFrame(), "Rapports", true);
    _dApplic = dApplic;
    jbInit();
    setLocationRelativeTo(dApplic.getJFrame());
    setVisible(true);
  }//end constructor

  private void jbInit(){
    Dimension dialogDim = new Dimension(300,400);
    Dimension tabbedPaneDim = new Dimension((int)dialogDim.getWidth()-50, (int)dialogDim.getHeight()-60);
    getContentPane().setLayout(new BorderLayout());
    setSize(dialogDim);
    setResizable(true);
    //the tabbedPane
    JTabbedPane tabbedPane = new JTabbedPane();
    //tabbedPane.setPreferredSize(tabbedPaneDim);
    tabbedPane.addTab("Report 1", actReport(dialogDim));
    //the buttons panel
    _buttonsPanel = DXTools.buttonsPanel(this, _buttonsNames);

    getContentPane().add(tabbedPane, BorderLayout.NORTH);
    getContentPane().add(_buttonsPanel, BorderLayout.SOUTH);
  }

  private JPanel actReport(Dimension dialogDim){
    return new JPanel();
  }

  public void actionPerformed(ActionEvent e){
    String command = e.getActionCommand();
    System.out.println("command "+command);
    //If buttons OPTIONS
    //if (command.equals("OPTIONS"))
        new ReportOptionsDlg(_dApplic, _jd, 0);
  }


}//end class