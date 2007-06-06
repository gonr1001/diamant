
/**
 * <p>Title: Diamant 1.5</p>
 * <p>Description: exam timetable construction with Condition Pattern</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author rgr + ys
 * @version 1.0
 */




package lineInterface;


public class DILigne {

  public DILigne() {
  	//  TODO Auto-generated constructor stub
  }

  public void doIt(String[] args) {
  	if (args.length == 0){
//  	 TODO Auto-generated constructor stub
  	}
  }
}

/*
 
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
