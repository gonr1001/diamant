import java.io.*;
public class ReadPrintFile {

    public static void main(String[] args) throws Exception{

    File inputFile = new File (args[0]);

    if (!inputFile.exists()||!inputFile.canRead()){
       System.out.println ("Impossible de lire "+inputFile);
       return;
    } //end if

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
    }//end catch

 } // End of main
}
