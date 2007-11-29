/**
 *
 * Title: DApplication
 *
 * Description: DApplication is a class used to display the application GUI,
 *              The class creates the main window, and menuBar, and toolBar,
 *              and the logger
 *
 *
 * Copyright (c) 2001 by rgr.
 * All rights reserved.
 *
 *
 * This software is the confidential and proprietary information
 * of rgr. ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with rgr.
 *
 *
 *
 */
package dInterface;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.StringTokenizer;
import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;
import java.util.prefs.Preferences;

import javax.swing.DefaultDesktopManager;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import dConstants.DConst;
import developer.DxFlags;
import dInterface.dAffectation.ActivityDlg;
import dInterface.dAffectation.ActivityModifDlg;
import dInterface.dAffectation.SectionDlg;
import dInterface.dAlgorithms.PersonalizeMixingAlgorithmDlg;
import dInterface.dAssignementDlgs.DxActivityDlg;
import dInterface.dAssignementDlgs.DxEventsDlg;
import dInterface.dAssignementDlgs.DxInstructorAvailabilityDlg;
import dInterface.dAssignementDlgs.DxRoomAvailabilityDlg;
import dInterface.dData.DefFilesToImportDlg;
import dInterface.dData.ImportDlg;
import dInterface.dData.ImportSelectiveFileDlg;
import dInterface.dData.ReportsDlg;
import dInterface.dFileMenuDlgs.NewTimeTableDlg;
import dInterface.dFileMenuDlgs.OpenTimeTableDlg;
import dInterface.dMenus.DxMenuBar;
import dInterface.dPreferencesDlgs.DxPLAFDlg;
import dInterface.dTimeTable.ConflictsOfAnEventDlg;
import dInterface.dTimeTable.OpenTTSDlg;
import dInterface.dTimeTable.SaveAsTTDlg;
import dInterface.dUtil.AboutDlg;
import dInterface.dUtil.ConflictDlg;
import dInterface.dUtil.PLAFDlg;
import dInterface.selectiveSchedule.dialog.SelectiveScheduleDlg;
import dInternal.DModel;
import dInternal.dOptimization.DxAssignAllAlg;
import dInternal.dOptimization.DxAssignRoomsAlg;
import dInternal.dOptimization.SelectAlgorithm;
import eLib.exit.dialog.DxExceptionDlg;
import eLib.exit.dialog.FatalProblemDlg;
import eLib.exit.dialog.InformationDlg;
import eLib.exit.exception.DxException;

public class DApplication {

	private static final String LOGO_PATH = "images/logoDia.gif";
	private static final String LOGO_DESCRIPTION = "logoDiamant";
	// singleton: has only one instance
	private static int instanceNumber = 0;
	private static DApplication _instance = null;

	// DApplication is a singleton
	public static DApplication getInstance() {
		if (instanceNumber == 0) {
			instanceNumber++;
			_instance = new DApplication();
		}
		return _instance;
	}

	private static Logger _logger = Logger.getLogger(DApplication.class
			.getName());

	public static boolean _inDevelopment;

	// ZERO fix Frame Location (origin) 
	private final int ZERO = 0;

	
	 // ADJUST_WIDTH adjust the screenSize minus border pixels (the
	 // value is a guess) at each side of the screen
	private final int ADJUST_WIDTH = 6;

	private final int SCREEN_MIN_HEIGHT = 512;

	private final int SCREEN_MIN_WIDTH = 512;
	
	// -d" option in the command line means debug
	private final String DEV = "-d";

	private JFrame _jFrame;

	private JDesktopPane _jDesktopPane;

	private DxPreferences _dxPreferences;

	//	private Preferences _preferences;

	private DMediator _dMediator;

	private String _currentDir;

	private String _fileToOpenAtStart;

	private DxMenuBar _dxMenuBar;

	private DToolBar _toolBar;

	private boolean _increase;

	private boolean _best;

	/**
	 * DApplication initialize the data members
	 */

	public DApplication() {
		PropertyConfigurator.configureAndWatch("trace" + File.separator
				+ "log4j.conf");
		_logger.warn("Hi from DApplication");
		_inDevelopment = false;
		_best = true;
		_increase = false;
		_fileToOpenAtStart = "";
		String str = System.getProperty("user.home") + File.separator + "pref"
				+ File.separator + "pref.txt";
		System.out.println("Preference file is in :" + str);
		_dxPreferences = new DxPreferences(str);
		//		_preferences = Preferences.userRoot().node("/com.dinc/exit/diamant");
	}

