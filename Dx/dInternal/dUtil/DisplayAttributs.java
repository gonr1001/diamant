package dInternal.dUtil;

/**
 * <p>Title: Proto</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author ysyam
 * @version 1.0
 */

import dInternal.dData.SetOfResources;

public class DisplayAttributs {
  /**
   * the period key referencing this attribut. it is in a.b.c format where
   * a= day key, b= sequence key, c= period key
   */
  private String _periodKey;

  /**
   * the hour to display. it is in hh:mm format
   */
   private String _hourToDisplay;

   /**
    * events place in period
    */
   private SetOfResources _eventsInPeriods;

   /**
    * constructor
    * @param periodKey
    * @param hourToDisplay
    * @param eventsInPeriods
    */
   public DisplayAttributs(String periodKey, String hourToDisplay, SetOfResources eventsInPeriods) {
    _periodKey= periodKey;
    _hourToDisplay= hourToDisplay;
    _eventsInPeriods= eventsInPeriods;
  }

  /**
   * constructor
   */
  public DisplayAttributs(){

  }

  /**
    * set atributs
    * @param periodKey
    * @param hourToDisplay
    * @param eventsInPeriods
    */
   public void setAttributs(String periodKey, String hourToDisplay, SetOfResources eventsInPeriods) {
    _periodKey= periodKey;
    _hourToDisplay= hourToDisplay;
    _eventsInPeriods= eventsInPeriods;
  }

  /**
   *
   * @return
   */
  public String getPeriodKey(){
    return _periodKey;
  }

  /**
   *
   * @return
   */
  public String getHourToDisplay(){
    return _hourToDisplay;
  }

  /**
   *
   * @param hourToDisplay
   */
  public void setHourToDisplay(String hourToDisplay){
    _hourToDisplay= hourToDisplay;
  }

  /**
   *
   * @return
   */
  public SetOfResources getEventsInPeriods(){
    return _eventsInPeriods;
  }

}// end class