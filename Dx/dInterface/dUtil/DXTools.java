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
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.util.Vector;

import dInternal.dData.Resource;
import dInternal.dData.SetOfResources;
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
   * Search the indices to be showed as selected in a JList. The search is made in the vector that
   * contains the list items
   * @param Vector (itemsList) the items list where we are searching indices
   * @param Object [] (selectedItemsList) the selected items array to be found in the itemsList
   * @return An array containing the indices of the items to be showed as selected
   * */
  public static int[] getIndicesToSelect(Vector itemsList, Object[] selectedItemsList){
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
      int[] indices = getIndicesToSelect(destinationVector, elementsToTransfer);
      destinationList.setSelectedIndices(indices);
      sourceList.clearSelection();
    }
  }//end method

public static void listTransfers(JList sourceList, JList destinationList, Vector sourceVector, Vector destinationVector){
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
      destinationRes.sortSetOfResourcesByID();
      destinationVector = destinationRes.getNamesVector();
      sourceList.setListData(sourceVector);
      destinationList.setListData(destinationVector);
      int[] indices = getIndicesToSelect(destinationVector, elementsToTransfer);
      destinationList.setSelectedIndices(indices);
      sourceList.clearSelection();
    }//end for
}//end method


  /**
   * Set the vectors leftVector and rightVector with the values found in the SetOfActivities
   */
/*
  private static void setVectors(SetOfResources resources, int fieldIndex, String valueSource, String valueDestination, Vector sourceVector, Vector destinationVector){
    sourceVector = resources.getIDsByField(fieldIndex, valueSource);
    destinationVector = resources.getIDsByField(fieldIndex, valueDestination);
  }
  */

 public static int STIConvertGroup(String STIGroupID){
   return  (int)STIGroupID.charAt(0) -(int)'A'+1;
 }

 /**
  * Creates a panel of buttons to be placed at the bottom of a Dialog.
  * This method adds the ActionListener for each button
  * @param parentDialog The dialog where this panel is placed
  * @param buttonsNames An array of names of buttons
  * @return
  */
 public static JPanel buttonsPanel(ActionListener parentDialog, String [] buttonsNames){
   JPanel panel = new JPanel();
   JButton button;
   for(int i = 0; i<buttonsNames.length; i++){
     button = new JButton(buttonsNames[i]);
     button.addActionListener(parentDialog);
     panel.add(button) ;
   }
   return panel;
 }//end method


 /**
  * Build a panel containing the arrows «« and »». This panel implements the
  * action listeners for each arrow
  * @param parentDialog The dialog who calls this panel
  * @param arrowsNames It contains the symbols of the arrows
  * @return the JPanel to be added to the dialog
  */
 public static JPanel arrowsPanel(ActionListener parentDialog, String[] arrowsNames){
   JPanel panel = new JPanel(new BorderLayout());
   panel.setPreferredSize(new Dimension(50, 70));
   JButton _toRight = new JButton(arrowsNames[0]);
   _toRight.setPreferredSize(new Dimension(50,35));
   _toRight.addActionListener(parentDialog);
   JButton _toLeft = new JButton(arrowsNames[1]);
   _toLeft.setPreferredSize(new Dimension(50,35));
   _toLeft.addActionListener(parentDialog);
   panel.add(_toRight, BorderLayout.NORTH);
   panel.add(_toLeft, BorderLayout.SOUTH);
   return panel;
 }//end method


 public static JPanel listPanel(JList theList, int panelWidth, int panelHeight){
   JPanel panel = new JPanel(new BorderLayout());
   panel.setPreferredSize(new Dimension(panelWidth, panelHeight));
   JScrollPane scrollPane = new JScrollPane();
   scrollPane.setPreferredSize(new Dimension(panelWidth,panelHeight));
   scrollPane.getViewport().add(theList);
   panel.add(scrollPane);
   return panel;
 }

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

 public static JPanel infoListPanel(String[][] list, JList theList, int panelWidth, int panelHeight){
   JPanel panel = new JPanel(new BorderLayout());
   JPanel infoPanel = infoPanel(list, panelWidth, 23);
   JPanel listPanel = listPanel(theList, panelWidth, panelHeight-23);
   panel.add(infoPanel, BorderLayout.NORTH);
   panel.add(listPanel, BorderLayout.SOUTH);
   return panel;
 }




}