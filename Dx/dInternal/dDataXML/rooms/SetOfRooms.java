package dInternal.dDataXML.rooms;

/**
 * <p>Title: Diamant 1.5</p>
 * <p>Description: exam timetable construction with Condition Pattern</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author rgr, ysyam, alexander
 * @version 1.0
 */
//import java.util.Vector;

//import dConstants.DConst;
//import dInternal.dDataTxt.RoomsAttributesInterpretor;
import dInternal.dDataTxt.SetOfResources;

public class SetOfRooms extends SetOfResources{

  //private byte[] _dataloaded; //_st;// rooms in text format
  //private int _numberOfLines;// represent number of days
  //private int _numberOfColumns;// represent number of period a day.
  //private RoomsAttributesInterpretor _attr;
  private String _error="";
  //private Vector _sorListeners= new Vector(1);
  //private RoomsAttributesInterpretor _roomsAttributesInterpretor;

 /***
  * constructor
  * INPUTS: byte[]  dataloaded (information from file in byte type),
  * int nbDay (number of days), int nbPerDay (number of periods a day)
  * */
  public SetOfRooms(byte[] dataloaded, int nbDay, int nbPerDay) {
    super(3);
    dataloaded[0]+=0;
    nbDay+=0;
   nbPerDay+=0;
  }

  /**
   *
   * @param dataloaded
   */
  public void setDataToLoad(byte[]  dataloaded, int nbDay, int nbPerDay){
  	dataloaded[0]+=0;
    nbDay+=0;
    nbPerDay+=0;
  }

  /***
   * */
  public boolean analyseTokens(int beginPosition){
  	beginPosition+=0;
    return true;
  }

  /*public void setAttributesInterpretor(RoomsAttributesInterpretor attr){
    _roomsAttributesInterpretor= attr;
  }*/

  /**
   *build rooms list.
   *use StringTokenizer st: rooms in text format
   *
   */
  public void buildSetOfResources(int beginPosition){
  	beginPosition+=0;
  }

  /**
   * */
  /*private boolean isIntValue(String value, String message){
    try{
      (new Integer (value.trim())).intValue();
    }catch (NumberFormatException exc){
      _error = message +DConst.ROOM_TEXT5 +
      "\n" + DConst.ROOM_TEXT6;
      return false;
    }
    return true;
  }*/

  public String getError() {
    return _error;
  }

}