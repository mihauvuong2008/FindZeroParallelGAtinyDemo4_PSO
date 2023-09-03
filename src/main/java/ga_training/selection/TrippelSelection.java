package ga_training.selection;

import java.util.ArrayList;
import java.util.Random;

import java.util.List;

import ga_training.EvaluatedCandidate;
import ga_training.GENE;
import ga_training.Selector;
import ga_training.aiEvolution.AiEvolution;
import parallel.Accelerater;

public class TrippelSelection {

	private ArrayList<EvaluatedCandidate> candidateSet;
	private ArrayList<EvaluatedCandidate>[] _fakeCandidateSet;
	@SuppressWarnings("unused")
	private ArrayList<GENE> populations;
	private ArrayList<GENE>[] _fakePopulations;
	private Random[] spaceRandom;
	private boolean cPUprioritize;
	private static final int TRIPLE = 6;
	private Accelerater accelerater;

	public TrippelSelection(AiEvolution aiEvolution, boolean cPUprioritize, Selector selector) {
		super();
		this.cPUprioritize = cPUprioritize;
		accelerater = new Accelerater(aiEvolution, cPUprioritize, selector);
	}

	public Accelerater getAccelerater() {
		return accelerater;
	}

	public void setAccelerater(Accelerater accelerater) {
		this.accelerater = accelerater;
	}

	@SuppressWarnings("unchecked")
	public void setupGate(ArrayList<GENE> populations, ArrayList<EvaluatedCandidate> candidateSet,
			boolean cPUprioritize) {
		this.candidateSet = candidateSet;
		this.populations = populations;
		this.cPUprioritize = cPUprioritize;
		_fakeCandidateSet = new ArrayList[TRIPLE];
		_fakePopulations = new ArrayList[TRIPLE];
		spaceRandom = new Random[TRIPLE];
		for (int i = 0; i < TRIPLE; i++) {
			_fakeCandidateSet[i] = new ArrayList<>();
			_fakePopulations[i] = new ArrayList<>();
			spaceRandom[i] = new Random();
		}
	}

	public ArrayList<GENE> TrippelSelect(boolean naturalFitnessScores, Random rouletteRandom, int size)
			throws InterruptedException {
		ArrayList<GENE> populationsResult = new ArrayList<>();
		int breakPoint = candidateSet.size() / TRIPLE;
		int resize = size / TRIPLE;
		for (int i = 0; i < TRIPLE; i++) {
			if (i == TRIPLE - 1) {
				List<EvaluatedCandidate> sub = candidateSet.subList(i * breakPoint, candidateSet.size() - 1);
				_fakeCandidateSet[i].addAll(sub);
			} else {
				List<EvaluatedCandidate> sub = candidateSet.subList(i * breakPoint, (i + 1) * breakPoint);
				_fakeCandidateSet[i].addAll(sub);
			}
		}
		for (int i = 0; i < TRIPLE; i++) {
			accelerater.setupGate(null, _fakeCandidateSet[i], cPUprioritize);
			_fakePopulations[i] = accelerater.SelectionSuport(naturalFitnessScores, spaceRandom[i], resize);
		}

		for (int i = 0; i < TRIPLE; i++) {
			populationsResult.addAll(_fakePopulations[i]);
		}

		return populationsResult;
	}

	public ArrayList<GENE> TrippelSelect2(boolean naturalFitnessScores, Random rouletteRandom, int size)
			throws InterruptedException {
		ArrayList<GENE> populationsResult = new ArrayList<>();
		int resize = size / TRIPLE;

		accelerater.setupGate(null, candidateSet, cPUprioritize);
		ArrayList<GENE> firstStepPopnsTripSel = accelerater.SelectionSuport(naturalFitnessScores, rouletteRandom, size);

		accelerater.setupGate(firstStepPopnsTripSel, null, cPUprioritize);
		ArrayList<EvaluatedCandidate> firstCandidateSet = accelerater.ValueSuport();

		for (int i = 0; i < TRIPLE; i++) {
			accelerater.setupGate(null, firstCandidateSet, cPUprioritize);
			_fakePopulations[i] = accelerater.SelectionSuport(naturalFitnessScores, spaceRandom[i], resize);
//			System.out.println("_fakePopulations[i]: " + _fakePopulations[i].size());
		}

		for (int i = 0; i < TRIPLE; i++) {
			populationsResult.addAll(_fakePopulations[i]);
		}

//		System.out.println("populationsResult: " + populationsResult.size());
		return populationsResult;
	}
}
