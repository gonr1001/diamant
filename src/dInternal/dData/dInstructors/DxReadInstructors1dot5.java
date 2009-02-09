package dInternal.dData.dInstructors;

import java.util.StringTokenizer;

import dConstants.DConst;
import dExceptions.DiaException;
import dInternal.DataExchange;
import dInternal.dData.DxAvailability;
import dInternal.dUtil.DXToolsMethods;

public class DxReadInstructors1dot5 implements DxInstructorsReader {

	private DataExchange _deInstructors;

	private int _nDays, _nPeriods;

	public DxReadInstructors1dot5(DataExchange de, int nDays, int nPeriods) {
		_deInstructors = de;
		_nDays = nDays;
		_nPeriods = nPeriods;
	}

	public DxSetOfInstructors readSetOfInstructors() throws DiaException {
		StringTokenizer st = new StringTokenizer(_deInstructors.getContents(),
				DConst.CR_LF);
		String token;

		int state = 0;
		int stateDispo = 1;
		int numberOfInstructors = 0;
		int countInstructor = 0;
		// Used to report line where error was found
		int currentLine =4;

		String instID = "";
		DxSetOfInstructors dxsoiInst = new DxSetOfInstructors();
		DxAvailability dxaAvaTemp = new DxAvailability();

		// As long as we dont reach the end of the string tokenizer
		// Reads all tokens in the file
		while (st.hasMoreElements()) {
			token = st.nextToken();
			currentLine++;

			// Finite state machine
			switch (state) {
			// Line indicating the number of instructors in the file
			case 0:
				try {
					numberOfInstructors = (new Integer(token.trim()))
							.intValue();
				} catch (NumberFormatException exc) {
					// _error = DConst.INST_TEXT1 + "\n" + DConst.INST_TEXT6;
					throw new DiaException(DConst.INVALID_NUMBER_OF_INSTRUCTORS
							+ exc.getMessage());
				}
				state = 1;
				break;

			// Line containing the name of the instructor
			case 1:
				// delimiters
				// Thus, if string is empty, 2 delimiters will be skipped and
				// the empty string wont appear
				if (token.length() == 0) {

					throw new DiaException("Line should contain name of the instructor !");
				}
				state = 2;
				stateDispo = 1;
				countInstructor++;

				instID = token;
				dxaAvaTemp = new DxAvailability();
				break;

			case 2:
				// extract a line that gives availability of a day
				String line = DXToolsMethods.getToken(token,
						DConst.AVAILABILITY_SEPARATOR, 0);
				StringTokenizer tokenDispo = new StringTokenizer(line);

				// Verifies that number of period per day was correctly
				// indicated
				if (tokenDispo.countTokens() != _nPeriods) {
				        throw new DiaException(DConst.INVALID_AVAILABILITY_AT
									+ currentLine);
				}

				// Verifies that every availability element is valid
				while (tokenDispo.hasMoreElements()) {
					String dispo = tokenDispo.nextToken();
					if (isValidDayAvailability(dispo)) {
						throw new DiaException(DConst.INVALID_AVAILABILITY_AT
										+ currentLine);
					}
				}

				// After line is validated, we add it to the availability
				dxaAvaTemp.addDayAvailability(line);

				stateDispo++;

				// Once we have nDays availability for an instructor, we are
				// ready to create it and step to next instructor
				if (stateDispo > _nDays) {
					dxsoiInst.addInstructor(instID, dxaAvaTemp);
					state = 1;
				}
				break;
			}
		}

		// Verifies if number of instructors indicated at the beginning of the
		// file was valid
		if (countInstructor != numberOfInstructors) {
			// _error = DConst.INST_TEXT1 + "\n" + DConst.INST_TEXT6;
			throw new DiaException(DConst.INVALID_NUMBER_OF_INSTRUCTORS);
		}

		return dxsoiInst;
	}

	private boolean isValidDayAvailability(String sDispo) {
		return (!sDispo.equalsIgnoreCase("1"))
				&& (!sDispo.equalsIgnoreCase("5"))
				&& (!sDispo.equalsIgnoreCase("2"));
	}

	@Override
	public int getLines() {
		System.out.println("DxReadInstructors1dot5.bad value -1");
		return -1;
	}
}
