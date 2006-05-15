package dInternal.dOptimization;

import java.util.StringTokenizer;
import java.util.Vector;

import dConstants.DConst;
import dInterface.dUtil.DXTools;
import dInternal.DModel;
import dInternal.DResource;
import dInternal.DSetOfResources;
import dInternal.DValue;
import dInternal.dData.AvailabilityAttach;
import dInternal.dData.dActivities.Activity;
import dInternal.dData.dActivities.Assignment;
import dInternal.dData.dActivities.Section;
import dInternal.dData.dActivities.SetOfActivities;
import dInternal.dData.dActivities.Type;
import dInternal.dData.dActivities.Unity;
import dInternal.dData.dInstructors.DxSetOfInstructors;
import dInternal.dData.dInstructors.SetOfInstructors;
import dInternal.dData.dRooms.SetOfRooms;
import dInternal.dData.dStudents.Student;
import dInternal.dTimeTable.Period;
import dInternal.dUtil.DXToolsMethods;

public class SetOfEvents extends DSetOfResources {
	/**
	 * @associates SetOfEventsListener
	 */
	//public Vector _soeListeners = new Vector(1);
	//protected boolean _isEventPlaced=false;
	private DModel _dm;

	//private String _UNAVAILABLE= "------";

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
	public void build(SetOfActivities soa, DSetOfResources soie) {
		String unityKey;
		for (int i = 0; i < soa.size(); i++) {
			DResource activity = soa.getResourceAt(i);
			long instructorKey = -1, roomKey; //=-1;
			if (((Activity) activity.getAttach()).isActivityVisibility()) {
				for (int j = 0; j < ((Activity) activity.getAttach())
						.getSetOfTypes().size(); j++) {
					DResource type = ((Activity) activity.getAttach())
							.getSetOfTypes().getResourceAt(j);
					for (int k = 0; k < ((Type) type.getAttach())
							.getSetOfSections().size(); k++) {
						DResource section = ((Type) type.getAttach())
								.getSetOfSections().getResourceAt(k);
						for (int l = 0; l < ((Section) section.getAttach())
								.getSetOfUnities().size(); l++) {
							DResource unity = ((Section) section.getAttach())
									.getSetOfUnities().getResourceAt(l);
							Assignment assignment = (Assignment) ((Unity) unity
									.getAttach()).getAssignment(
									_dm.getTTStructure()
											.getCurrentCycleResource().getID())
									.getAttach();
							if (assignment != null) {
								unityKey = activity.getKey() + "."
										+ type.getKey() + "."
										+ section.getKey() + "."
										+ unity.getKey() + ".";
								String unityID = activity.getID() + "."
										+ type.getID() + "." + section.getID()
										+ "." + unity.getID() + ".";
								if (DXToolsMethods.getToken(
										assignment.getPeriodKey(), ".", 0)
										.equalsIgnoreCase("0")) {
									String perKeys = _dm
											.getTTStructure()
											.getCurrentCycle()
											.getPeriod(
													assignment.getDateAndTime());
									Period per = _dm.getTTStructure()
											.getCurrentCycle()
											.getPeriodByPeriodKey(perKeys);
									if (per != null)
										assignment.setPeriodKey(perKeys);
									else
										assignment.setPeriodKey("1.1.1");
								}// end if(assignment.getPeriodKey()[0]==0)
								//System.out.println("event " +unityID+"
								// InsName " +assignment.getInstructorName());
								if(!DConst.newInstructors)
									rgr(soie, assignment, unityID);

								int roomIndex = _dm.getSetOfRooms()
										.getIndexOfResource(
												assignment.getRoomName());
								if (roomIndex != -1) {
									roomKey = _dm.getSetOfRooms()
											.getResourceAt(roomIndex).getKey();
									//assignment.setRoomKey(roomKey);
								} else {
									roomKey = -1;
									DValue error = new DValue();
									String str = assignment.getRoomName();
									if (str.equals(DConst.NO_ROOM_INTERNAL))
										str = DConst.NO_ROOM_EXTERNAL;
									error.setStringValue(DConst.ERROR_TAG
											+ unityID + ": " + DConst.NOT_ROOM
											+ "« " + str + " »");
									soie.addResource(new DResource("3", error),
											0);
								}
								//int[] dayTime = assignment.getDateAndTime();

								EventAttach event = new EventAttach(unityKey,
										assignment.getSetInstructorKeys(),
										roomKey, ((Unity) unity.getAttach())
												.getDuration(), assignment
												.getPeriodKey());
								event.setAssigned(((Unity) unity.getAttach())
										.isAssign());
								event.setPermanentState(((Unity) unity
										.getAttach()).isPermanent());
								event.setRoomFixed(assignment.getRoomState());
								event.setRoomFunction(((Unity) unity
										.getAttach())
										.getFirstPreferFunctionRoom());
								//System.out.println("Unity Key: "+unityKey+ "
								// - Period Key:
								// "+((Cycle)cycle.getAttach()).getPeriod(dayTime));//debug
								this.addResource(new DResource(unityID, event),
										0);
								//System.out.println("event " +unityID+"
								// InsName " +assignment.getInstructorName());
							}// end if(assignement!=null)
						}// end for(int l=0; l<
						// ((Section)section.getAttach()).getSetOfUnities().size();
						// l++)
					}// end for(int k=0; k<
					// ((Type)type.getAttach()).getSetOfSections().size(); k++)
				}//for(int j=0; j< activity.getSetOfTypes().size(); j++)
			}//end if(((Activity)activity.getAttach()).getActivityVisibility())
		}// end for (int i=0; i< soa.size(); i++)
	} //end build

