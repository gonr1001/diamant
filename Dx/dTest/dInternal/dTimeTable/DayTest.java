package dTest.dInternal.dTimeTable;

/**
 * <p>Title: Diamant</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author  ysyam
 * @version 1.0
 */

import junit.framework.*;
import dInternal.dData.Activity;
import java.util.Vector;
import java.io.File;

import dInternal.dTimeTable.Day;

import xml.InPut.readFile;
import xml.InPut.ReadXMLElement;
import xml.OutPut.*;

import org.w3c.dom.Element;
import org.w3c.dom.Document;


public class DayTest extends TestCase {
String path;
  public DayTest(String name) {
    super(name);
    path =System.getProperty("user.dir")+ File.separator+"data"+File.separator+"TTxmlFiles"+File.separator;
  }

  public static Test suite() {
   // the type safe way is in SimpleTest
   // the dynamic way :
   return new TestSuite(DayTest.class);
  } // end suite


  /**
   * test to read the day xml tag
   * */
  public void test_readXMLtag(){
    readFile xmlFile;
    Element  eDay;
    Day day= new Day();
    try{
      xmlFile = new readFile();
      //System.out.println(path+"period.xml");//debug
      Document  doc = xmlFile.getDocumentFile(path+"day.xml");
      ReadXMLElement list= new ReadXMLElement();
      eDay = list.getRootElement(doc);
      day.readXMLtag(eDay);
    }catch(Exception e){
      System.out.println(e);
    }
    assertEquals("test_readXMLtag : assertEquals 1(Size of the SetOfSequences ):", 2, day.getSetOfSequences().size());
    //assertEquals("test_readXMLtag : assertEquals 2(Minute):", period.getBeginHour()[1], 15);
    //assertEquals("test_readXMLtag : assertEquals 3(priotity):", period.getPriority(), 0);
  }


  /**
   * test that gives the end hour
   * */

  /**
   * test that gives the end hour
   * */

  /*
  public void test_writeXMLtag(){
    readFile xmlFile;
    Element  item, ID;
    Period period= new Period();
    Period periodS= new Period();
    try{
      xmlFile = new readFile();
      //System.out.println(path+"period.xml");//debug
      Document  doc;// = xmlFile.getDocumentFile(path+"period.xml");

    */
      /*ReadXMLElement list= new ReadXMLElement();
      item= list.getRootElement(doc);
      period.readXMLtag(item);*/
      //write xml file

  /*
  period.setBeginHour(9,30);
      period.setPriority(2);
      BuildXMLElement wr= new BuildXMLElement();
      doc=wr.getNewDocument();
       Element ttPeriod= period.writeXMLtag(doc);
      doc= wr.buildDOM(doc,ttPeriod);
      writeFile.write(doc,path+"SavePeriod.xml");

      // read xml file
      doc = xmlFile.getDocumentFile(path+"SavePeriod.xml");
      ReadXMLElement list= new ReadXMLElement();
      item= list.getRootElement(doc);
      periodS= new Period();
      periodS.readXMLtag(item);
      //Element ttStruc= _setOfCycles.writeXMLtag(doc);
      // create document and write in the file

      //_setOfCycles.readXMLtag(root);
    }catch(Exception e){
      System.out.println(e);
    }
    assertEquals("test_writeXMLtag : assertEquals (beginHour):", periodS.getBeginHour()[0], period.getBeginHour()[0]);
    assertEquals("test_writeXMLtag : assertEquals (beginMinute):", periodS.getBeginHour()[1], period.getBeginHour()[1]);
    assertEquals("test_writeXMLtag : assertEquals (priority):", periodS.getPriority(), period.getPriority());
  }

*/
}