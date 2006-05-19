package dInternal.dData.dInstructors;

/**
 * <p>Title: Diamant 1.5</p>
 * <p>Description: exam timetable construction with Condition Pattern</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author rgr, ysyam, alexander
 * @version 1.0
 */
import java.util.StringTokenizer;
import java.util.Vector;

import dConstants.DConst;
import dInternal.DObject;

/**
 * @todo new name InstructorFields
 * @todo finish comments
 * @todo
 */

public class InstructorAttach extends DObject {
	/**
	 * @associates String 
	 */
	private Vector <String> _instructorAvailability;

    /**
     * @associates String 
     */
	private Vector <String> _instructorSiteAvailability;

	private final String CR_LF = "\r\n";

	public InstructorAttach() {
		_instructorAvailability = new Vector <String> ();
		_instructorSiteAvailability = new Vector <String> ();
	}

	/**
	 * add availability of instructor in the next day
	 */
	public void addAvailability(String disp) {
		_instructorAvailability.add(disp);
	}

	/**
	 * Remove an availibility day
	 * INPUT: day number. day =1 equals instructor Availability position = 0
	 * */
	public boolean removeAvailability(int day) {
		if (day > 0)
			if (day <= _instructorAvailability.size()) {
				_instructorAvailability.remove(day - 1);
				return true;
			}
		return false;
	}

	/**
	 * clear and set instructorDisp
	 * INPUT: Vector of new instructor availability (instDisp)
	 * */
	public void setAvailability(Vector instDisp) {
		_instructorAvailability = new Vector <String> ();
		_instructorAvailability = (Vector ) instDisp.clone();
	}
	
	/**
	 * clear and set instructorDisp
	 * INPUT: Vector of new instructor availability (instDisp)
	 * */
	public void setAssignAvailability(Vector instAssign) {
		_instructorSiteAvailability = new Vector <String>();
		_instructorSiteAvailability = (Vector) instAssign.clone();
	}

	/**
	 *
	 * */
	public Vector getVectorAvailability() {
		return _instructorAvailability;
	}

	public int[][] getMatrixAvailability() {
		return getMatrixAvailabilityTable(_instructorAvailability);
	}
	
	public String[][] getMatrixAssignAvailability() {
		return getAssignAvailabilityTable(_instructorSiteAvailability);
	}

	/**
	 * 
	 * @return
	 */
	private int[][] getMatrixAvailabilityTable(Vector vect) {
		//String jour = (String) _instructorAvailability.get(0);
		String jour = (String) vect.get(0);
		StringTokenizer st = new StringTokenizer(jour);
		int[][] a = new int[_instructorAvailability.size()][st.countTokens()];
		int nbOfTokens = st.countTokens();
		for (int i = 0; i < _instructorAvailability.size(); i++) {
			jour =  _instructorAvailability.get(i);
			st = new StringTokenizer(jour);
			nbOfTokens = st.countTokens();
			for (int j = 0; j < nbOfTokens; j++) {
				a[i][j] = Integer.parseInt(st.nextToken());
			} // end for j
		} //end for i
		return a;
	}
	
	/**
	 * 
	 * @return
	 */
	private String[][] getAssignAvailabilityTable(Vector <String> vect) {
		//String jour = (String) _instructorAvailability.get(0);
		String jour =  vect.get(0);
		StringTokenizer st = new StringTokenizer(jour);
		String[][] a = new String[vect.size()][st.countTokens()];
		int nbOfTokens = st.countTokens();
		for (int i = 0; i < vect.size(); i++) {
			jour =  vect.get(i);
			st = new StringTokenizer(jour);
			nbOfTokens = st.countTokens();
			for (int j = 0; j < nbOfTokens; j++) {
				a[i][j] = st.nextToken();
			} // end for j
		} //end for i
		return a;
	}

	/**
	 *
	 * @return
	 */
	

