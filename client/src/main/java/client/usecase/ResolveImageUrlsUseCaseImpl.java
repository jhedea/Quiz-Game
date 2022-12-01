package client.usecase;

import client.service.ServerService;
import com.google.inject.Inject;
import commons.model.Activity;
import commons.model.Question;

import java.util.List;

public class ResolveImageUrlsUseCaseImpl implements ResolveImageUrlsUseCase {
	private static final String SERVER_ADDRESS_PLACEHOLDER = "${SERVER_ADDRESS}";

	private final ServerService serverService;

	@Inject
	public ResolveImageUrlsUseCaseImpl(ServerService serverService) {
		this.serverService = serverService;
	}

	@Override
	public Question resolveImageUrls(Question question) {
		if (question instanceof Question.ComparisonQuestion q) {
			return new Question.ComparisonQuestion(resolveImageUrls(q.activities()), q.correctAnswer());
		} else if (question instanceof Question.EstimationQuestion q) {
			return new Question.EstimationQuestion(resolveImageUrl(q.activity()), q.correctAnswer());
		} else if (question instanceof Question.MultiChoiceQuestion q) {
			return new Question.MultiChoiceQuestion(resolveImageUrls(q.activities()), q.correctAnswer());
		} else if (question instanceof Question.PickEnergyQuestion q) {
			return new Question.PickEnergyQuestion(resolveImageUrl(q.activity()), q.correctAnswer(), q.answerOptions());
		} else {
			throw new IllegalStateException("Unknown question type: " + question); // Won't happen
		}
	}

	private List<Activity> resolveImageUrls(List<Activity> activities) {
		return activities.stream().map(this::resolveImageUrl).toList();
	}

	private Activity resolveImageUrl(Activity activity) {
		var newUrl = resolveImageUrl(activity.imageUrl());
		return activity.copyWithImageUrl(newUrl);
	}

	private String resolveImageUrl(String imageUrl) {
		return imageUrl.replace(SERVER_ADDRESS_PLACEHOLDER, serverService.getServerAddress());
	}
}
