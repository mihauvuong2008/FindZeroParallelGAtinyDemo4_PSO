package ga_training.aiEvolution;

import java.util.ArrayList;
import java.util.Random;

import ga_training.EvaluatedCandidate;
import ga_training.GENE;

public class AiEvolution {
	private int lenOfGen = 32;
	private int lenOfGenMutation = lenOfGen / 6;
	private Valuer valuer;
	private final ReinforcementLearning reinforcementLearning;
	private final Random firstClassEvolutionRandom;
	private final Random hybridEvolutionRandom;
	private final Random defendEvolutionRandom;
	private final Random mutationEvolutionRandom;
	private final Random somaMutationEvolutionRandom;
	private final Random makeChildEvolutionRandom;
	private final Random makeEvythgEvolutionRandom;
	private final Random makeBestChildRandom;

	public AiEvolution(int lenOfGen) {
		this.lenOfGen = lenOfGen;
		reinforcementLearning = new ReinforcementLearning();
		firstClassEvolutionRandom = new Random();
		hybridEvolutionRandom = new Random();
		defendEvolutionRandom = new Random();
		mutationEvolutionRandom = new Random();
		somaMutationEvolutionRandom = new Random();
		makeChildEvolutionRandom = new Random();
		makeEvythgEvolutionRandom = new Random();
		makeBestChildRandom = new Random();
		init();
	}

	private void init() {
		valuer = new Valuer();
	}

	public int getLenOfGen() {
		return lenOfGen;
	}

	public void setLenOfGen(int lenOfGen) {
		this.lenOfGen = lenOfGen;
		lenOfGenMutation = lenOfGen / 6;
	}

	public int getLenOfGenMutation() {
		return lenOfGenMutation;
	}

	public void setLenOfGenMutation(int lenOfGenMutation) {
		this.lenOfGenMutation = lenOfGenMutation;
	}

	public Valuer getValuer() {
		return valuer;
	}

	public void setValuer(Valuer valuer) {
		this.valuer = valuer;
	}

	public ReinforcementLearning getReinforcementLearning() {
		return reinforcementLearning;
	}

	private GENE Hybrid(GENE gene, GENE gene2, double HybridRatio) {
		double rt = hybridEvolutionRandom.nextDouble();
		if (rt > HybridRatio)
			return null;
		double[] DNA1 = gene.getGene();
		double[] DNA2 = gene2.getGene();

		double[] fusion = new double[lenOfGen];
		for (int i = 0; i < lenOfGen; i++) {
			fusion[i] = (DNA1[i] + DNA2[i]) / 2;
		}

		GENE candidate = new GENE();
		candidate.setGene(fusion);
		return candidate;
	}

	private GENE Defend(GENE gene, GENE gene2, double Defend) {
		double rt = defendEvolutionRandom.nextDouble();
		if (rt > Defend)
			return null;
		double[] DNA1 = gene.getGene();
		double[] DNA2 = gene2.getGene();

		double[] Defendend = new double[lenOfGen];
		for (int i = 0; i < lenOfGen; i++) {
			double df = (DNA1[i] - DNA2[i]) * 2;
			Defendend[i] = Math.abs(df - 1);
		}

		GENE candidate = new GENE();
		candidate.setGene(Defendend);
		return candidate;
	}

