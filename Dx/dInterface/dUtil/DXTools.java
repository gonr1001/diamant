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
   * * Search the indices to be showed as selected in a JList. The search is made in the vector that
   * contains the list items
   * @param Vector (itemsList) the items list where we are searching indices
   * @param Object [] (selectedItemsList) the selected items array to be found in the itemsList
   * @return An array containing the indices of the items to be showed as selected
   * */
  public static int[] getIndicesToSelected(Vector itemsList, Object[] selectedItemsList){
   int [] indices = new int[selectedItemsList.length];//the place fro keeping the indices to set selected
   for (int i = 0; i < selectedItemsList.length; i++){
     indices[i] = itemsList.indexOf(selectedItemsList[i]);
     //System.out.println("Indices2[" + i + "]= " + indices[i]);
   }
   return indices;
  }


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

/*
  public void actionButtonsLR(DApplication dApplic, int resourceType, int fieldIndex, String buttonPressed, Object [] elementsToTransfer, Vector leftVector, Vector rightVector, JList leftList, JList rightList){
    boolean leftButtonPressed = false;
    if (buttonPressed == _toLeftMes)
      leftButtonPressed = true;
    if (elementsToTransfer.length != 0){
      for (int i = 0; i < elementsToTransfer.length; i++)
        //((Activity)(_activities.getResource((String)_currentActivities[i]).getAttach())).setActivityVisibility(leftButtonPressed);
      setVectors(dApplic, leftVector, rightVector);
      rightList.setListData(rightVector);
      leftList.setListData(leftVector);
    }//end if (elementsToTransfer.length != 0)
    //if button pressed is "_toLeft"
    if (leftButtonPressed == true){
      int[] indices = DXTools.getIndicesToSelected(leftVector, elementsToTransfer);
      leftList.setSelectedIndices(indices);
      rightList.clearSelection();
    }else{
      int[] indices=DXTools.getIndicesToSelected(rightVector, elementsToTransfer);
      rightList.setSelectedIndices(indices);
      leftList.clearSelection();
    }
  }//end method
*/

  /**
   * Set the vectors leftVector and rightVector with the values found in the SetOfActivities
   */
  private static void setVectors(SetOfResources resources, int fieldIndex, String valueSource, String valueDestination, Vector sourceVector, Vector destinationVector){
    sourceVector = resources.getIDsByField(fieldIndex, valueSource);
    destinationVector = resources.getIDsByField(fieldIndex, valueDestination);
  }

}