package dInternal.dOptimization;

import java.util.StringTokenizer;
import java.util.Vector;

import dConstants.DConst;
import dDeveloper.DxFlags;
import dInterface.dUtil.DxTools;
import dInternal.DModel;
import dInternal.DResource;
import dInternal.DSetOfResources;
import dInternal.DValue;
import dInternal.dData.DxAvailability;
import dInternal.dData.dActivities.Activity;
import dInternal.dData.dActivities.Assignment;
import dInternal.dData.dActivities.Section;
import dInternal.dData.dActivities.SetOfActivities;
import dInternal.dData.dActivities.Type;
import dInternal.dData.dActivities.Unity;
import dInternal.dData.dInstructors.DxSetOfInstructors;
import dInternal.dData.dRooms.SetOfRooms;
import dInternal.dData.dStudents.Student;
import dInternal.dTimeTable.Period;
import dInternal.dUtil.DXToolsMethods;

public class SetOfEvents extends DSetOfResources {
	/**
	 * @associates SetOfEventsListener
	 */

	private DModel _dm;

	/***************************************************************************
	 * Constructor
	 */
	public SetOfEvents(DModel dm) {
		super();
		_dm = dm;
	}

	/**
	 * Build setOfEvents from activities
	 * 
	 * @param cycle
	 */
	public void build(SetOfActivities soActivities,
			DSetOfResources soImportErrors) {
		for (int i = 0; i < soActivities.size(); i++) {
			DResource activityResource = soActivities.getResourceAt(i);
			Activity activity = (Activity) activityResource.getAttach();
			forEachActivity(activityResource, activity, soImportErrors);
		}
	} // end build

	private void forEachActivity(DResource activityResource, Activity activity,
			DSetOfResources soImportErrors) {
		if (activity.isActivityVisibility()) { // // RGR RGR pas necessaire de
												// tester Visi
			for (int j = 0; j < activity.getSetOfTypes().size(); j++) {
				DResource typeResource = activity.getSetOfTypes()
						.getResourceAt(j);
				forEachType(activityResource, typeResource, soImportErrors);
			}
		}
	}

	private void forEachType(DResource activityResource,
			DResource typeResource, DSetOfResources soImportErrors) {
		String unityKey;
		long roomKey;
		for (int k = 0; k < ((Type) typeResource.getAttach())
				.getSetOfSections().size(); k++) {
			DResource sectionResource = ((Type) typeResource.getAttach())
					.getSetOfSections().getResourceAt(k);
			for (int l = 0; l < ((Section) sectionResource.getAttach())
					.getSetOfUnities().size(); l++) {
				DResource unityResource = ((Section) sectionResource
						.getAttach()).getSetOfUnities().getResourceAt(l);
				Assignment assignment = (Assignment) ((Unity) unityResource
						.getAttach()).getAssignment(
						_dm.getTTStructure().getCurrentCycleResource().getID())
						.getAttach();
				if (assignment != null) {
					unityKey = buildUnitKey(activityResource, typeResource,
							sectionResource, unityResource);
					String unityID = buildUnityID(activityResource,
							typeResource, sectionResource, unityResource);
					if (DXToolsMethods.getToken(assignment.getPeriodKey(), ".",
							0).equalsIgnoreCase("0")) {
						String periodKeys = _dm.getTTStructure()
								.getCurrentCycle().getPeriod(
										assignment.getDateAndTime());
						Period period = _dm.getTTStructure().getCurrentCycle()
								.getPeriodByPeriodKey(periodKeys);
						if (period != null)
							assignment.setPeriodKey(periodKeys);
						else
							assignment.setPeriodKey("1.1.1");
					}

					assignDxInstructors(soImportErrors, assignment, unityID);

					if (DxFlags.newRooms)
						roomKey = assignDxRooms(assignment,
								unityID, soImportErrors);
					else
						roomKey = assignRooms(assignment,
								unityID, soImportErrors);

					int cLimit = ((Section) sectionResource.getAttach())
							.getCapacityLimit();

					if (DxFlags.newEvent) {
						DxEvent dxevent = new DxEvent(unityKey, assignment
								.getSetInstructorKeys(), roomKey,
								unityResource, assignment, cLimit);
						this.addResource(new DResource(unityID, dxevent), 0);
					} else {
						EventDx event = new EventDx(unityKey, assignment
								.getSetInstructorKeys(), roomKey,
								((Unity) unityResource.getAttach())
										.getDuration(), assignment
										.getPeriodKey(), cLimit);

						event.setAssigned(((Unity) unityResource.getAttach())
								.isAssign());
						event.setPermanentState(((Unity) unityResource
								.getAttach()).isPermanent());
						event.setRoomFixed(assignment.getRoomState());
						event.setRoomFunction(((Unity) unityResource
								.getAttach()).getFirstPreferFunctionRoom());
						this.addResource(new DResource(unityID, event), 0);
					}

				}// end if(assignement!=null)
			}// end for(int l=0; l<
			// ((Section)section.getAttach()).getSetOfUnities().size();
			// l++)
		}// end for(int k=0; k<
	}

