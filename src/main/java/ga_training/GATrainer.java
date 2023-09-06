package ga_training;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;

import appMain.FindZeroInout;
import appMain.GA_PSO_InOutForm;
import ga_training.aiEvolution.AiEvolution;
import ga_training.aiEvolution.PlanOfchilds;
import ga_training.selection.CoinFlipGameSelection;
import ga_training.selection.DiceGameSelection;
import ga_training.selection.RemakeRouletteWheelSelection;
import ga_training.selection.RouletteWheelSelection;
import ga_training.selection.SortSelection;
import ga_training.selection.TrippelSelection;
import genetoPhenotypic.BinnaryGentoPhenotypic;
import parallel.Accelerater;
import pso_network.Space;
import pso_network.training.PSO_InOut;

@SuppressWarnings("unused")
public class GATrainer {

	static Logger log = Logger.getLogger("GATrainer");

	private boolean isPauseTrain = false;
	private boolean isCompleteTrain = false;
	private boolean cancelTrain = false;
	private boolean flagUpdateResult = false;

	private TrainInfor trainInfor;

	private Random selectionRandom;
	private AiEvolution aiEvolution;

	private int loop = 100;
	private int lenOfGen = 40;
	private boolean naturalFitnessScores = true;
	private boolean cPUprioritize = false;
	private String selectorValue = SelectionChooser.COINFLIPGAMESELECTION;
	Selector selectorChooser;
	private int firstClasssize = 50000;
	private int minimumPopsize = 30000;
	private int maximunPopsize = 60000;
	private double valueLevel = 1;
	private double makeBestChildgRatio = 0.06;
	private double makeEverythingRatio = 0.06;
	private double selectionRatio = 0.65;
	private double mutantRatio = 0.2;
	private double somaMutantRatio = 0.06;
	private double defendRatio = 0.06;
	private double hybridRatio = 0.06;
	private int numOfChild = 2;
	private double nChildPlan = 0.5;
	private final float gama = 0.5f;
	private boolean autoUpgradeSolution = false;
	private boolean isKeepBestResult = true;

	private Result solution;
	private Result currResult;
	private Result bestResult;
	private double average;
	private int upgradeRatio = 2;// default
	private int upgradeStep = 3;// min = 3 to remove comma

	private Accelerater accelerater;

	private TrippelSelection tripleSelection;

	private boolean autoUpgradeByPSO = false;
	private int sizeOfPSOPopulation = 2500;
	private int PSOLoopTotal = 200;
	private double psoBorder = 20;
	private GA_PSO_InOutForm ga_PSO_InOutForm;
	private PSOsupport psoupport;

	public GATrainer() {
		super();
		init();
	}

	private void init() {
		trainInfor = new TrainInfor();
		selectionRandom = new Random();
		aiEvolution = new AiEvolution(lenOfGen);
		selectorChooser = new Selector(selectorValue);
		isCompleteTrain = false;
		log.info("============init================");
		log.info("================================");
		log.info("");
	}

