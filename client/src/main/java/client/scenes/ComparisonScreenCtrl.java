package client.scenes;

import client.model.QuestionData;
import client.service.MessageLogicService;
import client.utils.NumberUtils;
import com.google.inject.Inject;
import commons.model.Question;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

public class ComparisonScreenCtrl extends QuestionCtrl<Question.ComparisonQuestion> {

	private static final int FIT_WIDTH = 217;
	private static final int FIT_HEIGHT = 93;

	@FXML
	private TextField answer;

	@FXML
	private Label answerMessage;

	@FXML
	private Button ok;

	@FXML
	private Label errorMessage;

	@FXML
	private ImageView activityA;

	@FXML
	private ImageView activityB;

	@Inject
	public ComparisonScreenCtrl(MessageLogicService messageService, MainCtrl mainCtrl) {
		super(messageService, mainCtrl);
	}

	@Override
	public void init() {
		super.init();
		answer.setText("");
		answerMessage.setText("");
		resetError();
		resetCorrectAnswer();
	}

	@Override
	public void setQuestion(QuestionData<Question.ComparisonQuestion> questionData) {
		super.setQuestion(questionData);

		var question = questionData.question();
		var textActivity1 = question.activities().get(0).name();
		var textActivity2 = question.activities().get(1).name();
		var textQuestion = "Instead of " + textActivity1 + " , you can " + textActivity2 + " how many times?";
		setQuestionText(textQuestion);

		var imageA = question.activities().get(0).imageUrl();
		var imageB = question.activities().get(1).imageUrl();
		setActivityImages(imageA, imageB);

		ok.setDisable(false);
	}


	public void setActivityImages(String a, String b) {
		setImage(activityA, a, FIT_WIDTH, FIT_HEIGHT);
		setImage(activityB, b, FIT_WIDTH, FIT_HEIGHT);
	}

	public void sendAnswer() {
		var parsedValue = NumberUtils.parseFloatOrNull(answer.getText());
		answerMessage.setText("Answer saved!");
		if (parsedValue != null) {
			messageService.answerQuestion(parsedValue);
			markAnswerGiven();
			resetError();
		} else {
			errorMessage.setText("Invalid value");
		}
	}

	public void showAnswer(Number correctAnswer, int scoreIncrement, int numberOfPlayersScored) {
		String message = "The correct answer was: " + correctAnswer + " times. "
						+ "\nYou score " + scoreIncrement + " points.";
		if (numberOfPlayersScored != -1) {
			if (numberOfPlayersScored == 1) {
				message += "\n" + numberOfPlayersScored + " player scored on this question.";
			} else {
				message += "\n" + numberOfPlayersScored + " players scored on this question.";
			}
		}
		answerMessage.setText(message);
		ok.setDisable(true);
		timeStop();
		disableJokers();
	}

	private void resetError() {
		errorMessage.setText("");
	}

	private void resetCorrectAnswer() {
		answerMessage.setText("");
	}

	public void keyPressed(KeyEvent e) {
		switch (e.getCode()) {
			case ENTER:
				sendAnswer();
				break;
			default:
				break;
		}
	}

}
