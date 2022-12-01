package commons.clientmessage;

public record QuestionAnswerMessage(Integer answerInt, Float answerFloat) {

	public Number getAnswer() {
		if (answerInt != null) return answerInt;
		else return answerFloat;
	}
}
