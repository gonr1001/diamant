package dInterface;

/**
 * <p>Title: Proto</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author ysyam
 * @version 1.0
 */

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;

import dInterface.dUtil.LongTask;

@SuppressWarnings("serial")
public class ProgressBar extends JPanel implements ActionListener {
	public final static int ONE_SECOND = 1000;

	private JProgressBar _jProgressBar;

	private Timer timer;

	private static int L_WIDHT = 300;

	private static int L_HEIGHT = 100;

	private JButton _startButton;

	private LongTask task;

	//private JTextArea taskOutput;
	private static String _title = "";

	//private String newline = "\n";
	private static DApplication _dApplic;

	private JDialog _jDialog = new JDialog();

	public ProgressBar(String title, DApplication dApplic) {
		super(new BorderLayout());
		task = new LongTask();
		_dApplic = dApplic;
		_title = title;
		//Create the demo's UI.
		_startButton = new JButton("Start");
		_startButton.setActionCommand("start");
		_startButton.addActionListener(this);

		_jProgressBar = new JProgressBar(0, task.getLengthOfTask());// XXXX Pascal: magic numbers
		_jProgressBar.setValue(0);// XXXX Pascal: magic numbers
		_jProgressBar.setStringPainted(true);
		JPanel panel = new JPanel();
		//panel.add(_startButton);
		panel.add(_jProgressBar);

		add(panel, BorderLayout.PAGE_START);
		//add(new JScrollPane(taskOutput), BorderLayout.CENTER);
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // XXXX Pascal: magic numbers

		//Create a timer.
		timer = new Timer(ONE_SECOND, new ActionListener() {
			@SuppressWarnings("synthetic-access")
			public void actionPerformed(ActionEvent e) {
				e.toString();
				//System.out.println("Timer event: "+evt);
				_jProgressBar.setValue(task.getCurrent());
				//String s = task.getMessage();
				if (task.isDone()) {
					Toolkit.getDefaultToolkit().beep();
					timer.stop();
					//_startButton.setEnabled(true);
					setCursor(null); //turn off the wait cursor
					//_jProgressBar.setValue(_jProgressBar.getMinimum());
					_jDialog.dispose();
				}
			}
		});

		_startButton.doClick();
	}

	/**
	 * Called when the user presses the start button.
	 */
	public void actionPerformed(ActionEvent e) {
		//System.out.println("Action performed event: "+evt);
		//_startButton.setEnabled(false);
		e.toString();
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		task.go();
		timer.start();
	}

	/**
	 * Create the GUI and show it.  For thread safety,
	 * this method should be invoked from the
	 * event-dispatching thread.
	 */
	private void createAndShowDialogGUI() {
		_jDialog = new JDialog(_dApplic.getJFrame(), _title, true);
		_jDialog.getContentPane().setLayout(new BorderLayout());
		_jDialog.setSize(new Dimension(L_WIDHT, L_HEIGHT));
		_jDialog.setResizable(true);
		//buildVectors();
		//dialog.setPanels();
		_jDialog.getContentPane().add(new ProgressBar(_title, _dApplic),
				BorderLayout.CENTER);
		_jDialog.setLocationRelativeTo(_dApplic.getJFrame());
		_jDialog.setVisible(true);
	}

	/**
	 * execute progress bar
	 * Schedule a job for the event-dispatching thread:
	 * creating and showing this application's GUI.
	 */
	public void execute() {
		createAndShowDialogGUI();
	}

	/**
	 * close the progress bar
	 */
	public void close() {
		_jDialog.dispose();
	}

}