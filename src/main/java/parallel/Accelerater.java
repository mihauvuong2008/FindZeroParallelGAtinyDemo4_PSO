package parallel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ga_training.EvaluatedCandidate;
import ga_training.GENE;
import ga_training.Selector;
import ga_training.aiEvolution.AiEvolution;

public class Accelerater {
	private final int maxOfStream = 480;
	private final int minOfStream = 5;
	private final int maxSizeOfIngredien = 1800;
	private final int minSizeOfIngredient = 150;
	private final int resourceStep = 100;
	private AiEvolution aiEvolution;
	private boolean cPUprioritize = true;
	private final int FORGIVE_TIME = 1;

	private StreamInfo sinfo;

	private ArrayList<EvaluatedCandidate> candidateSet;
	private ArrayList<GENE> populations;
	private Selector selector;

	public Selector getSelector() {
		return selector;
	}

	public void setSelector(Selector selector) {
		this.selector = selector;
	}

	public boolean iscPUprioritize() {
		return cPUprioritize;
	}

	public void setcPUprioritize(boolean cPUprioritize) {
		this.cPUprioritize = cPUprioritize;
	}

	public Accelerater(AiEvolution aiEvolution, boolean cPUprioritize, Selector selector) {
		super();
		this.aiEvolution = aiEvolution;
		this.cPUprioritize = cPUprioritize;
		this.selector = selector;
	}

	public void setupGate(ArrayList<GENE> populations, ArrayList<EvaluatedCandidate> candidateSet,
			boolean cPUprioritize) {
		if (candidateSet == null)
			candidateSet = new ArrayList<>();

		this.candidateSet = candidateSet;
		this.populations = populations;
		this.cPUprioritize = cPUprioritize;
	}

	public ArrayList<GENE> SelectionSuport(Boolean naturalFitnessScores, Random rouletteRandom, int size)
			throws InterruptedException {
		sinfo = setNumOfStream(candidateSet.size());
//		System.out.println("size " + size + ", sinfo.numOfStream: " + sinfo.numOfStream);
		int suportSize = (int) ((double) sinfo.sizeOfIngredient * ((double) size / candidateSet.size()));
		ArrayList<GENE> populations_result = new ArrayList<>();

		ArrayList<ArrayList<EvaluatedCandidate>> ListSub = new ArrayList<>();
		for (int i = 0; i < sinfo.numOfStream - 1; i++) {

			List<EvaluatedCandidate> sub = candidateSet.subList(i * sinfo.sizeOfIngredient,
					(i + 1) * sinfo.sizeOfIngredient);
			ArrayList<EvaluatedCandidate> _sub = new ArrayList<>();
			_sub.addAll(sub);
			ListSub.add(_sub);
		}

		int last = (sinfo.numOfStream - 1) * sinfo.sizeOfIngredient;
		List<EvaluatedCandidate> sub = candidateSet.subList(last, candidateSet.size());

		if (sub.size() != 0) {// catch last element size = 0
			ArrayList<EvaluatedCandidate> _sub = new ArrayList<>();
			_sub.addAll(sub);
			ListSub.add(_sub);
		}

		SelectionSupportStreamer[] execThread = new SelectionSupportStreamer[ListSub.size()];

		for (int j = 0; j < ListSub.size(); j++) {

			execThread[j] = new SelectionSupportStreamer(selector, populations_result, ListSub.get(j),
					naturalFitnessScores, rouletteRandom, suportSize);
		}

		Random shake = new Random();
		for (SelectionSupportStreamer selectionSuportStreamer : execThread) {
			if (selectionSuportStreamer != null)
				selectionSuportStreamer.start();
			Thread.sleep(shake.nextInt(FORGIVE_TIME));// here fix loss digital, increse performance and find best
														// solotion
		}

		for (SelectionSupportStreamer selectionSuportStreamer : execThread) {
			if (selectionSuportStreamer != null)
				selectionSuportStreamer.join();
			Thread.sleep(shake.nextInt(FORGIVE_TIME));// here fix loss digital, increse performance and find best
														// solotion
		}

		return populations_result;

	}

	public ArrayList<EvaluatedCandidate> ValueSuport() throws InterruptedException {
		sinfo = setNumOfStream(populations.size());
		ArrayList<EvaluatedCandidate> evaluatedCandidate_result = new ArrayList<>();

		ArrayList<ArrayList<GENE>> ListSub = new ArrayList<>();
		for (int i = 0; i < sinfo.numOfStream - 1; i++) {

			List<GENE> sub = populations.subList(i * sinfo.sizeOfIngredient, (i + 1) * sinfo.sizeOfIngredient);
			if (sub.size() == 0)// catch last element size = 0
				break;
			ArrayList<GENE> _sub = new ArrayList<>();
			_sub.addAll(sub);
			ListSub.add(_sub);

		}

		int last = (sinfo.numOfStream - 1) * sinfo.sizeOfIngredient;
		List<GENE> sub = populations.subList(last, populations.size());
		if (sub.size() != 0) {
			ArrayList<GENE> _sub = new ArrayList<>();
			_sub.addAll(sub);
			ListSub.add(_sub);
		}

		ValueSupportStreamer[] execThread = new ValueSupportStreamer[ListSub.size()];
		for (int j = 0; j < ListSub.size(); j++) {
			execThread[j] = new ValueSupportStreamer(aiEvolution, ListSub.get(j), evaluatedCandidate_result);
		}

		Random shake = new Random();
		for (ValueSupportStreamer valueSuportStreamer : execThread) {
			valueSuportStreamer.start();
			Thread.sleep(shake.nextInt(FORGIVE_TIME));// fix loss digital, increse performance and find best solotion
		}

		for (ValueSupportStreamer valueSuportStreamer : execThread) {
			valueSuportStreamer.join();
			Thread.sleep(shake.nextInt(FORGIVE_TIME));// fix loss digital, increse performance and find best solotion
		}

		return evaluatedCandidate_result;
	}

	public StreamInfo setNumOfStream(int setSize) {
		StreamInfo sinfo = new StreamInfo();
		boolean flag = false;
		if (cPUprioritize) {
			for (int i = minOfStream; i < maxOfStream; i += 1) {
				if (!flag)
					for (int j = minSizeOfIngredient; j < maxSizeOfIngredien; j += resourceStep) {
						if (i * j > setSize) {
							sinfo.numOfStream = i;
							sinfo.sizeOfIngredient = j;
							flag = true;
							break;
						}
					}
			}
		} else {
			for (int j = minSizeOfIngredient; j < maxSizeOfIngredien; j += resourceStep) {
				if (!flag)
					for (int i = minOfStream; i < maxOfStream; i += 1) {
						if (i * j > setSize) {
							sinfo.numOfStream = i;
							sinfo.sizeOfIngredient = j;
							flag = true;
							break;
						}
					}
			}
		}

		if (!flag) {
			sinfo.numOfStream = maxOfStream;
			sinfo.sizeOfIngredient = setSize / sinfo.numOfStream;
		}
		return sinfo;
	}

	class StreamInfo {
		private int numOfStream;
		private int sizeOfIngredient;

		public int getNumOfStream() {
			return numOfStream;
		}

		public void setNumOfStream(int numOfStream) {
			this.numOfStream = numOfStream;
		}

		public int getSizeOfIngredient() {
			return sizeOfIngredient;
		}

		public void setSizeOfIngredient(int sizeOfIngredient) {
			this.sizeOfIngredient = sizeOfIngredient;
		}
	}
}
