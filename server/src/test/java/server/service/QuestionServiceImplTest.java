package server.service;

import commons.model.Activity;
import commons.model.Question;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import server.entity.ActivityEntity;
import server.repository.ActivityRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class QuestionServiceImplTest {
	private static final Activity FAKE_ACTIVITY = new Activity(0, "A1", null, 1f);
	private static final List<Activity> FAKE_ACTIVITY_LIST = List.of(
			FAKE_ACTIVITY,
			new Activity(1, "A2", null, 2f),
			new Activity(2, "A3", null, 3f)
	);
	private static final List<ActivityEntity> FAKE_ACTIVITY_ENTITY_LIST = List.of(
			new ActivityEntity(0, "A1", null, 1f),
			new ActivityEntity(1, "A2", null, 2f),
			new ActivityEntity(2, "A3", null, 3f)
	);
	private static final List<Float> FAKE_ENERGIES = List.of(0.5f, 15f, 25f);


	@Mock
	private ActivityRepository activityRepository;

	private QuestionServiceImpl createService() {
		return new QuestionServiceImpl(activityRepository);
	}


	@Test
	public void correct_answer_for_mc_question_should_give_max_score() {
		var service = createService();
		var question = new Question.MultiChoiceQuestion(FAKE_ACTIVITY_LIST, 2);
		var score = service.calculateScore(question, 2, 1000L, false);

		assertEquals(QuestionServiceImpl.MAX_SCORE, score);
	}

	@Test
	public void wrong_answer_for_mc_question_should_give_zero_score() {
		var service = createService();
		var question = new Question.MultiChoiceQuestion(FAKE_ACTIVITY_LIST, 2);
		var score = service.calculateScore(question, 1, 1000L, false);

		assertEquals(0, score);
	}

	@Test
	public void close_answer_for_est_question_should_give_max_score() {
		var service = createService();
		var question = new Question.EstimationQuestion(FAKE_ACTIVITY, 100f);
		var score = service.calculateScore(question, 95f, 1000L, false);

		assertEquals(QuestionServiceImpl.MAX_SCORE, score);
	}

	@Test
	public void distant_answer_for_est_question_should_give_zero_score() {
		var service = createService();
		var question = new Question.EstimationQuestion(FAKE_ACTIVITY, 100f);
		var score = service.calculateScore(question, 40f, 1000L, false);

		assertEquals(0, score);
	}

	@Test
	public void medium_answer_for_est_question_should_partial_score() {
		var service = createService();
		var question = new Question.EstimationQuestion(FAKE_ACTIVITY, 100f);
		var score = service.calculateScore(question, 80f, 1000L, false);
		assertTrue(score > 0);
		assertTrue(score < QuestionServiceImpl.MAX_SCORE);
	}

	@Test
	public void close_answer_for_comp_question_should_give_max_score() {
		var service = createService();
		var question = new Question.ComparisonQuestion(FAKE_ACTIVITY_LIST.subList(0, 2), 1f);
		var score = service.calculateScore(question, 1.1f, 1000L, false);

		assertEquals(99, score);
	}

	@Test
	public void distant_answer_for_comp_question_should_give_zero_score() {
		var service = createService();
		var question = new Question.ComparisonQuestion(FAKE_ACTIVITY_LIST.subList(0, 2), 1f);
		var score = service.calculateScore(question, 2.1f, 1000L, false);

		assertEquals(0, score);
	}

	@Test
	public void medium_answer_for_comp_question_should_partial_score() {
		var service = createService();
		var question = new Question.ComparisonQuestion(FAKE_ACTIVITY_LIST.subList(0, 2), 1f);
		var score = service.calculateScore(question, 1.5f, 1000L, false);

		assertTrue(score > 0);
		assertTrue(score < QuestionServiceImpl.MAX_SCORE);
	}

	@Test
	public void correct_answer_for_pick_question_should_give_max_score() {
		var service = createService();
		var question = new Question.PickEnergyQuestion(FAKE_ACTIVITY, 2, FAKE_ENERGIES);
		var score = service.calculateScore(question, 2, 1000L, false);

		assertEquals(QuestionServiceImpl.MAX_SCORE, score);
	}

	@Test
	public void wrong_answer_for_pick_question_should_give_zero_score() {
		var service = createService();
		var question = new Question.PickEnergyQuestion(FAKE_ACTIVITY, 2, FAKE_ENERGIES);
		var score = service.calculateScore(question, 1, 1000L, false);

		assertEquals(0, score);
	}

	@Test
	public void pick_energy_answer_generator_should_generate_positive_answers() {
		var service = createService();
		var answerOptions = service.generatePickOptions(55, 2);

		assertTrue(answerOptions.get(0) > 0 && answerOptions.get(1) > 0
				&& answerOptions.get(2) > 0);
	}

	@Test
	public void pick_energy_answer_generator_should_generate_similar_answers() {
		var service = createService();
		var answerOptions = service.generatePickOptions(55, 2);

		assertTrue(answerOptions.get(0) <= answerOptions.get(2) * 2
				&& answerOptions.get(1) <= answerOptions.get(2) * 2);
	}

	@Test
	public void pick_energy_answer_generator_should_generate_different_answers() {
		var service = createService();
		var answerOptions = service.generatePickOptions(55, 2);

		assertTrue(answerOptions.get(0) != answerOptions.get(2)
				&& answerOptions.get(1) != answerOptions.get(2)
				&& answerOptions.get(0) != answerOptions.get(1));
	}

	@Test
	public void generate_MC_question_should_generate_three_activities() {
		var service = createService();
		when(activityRepository.findAll()).thenReturn(FAKE_ACTIVITY_ENTITY_LIST);
		var question = service.generateMC();

		assertEquals(question.activities().size(), 3);
	}

	@Test
	public void generate_MC_question_should_generate_activities() {
		var service = createService();
		when(activityRepository.findAll()).thenReturn(FAKE_ACTIVITY_ENTITY_LIST);
		var question = service.generateMC();

		assertTrue(FAKE_ACTIVITY_LIST.contains(question.activities().get(0))
				&& FAKE_ACTIVITY_LIST.contains(question.activities().get(1))
				&& FAKE_ACTIVITY_LIST.contains(question.activities().get(2)));
	}

	@Test
	public void generate_MC_question_should_generate_distinct_activities() {
		var service = createService();
		when(activityRepository.findAll()).thenReturn(FAKE_ACTIVITY_ENTITY_LIST);
		var question = service.generateMC();

		assertTrue(!question.activities().get(0).equals(question.activities().get(1))
				&& !question.activities().get(1).equals(question.activities().get(2)));
	}

	@Test
	public void generate_comparison_question_should_generate_two_activities() {
		var service = createService();
		when(activityRepository.findAll()).thenReturn(FAKE_ACTIVITY_ENTITY_LIST);
		var question = service.generateComp();

		assertEquals(question.activities().size(), 2);
	}

	@Test
	public void generate_comparison_question_should_generate_activities() {
		var service = createService();
		when(activityRepository.findAll()).thenReturn(FAKE_ACTIVITY_ENTITY_LIST);
		var question = service.generateComp();

		assertTrue(FAKE_ACTIVITY_LIST.contains(question.activities().get(0))
				&& FAKE_ACTIVITY_LIST.contains(question.activities().get(1)));
	}

	@Test
	public void generate_comparison_question_should_generate_distinct_activities() {
		var service = createService();
		when(activityRepository.findAll()).thenReturn(FAKE_ACTIVITY_ENTITY_LIST);
		var question = service.generateComp();

		assertNotEquals(question.activities().get(0), question.activities().get(1));
	}

	@Test
	public void generate_estimation_question_should_generate_an_activity() {
		var service = createService();
		when(activityRepository.findAll()).thenReturn(FAKE_ACTIVITY_ENTITY_LIST);
		var question = service.generateEst();

		assertTrue(FAKE_ACTIVITY_LIST.contains(question.activity()));
	}

	@Test
	public void generate_pick_energy_question_should_generate_an_activity() {
		var service = createService();
		when(activityRepository.findAll()).thenReturn(FAKE_ACTIVITY_ENTITY_LIST);
		var question = service.generatePick();

		assertTrue(FAKE_ACTIVITY_LIST.contains(question.activity()));
	}
}
