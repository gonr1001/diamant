package dInternal.dData;

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
import dInternal.dUtil.DXObject;

public class RoomAttach extends DXObject{

  private int _capacity=0;
  private String _description="";
  private String _function="";
  private String _caracteristics="";
  private Vector _roomDisp;//
  private final String CR_LF = "\r\n";

  public RoomAttach() {
    _roomDisp = new Vector();
  }
  /**
   * add an availability day in roomDisp
   */
  public void addAvailability(String disp){
    _roomDisp.add(disp);
  }

  /***
   * add function
   * INPUT: funct (a string)
   * */
  public void setFunction(String funct){
    _function= funct;
  }

  /***
   * set capacity
   * INPUT: capa (an integer)
   * */
  public void setCapacity(int capa){
    _capacity = capa;
  }

  /***
   * set description
   * INPUT: desc (a string)
   * */
  public void setDescription(String desc){
    _description = desc;
  }

  /***
   * add caracteristics
   * INPUT: carac (a string)
   * */
  public void setCaracteristics(String carac){
    _caracteristics=carac;
  }

  /**
   * Remove an availibility day
   * INPUT: day number. day =1 equals roomDisp position = 0
   * */
  public boolean removeDispDay(int day){
    if (day>0)
      if (day <= _roomDisp.size()){
        _roomDisp.remove(day-1);
        return true;
      }
    return false;
  }

  /**
   * clear and set roomDisp
   * INPUT: Vector of new instructor availability (roomDisp)
   * */
  public void setRoomDisp(Vector roomDisp){
    _roomDisp= new Vector();
    _roomDisp= (Vector)roomDisp.clone();
  }

  /**
   *
   * */
  public Vector getRoomAttachDisp(){
    return _roomDisp;
  }
  /*
  */
  public int getCapacity(){
    return _capacity;
  }

   /*
  */
  public String getDescription(){
    return _description;
  }

  /**
   * */
  public String getFunction(){
    return _function;
  }

  /**
   * */
  public String getCaracteristics(){
    return _caracteristics;
  }

  public int[][] getRoomAvailability(){
    String jour = (String) _roomDisp.get(0);
    StringTokenizer st = new StringTokenizer(jour);
    int[][] a = new int[_roomDisp.size()][st.countTokens()];
    int nbOfTokens = st.countTokens();
    for(int i = 0; i < _roomDisp.size(); i++) {
      jour = (String) _roomDisp.get(i);
      st = new StringTokenizer(jour);
      nbOfTokens = st.countTokens();
      for(int j=0; j < nbOfTokens; j++) {
        a[i][j] = Integer.parseInt(st.nextToken());
      } // end for j
    } //end for i
    return a;
  }

  public void  setRoomAvailability(int[][] a){
    _roomDisp = new Vector();
    String str = "";
    for(int i = 0; i < a.length; i++) {
      for(int j=0; j <a[i].length; j++) {
        str += a[i][j] + " ";
      } // end for j
      _roomDisp.add(str);
      str = "";
    } //end for i
  }

  /**
   * return the value of the selected key
   * INPUT: choice, an integer. choice = 0 return _capacity
   * OUTPUT: an integer. it return -1 if choice is invalid
   * */
  public int getSelectedField(int choice){
    switch(choice){
      case 0: return _capacity;
    }
    return -1;
  }

  /**
   * Print local information
   * OUTPUT: String of roomID and room availability
   * */
  public String toWrite(){
    String roomInfo=_capacity+";"+_function+";"+_caracteristics+";"
                   +_description+";";
    return roomInfo;
  }

}