	private String buildUnityID(DResource activityResource,
			DResource typeResource, DResource sectionResource,
			DResource unityResource) {
		return activityResource.getID() + "." + typeResource.getID() + "."
				+ sectionResource.getID() + "." + unityResource.getID() + ".";
	}

	private String buildUnitKey(DResource activityResource,
			DResource typeResource, DResource sectionResource,
			DResource unityResource) {
		return activityResource.getKey() + "." + typeResource.getKey() + "."
				+ sectionResource.getKey() + "." + unityResource.getKey() + ".";
	}

	private long assignRooms( Assignment assignment,
			String unityID, DSetOfResources soImportErrors) {
		long roomKey;
		int roomIndex = _dm.getSetOfRooms().getIndexOfResource(
				assignment.getRoomName());

		if (roomIndex != -1) {
			roomKey = _dm.getSetOfRooms().getResourceAt(roomIndex).getKey();
		} else {
			roomKey = -1;
			DValue error = new DValue();
			String str = assignment.getRoomName();
			if (str.equals(DConst.NO_ROOM_INTERNAL))
				str = DConst.NO_ROOM_EXTERNAL;
			error.setStringValue(DConst.ERROR_TAG + unityID + ": "
					+ DConst.NOT_ROOM + "« " + str + " »");
			soImportErrors.addResource(new DResource("3", error), 0);
		}
		return roomKey;
	}

	private long assignDxRooms(Assignment assignment,
			String unityID, DSetOfResources soImportErrors) {
		long roomKey = _dm.getDxSetOfRooms().getRoomKeyByName(
				assignment.getRoomName());
		if (roomKey == -1) {
			DValue error = new DValue();
			String str = assignment.getRoomName();
			if (str.equals(DConst.NO_ROOM_INTERNAL))
				str = DConst.NO_ROOM_EXTERNAL;
			error.setStringValue(DConst.ERROR_TAG + unityID + ": "
					+ DConst.NOT_ROOM + "« " + str + " »");
			soImportErrors.addResource(new DResource("3", error), 0);
		}
		return roomKey;
	}

	private void assignDxInstructors(DSetOfResources soie,
			Assignment assignment, String unityID) {
		String[] instructorNames = assignment.getInstructorNames();
		for (int m = 0; m < instructorNames.length; m++) {
			long lKey = _dm.getDxSetOfInstructors().getInstructorKey(
					instructorNames[m]);
			if (lKey != -1) {
				assignment.addInstructorKeys(lKey);
			} else {
				DValue error = new DValue();
				error.setStringValue(DConst.ERROR_TAG + unityID + ": "
						+ DConst.NOT_INSTRUCTOR + "« " + instructorNames[m]
						+ " »");
				soie.addResource(new DResource("2", error), 0);
			}
		}
	}

