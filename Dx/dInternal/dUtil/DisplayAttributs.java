package dInternal.dUtil;

/**
 * <p>Title: Proto</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author ysyam
 * @version 1.0
 */

import dInternal.dDataTxt.SetOfResources;

public class DisplayAttributs {
  /**
   * the period key referencing this attribut. it is in a.b.c format where
   * a= day key, b= sequence key, c= period key
   */
  private String _periodKey = "";

  /**
   * if true= period is active otherwise period is a hole
   */
  private boolean _periodType;

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
 * 
 */
public DisplayAttributs() {	
	// TODO Auto-generated constructor stub
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
   * set the period type
   * @param periodType if true= period is an active otherwise period is a hole
   */
  public void setPeriodType(boolean periodType){
    _periodType= periodType;
  }

  public boolean getPeriodType(){
    return _periodType;
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