

package dmains;
import eLib.exit.txt.ByteInputFile;

public class ScanFile {

  public static void main(String[] args) throws Exception{

    //FileInputStream _fis = new FileInputStream(args[0]);dev
    byte [] b;
    ByteInputFile inputFile= new ByteInputFile (args[0]);


    //InputStreamReader isr = new InputStreamReader(_fis);rgr21333ffss
    // System.out.println(isr.getEncoding());ddnew sur branch
    b = inputFile.readFileAsBytes();
    inputFile.close();
	int i = 0;
    while ( i < b.length) {
     // System.out.println("char: "+(char)b[i] +
     // " int: "+ b[i] +
      //" byte: " + (byte)b[i] +
      //" short: "+(short)b[i] +
      //" HEX:" + Integer.toHexString(b[i]).toUpperCase());dev
      String str1 = "";
      String str2 = "";
      if (i+16 < b.length) {
		for(int j = 0 ; j < 16 ; j ++) {
				if (b[i+j] > 16) {
					str1 += Integer.toHexString(b[i+ j]).toUpperCase() + " ";
				} else {
					str1 += "0";
					str1 += Integer.toHexString(b[i+ j]).toUpperCase() + " ";
				}

			   if ( b[i + j ] < 126  && b[i + j] > 31 )
				   str2 += (char) b[i + j ];
			   else {
			   	if ( b[i + j ] == 13  ||b[i + j] == 10 )
			   		str2 += ".";
			   	else
					str2 +=  b[i + j] + " ";
			   }
			 }
			 System.out.println(str1 + "   " + str2);
			 i += 16 ;
      }
	  else {
		for(; i < b.length ; i ++) {
		if (b[i] > 16) {
			str1 += Integer.toHexString(b[i]).toUpperCase() + " ";
		} else {
			str1 += "0";
			str1 += Integer.toHexString(b[i]).toUpperCase() + " ";
		}

	   if ( b[i ] < 126  && b[ i] > 31 )
		   str2 += (char) b[ i ];
	   else {
		if ( b[i ] == 13  ||b[ i] == 10 )
			str2 += ".";
		else
			str2 +=  b[i] + " ";
	   }
	 }

	 System.out.println(str1 + "   " + str2);

	  }
    } // end while
    System.exit(1);
  } // End of main
}