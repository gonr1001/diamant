package dTest.dInternal.dData.dRooms;

/**
 * <p>Title: Diamant 1.5</p>
 * <p>Description: exam timetable construction with Condition Pattern</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author rgr, ysyam, alexander
 * @version 1.0
 */

import java.io.File;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import dConstants.DConst;
import dInternal.Preferences;
import dInternal.dData.DLoadData;
import dInternal.dData.dRooms.SetOfCategories;
import dInternal.dData.dRooms.SetOfRooms;
import dInternal.dData.dRooms.SetOfSites;
import eLib.exit.txt.FilterFile;


public class SetOfSitesTest  extends TestCase{

  public SetOfSitesTest(String name) {
    super(name);
  }

  public static Test suite() {
   // the type safe way is in SimpleTest
   // the dynamic way :
   return new TestSuite(SetOfSitesTest.class);
  } // end suite

  /**
   * test_analyseTokens, test that analyse the empty room name
   * in the rooms file
   * */
  public void test_analyseTokens1_5(){
    String tokens= ";32;211;08,11,14,57;laboratoire de chimie;xx;"+"\r\n"+
                   "D13013;40;211;08,11,57;laboratoire de chimie;"+"\r\n"+
                   "D13014;20;211;08,44,57;laboratoire de chimie;"+"\r\n"+
                   "D20051;30;210;8,11,27,44;laboratoire informatique;"+"\r\n"+
                   "D22048;15;211;08,53;laboratoire de physique;"+"\r\n"+
                   "D32028;48;211;08,11,55;laboratoire de microbiologie;"+"\r\n";

    SetOfSites setOfSites = new SetOfSites(); //,5,14);
    DLoadData ld = new DLoadData();
    setOfSites.analyseTokens(ld.buildDataExchange(tokens.getBytes()),3);
    assertEquals("test_analyseTokens: assertEquals", DConst.ROOM_TEXT3,
    		setOfSites.getError().substring(0,DConst.ROOM_TEXT3.length()));

  }
  
  /**
   * test_analyseTokens, test that analyse the empty room name
   * in the rooms file
   * */
  public void test_analyseTokens1_6(){
    String tokens= "Diamant1.6"+"\r\n"+
    				"Faculte des sciences infirmieres;"+"\r\n"+
					"Liste des locaux pour reservations;"+"\r\n"+
					"Nom du local; 	Capacite; 	Fonction; 	Liste des caracteristiques; 	Localite; 	Notes;"+"\r\n"+
    				";32;211;08,11,14,57;laboratoire de chimie;xx;"+"\r\n"+
                   "D13013;40;211;08,11,57;laboratoire de chimie;"+"\r\n"+
                   "D13014;20;211;08,44,57;laboratoire de chimie;"+"\r\n"+
                   "D20051;30;210;8,11,27,44;laboratoire informatique;"+"\r\n"+
                   "D22048;15;211;08,53;laboratoire de physique;"+"\r\n"+
                   "D32028;48;211;08,11,55;laboratoire de microbiologie;"+"\r\n";

    SetOfSites setOfSites = new SetOfSites(); //,5,14);
    DLoadData ld = new DLoadData();
    setOfSites.analyseTokens(ld.buildDataExchange(tokens.getBytes()),0);
    
    assertEquals("test_analyseTokens1_6: assertEquals", DConst.ROOM_TEXT3,
    		setOfSites.getError().substring(0,DConst.ROOM_TEXT3.length()));

  }

  /**
   * test_analyseTokens, test that analyse the capacity of the room
   * in the rooms file
   * */
  public void test1_analyseTokens1_5(){
    String tokens= "D13012;32;211;08,11,14,57;laboratoire de chimie;"+"\r\n"+
                   "D13013;40x;211;08,11,57;laboratoire de chimie;"+"\r\n"+
                   "D13014;20;211;08,44,57;laboratoire de chimie;"+"\r\n"+
                   "D20051;30;210;8,11,27,44;laboratoire informatique;"+"\r\n"+
                   "D22048;15;211;08,53;laboratoire de physique;"+"\r\n"+
                   "D32028;48;211;08,11,55;laboratoire de microbiologie;"+"\r\n";

    SetOfSites setOfSites= new SetOfSites();//,5,14);
    DLoadData ld = new DLoadData();
    setOfSites.analyseTokens(ld.buildDataExchange(tokens.getBytes()),3);
    assertEquals("test1_analyseTokens: assertEquals", DConst.ROOM_TEXT2,
    		setOfSites.getError().substring(0,DConst.ROOM_TEXT2.length()));

  }
  
