package ca.sixs.ioFiles;

import java.util.HashMap;

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

/**
 * @author gonr1001
 * 
 */
public class StiInstructorID implements InstructorConst {

	public StiInstructorID(HashMap<Integer, String> hm) {
		_instructorID = hm.get(ID_I);
	}

	public String getInstructorID() {
		return this._instructorID;
	}

	private String _instructorID;

}