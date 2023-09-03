package appMain;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wb.swt.SWTResourceManager;

import ga_training.SelectionChooser;
import ga_training.TrainInfor;
import ga_training.GATrainer;

import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.widgets.Combo;

public class FindZeroPRLMainWindow {

	protected Shell shlGaTrainer;
	GATrainer trainer;
	private Label lblValue_2;
	private Label lblValue;
	private Label lblValue_1;
	private Label label;
	private Label label_1;
	private Label label_Varat;
	private Label label_Varat_1;
	private Label label_Varat_1_1;
	private Label lblValue_3;
	private Label label_Varat_1_1_1;
	private Label lblValue_3_1;
	private Scale scale_4;
	private Scale scale_1_1;
	private Scale scale_1_1_1;
	private Scale scale_1_1_1_1;
	private Composite composite_3;
	private Label label_Varat_1_2;
	private Text textSolution;

	Double solution;
	private Label label_currErr;
	private Label label_SoluErr;
	private Spinner spinner_2;
	private Label lblValue_1_1_1;
	private Label label_Varat_2;
	private Label lblAverage;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			FindZeroPRLMainWindow window = new FindZeroPRLMainWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlGaTrainer.open();
		shlGaTrainer.layout();
		while (!shlGaTrainer.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlGaTrainer = new Shell();
		shlGaTrainer.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				System.exit(0);
			}
		});
		shlGaTrainer.setMinimumSize(new Point(800, 480));
		shlGaTrainer.setImage(SWTResourceManager.getImage(FindZeroPRLMainWindow.class, "/newt/data/jogamp-32x32.png"));
		shlGaTrainer.setLocation(50, 50);
		shlGaTrainer.setSize(1070, 660);
		shlGaTrainer.setText("GA trainer");
		shlGaTrainer.setLayout(new GridLayout(2, false));
		trainer = new GATrainer();

		ToolBar toolBar = new ToolBar(shlGaTrainer, SWT.FLAT | SWT.RIGHT);
		toolBar.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));

		ToolItem tltmLog = new ToolItem(toolBar, SWT.NONE);
		tltmLog.setText("data");

		ToolItem tltmPopulationIndex = new ToolItem(toolBar, SWT.NONE);
		tltmPopulationIndex.setText("Population Index");

		ToolItem tltmInout = new ToolItem(toolBar, SWT.NONE);
		tltmInout.setText("Input");

		ToolItem tltmOutput = new ToolItem(toolBar, SWT.NONE);
		tltmOutput.setText("Output");

		Composite composite = new Composite(shlGaTrainer, SWT.BORDER);
		composite.setLayout(new GridLayout(6, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));

		Label lblFirstclasssize = new Label(composite, SWT.NONE);
		lblFirstclasssize.setText("firstClass\r\nsize:");

		final Scale scale_3 = new Scale(composite, SWT.NONE);
		GridData gd_scale_3 = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_scale_3.widthHint = 350;
		scale_3.setLayoutData(gd_scale_3);
		scale_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int data = scale_3.getSelection();
				trainer.setFirstClasssize(data);
				lblValue_2.setText(String.valueOf(data));
			}
		});
		scale_3.setMaximum(250000);
		scale_3.setMinimum(1500);
		scale_3.setSelection(trainer.getFirstClasssize());

		lblValue_2 = new Label(composite, SWT.NONE);
		lblValue_2.setText("" + trainer.getFirstClasssize());

		Label lblsepar = new Label(composite, SWT.SEPARATOR | SWT.VERTICAL);
		GridData gd_lblsepar = new GridData(SWT.CENTER, SWT.FILL, false, false, 1, 4);
		gd_lblsepar.widthHint = 25;
		lblsepar.setLayoutData(gd_lblsepar);
		lblsepar.setText("asd");

		Group grpCandidate = new Group(composite, SWT.NONE);
		grpCandidate.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 2));
		grpCandidate.setText("Pop Paramater");
		grpCandidate.setLayout(new GridLayout(5, false));

		Label lblNumofchild = new Label(grpCandidate, SWT.NONE);
		lblNumofchild.setText("numOf\r\nChild: ");

		final Scale scale_2 = new Scale(grpCandidate, SWT.VERTICAL);
		GridData gd_scale_2 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_scale_2.heightHint = 50;
		scale_2.setLayoutData(gd_scale_2);
		scale_2.setMaximum(5);
		scale_2.setMinimum(1);
		scale_2.setSelection(trainer.getNumOfChild());

		label = new Label(grpCandidate, SWT.NONE);
		GridData gd_label = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_label.widthHint = 25;
		label.setLayoutData(gd_label);
		label.setText("" + trainer.getNumOfChild());

		Label lblLenOfDan = new Label(grpCandidate, SWT.NONE);
		lblLenOfDan.setText("Len of DNA: ");

		spinner_2 = new Spinner(grpCandidate, SWT.BORDER);
		spinner_2.setMaximum(250);
		spinner_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				trainer.setLenOfGen(spinner_2.getSelection());
			}
		});
		spinner_2.setSelection(trainer.getAiEvolution().getLenOfGen());

		Label lblPlannchild = new Label(grpCandidate, SWT.NONE);
		lblPlannchild.setText("planNmOfChl: ");

		final Spinner spinner = new Spinner(grpCandidate, SWT.BORDER);
		spinner.setMaximum(8);
		spinner.setMinimum(2);
		spinner.setSelection(5);
		spinner.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				trainer.setNChildPlan(spinner.getSelection() / 10);
			}
		});
		new Label(grpCandidate, SWT.NONE);

		final Button btnNatureSelection = new Button(grpCandidate, SWT.CHECK);
		btnNatureSelection.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		btnNatureSelection.setSelection(trainer.getNaturalFitnessScores());
		btnNatureSelection.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				trainer.setNaturalFitnessScores(btnNatureSelection.getSelection());
