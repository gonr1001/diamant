package dInternal.dData;

/**
 * <p>Title: miniDia</p>
 * <p>Description: exam timetable construction with Condition Pattern</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author rgr, ysyam, alexander
 * @version 1.0
 */

import dInternal.dData.SetOfResources;
import dInternal.dUtil.DXValue;
import java.util.StringTokenizer;

public class RoomsAttributesInterpretor {

  /** set of functions. It contains a set of resource where
   * _resourceKey represents the reference number of the function and
   * _resourceId represents function description*/
  private SetOfResources _setOfFunctions;
  /** set of caracteristics. It contains a set of resource where
   * _resourceKey represents the reference number of the caracteristic and
   * _resourceId represents caracteristic description*/
  private SetOfResources _setOfCaracteristics;

  /**
   * Constructor
   * */
  public RoomsAttributesInterpretor() {
    _setOfFunctions = new SetOfResources(3);
    _setOfCaracteristics = new SetOfResources(3);
  }

  /**
   * this method add in the set of functions a function by
   * using its reference number and its function description
   * @param int the reference number
   * @param String the function description
   * @return boolean the result of operation
   * */
  public boolean addFunction(int refNo, String function){
    _setOfFunctions.setCurrentKey(refNo);
    return _setOfFunctions.addResource(new Resource(function, new DXValue()),0);
  }

  /**
   * this method add in the set of caracteristics a caracteristic by
   * using its reference number and its caracteristic description
   * @param int the reference number
   * @param String the caracteristic description
   * @return boolean the result of operation
   * */
  public boolean addCaracteristic(int refNo, String caracteristic){
    _setOfCaracteristics.setCurrentKey(refNo);
    return _setOfCaracteristics.addResource(new Resource(caracteristic, new DXValue()),0);
  }

  /**
   * @param SetOfResources the set of functions
   * */
  public void setSetOfFunctions(SetOfResources function){
    _setOfFunctions= function;
  }

  /**
   * @param SetOfResources the set of caracteristics
   * */
  public void setSetOfCaracteristics(SetOfResources caracteristics){
    _setOfCaracteristics= caracteristics;
  }

  /**
   * */
  public SetOfResources getSetOfFunctions(){
    return _setOfFunctions;
  }

  /**
   * */
  public SetOfResources getSetOfCaracteristics(){
    return _setOfCaracteristics;
  }

  /**
   * @todo implement method
   * @param byte[] the file contains of the function description
   * */
  public boolean loadSetOfFunctions(byte[] dataloaded){
    String token;
    StringTokenizer st = new StringTokenizer(new String (dataloaded),"\r\n" );
    int state=0;
    while (st.hasMoreElements()){
      state =0;
      token = st.nextToken();
      StringTokenizer currentLine = new StringTokenizer(token,";" );
      if (currentLine.countTokens()==2){
       addFunction(Integer.parseInt(currentLine.nextToken()),
                        currentLine.nextToken());
      }else
        return false;
    }// end while (st.hasMoreElements()
    return true;
  }

  /**
   * @todo implement method
   * @param byte[] the file contains of the caracteristic description
   * */
  public boolean loadSetOfCaracteristics(byte[] dataloaded){
    String token;
    StringTokenizer st = new StringTokenizer(new String (dataloaded),"\r\n" );
    int state=0;
    while (st.hasMoreElements()){
      state =0;
      token = st.nextToken();
      StringTokenizer currentLine = new StringTokenizer(token,";" );
      if (currentLine.countTokens()==2){
       addCaracteristic(Integer.parseInt(currentLine.nextToken()),
                        currentLine.nextToken());
      }else
        return false;
    }// end while (st.hasMoreElements()
    return true;
  }

}