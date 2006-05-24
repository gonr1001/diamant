/**
 * Created on May 24, 2006
 * 
 * 
 * Title: DxStudentConflictMatrix.java 
 *
 * Copyright (c) 2001 by rgr.
 * All rights reserved.
 *
 *
 * This software is the confidential and proprietary information
 * of rgr. ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with rgr.
 *
 * 
 * 
 */
package dInternal.dOptimization;

import dConstants.DConst;
import dInterface.dUtil.DXTools;
import dInternal.DModel;
import dInternal.DResource;
import dInternal.DSetOfResources;
import dInternal.DValue;
import dInternal.dData.StandardCollection;
import dInternal.dData.dActivities.Activity;
import dInternal.dData.dActivities.SetOfActivities;
import dInternal.dData.dActivities.Type;
import dInternal.dData.dStudents.SetOfStudents;
import dInternal.dData.dStudents.Student;
import dInternal.dUtil.DXToolsMethods;

/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DxStudentConflictMatrix is a class used to:
 * <p>
 * TODO:insert comments
 * <p>
 * 
 */
public class DxStudentConflictMatrix {

	private int[][] _theMatrix;

	private DSetOfResources _allSections;

	private boolean _doFirstGroupAssignement = true;

	/**
	 * 
	 */
	public void doFirstGroupAssignement() {
		_doFirstGroupAssignement = true;
	}

	/**
	 * 
	 * @param soa
	 * @param sos
	 */
	public void buildMatrix(DModel dm) {
		SetOfActivities soa = dm.getSetOfActivities();
		SetOfStudents sos = dm.getSetOfStudents();
		_allSections = buildSections(soa);
		if (_doFirstGroupAssignement) {
			firstStudentGroupAssignment(soa, sos, dm.getSetOfImportErrors());
			_doFirstGroupAssignement = false;
		}
		_theMatrix = new int[_allSections.size() + 1][_allSections.size() + 1];
		for (int i = 0; i < sos.size(); i++) {
			Student student = (Student) sos.getResourceAt(i);
			for (int j = 0; j < student.getCoursesList().size(); j++) {
				String course1 = student.getCoursesList().getResourceAt(j)
						.getID()
						.substring(0, SetOfActivities._COURSENAMELENGTH)
						+ "."
						+ student.getCoursesList().getResourceAt(j).getID()
								.substring(SetOfActivities._COURSENAMELENGTH)
						+ "."
						+ DXTools.STIConvertGroup(((DValue) student
								.getCoursesList().getResourceAt(j).getAttach())
								.getIntValue());
				String token = DXToolsMethods.getToken(course1, ".", 0);
				String tokenType = DXToolsMethods.getToken(course1, ".", 1);
				if (dm.getSetOfActivities().getType(token, tokenType) == null) {
					DValue error = new DValue();
					String matricule = "00000000"
							+ sos.getResourceAt(i).getKey();
					error.setStringValue(DConst.ERROR_TAG
							+ matricule.substring(matricule.length() - 8,
									matricule.length()) + " - "
							+ sos.getResourceAt(i).getID()
							+ DConst.NOT_STUD_ACT + "« " + token + tokenType
							+ " »");
					dm.getSetOfImportErrors().addResource(
							new DResource("1", error), 0);
				}// end if(dm.getSetOfActivities().getResource(token)==null)

				for (int k = j; k < student.getCoursesList().size(); k++) {
					String course2 = student.getCoursesList().getResourceAt(k)
							.getID().substring(0,
									SetOfActivities._COURSENAMELENGTH)
							+ "."
							+ student.getCoursesList().getResourceAt(k).getID()
									.substring(
											SetOfActivities._COURSENAMELENGTH)
							+ "."
							+ DXTools.STIConvertGroup(((DValue) student
									.getCoursesList().getResourceAt(k)
									.getAttach()).getIntValue());
					int[] index = getSectionsKeys(course1, course2);
					if ((index[0] != -1) && (index[1] != -1)) {
						_theMatrix[index[0]][index[1]]++;
					}
				}// end for (int k=j; k< student.getCoursesList().size()
			}// end for(int j=0; j< ((Activity)rescActivity.getAttach()).get
		}// end for(int i=0; i< soa.size(); i++)
		// this.toWriteMatrix();
	}

	/**
	 * 
	 * @param str1
	 *            is the key of the first section ADM111.1.A
	 * @param str2
	 *            is the key of the second section ADM111.1.A
	 * @return
	 */
	public int getNumberOfCOnflicts(String str1, String str2) {
		int[] index = getSectionsKeys(str1, str2);
		if ((index[0] != -1) && (index[1] != -1))
			return _theMatrix[index[0]][index[1]];
		return 0;
	}

