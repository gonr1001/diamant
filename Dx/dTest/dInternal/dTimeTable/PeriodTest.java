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
import java.io.File;
import java.util.Vector;

import dInternal.dTimeTable.Period;
import dInternal.dConditionsTest.ConflictsAttach;
import dInternal.dData.Resource;
import dInternal.dData.SetOfResources;

import xml.InPut.readFile;
import xml.InPut.ReadXMLElement;
import xml.OutPut.*;

import org.w3c.dom.Element;
import org.w3c.dom.Document;


public class PeriodTest extends TestCase {
String path;
  public PeriodTest(String name) {
    super(name);
    path =System.getProperty("user.dir")+ File.separator+"data"+File.separator+"TTxmlFiles"+File.separator;
  }

  public static Test suite() {
   // the type safe way is in SimpleTest
   // the dynamic way :
   return new TestSuite(PeriodTest.class);
  } // end suite



  /**
   * test that read the period xml tag
   * */
  public void test_readXMLtag(){
    readFile xmlFile;
    Element  item, ID;
    Period period= new Period();
    try{
      xmlFile = new readFile();
      //System.out.println(path+"period.xml");//debug
      Document  doc = xmlFile.getDocumentFile(path+"period.xml");
      ReadXMLElement list= new ReadXMLElement();
      item= list.getRootElement(doc);
      period.readXMLtag(item);
      //_setOfCycles.readXMLtag(root);
    }catch(Exception e){
      System.out.println(e);
    }
    assertEquals("test_readXMLtag : assertEquals 1(Hour):", period.getBeginHour()[0], 8);
    assertEquals("test_readXMLtag : assertEquals 2(Minute):", period.getBeginHour()[1], 15);
    assertEquals("test_readXMLtag : assertEquals 3(priotity):", period.getPriority(), 0);
  }

  /**
   *
   */
  public void test_eventsInPeriod(){
    readFile xmlFile;
    Element  item, ID;
    Period period= new Period();
    try{
      xmlFile = new readFile();
      //System.out.println(path+"period.xml");//debug
      Document  doc = xmlFile.getDocumentFile(path+"period.xml");
      ReadXMLElement list= new ReadXMLElement();
      item= list.getRootElement(doc);
      period.readXMLtag(item);
      //_setOfCycles.readXMLtag(root);
    }catch(Exception e){
      System.out.println(e);
    }
    ConflictsAttach confAttach= new ConflictsAttach();
     Vector vec= new Vector();
     vec.add("YS,RGR,AJ");
     confAttach.addConflict("AMC640.1.02.1",3,"student",vec);
     period.getEventsInPeriod().addResource(new Resource("GEI200.1.01.1",confAttach),0);
     vec= new Vector();
     vec.add("Alex");
     confAttach= new ConflictsAttach();
     confAttach.addConflict("AMC645.1.01.1",1,"Instructor",vec);
     period.getEventsInPeriod().addResource(new Resource("ADM111.1.01.1",confAttach),0);
     SetOfResources sor= period.getConflictsEventsInPeriod("GEI200.1.01.1");
     assertEquals("test_eventsInPeriod : assertEquals :", 2, sor.size());
  }


  /**
   * test that gives the end hour
   * */
  public void test_getEndHour(){
    readFile xmlFile;
    Element  item, ID;
    Period period= new Period();
    try{
      xmlFile = new readFile();
      //System.out.println(path+"period.xml");//debug
      Document  doc = xmlFile.getDocumentFile(path+"period.xml");
      ReadXMLElement list= new ReadXMLElement();
      item= list.getRootElement(doc);
      period.readXMLtag(item);
      //_setOfCycles.readXMLtag(root);
    }catch(Exception e){
      System.out.println(e);
    }
    assertEquals("test_getEndHour : assertEquals (endHour):", period.getEndHour(60)[0], 9);
    assertEquals("test_getEndHour : assertEquals (endMinute):", period.getEndHour(60)[1], 15);
  }


  /**
   * test that gives the end hour
   * */
  public void test_writeXMLtag(){
    readFile xmlFile;
    Element  item, ID;
    Period period= new Period();
    Period periodS= new Period();
    try{
      xmlFile = new readFile();
      //System.out.println(path+"period.xml");//debug
      Document  doc;// = xmlFile.getDocumentFile(path+"period.xml");
      /*ReadXMLElement list= new ReadXMLElement();
      item= list.getRootElement(doc);
      period.readXMLtag(item);*/
      //write xml file
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


}