	public void doIt(String[] args) {
		if (args.length > 0) {
			lookUpforOptions(args); // args came from the command line
		}
		String str = System.getProperty("user.home") + File.separator + "pref"
				+ File.separator + "pref.txt";
		System.out.println("Preference file is in :" + str);
		_dxPreferences = new DxPreferences(str);
		_dMediator = new DMediator(this);
		_currentDir = System.getProperty("user.dir");
		_jFrame = createFrame(DConst.APP_NAME + "   " + DConst.V_DATE);
		if (DxFlags.newPref) {
			setLAF(getLAFFromPref());
		} else {
			setLAF(_dxPreferences._lookAndFeel);
		}
		if (_inDevelopment) {
			tryOpenDevFile();
		}
		_logger.warn("bye_from DApplication"); // at the end of an execution
	}

	private String getLAFFromPref() {
		boolean visible = false;
		DxPLAFDlg dlg = new DxPLAFDlg(this, visible);
		String str = dlg.getLookAndFeelFromPref();
		dlg.dispose();
		return str;
	}

	private void lookUpforOptions(String[] args) {
		if (args[0].compareTo(DEV) == 0) {
			_inDevelopment = true;
			System.out.println("I am in Mode Development");
		}
		if (args.length == 2) {
			// it contains a file name
			_fileToOpenAtStart = args[1];
		}
		// all other arguments are ignored
	}