	private GENE Mutation(GENE candidate, double mutantRatio) {
		double rt = mutationEvolutionRandom.nextFloat(); // Double!!??
		if (rt > mutantRatio)
			return candidate;
		int type = (int) mutationEvolutionRandom.nextInt(3);
		int knot = (int) (lenOfGen / (3.2 - 1.2 * mutationEvolutionRandom.nextDouble()));
		if (type < 0) {
			// dao doan 1
			double[] child = candidate.getGene();
			for (int i = 0; i < knot; i++) {
				double tmp = child[i];
				child[i] = child[lenOfGen - i - 1];
				child[lenOfGen - i - 1] = tmp;
			}
			return candidate;
		} else if (type < 1) {
			// dao doan 2
			double[] child = candidate.getGene();
			for (int i = 0; i < knot; i++) {
				double tmp = child[i];
				child[i] = child[lenOfGen / 2 + i];
				child[lenOfGen / 2 + i] = tmp;
			}
			return candidate;
		} else {
			// thay the
			int numOfMutPoint = (int) (lenOfGenMutation /* 6 */ + (mutationEvolutionRandom.nextBoolean() ? 3 : -3));
			for (int i = 0; i < numOfMutPoint; i++) {
				int genIdx = mutationEvolutionRandom.nextInt(lenOfGen);
				if (mutationEvolutionRandom.nextBoolean()) {
					candidate.getGene()[genIdx] = (candidate.getGene()[genIdx] >= 0.5) ? 0 : 1;
				} else {
					candidate.getGene()[genIdx] = mutationEvolutionRandom.nextDouble();
				}
			}
			return candidate;
		}
	}

	private void SomaMutation(GENE candidate, double SomaMutationRatio) {
		double rt = somaMutationEvolutionRandom.nextDouble();
		if (rt > SomaMutationRatio)
			return;
//		System.out.println("rt " + rt + " SomaMutation " + SomaMutationRatio);
		int len = candidate.getGene().length;
		int DNAposi = (int) somaMutationEvolutionRandom.nextInt(len);
		if (somaMutationEvolutionRandom.nextBoolean()) {
			candidate.getGene()[DNAposi] = (candidate.getGene()[DNAposi] >= 0.5) ? 0 : 1;
		} else {
			candidate.getGene()[DNAposi] = somaMutationEvolutionRandom.nextDouble();
		}
	}

	private GENE MakeChild(GENE gene, GENE gene2) {
		double[] DNA1 = gene.getGene();
		double[] DNA2 = gene2.getGene();
		double[] childDNA = new double[lenOfGen];
		int knot = (int) (lenOfGen / (makeChildEvolutionRandom.nextDouble() + 2.5));
		int randomChild = makeChildEvolutionRandom.nextInt(2);
		if (randomChild == 0) {
			for (int i = 0; i < lenOfGen; i++) {
				if (i > knot) {
					childDNA[i] = DNA1[i];
				} else {
					childDNA[i] = DNA2[i];
				}
			}
		} else if (randomChild == 1) {
			for (int i = 0; i < lenOfGen; i++) {
				if (i > knot) {
					childDNA[i] = DNA2[i];
				} else {
					childDNA[i] = DNA1[i];
				}
			}
		}
		GENE candidate = new GENE();
		candidate.setGene(childDNA);
		return candidate;
	}

	private GENE[] MakeEverything(GENE gene, GENE gene2, GENE gene3, double makeEverythingRatio) {
		double rt = makeEvythgEvolutionRandom.nextDouble();
		if (rt > makeEverythingRatio)
			return null;
		double[] DNA1 = gene.getGene();
		double[] DNA2 = gene2.getGene();
		double[] DNA3 = gene3.getGene();
		GENE[] result = new GENE[6];
		int x = 0;
		for (int i = 1; i < 4; i++) {
			for (int j = 1; j < 4; j++) {
				if (j != i)
					for (int k = 1; k < 4; k++) {
						if (k != j && k != i) {
							result[x] = new GENE();
							double[] gen = new double[lenOfGen];
							for (int g = 0; g < lenOfGen; g++) {
								gen[g] = (i * DNA1[g] + j * DNA2[g] + k * DNA3[g]) / 6;
							}
							result[x].setGene(gen);
							x++;

						}
					}
			}
		}
		return result;
	}

//	GENE[] FindBait(GENE gene, double findBaitRatio) {
//
//	}

//	GENE[] emulatorMatrix(GENE gene, double findBaitRatio) {
//		
//	}
// best child

