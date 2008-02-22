/**
 * 
 */
package dInternal.dTimeTable;

/**
 * @author gonr1001
 *
 */


import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.util.XMLEventAllocator;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import java.io.FileInputStream;


import com.sun.xml.stream.events.XMLEventAllocatorImpl;

/**
 * StAX has two approach to parse XML. 
 * <li>
 *     <ul> Cursor : <code>XMLStreamReader</code> </ul>
 *     <ul> Event : <code>XMLEventReader</code> </ul>
 * </li>
 * 
 * <code>XMLStreamReader</code> returns the integer constant corresponding to particular event and 
 * application need to call relevant function to get information about that event. This is the most
 * efficient way to parse XML.
 *
 * <code>XMLEventReader</code> returns immutable and persistent event objects. 
 * All the information related to particular event is encapsulated in the returned <code>XMLEvent</code>
 * object. In this approach user need not worry about how to get relevant information corresponding to 
 * particular event,as in case of XMLStreamReader. This makes it pretty easy for the user to work with 
 * event approach. 
 * 
 * The disadvantage of event approach is the extra overhead of creating every event objects which
 * consumes both time and memory. However, It is possible to get event information as an <code>XMLEvent<code/> 
 * object even when using cursor approach ie. <code>XMLStreamReader</code> using <code>XMLEventAllocator</code>.
 * 
 * This class shows how application can get information as an <code>XMLEvent</code> object when using cursor 
 * approach. 
 * 
 *
 * @author Neeraj Bajaj, Sun Microsystems.
 *
 */

public class StaxTry {
    
    
    static XMLEventAllocator allocator = null;
    
    private static void printUsage() {
        System.out.println("Usage: java -classpath SwitchCursorToEvent <xmlfile>");
    }
    
    public static void main(String[] args) throws Exception {

        String filename = null; // RESOURCES_FOLDER + "/ntest.xml";
        
        try {
            filename = args[0];
            System.out.println("fn " + filename);
        } catch (ArrayIndexOutOfBoundsException aioobe){
            printUsage();
            System.exit(0);
        } catch (Exception ex){
            printUsage();
            ex.printStackTrace() ;
        }
        
        try{
            XMLInputFactory xmlif = XMLInputFactory.newInstance();        
            System.out.println("FACTORY: " + xmlif);        
            xmlif.setEventAllocator(new XMLEventAllocatorImpl());
            allocator = xmlif.getEventAllocator();
            XMLStreamReader xmlr = xmlif.createXMLStreamReader(filename, new FileInputStream(filename));

            int eventType = xmlr.getEventType();
            int state = 0;
            while(xmlr.hasNext()){
                eventType = xmlr.next();
                //Get all "Book" elements as XMLEvent object
                switch (state) {
                case 0 : break; //test start  state 1
                case 1 : break; //test cycle state 2
                case 2 : break; //test days state 3
                case 3 : break; //test day state 4
                case 4 : break; //test seqs state 5 
                case 5 : break; //test period state 6
                case 6 : break; //test in period 7
                case 7 : break; //test end period 8
                case 8 : break; //test end period 7 or end seq 9
                case 9 : break; //test end day 10 or seq
                case 10 : break; //test end cycle 11 or day 3
                case 11 : break; //test en tt 
                }
                if(eventType == XMLStreamConstants.START_ELEMENT ){//&& xmlr.getLocalName().equals("Book")){
                    //get immutable XMLEvent
                    StartElement sEvent = getXMLEvent(xmlr).asStartElement();                        
                    System.out.println("EVENT sE: " + sEvent.toString());
                }
                chars(xmlr, eventType);
                if(eventType == XMLStreamConstants.END_ELEMENT){//&& xmlr.getLocalName().equals("Book")){
                    //get immutable XMLEvent
                	EndElement eEvent = getXMLEvent(xmlr).asEndElement();                        
                    System.out.println("EVENT eE: " + eEvent.toString());
                }
            }
        }catch(XMLStreamException ex){
            ex.printStackTrace();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        
    }

	/**
	 * @param xmlr
	 * @param eventType
	 * @throws XMLStreamException
	 */
	private static void chars(XMLStreamReader xmlr, int eventType)
			throws XMLStreamException {
		if(eventType == XMLStreamConstants.CHARACTERS){//&& xmlr.getLocalName().equals("Book")){
		    //get immutable XMLEvent
			Characters chars = getXMLEvent(xmlr).asCharacters();  
			if (chars.toString() != "")
				System.out.println("EVENT c: " + chars);
		}
	}
    
    
    /** Get the immutable XMLEvent from given XMLStreamReader using XMLEventAllocator */
    private static XMLEvent getXMLEvent(XMLStreamReader reader) throws XMLStreamException{
        return allocator.allocate(reader);
    }    
    
}
