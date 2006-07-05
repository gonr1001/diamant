/**
 * 
 * Title: SetOfCategoriesTest $Revision $ $Date: 2006-07-05 20:22:05 $
 * Description: SetOfCategoriesTest is a class used to test the class
 * SetOfCategories
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
 * @version $ $
 * @author $Author: gonzrubi $
 * @since JDK1.3
 */

package dTest.dInternal.dData.dRooms;

import dConstants.DConst;
import dInternal.DataExchange;
import dInternal.dData.ByteArrayMsg;
import dInternal.dData.DLoadData;
import dInternal.dData.dRooms.SetOfCategories;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class SetOfCategoriesTest extends TestCase {
    SetOfCategories _setOfCategories;

    DataExchange _dataExchange;

    DLoadData _loadData;

    public SetOfCategoriesTest(String name) {
        super(name);
    }

    public static Test suite() {
        // the type safe way is in SimpleTest
        // the dynamic way :
        return new TestSuite(SetOfCategoriesTest.class);
    } // end suite

    public void setUp() {
        _setOfCategories = new SetOfCategories();
        _dataExchange = new ByteArrayMsg(DConst.FILE_VER_NAME1_5,
                "course+token");
        _loadData = new DLoadData();
    }

    public void test_buildSetOfResources() {
        String tokens = "D13013;40;211;8,11,57;SHE;CAT 1;laboratoire de chimie;1 1 1 1 1 1 1 1 1 1 1 1,1 1 1 1 1 1 1 1 1 1 1 1,1 1 1 1 1 1 1 1 1 1 1 1,1 1 1 1 1 1 1 1 1 1 1 1,1 1 1 1 1 1 1 1 1 1 1 1;"
                + "\r\n" + "D60054;40;217;9,11,14,15,37,38,44;SHE;CAT 2;xxx;1 1 1 1 1 1 1 1 1 1 1 1,1 1 1 1 1 1 1 1 1 1 1 1,1 1 1 1 1 1 1 1 1 1 1 1,1 1 1 1 1 1 1 1 1 1 1 1,1 1 1 1 1 1 1 1 1 1 1 1"
                + "\r\n";
        _setOfCategories.buildSetOfResources(_loadData.buildDataExchange(tokens.getBytes()), 0);
        assertEquals("test_buildSetOfResources: assertEquals", "CAT 1",  _setOfCategories.getSetOfResources().firstElement().getID());
    }

    public void test1_analyseTokens() {
        String tokens = "D13013;40;211;8,11,57;SHE;CAT 1;laboratoire de chimie;1 1 1 1 1 1 1 1 1 1 1 1,1 1 1 1 1 1 1 1 1 1 1 1,1 1 1 1 1 1 1 1 1 1 1 1,1 1 1 1 1 1 1 1 1 1 1 1,1 1 1 1 1 1 1 1 1 1 1 1;"
            + "\r\n" + "D60054;40;217;9,11,14,15,37,38,44;SHE;CAT 2;xxx;1 1 1 1 1 1 1 1 1 1 1 1,1 1 1 1 1 1 1 1 1 1 1 1,1 1 1 1 1 1 1 1 1 1 1 1,1 1 1 1 1 1 1 1 1 1 1 1,1 1 1 1 1 1 1 1 1 1 1 1"
            + "\r\n";
        _setOfCategories.buildSetOfResources(_loadData.buildDataExchange(tokens.getBytes()), 0);
        _setOfCategories.analyseTokens(_loadData.buildDataExchange(tokens.getBytes()), 0);
        assertEquals("test1_analyseTokens: assertEquals", "", _setOfCategories.getError());
    }

    public void test_toWrite() {
        String tokens = "D13013;40;211;8,11,57;SHE;CAT 1;laboratoire de chimie;1 1 1 1 1 1 1 1 1 1 1 1,1 1 1 1 1 1 1 1 1 1 1 1,1 1 1 1 1 1 1 1 1 1 1 1,1 1 1 1 1 1 1 1 1 1 1 1,1 1 1 1 1 1 1 1 1 1 1 1;"
            + "\r\n" + "D60054;40;217;9,11,14,15,37,38,44;SHE;CAT 2;xxx;1 1 1 1 1 1 1 1 1 1 1 1,1 1 1 1 1 1 1 1 1 1 1 1,1 1 1 1 1 1 1 1 1 1 1 1,1 1 1 1 1 1 1 1 1 1 1 1,1 1 1 1 1 1 1 1 1 1 1 1"
            + "\r\n";
        _setOfCategories.buildSetOfResources(_loadData.buildDataExchange(tokens.getBytes()), 0);
        assertEquals("test_toWrite: assertEquals", 
                 "D13013;40;211;8,11,57;Diamant1.6;CAT 1;laboratoire de chimie;1 1 1 1 1 1 1 1 1 1 1 1,1 1 1 1 1 1 1 1 1 1 1 1,1 1 1 1 1 1 1 1 1 1 1 1,1 1 1 1 1 1 1 1 1 1 1 1,1 1 1 1 1 1 1 1 1 1 1 1;\r\n",
                 _setOfCategories.toWrite(DConst.FILE_VER_NAME1_6));
    }

}