	public void Train() throws InterruptedException {
		if (isCompleteTrain) {
			init();
		}
		ArrayList<GENE> populations = aiEvolution.getFirstClass(firstClasssize);
		ArrayList<EvaluatedCandidate> candidateSet = null;
		ArrayList<EvaluatedCandidate> keepBestResultCache = null;
		PlanOfchilds plcls = new PlanOfchilds();
		EvaluatedCandidate evaluatedCandidate = null;
		accelerater = new Accelerater(aiEvolution, cPUprioritize, selectorChooser);
		tripleSelection = new TrippelSelection(aiEvolution, cPUprioritize, selectorChooser);
		int loopTotaltime = 0;
		long tmpTime = 0;
		int upgradecount = 0;
		boolean isFirstupgradeSolution = true;
		boolean isfirstClassinit = false;

		for (int i = 0; i < loop; i++) {
			pauseTrain();
			if (cancelTrain) {
				log.info("cancelTrain: " + cancelTrain);
				break;
			}
			log.info("");
			log.info("Start... - loop: " + i);

			tmpTime = System.currentTimeMillis();
			upgradecount += 1;
			flagUpdateResult = false;

			if (populations.size() < maximunPopsize || !isfirstClassinit) {
				isfirstClassinit = true;
				//
				//
				//
				// ===== Incorporating ======

				log.info("Incorporating...");
				plcls.setupPlandescr(gama, nChildPlan, numOfChild);
				aiEvolution.Incorporate /* ket hop */ (populations, plcls, makeBestChildgRatio, mutantRatio,
						somaMutantRatio, hybridRatio, defendRatio, makeEverythingRatio);
				//
				//
				//
				// ===== Value ======

				log.info("");
				log.info("aiEvolution Value...");
//				candidateSet = aiEvolution.Value(populations, valueLevel);
				aiEvolution.getValuer().setValueLevel(valueLevel);
				accelerater.setupGate(populations, null, cPUprioritize);
				candidateSet = accelerater.ValueSuport();

				log.info("candidateSet.size(): [" + candidateSet.size() + "] , PlannumOfChild: " + plcls.getPlcls()
						+ ", makeBestChildgRatio: " + makeBestChildgRatio + ", MutantRatio: " + mutantRatio
						+ ", SomaMutantRatio: " + somaMutantRatio + ", HybridRatio: " + hybridRatio + ", DefendRatio: "
						+ defendRatio + ", makeEverythingRatio " + makeEverythingRatio);
				//
				//
				//
				// ====== current result ====== AlgoritResult====
				log.info("");
				log.info("get Algor Result...");
				evaluatedCandidate = getAlgoritResult(candidateSet); // get fake solution
				keepBestResultCache = aiEvolution.getReinforcementLearning().getTopResult(candidateSet);
				//
				getResultAndSolution(evaluatedCandidate);
				//
				//
				// ====== updateTrainParamater =====
				updateTrainParamater();
			}
			//
			//
			//
			// ===== selection =====
			int size = (int) (selectionRatio * populations.size());
			size = size > minimumPopsize ? size : populations.size();

			log.info("");
			log.info("selection...");

//			accelerater.setupGate(populations, candidateSet, cPUprioritize);
//			populations = accelerater.SelectionSuport(naturalFitnessScores, rouletteRandom, size);
			tripleSelection.setupGate(populations, candidateSet, cPUprioritize);
			populations = tripleSelection.TrippelSelect2(naturalFitnessScores, selectionRandom, size);
//			populations = RouletteWheelSelection.select(candidateSet, naturalFitnessScores, rouletteRandom, size);

			//
			//
			//
			// ===== upgrade ====

			log.info("upgrade...");

			if ((isFirstupgradeSolution || flagUpdateResult/* co result moi */) && getAutoUpgradeSolution()
					&& isHaveUpgradeRatio(upgradecount)/* User yeu cau */) {
				upgradeSolution();
				upgradecount = 0;
				isFirstupgradeSolution = false;
			}
			// ===========PSO============
			//
			//
			if (psoupport == null) {
				psoupport = new PSOsupport();
				psoupport.start();
			}
			//
			//
			// ==== isKeepBestResult ====
			if (isKeepBestResult) {
				keepBestResult(populations, keepBestResultCache);
			}

			// == refresh flag ==
			flagUpdateResult = false;
			log.info("refresh flag. Populations: " + populations.size() + ", SelectionRatio: " + selectionRatio);
			log.info("");

			// ==== end =====
			log.info("===End...===");
			int thisLoopTotaltime = (int) (System.currentTimeMillis() - tmpTime);

			loopTotaltime += thisLoopTotaltime;
			log.info("current time: " + thisLoopTotaltime);
			log.info("Total time: " + loopTotaltime);
			log.info("");
			// ==========traininfo============
			//
			//
			updateTrainInfo(thisLoopTotaltime, populations.size(), i);
			//
		}
		isCompleteTrain = true;
	}

	class PSOsupport extends Thread {
		double upgrade = 0;

