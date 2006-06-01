///*package dInternal.dData.dRooms;
//
//*//**
// * <p>Title: Diamant 1.5</p>
// * <p>Description: exam timetable construction with Condition Pattern</p>
// * <p>Copyright: Copyright (c) 2002</p>
// * <p>Company: UdeS</p>
// * @author rgr, ysyam, alexander
// * @version 1.0
// *//*
//
//import java.util.StringTokenizer;
//
//import dConstants.DConst;
//import dInternal.DResource;
//import dInternal.DSetOfResources;
//import dInternal.DValue;
////import dInternal.dUtil.DXValue;
//
//public class RoomsAttributesInterpretor {
//
//  *//** set of functions. It contains a set of resource where
//   * _resourceKey represents the reference number of the function and
//   * _resourceId represents function description*//*
//  private DSetOfResources _setOfFunctions;
//  *//** set of caracteristics. It contains a set of resource where
//   * _resourceKey represents the reference number of the caracteristic and
//   * _resourceId represents caracteristic description*//*
//  private DSetOfResources _setOfCaracteristics;
//
//  *//**
//   * Constructor
//   * *//*
//  public RoomsAttributesInterpretor() {
//    _setOfFunctions = new SetOfCaracteristics();
//    _setOfCaracteristics = new SetOfCaracteristics();
//  }
//
//  *//**
//   * this method add in the set of functions a function by
//   * using its reference number and its function description
//   * @param int the reference number
//   * @param String the function description
//   * @return boolean the result of operation
//   * *//*
//  public boolean addFunction(int refNo, String function){
//    _setOfFunctions.setCurrentKey(refNo);
//    return _setOfFunctions.addResource(new DResource(function, new DValue()),0); // XXXX Pascal: magic number
//  }
//
//  *//**
//   * this method add in the set of caracteristics a caracteristic by
//   * using its reference number and its caracteristic description
//   * @param int the reference number
//   * @param String the caracteristic description
//   * @return boolean the result of operation
//   * *//*
//  public boolean addCaracteristic(int refNo, String caracteristic){
//    _setOfCaracteristics.setCurrentKey(refNo);
//    return _setOfCaracteristics.addResource(new DResource(caracteristic, new DValue()),0);
//  }
//
//  *//**
//   * @param SetOfResources the set of functions
//   * *//*
//  public void setSetOfFunctions(DSetOfResources function){
//    _setOfFunctions= function;
//  }
//
//  *//**
//   * @param SetOfResources the set of caracteristics
//   * *//*
//  public void setSetOfCaracteristics(DSetOfResources caracteristics){
//    _setOfCaracteristics= caracteristics;
//  }
//
//  *//**
//   * *//*
//  public DSetOfResources getSetOfFunctions(){
//    return _setOfFunctions;
//  }
//
//  *//**
//   * *//*
//  public DSetOfResources getSetOfCaracteristics(){
//    return _setOfCaracteristics;
//  }
//
//  *//**
//   * @todo implement method
//   * @param byte[] the file contains of the function description
//   * *//*
//  public boolean loadSetOfFunctions(byte[] dataloaded){
//    String token;
//    StringTokenizer st = new StringTokenizer(new String (dataloaded),DConst.CR_LF );
//    //int state=0;
//    while (st.hasMoreElements()){
//      //state =0;
//      token = st.nextToken();
//      StringTokenizer currentLine = new StringTokenizer(token,";" );
//      if (currentLine.countTokens()==2){ // XXXX Pascal: magic numbers
//       addFunction(Integer.parseInt(currentLine.nextToken()),
//                        currentLine.nextToken());
//      }else
//        return false;
//    }// end while (st.hasMoreElements()
//    return true;
//  }
//
//  *//**
//   * @todo implement method
//   * @param byte[] the file contains of the caracteristic description
//   * *//*
//  public boolean loadSetOfCaracteristics(byte[] dataloaded){
//    String token;
//    StringTokenizer st = new StringTokenizer(new String (dataloaded),DConst.CR_LF );
//    //int state=0;
//    while (st.hasMoreElements()){
//     //state =0;
//      token = st.nextToken();
//      StringTokenizer currentLine = new StringTokenizer(token,";" );
//      if (currentLine.countTokens()==2){
//       addCaracteristic(Integer.parseInt(currentLine.nextToken()),
//                        currentLine.nextToken());
//      }else
//        return false;
//    }// end while (st.hasMoreElements()
//    return true;
//  }
//
//}*/