/**
 * 
 * Title: SetOfStuSitesTest $Revision: 1.4 $ $Date: 2005-03-08 16:00:45 $
 * Description: SetOfStuSitesTest is a class used as a test container. It test
 * students and their attributes.
 * 
 * 
 * Copyright (c) 2001 by rgr. All rights reserved.
 * 
 * 
 * This software is the confidential and proprietary information of rgr.
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the license
 * agreement you entered into with rgr.
 * 
 * @version $Revision: 1.4 $
 * @author $Author: syay1801 $
 * @since JDK1.3
 */

package dTest.dInternal.dData.dStudents;

import java.util.Vector;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import dConstants.DConst;
import dInternal.dData.DLoadData;
import dInternal.dData.dStudents.SetOfStuSites;
import dInternal.dData.dStudents.SetOfStudents;

public class SetOfStuSitesTest extends TestCase {

	public SetOfStuSitesTest(String name) {
		super(name);
	}

	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(SetOfStuSitesTest.class);
	} // end suite

	/**
	 * test_analyseTokens, test that analyse the first line (the number of
	 * students) of students file
	 */
	public void test_analyseTokens1_5() {
		String tokens = "    0015s" + "\r\n"
				+ "009008132035030720003LUPIEN MY05" + "\r\n"
				+ "CTB301101 GIS251102 GIS3511 GRH111101 GRH332101" + "\r\n"
				+ "009011991290000520021AUDET FRE05" + "\r\n"
				+ "CTB3411 FEC111102 FEC4441 GIS114101 MAR2211" + "\r\n"
				+ "009027042035010720003VEILLEUX 04" + "\r\n"
				+ "CTB443101 CTB451102 CTB513102 CTB563101" + "\r\n";

		SetOfStuSites setOfStuSites = new SetOfStuSites(); //,5,14);
		DLoadData ld = new DLoadData();
		setOfStuSites.analyseTokens(ld.buildDataExchange(tokens.getBytes()), 0);

		assertEquals("test_analyseTokens1_5: assertEquals", DConst.STUD_TEXT6,
				setOfStuSites.getError().substring(0,
						DConst.STUD_TEXT6.length()));
	}

	/**
	 * test1_analyseTokens, test that analyse the empty student name in the
	 * student file
	 */
	public void test1_analyseTokens1_5() {
		String tokens = "    0015" + "\r\n" + "00900813203503072000305"
				+ "\r\n" + "CTB301101 GIS251102 GIS3511 GRH111101 GRH332101"
				+ "\r\n" + "009011991290000520021AUDET FRE05" + "\r\n"
				+ "CTB3411 FEC111102 FEC4441 GIS114101 MAR2211" + "\r\n"
				+ "009027042035010720003VEILLEUX 04" + "\r\n"
				+ "CTB443101 CTB451102 CTB513102 CTB563101" + "\r\n";
		SetOfStuSites setOfStuSites = new SetOfStuSites(); //,5,14);
		DLoadData ld = new DLoadData();
		setOfStuSites.analyseTokens(ld.buildDataExchange(tokens.getBytes()), 0);

		assertEquals("test1_analyseTokens1_5: assertEquals", DConst.STUD_TEXT2,
				setOfStuSites.getError().substring(0,
						DConst.STUD_TEXT2.length()));
	}

	/**
	 * test1_analyseTokens, test that analyse the empty student name or empty
	 * matricule or mistakes line description in the student file
	 */
	public void test1_analyseTokens1_6() {
		String tokens = "Diamant1.6" + "\r\n"
				+ "E 009391402270000320033 Pinard, P" + "\r\n"
				+ "C FII221100 LON" + "\r\n" + "C SOI146100 LON" + "\r\n"
				+ "C SOI247100 LON" + "\r\n" + "C SOI250100 LON" + "\r\n"
				+ "C SOI320100 LON" + "\r\n"
				+ "E 011081522270010320011 Décarie-Drolet, Chloe" + "\r\n"
				+ "C FII143100 LON" + "\r\n" + "C FII356100 LON" + "\r\n"
				+ "C SOI247100 LON" + "\r\n" + "C SOI362100 LON" + "\r\n"
				+ "E 011098382270000320013 Roy, Julie" + "\r\n"
				+ "C FII155100 SHE" + "\r\n" + "C FII211100 SHE" + "\r\n"
				+ "C FII221100 SHE" + "\r\n" + "C MQB144100 SHE" + "\r\n"
				+ "T 003";
		SetOfStuSites setOfStuSites = new SetOfStuSites(); //,5,14);
		DLoadData ld = new DLoadData();
		setOfStuSites.analyseTokens(ld.buildDataExchange(tokens.getBytes()), 0);
		assertEquals("test1_analyseTokens1_6: assertEquals", "", setOfStuSites
				.getError());
	}

	/**
	 * test2_analyseTokens, test that analyse the empty student name or empty
	 * matricule or mistakes line description in the student file
	 */
	public void test2_analyseTokens1_6() {
		String tokens = "Diamant1.6" + "\r\n"
				+ "E 009391402270000320033 Pinard, Yannick" + "\r\n"
				+ "C FII221100 LON" + "\r\n" + "C SOI146100 LON" + "\r\n"
				+ "C SOI247100 LON" + "\r\n" + "C SOI250100 LON" + "\r\n"
				+ "C SOI320100 LON" + "\r\n"
				+ "E 011081522270010320011 Décarie-Drolet, Chloe" + "\r\n"
				+ "C FII143100 LON" + "\r\n" + "C FII356100 LON" + "\r\n"
				+ "C SOI247100 LON" + "\r\n" + "C SOI362100 LON" + "\r\n"
				+ "E 011098382270000320013 Roy, Julie" + "\r\n"
				+ "C FII155100 SHE" + "\r\n" + "C FII211100 SHE" + "\r\n"
				+ "C FII221100 SHE" + "\r\n" + "C MQB144100 SHE" + "\r\n"
				+ "T 003";
		SetOfStuSites setOfStuSites = new SetOfStuSites(); //,5,14);
		DLoadData ld = new DLoadData();
		setOfStuSites.analyseTokens(ld.buildDataExchange(tokens.getBytes()), 0);
		assertEquals("test2_analyseTokens1_6: assertEquals", "", setOfStuSites
				.getError());//.substring(0,DConst.STUD_TEXT8.length()));
	}

	/**
	 * test2_analyseTokens, test that analyse the empty student name or empty
	 * matricule or mistakes line description in the student file
	 */
	public void test3_analyseTokens1_6() {
		String tokens = "Diamant1.6" + "\r\n"
				+ "E 009391402270000320033 Pinard,  Yannick, Ulrich" + "\r\n"
				+ "C FII221100 LON" + "\r\n" + "C SOI146100 LON" + "\r\n"
				+ "C SOI247100 LON" + "\r\n" + "C SOI250100 LON" + "\r\n"
				+ "C SOI320100 LON" + "\r\n"
				+ "E 011081522270010320011 Décarie-Drolet, Chloe" + "\r\n"
				+ "C FII143100 LON" + "\r\n" + "C FII356100 LON" + "\r\n"
				+ "C SOI247100 LON" + "\r\n" + "C SOI362100 LON" + "\r\n"
				+ "E 011098382270000320013 Roy, Julie" + "\r\n"
				+ "C FII155100 SHE" + "\r\n" + "C FII211100 SHE" + "\r\n"
				+ "C FII221100 SHE" + "\r\n" + "C MQB144100 SHE" + "\r\n"
				+ "T 003";
		SetOfStuSites setOfStuSites = new SetOfStuSites(); //,5,14);
		DLoadData ld = new DLoadData();
		setOfStuSites.analyseTokens(ld.buildDataExchange(tokens.getBytes()), 0);
		assertEquals("test3_analyseTokens1_6: assertEquals", "", setOfStuSites
				.getError());
	}

	/**
	 * test2_analyseTokens, test that analyse the empty student name or empty
	 * matricule or mistakes line description in the student file
	 */
	public void test4_analyseTokens1_6() {
		String tokens = "Diamant1.6" + "\r\n"
				+ "E 009391402270000320033 Syam,  Yannick Ulrich" + "\r\n"
				+ "C FII221100 LON" + "\r\n" + "C SOI146100 LON" + "\r\n"
				+ "C SOI247100 LON" + "\r\n" + "C SOI250100 LON" + "\r\n"
				+ "C SOI320100 LON" + "\r\n"
				+ "E 011081A22270010320011 Décarie-Drolet, Chloe" + "\r\n"
				+ "C FII143100 LON" + "\r\n" + "C FII356100 LON" + "\r\n"
				+ "C SOI247100 LON" + "\r\n" + "C SOI362100 LON" + "\r\n"
				+ "E 011098382270000320013 Roy, Julie" + "\r\n"
				+ "C FII155100 SHE" + "\r\n" + "C FII211100 SHE" + "\r\n"
				+ "C FII221100 SHE" + "\r\n" + "C MQB144100 SHE" + "\r\n"
				+ "T 00293";
		SetOfStuSites setOfStuSites = new SetOfStuSites(); //,5,14);
		DLoadData ld = new DLoadData();
		setOfStuSites.analyseTokens(ld.buildDataExchange(tokens.getBytes()), 0);
		assertEquals("test4_analyseTokens1_6: assertEquals", DConst.STUD_TEXT1,
				setOfStuSites.getError().substring(0,
						DConst.STUD_TEXT1.length()));
	}

	/**
	 * test2_analyseTokens, test that analyse the empty student name or empty
	 * matricule or mistakes line description in the student file
	 */
	public void test5_analyseTokens1_6() {
		String tokens = "Diamant1.6" + "\r\n"
				+ "E 009391402270000320033 Syam,  Yannick Ulrich" + "\r\n"
				+ "C FII221100 LON" + "\r\n" + "C SOI146100 LON" + "\r\n"
				+ "C SOI247100 LON" + "\r\n" + "C SOI250100 LON" + "\r\n"
				+ "C SOI320100 LON" + "\r\n"
				+ "E 011081522270010320011 Décarie-Drolet, Chloe" + "\r\n"
				+ "C FII143100 LON 2" + "\r\n" + "C FII356100 LON" + "\r\n"
				+ "C SOI247100 LON" + "\r\n" + "C SOI362100 LON" + "\r\n"
				+ "E 011098382270000320013 Roy, Julie" + "\r\n"
				+ "C FII155100 SHE" + "\r\n" + "C FII211100 SHE" + "\r\n"
				+ "C FII221100 SHE" + "\r\n" + "C MQB144100 SHE" + "\r\n"
				+ "T 00293";
		SetOfStuSites setOfStuSites = new SetOfStuSites(); //,5,14);
		DLoadData ld = new DLoadData();
		setOfStuSites.analyseTokens(ld.buildDataExchange(tokens.getBytes()), 0);
		assertEquals("test5_analyseTokens1_6: assertEquals", DConst.STUD_TEXT8,
				setOfStuSites.getError().substring(0,
						DConst.STUD_TEXT8.length()));
	}

	/**
	 * test6_analyseTokens, test that analyse student course choice it analyse
	 * the group where student is assigned in the student file
	 */
	public void test6_analyseTokens1_6() {
		String tokens = "Diamant1.6" + "\r\n"
				+ "E 009391402270000320033 Syam,  Yannick Ulrich" + "\r\n"
				+ "C FII221100 LON" + "\r\n" + "C SOI146100 LON" + "\r\n"
				+ "C SOI247100 LON" + "\r\n" + "C SOI250100 LON" + "\r\n"
				+ "C SOI320100 LON" + "\r\n"
				+ "E 011081522270010320011 Décarie-Drolet, Chloe" + "\r\n"
				+ "C FII1431A0 LON" + "\r\n" + "C FII356100 LON" + "\r\n"
				+ "C SOI247100 LON" + "\r\n" + "C SOI362100 LON" + "\r\n"
				+ "E 011098382270000320013 Roy, Julie" + "\r\n"
				+ "C FII155100 SHE" + "\r\n" + "C FII211100 SHE" + "\r\n"
				+ "C FII221100 SHE" + "\r\n" + "C MQB144100 SHE" + "\r\n"
				+ "T 00293";
		SetOfStuSites setOfStuSites = new SetOfStuSites(); //,5,14);
		DLoadData ld = new DLoadData();
		setOfStuSites.analyseTokens(ld.buildDataExchange(tokens.getBytes()), 0);
		assertEquals("test6_analyseTokens1_6: assertEquals", DConst.STUD_TEXT3,
				setOfStuSites.getError().substring(0,
						DConst.STUD_TEXT3.length()));
	}

	/**
	 * test6_analyseTokens, test that analyse student course choice it analyse
	 * the length of the course in the student file
	 */
	public void test7_analyseTokens1_6() {
		String tokens = "Diamant1.6" + "\r\n"
				+ "E 009391402270000320033 Syam,  Yannick Ulrich" + "\r\n"
				+ "C FII221100 LON" + "\r\n" + "C SOI146100 LON" + "\r\n"
				+ "C SOI247100 LON" + "\r\n" + "C SOI250100 LON" + "\r\n"
				+ "C SOI320100 LON" + "\r\n"
				+ "E 011081522270010320011 Décarie-Drolet, Chloe" + "\r\n"
				+ "C FII143 LON" + "\r\n" + "C FII356100 LON" + "\r\n"
				+ "C SOI247100 LON" + "\r\n" + "C SOI362100 LON" + "\r\n"
				+ "E 011098382270000320013 Roy, Julie" + "\r\n"
				+ "C FII155100 SHE" + "\r\n" + "C FII211100 SHE" + "\r\n"
				+ "C FII221100 SHE" + "\r\n" + "C MQB144100 SHE" + "\r\n"
				+ "T 00293";
		SetOfStuSites setOfStuSites = new SetOfStuSites(); //,5,14);
		DLoadData ld = new DLoadData();
		setOfStuSites.analyseTokens(ld.buildDataExchange(tokens.getBytes()), 0);
		assertEquals("test7_analyseTokens1_6: assertEquals", DConst.STUD_TEXT3,
				setOfStuSites.getError().substring(0,
						DConst.STUD_TEXT3.length()));
	}

	/**
	 * test6_analyseTokens, test that analyse student course choice it analyse
	 * the group where student is assigned in the student file
	 */
	public void test8_analyseTokens1_6() {
		String tokens = "Diamant1.6" + "\r\n"
				+ "E 009391402270000320033 Syam,  Yannick Ulrich" + "\r\n"
				+ "C FII221100 LON" + "\r\n" + "C SOI146100 LON" + "\r\n"
				+ "C SOI247100 LON" + "\r\n" + "C SOI250100 LON" + "\r\n"
				+ "C SOI320100 LON" + "\r\n"
				+ "E 011081522270010320011 Décarie-Drolet, Chloe" + "\r\n"
				+ "C FII14310 LON" + "\r\n" + "C FII356100 LON" + "\r\n"
				+ "C SOI247100 LON" + "\r\n" + "C SOI362100 LON" + "\r\n"
				+ "E 011098382270000320013 Roy, Julie" + "\r\n"
				+ "C FII155100 SHE" + "\r\n" + "C FII211100 SHE" + "\r\n"
				+ "C FII221100 SHE" + "\r\n" + "C MQB144100 SHE" + "\r\n"
				+ "T 00293";
		SetOfStuSites setOfStuSites = new SetOfStuSites(); //,5,14);
		DLoadData ld = new DLoadData();
		setOfStuSites.analyseTokens(ld.buildDataExchange(tokens.getBytes()), 0);
		assertEquals("test8_analyseTokens1_6: assertEquals", DConst.STUD_TEXT3,
				setOfStuSites.getError().substring(0,
						DConst.STUD_TEXT3.length()));
	}

	/**
	 * test6_analyseTokens, test that analyse the site of the student course
	 * choice it analyse the group where student is assigned in the student file
	 */
	public void test9_analyseTokens1_6() {
		String tokens = "Diamant1.6" + "\r\n"
				+ "E 009391402270000320033 Syam,  Yannick Ulrich" + "\r\n"
				+ "C FII221100 LON" + "\r\n" + "C SOI146100 LON" + "\r\n"
				+ "C SOI247100 LON" + "\r\n" + "C SOI250100 LON" + "\r\n"
				+ "C SOI320100 LON" + "\r\n"
				+ "E 011081522270010320011 Décarie-Drolet, Chloe" + "\r\n"
				+ "C FII143100 LON" + "\r\n" + "C FII356100 LON" + "\r\n"
				+ "C SOI247100 LON" + "\r\n" + "C SOI362100 LON" + "\r\n"
				+ "E 011098382270000320013 Roy, Julie" + "\r\n"
				+ "C FII155100 SH" + "\r\n" + "C FII211100 SHE" + "\r\n"
				+ "C FII221100 SHE" + "\r\n" + "C MQB144100 SHE" + "\r\n"
				+ "T 00293";
		SetOfStuSites setOfStuSites = new SetOfStuSites(); //,5,14);
		DLoadData ld = new DLoadData();
		setOfStuSites.analyseTokens(ld.buildDataExchange(tokens.getBytes()), 0);
		assertEquals("test9_analyseTokens1_6: assertEquals", DConst.STUD_TEXT9,
				setOfStuSites.getError().substring(0,
						DConst.STUD_TEXT9.length()));
	}

	/**
	 * test6_analyseTokens, test that analyse the wrong number of students it
	 * analyse the group where student is assigned in the student file
	 */
	public void test10_analyseTokens1_6() {
		String tokens = "Diamant1.6" + "\r\n"
				+ "E 009391402270000320033 Syam,  Yannick Ulrich" + "\r\n"
				+ "C FII221100 LON" + "\r\n" + "C SOI146100 LON" + "\r\n"
				+ "C SOI247100 LON" + "\r\n" + "C SOI250100 LON" + "\r\n"
				+ "C SOI320100 LON" + "\r\n"
				+ "E 011081522270010320011 Décarie-Drolet, Chloe" + "\r\n"
				+ "C FII143100 LON" + "\r\n" + "C FII356100 LON" + "\r\n"
				+ "C SOI247100 LON" + "\r\n" + "C SOI362100 LON" + "\r\n"
				+ "E 011098382270000320013 Roy, Julie" + "\r\n"
				+ "C FII155100 SHE" + "\r\n" + "C FII211100 SHE" + "\r\n"
				+ "C FII221100 SHE" + "\r\n" + "C MQB144100 SHE" + "\r\n"
				+ "T 00293";
		SetOfStuSites setOfStuSites = new SetOfStuSites(); //,5,14);
		DLoadData ld = new DLoadData();
		setOfStuSites.analyseTokens(ld.buildDataExchange(tokens.getBytes()), 0);
		assertEquals("test10_analyseTokens1_6: assertEquals",
				DConst.STUD_TEXT6, setOfStuSites.getError().substring(0,
						DConst.STUD_TEXT6.length()));
	}

	/**
	 * test2_analyseTokens, test that analyse the wrong number of student
	 * courses choices in the students file
	 */
	public void test2_analyseTokens1_5() {
		String tokens = "    0015" + "\r\n"
				+ "009008132035030720003LUPIEN MY04" + "\r\n"
				+ "CTB301101 GIS251102 GIS3511 GRH111101 GRH332101" + "\r\n"
				+ "009011991290000520021AUDET FRE05" + "\r\n"
				+ "CTB3411 FEC111102 FEC4441 GIS114101 MAR2211" + "\r\n"
				+ "009027042035010720003VEILLEUX 04" + "\r\n"
				+ "CTB443101 CTB451102 CTB513102 CTB563101" + "\r\n";
		SetOfStuSites setOfStuSites = new SetOfStuSites(); //,5,14);
		DLoadData ld = new DLoadData();
		setOfStuSites.analyseTokens(ld.buildDataExchange(tokens.getBytes()), 0);
		assertEquals("test2_analyseTokens1_5: assertEquals", DConst.STUD_TEXT7,
				setOfStuSites.getError().substring(0,
						DConst.STUD_TEXT7.length()));
	}

	/**
	 * test3_analyseTokens, test that analyse matricule of students in the
	 * students file
	 */
	public void test3_analyseTokens1_5() {
		String tokens = "    0015" + "\r\n"
				+ "0x9008132035030720003LUPIEN MY04" + "\r\n"
				+ "CTB301101 GIS251102 GIS3511 GRH111101 GRH332101" + "\r\n"
				+ "009011991290000520021AUDET FRE05" + "\r\n"
				+ "CTB3411 FEC111102 FEC4441 GIS114101 MAR2211" + "\r\n"
				+ "009027042035010720003VEILLEUX 04" + "\r\n"
				+ "CTB443101 CTB451102 CTB513102 CTB563101" + "\r\n";
		SetOfStuSites setOfStuSites = new SetOfStuSites(); //,5,14);
		DLoadData ld = new DLoadData();
		setOfStuSites.analyseTokens(ld.buildDataExchange(tokens.getBytes()), 0);
		assertEquals("test3_analyseTokens1_5: assertEquals", DConst.STUD_TEXT1,
				setOfStuSites.getError().substring(0,
						DConst.STUD_TEXT1.length()));
	}

	/**
	 * test4_analyseTokens, test that analyse that the course choice has a valid
	 * format in the students file one course choise has 6 char (CTB301)
	 */
	public void test4_analyseTokens1_5() {
		String tokens = "    003" + "\r\n" + "009008132035030720003LUPIEN MY05"
				+ "\r\n" + "CTB301 GIS251102 GIS351101 GRH111101 GRH332101"
				+ "\r\n" + "009011991290000520021AUDET FRE05" + "\r\n"
				+ "CTB3411 FEC111102 FEC4441 GIS114101 MAR2211" + "\r\n"
				+ "009027042035010720003VEILLEUX 04" + "\r\n"
				+ "CTB443101 CTB451102 CTB513102 CTB563101" + "\r\n";
		SetOfStuSites setOfStuSites = new SetOfStuSites(); //,5,14);
		DLoadData ld = new DLoadData();
		setOfStuSites.analyseTokens(ld.buildDataExchange(tokens.getBytes()), 0);
		assertEquals("test4_analyseTokens1_5: assertEquals", DConst.STUD_TEXT3,
				setOfStuSites.getError().substring(0,
						DConst.STUD_TEXT3.length()));
	}

	/**
	 * test5_analyseTokens, test that analyse that the course choice has a valid
	 * format in the students file one course choise has 8 char (CTB30111)
	 */
	public void test5_analyseTokens1_5() {
		String tokens = "    003" + "\r\n" + "009008132035030720003LUPIEN MY05"
				+ "\r\n" + "CTB30111 GIS251102 GIS351101 GRH111101 GRH332101"
				+ "\r\n" + "009011991290000520021AUDET FRE05" + "\r\n"
				+ "CTB3411 FEC111102 FEC4441 GIS114101 MAR2211" + "\r\n"
				+ "009027042035010720003VEILLEUX 04" + "\r\n"
				+ "CTB443101 CTB451102 CTB513102 CTB563101" + "\r\n";
		SetOfStuSites setOfStuSites = new SetOfStuSites(); //,5,14);
		DLoadData ld = new DLoadData();
		setOfStuSites.analyseTokens(ld.buildDataExchange(tokens.getBytes()), 0);
		assertEquals("test5_analyseTokens1_5: assertEquals", DConst.STUD_TEXT3,
				setOfStuSites.getError().substring(0,
						DConst.STUD_TEXT3.length()));
	}

	/**
	 * test6_analyseTokens, test that analyse that the course choice has a valid
	 * format in the students file one course choise has an invalid char in the
	 * group reserved place (CTB30110x)
	 */
	public void test6_analyseTokens1_5() {
		String tokens = "    003" + "\r\n" + "009008132035030720003LUPIEN MY05"
				+ "\r\n" + "CTB30110x GIS251102 GIS351101 GRH111101 GRH332101"
				+ "\r\n" + "009011991290000520021AUDET FRE05" + "\r\n"
				+ "CTB3411 FEC111102 FEC4441 GIS114101 MAR2211" + "\r\n"
				+ "009027042035010720003VEILLEUX 04" + "\r\n"
				+ "CTB443101 CTB451102 CTB513102 CTB563101" + "\r\n";
		SetOfStuSites setOfStuSites = new SetOfStuSites(); //,5,14);
		DLoadData ld = new DLoadData();
		setOfStuSites.analyseTokens(ld.buildDataExchange(tokens.getBytes()), 0);
		assertEquals("test6_analyseTokens1_5: assertEquals", DConst.STUD_TEXT3,
				setOfStuSites.getError().substring(0,
						DConst.STUD_TEXT3.length()));
	}

	/**
	 * test7_analyseTokens, test that analyse the number of students in the
	 * students file
	 */
	public void test7_analyseTokens1_5() {
		String tokens = "    0015" + "\r\n"
				+ "009008132035030720003LUPIEN MY05" + "\r\n"
				+ "CTB3011 GIS251102 GIS351101 GRH111101 GRH332101" + "\r\n"
				+ "009011991290000520021AUDET FRE05" + "\r\n"
				+ "CTB3411 FEC111102 FEC4441 GIS114101 MAR2211" + "\r\n"
				+ "009027042035010720003VEILLEUX 04" + "\r\n"
				+ "CTB443101 CTB451102 CTB513102 CTB563101" + "\r\n";
		SetOfStuSites setOfStuSites = new SetOfStuSites(); //,5,14);
		DLoadData ld = new DLoadData();
		setOfStuSites.analyseTokens(ld.buildDataExchange(tokens.getBytes()), 0);
		assertEquals("test7_analyseTokens1_5: assertEquals", DConst.STUD_TEXT6,
				setOfStuSites.getError().substring(0,
						DConst.STUD_TEXT6.length()));
	}

	/**
	 * test1_buildSetOfResources1_6, test that build the student structure from
	 * the student file
	 */
	public void test1_buildSetOfResources1_6() {
		String tokens = "Diamant1.6" + "\r\n"
				+ "E 009391402270000320033 Syam,  Yannick Ulrich" + "\r\n"
				+ "C FII221100 LON" + "\r\n" + "C SOI146100 LON" + "\r\n"
				+ "C SOI247100 LON" + "\r\n" + "C SOI250100 SHE" + "\r\n"
				+ "C MQB144101 LON" + "\r\n"
				+ "E 011081522270010320011 Décarie-Drolet, Chloe" + "\r\n"
				+ "C FII143100 LON" + "\r\n" + "C FII356100 LON" + "\r\n"
				+ "C SOI247100 LON" + "\r\n" + "C SOI362100 LON" + "\r\n"
				+ "E 011098382270000320013 Roy, Julie" + "\r\n"
				+ "C FII155100 SHE" + "\r\n" + "C FII211100 SHE" + "\r\n"
				+ "C FII221100 SHE" + "\r\n" + "C MQB144101 LON" + "\r\n"
				+ "T 003";
		Vector list = new Vector();
		list.add("Syam, Yannick Ulrich            00939140");
		list.add("Roy, Julie  01109838");
		SetOfStuSites setOfStuSites = new SetOfStuSites(); //,5,14);
		DLoadData ld = new DLoadData();
		setOfStuSites.analyseTokens(ld.buildDataExchange(tokens.getBytes()), 0);
		setOfStuSites.buildSetOfResources(ld.buildDataExchange(tokens
				.getBytes()), 0);
		SetOfStudents sos = (SetOfStudents) setOfStuSites.getResource("LON")
				.getAttach();
		assertEquals("test1_buildSetOfResources1_6: assertEquals", list, sos
				.getStudentsByGroup("MQB144", "1", 1));
	}

	/**
	 * test_getStudentsByGroup, test that analyse the list if students who have
	 * inscription in a group of activity
	 */
	public void test_getStudentsByGroup() {
		String tokens = "    004" + "\r\n" + "009008132035030720003LUPIEN MY05"
				+ "\r\n" + "GEI442101 GIS251102 GIS3511 GRH111101 GRH332101"
				+ "\r\n" + "009011991290000520021AUDET FRE05" + "\r\n"
				+ "CTB3411 FEC111102 FEC4441 GIS114101 MAR2211" + "\r\n"
				+ "009027042035010720003VEILLEUX 04" + "\r\n"
				+ "CTB443101 CTB451102 CTB513102 CTB563101" + "\r\n"
				+ "019027042035010720003ALEX JARA04" + "\r\n"
				+ "GEI700101 GEI450202 CTB513102 GEI442101" + "\r\n";
		SetOfStuSites setOfStuSites = new SetOfStuSites(); //,5,14);
		DLoadData ld = new DLoadData();
		setOfStuSites.analyseTokens(ld.buildDataExchange(tokens.getBytes()), 0);
		setOfStuSites.buildSetOfResources(ld.buildDataExchange(tokens
				.getBytes()), 0);
		Vector list = new Vector();
		list.add("LUPIEN MY 00900813");
		list.add("ALEX JARA 01902704");
		SetOfStudents sos = (SetOfStudents) setOfStuSites.getResource(
				DConst.STUDENT_STANDARD_SITE).getAttach();
		assertEquals("test_getStudentsByGroup: assertEquals", list, sos
				.getStudentsByGroup("GEI442", "1", 1));
	}

	public void test_toWrite() {
		String tokens = "    004" + "\r\n" + "009008132035030720003LUPIEN MY05"
				+ "\r\n" + "GEI442101 GIS251102 GIS3511 GRH111101 GRH332101"
				+ "\r\n" + "009011991290000520021AUDET FRE05" + "\r\n"
				+ "CTB3411 FEC111102 FEC4441 GIS114101 MAR2211" + "\r\n"
				+ "009027042035010720003VEILLEUX 04" + "\r\n"
				+ "CTB443101 CTB451102 CTB513102 CTB563101" + "\r\n"
				+ "019027042035010720003ALEX JARA04" + "\r\n"
				+ "GEI700101 GEI450202 CTB513102 GEI442101" + "\r\n";
		SetOfStuSites setOfStuSites = new SetOfStuSites();
		DLoadData ld = new DLoadData();
		setOfStuSites.analyseTokens(ld.buildDataExchange(tokens.getBytes()), 0);
		setOfStuSites.buildSetOfResources(ld.buildDataExchange(tokens
				.getBytes()), 0);
		Vector list = new Vector();
		list.add("LUPIEN MY 00900813");
		list.add("ALEX JARA 01902704");
		assertEquals("test_toWrite: assertEquals",
				"E 009008132035030720003 LUPIEN MY\r\n"
						+ "C GEI442101;1 SHE\r\n" + "C GIS251102;1 SHE\r\n"
						+ "C GIS351100 SHE\r\n" + "C GRH111101;1 SHE\r\n"
						+ "C GRH332101;1 SHE\r\n"
						+ "E 009011991290000520021 AUDET FRE\r\n"
						+ "C CTB341100 SHE\r\n" + "C FEC111102;1 SHE\r\n"
						+ "C FEC444100 SHE\r\n" + "C GIS114101;1 SHE\r\n"
						+ "C MAR221100 SHE\r\n"
						+ "E 009027042035010720003 VEILLEUX\r\n"
						+ "C CTB443101;1 SHE\r\n" + "C CTB451102;1 SHE\r\n"
						+ "C CTB513102;1 SHE\r\n" + "C CTB563101;1 SHE\r\n"
						+ "E 019027042035010720003 ALEX JARA\r\n"
						+ "C CTB513102;1 SHE\r\n" + "C GEI442101;1 SHE\r\n"
						+ "C GEI450202;1 SHE\r\n" + "C GEI700101;1 SHE",
				setOfStuSites.toWrite());
	}
}