package dInternal.dXMLData;

/**
 * <p>Title: Diamant 1.5</p>
 * <p>Description: exam timetable construction with Condition Pattern</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author  ysyam
 * @version 1.0
 */
import java.util.StringTokenizer;
import java.util.Vector;
import java.io.File;
import java.awt.Component;
import dResources.DConst;

import xml.OutPut.BuildXMLElement;
import xml.OutPut.writeFile;
import xml.InPut.readFile;
import xml.InPut.ReadXMLElement;
import dResources.DConst;
import org.w3c.dom.*;
import dInternal.dData.SetOfResources;
import dInternal.dData.RoomsAttributesInterpretor;
import dInternal.dData.SetOfRoomsListener;
import dInternal.dUtil.XMLTools;

public class SetOfRoomsCategories extends SetOfResources{

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
  private int _numberOfLines;// represent number of days
  private int _numberOfColumns;// represent number of period a day.
  private String _error="";
  private Vector _sorListeners= new Vector(1);
  private RoomsAttributesInterpretor _roomsAttributesInterpretor;

 /***
  * constructor
  * INPUTS: byte[]  dataloaded (information from file in byte type),
  * int nbDay (number of days), int nbPerDay (number of periods a day)
  * */
  public SetOfRoomsCategories(String fileName, int nbDay, int nbPerDay) {
    super(3);
    _numberOfLines = nbDay;
    _numberOfColumns = nbPerDay;
    fileName =System.getProperty("user.dir")+ File.separator+"devData"+File.separator+"NewRooms.xml";// to remove
    _error=loadCategoryStructure(fileName);
  }

  /**
   *
   * @param dataloaded
   */
  public void setDataToLoad(String fileName, int nbDay, int nbPerDay){
    _error=loadCategoryStructure(fileName);
    _numberOfLines = nbDay;
    _numberOfColumns = nbPerDay;
  }

  /***
   * */
  public boolean analyseTokens(int beginPosition){
    ReadXMLElement list= new ReadXMLElement();
   String ID="";
   int size= list.getSize(_setofcat,_ROOMTAGITEM1);
   if (size == 0){
     _error = DConst.ERROR_XML_FILE;
     return false;
   }
  for (int i=0; i< size; i++){
    SetOfResources setOfRooms = new SetOfResources(66);
    Element category= list.getElement(_setofcat,_ROOMTAGITEM1,i);
    ID= list.getElementValue(category,_ROOMTAGITEM1_1);
    int numberOfRooms= list.getSize(category,_ROOMTAGITEM1_2);
    //_periodLenght= Integer.parseInt(list.getElementValue(cycle,_TAGITEM2));
    System.out.println(" Category ID: "+ID+" number of rooms: "+numberOfRooms);//debug
    //_setOfCycles.addResource(new Resource(ID,setOfdays),0);

   }// end for (int i=0; i< size; i++)
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
   SetOfRoomsCategoriesEvent event = new SetOfRoomsCategoriesEvent(this);
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
   * it load the xml category file
   * @param String the xml file containing the xml category structure
   * @return String the error message, empty if it does not found error
   * */
  private String loadCategoryStructure(String fileName){
    readFile xmlFile;
    Element  item, ID;
    String error="";
    _setofcat= XMLTools.getRootDocument(fileName);
    return error;
  }


}