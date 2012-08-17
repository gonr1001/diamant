/**
 * #rep
 * This comment must be replaced by
 * a copyright or copy left allowing to
 * distribute the code as open source 
 *
 * the prefix for the packages is
 * ca.sixs.
 *
 * 
 */
package ca.sixs.ioFiles;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author gonr1001
 * 
 */
public class StiActivity implements ActivityConst {

	public StiActivity(HashMap<Integer, String> hm) {
		//_instructorsForActivity = new ArrayList<StiInstructorID>();
		_activityCode = hm.get(AC);
		_activityType = hm.get(ACT_TYP);
		_group = hm.get(GRP);
		_uaa = hm.get(UAA);
		_location = hm.get(LOC);
		_maxStudents = hm.get(MAX_S);
	}

	public String getActivityCode() {
		return this._activityCode;
	}

	public String getType() {
		return this._activityType;
	}

	public String getGroup() {
		return this._group;
	}

	public String getUAA() {
		return this._uaa;
	}

	public String getLocation() {
		return this._location;
	}

	public String getMaxStu() {
		return this._maxStudents;
	}

	/**
	 * @param loadInstructorsFromActivities
	 */
	public void setInstructors(ArrayList<StiInstructorID> instructors) {
		_instructorsForActivity = instructors;
	}

	public ArrayList<StiInstructorID> getInstructors() {
		return _instructorsForActivity;
	}

	/**
	 * @param loadInstructorsFromActivities
	 */
	public void setSlots(ArrayList<StiSlot> slots) {
		_slotsForActivity = slots;
	}

	public ArrayList<StiSlot> getSlots() {
		return _slotsForActivity;
	}

	private String _activityCode;
	private String _activityType;
	private String _group;
	private String _uaa;
	private String _location;
	private String _maxStudents;
	private ArrayList<StiInstructorID> _instructorsForActivity;
	private ArrayList<StiSlot> _slotsForActivity;

}
