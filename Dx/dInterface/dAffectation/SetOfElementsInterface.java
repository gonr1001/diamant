package dInterface.dAffectation;

/**
 * <p>Title: Proto</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author ysyam
 * @version 1.0
 */

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import dConstants.DConst;
import dInterface.DApplication;
import dInterface.dUtil.DXTools;

public abstract class SetOfElementsInterface extends JDialog implements ActionListener{

  private Dialog _parent;
  private Dimension _dialogDim;
  private Dimension _panelDim;
  private int buttonsPanelHeight = 80;
  private JLabel [] _labelOfElements;
  protected JPanel  _buttonsPanel;
  protected JList[] _listOfElements;
  private JPanel[] _panelOfElements;
  private Vector[] _vectorOfElements;
  private Object[] _selectedItems;
  protected int _selectedPanel=0;
  private String _title;
  private int _numberOfPanel;
  private String _elementsToDisplay;
  private int _WIDTH=300;
  private int _MINHEIGHT=160;
  private int _LINEHEIGHT=20;
  private int _MAXHEIGHT=400;
  private JDialog _jDialog;
  protected DApplication _dApplic;


  /**
   * Constructor
   * @param dApplic
   * @param title
   * @param numberOfPanel
   */
  public SetOfElementsInterface(Dialog parent, DApplication dApplic,String title, String elementsToDisplay, int numberOfPanel) {
    super(parent, title, true);
    _parent = parent;
    _title= title;
    _dApplic= dApplic;
    _elementsToDisplay= elementsToDisplay;
    _jDialog= this;
    _numberOfPanel=numberOfPanel;
    _listOfElements = new JList[_numberOfPanel];
    _panelOfElements= new JPanel[_numberOfPanel];
    _vectorOfElements= new Vector[_numberOfPanel];
    _labelOfElements= new JLabel[_numberOfPanel];
    _dialogDim = new Dimension(_WIDTH, _MINHEIGHT);
    _panelDim = new Dimension((_WIDTH-100),
                               _MINHEIGHT-buttonsPanelHeight-20);
  }//end method


  /**
   * Initialise the dialog
   */
  public void initDialog(){
    getContentPane().setLayout(new BorderLayout());
    setSize(_dialogDim);
    setResizable(true);
    //buildVectors();
    setPanels();
    getContentPane().add(_buttonsPanel, BorderLayout.SOUTH);
    setLocationRelativeTo(_parent);
    setVisible(true);
  }

   /**
   * Builds the vectors for their first display
   */
  public boolean setVectorsOfElements( Vector[] vectorOfElements){
    if (vectorOfElements.length==_vectorOfElements.length){
      for(int i=0; i< vectorOfElements.length; i++){
        _vectorOfElements[i]= (Vector)vectorOfElements[i].clone();
      }// end for(int i=0; i< vectorOfElements.length; i++)
    }// end if (vectorOfElements.length==_vectorOfElements.length)
    int realSize= (_MINHEIGHT+_LINEHEIGHT*_vectorOfElements[0].size());
    if (realSize > _MAXHEIGHT)
      realSize = _MAXHEIGHT;
    _dialogDim = new Dimension(_WIDTH, realSize);
    _panelDim = new Dimension((_WIDTH-100),
                               realSize-buttonsPanelHeight-20);
    if(_listOfElements[_selectedPanel]!=null){
     _listOfElements[_selectedPanel].setListData(_vectorOfElements[_selectedPanel]);
    }
    return false;
  }//end method



  /**
   * build buttom to use in the dialog
   */
  protected void buildButtons(){

  }


  /**
   * Sets the _centerPanel, the panel containing the _centerList and the
   * arrows panels
   */
  private void setPanels(){
    //Dimension panelDim = new Dimension((int)(_dialogDim.getWidth()*0.5), (int)_dialogDim.getHeight()-buttonsPanelHeight);

      _listOfElements[_selectedPanel] = new JList(_vectorOfElements[_selectedPanel]);
      _listOfElements[_selectedPanel].addMouseListener(mouseListenerLists);
      JLabel titleLabel = new JLabel(_elementsToDisplay+ " : ");
      _labelOfElements[_selectedPanel] = new JLabel(String.valueOf(_vectorOfElements[_selectedPanel].size()));
      _labelOfElements[_selectedPanel].setForeground(DConst.COLOR_QUANTITY_DLGS);
      //The listContainerPanel
      JPanel listPanel = DXTools.listPanel(_listOfElements[_selectedPanel]
      , (int)_panelDim.getWidth(), (int)_panelDim.getHeight());
      JPanel listContainerPanel = new JPanel();
      listContainerPanel.setPreferredSize(new Dimension((int)_panelDim.getWidth(), (int)_panelDim.getHeight()+10));
      listContainerPanel.add(titleLabel);
      listContainerPanel.add(_labelOfElements[_selectedPanel] );
      listContainerPanel.add(listPanel);
      //the _centerPanel
      _panelOfElements[_selectedPanel] = new JPanel();
      //_panelOfElements[_selectedPanel].setPreferredSize(panelDim);
      //_panelOfElements[_selectedPanel].add(_leftArrowsPanel);
      _panelOfElements[_selectedPanel].add(listContainerPanel);
      //_panelOfElements[_selectedPanel].add(_rightArrowsPanel);
    getContentPane().add(_panelOfElements[_selectedPanel], BorderLayout.CENTER);
  }//end method



  public void actionPerformed(ActionEvent e){

  }//end method


  /**
   * The MouseListener for the JLists
   */
  private MouseListener mouseListenerLists = new MouseAdapter(){
    public void mouseClicked(MouseEvent e) {
      if (((JList)e.getSource()).getModel().getSize() == 0)
        return;
      if (e.getSource().equals(_listOfElements[_selectedPanel])){
          //_centerList.clearSelection();
          //_rightList.clearSelection();
          //selectedItems = _leftList.getSelectedValues();
      }//end if (e.getSource().equals(_leftList))
      if (e.getClickCount() == 2) {
        doubleClicMouseProcess();
      }//end if
    }// end public void mouseClicked
  };//end definition of MouseListener mouseListener = new MouseAdapter(){

  /**
  *
  */
  protected void doubleClicMouseProcess(){
  }

}//end class