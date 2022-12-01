package commons.servermessage;

import commons.model.JokerType;
import commons.model.Question;

import java.util.HashSet;
import java.util.Set;

public record QuestionMessage(
		Question question,
		int questionNumber,
		boolean reduceTimeAvailable,
		boolean doublePointsAvailable,
		boolean eliminateMCOptionAvailable
) {
	public QuestionMessage(Question question, int questionNumber) {
		this(question, questionNumber, true, true, true);
	}

	public Set<JokerType> getAvailableJokers() {
		var set = new HashSet<JokerType>();
		if (reduceTimeAvailable) set.add(JokerType.REDUCE_TIME);
		if (doublePointsAvailable) set.add(JokerType.DOUBLE_POINTS);
		if (eliminateMCOptionAvailable) set.add(JokerType.ELIMINATE_MC_OPTION);
		return set;
	}
}
