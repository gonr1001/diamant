

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
	int i = 0;
    while ( i < b.length) {
     // System.out.println("char: "+(char)b[i] +
     // " int: "+ b[i] +
      //" byte: " + (byte)b[i] +
      //" short: "+(short)b[i] +
      //" HEX:" + Integer.toHexString(b[i]).toUpperCase());
      String str1 = "";
      String str2 = "";
      if (i+16 < b.length) {
		for(int j = 0 ; j < 16 ; j ++) {
			   str1 += (byte) b[i + j ] + " ";
			   if ( (byte)b[i + j ] < 126  && (byte)b[i + j] > 31 )
				   str2 += (char) b[i + j ];
			   else {
			   	if ( (byte)b[i + j ] == 13  ||(byte)b[i + j] == 10 )
			   		str2 += ".";
			   	else 
					str2 += (byte) b[i + j] + " ";
			   }
			 }
			 System.out.println(str1 + "   " + str2);
			 i += 16 ;
      }     
	  else {
	  	;
	  }
    } // end while
  } // End of main
}