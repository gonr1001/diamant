package dInterface.dUtil;

/**
 * <p>Title: Diamant</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author ys
 * @version 1.0
 */

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import dConstants.DConst;
import dInternal.dDataTxt.Resource;
import dInternal.dDataTxt.SetOfResources;
import dInternal.dUtil.DXToolsMethods;


public class DXTools{

  public DXTools() {
  }

  /**
    * check if the selected file exist before closed the dialog
    * @param string the file name
    * @return boolean true if the file exist a nd false otherwise
    * */
   public final static int showOpenDialog(Component parent, JFileChooser fc){
     int returnVal=0;
      String filename="nothing.txt";
      while((!DXToolsMethods.isFileExist(filename))&&
            (returnVal==JFileChooser.APPROVE_OPTION)){
          returnVal = fc.showOpenDialog(parent);
        if(fc.getSelectedFile()!=null)
          filename= fc.getSelectedFile().getAbsolutePath();

      }
     return returnVal;
   }

   /**
   * check if the selected file exist before closed the dialog
   * @param string the file name
   * @return boolean true if the file exist a nd false otherwise
   * */
  public final static int showDialog(Component parent, JFileChooser fc, String message){
    int returnVal=0;
     String filename="nothing.txt";
     while((!DXToolsMethods.isFileExist(filename))&&
           (returnVal==JFileChooser.APPROVE_OPTION)){
         returnVal = fc.showDialog(parent, message);
       if(fc.getSelectedFile()!=null)
         filename= fc.getSelectedFile().getAbsolutePath();

     }
    return returnVal;
   }

   /**
   * Search the indexes to be showed as selected in a JList.
   * The search is made in the vector that
   * contains the list items
   * @param Vector (itemsList) the items list where we are searching indices
   * @param Object [] (selectedItemsList) the selected items array to be found in the itemsList
   * @return An array containing the indices of the items to be showed as selected
   * */
  public static int[] getIndicesOfIntersection(Vector itemsList, Object[] selectedItemsList){
   int [] indices = new int[selectedItemsList.length];//the place for keeping the indices to set selected
   for (int i = 0; i < selectedItemsList.length; i++){
     indices[i] = itemsList.indexOf(selectedItemsList[i]);
   }
   return indices;
  }

  /**
   * Defines the actions when transfering data between 2 JLists by using a JButton. The 2 JList
   * contain the IDs of a setOfResources.
   * @param resources The setOfResources to be manipulated by using the JLists
   * @param fieldIndex The index of the criteria field (this field is the criteria to get/set the resources of the set
   * @param valueSource The value to be getted/setted in the resources belonging the JList source
   * @param valueDestination The value to be getted/setted in the resources belonging the JList destination
   * @param sourceList The JList source. It contains the data to be transferred to the JList destination
   * @param destinationList The JList destination
   */
  public static void actionButton(SetOfResources resources, int fieldIndex, String valueSource, String valueDestination, JList sourceList, JList destinationList) {
    Object [] elementsToTransfer = sourceList.getSelectedValues();
    if (elementsToTransfer.length != 0){
      String currentElement;
      Vector sourceVector = new Vector();
      Vector destinationVector = new Vector();
      resources.setSubsetOfResources(elementsToTransfer, fieldIndex, valueDestination);
      sourceVector = resources.getIDsByField(fieldIndex, valueSource);
      destinationVector = resources.getIDsByField(fieldIndex, valueDestination);
      sourceList.setListData(sourceVector);
      destinationList.setListData(destinationVector);
      int[] indices = getIndicesOfIntersection(destinationVector, elementsToTransfer);
      destinationList.setSelectedIndices(indices);
      sourceList.clearSelection();
    }
  }//end method


/**
 *
 * @param sourceList
 * @param destinationList
 * @param sourceVector
 * @param destinationVector
 * @param sortIndex Zero if we want to sort by key
 */

public static void listTransfers(JList sourceList, JList destinationList, Vector sourceVector, Vector destinationVector, int sortIndex){
  if (sourceList == null || destinationList == null || sourceVector == null || destinationVector == null )
    return;
  SetOfResources destinationRes = new SetOfResources(0);
  Resource res;
  Object [] elementsToTransfer = sourceList.getSelectedValues();

  if (elementsToTransfer.length != 0){
      String currentElement;
      for (int i = 0; i < elementsToTransfer.length; i++){
        sourceVector.remove(elementsToTransfer[i]);
        destinationVector.add(elementsToTransfer[i]);
      }
      for(int j = 0; j < destinationVector.size(); j++){
        res = new Resource((String)destinationVector.elementAt(j),null);
        destinationRes.addResource(res, 1);
      }
      destinationVector = destinationRes.getNamesVector(sortIndex);
      sourceList.setListData(sourceVector);
      destinationList.setListData(destinationVector);
      int[] indices = getIndicesOfIntersection(destinationVector, elementsToTransfer);
      destinationList.setSelectedIndices(indices);
      sourceList.clearSelection();
    }//end for
}//end method


/*
public static void listTransfersWithFixed(JList sourceList, JList destinationList, Vector sourceVector, Vector destinationVector, boolean toLeft, String chain){
  if (sourceList == null || destinationList == null || sourceVector == null || destinationVector == null )
    return;
  SetOfResources destinationRes = new SetOfResources(0);
  Resource res;
  Object [] elementsToTransfer = sourceList.getSelectedValues();
  String strElement;
  if (elementsToTransfer.length != 0){
      for (int i = 0; i < elementsToTransfer.length; i++){
        sourceVector.remove(elementsToTransfer[i]);
        strElement = (String)elementsToTransfer[i];
        if (toLeft){
          if(strElement.endsWith(chain))
            elementsToTransfer[i] = strElement.substring(0, strElement.length()-chain.length());
        }else{
          elementsToTransfer[i] = strElement + chain;
        }
        destinationVector.add(elementsToTransfer[i]);
      }
      for(int j = 0; j < destinationVector.size(); j++){
        res = new Resource((String)destinationVector.elementAt(j),null);
        destinationRes.addResource(res, 1);
      }
      destinationRes.sortSetOfResourcesByID();
      destinationVector = destinationRes.getNamesVector();
      sourceList.setListData(sourceVector);
      destinationList.setListData(destinationVector);
      int[] indices = getIndicesToSelect(destinationVector, elementsToTransfer);
      destinationList.setSelectedIndices(indices);
      sourceList.clearSelection();
    }//end for
}//end method
*/