	/**
	 * get the complet activity name of an event
	 * 
	 * @param eventKey
	 * @param soa
	 * @return
	 */
	public String getEventID(String eventID, SetOfActivities soa) {
		String id = eventID;
		StringTokenizer event1 = new StringTokenizer(eventID,
				DConst.TOKENSEPARATOR);
		if (event1.countTokens() >= 4) {
			DResource activity = soa.getResource(event1.nextToken());
			DResource type = ((Activity) activity.getAttach()).getSetOfTypes()
					.getResource(event1.nextToken());
			DResource section = ((Type) type.getAttach()).getSetOfSections()
					.getResource(event1.nextToken());
			DResource unity = ((Section) section.getAttach()).getSetOfUnities()
					.getResource(event1.nextToken());
			id = activity.getID() + DConst.TOKENSEPARATOR + type.getID()
					+ DConst.TOKENSEPARATOR + section.getID()
					+ DConst.TOKENSEPARATOR + unity.getID();
		}
		return id;
	}

	/**
	 * 
	 * @return
	 */
	public int getNumberOfEventAssign() {
		int count = 0;
		for (int i = 0; i < this.size(); i++) {
			if (DxFlags.newEvent) {
				if (((DxEvent) getResourceAt(i).getAttach()).isPlaceInAPeriod())
					count++;
			} else {
				if (((EventDx) getResourceAt(i).getAttach()).isPlaceInAPeriod())
					count++;
			}

		}// end for (int i=0; i< this.size(); i++)
		return count;
	}

	/**
	 * updateActivities when an event or more than an event is changed, this
	 * change must be reflected in the SetOfActivities, which is the persistant
	 * structure.
	 * 
	 * @param soa
	 *            the SetOfActivities
	 * @param eventsToUpdate
	 *            <p>
	 *            A vector containing all changed events
	 */
	public void updateActivities(SetOfActivities soa, Vector eventsToUpdate) {
		if (DxFlags.newEvent) {
			DxEvent event;
			for (int i = 0; i < eventsToUpdate.size(); i++) {
				event = (DxEvent) ((DResource) eventsToUpdate.get(i))
						.getAttach();
				long actKey = Long.parseLong(DXToolsMethods.getToken4Activitiy(
						event.getPrincipalRescKey(), ".", 0));
				long typeKey = Long
						.parseLong(DXToolsMethods.getToken4Activitiy(event
								.getPrincipalRescKey(), ".", 1));
				long sectKey = Long
						.parseLong(DXToolsMethods.getToken4Activitiy(event
								.getPrincipalRescKey(), ".", 2));
				long unitKey = Long
						.parseLong(DXToolsMethods.getToken4Activitiy(event
								.getPrincipalRescKey(), ".", 3));

				Unity unit = soa.getUnity(actKey, typeKey, sectKey, unitKey);
				Assignment assignment = (Assignment) unit.getSetOfAssignments()
						.getResourceAt(
								_dm.getTTStructure().getCurrentCycleIndex())
						.getAttach();
				long[] keys = event.getInstructorKey();

				assignment.emptyInstructorNames();

				for (int j = 0; j < keys.length; j++) {
					assignment.addInstructorName(getDxInstName(_dm
							.getDxSetOfInstructors(), keys[j]));
				}// end for

				if (DxFlags.newRooms) {
					assignment.setRoomName(_dm.getDxSetOfSites()
							.getAllDxRooms().getRoomName(event.getRoomKey()));
				} else {
					assignment.setRoomName(getRoomName(_dm.getSetOfRooms(),
							event.getRoomKey()));
				}
				assignment.setPeriodKey(event.getPeriodKey());

				unit.updateWith(event);
				assignment.setRoomState(event.isRoomFixed());

			}// end for (int i=0; i< eventsToUpdate.size(); i++)
		} else {
			EventDx event;
			for (int i = 0; i < eventsToUpdate.size(); i++) {
				event = (EventDx) ((DResource) eventsToUpdate.get(i))
						.getAttach();

				long actKey = Long.parseLong(DXToolsMethods.getToken4Activitiy(
						event.getPrincipalRescKey(), ".", 0));
				long typeKey = Long
						.parseLong(DXToolsMethods.getToken4Activitiy(event
								.getPrincipalRescKey(), ".", 1));
				long sectKey = Long
						.parseLong(DXToolsMethods.getToken4Activitiy(event
								.getPrincipalRescKey(), ".", 2));
				long unitKey = Long
						.parseLong(DXToolsMethods.getToken4Activitiy(event
								.getPrincipalRescKey(), ".", 3));

				Unity unit = soa.getUnity(actKey, typeKey, sectKey, unitKey);
				Assignment assignment = (Assignment) unit.getSetOfAssignments()
						.getResourceAt(
								_dm.getTTStructure().getCurrentCycleIndex())
						.getAttach();
				long[] keys = event.getInstructorKey();

				assignment.emptyInstructorNames();

				for (int j = 0; j < keys.length; j++) {
					assignment.addInstructorName(getDxInstName(_dm
							.getDxSetOfInstructors(), keys[j]));
				}// end for

				if (DxFlags.newRooms) {
					assignment.setRoomName(_dm.getDxSetOfSites()
							.getAllDxRooms().getRoomName(event.getRoomKey()));
				} else {
					assignment.setRoomName(getRoomName(_dm.getSetOfRooms(),
							event.getRoomKey()));
				}
				assignment.setPeriodKey(event.getPeriodKey());

				unit.updateWith(event);
				assignment.setRoomState(event.isRoomFixed());

			}// end for (int i=0; i< eventsToUpdate.size(); i++)
		}

	}