		public void run() {
			while (true) {
				try {
					Thread.sleep(1200);
					if (isAutoUpgradeByPSO() && !isCompleteTrain) {
						autoUpgradeByPSO();
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		private void autoUpgradeByPSO() throws InterruptedException {

			double border = currResult.getResult();
			GA_PSO_InOutForm ga_PSO_InOutForm = new GA_PSO_InOutForm();
			ga_PSO_InOutForm.setSizeOfPopulation(sizeOfPSOPopulation);

			Space space = new Space((int) (psoBorder / 10), border + psoBorder, border + psoBorder, border + psoBorder,
					border - psoBorder, border - psoBorder, border - psoBorder);
			ga_PSO_InOutForm.setSpace(space);
			ga_PSO_InOutForm.setLoopTotal(PSOLoopTotal);
			PSO_InOut pso_InOut = new PSO_InOut(ga_PSO_InOutForm);
			pso_InOut.setUpgrade(upgrade);
			pso_InOut.action();

			double _result = pso_InOut.getResult();

			upgrade = _result;
			double partnerFiness = aiEvolution.getValuer().getpartnerValue(_result);

			if (partnerFiness > solution.getError().getFitness()) {
				synchronized (aiEvolution) {
					aiEvolution.getValuer().setUpgrade(_result);
				}
			}

			log.info("PSO upgrade: " + "(" + pso_InOut.getResult() + ")");
		}
	}

	private void updateTrainInfo(int thisLoopTotaltime, int size, int loop) {
		trainInfor.setSolution(solution);
		trainInfor.setBestrs(bestResult);
		trainInfor.setCurrrs(currResult);
		trainInfor.setAverage(average);
		trainInfor.setCurrLoop(loop);
		trainInfor.setCurrSize(size);
		trainInfor.setCurrTime(thisLoopTotaltime);
	}

	private void updateTrainParamater() {
		switch (selectorValue) {
		case SelectionChooser.COINFLIPGAMESELECTION:
			selectorChooser.setChooser(SelectionChooser.COINFLIPGAMESELECTION);
			break;
		case SelectionChooser.DICEGAMESELECTION:
			selectorChooser.setChooser(SelectionChooser.DICEGAMESELECTION);
			break;
		case SelectionChooser.REMAKEROULETTEWHEELSELECTION:
			selectorChooser.setChooser(SelectionChooser.REMAKEROULETTEWHEELSELECTION);
			break;
		case SelectionChooser.ROULETTEWHEELSELECTION:
			selectorChooser.setChooser(SelectionChooser.ROULETTEWHEELSELECTION);
			break;
		case SelectionChooser.SORTSELECTION:
			selectorChooser.setChooser(SelectionChooser.SORTSELECTION);
		default:
			break;
		}
	}

	private boolean isHaveUpgradeRatio(int upgradecount) {
		if (upgradecount >= upgradeRatio)
			return true;
		return false;
	}

	private void pauseTrain() throws InterruptedException {
		while (isPauseTrain) {
			Thread.sleep(2000);
		}
	}

	private void getResultAndSolution(EvaluatedCandidate evaluatedCandidate) {
		log.info("");
		if (evaluatedCandidate == null) {
			log.info("current result null ");
			return;
		}

		currResult = getCurrResult(evaluatedCandidate);
		showCurrResult();

		getBestResult();
		showBestResult();

		getSolution(bestResult);
	}

	private Result getCurrResult(EvaluatedCandidate evaluatedCandidate) {

		double UpgradeValue = aiEvolution.getValuer().getUpgrade(); // update news
		double Valuelevel = aiEvolution.getValuer().getValueLevel(); // update news
		double DNAres = BinnaryGentoPhenotypic
				.convertFromBinaryToNegativeDec(evaluatedCandidate.getCandidate().getGene());
		double ResultValue = FindZeroInout.getUpgradedx(UpgradeValue, aiEvolution.getValuer().getUpgradeLen(), DNAres);
		Result rs = new Result(UpgradeValue, ResultValue, evaluatedCandidate, Valuelevel);

		return rs;
	}

	private void getBestResult() {
		if (bestResult == null) {
			bestResult = currResult;
			flagUpdateResult = true;
			log.info("BestResul updated");
			return;
		}

//		if (bestResult.getValueLevel() != currResult.getValueLevel()) {
//			bestResult.setValueLevel(currResult.getValueLevel());
//			return;
//		}

		if (bestResult.getError().getFitness() <= currResult.getError().getFitness()) {
			bestResult = currResult;
			flagUpdateResult = true;
			log.info("BestResul updated");
		}

	}

	private void getSolution(Result bestResult) {
		solution = new Result(bestResult.getUpgradeValue(), bestResult.getResult(), bestResult.getError(),
				aiEvolution.getValuer().getValueLevel());
	}

	public String getSelectorValue() {
		return selectorValue;
	}

	public void setSelectorValue(String selectorValue) {
		this.selectorValue = selectorValue;
	}

	public double getAverage() {
		return average;
	}

	public void setAverage(double average) {
		this.average = average;
	}

	private void showCurrResult() {
		log.info("");
		log.info("current result: ");
		log.info("x (upgred): " + currResult.getResult() + "(UpgradeValue: " + currResult.getUpgradeValue() + " + dna  "
				+ (currResult.getResult() - currResult.getUpgradeValue()) + " )" + " - getFitness: "
				+ currResult.getError().getFitness());
	}

	private void showBestResult() {
		log.info("");
		log.info("best result: ");
		log.info("x (upgred): " + bestResult.getResult() + "(UpgradeValue: " + bestResult.getUpgradeValue() + " + dna  "
				+ (bestResult.getResult() - bestResult.getUpgradeValue()) + " )" + " - getFitness: "
				+ bestResult.getError().getFitness());

	}

	private void keepBestResult(ArrayList<GENE> _populations, ArrayList<EvaluatedCandidate> candidateSet) {
		synchronized (_populations) {
			if (candidateSet != null)
				for (EvaluatedCandidate evaluatedCandidateGene : candidateSet) {
					GENE candidate = new GENE();
					candidate.setGene(evaluatedCandidateGene.getCandidate().getGene());
					_populations.add(candidate);
				}
		}
	}

	private EvaluatedCandidate getAlgoritResult(ArrayList<EvaluatedCandidate> candidateSet) {
		double max = -1 * Double.MAX_VALUE; //
		EvaluatedCandidate result = null;
		int size = candidateSet.size();
		double count = 0;

		for (EvaluatedCandidate evaluatedCandidate : candidateSet) {

			double getFitness = evaluatedCandidate.getFitness();
			if (getFitness >= max) {
				max = getFitness;
				result = evaluatedCandidate;
			}
			count += getFitness;
		}
		this.setAverage(average = count / size);
		log.info("=> Average Fitness: " + average);
		return result;
	}

	private double compare(double currResult, double bestResult) {
		char[] compare1 = String.valueOf(currResult).toCharArray();
		char[] compare2 = String.valueOf(bestResult).toCharArray();
		boolean flagMatch = true;
		String UpgradeValuex = "";
		int step = 0;
		for (int i = 0; i < compare1.length; i++) {
			for (int j = 0; j < compare2.length; j++) {
				if (i == j) {
					if (compare1[i] != compare2[j]) {
						flagMatch = false;
						break;
					}
					step += 1;
					UpgradeValuex += String.valueOf(compare2[j]);
//					System.out.print(" " + String.valueOf(compare2[j]));
					break;
				}
			}
			if (!flagMatch) {
				break;
			}
		}

		if (!UpgradeValuex.isEmpty()) {

			double upgrade = Double.valueOf(UpgradeValuex);
			return upgrade;
		}
		return bestResult;
	}

	public double upgradeSolution() {
		// here is another loop for reinforcement learning///
		double _result = compare(currResult.getResult(), bestResult.getResult());
//		if (bestResult.getError().getFitness() < 1) {
//			_result = 0;
//		}
		aiEvolution.getValuer().setUpgrade(_result);
		log.info("UpgradeValuex compare: " + _result);
		return _result;
	}

	public TrainInfor getTrainInfor() {
		return trainInfor;
	}

	public void setTrainInfor(TrainInfor trainInfor) {
		this.trainInfor = trainInfor;
	}

	public AiEvolution getAiEvolution() {
		return aiEvolution;
	}

	public Result getSolution() {
		return solution;
	}

	public void setSolution(Result solution) {
		this.solution = solution;
	}

	public boolean isCancelTrain() {
		return cancelTrain;
	}

	public void setCancelTrain(boolean cancelTrain) {
		this.cancelTrain = cancelTrain;
	}

	public boolean isPauseTrain() {
		return isPauseTrain;
	}

	public void setPauseTrain(boolean isPauseTrain) {
		this.isPauseTrain = isPauseTrain;
	}

	public void setLenOfGen(int lenOfGen) {
		resetTrainer();
		this.lenOfGen = lenOfGen;
	}

	private void resetTrainer() {
		aiEvolution.setLenOfGen(lenOfGen);
		currResult = null;
		bestResult = null;
		solution = null;
	}

	public int getLoop() {
		return loop;
	}

	public void setLoop(int loop) {
		this.loop = loop;
	}

	public Boolean getNaturalFitnessScores() {
		return naturalFitnessScores;
	}

	public void setNaturalFitnessScores(Boolean naturalFitnessScores) {
		this.naturalFitnessScores = naturalFitnessScores;
	}

	public void setCPUprioritize(boolean cPUprioritize) {
		this.cPUprioritize = cPUprioritize;
		if (accelerater != null)
			this.accelerater.setcPUprioritize(cPUprioritize);
	}

	public boolean getCPUprioritize() {
		if (accelerater != null)
			return accelerater.iscPUprioritize();
		return this.cPUprioritize;
	}

	public int getFirstClasssize() {
		return firstClasssize;
	}

	public void setFirstClasssize(int firstClasssize) {
		this.firstClasssize = firstClasssize;
	}

	public int getMinimumPopsize() {
		return minimumPopsize;
	}

	public void setMinimumPopsize(int minimumPopsize) {
		this.minimumPopsize = minimumPopsize;
	}

	public double getMakeBestChildgRatio() {
		return makeBestChildgRatio;
	}

	public void setMakeBestChildgRatio(double makeBestChildgRatio) {
		this.makeBestChildgRatio = makeBestChildgRatio;
	}

	public int getNumOfChild() {
		return numOfChild;
	}

	public void setNumOfChild(int numOfChild) {
		this.numOfChild = numOfChild;
	}

	public double getMakeEverythingRatio() {
		return makeEverythingRatio;
	}

	public void setMakeEverythingRatio(double makeEverythingRatio) {
		this.makeEverythingRatio = makeEverythingRatio;
	}

	public double getSelectionRatio() {
		return selectionRatio;
	}

	public void setSelectionRatio(double selectionRatio) {
		this.selectionRatio = selectionRatio;
	}

	public double getHybridRatio() {
		return hybridRatio;
	}

	public void setHybridRatio(double hybridRatio) {
		this.hybridRatio = hybridRatio;
	}

	public double getMutantRatio() {
		return mutantRatio;
	}

	public void setMutantRatio(double mutantRatio) {
		this.mutantRatio = mutantRatio;
	}

	public double getSomaMutantRatio() {
		return somaMutantRatio;
	}

	public void setSomaMutantRatio(double somaMutantRatio) {
		this.somaMutantRatio = somaMutantRatio;
	}

	public double getDefendRatio() {
		return defendRatio;
	}

	public void setDefendRatio(double defendRatio) {
		this.defendRatio = defendRatio;
	}

	public double getValueLevel() {
		return valueLevel;
	}

	public void setValueLevel(double valueLevel) {
		this.valueLevel = valueLevel;
	}

	public void setNChildPlan(int NChildPlan) {
		this.nChildPlan = NChildPlan;

	}

	public void setMaximunPopsize(int maximunPopsize) {
		this.maximunPopsize = maximunPopsize;
	}

	public int getMaximunPopsize() {
		return maximunPopsize;
	}

	public void setAutoUpgradeSolution(boolean autoUpgradeSolution) {
		this.autoUpgradeSolution = autoUpgradeSolution;
	}

	public boolean getAutoUpgradeSolution() {
		return autoUpgradeSolution;
	}

	public boolean isKeepBestResult() {
		return isKeepBestResult;
	}

	public void setKeepBestResult(boolean isKeepBestResult) {
		this.isKeepBestResult = isKeepBestResult;
	}

	public double getcurrResuErr() {
		return currResult == null ? -1 : currResult.getError().getFitness();
	}

	public double getbestResuErr() {
		return bestResult == null ? -1 : bestResult.getError().getFitness();
	}

	public void setUpgradeRatio(int upgradeRatio) {
		this.upgradeRatio = upgradeRatio;
	}

	public int getUpgradeRatio() {
		return upgradeRatio;
	}

	public boolean isAutoUpgradeByPSO() {
		return autoUpgradeByPSO;
	}

	public void setAutoUpgradeByPSO(boolean autoUpgradeByPSO) {
		this.autoUpgradeByPSO = autoUpgradeByPSO;
	}

	public int getSizeOfPSOPopulation() {
		return sizeOfPSOPopulation;
	}

	public void setSizeOfPSOPopulation(int sizeOfPSOPopulation) {
		this.sizeOfPSOPopulation = sizeOfPSOPopulation;
	}

	public int getPSOLoopTotal() {
		return PSOLoopTotal;
	}

	public void setPSOLoopTotal(int pSOLoopTotal) {
		PSOLoopTotal = pSOLoopTotal;
	}

	public void setBorder(double psoBorder) {
		this.psoBorder = psoBorder;
	}

	public double getPsoBorder() {
		return psoBorder;
	}

}
