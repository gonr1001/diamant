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

import java.util.HashMap;

/**
 * @author gonr1001
 *
 */
public class StiActivityInStudent implements ActivityConst {
	
	public StiActivityInStudent(HashMap<Integer, String> hm) {
		_activityCode = hm.get(AC);
		_activityType = hm.get(ACT_TYP);
		_group = hm.get(GRP);
		_location = hm.get(LOC);
		_fixedGroup = hm.get(FIX_GRP);
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

	public String getLocation() {
		return this._location;
	}

	public String getFixedGroup() {
		return this._fixedGroup;
	}


	private String _activityCode;
	private String _activityType;
	private String _group;
	private String _location;
	private String _fixedGroup;
	
}

