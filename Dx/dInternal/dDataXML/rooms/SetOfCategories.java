package dInternal.dDataXML.rooms;

/**
 * <p>Title: Diamant 1.5</p>
 * <p>Description: exam timetable construction with Condition Pattern</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author  ysyam
 * @version 1.0
 */

import java.awt.Component;
import java.util.Vector;

import org.w3c.dom.Element;


import dInternal.dDataTxt.Resource;
//import dInternal.dDataTxt.RoomsAttributesInterpretor;
import dInternal.dDataTxt.SetOfResources;
import dInternal.dDataTxt.SetOfRoomsListener;
import dInternal.dUtil.XMLTools;
import eLib.exit.xml.input.ReadXMLElement;

public class SetOfCategories extends SetOfResources{

  //XML tags
  static final String _ROOMTAGITEM="SetofCategories";
  static final String _ROOMTAGITEM1="category";
  static final String _ROOMTAGITEM1_1="categoryID";
  static final String _ROOMTAGITEM1_2="setofrooms";
  static final String _ROOMTAGITEM2="room";
  static final String _ROOMTAGITEM2_1="name";
  static final String _ROOMTAGITEM2_2="capacity";
  static final String _ROOMTAGITEM2_3="caracteristics";
  static final String _ROOMTAGITEM2_3_1="caracteristic";
  static final String _ROOMTAGITEM2_4="avalaibility";
  static final String _ROOMTAGITEM2_4_1="day";

  //private byte[] _dataloaded; //_st;// rooms in text format to remove
  private Element _setofcat;// XML set of category element
  //private int _numberOfLines;// represent number of days
  //private int _numberOfColumns;// represent number of period a day.
  private String _error="";
  private Vector _sorListeners= new Vector(1);
  //private RoomsAttributesInterpretor _roomsAttributesInterpretor;

 /***
  * constructor
  * INPUTS: byte[]  dataloaded (information from file in byte type),
  * int nbDay (number of days), int nbPerDay (number of periods a day)
  * */
  public SetOfCategories(String fileName, int nbDay, int nbPerDay) {
    super(3);
    //_numberOfLines = nbDay;
    nbDay+=0;
    //_numberOfColumns = nbPerDay;
    nbPerDay+=0;
    _error=readCategoryTag(fileName);
  }

  /**
   *
   * @param dataloaded
   */
  public void setDataToLoad(String fileName, int nbDay, int nbPerDay){
    _error=readCategoryTag(fileName);
    nbDay+=0;
    nbPerDay+=0;
  }

  /***
   * */
  public boolean analyseTokens(){

    return true;
  }

  /*public void setAttributesInterpretor(RoomsAttributesInterpretor attr){
    //_roomsAttributesInterpretor= attr;
  }*/

  /**
   *build rooms list.
   *use StringTokenizer st: rooms in text format
   *
   */
  public void buildSetOfResources(){

  }


  /**
   *
   * @return
   */
  public String getError() {
    return _error;
  }

  /**
   *
   * @param component
   */
 public void sendEvent(Component component) {
   //SetOfCategoriesEvent event = new SetOfCategoriesEvent(this);
   component.getSize();
   /*for (int i=0; i< _sorListeners.size(); i++) {
     SetOfRoomsListener sorl = (SetOfRoomsListener) _sorListeners.elementAt(i);
     sorl.changeInSetOfRooms(event, component);
   }*/
  }

  /**
   *
   * @param dml
   */
  public synchronized void addSetOfRoomsListener(SetOfRoomsListener sorl) {
    //System.out.println("SetOfActivities listener addeed: ");//debug
    if (_sorListeners.contains(sorl)){
      return;
    }
    _sorListeners.addElement(sorl);
    //System.out.println("addSetOfRooms Listener ...");//debug
  }


  /**
   * Read categories in the xml file
   * @return
   */
  private String readCategoryTag(String fileName){
    _setofcat= XMLTools.getRootDocument(fileName);
    ReadXMLElement list= new ReadXMLElement();
    String ID="";
    String error= XMLTools.tagError(_setofcat,_ROOMTAGITEM1);
    if(error.length()==0){
      for (int i=0; i< XMLTools.tagSize(_setofcat,_ROOMTAGITEM1); i++){
        Element category= list.getElement(_setofcat,_ROOMTAGITEM1,i);
        ID= list.getElementValue(category,_ROOMTAGITEM1_1);
        //Element setofrooms = list.getElement(category,_ROOMTAGITEM1_2,1);
        //System.out.println(" Category ID: "+ID);//debug
        error= XMLTools.tagError(category,_ROOMTAGITEM1_2);
        if(error.length()==0){
            Element setofrooms= list.getElement(category,_ROOMTAGITEM1_2,0);
            //read rooms
            readRoomsTag(setofrooms);// read rooms
        }// end if(error.length()==0)
        addResource(new Resource(ID,null),0);
      }// end for (int i=0; i< size; i++)
    }// end if(error.length()==0)
    return error;
  }

  /**
   * read rooms of a category
   * @param setofrooms
   * @return
   */
  private String readRoomsTag(Element setofrooms){
    //ReadXMLElement list= new ReadXMLElement();
   //String name="", capacity="";
   String error= XMLTools.tagError(setofrooms,_ROOMTAGITEM2);
   if(error.length()==0){
     for (int i=0; i< XMLTools.tagSize(setofrooms,_ROOMTAGITEM2); i++){
       //Element room= list.getElement(setofrooms,_ROOMTAGITEM2,i);
       //name= list.getElementValue(room,_ROOMTAGITEM2_1);
       //capacity= list.getElementValue(room,_ROOMTAGITEM2_2);
       //Element caracteristics= list.getElement(room,_ROOMTAGITEM2_3,i);
       //Element availabilities= list.getElement(room,_ROOMTAGITEM2_4,i);
       
       //System.out.println(" Room name: "+name+ " Capacity: "+ capacity);//debug
       //_setOfCycles.addResource(new Resource(ID,setOfdays),0);
     }// end for (int i=0; i< size; i++)
   }// end if(error.length()==0)
    return error;
  }


}