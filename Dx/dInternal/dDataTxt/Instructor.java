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

public class Instructor extends Object {

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
  public Vector getInstDisp(){return _instructorDisp;}

  /**
   * Print local information
   * OUTPUT: String of instructorID and instructor availability
   * */
  public String toString(){
    String instInfo="";
    for(int i=0; i< _instructorDisp.size()-1; i++)
      instInfo += (String)_instructorDisp.get(i)+CR_LF;
    instInfo += (String)_instructorDisp.get(_instructorDisp.size()-1);
    return instInfo;
  }

}