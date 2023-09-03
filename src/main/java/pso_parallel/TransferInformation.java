package pso_parallel;

import java.util.ArrayList;

import pso_network.Connection;
import pso_network.Foodlocation;
import pso_network.Individual;

public class TransferInformation extends Thread {
	private ArrayList<Individual> population;
	private int maxHerdSize;

	public TransferInformation(ArrayList<Individual> population, int maxHerdSize) {
		this.population = population;
		this.maxHerdSize = maxHerdSize;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		int size = population.size();
		for (int i = 0; i < size; i++) {
			Individual source = population.get(i);
			ArrayList<Connection> connectFromMe = source.getConnectFromMe();
			ArrayList<Connection> connectToMe = source.getConnectToMe();
			Foodlocation message = null;
			double max = source.getValue();
			message = new Foodlocation(source.getPosition(), max);
			for (Connection connection : connectToMe) {
				double tmp = connection.getSource().getValue();
				if (max < tmp) {
					max = tmp;
					message = new Foodlocation(connection.getSource().getPosition(), tmp);
				}
			}
			for (Connection connection : connectFromMe) {
				double tmp = connection.getDest().getValue();
				if (max < tmp) {
					max = tmp;
					message = new Foodlocation(connection.getSource().getPosition(), tmp);
				}
			}
			source.setFoodlocation(new Foodlocation(source.getPosition(), source.getValue()));
			if (max < source.getValue()) {
				message = new Foodlocation(source.getPosition(), source.getValue());
				source.setFoodlocation(message);
			}

			int connectFromMeSize = source.getConnectFromMe().size();
			for (int j = 0; j < connectFromMeSize; j++) {
				pushMessage(connectFromMe.get(j).getDest().getFriendMessage(), message);
			}

			int connectToMeeSize = connectToMe.size();
			for (int j = 0; j < connectToMeeSize; j++) {
				pushMessage(connectToMe.get(j).getSource().getFriendMessage(), message);
			}

		}

	}

	private void pushMessage(ArrayList<Foodlocation> arrayList, Foodlocation message) {
		if (message != null) {
			synchronized (arrayList) {
				arrayList.add(message);
			}
			int fullyConnectSize = maxHerdSize * 2;
			if (arrayList.size() <= fullyConnectSize) {
				return;
			}
			synchronized (arrayList) {
				arrayList.remove(0);
			}
		}
	}
}
