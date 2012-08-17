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
public class StiStudent implements StudentConst {

	public StiStudent(HashMap<Integer, String> hm) {
		_student_ID = hm.get(ID_S);
		_studentFirstName = hm.get(FN_S);
		_studentLastName = hm.get(LN_S);

	}

	public String getInstructorID() {
		return this._student_ID;
	}

	public String getinstructorFirstName() {
		return this._studentFirstName;
	}

	public String getInstructorLastName() {
		return this._studentLastName;
	}

	private String _student_ID;
	private String _studentFirstName;
	private String _studentLastName;

}
