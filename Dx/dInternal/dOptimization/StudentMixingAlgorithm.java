package dInternal.dAlgorithms;

/**
 * <p>Title: Proto</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author unascribed
 * @version 1.0
 */

import dInternal.DModel;
import dInternal.dTimeTable.*;
import dInternal.dConditionsTest.*;
import dInternal.dData.*;
import dInternal.dUtil.DXValue;
import dInternal.dUtil.DXToolsMethods;
import java.util.Vector;

public class StudentMixingAlgorithm implements Algorithm {

  private DModel _dm;
  private Vector _eventsRescList;
  private int _mixingType;// 0= balance student mixing, 1= optimize student mixing

  /**
   *
   * @param DModel dm
   * @param int the mixingType 0= balance student mixing, 1= optimize student mixing
   */
  public StudentMixingAlgorithm(DModel dm, int mixingType) {
    _dm= dm;
    _mixingType= mixingType;

  }

  /**
  *
  * @param DModel
  * @param vectorOfEvents
  */
  public void build(){
    _eventsRescList= buildEventsVector();
    //System.out.println("Mixing type: "+_mixingType+" Vector size: "+_eventsRescList.size());// debug
  }

  /**
   * return events list to use by the algorithm. these events is all events place
   * in ttstructure
   * @return
   */
  private Vector buildEventsVector(){
    Vector vect= new Vector(1);
    for (int i=0; i< _dm.getSetOfEvents().size(); i++){
      Resource rescEvent= _dm.getSetOfEvents().getResourceAt(i);
      if(((EventAttach)rescEvent.getAttach()).isPlaceInAPeriod()){
        String actID= DXToolsMethods.getToken(rescEvent.getID(),".",0);
        String typeID= DXToolsMethods.getToken(rescEvent.getID(),".",1);
        Type type= _dm.getSetOfActivities().getType(actID,typeID);
        String str= actID+"."+typeID+".";
        if( (type.getSetOfSections().size()>1) && (!vect.contains(str)) )
          vect.add(str);
      }
    }// end for (int i=0; i< _dm.getSetOfEvents().size();
    return vect;
  }

}// end class