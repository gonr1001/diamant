import java.io.*;

import com.iLib.gIO.ByteInputFile;
public class ReadPrintFile {

  public static void main(String[] args) throws Exception{
    byte [] b;
    ByteInputFile inputFile= new ByteInputFile (args[0]);

    b = inputFile.readFileAsBytes();
    inputFile.close();
 /*   if (!inputFile.available()||!inputFile.canRead()){
      System.out.println ("Impossible de lire "+inputFile);
      return;
    } //end if*/
    for (int i = 0 ; i < b.length; i ++ ) {
      System.out.println("char: "+(char)b[i] +
                         " int: "+ b[i] +
                         " byte: " + (byte)b[i] +
                         " short: "+(short)b[i] +
                           " HEX:" + Integer.toHexString(b[i]).toUpperCase());
    }
/*
    try {


      FileReader in = new FileReader(inputFile);
      int c;
      int i=0;
      int limit=1000000;

      while (((c = in.read()) != -1)) //&&(i<limit))
      {
        System.out.println("char: "+(char)c +
                           " int: "+ c +
                           " byte: " + (byte)c +
                           " short: "+(short)c +
                           " HEX:" + Integer.toHexString(c).toUpperCase());
        i++;
      }
      in.close();
    }//end try
    catch(FileNotFoundException e){
      System.out.println ("Le fichier n'existe plus.");
    }//end catch*/

  } // End of main
}