package lineInterface;

/**
 * <p>Title: Diamant 1.5</p>
 * <p>Description: exam timetable construction with Condition Pattern</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author rgr + ys
 * @version 1.0
 */

public class DILigne {

  public DILigne() {
  }

  public void doIt(String[] args) {
  	
  }
}

/*
 /**
  *
  * Title: RunAkko $Revision: 1.4 $  $Date: 2004-10-14 18:59:35 $
  * Description: RunAkko is a class used to call the whole application.
  *             the main method of the whole program is here.
  * Copyright (c) 2001 by rgr-fdl.
  * All rights reserved.
  *
  *
  * This software is the confidential and proprietary information
  * of rgr-fdl. ("Confidential Information").  You
  * shall not disclose such Confidential Information and shall use
  * it only in accordance with the terms of the license agreement
  * you entered into with rgr-fdl.
  *
  * @version $Revision: 1.4 $
  * @author  $Author: gonzrubi $
  * @since JDK1.3
  */
 /*
 package lineInterface;

 import java.io.*;
 import java.util.Vector;
 import gInternal.*;
 import utilities.*;

 public class RunAkko {
     Vector _temp = new Vector();
     public RunAkko () {
         super();
         _temp = new Vector();
     }

     public void execute(String[] args) {
         System.out.println("hello RunAkko");
         try {
             readFile();
         } catch(Exception ioe) {
             System.out.println(ioe );
             ioe.printStackTrace();
             System.exit(10);

         }
         DataDoc _dD = new DataDoc(_temp);

    //     _dD.verificationResources();
    //     _dD.verificationConsomers();
         _dD.calcul();
         try {
             writeFile();
         } catch(Exception ioe) {
             System.out.println(ioe );
             ioe.printStackTrace();
             System.exit(11);
         }
         System.out.println("bye RunAkko");

     } // end execute



     private void readFile() throws Exception {
         InputTextFile f = new InputTextFile("akko.ent");
         String s = f.readLine();
         while (s != null) {
             //AbsItem  ai = new AbsItem(s);
             _temp.addElement(s);
             s = f.readLine();
         } // end while
         f.close();

     } // end readFile

     private void writeFile()throws Exception {
         OutputFile f = new OutputFile("akko.sor");
         for(int i=0; i < _temp.size() ; i++) {
             f.writeln((String)_temp.elementAt(i));
         } // end for i
         f.close();

     } // end writeFile


}  // end RunAkko

*/
