package client.usecase;

import commons.model.Question;

public interface ResolveImageUrlsUseCase {

	/**
	 * Converts image URLs to correspond to the URLs hosted on the game server.
	 *
	 * @param question the question with an incorrect image URL
	 * @return returns the input question with the correct image URL
	 */
	Question resolveImageUrls(Question question);
}
