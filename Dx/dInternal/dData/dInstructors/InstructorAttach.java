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

import dInternal.DObject;
/**
 * @todo new name InstructorFields
 * @todo finish comments
 * @todo
 */

public class InstructorAttach extends DObject {

  private Vector _instructorAvailability;
  private final String CR_LF = "\r\n";

  public InstructorAttach() {
    _instructorAvailability = new Vector();
  }
  /**
   * add availability of instructor in the next day
   */
  public void addAvailability(String disp){
    _instructorAvailability.add(disp);
  }

  /**
   * Remove an availibility day
   * INPUT: day number. day =1 equals instructor Availability position = 0
   * */
  public boolean removeAvailability(int day){
    if (day>0)
      if (day <= _instructorAvailability.size()){
        _instructorAvailability.remove(day-1);
        return true;
      }
    return false;
  }

  /**
   * clear and set instructorDisp
   * INPUT: Vector of new instructor availability (instDisp)
   * */
  public void setAvailability(Vector instDisp){
    _instructorAvailability= new Vector();
    _instructorAvailability= (Vector)instDisp.clone();
  }

  /**
   *
   * */
  public Vector getVectorAvailability(){
    return _instructorAvailability;
  }

  public int[][] getMatrixAvailability(){
    String jour = (String) _instructorAvailability.get(0);
    StringTokenizer st = new StringTokenizer(jour);
    int[][] a = new int[_instructorAvailability.size()][st.countTokens()];
    int nbOfTokens = st.countTokens();
    for(int i = 0; i < _instructorAvailability.size(); i++) {
      jour = (String) _instructorAvailability.get(i);
      st = new StringTokenizer(jour);
      nbOfTokens = st.countTokens();
      for(int j=0; j < nbOfTokens; j++) {
        a[i][j] = Integer.parseInt(st.nextToken());
      } // end for j
    } //end for i
    return a;
  }

  /**
   *
   * @return
   */
  /*public int getMatrixAvailabilityValue(int day, int period){
    if(day < )
  }*/

  public void  setAvailability(int[][] a){
    _instructorAvailability = new Vector();
    String str = "";
    for(int i = 0; i < a.length; i++) {
      for(int j=0; j <a[i].length-1; j++) {
        str += a[i][j] + " ";
      } // end for j
      str += a[i][a[i].length-1];
      _instructorAvailability.add(str);
      str = "";
    } //end for i
  }

  public void  setFullAvailability(){
    String str = "";
    for (int i = 0; i < _instructorAvailability.size(); i++){
      str = _instructorAvailability.get(i).toString();
      str = str.replaceAll("5","1");
       _instructorAvailability.setElementAt(str, i);
    }
  }
  /**
   * return the value of the selected key
   * INPUT: choice, an integer. choice =
   * OUTPUT: an integer. it return -1 if choice is invalid
   * */
  public long getSelectedField(int choice){
    switch(choice){
      case 0: break;
    }
    return -1;
  }

  /**
   * Print local information
   * OUTPUT: String instructor availability
   * */
  public String toWrite(){
    String instInfo="";
    for(int i=0; i< _instructorAvailability.size()-1; i++)
      instInfo += (String)_instructorAvailability.get(i)+CR_LF;
    instInfo += (String)_instructorAvailability.get(_instructorAvailability.size()-1);
    return instInfo;
  }

  /**
  * compare this resource with the specified resource
  * @param resource the specified resource
  * @return bolean true if this resource and the specified resource are equals
  * false if they are not equals
  * */
 public boolean isEquals(DObject inst){
   InstructorAttach instAttach = (InstructorAttach)inst;
   if(!_instructorAvailability.equals(instAttach._instructorAvailability))
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






}