	private String getDxInstName(DxSetOfInstructors soi, long key) {
		if (key != -1) {
			return soi.getInstructorName(key);
		}
		return DConst.NO_ROOM_INTERNAL;
	}

	private String getRoomName(SetOfRooms sor, long eltkey) {
		if (eltkey != -1) {
			return sor.getResource(eltkey).getID();
		}
		return DConst.NO_ROOM_INTERNAL;
	}

	/**
	 * for two event in conflict
	 * 
	 * @param eventIDOne
	 * @param eventIDTwo
	 * @return
	 */
	public String getInstructorConflictDescriptions(String eventIDOne,
			String eventIDTwo) {
		String res = "";
		String str;
		long[] instKeyOne;
		long[] instKeyTwo;
		if (DxFlags.newEvent) {
			instKeyOne = ((DxEvent) getResource(eventIDOne).getAttach())
					.getInstructorKey();
			instKeyTwo = ((DxEvent) getResource(eventIDTwo).getAttach())
					.getInstructorKey();
		} else {
			instKeyOne = ((EventDx) getResource(eventIDOne).getAttach())
					.getInstructorKey();
			instKeyTwo = ((EventDx) getResource(eventIDTwo).getAttach())
					.getInstructorKey();
		}

		for (int i = 0; i < instKeyOne.length; i++) {
			for (int j = 0; j < instKeyTwo.length; j++) {
				if (instKeyOne[i] == instKeyTwo[j]) {
					str = _dm.getDxSetOfInstructors().getInstructorName(
							instKeyOne[i]);
					res += DXToolsMethods.getToken(str, ",", 0) + " "
							+ DXToolsMethods.getToken(str, ",", 1) + ",";
				}// end if(instKeyOne[i] == instKeyTwo[j])
			}// end for (int j=0; j< instKeyOne.length; j++)
		}// end for (int i=0; i< instKeyOne.length; i++)
		return res;
	}

	/**
	 * for instructor availibility conflict
	 * 
	 * @param eventIDOne
	 * @param eventIDTwo
	 * @return
	 */
	public String getInstructorConflictDescriptions(DValue confAt) {
		String res = "";
		String str = "";
		Vector insKeys = (Vector) (confAt).getObjectValue();
		for (int j = 0; j < insKeys.size(); j++) {
			str = _dm.getDxSetOfInstructors().getInstructorName(
					((Long) insKeys.get(j)).longValue());
			res += DXToolsMethods.getToken(str, ",", 0) + " "
					+ DXToolsMethods.getToken(str, ",", 1) + ",";
		}// end for (int j=0; j< insKeys.size(); j++)
		return res;
	}

