package dInterface.dUtil;

/**
 * <p>Title: Diamant</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author ys
 * @version 1.0
 */
import javax.swing.JFileChooser;
import javax.swing.JList;
import java.awt.Component;
import java.util.Vector;

import dInternal.dData.SetOfResources;
import dInternal.dUtil.DXToolsMethods;

public class DXTools {

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
  public static int[] getIndicesToSelected(Vector itemsList, Object[] selectedItemsList){
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
      int[] indices = getIndicesToSelected(destinationVector, elementsToTransfer);
      destinationList.setSelectedIndices(indices);
      sourceList.clearSelection();
    }
  }//end method

public static void listTransfers(JList sourceList, JList destinationList, Vector sourceVector, Vector destinationVector){
  if (sourceList == null || destinationList == null || sourceVector == null || destinationVector == null )
    return;
  Object [] elementsToTransfer = sourceList.getSelectedValues();
  if (elementsToTransfer.length != 0){
      String currentElement;
      for (int i = 0; i < elementsToTransfer.length; i++){
        sourceVector.remove(elementsToTransfer[i]);
        destinationVector.add(elementsToTransfer[i]);
      }

      sourceList.setListData(sourceVector);
      destinationList.setListData(destinationVector);
      int[] indices = getIndicesToSelected(destinationVector, elementsToTransfer);
      destinationList.setSelectedIndices(indices);
      sourceList.clearSelection();
    }
}


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


}