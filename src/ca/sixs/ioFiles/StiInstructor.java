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
public class StiInstructor implements InstructorConst {

	public StiInstructor(HashMap<Integer, String> hm) {
		_instructorID = hm.get(ID);
		_instructorFirstName = hm.get(FN);
		_intructorLastName = hm.get(LN);
		_instructorType = hm.get(TY);
	}

	public String getInstructorID() {
		return this._instructorID;
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

	private String _instructorID;
	private String _instructorFirstName;
	private String _intructorLastName;
	private String _instructorType;

}
