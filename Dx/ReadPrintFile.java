

import com.iLib.gIO.ByteInputFile;

public class ReadPrintFile {

  public static void main(String[] args) throws Exception{

    //FileInputStream _fis = new FileInputStream(args[0]);
    byte [] b;
    ByteInputFile inputFile= new ByteInputFile (args[0]);

    //InputStreamReader isr = new InputStreamReader(_fis);
    // System.out.println(isr.getEncoding());
    b = inputFile.readFileAsBytes();
    inputFile.close();

    for (int i = 0 ; i < b.length; i ++ ) {
      System.out.println("char: "+(char)b[i] +
      " int: "+ b[i] +
      " byte: " + (byte)b[i] +
      " short: "+(short)b[i] +
      " HEX:" + Integer.toHexString(b[i]).toUpperCase());
    }
  } // End of main
}