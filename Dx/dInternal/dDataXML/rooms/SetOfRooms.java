package dInternal.dDataXML.rooms;

/**
 * <p>Title: Diamant 1.5</p>
 * <p>Description: exam timetable construction with Condition Pattern</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author rgr, ysyam, alexander
 * @version 1.0
 */

import dInternal.dDataTxt.SetOfResources;

public class SetOfRooms extends SetOfResources{


  private String _error="";


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


  /**
   *build rooms list.
   *use StringTokenizer st: rooms in text format
   *
   */
  public void buildSetOfResources(int beginPosition){
  	beginPosition+=0;
  }



  public String getError() {
    return _error;
  }

}