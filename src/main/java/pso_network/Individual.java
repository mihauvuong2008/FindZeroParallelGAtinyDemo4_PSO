package pso_network;

import java.util.ArrayList;

public class Individual {
	private ArrayList<Connection> connectFromme;
	private ArrayList<Connection> connectTome;
	private Position position;
	private Direction direction;
	private ArrayList<Foodlocation> friendMessage;
	private Foodlocation foodlocation;
	private double speed = 0;// step per loop
	private double value;

	public Individual(Position position) {
		super();
		this.position = position;
		friendMessage = new ArrayList<>();
		connectFromme = new ArrayList<>();
		connectTome = new ArrayList<>();
	}

	public ArrayList<Connection> getConnectFromMe() {
		return connectFromme;
	}

	public void setConnectFromme(ArrayList<Connection> connectFromme) {
		this.connectFromme = connectFromme;
	}

	public ArrayList<Connection> getConnectToMe() {
		return connectTome;
	}

	public void setConnectTome(ArrayList<Connection> connectTome) {
		this.connectTome = connectTome;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public ArrayList<Foodlocation> getFriendMessage() {
		return friendMessage;
	}

	public void setFriendMessage(ArrayList<Foodlocation> friendMessage) {
		this.friendMessage = friendMessage;
	}

	public Foodlocation getFoodlocation() {
		return foodlocation;
	}

	public void setFoodlocation(Foodlocation foodlocation) {
		this.foodlocation = foodlocation;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

}
