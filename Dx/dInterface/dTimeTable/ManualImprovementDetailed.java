package dInterface.dTimeTable;




/**
 * <p>Title: Proto</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author unascribed
 * @version 1.0
 */

import javax.swing.JDialog;
import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.Dimension;
import java.awt.Toolkit;



import dInterface.DToolBar;


import dInternal.dUtil.DXToolsMethods;



import dInternal.dConditionsTest.EventAttach;
import dInternal.dTimeTable.*;
import dInternal.DModel;
import dInternal.dData.Resource;




public class ManualImprovementDetailed extends JDialog implements ActionListener{
  /* ADJUST_HEIGHT is needed to ajdust the screenSize
  * minus the barSize (the value is a guess) at the bottom */
  private final static int ADJUST_HEIGHT = 88;
  /* ADJUST_WIDTH is needed to ajdust the screenSize
  * minus border pixels (the value is a guess) at each side of the screen */
  private final static int ADJUST_WIDTH = 6;
  //private JInternalFrame _jif;
  private TTPane _ttPane;
  private TTStructure _ttStruct;
  private DToolBar _toolBar;
  //private DModel _dm;
  //private JFrame _jFrame;
  //private JDialog _jd;


  //private String FRAMENAME="Amélioration Manuelle";
  //private int INITIALPOSITION=25;
  /**
   * constructor
   */
  public ManualImprovementDetailed(JDialog jDialog, DToolBar toolbar,
                                      String eventName,  DModel dm) {
    super(jDialog, eventName, true);
    TTStructure oldTTS= dm.getTTStructure();
    _ttStruct = new TTStructure();
    _ttStruct.setTTStructureDocument(oldTTS.getTTStructureDocument());
    _toolBar= toolbar;
    initDlg(eventName, dm);
  }

  /**
   *
   * @return
   */
 /* public TTStructure getTTS(){
    return _ttStruct;
  }*/

 /**
  *
  * @return
  */
  /*public JFrame getJFrame(){
    return _jFrame;
  }*/

 /**
  *
  * @param e
  */
  public void actionPerformed(ActionEvent e){
    String command = e.getActionCommand();
  }


  public void initDlg(String eventName, DModel dm){
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    //Dimension frameDim = new Dimension(700,650);
    this.setSize(new Dimension(screenSize.width - ADJUST_WIDTH,
                                screenSize.height - ADJUST_HEIGHT));
    _ttPane = new DetailedMITTPane(_ttStruct,_toolBar, true, screenSize);

    Resource event = dm.getSetOfEvents().getResource(eventName);
    buildNewTTSTestConditions(event, dm);
    String eventPeriodKey=((EventAttach)event.getAttach()).getPeriodKey();
    long[] perKey={Long.parseLong(DXToolsMethods.getToken(eventPeriodKey,".",0)),
      Long.parseLong(DXToolsMethods.getToken(eventPeriodKey,".",1)),
      Long.parseLong(DXToolsMethods.getToken(eventPeriodKey,".",2))};
    int dayIndex= _ttStruct.getCurrentCycle().getSetOfDays().getIndexOfResource(perKey[0]);
    int seqIndex= ((Day)_ttStruct.getCurrentCycle().getSetOfDays().getResourceAt(dayIndex).
                   getAttach()).getSetOfSequences().getIndexOfResource(perKey[1]);
    int perIndex= ((Sequence)((Day)_ttStruct.getCurrentCycle().getSetOfDays().getResourceAt(dayIndex).
                              getAttach()).getSetOfSequences().getResourceAt(seqIndex).getAttach()).
                  getSetOfPeriods().getIndexOfResource(perKey[2]);
    //int[] perKeyIndex={};
    int duration = ((EventAttach)event.getAttach()).getDuration()/ dm.getTTStructure().getPeriodLenght();
    _ttPane.updateTTPane(_ttStruct);
    setColorOfPanel(dayIndex,seqIndex,perIndex,duration,((EventAttach)event.getAttach()).isPlaceInAPeriod());
    //_frameResult.setColorOfPanel(event.getID());

    this.getContentPane().add(_ttPane.getPane());

    this.show();
  }