	private void rgr(DSetOfResources soie, Assignment assignment, String unityID) {
		long instructorKey;
		String[] instructorNames = assignment
				.getInstructorNames();
		for (int m = 0; m < instructorNames.length; m++) {
			int instructorIndex = _dm
					.getSetOfInstructors()
					.getIndexOfResource(
							instructorNames[m]);
			if (instructorIndex != -1) {
				instructorKey = _dm
						.getSetOfInstructors()
						.getResourceAt(instructorIndex)
						.getKey();
				assignment
						.addInstructorKeys(instructorKey);
			} else {
				DValue error = new DValue();
				error.setStringValue(DConst.ERROR_TAG
						+ unityID + ": "
						+ DConst.NOT_INSTRUCTOR + "« "
						+ instructorNames[m] + " »");
				soie.addResource(new DResource("2",
						error), 0);
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
			if (((EventAttach) getResourceAt(i).getAttach()).isPlaceInAPeriod())
				count++;
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
		EventAttach event;

		for (int i = 0; i < eventsToUpdate.size(); i++) {
			event = (EventAttach) ((DResource) eventsToUpdate.get(i))
					.getAttach();

			long actKey = Long.parseLong(DXToolsMethods.getToken(event
					.getPrincipalRescKey(), ".", 0));
			long typeKey = Long.parseLong(DXToolsMethods.getToken(event
					.getPrincipalRescKey(), ".", 1));
			long sectKey = Long.parseLong(DXToolsMethods.getToken(event
					.getPrincipalRescKey(), ".", 2));
			long unitKey = Long.parseLong(DXToolsMethods.getToken(event
					.getPrincipalRescKey(), ".", 3));

			Unity unity = soa.getUnity(actKey, typeKey, sectKey, unitKey);
			Assignment assignment = (Assignment) unity.getSetOfAssignments()
					.getResourceAt(_dm.getTTStructure().getCurrentCycleIndex())
					.getAttach();
			long[] keys = event.getInstructorKey();

			assignment.emptyInstructorNames();
			for (int j = 0; j < keys.length; j++) {
				assignment.addInstructorName(getInstName(_dm
						.getSetOfInstructors(), keys[j]));
			}// end for

			assignment.setRoom(getRoomName(_dm.getSetOfRooms(), event
					.getRoomKey()));
			assignment.setPeriodKey(event.getPeriodKey());

			unity.setAssign(event.isAssigned());
			unity.setPermanent(event.getPermanentState());
			unity.setDuration(event.getDuration());
			unity.setFirstPreferFunctionRoom(event.getRoomFunction());
			assignment.setRoomState(event.isRoomFixed());

		}// end for (int i=0; i< eventsToUpdate.size(); i++)

	}

	/**
	 * get a resource key
	 * 
	 * @param soresc
	 * @param elt
	 * @return the resource key or -1 if key does not found
	 */
	/*
	 * private String getRescName(DSetOfResources sor, long eltkey){ if
	 * (eltkey!=-1){ return sor.getResource(eltkey).getID(); } return
	 * DConst.NO_ROOM_INTERNAL; }
	 */

	/**
	 * get a resource key
	 * 
	 * @param soresc
	 * @param elt
	 * @return the resource key or -1 if key does not found
	 */
	private String getInstName(SetOfInstructors sor, long eltkey) {
		if (eltkey != -1) {
			return sor.getResource(eltkey).getID();
		}
		return DConst.NO_ROOM_INTERNAL;
	}

	/**
	 * get a resource key
	 * 
	 * @param soresc
	 * @param elt
	 * @return the resource key or -1 if key does not found
	 */
	private int getNewInstName(DxSetOfInstructors sor, int eltkey) {
		if (eltkey != -1) {
			return sor.getInstructorID(eltkey);
		}
		return 0;
	}

	/**
	 * get a resource key
	 * 
	 * @param soresc
	 * @param elt
	 * @return the resource key or -1 if key does not found
	 */
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
		long[] instKeyOne = ((EventAttach) getResource(eventIDOne).getAttach())
				.getInstructorKey();
		long[] instKeyTwo = ((EventAttach) getResource(eventIDTwo).getAttach())
				.getInstructorKey();
		for (int i = 0; i < instKeyOne.length; i++) {
			for (int j = 0; j < instKeyTwo.length; j++) {
				if (instKeyOne[i] == instKeyTwo[j]) {
					String str = _dm.getSetOfInstructors().getResource(
							instKeyOne[i]).getID();
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
	public String getInstructorConflictDescriptions(DValue confAt) {//, String
		// eventIDOne)
		// {
		String res = "";
		Vector insKeys = (Vector) (confAt).getObjectValue();
		for (int j = 0; j < insKeys.size(); j++) {
			String str = _dm.getSetOfInstructors().getResource(
					((Long) insKeys.get(j)).longValue()).getID();
			res += DXToolsMethods.getToken(str, ",", 0) + " "
					+ DXToolsMethods.getToken(str, ",", 1) + ",";
		}// end for (int j=0; j< insKeys.size(); j++)

		return res;
	}

	/*	public String getInstructorConflictDescriptions(DValue confAt) {//, String
	 // eventIDOne)
	 // {
	 String res = "";
	 Vector insKeys = (Vector) (confAt).getObjectValue();
	 for (int j = 0; j < insKeys.size(); j++) {
	 String str = _dm.getSetOfInstructors().getResource(
	 ((Long) insKeys.get(j)).longValue()).getID();
	 res += DXToolsMethods.getToken(str, ",", 0) + " "
	 + DXToolsMethods.getToken(str, ",", 1) + ",";
	 }// end for (int j=0; j< insKeys.size(); j++)

	 return res;
	 }
	 */
	/**
	 * 
	 * @param eventIDOne
	 * @param eventIDTwo
	 * @return
	 */
	public String getStudentConflictDescriptions(String eventIDOne,
			String eventIDTwo) {
		//System.out.println("Event 1: "+eventIDOne+" Event2: "+eventIDTwo);
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
		Vector res = new Vector();
		for (int i = 0; i < students.size(); i++) {
			//StudentAttach sa =
			// (StudentAttach)_dm.getSetOfStudents().getResource(Long.parseLong((String)students.get(i))).getAttach();
			Student student = _dm.getSetOfStudents().getStudent(
					Long.parseLong((String) students.get(i)));
			if (student.isInGroup(activityAndType, DXTools
					.STIConvertGroupToInt(section)))
				res.add(students.get(i));
		}
		return res;
	}

	/**
	 * 
	 * @param component
	 */
	/*public void sendEvent(Component component) {
	 SetOfEventsEvent event = new SetOfEventsEvent(this);
	 for (int i = 0; i < _soeListeners.size(); i++) {
	 SetOfEventsListener soel = (SetOfEventsListener) _soeListeners
	 .elementAt(i);
	 soel.changeInSetOfEvents(event, component);
	 }
	 }*/

	/**
	 * 
	 * @param dml
	 */
	/*public synchronized void addSetOfEventsListener(SetOfEventsListener sorl) {
	 if (_soeListeners.contains(sorl)) {
	 return;
	 }
	 _soeListeners.addElement(sorl);
	 //System.out.println("addSetOfEvents Listener ...");//debug
	 }*/

	/*
	 * (non-Javadoc)
	 * 
	 * @see dInternal.DSetOfResources#getError()
	 */
	public String getError() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dInternal.DSetOfResources#toWrite()
	 */
	public String toWrite() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dInternal.DObject#getSelectedField()
	 */
	public long getSelectedField() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 
	 * @param valueToFind
	 * @param newValue
	 */
	public void setAssignedInstAvail() {
		EventAttach event;
		long instKey[];
		String currentSite = _dm.getCurrentSite();
		_dm.getSetOfInstructors().remAllAssignedToASite(currentSite);
		for (int i = 0; i < this.size(); i++) {
			event = (EventAttach) this.getResourceAt(i).getAttach();
			int[] perKey = event.getPeriodKeyTable();
			int duration = event.getDuration()
					/ _dm.getTTStructure().getPeriodLenght();
			if (event.isPlaceInAPeriod()) {
				instKey = event.getInstructorKey();
				for (int j = 0; j < instKey.length; j++) {
					AvailabilityAttach inst = (AvailabilityAttach) _dm
							.getSetOfInstructors().getResource(instKey[j])
							.getAttach();
					int dayIndex = _dm.getTTStructure().findIndexInWeekTable(
							perKey[0]);
					int perPosition = _dm.getTTStructure().getCurrentCycle()
							.getPeriodPositionInDay(perKey[0], perKey[1],
									perKey[2]);
					if (event.getPeriodKey().length() != 0)
						for (int k = 0; k < duration; k++) {
							int perIndex = perPosition + k - 1;
							inst.setAvailabilityOfAPeriod(dayIndex, perIndex,
									currentSite);
						}
				}// end for (int j=0; j< instKey.length; j++)
			}// end if(event.isPlaceInAPeriod())
		}// end for (int i = 0; i < this.size();
	}
}// end class