	/**
	 * 
	 */
	public void setAvailability(int[][] a) {
		_instructorAvailability = new Vector <String> ();
		String str = "";
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length - 1; j++) {
				str += a[i][j] + " ";
			} // end for j
			str += a[i][a[i].length - 1];
			_instructorAvailability.add(str);
			str = "";
		} //end for i
	}
	
	private void initAssignAvailability() {
		int[][] a =  this.getMatrixAvailabilityTable(_instructorAvailability);
		_instructorSiteAvailability = new Vector <String> ();
		String site="";
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length - 1; j++) {
				site += DConst.AVAILABILITY_ASSIGN_DEFAULT+" ";
			} // end for j
			site += DConst.AVAILABILITY_ASSIGN_DEFAULT;
			_instructorSiteAvailability.add(site);
			site="";
		} //end for i
	}
	
	public void setAssignAvailability(String[][] a) {
		//_instructorAvailability = new Vector(1);
		_instructorSiteAvailability = new Vector <String> ();
		String str = "";
		//String site="";
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length - 1; j++) {
				str += a[i][j] + " ";
				
			} // end for j
			str += a[i][a[i].length - 1];
			_instructorSiteAvailability.add(str);
			str = "";
		} //end for i
	}

	public void setFullAvailability() {
		String str = "";
		for (int i = 0; i < _instructorAvailability.size(); i++) {
			str = _instructorAvailability.get(i).toString();
			str = str.replaceAll("5", "1");
			_instructorAvailability.setElementAt(str, i);
		}
	}

	/**
	 * return the value of the selected key
	 * INPUT: choice, an integer. choice =
	 * OUTPUT: an integer. it return -1 if choice is invalid
	 * */
	public long getSelectedField(int choice) {
		switch (choice) {
		case 0:
			break;
		}
		return -1;
	}

	/**
	 * Print local information
	 * OUTPUT: String instructor availability
	 * */
	public String toWrite() {
		String instInfo = "";
		for (int i = 0; i < _instructorAvailability.size() - 1; i++){
			instInfo +=  _instructorAvailability.get(i);
			if(_instructorSiteAvailability.size()==_instructorAvailability.size())
				instInfo += DConst.AVAILABILITY_SEPARATOR+
					 _instructorSiteAvailability.get(i);
			instInfo += CR_LF;
		}
		instInfo +=  _instructorAvailability
				.get(_instructorAvailability.size() - 1);
		if(_instructorSiteAvailability.size()==_instructorAvailability.size())
			instInfo +=DConst.AVAILABILITY_SEPARATOR+
				 _instructorSiteAvailability.get(_instructorAvailability.size() - 1);
		return instInfo;
	}

	/**
	 * compare this resource with the specified resource
	 * @param resource the specified resource
	 * @return bolean true if this resource and the specified resource are equals
	 * false if they are not equals
	 * */
	public boolean isEquals(DObject inst) {
		InstructorAttach instAttach = (InstructorAttach) inst;
		if (!_instructorAvailability.equals(instAttach._instructorAvailability))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see dInternal.DObject#getSelectedField()
	 */
	public long getSelectedField() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 
	 * @param dayIndex
	 * @param periodIndex
	 * @param site
	 */
	public void setAvailabilityOfAPeriod(int dayIndex, int periodIndex, String site) {
		if (_instructorSiteAvailability.size()==0)
			this.initAssignAvailability();
		String[][] matrix = getAssignAvailabilityTable(_instructorSiteAvailability);
		String defaultValue = String.valueOf(DConst.AVAILABILITY_ASSIGN_DEFAULT);
		if ((dayIndex < matrix.length))
			if (periodIndex < matrix[dayIndex].length)
				if (matrix[dayIndex][periodIndex].equalsIgnoreCase(defaultValue)){
					matrix[dayIndex][periodIndex] += site;
					setAssignAvailability(matrix);
				}else{// else if (matrix[dayIndex][periodIndex].equalsIgnoreCase("0"))
//					if(!matrix[dayIndex][periodIndex].contains(site)){ // XXXX Pascal: lien inutile avec JDK 1.5
					if(!matrix[dayIndex][periodIndex].matches(".*" + site + ".*")){
						matrix[dayIndex][periodIndex] += /*DConst.AVAILABILITY_ASSIGN_SEPARATOR+*/ site;
						setAssignAvailability(matrix);
					}
				}
	}// end setAvailabilityOfAPeriod(int dayIndex, i ...
	
	/**
	 * Return sites where instructor is already assigned in the given period
	 * @param dayIndex
	 * @param periodIndex
	 * @param site
	 * @return
	 */
	public Vector isAssignedInPeriod(int dayIndex, int periodIndex, Vector sites){
		Vector <String> v= new Vector <String> ();
		for (int i = 0; i< sites.size(); i++){
			String site = (String)sites.get(i);
			if (_instructorSiteAvailability.size()==0)
				this.initAssignAvailability();
			String[][] matrix = getAssignAvailabilityTable(_instructorSiteAvailability);
			if ((dayIndex < matrix.length))
				if (periodIndex < matrix[dayIndex].length)
//					if(matrix[dayIndex][periodIndex].contains(site)) // XXXX Pascal: lien inutile avec JDK 1.5
			if(matrix[dayIndex][periodIndex].matches(".*" + site + ".*"))
						v.add(site);
		} //end for (int i = 0; i< sites.size(); i++)
		return v;
	}
	
	
	/**
	 * 
	 * @param site
	 */
	public void remAllAssignedToASite(String site){
		if (_instructorSiteAvailability.size()==0)
			this.initAssignAvailability();
		for (int i = 0; i < _instructorSiteAvailability.size(); i++) {
			String str = _instructorSiteAvailability.get(i);
			str = str.replaceAll(site,"");
			_instructorSiteAvailability.setElementAt(str,i);
		}// for (int i = 0; i < _instructorSiteAvailability.size(); i++)
	}

}