  private void buildNewTTSTestConditions(Resource event, DModel dm){
    dm.getConditionsTest().buildAllConditions(_ttStruct);
    //Resource event= _dm.getSetOfEvents().getResource((String)selectedItems[0]);
    String eventPeriodKey = ((EventAttach)event.getAttach()).getPeriodKey();
    boolean eventAssignState = ((EventAttach)event.getAttach()).getAssignState();
    boolean inAPeriod = ((EventAttach)event.getAttach()).isPlaceInAPeriod();
    if (event!=null) {
      ((EventAttach)event.getAttach()).setAssignState(true);
      dm.getConditionsTest().addOrRemEventInTTs(_ttStruct,event,-1,false);
      ((EventAttach)event.getAttach()).setAssignState(false);
      for(int i=0; i< _ttStruct.getCurrentCycle().getSetOfDays().size(); i++){
        Resource day= _ttStruct.getCurrentCycle().getSetOfDays().getResourceAt(i);
        for(int j=0; j< ((Day)day.getAttach()).getSetOfSequences().size(); j++){
          Resource seq= ((Day)day.getAttach()).getSetOfSequences().getResourceAt(j);
          for(int k=0; k< ((Sequence)seq.getAttach()).getSetOfPeriods().size();k++){
            Resource per= ((Sequence)seq.getAttach()).getSetOfPeriods().getResourceAt(k);
            int[] daytime={(int)day.getKey(), (int)seq.getKey(), (int)per.getKey()};
             int duration = ((EventAttach)event.getAttach()).getDuration()/_ttStruct.getPeriodLenght();
            if(_ttStruct.getCurrentCycle().isPeriodContiguous(daytime[0],daytime[1],daytime[2],duration,new int[0],false)){
            String periodKey=daytime[0]+"."+daytime[1]+"."+daytime[2];
            ((EventAttach)event.getAttach()).setKey(4,periodKey);
            ((EventAttach)event.getAttach()).setAssignState(true);
            dm.getConditionsTest().addOrRemEventInTTs(_ttStruct,event,1,true);
            ((EventAttach)event.getAttach()).setAssignState(false);
            }// end  if(_ttStruct.getCurrentCycle().isPeriodContiguous(day
          }// end for(int k=0; k< ((Sequence)seq.getAttach())
        }// end for(int j=0; j< ((Day)day.getAttach()).getSetOfSequences().size(); j++)
      }// end for(int i=0; i< _newTTS.getCurrentCycle()
    }// end if(event!=null){
    ((EventAttach)event.getAttach()).setKey(4,eventPeriodKey);
    ((EventAttach)event.getAttach()).setAssignState(eventAssignState);
    ((EventAttach)event.getAttach()).setInAPeriod(inAPeriod);
  }

  /**
   *
   * @param simple
   */
 /* protected JInternalFrame  buildInternalFrame(boolean simple){
    JInternalFrame jif;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameDim = new Dimension(700,650);
    //_documentName = title;
   jif = new JInternalFrame(FRAMENAME, true, true, true, true);
   //jif.addInternalFrameListener(this);
   jif.setDefaultCloseOperation(jif.DO_NOTHING_ON_CLOSE);
   jif.setTitle(FRAMENAME);
   //_simpleView= simpleView;
   //jif.addInternalFrameListener(this);
   jif.setMinimumSize(frameDim);
   jif.setPreferredSize(frameDim);

   if (simple)
     _ttPane = new SimpleTTPane(_ttStruct,_toolBar);
   else
     _ttPane = new DetailedTTPane(_ttStruct,_toolBar,false, jif.getSize());

   jif.getContentPane().add(_ttPane.getPane(), BorderLayout.CENTER);
   jif.pack();
   jif.setVisible(true);
   return jif;
  } // end buidDocument*/

 /**
  *
  * @param str
  * @return
  */
 /* protected void createFrame( String eventName, boolean simple) {
    setTitle(eventName);
    JPanel panel = new JPanel(new BorderLayout(0,0));
    setContentPane(panel);
    //DMenuBar dMenuBar = new DMenuBar(this,1);
    //setJMenuBar(dMenuBar);
    JDesktopPane jDesktopPane = new JDesktopPane();
    jDesktopPane.setOpaque(false);
    jDesktopPane.setDesktopManager(new DefaultDesktopManager());
    panel.add(jDesktopPane,BorderLayout.CENTER);
    //setContentPane(buildInternalFrame(simple));
    getContentPane().add(buildInternalFrame(simple));
    //getContentPane().add(jDesktopPane, BorderLayout.CENTER);
    //getContentPane().add(jDesktopPane, BorderLayout.CENTER);
    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    addWindowListener(new WindowAdapter() {
     public void windowClosing( WindowEvent e ) {
       dispose();
     }
      });
        pack();
        show();
        //doLayout();
      setLocation(INITIALPOSITION,INITIALPOSITION);
      setVisible(true);
    } //end createUI*/

   /**
    *
    */
  protected void setColorOfPanel(int dayIndex, int seqIndex, int perIndex, int duration, boolean isAssign){
    for (int i=0; i< ((JPanel)_ttPane.getViewport().getComponent(0)).getComponentCount(); i++){
      PeriodPanel perPanel= (PeriodPanel)((JPanel)_ttPane.getViewport().getComponent(0)).getComponent(i);
      Period period= _ttStruct.getCurrentCycle().getPeriodByIndex( perPanel.getPeriodRef()[0],
          perPanel.getPeriodRef()[1],perPanel.getPeriodRef()[2]);
      int[] ppKey={};
      if((dayIndex==perPanel.getPeriodRef()[0]) &&
         (seqIndex==perPanel.getPeriodRef()[1]) &&
         (perIndex<=perPanel.getPeriodRef()[2])&&
         (perPanel.getPeriodRef()[2]<= (perIndex+duration-1)) &&
         (isAssign)) {
        perPanel.setPanelColor(4);
      } else{
        if((period.getNbInstConflict()+period.getNbRoomConflict()+period.getNbStudConflict()) != 0){
          perPanel.setPanelColor(3);
        }// end if((period.getNbInstConflict()+period.getNbRoomConfli
      }

    }// end for (int i=0; i< ((JPanel)_ttPanel.getViewport().
  }




}
