/*
 * Created on 5 juin 2005
 *
 */
package dInterface.selectiveSchedule.relationTesters;

import dInterface.selectiveSchedule.relationTesters.RelationTesterTemplate;
import dInterface.selectiveSchedule.relationTesters.RelationTester_ActivityEvent;

/**
 * Un Tuple est utilis� par SelectiveScheduleManager afin d'associer
 * un tuple de types d'�l�ments � son RelationTester appropri�.
 *
 * Par exemple, un Tuple contenant une instance de Class du type
 * EventAttach et une instance de Class de Activity va permettre
 * d'identifier que le RelationTester � utiliser est
 * RelationTester_ActivityEvent.
 *
 * @author Pascal
 *  
 */
public class Tuple {
    @SuppressWarnings("unchecked")
	private Class[] _elements = null;

    public Tuple() {
    	super();
    }
    
    @SuppressWarnings("unchecked")
	public Tuple(Class[] elements) {
        _elements = elements;
    }
    
    @SuppressWarnings("unchecked")
	public void setTuple(Class[] elements) {
        _elements = elements;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @SuppressWarnings("unchecked")
	public boolean equals(Object obj) {
        if (!(obj instanceof Tuple))
            return false;

        Tuple otherTuple = (Tuple) obj;

        Class[] otherTupleElements = null;

        try {
            otherTupleElements = (Class[]) otherTuple.getClass()
                    .getDeclaredField("_elements").get(otherTuple);
        } catch (IllegalArgumentException e) {
            System.err.println(e);
            return false;
        } catch (IllegalAccessException e) {
            System.err.println(e);
            return false;
        } catch (NoSuchFieldException e) {
            System.err.println(e);
            return false;
        }

        if (_elements.length != otherTupleElements.length)
            return false;

        int nbMatchingElements = 0;
        for (int i = 0; i < otherTupleElements.length; ++i) {
            for (int j = 0; j < _elements.length; ++j) {
                if (otherTupleElements[i] == _elements[j]) {
                    ++nbMatchingElements;
                    break;
                }
            }
        }

        if (nbMatchingElements != _elements.length)
            return false;

        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        int hashcode = 0;
        for (int i = 0; i < _elements.length; ++i) {
            hashcode += _elements[i].toString().hashCode();
        }

        return hashcode;
    }

    public static void main(String[] args) {
        Tuple t1 = new Tuple();
        t1.setTuple(new Class[] { RelationTester_ActivityEvent.class,
                RelationTesterTemplate.class });
        Tuple t2 = new Tuple();
        t2.setTuple(new Class[] { RelationTesterTemplate.class,
                RelationTester_ActivityEvent.class });

        System.out.println("t1.equals(t2) = " + t1.equals(t2));
        System.out.println("t1.hashCode()=" + t1.hashCode() + " == t2.hashCode()="
                + t2.hashCode() + " = " + (t1.hashCode() == t2.hashCode()));
    }
}
