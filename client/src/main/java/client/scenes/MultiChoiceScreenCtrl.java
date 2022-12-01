package client.scenes;

import client.model.QuestionData;
import client.service.MessageLogicService;
import com.google.inject.Inject;
import commons.model.Question;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.util.Random;

public class MultiChoiceScreenCtrl extends QuestionCtrl<Question.MultiChoiceQuestion> {

	private static final int FIT_WIDTH = 168;
	private static final int FIT_HEIGHT = 112;

	@FXML
	private Button optionA;

	@FXML
	private Button optionB;

	@FXML
	private Button optionC;

	private int selectedAnswer = -1;

	@FXML
	private ImageView activityA;

	@FXML
	private ImageView activityB;

	@FXML
	private ImageView activityC;

	@FXML
	private Label scoreMessage;

	@Inject
	public MultiChoiceScreenCtrl(MessageLogicService messageService, MainCtrl mainCtrl) {
		super(messageService, mainCtrl);
	}

	@Override
	public void init() {
		super.init();
		optionA.setStyle(null);
		optionB.setStyle(null);
		optionC.setStyle(null);
		scoreMessage.setText("");
	}

	@Override
	public void setQuestion(QuestionData<Question.MultiChoiceQuestion> questionData) {
		super.setQuestion(questionData);

		var question = questionData.question();
		var textQuestion = "Which of the following takes the most energy?";
		setQuestionText(textQuestion);

		var a = question.activities().get(0).name();
		var b = question.activities().get(1).name();
		var c = question.activities().get(2).name();
		setAnswerOptions(a, b, c);

		var imageA = question.activities().get(0).imageUrl();
		var imageB = question.activities().get(1).imageUrl();
		var imageC = question.activities().get(2).imageUrl();
		setActivityImages(imageA, imageB, imageC);

		selectedAnswer = -1;
	}

	private void setAnswerOptions(String a, String b, String c) {
		this.optionA.setText(a);
		this.optionB.setText(b);
		this.optionC.setText(c);

		optionA.setDisable(false);
		optionB.setDisable(false);
		optionC.setDisable(false);
	}

	public void setActivityImages(String a, String b, String c) {
		setImage(activityA, a, FIT_WIDTH, FIT_HEIGHT);
		setImage(activityB, b, FIT_WIDTH, FIT_HEIGHT);
		setImage(activityC, c, FIT_WIDTH, FIT_HEIGHT);
	}

	public void optionAClicked() {
		scoreMessage.setText("Answer saved!");
		selectedAnswer = 0;
		messageService.answerQuestion(0);
		markAnswerGiven();
	}

	public void optionBClicked() {
		scoreMessage.setText("Answer saved!");
		selectedAnswer = 1;
		messageService.answerQuestion(1);
		markAnswerGiven();
	}

	public void optionCClicked() {
		scoreMessage.setText("Answer saved!");
		selectedAnswer = 2;
		messageService.answerQuestion(2);
		markAnswerGiven();
	}

	public void showAnswer(int option, int numberOfPlayersScored) {
		switch (option) {
			case 0:
				optionA.setStyle("-fx-background-color: #8dcc4f; ");

				optionA.setDisable(true);
				optionB.setDisable(true);
				optionC.setDisable(true);

				if (selectedAnswer == 1) {
					optionB.setStyle("-fx-background-color: #cc4f4f; ");
				}
				if (selectedAnswer == 2) {
					optionC.setStyle("-fx-background-color: #cc4f4f; ");
				}

				break;
			case 1:
				optionB.setStyle("-fx-background-color: #8dcc4f; ");

				optionA.setDisable(true);
				optionB.setDisable(true);
				optionC.setDisable(true);

				if (selectedAnswer == 0) {
					optionA.setStyle("-fx-background-color: #cc4f4f; ");
				} else if (selectedAnswer == 2) {
					optionC.setStyle("-fx-background-color: #cc4f4f; ");
				}

				break;
			case 2:
				optionC.setStyle("-fx-background-color: #8dcc4f; ");

				optionA.setDisable(true);
				optionB.setDisable(true);
				optionC.setDisable(true);

				if (selectedAnswer == 0) {
					optionA.setStyle("-fx-background-color: #cc4f4f; ");
				} else if (selectedAnswer == 1) {
					optionB.setStyle("-fx-background-color: #cc4f4f; ");
				}

				break;
		}
		String message = "";
		if (numberOfPlayersScored != -1) {
			if (numberOfPlayersScored == 1) {
				message += numberOfPlayersScored + " player scored on this question.";
			} else {
				message += numberOfPlayersScored + " players scored on this question.";
			}
		}
		scoreMessage.setText(message);
		timeStop();
		disableJokers();
	}

	@Override
	public void useEliminateOptionJoker() {
		super.useEliminateOptionJoker();

		var correctAnswer = getQuestionData().question().correctAnswer();
		switch (correctAnswer) {
			case 0 -> disableOneOf(optionB, optionC);
			case 1 -> disableOneOf(optionA, optionC);
			case 2 -> disableOneOf(optionA, optionB);
		}
	}

	private void disableOneOf(Button option1, Button option2) {
		var randomBool = new Random().nextBoolean();
		var randomOption = randomBool ? option1 : option2;
		randomOption.setDisable(true);
	}
}
