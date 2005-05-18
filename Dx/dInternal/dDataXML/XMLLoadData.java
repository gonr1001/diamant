package dInternal.dDataXML;

/**
 * <p>Title: Diamant 1.5</p>
 * <p>Description: exam timetable construction with Condition Pattern</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author ysyam
 * @version 1.0
 */

import java.io.File;

import java.util.Vector;

import org.w3c.dom.Element;





import dConstants.DConst;
import dInternal.DModel;
import dInternal.Preferences;
import dInternal.dDataTxt.SetOfActivities;

import dInternal.dDataXML.rooms.SetOfCategories;
import dInternal.dUtil.XMLTools;
import eLib.exit.txt.FilterFile;
import eLib.exit.xml.input.XMLReader;
//import eLib.exit.xml.input.ReadXMLFile;

public class XMLLoadData {
/*  //XML tags
  static final String _ROOMTAGITEM1="importfiles";
  static final String _ROOMTAGITEM1_1="instructors";
  static final String _ROOMTAGITEM1_2="rooms";
  static final String _ROOMTAGITEM1_3="activities";
  static final String _ROOMTAGITEM1_4="students";

  private Element _importElement;//element containing all import files
  private String _instructorFileName;
  private String _roomsFileName;
  private String _activitiesFileName;
  private String _studentsFileName;

  private String _chars;


  public XMLLoadData() {
    initLoadData();

  }


  public XMLLoadData(String importfile, DModel dm) {
    
  	dm.getVersion();
    _chars+="";
    _instructorFileName+="";
    _roomsFileName="";
    _activitiesFileName+="";
    _studentsFileName+="";
    
    FilterFile filter = new FilterFile();
    if (filter.validFile(importfile)) {
      loadImportFile(importfile);
    }
  }


  private void initLoadData() {
    Preferences preferences = new Preferences(System.getProperty("user.dir")
        + File.separator +
        "pref"
        + File.separator +
        "pref.txt");
    _chars = preferences._acceptedChars;
  }



  public SetOfCategories extractRooms(SetOfCategories currentList, boolean merge){
  	currentList= new SetOfCategories(_roomsFileName,5,14);// todo
  	currentList.getError();
  	merge= merge && true;
    SetOfCategories roomsList = new SetOfCategories(_roomsFileName,5,14);
    roomsList.analyseTokens(0);
     return roomsList;
  }


/*  public SetOfStudents extractStudents(SetOfStudents currentList, boolean merge){
  	currentList.hashCode();
  	merge= merge && true;
    SetOfStudents studentsList = new SetOfStudents((new String("1")).getBytes());
    return studentsList;
  }


  public SetOfActivities extractActivities(SetOfActivities currentList, boolean merge){
  	currentList.hashCode();
  	merge= merge && true;
  	SetOfActivities activitiesList = new SetOfActivities((new String("1")).getBytes(),false);
    return activitiesList;
  }


  public Vector loadProject(String fileName){
  	fileName+="";
    Vector extract= new Vector();
    return extract;
  }

  private String loadImportFile(String fileName){
    //ReadXMLFile xmlFile;
    //Element  item, ID;
    int numberfoElements=0;
    String error="";//DConst.ERROR_XML_FILE;
    _importElement = XMLTools.getRootDocument(fileName);
     ReadXMLElement list= new ReadXMLElement();
      numberfoElements=list.getSize(_importElement,_ROOMTAGITEM1_1)+list.getSize(_importElement,_ROOMTAGITEM1_2)
                       +list.getSize(_importElement,_ROOMTAGITEM1_3)+list.getSize(_importElement,_ROOMTAGITEM1_4);

    if((_importElement!=null) && (numberfoElements==4)){
        _instructorFileName= list.getElementValue(_importElement,_ROOMTAGITEM1_1);
        _roomsFileName= list.getElementValue(_importElement,_ROOMTAGITEM1_2);
        _activitiesFileName= list.getElementValue(_importElement,_ROOMTAGITEM1_3);
        _studentsFileName= list.getElementValue(_importElement,_ROOMTAGITEM1_4);
    } else{
      error=DConst.ERROR_XML_FILE;
    }// end else if(_importElement!=null)

    return error;
  }

*/
}