	/**
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	public int[] getSectionsKeys(String str1, String str2) {
		int[] keys = { -1, -1 };
		if (_allSections != null) {
			DResource resc1 = _allSections.getResource(str1);
			DResource resc2 = _allSections.getResource(str2);
			if ((resc1 != null) && (resc2 != null)) {
				if (resc1.getKey() < resc2.getKey()) {
					keys[0] = (int) resc1.getKey();
					keys[1] = (int) resc2.getKey();
				} else {
					keys[1] = (int) resc1.getKey();
					keys[0] = (int) resc2.getKey();
				}
			}
		}
		return keys;
	}

	/**
	 * 
	 * @param soa
	 * @return
	 */
	private DSetOfResources buildSections(SetOfActivities soa) {
		DSetOfResources allSections = new StandardCollection();
		for (int i = 0; i < soa.size(); i++) {
			DResource activity = soa.getResourceAt(i);
			for (int j = 0; j < ((Activity) activity.getAttach())
					.getSetOfTypes().size(); j++) {
				DResource type = ((Activity) activity.getAttach())
						.getSetOfTypes().getResourceAt(j);
				for (int k = 0; k < ((Type) type.getAttach())
						.getSetOfSections().size(); k++) {
					String idSection = activity.getID()
							+ "."
							+ type.getID()
							+ "."
							+ ((Type) type.getAttach()).getSetOfSections()
									.getResourceAt(k).getID();
					allSections.addResource(new DResource(idSection, null), 0);
				}// end for (int k=0; k<

			}// end for (int j=0; j< activity.getSetOfTypes().size(); j++)
		}// end for(int i=0; i< soa.size(); i++)
		return allSections;
	}

	/**
	 * First assignment of students in the groups
	 * 
	 * @param sos
	 */
	private void firstStudentGroupAssignment(SetOfActivities soa,
			SetOfStudents sos, DSetOfResources setOfImportErrors) {
		for (int i = 0; i < soa.size(); i++) {
			DResource rescActivity = soa.getResourceAt(i);
			for (int j = 0; j < ((Activity) rescActivity.getAttach())
					.getSetOfTypes().size(); j++) {
				DResource rescType = ((Activity) rescActivity.getAttach())
						.getSetOfTypes().getResourceAt(j);
				int groupInd = 0;
				int[] tab = new int[((Type) rescType.getAttach())
						.getSetOfSections().size()];
				for (int z = 0; z < ((Type) rescType.getAttach())
						.getSetOfSections().size(); z++) {
					DResource rescSection = ((Type) rescType.getAttach())
							.getSetOfSections().getResourceAt(z);
					tab[z] = sos.getStudentsByGroup(rescActivity.getID(),
							rescType.getID(),
							DXTools.STIConvertGroupToInt(rescSection.getID()))
							.size();
				}// end for(int z=0;
					
				for (int k = 0; k < ((Activity) rescActivity.getAttach())
						.getStudentRegistered().size(); k++) {
					groupInd = this.getIndexOfSmallerValue(tab);// groupInc%
																
					String studentKey = (String) ((Activity) rescActivity
							.getAttach()).getStudentRegistered().get(k);
					Student student = (Student) sos.getResource(Long
							.parseLong(studentKey));
					int groupValue = DXTools
							.STIConvertGroupToInt(((Type) rescType.getAttach())
									.getSetOfSections().getResourceAt(groupInd)
									.getID());
					

					if ((!student.isFixedInGroup(rescActivity.getID()
							+ rescType.getID(), groupValue))
							&& (student.getGroup(rescActivity.getID()
									+ rescType.getID()) == -1)) {
						(student).setInGroup(rescActivity.getID()
								+ rescType.getID(), groupValue, false);
						tab[groupInd]++;
						// groupInc++;
					} else {// else
							// if(!((StudentAttach)student.getAttach()).isFixedInGroup
						int studentGroup = student.getGroup(rescActivity
								.getID()
								+ rescType.getID());
						String groupeID = DXTools.STIConvertGroup(studentGroup);
						if (soa.getSection(rescActivity.getID(), rescType
								.getID(), groupeID) == null) {
							DValue error = new DValue();
							error.setStringValue(DConst.ERROR_TAG
									+ student.getKey() + " - "
									+ student.getID() + "- Activité: "
									+ rescActivity.getID() + "."
									+ rescType.getID() + DConst.NOT_STUD_GROUP
									+ "« " + groupeID + " »");
							setOfImportErrors.addResource(new DResource("1",
									error), 0);
							student.setInGroup(rescActivity.getID()
									+ rescType.getID(), -1, false);
						}// end							
					}// end else
				}// end for (int k=0; k<
			}// end for (int j=0; j< ((Activity)rescActivity.getAttach()).
		}// end for (int i=0; i< soa.size(); i++)
	}

	/**
	 * get the index of the smaller value of a table
	 * 
	 * @param tab
	 * @return
	 */
	private int getIndexOfSmallerValue(int[] tab) {
		int inc = 1000;
		int index = 0;
		for (int i = 0; i < tab.length; i++) {
			if (tab[i] < inc)
				index = i;
			inc = tab[i];
		}
		return index;
	}

	/**
	 * 
	 * @return
	 */
	public String toWriteMatrix() {
		String str = "";
		for (int i = 1; i < _allSections.size() + 1; i++) {
			str += _allSections.getResourceAt(i - 1).getID() + " --> ";
			for (int j = 1; j < i + 1; j++) {
				if (_theMatrix[i][j] < 10)
					str += _theMatrix[i][j] + "  ";
				else if (_theMatrix[i][j] < 100)
					str += _theMatrix[i][j] + " ";
			}// end for (int j=0; j< _theMatrix[i].length; j++)
			str += DConst.CR_LF;
		}// end for (int i=0; i< _allSections.size(); i++)

		return str;
	}

	public int[][] getTheMatrix() {
		return _theMatrix;
	}

}// end class
