package dInternal.dXMLData.rooms;

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
import java.awt.Component;
import dResources.DConst;
import dInternal.dData.*;
import dInternal.dData.RoomsAttributesInterpretor;

public class SetOfRooms extends SetOfResources{

  private byte[] _dataloaded; //_st;// rooms in text format
  private int _numberOfLines;// represent number of days
  private int _numberOfColumns;// represent number of period a day.
  //private RoomsAttributesInterpretor _attr;
  private String _error="";
  private Vector _sorListeners= new Vector(1);
  private RoomsAttributesInterpretor _roomsAttributesInterpretor;

 /***
  * constructor
  * INPUTS: byte[]  dataloaded (information from file in byte type),
  * int nbDay (number of days), int nbPerDay (number of periods a day)
  * */
  public SetOfRooms(byte[] dataloaded, int nbDay, int nbPerDay) {
    super(3);
    _dataloaded = dataloaded;
    _numberOfLines = nbDay;
   _numberOfColumns = nbPerDay;
  }

  /**
   *
   * @param dataloaded
   */
  public void setDataToLoad(byte[]  dataloaded, int nbDay, int nbPerDay){
    _dataloaded = dataloaded;
    _numberOfLines = nbDay;
    _numberOfColumns = nbPerDay;
  }

  /***
   * */
  public boolean analyseTokens(int beginPosition){

    return true;
  }

  public void setAttributesInterpretor(RoomsAttributesInterpretor attr){
    _roomsAttributesInterpretor= attr;
  }

  /**
   *build rooms list.
   *use StringTokenizer st: rooms in text format
   *
   */
  public void buildSetOfResources(int beginPosition){

  }

  /**
   * */
  private boolean isIntValue(String value, String message){
    try{
      (new Integer (value.trim())).intValue();
    }catch (NumberFormatException exc){
      _error = message +DConst.ROOM_TEXT5 +
      "\n" + DConst.ROOM_TEXT6;
      return false;
    }
    return true;
  }

  public String getError() {
    return _error;
  }

}