  /**
   * test1_analyseTokens1_6, test that analyse the capacity of the room
   * in the rooms file
   * */
  public void test1_analyseTokens1_6(){
    String tokens= "Diamant1.6;"+"\r\n"+
					"Faculte des sciences infirmieres;"+"\r\n"+
					"Liste des locaux pour reservations;"+"\r\n"+
					"Nom du local; 	Capacite; 	Fonction; 	Liste des caracteristiques; 	Localite; 	Notes;"+"\r\n"+
    				"D13012;32x;211;08,11,14,57;laboratoire de chimie;"+"\r\n"+
                   "D13013;40;211;08,11,57;laboratoire de chimie;"+"\r\n"+
                   "D13014;20;211;08,44,57;laboratoire de chimie;"+"\r\n"+
                   "D20051;30;210;8,11,27,44;laboratoire informatique;"+"\r\n"+
                   "D22048;15;211;08,53;laboratoire de physique;"+"\r\n"+
                   "D32028;48;211;08,11,55;laboratoire de microbiologie;"+"\r\n";

    SetOfSites setOfSites= new SetOfSites();
    DLoadData ld = new DLoadData();
    setOfSites.analyseTokens(ld.buildDataExchange(tokens.getBytes()),0);
    assertEquals("test1_analyseTokens1_6: assertEquals", DConst.ROOM_TEXT2,
    		setOfSites.getError().substring(0,DConst.ROOM_TEXT2.length()));

  }

  /**
  * test2_analyseTokens, test that analyse the function of the room
  * in the rooms file
  * */
 public void test2_analyseTokens1_5(){
   String tokens= "D13012;32;211x;08,11,14,57;laboratoire de chimie;"+"\r\n"+
                  "D13013;40;211;08,11,57;laboratoire de chimie;"+"\r\n"+
                  "D13014;20;211;08,44,57;laboratoire de chimie;"+"\r\n"+
                  "D20051;30;210;8,11,27,44;laboratoire informatique;"+"\r\n"+
                  "D22048;15;211;08,53;laboratoire de physique;"+"\r\n"+
                  "D32028;48;211;08,11,55;laboratoire de microbiologie;"+"\r\n";

   SetOfSites setOfSites= new SetOfSites();
   DLoadData ld = new DLoadData();
   setOfSites.analyseTokens(ld.buildDataExchange(tokens.getBytes()),3);
   assertEquals("test2_analyseTokens1_5: assertEquals", DConst.ROOM_TEXT3,
                setOfSites.getError().substring(0,DConst.ROOM_TEXT3.length()));

  }
 
 /**
  * test2_analyseTokens1_6, test that analyse the function of the room
  * in the rooms file
  * */
 public void test2_analyseTokens1_6(){
   String tokens= "Diamant1.6"+"\r\n"+
					"Faculte des sciences infirmieres;"+"\r\n"+
					"Liste des locaux pour reservations;"+"\r\n"+
					"Nom du local; 	Capacite; 	Fonction; 	Liste des caracteristiques; 	Localite; 	Notes;"+"\r\n"+
					"D13012;32;211x;08,11,14,57;laboratoire de chimie;"+"\r\n"+
                  "D13013;40;211;08,11,57;laboratoire de chimie;"+"\r\n"+
                  "D13014;20;211;08,44,57;laboratoire de chimie;"+"\r\n"+
                  "D20051;30;210;8,11,27,44;laboratoire informatique;"+"\r\n"+
                  "D22048;15;211;08,53;laboratoire de physique;"+"\r\n"+
                  "D32028;48;211;08,11,55;laboratoire de microbiologie;"+"\r\n";

   SetOfSites setOfSites= new SetOfSites();
   DLoadData ld = new DLoadData();
   setOfSites.analyseTokens(ld.buildDataExchange(tokens.getBytes()),0);
   assertEquals("test2_analyseTokens1_6: assertEquals", DConst.ROOM_TEXT3,
   		setOfSites.getError().substring(0,DConst.ROOM_TEXT3.length()));

  }

