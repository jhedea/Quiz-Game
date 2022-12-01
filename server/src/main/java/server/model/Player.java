package server.model;

import commons.model.JokerType;
import lombok.Getter;

import java.util.HashMap;

@Getter
public class Player {
	private final String name;
	private final int playerId;
	private final HashMap<JokerType, Boolean> jokerAvailability = new HashMap<>();
	private Number latestAnswer;
	private long timeTakenToAnswer;
	private int score = 0;
	private boolean doublePointsForCurrentQuestion;

	public Player(String name, int playerId) {
		this.name = name;
		this.playerId = playerId;
		init();
	}

	public Player(String name, int playerId, int score) {
		this(name, playerId);
		this.score = score;
	}

	private void init() {
		jokerAvailability.put(JokerType.REDUCE_TIME, true);
		jokerAvailability.put(JokerType.DOUBLE_POINTS, true);
		jokerAvailability.put(JokerType.ELIMINATE_MC_OPTION, true);
	}

	public void incrementScore(int scoreDelta) {
		score += scoreDelta;
	}

	public void setLatestAnswer(Number answer) {
		this.latestAnswer = answer;
	}

	public void setTimeTakenToAnswer(Long time) {
		this.timeTakenToAnswer = time;
	}

	public void setJokerAvailability(JokerType type, boolean isAvailable) {
		jokerAvailability.put(type, isAvailable);
	}

	public void setDoublePointsForCurrentQuestion(boolean doublePoints) {
		this.doublePointsForCurrentQuestion = doublePoints;
	}
}
