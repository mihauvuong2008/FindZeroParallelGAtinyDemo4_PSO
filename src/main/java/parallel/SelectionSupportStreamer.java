package parallel;

import java.util.ArrayList;
import java.util.Random;

import ga_training.EvaluatedCandidate;
import ga_training.GENE;
import ga_training.Selector;
import ga_training.selection.CoinFlipGameSelection;
import ga_training.selection.DiceGameSelection;
import ga_training.selection.RemakeRouletteWheelSelection;
import ga_training.selection.RouletteWheelSelection;
import ga_training.selection.SortSelection;
import ga_training.SelectionChooser;

@SuppressWarnings("unused")
public class SelectionSupportStreamer extends Thread {

	private ArrayList<EvaluatedCandidate> candidateSet;
	private Boolean naturalFitnessScores;
	private Random RouletteRandom;
	private int size;
	private ArrayList<GENE> populations_result;
	private Selector selector;

	public SelectionSupportStreamer(Selector selector, ArrayList<GENE> populations_result,
			ArrayList<EvaluatedCandidate> candidateSet, Boolean naturalFitnessScores, Random RouletteRandom, int size) {
		super();
		this.selector = selector;
		this.populations_result = populations_result;
		this.candidateSet = candidateSet;
		this.naturalFitnessScores = naturalFitnessScores;
		this.RouletteRandom = RouletteRandom;
		this.size = size;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
//		ArrayList<GENE> _populations = RouletteWheelSelection.select2(candidateSet, naturalFitnessScores,
//				RouletteRandom, size);
//		DiceGameSelection engine = new DiceGameSelection();
//		ArrayList<GENE> _populations = engine.select(candidateSet, naturalFitnessScores, RouletteRandom, size);
//		for (GENE gene : _populations) {
//			if (gene == null) {
//				System.out.println("check null: null");
//			}
//		}
//		System.out.println(selector);
		switch (selector.getChooser()) {
		case SelectionChooser.COINFLIPGAMESELECTION:
			CoinFlipGameSelection engine1 = new CoinFlipGameSelection();
			ArrayList<GENE> _populations1 = engine1.select(candidateSet, naturalFitnessScores, RouletteRandom, size);
			synchronized (populations_result) {
				populations_result.addAll(_populations1);
			}
//			System.out.println("COINFLIPGAMESELECTION");
			break;
		case SelectionChooser.DICEGAMESELECTION:
			DiceGameSelection engine2 = new DiceGameSelection();
			ArrayList<GENE> _populations2 = engine2.select(candidateSet, naturalFitnessScores, RouletteRandom, size);
			synchronized (populations_result) {
				populations_result.addAll(_populations2);
			}
//			System.out.println("DICEGAMESELECTION");
			break;
		case SelectionChooser.REMAKEROULETTEWHEELSELECTION:
			RemakeRouletteWheelSelection engine3 = new RemakeRouletteWheelSelection();
			ArrayList<GENE> _populations3 = engine3.select(candidateSet, naturalFitnessScores, RouletteRandom, size);
			synchronized (populations_result) {
				populations_result.addAll(_populations3);
			}
//			System.out.println("REMAKEROULETTEWHEELSELECTION");
			break;
		case SelectionChooser.ROULETTEWHEELSELECTION:
			RouletteWheelSelection engine4 = new RouletteWheelSelection();
			ArrayList<GENE> _populations4 = engine4.select(candidateSet, naturalFitnessScores, RouletteRandom, size);
			synchronized (populations_result) {
				populations_result.addAll(_populations4);
			}
//			System.out.println("ROULETTEWHEELSELECTION");
			break;
		case SelectionChooser.SORTSELECTION:
			SortSelection engine5 = new SortSelection();
			ArrayList<GENE> _populations5 = engine5.select(candidateSet, naturalFitnessScores, RouletteRandom, size);
			synchronized (populations_result) {
				populations_result.addAll(_populations5);
			}
//			System.out.println("SORTSELECTION");
			break;

		default:
			break;
		}

	}
}