//				System.out.println("btnNatureSelection.getSelection(): " + btnNatureSelection.getSelection());
			}
		});
		btnNatureSelection.setText("Nature selection");
		scale_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int data = scale_2.getSelection();
				trainer.setNumOfChild(scale_2.getSelection());
				label.setText(String.valueOf(data));
			}
		});

		Group grpCandidate_1 = new Group(composite, SWT.NONE);
		grpCandidate_1.setLayout(new GridLayout(2, false));
		grpCandidate_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 2));
		grpCandidate_1.setText("Accelerater");

		final Button btnCPUprioritize = new Button(grpCandidate_1, SWT.CHECK);
		btnCPUprioritize.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				trainer.setCPUprioritize(btnCPUprioritize.getSelection());
			}
		});
		btnCPUprioritize.setText("CPU Prioritize");
		btnCPUprioritize.setSelection(trainer.getCPUprioritize());
		new Label(grpCandidate_1, SWT.NONE);

		Label lblChooseSelector = new Label(grpCandidate_1, SWT.NONE);
		lblChooseSelector.setText("Choose Selector");

		final Combo combo = new Combo(grpCandidate_1, SWT.READ_ONLY);
		combo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int selected = combo.getSelectionIndex();
				System.out.println("selected : " + selected);
				switch (selected) {
				case 0:
					trainer.setSelectorValue(SelectionChooser.COINFLIPGAMESELECTION);
					break;
				case 1:
					trainer.setSelectorValue(SelectionChooser.DICEGAMESELECTION);
					break;
				case 2:
					trainer.setSelectorValue(SelectionChooser.REMAKEROULETTEWHEELSELECTION);
					break;
				case 3:
					trainer.setSelectorValue(SelectionChooser.ROULETTEWHEELSELECTION);
					break;

				default:
					trainer.setSelectorValue(SelectionChooser.SORTSELECTION);
					break;
				}
			}
		});
		combo.setItems(new String[] { "CoinFlip Selection", "DiceGame Selection", "RemakeRoulette Selection",
				"Roulette Selection", "Sort Selection" });
		GridData gd_combo = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_combo.widthHint = 100;
		combo.setLayoutData(gd_combo);
		combo.select(0);
		Label lblMinPopSize = new Label(composite, SWT.NONE);
		lblMinPopSize.setText("minimum \r\npop size:");

		final Scale scale_3_2 = new Scale(composite, SWT.NONE);
		scale_3_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		scale_3_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				trainer.setMinimumPopsize(scale_3_2.getSelection());
				lblValue_3.setText(String.valueOf(scale_3_2.getSelection()));
			}
		});
		scale_3_2.setMaximum(500000);
		scale_3_2.setMinimum(500);
		scale_3_2.setSelection(trainer.getMinimumPopsize());

		lblValue_3 = new Label(composite, SWT.NONE);
		lblValue_3.setText("" + trainer.getMinimumPopsize());

		Label lblMaxPopSize_1 = new Label(composite, SWT.NONE);
		lblMaxPopSize_1.setText("maximum \r\npop size:");

		final Scale scale_3_2_1 = new Scale(composite, SWT.NONE);
		scale_3_2_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int maximunPopsize = scale_3_2_1.getSelection();
				trainer.setMaximunPopsize(maximunPopsize);
				lblValue_3_1.setText("" + maximunPopsize);
			}
		});
		scale_3_2_1.setIncrement(10);
		scale_3_2_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		scale_3_2_1.setMaximum(150000);
		scale_3_2_1.setMinimum(35000);
		scale_3_2_1.setSelection(trainer.getMaximunPopsize());

		lblValue_3_1 = new Label(composite, SWT.NONE);
		lblValue_3_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblValue_3_1.setText("" + trainer.getMaximunPopsize());

		Group grpSolution = new Group(composite, SWT.NONE);
		grpSolution.setLayout(new GridLayout(6, false));
		grpSolution.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 2));
		grpSolution.setText("solution");

		Label lblCurrerror = new Label(grpSolution, SWT.NONE);
		lblCurrerror.setText("currError:");

		label_currErr = new Label(grpSolution, SWT.NONE);
		GridData gd_label_currErr = new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1);
		gd_label_currErr.widthHint = 200;
		label_currErr.setLayoutData(gd_label_currErr);
		label_currErr.setText("0");

		final Button btnKeepBestResult = new Button(grpSolution, SWT.CHECK);
		btnKeepBestResult.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				trainer.setKeepBestResult(btnKeepBestResult.getSelection());
			}
		});

		btnKeepBestResult.setSelection(true);
		btnKeepBestResult.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		btnKeepBestResult.setText("keep best result: ");

		spinner_keepsltion = new Spinner(grpSolution, SWT.BORDER);
		spinner_keepsltion.setMaximum(1000);
		spinner_keepsltion.setMinimum(5);
		spinner_keepsltion.setSelection(trainer.getAiEvolution().getReinforcementLearning().getTopSize());
		spinner_keepsltion.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int selected = spinner_keepsltion.getSelection();
				trainer.getAiEvolution().getReinforcementLearning().setTopSize(selected);
			}
		});

		Label lblSolutionError = new Label(grpSolution, SWT.NONE);
		lblSolutionError.setText("Solution Error:");

		label_SoluErr = new Label(grpSolution, SWT.NONE);
		GridData gd_label_SoluErr = new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1);
		gd_label_SoluErr.widthHint = 200;
		label_SoluErr.setLayoutData(gd_label_SoluErr);
		label_SoluErr.setText("0");

		Label lblAveragelbl = new Label(grpSolution, SWT.NONE);
		lblAveragelbl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblAveragelbl.setText("Average: ");

		lblAverage = new Label(grpSolution, SWT.NONE);
		GridData gd_lblAverage = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_lblAverage.widthHint = 350;
		lblAverage.setLayoutData(gd_lblAverage);
		lblAverage.setText("0");
		new Label(grpSolution, SWT.NONE);

		final Button btnSolution = new Button(grpSolution, SWT.CHECK);
		btnSolution.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 2, 1));
		btnSolution.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				Boolean AutoUpgradeSolution = btnSolution.getSelection();

				UpdateSolution updt = new UpdateSolution(AutoUpgradeSolution);
				updt.start();

			}

		});

		btnSolution.setText("(Auto) updt \r\nsolution: ");

		final Spinner spinner_1 = new Spinner(grpSolution, SWT.BORDER);
		spinner_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				trainer.setUpgradeRatio(spinner_1.getSelection());
			}
		});
		spinner_1.setMinimum(1);
		spinner_1.setSelection(trainer.getUpgradeRatio());

		textSolution = new Text(grpSolution, SWT.BORDER);
		textSolution.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		Button btnUpdate = new Button(grpSolution, SWT.NONE);
		btnUpdate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnUpdate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				textSolution.setText(String.valueOf(trainer.upgradeSolution()));
			}
		});
		btnUpdate.setText("Update");

		Label lblLoop = new Label(composite, SWT.NONE);
		lblLoop.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblLoop.setText("Loop:");

		final Scale scale = new Scale(composite, SWT.NONE);
		scale.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		scale.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int data = scale.getSelection();
				trainer.setLoop(data);
				lblValue.setText(String.valueOf(data));
			}
		});
		scale.setMaximum(10000);
		scale.setSelection(trainer.getLoop());

		lblValue = new Label(composite, SWT.NONE);
		GridData gd_lblValue = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblValue.widthHint = 30;
		lblValue.setLayoutData(gd_lblValue);
		lblValue.setText("" + trainer.getLoop());

		Composite composite_4 = new Composite(shlGaTrainer, SWT.NONE);
		composite_4.setLayout(new GridLayout(7, false));
		composite_4.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));

		Label lblMoreDigitalResource = new Label(composite_4, SWT.NONE);
		lblMoreDigitalResource.setText("More digital\r\nresource:");

		final Scale scale_3_1_1 = new Scale(composite_4, SWT.NONE);
		scale_3_1_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				long setval = (long) Math.pow(10, scale_3_1_1.getSelection());
				trainer.getAiEvolution().getValuer().setUpgradeLen(setval);
				label_Varat_2.setText("" + trainer.getAiEvolution().getValuer().getUpgradeLen());
			}
		});
		scale_3_1_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		scale_3_1_1.setMaximum(9);
		scale_3_1_1.setMinimum(1);
		scale_3_1_1.setSelection(1);
		scale_3_1_1.setIncrement(1);

		label_Varat_2 = new Label(composite_4, SWT.NONE);
		GridData gd_label_Varat_2 = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_label_Varat_2.widthHint = 40;
		label_Varat_2.setLayoutData(gd_label_Varat_2);
		label_Varat_2.setText("" + (long) (trainer.getAiEvolution().getValuer().getUpgradeLen()));

		Label lblsepar_1 = new Label(composite_4, SWT.SEPARATOR);
		GridData gd_lblsepar_1 = new GridData(SWT.LEFT, SWT.FILL, false, true, 1, 2);
		gd_lblsepar_1.widthHint = 25;
		lblsepar_1.setLayoutData(gd_lblsepar_1);
		lblsepar_1.setText("asd");

		Group grpLoopInfo = new Group(composite_4, SWT.NONE);
		grpLoopInfo.setLayout(new GridLayout(2, false));
		GridData gd_grpLoopInfo = new GridData(SWT.FILL, SWT.FILL, false, true, 2, 2);
		gd_grpLoopInfo.widthHint = 250;
		grpLoopInfo.setLayoutData(gd_grpLoopInfo);
		grpLoopInfo.setText("Loop info");

		Label lblCurrentLoop = new Label(grpLoopInfo, SWT.NONE);
		lblCurrentLoop.setText("Current loop: ");

		label_currloop = new Label(grpLoopInfo, SWT.NONE);
		label_currloop.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		label_currloop.setText("0");

		Label lblCurrSize = new Label(grpLoopInfo, SWT.NONE);
		lblCurrSize.setText("Current size: ");

		label_currsize = new Label(grpLoopInfo, SWT.NONE);
		label_currsize.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		label_currsize.setText("0");

		Label lblCurrentTime = new Label(grpLoopInfo, SWT.NONE);
		lblCurrentTime.setText("Current time: ");

		label_currtime = new Label(grpLoopInfo, SWT.NONE);
		label_currtime.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		label_currtime.setText("0");

		Group grpPso = new Group(composite_4, SWT.NONE);
		grpPso.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 2));
		grpPso.setLayout(new GridLayout(6, false));
		grpPso.setText("PSO");

		final Button btnPso = new Button(grpPso, SWT.CHECK);
		btnPso.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));
		btnPso.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				trainer.setAutoUpgradeByPSO(btnPso.getSelection());
			}
		});
		btnPso.setText("PSO support:");
		btnPso.setSelection(trainer.isAutoUpgradeByPSO());

		Label lblBorder = new Label(grpPso, SWT.NONE);
		lblBorder.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblBorder.setText("Border:");

		final Scale scale_7 = new Scale(grpPso, SWT.NONE);
		scale_7.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				double select = scale_7.getSelection() / 1000;
				trainer.setBorder(select);
				label_3.setText("" + select);
			}
		});
		scale_7.setMaximum(100000);
		scale_7.setMinimum(5);
		scale_7.setSelection((int) trainer.getPsoBorder() * 1000);

		label_3 = new Label(grpPso, SWT.NONE);
		label_3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		label_3.setText("" + scale_7.getSelection() / 1000);

		Label lblLoop_1 = new Label(grpPso, SWT.NONE);
		lblLoop_1.setText("Loop:");

		final Spinner spinner_3 = new Spinner(grpPso, SWT.BORDER);
		spinner_3.setMaximum(500);
		spinner_3.setMinimum(5);
		spinner_3.setSelection(trainer.getPSOLoopTotal());
		spinner_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				trainer.setPSOLoopTotal(spinner_3.getSelection());
			}
		});
		spinner_3.setSelection(trainer.getPSOLoopTotal());

		Label label_2 = new Label(grpPso, SWT.SEPARATOR | SWT.VERTICAL);
		GridData gd_label_2 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_label_2.heightHint = 25;
		label_2.setLayoutData(gd_label_2);

		Label lblPsoSize_1 = new Label(grpPso, SWT.NONE);
		lblPsoSize_1.setText("PSO size: ");

		final Scale scale_6 = new Scale(grpPso, SWT.NONE);
		scale_6.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				trainer.setSizeOfPSOPopulation(scale_6.getSelection());
				lblPsoSize.setText("" + scale_6.getSelection());
			}
		});
		scale_6.setMaximum(5000);
		scale_6.setMinimum(2000);
		scale_6.setSelection(trainer.getSizeOfPSOPopulation());

		lblPsoSize = new Label(grpPso, SWT.NONE);
		GridData gd_lblPsoSize = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblPsoSize.widthHint = 30;
		lblPsoSize.setLayoutData(gd_lblPsoSize);
		lblPsoSize.setText("" + trainer.getSizeOfPSOPopulation());

		Label lblValuerRator = new Label(composite_4, SWT.NONE);
		lblValuerRator.setText("Valuer ratio:\r\n(desmoooth)");

		final Scale scale_3_1 = new Scale(composite_4, SWT.NONE);
		scale_3_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		scale_3_1.setIncrement(3);
		scale_3_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				double data = (double) scale_3_1.getSelection() * 2 / 1000000;
				trainer.setValueLevel(data);
				label_Varat.setText(String.valueOf(data));
			}
		});
		scale_3_1.setMaximum(1000000);
		scale_3_1.setMinimum(500000);
		scale_3_1.setSelection(500000);

		label_Varat = new Label(composite_4, SWT.NONE);
		GridData gd_label_Varat = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_label_Varat.widthHint = 40;
		label_Varat.setLayoutData(gd_label_Varat);
		label_Varat.setText("" + trainer.getValueLevel());

		composite_3 = new Composite(shlGaTrainer, SWT.NONE);
		composite_3.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, true, 1, 1));
		composite_3.setLayout(new GridLayout(1, false));

		Label lblControl = new Label(composite_3, SWT.NONE);
		lblControl.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblControl.setText("Ratio \r\nRange: ");

		final Scale scale_5 = new Scale(composite_3, SWT.VERTICAL);
		GridData gd_scale_5 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_scale_5.heightHint = 85;
		scale_5.setLayoutData(gd_scale_5);
		scale_5.setMaximum(10000);
		scale_5.setMinimum(100);
		scale_5.setSelection(500);

		final Label lblValue_1_1 = new Label(composite_3, SWT.NONE);
		GridData gd_lblValue_1_1 = new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1);
		gd_lblValue_1_1.widthHint = 30;
		lblValue_1_1.setLayoutData(gd_lblValue_1_1);
		lblValue_1_1.setText("" + scale_5.getSelection());
		scale_5.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int perf = scale_5.getSelection();
				double f1 = scale_1_1.getSelection() / scale_1_1.getMaximum();
				double f2 = scale_1_1_1.getSelection() / scale_1_1_1.getMaximum();
				double f3 = scale_1_1_1_1.getSelection() / scale_1_1_1_1.getMaximum();
				scale_1_1.setMaximum(perf);
				scale_1_1_1.setMaximum(perf);
				scale_1_1_1_1.setMaximum(perf);
				scale_1_1.setSelection((int) (f1 * perf));
				scale_1_1_1.setSelection((int) (f2 * perf));
				scale_1_1_1_1.setSelection((int) (f3 * perf));
				lblValue_1_1.setText("" + perf);
			}
		});

		Composite composite_2 = new Composite(shlGaTrainer, SWT.BORDER);
		composite_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		composite_2.setLayout(new GridLayout(8, false));

		Label lblNaturalfitnessscores = new Label(composite_2, SWT.NONE);
		lblNaturalfitnessscores.setText("selectionSize \r\nratio:");

		final Scale scale_1 = new Scale(composite_2, SWT.NONE);
		scale_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		scale_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				double data = (double) ((double) scale_1.getSelection() / 1000);
				trainer.setSelectionRatio(data);
				lblValue_1.setText(String.valueOf(data));
			}
		});
		scale_1.setMaximum(1000);
		scale_1.setMinimum(1);
		scale_1.setSelection((int) (trainer.getSelectionRatio() * 1000));

		lblValue_1 = new Label(composite_2, SWT.NONE);
		GridData gd_lblValue_1 = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_lblValue_1.widthHint = 30;
		lblValue_1.setLayoutData(gd_lblValue_1);
		lblValue_1.setText("" + trainer.getSelectionRatio());

		Composite composite_3_1 = new Composite(composite_2, SWT.NONE);
		composite_3_1.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 3));
		composite_3_1.setLayout(new GridLayout(1, false));

		Label lblMutationrange = new Label(composite_3_1, SWT.NONE);
		lblMutationrange.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblMutationrange.setText("Mutation \r\nRange: ");

		final Scale scale_5_1 = new Scale(composite_3_1, SWT.VERTICAL);
		scale_5_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				trainer.getAiEvolution().setLenOfGenMutation(scale_5_1.getSelection());
				lblValue_1_1_1.setText("" + trainer.getAiEvolution().getLenOfGenMutation());
			}
		});
		GridData gd_scale_5_1 = new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1);
		gd_scale_5_1.widthHint = 25;
		gd_scale_5_1.heightHint = 70;
		scale_5_1.setLayoutData(gd_scale_5_1);
		scale_5_1.setMaximum(trainer.getAiEvolution().getLenOfGen());
		scale_5_1.setMinimum(1);
		scale_5_1.setSelection(6);

		lblValue_1_1_1 = new Label(composite_3_1, SWT.NONE);
		GridData gd_lblValue_1_1_1 = new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1);
		gd_lblValue_1_1_1.widthHint = 30;
		lblValue_1_1_1.setLayoutData(gd_lblValue_1_1_1);
		lblValue_1_1_1.setText("" + trainer.getAiEvolution().getLenOfGenMutation());

		Label lblSep = new Label(composite_2, SWT.SEPARATOR | SWT.VERTICAL);
		GridData gd_lblSep = new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 3);
		gd_lblSep.widthHint = 25;
		lblSep.setLayoutData(gd_lblSep);
		lblSep.setText("sep");

		Label lblHybridratio = new Label(composite_2, SWT.NONE);
		lblHybridratio.setText("HybridRatio: ");

		scale_1_1_1 = new Scale(composite_2, SWT.NONE);
		scale_1_1_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		scale_1_1_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				double data = (double) scale_1_1_1.getSelection() / scale_1_1.getMaximum();
				trainer.setHybridRatio(data);
				label_Varat_1_1.setText(String.valueOf(data));
			}
		});
		scale_1_1_1.setMaximum(500);
		scale_1_1_1.setSelection((int) (500 * trainer.getHybridRatio()));

		label_Varat_1_1 = new Label(composite_2, SWT.NONE);
		GridData gd_label_Varat_1_1 = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_label_Varat_1_1.widthHint = 30;
		label_Varat_1_1.setLayoutData(gd_label_Varat_1_1);
		label_Varat_1_1.setText("" + trainer.getHybridRatio());

		Label lblMutantratio = new Label(composite_2, SWT.NONE);
		lblMutantratio.setText("MutantRatio: ");

		scale_4 = new Scale(composite_2, SWT.NONE);
		scale_4.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		scale_4.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				double data = (double) scale_4.getSelection() / 1000;
				trainer.setMutantRatio(data);
