package dInternal.dUtil;

/**
 * Wrapper class for use with generic student choice.
 * Mimics Integer.
 * @authors ysyam and alexander
 */

public class StudentChoice implements Cloneable{

    /**
    * Construct the StudentChoice object.
    */
    public StudentChoice( ){
      _group = 0;
      _fixed = false;
    }

    /**
     * Clone the StudentChoice object
     * */
    public Object clone(){
        StudentChoice a = new StudentChoice();
        a._Course= this._Course;
        a._fixed = this._fixed;
        a._group= this._group;
        return a;
    }

    /**
     * set the course name
     * INPUT: x, a string of 7 chars eg. GEI4421
     * OUTPUT: a boolean. "true" if x string size is equal or taller than 8 and "false"
     * otherwise
     * */
    public boolean setCourse(String x){
      if (x.length()>=_COURSELENGTH){
        _Course = x.substring(0,_COURSELENGTH);
        return true;
      }
      return false;
    }

    /**
     * set the group choice
     * INPUT: grp, an integer.
     * */
    public void setGroup(int grp){
      _group = grp;
    }

    /**
     * "true" if the student is fixed in the group "false" otherwise
     * INPUT: state, a boolean
     * */
    public void setState(boolean state){
      _fixed= state;
    }

    /**
     * return the course name
     * @return string the course name
     * */
    public String getCourse(){
      return _Course;
    }

    /**
     * return the group
     * @return integer the group
     * */
    public int getGroup(){
      return _group;
    }

    /**
     * return the state
     * @return boolean the state
     * */
    public boolean getState(){
      return _fixed;
    }

    /**
     * Implements the toWrite method.
     * @return the String representation.
     */
    public String toWrite( )    {
      int avalue= (int)'A'-1;
      avalue+=_group;
        return  _Course+"  "+(char)avalue;
    }

    /**"true" if the student is fixed in the group "false" otherwise*/
    private boolean _fixed;
    /**the group in wich the student is placed or fixed 0 if not assigned*/
    private int _group;
    /**course name eg. GEI4421*/
    private String _Course;
    /** Course length*/
    private int _COURSELENGTH = 7;

    }