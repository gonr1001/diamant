package dInternal;

/**
 * <p>Title: miniDia</p>
 * <p>Description: exam timetable construction with Condition Pattern</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author rgr, ysyam, alexander
 * @version 1.0
 */
import java.util.Vector;
import java.util.StringTokenizer;
import dResources.DXObject;

public class Instructor extends DXObject {

  private Vector _instructorDisp;//
  private final String CR_LF = "\r\n";

  public Instructor() {
    _instructorDisp = new Vector();
  }
  /**
   * add an availability day in instructorDisp
   */
  public void addDispDay(String disp){
    _instructorDisp.add(disp);
  }

  /**
   * Remove an availibility day
   * INPUT: day number. day =1 equals instructorDisp position = 0
   * */
  public boolean removeDispDay(int day){
    if (day>0)
      if (day <= _instructorDisp.size()){
        _instructorDisp.remove(day-1);
        return true;
      }
    return false;
  }

  /**
   * clear and set instructorDisp
   * INPUT: Vector of new instructor availability (instDisp)
   * */
  public void setInstructorDisp(Vector instDisp){
    _instructorDisp= new Vector();
    _instructorDisp= (Vector)instDisp.clone();
  }

  /**
   *
   * */
  public Vector getInstDisp(){
    return _instructorDisp;
  }

  public int[][] getInstAvailability(){
    String jour = (String) _instructorDisp.get(0);
    StringTokenizer st = new StringTokenizer(jour);
    int[][] a = new int[_instructorDisp.size()][st.countTokens()];
    int nbOfTokens = st.countTokens();
    for(int i = 0; i < _instructorDisp.size(); i++) {
      jour = (String) _instructorDisp.get(i);
      st = new StringTokenizer(jour);
      nbOfTokens = st.countTokens();
      for(int j=0; j < nbOfTokens; j++) {
        a[i][j] = Integer.parseInt(st.nextToken());
      } // end for j
    } //end for i
    return a;
  }

  public void  setInstAvailability(int[][] a){
    _instructorDisp = new Vector();
    String str = "";
    for(int i = 0; i < a.length; i++) {
      for(int j=0; j <a[i].length; j++) {
        str += a[i][j] + " ";
      } // end for j
      _instructorDisp.add(str);
      str = "";
    } //end for i
  }

  /**
   * return the value of the selected key
   * INPUT: choice, an integer. choice =
   * OUTPUT: an integer. it return -1 if choice is invalid
   * */
  public int getSelectedField(int choice){
    switch(choice){
      case 0: break;
    }
    return -1;
  }

  /**
   * Print local information
   * OUTPUT: String instructor availability
   * */
  public String toString(){
    String instInfo="";
    for(int i=0; i< _instructorDisp.size()-1; i++)
      instInfo += (String)_instructorDisp.get(i)+CR_LF;
    instInfo += (String)_instructorDisp.get(_instructorDisp.size()-1);
    return instInfo;
  }



}