  /**
   * Set the vectors leftVector and rightVector with the values found in the SetOfActivities
   */
/*
  private static void setVectors(SetOfResources resources, int fieldIndex, String valueSource, String valueDestination, Vector sourceVector, Vector destinationVector){
    sourceVector = resources.getIDsByField(fieldIndex, valueSource);
    destinationVector = resources.getIDsByField(fieldIndex, valueDestination);
  }
  */

 /**
  *
  * @param STIGroupID
  * @return
  */
public static int STIConvertGroupToInt(String STIGroupID){
   return  Integer.parseInt(STIGroupID);
 }

 /**
  *
  * @param STIGroupNumber
  * @return
  */
 public static String STIConvertGroup(String STIGroupNumber){
   return  STIGroupNumber;
 }

 /**
  *
  * @param STIGroupNumber
  * @return
  */
 public static String STIConvertGroup(int STIGroupNumber){
    String str = "00" + Integer.toString(STIGroupNumber);
    return str.substring(str.length()-2, str.length());

 }




 /**
  * Creates a panel of buttons to be placed at the bottom of a Dialog.
  * This method adds the ActionListener for each button
  * @param parentDialog The dialog where this panel is placed
  * @param buttonsNames An array of names of buttons
  * @return panel
  */

 // to be commented
 public static JPanel buttonsPanel(ActionListener parentDialog, String [] buttonsNames){
   JPanel panel = new JPanel();
   JButton button;
   for(int i = 0; i<buttonsNames.length; i++){
     button = new JButton(buttonsNames[i]);
     button.setActionCommand(buttonsNames[i]);
     button.addActionListener(parentDialog);
     panel.add(button) ;
   }
   return panel;
 }//end method
 /*
 public static JPanel buttonsPanel2(ActionListener parentDialog, String [] buttonsNames, Dimension parentDim){
   JPanel panel = new JPanel();
   panel.setPreferredSize(new Dimension((int)parentDim.getWidth(), 35));
   JButton button;
   for(int i = 0; i<buttonsNames.length; i++){
     button = new JButton(buttonsNames[i]);
     button.addActionListener(parentDialog);
     panel.add(button) ;
   }
   return panel;
 }//end method
 */


 /**
  * Build a panel containing the arrows for information transfert. This panel implements the
  * action listeners for each arrow
  * @param parentDialog The dialog who calls this panel
  * @param arrowsNames It contains the symbols of the arrows
  * @return the JPanel to be added to the dialog
  */
 public static JPanel arrowsPanel(ActionListener parentDialog, String[] arrowsNames, boolean enableBut){
   Dimension panelDim = new Dimension(50, 41*arrowsNames.length);
   JButton [] buttons = new JButton [arrowsNames.length];
   JPanel panel = new JPanel();
   panel.setPreferredSize(panelDim);
   for(int i = 0; i < arrowsNames.length; i++){
     buttons[i] = new JButton(arrowsNames[i]);
     buttons[i].setPreferredSize(new Dimension(50,35));
     buttons[i].addActionListener(parentDialog);
     buttons[i].setEnabled(enableBut);
     panel.add(buttons[i]);//, BorderLayout.NORTH);
   }
   //panel.setBorder(BorderFactory.createLineBorder(Color.CYAN));
   return panel;
 }//end method