	/**
	 * 
	 * @param eventIDOne
	 * @param eventIDTwo
	 * @return
	 */
	public String getStudentConflictDescriptions(String eventIDOne,
			String eventIDTwo) {
		// System.out.println("Event 1: "+eventIDOne+" Event2: "+eventIDTwo);
		Vector studentOne = ((Activity) _dm.getSetOfActivities().getResource(
				DXToolsMethods.getToken(eventIDOne, ".", 0)).getAttach())
				.getStudentRegistered();
		Vector studentTwo = ((Activity) _dm.getSetOfActivities().getResource(
				DXToolsMethods.getToken(eventIDTwo, ".", 0)).getAttach())
				.getStudentRegistered();
		Vector studentOneInSection = studentsInSection(studentOne,
				DXToolsMethods.getToken(eventIDOne, ".", 0)
						+ DXToolsMethods.getToken(eventIDOne, ".", 1),
				DXToolsMethods.getToken(eventIDOne, ".", 2));
		Vector studentTwoInSection = studentsInSection(studentTwo,
				DXToolsMethods.getToken(eventIDTwo, ".", 0)
						+ DXToolsMethods.getToken(eventIDTwo, ".", 1),
				DXToolsMethods.getToken(eventIDTwo, ".", 2));
		String res = "";
		for (int i = 0; i < studentOneInSection.size(); i++) {
			if (studentTwoInSection.contains(studentOneInSection.get(i))) {
				String id = _dm.getSetOfStudents().getResource(
						Long.parseLong(studentOneInSection.get(i).toString()))
						.getID();
				id = id.replace(',', ' ');
				id = id.replaceFirst("  ", " ");
				if (id.length() >= DConst.STUDENT_ID_LENGTH) {
					id = id.substring(0, DConst.STUDENT_ID_LENGTH);
				}
				String matricule = "00" + studentOneInSection.get(i).toString();
				res += matricule.substring(matricule.length()
						- DConst.END_STUDENT_MATRICULE)
						+ "-" + id + ",";
			}
		}
		return res;
	}

	/**
	 * 
	 * @param students
	 * @param activityAndType
	 * @param section
	 * @return
	 */
	public Vector studentsInSection(Vector students, String activityAndType,
			String section) {
		Vector<Object> res = new Vector<Object>();
		for (int i = 0; i < students.size(); i++) {
			Student student = _dm.getSetOfStudents().getStudent(
					Long.parseLong((String) students.get(i)));
			if (student.isInGroup(activityAndType, DxTools
					.STIConvertGroupToInt(section)))
				res.add(students.get(i));
		}
		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dInternal.DSetOfResources#getError()
	 */
	public String getError() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dInternal.DSetOfResources#toWrite()
	 */
	public String toWrite() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dInternal.DObject#getSelectedField()
	 */
	public long getSelectedField() {
		return 0;
	}

	/**
	 * 
	 * @param valueToFind
	 * @param newValue
	 */
	public void setAssignedInstAvail() {
		EventDx event;
		long instKey[];
		String currentSite = _dm.getCurrentSiteName();
		_dm.getDxSetOfInstructors().remAllAssignedToASite(currentSite);
		for (int i = 0; i < this.size(); i++) {
			event = (EventDx) this.getResourceAt(i).getAttach();
			int[] perKey = event.getPeriodKeyTable();
			int duration = event.getDuration()
					/ _dm.getTTStructure().getPeriodLenght();
			if (event.isPlaceInAPeriod()) {
				instKey = event.getInstructorKey();
				for (int j = 0; j < instKey.length; j++) {
					DxAvailability instAvailability = _dm
							.getDxSetOfInstructors().getInstructorAvailability(
									instKey[j]);
					int dayIndex = _dm.getTTStructure().findIndexInWeekTable(
							perKey[0]);
					int perPosition = _dm.getTTStructure().getCurrentCycle()
							.getPeriodPositionInDay(perKey[0], perKey[1],
									perKey[2]);
					if (event.getPeriodKey().length() != 0)
						for (int k = 0; k < duration; k++) {
							int perIndex = perPosition + k - 1;
							instAvailability.setAvailabilityOfAPeriod(dayIndex,
									perIndex, currentSite);
						}
				}// end for (int j=0; j< instKey.length; j++)
			}// end if(event.isPlaceInAPeriod())
		}// end for (int i = 0; i < this.size();
	}
}// end class