//				System.out.println(trainer.getMutantRatio() + " " + scale_4.getSelection());
				label_1.setText("" + trainer.getMutantRatio());
			}
		});
		scale_4.setMaximum(1000);
		scale_4.setSelection((int) (trainer.getMutantRatio() * 1000));

		label_1 = new Label(composite_2, SWT.NONE);
		label_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		label_1.setText("" + String.valueOf(trainer.getMutantRatio()));

		Label lblDefendratio = new Label(composite_2, SWT.NONE);
		lblDefendratio.setText("DefendRatio: ");

		scale_1_1_1_1 = new Scale(composite_2, SWT.NONE);
		scale_1_1_1_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		scale_1_1_1_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				double data = (double) scale_1_1_1_1.getSelection() / scale_1_1.getMaximum();
				trainer.setDefendRatio(data);
				label_Varat_1_1_1.setText(String.valueOf(data));
			}
		});
		scale_1_1_1_1.setMaximum(500);
		scale_1_1_1_1.setSelection((int) (500 * trainer.getDefendRatio()));

		label_Varat_1_1_1 = new Label(composite_2, SWT.NONE);
		label_Varat_1_1_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		label_Varat_1_1_1.setText("" + trainer.getDefendRatio());

		Label lblSomamutantratio = new Label(composite_2, SWT.NONE);
		lblSomamutantratio.setText("SomaMutant\r\nRatio: ");

		scale_1_1 = new Scale(composite_2, SWT.NONE);
		scale_1_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		scale_1_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				double data = (double) scale_1_1.getSelection() / scale_1_1.getMaximum();
				trainer.setSomaMutantRatio(data);
				label_Varat_1.setText(String.valueOf(data));
			}
		});
		scale_1_1.setMaximum(500);
		scale_1_1.setSelection((int) (500 * trainer.getSomaMutantRatio()));

		label_Varat_1 = new Label(composite_2, SWT.NONE);
		label_Varat_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		label_Varat_1.setText("" + trainer.getSomaMutantRatio());

		Label lblbestchild = new Label(composite_2, SWT.NONE);
		lblbestchild.setText("Best Child \r\nRatio: ");

		final Scale scale_1_1_2 = new Scale(composite_2, SWT.NONE);
		scale_1_1_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				double data = (double) scale_1_1_2.getSelection() / scale_1_1_2.getMaximum();
				trainer.setMakeBestChildgRatio(data);
				label_Varat_1_2.setText(String.valueOf(data));
			}
		});
		scale_1_1_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		scale_1_1_2.setMaximum(500);
		scale_1_1_2.setSelection((int) (500 * trainer.getMakeBestChildgRatio()));

		label_Varat_1_2 = new Label(composite_2, SWT.NONE);
		label_Varat_1_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		label_Varat_1_2.setText("" + trainer.getMakeBestChildgRatio());

		Composite composite_1 = new Composite(shlGaTrainer, SWT.NONE);
		composite_1.setLayout(new GridLayout(5, false));
		composite_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));

		Button btnStartTrainning = new Button(composite_1, SWT.NONE);
		GridData gd_btnStartTrainning = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnStartTrainning.widthHint = 85;
		btnStartTrainning.setLayoutData(gd_btnStartTrainning);
		btnStartTrainning.addSelectionListener(new SelectionAdapter() {
			MyRunnable myRunnable = new MyRunnable(trainer);
			Thread trainThread = new Thread(myRunnable);

			@Override
			public void widgetSelected(SelectionEvent e) {

				if (trainThread == null) {
					trainThread.start();
				} else {
					if (!trainThread.isAlive()) {
						trainThread = new Thread(myRunnable);
						trainThread.start();
					}
				}
			}
		});
		btnStartTrainning.setText("Start Trainning");

		final Button btnPauseTrain = new Button(composite_1, SWT.NONE);
		GridData gd_btnPauseTrain = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnPauseTrain.widthHint = 85;
		btnPauseTrain.setLayoutData(gd_btnPauseTrain);
		btnPauseTrain.addSelectionListener(new SelectionAdapter() {
			boolean isPause = false;

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!isPause) {
					isPause = true;
					trainer.setPauseTrain(isPause);
					btnPauseTrain.setText("Continue Train");
					return;
				}
				isPause = false;
				trainer.setPauseTrain(isPause);
				btnPauseTrain.setText("Pause Train");
			}
		});
		btnPauseTrain.setText("Pause Train");

		Button btnCancelTrain = new Button(composite_1, SWT.NONE);
		btnCancelTrain.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				trainer.setCancelTrain(true);
			}
		});
		GridData gd_btnCancelTrain = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnCancelTrain.widthHint = 85;
		btnCancelTrain.setLayoutData(gd_btnCancelTrain);
		btnCancelTrain.setText("Cancel train");

		Button btnTest = new Button(composite_1, SWT.NONE);
		GridData gd_btnTest = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
		gd_btnTest.widthHint = 85;
		btnTest.setLayoutData(gd_btnTest);
		btnTest.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

			}

		});
		btnTest.setText("Test");

		Button btnExit = new Button(composite_1, SWT.NONE);
		GridData gd_btnExit = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnExit.widthHint = 85;
		btnExit.setLayoutData(gd_btnExit);
		btnExit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setText("Exit");

		Menu menu = new Menu(shlGaTrainer, SWT.BAR);
		shlGaTrainer.setMenuBar(menu);

		MenuItem mntmFile = new MenuItem(menu, SWT.CASCADE);
		mntmFile.setText("File");

		Menu menu_1 = new Menu(mntmFile);
		mntmFile.setMenu(menu_1);

		MenuItem mntmExit = new MenuItem(menu_1, SWT.NONE);
		mntmExit.setText("Exit");

		MenuItem mntmInout = new MenuItem(menu, SWT.CASCADE);
		mntmInout.setText("Inout");

		Menu menu_2 = new Menu(mntmInout);
		mntmInout.setMenu(menu_2);

		MenuItem mntmInput = new MenuItem(menu_2, SWT.NONE);
		mntmInput.setText("Input");

		MenuItem mntmOutput = new MenuItem(menu_2, SWT.NONE);
		mntmOutput.setText("Output");

		MenuItem mntmIndex = new MenuItem(menu, SWT.CASCADE);
		mntmIndex.setText("Index");

		Menu menu_3 = new Menu(mntmIndex);
		mntmIndex.setMenu(menu_3);

		MenuItem mntmPopulationIndex = new MenuItem(menu_3, SWT.NONE);
		mntmPopulationIndex.setText("Population Index");

	}

	String Err;
	private Label label_currloop;
	private Label label_currsize;
	private Label label_currtime;
	private Spinner spinner_keepsltion;
	private Label lblPsoSize;
	private Label label_3;

	class MyRunnable implements Runnable {
		GATrainer trainner;

		public MyRunnable(GATrainer trainner) {
			this.trainner = trainner;
		}

		@Override
		public void run() {
			try {
				Display.getDefault().syncExec(new Runnable() {
					public void run() {
						spinner_2.setEnabled(false);
					}
				});
				trainner.Train();
				Display.getDefault().syncExec(new Runnable() {
					public void run() {
						spinner_2.setEnabled(true);
						@SuppressWarnings("unused")
						int buttonID = showMsgComplete("Warning", "Training complete!");
						trainner.setCancelTrain(false);

//						switch (buttonID) {
//						case SWT.YES:
//							trainner.setCancelTrain(false);
//						case SWT.NO:
//							trainner.setCancelTrain(false);
//							break;
//						case SWT.CANCEL:
//							trainner.setCancelTrain(false);
//						}
					}
				});

			} catch (Exception e) {
				Err = e.toString();
				Display.getDefault().syncExec(new Runnable() {
					public void run() {
						showMsgComplete("error", "E: " + Err);
					}
				});
			}
		}

	}

	private int showMsgComplete(String type, String msg) {
		MessageBox messageBox = new MessageBox(shlGaTrainer, SWT.ICON_WARNING | SWT.ABORT | SWT.CANCEL);
		messageBox.setText(type);
		messageBox.setMessage(msg);
		return messageBox.open();

	}

	class UpdateSolution extends Thread {
		private boolean AutoUpgradeSolution;

		public UpdateSolution(Boolean AutoUpgradeSolution) {
			this.AutoUpgradeSolution = AutoUpgradeSolution;
		}

		@SuppressWarnings("static-access")
		@Override
		public void run() {
			super.run();
			try {
				while (AutoUpgradeSolution) {
					this.sleep(1200);
					Display.getDefault().syncExec(new Runnable() {
						public void run() {
							try {
								TrainInfor trainInfor = trainer.getTrainInfor();
								textSolution.setText("" + (trainInfor.getSolution() == null ? -1
										: trainInfor.getSolution().getResult()));
								label_currErr.setText("" + (trainInfor.getCurrrs() == null ? -1
										: trainInfor.getCurrrs().getError().getFitness()));
								label_SoluErr.setText("" + (trainInfor.getBestrs() == null ? -1
										: trainInfor.getBestrs().getError().getFitness()));// trainer.getbestResuErr());
								lblAverage.setText("" + trainInfor.getAverage());
								label_currloop.setText("" + trainInfor.getCurrLoop());
								label_currsize.setText("" + trainInfor.getCurrSize());
								label_currtime.setText("" + trainInfor.getCurrTime());
							} catch (Exception e) {
								textSolution.setText("Err: " + e.toString());
							}
						}
					});
					trainer.setAutoUpgradeSolution(AutoUpgradeSolution);

				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("end update solution");
		}
	}
}
