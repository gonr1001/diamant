package dInternal.dDataXML.rooms;

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

import dInternal.dDataTxt.Resource;
import dInternal.dDataTxt.SetOfResources;
import dInternal.dUtil.DXObject;
import dInternal.dUtil.DXValue;

public class RoomAttach extends DXObject{

  private int _capacity=0;
  private String _description="";
  private int _function=-1;
  private SetOfResources _setOfCaracteristics;
  private Vector _roomAvailability;//
  //private final String CR_LF = "\r\n";

  public RoomAttach() {
    _roomAvailability = new Vector();
    _setOfCaracteristics= new SetOfResources(3);
  }
  /**
   * add an availability day in roomDisp
   */
  public void addAvailability(String disp){
    _roomAvailability.add(disp);
  }

  /***
   * add function
   * INPUT: funct (a string)
   * */
  public void setFunction(int funct){
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
   * @param int the caracrteristic to add in the room
   * */
  public boolean addCaracteristics(int  carac){
    if(carac!=-1){
      _setOfCaracteristics.setCurrentKey(carac);
      return _setOfCaracteristics.addResource(new Resource("",new DXValue()),0);
    }
    return false;
  }

  /**
   * Remove an availibility day
   * INPUT: day number. day =1 equals roomDisp position = 0
   * */
  public boolean removeAvailDay(int day){
    if (day>0)
      if (day <= _roomAvailability.size()){
        _roomAvailability.remove(day-1);
        return true;
      }
    return false;
  }

  /**
   * clear and set roomDisp
   * INPUT: Vector of new room availability (roomDisp)
   * */
  public void setAvailability(Vector roomDisp){
    _roomAvailability= new Vector();
    _roomAvailability= (Vector)roomDisp.clone();
  }

  /**
   *
   * */
  public Vector getVectorAvailability(){
    return _roomAvailability;
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
  public int getFunction(){
    return _function;
  }

  /**
   * */
  public SetOfResources getSetOfCaracteristics(){
    return _setOfCaracteristics;
  }

  public int[][] getMatrixAvailability(){
    String jour = (String) _roomAvailability.get(0);
    StringTokenizer st = new StringTokenizer(jour);
    int[][] a = new int[_roomAvailability.size()][st.countTokens()];
    int nbOfTokens = st.countTokens();
    for(int i = 0; i < _roomAvailability.size(); i++) {
      jour = (String) _roomAvailability.get(i);
      st = new StringTokenizer(jour);
      nbOfTokens = st.countTokens();
      for(int j=0; j < nbOfTokens; j++) {
        a[i][j] = Integer.parseInt(st.nextToken());
      } // end for j
    } //end for i
    return a;
  }

  public void  setAvailability(int[][] a){
    _roomAvailability = new Vector();
    String str = "";
    for(int i = 0; i < a.length; i++) {
      for(int j=0; j <a[i].length; j++) {
        str += a[i][j];
        if (j< a[i].length-1)
          str += " ";
      } // end for j
      _roomAvailability.add(str);
      str = "";
    } //end for i
  }

  /**
   * return the value of the selected key
   * INPUT: choice, an integer. choice = 0 return _capacity
   * OUTPUT: an integer. it return -1 if choice is invalid
   * */
  public long getSelectedField(int choice){
    switch(choice){
      case 0: return _capacity;
    }
    return -1;
  }

  public void  setFullAvailability(){
    String str = "";
    for (int i = 0; i < _roomAvailability.size(); i++){
      str = _roomAvailability.get(i).toString();
      str = str.replaceAll("5","1");
       _roomAvailability.setElementAt(str, i);
    }
  }
  /**
   * Print local information
   * OUTPUT: String of roomID and room availability
   * */
  public String toWrite(){
    String _caracteristics="";
    for (int i=0; i< _setOfCaracteristics.size(); i++){
      _caracteristics+=_setOfCaracteristics.getResourceAt(i).getKey();
      if (i< _setOfCaracteristics.size()-1)
        _caracteristics+=",";
    }

    String avail="";
    for(int i=0; i< _roomAvailability.size()-1; i++)
      avail += (String)_roomAvailability.get(i)+",";
    //System.out.println("Room: "+_capacity+" -- "+_caracteristics+_roomAvailability.size());//debug
    avail += (String)_roomAvailability.get(_roomAvailability.size()-1);
    String roomInfo=_capacity+";"+_function+";"+_caracteristics+";"
                   +_description+";"+avail+";";
    return roomInfo;
  }

  /**
   *
   */
  public void setStandardAvailability(){
    String stAvail="1 1 1 1 1 1 1 1 1 1 1 1";
    for (int i=0; i< 5; i++)
      _roomAvailability.add(stAvail);
  }

  /**
   * compare this resource with the specified resource
   * @param resource the specified resource
   * @return bolean true if this resource and the specified resource are equals
   * false if they are not equals
   * */
  public boolean isEquals(DXObject room){
    RoomAttach roomAttach = (RoomAttach)room;
    if(_capacity!=roomAttach._capacity)
      return false;
    else
      if(_function!= roomAttach._function)
        return false;
    else
      if(!_description.equals(roomAttach._description))
        return false;
    else
      if (!_roomAvailability.equals(roomAttach._roomAvailability))
        return false;
    else
      if (!_setOfCaracteristics.isEquals(roomAttach._setOfCaracteristics))
        return false;
    return true;
  }

}