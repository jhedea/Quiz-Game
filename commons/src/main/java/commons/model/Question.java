package commons.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.List;

/**
 * Model of a question
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
		@Type(value = Question.MultiChoiceQuestion.class, name = "mc"),
		@Type(value = Question.ComparisonQuestion.class, name = "calculation"),
		@Type(value = Question.EstimationQuestion.class, name = "estimation"),
		@Type(value = Question.PickEnergyQuestion.class, name = "picking")
})
public interface Question {

	/**
	 * Question with x possible activities to choose, one of them is the correct answer
	 */
	record MultiChoiceQuestion(
			List<Activity> activities,
			int correctAnswer
	) implements Question { }

	/**
	 * Question with comparing 2 activities
	 */
	record ComparisonQuestion(
			List<Activity> activities,
			float correctAnswer
	) implements Question { }

	/**
	 * Question with estimation of energy consumption for 1 activity
	 */
	record EstimationQuestion(
			Activity activity,
			float correctAnswer
	) implements Question { }

	/**
	 * Question with estimation of energy consumption for 1 activity, with 3 possible answers
	 */
	record PickEnergyQuestion(
			Activity activity,
			int correctAnswer,
			List<Float> answerOptions
	) implements Question { }
}