  /**
  * test3_analyseTokens, test that analyse the caracteristic of the room
  * in the rooms file
  * */
 public void test3_analyseTokens1_5(){
   String tokens= "D13012;32;211;08,11x,14,57;laboratoire de chimie;"+"\r\n"+
                  "D13013;40;211;08,11,57;laboratoire de chimie;"+"\r\n"+
                  "D13014;20;211;08,44,57;laboratoire de chimie;"+"\r\n"+
                  "D20051;30;210;8,11,27,44;laboratoire informatique;"+"\r\n"+
                  "D22048;15;211;08,53;laboratoire de physique;"+"\r\n"+
                  "D32028;48;211;08,11,55;laboratoire de microbiologie;"+"\r\n";

   SetOfSites setOfSites= new SetOfSites();
   DLoadData ld = new DLoadData();
   setOfSites.analyseTokens(ld.buildDataExchange(tokens.getBytes()),3);
   assertEquals("test3_analyseTokens1_5: assertEquals", DConst.ROOM_TEXT4,
   		setOfSites.getError().substring(0,DConst.ROOM_TEXT4.length()));

  }
 
 /**
  * test3_analyseTokens1_6, test that analyse the caracteristic of the room
  * in the rooms file
  * */
 public void test3_analyseTokens1_6(){
   String tokens= "Diamant1.6"+"\r\n"+
					"Faculte des sciences infirmieres;"+"\r\n"+
					"Liste des locaux pour reservations;"+"\r\n"+
					"Nom du local; 	Capacite; 	Fonction; 	Liste des caracteristiques; 	Localite; 	Notes;"+"\r\n"+
					"D13012;32;211;08,11x,14,57;laboratoire de chimie;"+"\r\n"+
                  "D13013;40;211;08,11,57;laboratoire de chimie;"+"\r\n"+
                  "D13014;20;211;08,44,57;laboratoire de chimie;"+"\r\n"+
                  "D20051;30;210;8,11,27,44;laboratoire informatique;"+"\r\n"+
                  "D22048;15;211;08,53;laboratoire de physique;"+"\r\n"+
                  "D32028;48;211;08,11,55;laboratoire de microbiologie;"+"\r\n";

   SetOfSites setOfSites= new SetOfSites();
   DLoadData ld = new DLoadData();
   setOfSites.analyseTokens(ld.buildDataExchange(tokens.getBytes()),0);
   assertEquals("test3_analyseTokens1_6: assertEquals", DConst.ROOM_TEXT4,
   		setOfSites.getError().substring(0,DConst.ROOM_TEXT4.length()));

  }
 
 public void test4_analyseFile1_5(){
 	String path ="." + File.separator+"dataTest"+File.separator+"locaux.txt";
 	byte[] dataloaded = preLoad (path);
    SetOfSites setOfSites= new SetOfSites();
    DLoadData ld = new DLoadData();
    boolean analyse = setOfSites.analyseTokens(ld.buildDataExchange(dataloaded),0);
 	//boolean analyse = setOfSites.analyseTokens(dataloaded, 0);
 	//boolean analyse =
 	assertEquals("test4_analyseFile1_5: assertEquals", true,analyse);
 }
 
 public void test4_buildSetOfResources1_5(){
 	String path ="." + File.separator+"dataTest"+File.separator+"locaux.txt";
 	byte[] dataloaded = preLoad (path);
 	SetOfSites setOfSites= new SetOfSites();
 	DLoadData ld = new DLoadData();
    setOfSites.analyseTokens(ld.buildDataExchange(dataloaded),0);
 	setOfSites.buildSetOfResources(ld.buildDataExchange(dataloaded), 0);
 	assertEquals("test4_buildSetOfResources1_5-1: assertEquals", "SHE",
 			setOfSites.getResourceAt(0).getID());
 	SetOfCategories soc= (SetOfCategories)setOfSites.getResourceAt(0).getAttach();
 	assertEquals("test4_buildSetOfResources1_5-2: assertEquals", "CAT 1",
 			soc.getResourceAt(0).getID());
 	SetOfRooms sor= (SetOfRooms)soc.getResourceAt(0).getAttach();
 	assertEquals("test4_buildSetOfResources1_5-1: assertEquals", "D13000",
 			sor.getResourceAt(0).getID());
 	assertEquals("test4_buildSetOfResources1_5-2: assertEquals", "D73021",
 			sor.getResourceAt(sor.size()-1).getID());
 }
 
