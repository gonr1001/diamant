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
public class StiActivity implements ActivityConst {
	
	public StiActivity(HashMap<Integer, String> hm) {
		_activityCode = hm.get(AC);
		_instructorFirstName = hm.get(NAT);
		_intructorLastName = hm.get(GRP);
		_instructorType = hm.get(UAA);
	}

	public String getActivityCode() {
		return this._activityCode;
	}

	public String getinstructorFirstName() {
		return this._instructorFirstName;
	}

	public String getInstructorLastName() {
		return this._intructorLastName;
	}

	public String getinstructorType() {
		return this._instructorType;
	}

	private String _activityCode;
	private String _instructorFirstName;
	private String _intructorLastName;
	private String _instructorType;
	

}