 /**
  * Builds a JPanel containing just a JList
  * @param theList
  * @param panelWidth
  * @param panelHeight
  * @return
  */
 public static JPanel listPanel(JList theList, int panelWidth, int panelHeight){
   JPanel panel = new JPanel(new BorderLayout());
   panel.setPreferredSize(new Dimension(panelWidth, panelHeight));
   JScrollPane scrollPane = new JScrollPane();
   scrollPane.setPreferredSize(new Dimension(panelWidth,panelHeight));
   scrollPane.getViewport().add(theList);
   panel.add(scrollPane);
   return panel;
 }


 /**
  * Creates a Panel containing a valued title plus a listPanel
  * @param panelDim The panel dimension
  * @param list the list
  * @param vec the vector source for the list
  * @param labelsInfo The array containing the Strings to be displayed
  * @param ml the Mouselistener for the list
  * @return
  */

 public static JPanel setListPanel(Dimension panelDim, JList list, Vector vec, String [] labelsInfo, MouseListener ml){
   Dimension infoPanelDim = new Dimension((int)panelDim.getWidth(), 20);
   Dimension listPanelDim = new Dimension((int)panelDim.getWidth(), (int)(panelDim.getHeight() - infoPanelDim.getHeight()));
   list.setListData(vec);
   list.addMouseListener(ml);
   JPanel listPanel = listPanel(list, (int)listPanelDim.getWidth(), (int)listPanelDim.getHeight());
   //the panel
   JPanel panel = new JPanel();
   panel.setPreferredSize(panelDim);
   panel.add(setInfoPanel(infoPanelDim, labelsInfo));
   panel.add(listPanel);
   //panel.setBorder(BorderFactory.createLineBorder(Color.black));
   return panel;
 }


  /**
   * Builds a JPanel containig several labels arranged by couples.
   * The first label of the couple represents the name of a quantity, the second
   * label of the couple represents the value of that quantity. If there is
   * only the names, the labels represents the titles
   * @param parentDim The dimension of the panel containing this infoPanel
   * @param items the items to be displayed
   * @return the infoPanel
   */
  public static JPanel setInfoPanel(Dimension panelDim, String [] items){
    JPanel infoPanel = new JPanel();
    JLabel [] labels = new JLabel[items.length];
    infoPanel.setPreferredSize(panelDim);
    for (int i = 0; i < items.length; i++){
      labels[i] = new JLabel(items[i]+ " ");
      if (i%2 != 0){
        labels[i].setForeground(DConst.COLOR_QUANTITY_DLGS);
      }
      infoPanel.add(labels[i]);
    }
    return infoPanel;
  }



 /**
  * Builds a panel containing a Jlist
  * @param list
  * @param panelWidth
  * @param panelHeight
  * @return
  */
/*
 public static JPanel infoPanel(String[][] list, int panelWidth, int panelHeight){
   JPanel panel = new JPanel();
   JLabel lNumber;
   panel.setPreferredSize(new Dimension(panelWidth, panelHeight));
   for (int i = 0; i < list.length; i++){
     panel.add(new JLabel((String)list[i][0]));
     lNumber = new JLabel((String)list[i][1]);
     lNumber.setForeground(Color.blue);
     panel.add(lNumber);
   }
   return panel;
 }
 */

 /**
  * Sorts the elements of a vector
  * @param theVector The vector to sort
  * @return the vector sorted
  */
 public static Vector sortVector(Vector theVector){
   SetOfResources resources = new SetOfResources(0);
   Resource res;
   for(int i = 0; i < theVector.size(); i++){
        res = new Resource((String)theVector.elementAt(i),null);
        resources.addResource(res, 1);
      }
      resources.sortSetOfResourcesByID();
      return resources.getNamesVector(1);
 }



 /**
  * change the cursor
  * @param frame the parent frame
  * @param state if 0: set DEFAULT_CURSOR; 1 = WAIT_CURSOR; 2 = HAND_CURSOR
  */
 public static void setCursor(Component component, int state){
   switch (state){
     case 0: component.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
       break;
     case 1: component.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
       break;
     case 2: component.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
       break;
   }
 }



}