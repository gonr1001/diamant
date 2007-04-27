/*
 * Created on 26 nov. 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package dInternal.dData.dActivities;

import dInternal.DObject;
import dInternal.DResource;
import dInternal.DSetOfResources;
import dInternal.dData.StandardCollection;

public class Section extends DObject {

	/** the bloc list */
	private DSetOfResources _blocList;

	private int _capacityLimit;

	/**
	 * Constructor
	 */
	public Section() {
		_blocList = new StandardCollection();
	}

	/**
	 * set activity visibility
	 * 
	 * @param boolean
	 *            the activity visibility
	 */
	public void setCapacityLimit(int c) {
		_capacityLimit = c;
	}

	/**
	 * get the activity visibility
	 * 
	 * @return boolean the activity visibility
	 */
	public int getCapacityLimit() {
		return _capacityLimit;
	}

	/**
	 * add a bloc object in the list
	 * 
	 * @param String
	 *            the ID of the bloc
	 * @return boolean result of the operation
	 */
	public boolean addUnity(String id) {
		Unity bloc = new Unity();
		return _blocList.addResource(new DResource(id, bloc), 1);
	}

	/**
	 * add a bloc object in the list
	 * 
	 * @param String
	 *            the ID of the bloc
	 * @param int
	 *            the number of cycle in de TTStructure
	 * @return boolean result of the operation
	 */
	public boolean addUnity(String id, int NumberOfCycle,
			boolean isManualCreated) {
		_blocList.sortSetOfResourcesByID();
		Assignment cycleAss = new Assignment();
		cycleAss.setPeriodKey("1.1.1");
		Unity newUnity = new Unity();
		for (int i = 1; i <= NumberOfCycle; i++) {
			newUnity
					.addAssignment(new DResource(Integer.toString(i), cycleAss));
		}
		DResource newUnitResc = new DResource(id, newUnity);
		newUnitResc.setManuallyCreated(isManualCreated);
		return _blocList.addResource(newUnitResc, 1);
	}

	/**
	 * add a bloc object in the list
	 * 
	 * @param String
	 *            the ID of the bloc
	 * @param Bloc
	 *            the bloc to be added
	 * @return boolean result of the operation
	 */
	public boolean addUnity(String id, Unity unity) {
		return _blocList.addResource(new DResource(id, unity), 1);
	}

	/**
	 * remove a bloc object from de list
	 * 
	 * @param String
	 *            the ID of the bloc
	 * @return boolean result of the operation
	 */
	public boolean removeUnity(String id) {
		return _blocList.removeResource(id);
	}

	/**
	 * set a bloc object in the list
	 * 
	 * @param Resource
	 *            the bloc resource
	 * @return boolean result of the operation
	 */
	public boolean setUnity(DResource section) {
		return _blocList.setResource(section);
	}

	/**
	 * return a bloc object from de the list
	 * 
	 * @param String
	 *            the ID of the bloc
	 * @return Resource the nature object
	 */
	public DResource getUnity(String id) {
		return _blocList.getResource(id);
	}

	/**
	 * return a bloc object from de the list
	 * 
	 * @param String
	 *            the ID of the bloc
	 * @return Resource the nature object
	 */
	public DSetOfResources getSetOfUnities() {
		return _blocList;
	}

	/**
	 * This object (which is already a string!) is itself returned.
	 * 
	 * @return the string itself
	 */
	public String toWrite() {
		return _blocList.toWrite();
	}

	/**
	 * compare this resource with the specified resource
	 * 
	 * @param resource
	 *            the specified resource
	 * @return bolean true if this resource and the specified resource are
	 *         equals false if they are not equals
	 */
	public boolean isEquals(DObject sec) {
		Section section = (Section) sec;
		if (!this._blocList.isEquals(section._blocList))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dInternal.DObject#getSelectedField()
	 */
	public long getSelectedField() {
		// TODO Auto-generated method stub
		return 0;
	}

}