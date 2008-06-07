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
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.JButton;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import dInternal.DResource;
import dInternal.DSetOfResources;
import dInternal.dData.StandardCollection;

public class DxTools {

	/**
	 * Search the indexes to be showed as selected in a JList.
	 * The search is made in the vector that
	 * contains the list items
	 * @param Vector (itemsList) the items list where we are searching indices
	 * @param Object [] (selectedItemsList) the selected items array to be found in the itemsList
	 * @return An array containing the indices of the items to be showed as selected
	 * */
	public static int[] getIndicesOfIntersection(Vector<String> itemsList,
			Object[] selectedItemsList) {
		int[] indices = new int[selectedItemsList.length];//the place for keeping the indices to set selected
		for (int i = 0; i < selectedItemsList.length; i++) {
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
	public static void actionButton(DSetOfResources resources, int fieldIndex,
			String valueSource, String valueDestination, JList sourceList,
			JList destinationList) {
		Object[] elementsToTransfer = sourceList.getSelectedValues();
		if (elementsToTransfer.length != 0) {
			//String currentElement;
			Vector<String> sourceVector = new Vector<String>();
			Vector<String> destinationVector = new Vector<String>();
			resources.setSubsetOfResources(elementsToTransfer, fieldIndex,
					valueDestination);
			sourceVector = resources.getIDsByField(fieldIndex, valueSource);
			destinationVector = resources.getIDsByField(fieldIndex,
					valueDestination);
			sourceList.setListData(sourceVector);
			destinationList.setListData(destinationVector);
			int[] indices = getIndicesOfIntersection(destinationVector,
					elementsToTransfer);
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

	public static void listTransfers(JList sourceList, JList destinationList,
			Vector<String> sourceVector, Vector<String> destinationVector,
			int sortIndex) {
		if (sourceList == null || destinationList == null
				|| sourceVector == null || destinationVector == null)
			return;
		DSetOfResources destinationRes = new StandardCollection();
		DResource res;
		Object[] elementsToTransfer = sourceList.getSelectedValues();

		if (elementsToTransfer.length != 0) {
			//String currentElement;
			for (int i = 0; i < elementsToTransfer.length; i++) {
				sourceVector.remove(elementsToTransfer[i]);
				destinationVector.add((String) elementsToTransfer[i]);
			}
			for (int j = 0; j < destinationVector.size(); j++) {
				res = new DResource(destinationVector.elementAt(j), null);
				destinationRes.addResource(res, 1);
			}
			destinationVector = destinationRes.getNamesVector(sortIndex);
			sourceList.setListData(sourceVector);
			destinationList.setListData(destinationVector);
			int[] indices = getIndicesOfIntersection(destinationVector,
					elementsToTransfer);
			destinationList.setSelectedIndices(indices);
			sourceList.clearSelection();
		}//end for
	}//end method







	/**
	 *
	 * @param STIGroupID
	 * @return
	 */
	public static int STIConvertGroupToInt(String STIGroupID) {
		return Integer.parseInt(STIGroupID);
	}

//	/**
//	 *
//	 * @param STIGroupNumber
//	 * @return
//	 */
//	public static String STIConvertGroup(String STIGroupNumber) {
//		return STIGroupNumber;
//	}

	/**
	 *
	 * @param STIGroupNumber
	 * @return
	 */
	public static String STIConvertGroup(int STIGroupNumber) {
		String str = "00" + Integer.toString(STIGroupNumber);
		return str.substring(str.length() - 2, str.length());

	}

	/**
	 * Creates a panel of buttons to be placed at the bottom of a Dialog.
	 * This method adds the ActionListener for each button
	 * @param parentDialog The dialog where this panel is placed
	 * @param buttonsNames An array of names of buttons
	 * @return panel
	 */

	// to be commented
	public static JPanel buttonsPanel(ActionListener parentDialog,
			String[] buttonsNames) {
		JPanel panel = new JPanel();
		JButton button;
		for (int i = 0; i < buttonsNames.length; i++) {
			button = new JButton(buttonsNames[i]);
			button.setActionCommand(buttonsNames[i]);
			button.addActionListener(parentDialog);
			panel.add(button);
		}
		return panel;
	}//end method


	/**
	 * Build a panel containing the arrows for information transfert. This panel implements the
	 * action listeners for each arrow
	 * @param parentDialog The dialog who calls this panel
	 * @param arrowsNames It contains the symbols of the arrows
	 * @return the JPanel to be added to the dialog
	 */
	public static JPanel arrowsPanel(ActionListener parentDialog,
			String[] arrowsNames, boolean enableBut) {
		return arrowsPanel(parentDialog, "", arrowsNames, enableBut);
	}//end arrowsPanel

	/**
	 * Build a panel containing the arrows for information transfert. This panel implements the
	 * action listeners for each arrow
	 * @param parentDialog The dialog who calls this panel
	 * @param arrowsNames It contains the symbols of the arrows
	 * @return the JPanel to be added to the dialog
	 */
	public static JPanel arrowsPanel(ActionListener parentDialog, String id,
			String[] arrowsNames, boolean enableBut) {

		JButton[] buttons = new JButton[arrowsNames.length];
		JPanel panel = new JPanel();
		Container verticalBox = Box.createVerticalBox();

		for (int i = 0; i < arrowsNames.length; i++) {
			buttons[i] = new JButton(arrowsNames[i]);
			buttons[i].setActionCommand(id + arrowsNames[i]);
			buttons[i].addActionListener(parentDialog);
			buttons[i].setEnabled(enableBut);
			verticalBox.add(Box.createVerticalGlue());
			verticalBox.add(buttons[i]);
			verticalBox.add(Box.createVerticalStrut(10));

		}
		verticalBox.add(Box.createVerticalGlue());
		panel.add(verticalBox);
		return panel;
	}//end arrowsPanel

	/**
	 * Builds a JPanel containing just a JList
	 * @param theList
	 * @param panelWidth
	 * @param panelHeight
	 * @return
	 */
	public static JPanel listPanel(JList theList, int panelWidth,
			int panelHeight) {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setPreferredSize(new Dimension(panelWidth, panelHeight));
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(panelWidth, panelHeight));
		scrollPane.getViewport().add(theList);
		panel.add(scrollPane);
		return panel;
	}
	
	/**
	 * Builds a JPanel containing just a JList
	 * @param theList
	 * @param panelWidth
	 * @param panelHeight
	 * @return
	 */
	public static JPanel listPanel(JList theList) {
		JPanel panel = new JPanel(new BorderLayout());
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.getViewport().add(theList);
		panel.add(scrollPane);
		return panel;
	}


	/**
	 * Sorts the elements of a vector
	 * @param theVector The vector to sort
	 * @return the vector sorted
	 */
	public static Vector <String> sortVector(Vector <String> theVector) {
		DSetOfResources resources = new StandardCollection();
		DResource res;
		for (int i = 0; i < theVector.size(); i++) {
			res = new DResource(theVector.elementAt(i), null);
			resources.addResource(res, 1);
		}
		resources.sortSetOfResourcesByID();
		return resources.getNamesVector(1);
	}

}