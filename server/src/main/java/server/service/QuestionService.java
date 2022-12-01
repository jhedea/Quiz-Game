package server.service;

import commons.model.Question;

/**
 * Question generation service
 */
public interface QuestionService {
	/**
	 * Generates a new question.
	 *
	 * @param gameId the game the question is generated for
	 * @return generated question
	 */
	Question generateQuestion(int gameId);

	/**
	 * Calculates the score a player gets for an answer to a question.
	 *
	 * @param question the question that the player answered
	 * @param answer the answer that the player gave
	 * @param timeSpent the time the player spent answering the question
	 * @return the score of the player
	 */
	int calculateScore(Question question, Number answer, long timeSpent, boolean doublePoints);
}