	// -------------------------------------------
	private JFrame createFrame(String str) {
		JFrame jFrame = new JFrame(str + "   " + System.getProperty("user.dir"));
		jFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		jFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				e.toString(); //to avoid warning
				closeApplic();
			}
		});

		JPanel panel = new JPanel(new BorderLayout(0, 0));
		jFrame.setContentPane(panel);
		_dxMenuBar = new DxMenuBar(this); // constructs the menu bar
		jFrame.setJMenuBar(_dxMenuBar);

		_toolBar = new DToolBar(this); // constructs the tool bar
		jFrame.getContentPane().add(_toolBar, BorderLayout.NORTH);
		_toolBar.updateUI();
		hideToolBar();

		_jDesktopPane = new JDesktopPane();
		_jDesktopPane.setOpaque(false);
		_jDesktopPane.setDesktopManager(new DefaultDesktopManager());
		panel.add(_jDesktopPane, BorderLayout.CENTER);

		jFrame.setLocation(ZERO, ZERO);
		panel.setMinimumSize(new Dimension(SCREEN_MIN_WIDTH, SCREEN_MIN_HEIGHT));
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		panel.setMaximumSize(screenSize);

		if (_inDevelopment) {
			panel.setPreferredSize(new Dimension(screenSize.width
					- ADJUST_WIDTH, 500));
		} else {
			panel.setPreferredSize(new Dimension(screenSize.width
					- DConst.ADJUST_WIDTH, screenSize.height
					- DConst.ADJUST_HEIGHT));
		}
		/* Application Icon */
		// ImageIcon iconeDiamant
		ImageIcon diamantIcon = createImageIcon(LOGO_PATH, LOGO_DESCRIPTION);
		if (diamantIcon != null) {
			jFrame.setIconImage(diamantIcon.getImage());
		}
		jFrame.pack();
		jFrame.setVisible(true);
		return jFrame;
	} // end createUI

	public JDesktopPane getDesktop() {
		return _jDesktopPane;
	} // end getDesktop

	public JFrame getJFrame() {
		return _jFrame;
	} // end getJFrame

	public DMediator getDMediator() {
		return _dMediator;
	} // end getDMediator

	public DxDocument getCurrentDxDoc() {
		return _dMediator.getCurrentDxDoc();
	}

	public String saveCurrentDxDoc(String str) {
		return _dMediator.saveCurrentDxDoc(str);
	} // end getCurrentDoc
	
	public void closeCurrentDxDoc() {
		_dMediator.closeCurrentDxDoc();
	} // end getCurrentDoc

	public DxMenuBar getDxMenuBar() {
		return _dxMenuBar;
	} // end getDxMenuBar

	public DToolBar getToolBar() {
		return _toolBar;
	}

	public DxPreferences getDxPreferences() {
		return _dxPreferences;
	} // end getPreferences

	public String getCurrentDir() {
		return _currentDir;
	} // end getCurrentDir

	/*
	 * the str can contain a file name, it will be left out
	 */
	public void setCurrentDir(String str) {
		_currentDir = str.substring(0, str.lastIndexOf(File.separator) + 1);
	} // end setCurrentDir

	public void showToolBar() {
		_toolBar.setVisible(true);
	}

	public void hideToolBar() {
		_toolBar.setVisible(false);
	}

	// -------------------------------------------
	public void setLAF(String str) {
		// Force SwingApp to come up in the System L&F
		try {
			UIManager.setLookAndFeel(str);
			System.out.println("pref: " + str);
			UIManager.setLookAndFeel(str);
			System.out.println("pref: " + str);
		} catch (UnsupportedLookAndFeelException ulafe) {
			new FatalProblemDlg("UnsupportedLookAndFeel: " + str);
			System.err.println("Warning: UnsupportedLookAndFeel: " + str);
			ulafe.printStackTrace();
			System.exit(1);
		} catch (ClassNotFoundException cnfe) {
			new FatalProblemDlg("Error ClassNotFound LookAndFeel" + str);
			System.err.println("Error ClassNotFound LookAndFeel" + str);
			cnfe.printStackTrace();
			System.exit(1);
		} catch (IllegalAccessException iace) {
			new FatalProblemDlg("Error IllegalAccess LookAndFeel" + str);
			System.err.println("Error IllegalAccess LookAndFeel" + str);
			iace.printStackTrace();
			System.exit(1);
		} catch (InstantiationException ie) {
			new FatalProblemDlg("Error Instantiation LookAndFeel" + str);
			System.err.println("Error Instantiation LookAndFeel" + str);
			ie.printStackTrace();
			System.exit(1);
		}
	} // end setLF

	// -------------------------------------------
	/**
	 * This method updates the look and feel style
	 * 
	 * @param String
	 *            A look and feel style
	 */
	public void updateLAF(String str) {
		setLAF(str);
		SwingUtilities.updateComponentTreeUI(_jFrame);
	}


	/**
	 * Closes the document(s) and the application. Use this method for
	 * processing close via the exit menuItem.
	 * 
	 * @return void
	 * 
	 */
	public void closeApplic() {
		// while documents to close, close them
		while (this.getCurrentDxDoc() != null) { // is a while
			this.close(); 
			// but if the user cancel break
			if (_dMediator.getCancel())
				break;
		}
		// if there is no Document open exit the application
		if (_dMediator.getCurrentDxDoc() == null) {
			_jFrame.setVisible(false);
			_jFrame.dispose();
			System.exit(0);
		}
	} // end exit

	public DModel getCurrentDModel() {
		return getCurrentDxDoc().getCurrentDModel();
	}

	public void setCursorWait() {
		if (_jFrame != null)
			_jFrame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	}

	public void setCursorDefault() {
		if (_jFrame != null)
			_jFrame
					.setCursor(Cursor
							.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}



	/*
	 * In File menu
	 */
	
	/**
	 * 
	 */
	public void newTTable() {
		buildTTable(DConst.CYCLE);
	}
	
	/**
	 * 
	 */
	public void newTTableExam() {
		buildTTable(DConst.EXAM);
	}

	/**
	 * 
	 */
	public void newTTStrucCycle() {
		buildTTStruc(this.getDxPreferences()._standardTTC);
	}

	/**
	 * 
	 */
	public void newTTStrucExam() {
		buildTTStruc(this.getDxPreferences()._standardTTE);
	}


	/**
	 * 
	 */
	public void openTTable() {
		buildTTable();
	}

	/**
	 * 
	 */
	public void openTTStruc() {		
		buildTTStruc();
//		this.showToolBar();
//		new OpenTTSDlg(this);
//		_dxMenuBar.afterNewTTStruc();
//		this.afterOpenTTSruc();
	}

	/**
	 * 
	 */
	public void afterOpenTTSruc() {
		_dxMenuBar.afterOpenTTSruc();
	}

	/**
	 * 
	 */
	public void close() {
		this._dMediator.closeCurrentDxDoc();
		if (!this._dMediator.getCancel()) {
			_dxMenuBar.initialState();
		}
	}

	/**
	 * 
	 */
	public void save() {
		if (this.getCurrentDxDoc().getDocumentName().endsWith(DConst.NO_NAME))
			new SaveAsTTDlg(this);
		else if (this.getCurrentDxDoc().isModified())
			this._dMediator.saveCurrentDxDoc(this.getCurrentDxDoc()
					.getDocumentName());
	}

	/**
	 * 
	 */
	public void saveAs() {
		new SaveAsTTDlg(this);
	}

	/**
	 * 
	 */
	public void defineFiles() {
		new DefFilesToImportDlg(this);
	}

	/**
	 * 
	 */
	public void importFiles() {
		new ImportDlg(this);
	}

	public void doImport(String fil) {
		try {
			String error = "";
			this.setCursorWait();

			if (this.getCurrentDxDoc() != null) {
				error = this.getCurrentDxDoc().getCurrentDModel().importData(
						fil);
			} else
				error = "ImportDlg : Il n'existe pas de document pour effectuer l'importation des données";
			if (error.length() == 0) {
				new InformationDlg(this.getJFrame(), DConst.IMP_A_SUC);
			} else {
				throw new DxException(error);
			}
			this.setCursorDefault();
		} catch (DxException e) {
			new DxExceptionDlg(e.getMessage(), e);
			System.exit(-1);
		}
	}

	/**
	 * 
	 */
	public void afterImport() {
		_dxMenuBar.afterImport();
	}

	/**
	 * 
	 */
	public void exportFiles() {

		String dir = getTokenDir(this.getCurrentDxDoc().getDocumentName(),
				File.separator);

		File fileStu = new File(dir + DConst.TT_STUD_FILE);
		File fileTT = new File(dir + DConst.TT_FILE);
		String mess = "";
		if (fileStu.exists() || fileTT.exists()) {
			mess += "Un ou les deux fichiers existent dans le répertoire"
					+ DConst.CR_LF;
			mess += "PAS d'exportation";
			new InformationDlg(this.getJFrame(), mess, DConst.EXPORT_MESSAGE);
		} else { // if (fileStu.exists() || fileTT.exists())
			this.getCurrentDModel().exportData(dir);
			mess += dir + DConst.TT_STUD_FILE + DConst.CR_LF + dir
					+ DConst.TT_FILE + DConst.CR_LF + DConst.EXPORTED;
			new InformationDlg(this.getJFrame(), mess, DConst.EXPORT_MESSAGE);
		}
	}

	/**
	 * return a token in from a stringTokenizer
	 * 
	 * @param str
	 * @param delimiter
	 * @param position
	 * @return
	 */
	private String getTokenDir(String str, String delimiter) {
		StringTokenizer strToken = new StringTokenizer(str, delimiter);
		String string = "";
		int nbTokens = strToken.countTokens();
		for (int i = 0; i < nbTokens - 1; i++) {
			string += strToken.nextToken() + delimiter;
		}
		return string;
	}

	/**
	 * 
	 */
	public void assignActivities() {
		if (DxFlags.newActivity) {
			new DxActivityDlg(this.getJFrame(), this.getCurrentDModel());
		} else {
			if (DxFlags.newDxEditEventDlg) {
				new ActivityDlg(this.getJFrame(), this.getCurrentDModel());
			} else {
				new ActivityDlg(this.getJFrame(), this.getCurrentDModel());
			}
		}
	}

	/**
	 * 
	 */
	public void assignSections() {
		new SectionDlg(this);
	}

	/**
	 * 
	 */
	public void instructorAvailability() {
		new DxInstructorAvailabilityDlg(this, this.getCurrentDxDoc()
				.getCurrentDModel().getDxSetOfInstructors());
	}

	/**
	 * 
	 */
	public void roomAvailability() {
		// if (DxFlags.newRooms) {
		new DxRoomAvailabilityDlg(this);
		// new DxAvailabiltyRoomDlg(this, this.getCurrentDModel()
		// .getDxSetOfRooms(), DConst.ROOMASSIGN);
		// !!!NIC!!! How do we verify if it's multisite?
		// new DxRoomAvailabilityDlg(this, this.getCurrentDModel()
		// .getDxSetOfSites());
		// } else {
		// new AvailabiltyRoomDialog(this, this.getCurrentDModel()
		// .getSetOfRooms(), DConst.ROOMASSIGN);
		// }
	}

	/**
	 * 
	 */
	public void assignEvents() {
		if (DxFlags.newDxEditEventDlg) {
			new DxEventsDlg(this.getJFrame(), this.getCurrentDModel());
		} 
//		else {
//			new EventsDlg(this.getJFrame(), this.getCurrentDModel());
//		}
	}

	/**
	 * 
	 */
	public void selectiveViews() {
		SelectiveScheduleDlg.getInstance().displayDlg();
	}

	/**
	 * 
	 */
	public void conflictsOfAnEvent() {
		new ConflictsOfAnEventDlg(this.getJFrame(), this.getCurrentDModel(),
				DConst.CONFLICTS_OF_AN_EVENT_DLG_TITLE);
	}

	/**
	 * 
	 */
	public void modifyActivity() {
		new ActivityModifDlg(this.getJFrame(), this.getCurrentDModel());
	}

	/**
	 * 
	 */
	public void mergeInstructors() {
		new ImportSelectiveFileDlg(this, DConst.IMP_SELECT_INST);
	}

	/**
	 * 
	 */
	public void mergeRooms() {
		new ImportSelectiveFileDlg(this, DConst.IMP_SELECT_ROOM);
	}

	/**
	 * 
	 */
	public void mergeActivities() {
		new ImportSelectiveFileDlg(this, DConst.IMP_SELECT_ACT);
	}

	/**
	 * 
	 */
	public void mergeStudents() {
		new ImportSelectiveFileDlg(this, DConst.IMP_SELECT_STUD);
	}

	/**
	 * 
	 */
	public void initialAssignment() {
		this.getCurrentDModel().changeInDModel(this.getJFrame());
		_dxMenuBar.afterInitialAssignment();
		new InformationDlg(this.getJFrame(), DConst.INITIAL_ASSIGN_MESSAGE);
	}

	/**
	 * 
	 */
	public void afterInitialAssign() {
		_dxMenuBar.afterInitialAssignment();
	}

	/**
	 * 
	 */
	public void doTheTimeTable() {
		this.setCursorWait();

		if (DxFlags.newAlg) {
			new DxAssignAllAlg(this.getCurrentDModel(), this.getDxPreferences()
					.getDxConflictLimits()).doWork();

		} else {
			int _selectedContext = 0;
			(new SelectAlgorithm(this.getCurrentDModel(), _selectedContext))
					.execute();
		}

		this.setCursorDefault();
		new InformationDlg(this.getJFrame(), DConst.TT_BUILD_MESSAGE);
	}

	/**
	 * 
	 */
	public void doSectionPartition() {
		// boolean _userTestActiv = true;
		//
		// DConst.USER_TEST_ACTIV = _userTestActiv;
		PersonalizeMixingAlgorithmDlg perso = new PersonalizeMixingAlgorithmDlg(
				DConst.DEFAULT_MIX_ALGO);
		String input = perso.showInputDialog();
		if (input != null) {
			int personalizeAcceptableVariation = Integer.parseInt(input);
			(new SelectAlgorithm(personalizeAcceptableVariation, this
					.getCurrentDModel())).execute();
			new InformationDlg(this.getJFrame(), DConst.STUDENTS_MIXING_MESSAGE);
		}

	}

	/**
	 * 
	 */
	public void report() {
		new ReportsDlg(this);
	}

	/**
	 * 
	 */
	public void showPLAFDlg() {
		if (DxFlags.newPref) {
			new DxPLAFDlg(this, true);
		} else {
			new PLAFDlg(this);
		}
	}

	public void addPrefsListener(Preferences pref) {
		//_preferences
		pref.addPreferenceChangeListener(new PreferenceChangeListener() {
			public void preferenceChange(PreferenceChangeEvent pce) {
				String str = pce.getNewValue();
				System.out.println("Change: (" + pce.getNode() + ") key= "
						+ pce.getKey() + "   value = " + str);
				updateLAF(str);
			}
		});
	}

	/**
	 * 
	 */
	public void showConflictsDlg() {
		new ConflictDlg(this);
	}

	/**
	 * 
	 */
	public void simpleView() {
		if (this.getCurrentDxDoc() != null)
			this.getCurrentDxDoc().displaySimple();
	}

	/**
	 * 
	 */
	public void horizontalSplitView() {
		if (this.getCurrentDxDoc() != null)
			this.getCurrentDxDoc().displayHorizontalSplit();
	}

	/**
	 * 
	 */
	public void vericalSplitview() {
		if (this.getCurrentDxDoc() != null)
			this.getCurrentDxDoc().displayVericalSplit();
	}

	/**
	 * 
	 */
	public void showAboutDlg() {
		new AboutDlg(this.getJFrame());
	}

	/**
	 * 
	 */
	public void tryOpenDevFile() {
		if (!_fileToOpenAtStart.equalsIgnoreCase("")) {
			try {
				_dMediator.addDxTTableDoc("", _fileToOpenAtStart);
			} catch (DxException e) {
				new DxExceptionDlg(e.getMessage(), e);
			}
			getCurrentDxDoc().setAutoImportDIMFilePath(".\\devData\\");
			getCurrentDxDoc().getCurrentDModel().changeInDModel(
					this.getJFrame());
			_dxMenuBar.afterInitialAssignment();

		} else {
			setCurrentDir(".\\dataTest\\");
			// String fileName = "." + File.separator;
			// fileName += "dataTest" + File.separator;
			// fileName += "refFiles" + File.separator;
			// fileName += "facs" + File.separator;
			// fileName += "flsh2_1" + File.separator;
			// fileName += "RoomAffContTT.dia";
			try {
				String filepath = "." + File.separator;
				// filepath += "refFiles" + File.separator;
				// filepath += "facs" + File.separator;
				// filepath += "flsh2_1" + File.separator;
				filepath += "test2.dia";
				System.out.println("path: " + filepath);
				_dMediator.addDxTTableDoc("", getCurrentDir() + filepath);

			} catch (DxException e) {
				new DxExceptionDlg(e.getMessage(), e);
			}
			getCurrentDxDoc().setAutoImportDIMFilePath(".\\devData\\");
			getCurrentDxDoc().getCurrentDModel().changeInDModel(
					this.getJFrame());
			_dxMenuBar.afterInitialAssignment();
		}
	}

	/**
	 * 
	 */
	public void initialState() {
		_dxMenuBar.initialState();
	}

	/**
	 * 
	 */
	public void showAllMenus() {
		_dxMenuBar.showAllMenus();
	}

	/**
	 * 
	 */
	public void roomAssignment() {
		System.out.println("before call RAO" + _increase + " " + _best);
		new DxAssignRoomsAlg(this.getCurrentDModel(), this.getDxPreferences()
				.getDxConflictLimits(), _increase, _best).doWork();
		new InformationDlg(this.getJFrame(), DConst.ROOM_ASSIGN_MESSAGE);
	}

	/**
	 * 
	 */
	public void eventAssignment() {
			new DxEventsDlg(this.getJFrame(), this.getCurrentDModel());
	}

	/**
	 * 
	 */
	public void conflictOption() {
		new ConflictDlg(this);
	}

	/**
	 * 
	 */
	public boolean isMultiSite() {
		if (this.getCurrentDxDoc() == null) {
			return false;
		}
		return this.getCurrentDxDoc().getCurrentDModel().isMultiSite();
	}

	/**
	 * @param str
	 */
	public void changeInMulti(String str) {
		this.getCurrentDxDoc().getCurrentDModel().isMultiSite();
		if (str.equalsIgnoreCase(DConst.ALL_SITES))
			this.getCurrentDxDoc().getCurrentDModel().changeInDModel(
					this.getJFrame());
		else
			this.getCurrentDxDoc().getCurrentDModel().changeInDModel(
					this.getJFrame());
	}


	public void doImport(JFileChooser fc, String str, String dlg) {
		this.getCurrentDxDoc().setAutoImportDIMFilePath(
				fc.getSelectedFile().getAbsolutePath().substring(
						0,
						fc.getSelectedFile().getAbsolutePath().lastIndexOf(
								File.separatorChar) + 1));
		this.setCurrentDir(str);
		this.doImport(str);
		this.getCurrentDModel().setIsATimeTable();
		this.getCurrentDModel().changeInDModel(dlg);
		this.setCurrentDir(fc.getSelectedFile().getPath());
		this.afterImport();
	}

	/**
	 * @param d
	 * @param c
	 * @param b
	 * 
	 */
	public void roomAssignOptions(boolean best, boolean inc, boolean dec) {
		System.out.println("rAO" + best + " " + inc + " " + dec);
		if (best) {
			_best = true;
			_increase = false;
		} else {
			if (inc) {
				_best = false;
				_increase = true;
			} else {
				_best = false;
				_increase = false;
			}
		}

	}

	/**
	 * @return
	 */
	public boolean getBest() {
		return _best;
	}
	/**
	 * @return
	 */
	public static boolean isInDevelopment() {
		return _inDevelopment;
	}
	
	/**
	 * @param i timetable type
	 * 
	 */
	private void buildTTable(int i) {
		NewTimeTableDlg dlg = new NewTimeTableDlg();
		String pathfileName = dlg.getFileName(this, i);
		
		if (pathfileName == "") {// cancel button was pressed!
			dlg.dispose();
		} else {
			dlg.dispose();
			this.setCurrentDir(pathfileName);
			try {
				this.getDMediator().addDxTTableDoc(
						this.getCurrentDir() + DConst.NO_NAME, pathfileName);
				_dxMenuBar.afterNewTTable();
			} catch (DxException e) {
				new DxExceptionDlg(_jFrame, e.getMessage(), e);
			}
		}
	}
	
	/**
	 * @param i timetable type
	 * 
	 */
	private void buildTTable() {
		OpenTimeTableDlg dlg = new OpenTimeTableDlg();
		String fullFileName = dlg.getFileName(this);

		if (fullFileName == "") {// cancel button was pressed!
			
			this.initialState();
		} else {
			
			this.setCurrentDir(fullFileName);
			try {
				this.getDMediator().addDxTTableDoc(fullFileName, fullFileName);
				_dxMenuBar.afterNewTTable();
			} catch (DxException e) {
				new DxExceptionDlg(_jFrame, e.getMessage(), e);
			}catch (Exception e) {
				new DxExceptionDlg(_jFrame, e.getMessage(), e);
			} 
//			finally {
//				DMediator dMed = this.getDMediator();
//				dMed.clean();
//				dMed = null;
//				this.initialState();
//			}
			this.setCurrentDir(fullFileName);
			this.getCurrentDxDoc().changeInModel(this.getClass().toString());
			this.afterInitialAssign();			
		}
		dlg.dispose();

	}
	
	/**
	 * @param string indicates the type of timetable structure
	 * 
	 */
	private void buildTTStruc(String str) {
		this.showToolBar();
		this.setCursorWait();
		try {
			this._dMediator.addDxTTStructureDoc(str);
			_dxMenuBar.afterNewTTStruc();
		} catch (Exception e) {
			new DxExceptionDlg(this._jFrame, e.getMessage(), e);
			this.hideToolBar();
		}
		this.setCursorDefault();
	}
	
	/**
	 * @param string indicates the type of timetable structure
	 * 
	 */
	private void buildTTStruc() {
		this.showToolBar();
		this.setCursorWait();
		OpenTTSDlg dlg = new OpenTTSDlg();
		String fullFileName = dlg.getFileName(this);
		//dApplic.setCurrentDir(fil);
		
		if (fullFileName == "") {// cancel button was pressed!
			dlg.dispose();
			this.initialState();
		} else {
			dlg.dispose();
			//this.setCurrentDir(fullFileName);
			this.closeCurrentDxDoc();
			//this.hideToolBar();
			try {
				this._dMediator.addDxTTStructureDoc(fullFileName);
				_dxMenuBar.afterNewTTStruc();
			} catch (DxException e) {
				new DxExceptionDlg(_jFrame, e.getMessage(), e);
			} catch (Exception e) {
				new DxExceptionDlg(_jFrame, e.getMessage(), e);
			}
		}		
		this.setCursorDefault();
	}

	private ImageIcon createImageIcon(String path, String description) {
		java.net.URL imgURL = DApplication.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL, description);
		}
		System.err.println("Couldn't find file: " + path);
		return null;
	}
	


} /* end class DApplication */