 public void test4_analyseFile1_6(){
 	String path ="." + File.separator+"dataTest"+File.separator+"locauxINFIR.txt";
 	byte[] dataloaded = preLoad (path);
 	SetOfSites setOfSites= new SetOfSites();
    DLoadData ld = new DLoadData();
    boolean analyse = setOfSites.analyseTokens(ld.buildDataExchange(dataloaded),0);
 	assertEquals("test4_analyseFile1_6: assertEquals", true,analyse);
 }
 
 public void test4_buildSetOfResources1_6(){
 	String path ="." + File.separator+"dataTest"+File.separator+"locauxINFIRComplet.txt";
 	byte[] dataloaded = preLoad (path);
 	SetOfSites setOfSites= new SetOfSites();
 	DLoadData ld = new DLoadData();
    setOfSites.analyseTokens(ld.buildDataExchange(dataloaded),0);
 	setOfSites.buildSetOfResources(ld.buildDataExchange(dataloaded), 0);
 	assertEquals("test4_buildSetOfResources1_6-1: assertEquals", "COW",
 			setOfSites.getResourceAt(0).getID());
 	assertEquals("test4_buildSetOfResources1_6-2: assertEquals", "SHE",
 			setOfSites.getResourceAt(setOfSites.size()-1).getID());
 }
 
 public void test5_buildSetOfResources1_6(){
 	String path ="." + File.separator+"dataTest"+File.separator+"locauxINFIRComplet.txt";
 	byte[] dataloaded = preLoad (path);
 	SetOfSites setOfSites= new SetOfSites();
 	DLoadData ld = new DLoadData();
    setOfSites.analyseTokens(ld.buildDataExchange(dataloaded),0);
 	setOfSites.buildSetOfResources(ld.buildDataExchange(dataloaded), 0);
 	SetOfCategories setOfCat= (SetOfCategories) setOfSites.getResourceAt(setOfSites.size()-1).getAttach();
 	assertEquals("test5_buildSetOfResources1_6-1: assertEquals", "CAT1",
 			setOfCat.getResourceAt(0).getID());
 	assertEquals("test5_buildSetOfResources1_6-2: assertEquals", "CAT2",
 			setOfCat.getResourceAt(1).getID());
 }
 
 public void test6_buildSetOfResources1_6(){
 	String path ="." + File.separator+"dataTest"+File.separator+"locauxINFIRComplet.txt";
 	byte[] dataloaded = preLoad (path);
 	SetOfSites setOfSites= new SetOfSites();
 	DLoadData ld = new DLoadData();
    setOfSites.analyseTokens(ld.buildDataExchange(dataloaded),0);
 	setOfSites.buildSetOfResources(ld.buildDataExchange(dataloaded), 0);
 	SetOfCategories setOfCat= (SetOfCategories) setOfSites.getResourceAt(setOfSites.size()-1).getAttach();
 	SetOfRooms setOfRooms= (SetOfRooms)setOfCat.getResourceAt(0).getAttach();
 	assertEquals("test6_buildSetOfResources1_6-1: assertEquals", "Z7-2005",
 			setOfRooms.getResourceAt(setOfRooms.size()-1).getID());
 	setOfRooms= (SetOfRooms)setOfCat.getResourceAt(1).getAttach();
 	assertEquals("test6_buildSetOfResources1_6-2: assertEquals", "Z7-2003",
 			setOfRooms.getResourceAt(setOfRooms.size()-1).getID());
 }

/**
 * 
 * @param str
 * @return
 */	
 private byte[] preLoad(String str) {
		Preferences preferences = new Preferences("."+ File.separator +
	            "pref"
	            + File.separator +
	            "pref.txt");
		FilterFile filter = new FilterFile();
		filter.setCharKnown("");
		filter.appendToCharKnown(preferences._acceptedChars);
		if (filter.validFile(str)) {
			return filter.getByteArray();
		} 
		return null;
	} // preLoad(String str)
 

}