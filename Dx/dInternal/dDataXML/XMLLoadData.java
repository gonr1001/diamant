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
import dInternal.dDataTxt.SetOfInstructors;
import dInternal.dDataTxt.SetOfStudents;
import dInternal.dDataXML.rooms.SetOfCategories;
import dInternal.dUtil.XMLTools;
import eLib.exit.txt.FilterFile;
import eLib.exit.xml.input.ReadXMLElement;
import eLib.exit.xml.input.ReadXMLFile;

public class XMLLoadData {
  //XML tags
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
  private DModel _dm;
  private String _error="";
  private final int NUMBER_OF_TOKENS = 4;
  private final String CR_LF = "\r\n";
  private boolean _load = true;
  private String _chars;

  /***
   *constructor
   */
  public XMLLoadData() {
    initLoadData();

  }

  /**
   *
   * @param importfile
   * @param dm
   */
  public XMLLoadData(String importfile, DModel dm) {
    _dm = dm;
    FilterFile filter = new FilterFile();
    if (filter.validFile(importfile)) {
      loadImportFile(importfile);
    }
  }


  /**
   *
   */
  private void initLoadData() {
    _dm = null;
    String path =System.getProperty("user.dir")+ File.separator+"pref"+File.separator;
    Preferences preferences = new Preferences(System.getProperty("user.dir")
        + File.separator +
        "pref"
        + File.separator +
        "pref.txt");
    _chars = preferences._acceptedChars;
  }



  /**
   *
   * @param currentList
   * @param merge
   * @return
   */
  public SetOfInstructors extractInstructors(SetOfInstructors currentList, boolean merge){
    SetOfInstructors instructorsList= new SetOfInstructors((new String("1")).getBytes(),5,14);
    return instructorsList;
  }

  /**
   *
   * @param currentList
   * @param merge
   * @return
   */
  public SetOfCategories extractRooms(SetOfCategories currentList, boolean merge){
    SetOfCategories roomsList = new SetOfCategories(_roomsFileName,5,14);
    roomsList.analyseTokens(0);
     return roomsList;
  }

  /**
   *
   * @param currentList
   * @param merge
   * @return
   */
  public SetOfStudents extractStudents(SetOfStudents currentList, boolean merge){
    SetOfStudents studentsList = new SetOfStudents((new String("1")).getBytes());
    return studentsList;
  }

  /**
   *
   * @param currentList
   * @param merge
   * @return
   */
  public SetOfActivities extractActivities(SetOfActivities currentList, boolean merge){
    SetOfActivities activitiesList = new SetOfActivities((new String("1")).getBytes(),false);
    return activitiesList;
  }


  /**
   *
   * @param fileName
   * @return
   */
  public Vector loadProject(String fileName){
    Vector extract= new Vector();
    return extract;
  }

  /**
   * it load the xml import file
   * @param String the xml file containing the xml category structure
   * @return String the error message, empty if it does not found error
   * */
  private String loadImportFile(String fileName){
    ReadXMLFile xmlFile;
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


}