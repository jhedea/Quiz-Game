package client.model;

import commons.model.JokerType;
import commons.model.Question;

import java.util.Set;

public record QuestionData<Q extends Question>(
		Q question,
		int questionNumber,
		int currentScore,
		GameType gameType,
		Set<JokerType> availableJokers
) { }