	private GENE MakeBestChild(GENE gene, GENE gene2, double makeBestChildgRatio) {
		double rt = makeBestChildRandom.nextDouble();
		if (rt > makeBestChildgRatio)
			return null;
		double[] DNA1 = gene.getGene();
		double[] DNA2 = gene2.getGene();
		double[] childDNA = new double[lenOfGen];
		for (int i = 0; i < lenOfGen; i++) {
			double esilon1 = Math.abs(DNA1[i] - 0.5);
			double esilon2 = Math.abs(DNA2[i] - 0.5);
			childDNA[i] = esilon1 > esilon2 ? DNA1[i] : DNA2[i];
		}

		GENE candidate = new GENE();
		candidate.setGene(childDNA);
		return candidate;
	}

	public void Incorporate(ArrayList<GENE> pop, PlanOfchilds plcls, double makeBestChildgRatio, double MutantRatio,
			double SomaMutationRatio, double HybridRatio, double DefendRatio, double makeEverythingRatio) {

		int size = pop.size();
		for (int i = 0; i < size; i++) {

			int numOfChild = plcls.getNumofchildbyPlan();

			int woman = makeChildEvolutionRandom.nextInt(size);
			int fusionPathner = hybridEvolutionRandom.nextInt(size);
			int defendPathner = defendEvolutionRandom.nextInt(size);
			int[] makeEverythingPathner = new int[2];
			makeEverythingPathner[0] = makeEvythgEvolutionRandom.nextInt(size);
			makeEverythingPathner[1] = makeEvythgEvolutionRandom.nextInt(size);

			for (int c = 0; c < numOfChild; c++) {
				// System.out.println("MakeChild");
				GENE chil = MakeChild(pop.get(i), pop.get(woman));
				pop.add(Mutation(chil, MutantRatio));
				GENE bestChil = MakeBestChild(pop.get(i), pop.get(woman), makeBestChildgRatio);
				if (bestChil != null) {
					pop.add(bestChil);
				}

				// System.out.println("fusion");
				GENE fusion = Hybrid(pop.get(i), pop.get(fusionPathner), HybridRatio);
				if (fusion != null) {
					pop.add(fusion);
				}

				// System.out.println("defend");
				GENE defend = Defend(pop.get(i), pop.get(defendPathner), DefendRatio);
				if (defend != null) {
					pop.add(defend);
				}

				// System.out.println("SomaMutation");
				SomaMutation(pop.get(i), SomaMutationRatio);

				// System.out.println("makeEverything");
				GENE[] makeEverything = MakeEverything(pop.get(i), pop.get(makeEverythingPathner[0]),
						pop.get(makeEverythingPathner[1]), makeEverythingRatio);
				if (makeEverything != null) {
					for (int j = 0; j < makeEverything.length; j++) {
						pop.add(makeEverything[j]);
					}
				}

			}
		}
	}

	public ArrayList<GENE> getFirstClass(double firstClass_size) {
		ArrayList<GENE> rs = new ArrayList<>();
//		System.out.println(" lenOfGen " + lenOfGen);
		for (int i = 0; i < firstClass_size; i++) {
			GENE gene = new GENE();
			double[] g = new double[lenOfGen];
			for (int j = 0; j < lenOfGen; j++) {
				g[j] = firstClassEvolutionRandom.nextDouble();
			}
			gene.setGene(g);
			rs.add(gene);
		}
		return rs;
	}

	public ArrayList<EvaluatedCandidate> Value(ArrayList<GENE> part) {
		ArrayList<EvaluatedCandidate> result = new ArrayList<>();
		int size = part.size();
		for (int i = 0; i < size; i++) {
			EvaluatedCandidate evaluatedCandidate = new EvaluatedCandidate();
			GENE element = part.get(i);
			evaluatedCandidate.setCandidate(element);
			evaluatedCandidate.setIndex(valuer.getValue(element));
			result.add(evaluatedCandidate);
		}
		return result;
	}

}
