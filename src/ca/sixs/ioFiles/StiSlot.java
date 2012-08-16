/**
 * #rep
 * This comment must be replaced by
 * a copyright or copy left allowing to
 * distribute the code as open source 
 *
 * the prefix for the packages is
 * ca.sixs.
 *
 * 
 */
package ca.sixs.ioFiles;

import java.util.HashMap;

/**
 * @author gonr1001
 *
 */
public class StiSlot implements SlotConst {

	public StiSlot(HashMap<Integer, String> hm) {
		_day = hm.get(DAY);
		_begin = hm.get(BEGIN);
		_end = hm.get(END);
		_fixed = hm.get(FIXED);
		_room = hm.get(ROOM);
		_room_fixed = hm.get(ROOM_FIXED);

	}

	public String getDay() {
		return this._day; 		
	}

	public String getBegin() {
		return this._begin;
	}

	public String getEnd() {
		return this._end;
		
	}

	public String getFixed() {
		return this._fixed;
	}
	
	public String getRoom() {
		return this._room;
	}
	
	public String getRoomFixed() {
		return this._room_fixed; 
	}

	private String _day;
	private String _begin;
	private String _end;
	private String _fixed;
	private String _room;
	private String _room_fixed;

}