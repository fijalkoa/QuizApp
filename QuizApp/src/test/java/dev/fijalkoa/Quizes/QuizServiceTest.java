package dev.fijalkoa.Quizes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class QuizServiceTest {

    @Mock
    private QuizRepository quizRepository;

    @InjectMocks
    private QuizService quizService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturnCorrectListOfQuizzes_WhenDatabaseNotEmpty() {
        // when
        Quiz quiz1 = new Quiz();
        quiz1.setQuizId(1);
        quiz1.setTitle("Quiz 1");

        Quiz quiz2 = new Quiz();
        quiz2.setQuizId(2);
        quiz2.setTitle("Quiz 2");

        List<Quiz> expectedQuizzes = Arrays.asList(quiz1, quiz2);
        when(quizRepository.findAll()).thenReturn(expectedQuizzes);

        // when
        List<Quiz> actualQuizzes = quizService.allQuizzes();

        // then
        assertNotNull(actualQuizzes);
        assertEquals(2, actualQuizzes.size());
        assertEquals("Quiz 1", actualQuizzes.get(0).getTitle());
        assertEquals("Quiz 2", actualQuizzes.get(1).getTitle());

        verify(quizRepository, times(1)).findAll();
    }

    @Test
    public void shouldReturnEmptyListOfQuizzes_WhenDatabaseIsEmpty() {
        // given
        when(quizRepository.findAll()).thenReturn(Collections.emptyList());

        // when
        List<Quiz> actualQuizzes = quizService.allQuizzes();

        // then
        assertNotNull(actualQuizzes);
        assertTrue(actualQuizzes.isEmpty());

        verify(quizRepository, times(1)).findAll();
    }

    @Test
    public void shouldThrowException_WhenCannotFetchFromDatabase() {
        // given
        when(quizRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        // when
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            quizService.allQuizzes();
        });

        // then
        assertEquals("Unable to fetch quizzes at this time. Check your connection with database"
                , exception.getMessage());

        verify(quizRepository, times(1)).findAll();
    }


    @Test
    public void shouldFindQuiz_WhenQuizIdIsValid() {
        // given
        int quizId = 1;
        Quiz quiz = new Quiz();
        when(quizRepository.findQuizByQuizId(quizId)).thenReturn(Optional.of(quiz));

        // when
        Quiz quiz2 = quizService.singleQuiz(quizId);

        // then
        assertNotNull(quiz2);
        assertEquals(quiz,quiz2);
    }

    @Test
    public void shouldThrowException_WhenInvalidQuizId() {
        // given
        int quizId = 0;
        when(quizRepository.findQuizByQuizId(quizId)).thenReturn(Optional.empty());

        // when
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                quizService.singleQuiz(quizId) );
        String actualMessage = exception.getMessage();

        // then
        String exceptedMessage = "Quiz with id: " + quizId + " not found";
        assertTrue(actualMessage.contains(exceptedMessage));
    }

    @Test
    public void should_ReturnCorrectResult_WhenPassedValidAnswersDTO() {
        // given
        int quizId = 3;
        int nOfQuestions = 10;
        QuizSubmissionDTO dto = new QuizSubmissionDTO(quizId,
                generateRandomChosenAnswersMap(nOfQuestions));

        Quiz quiz = new Quiz(null, quizId, "", ""
                , generateQuestionList(nOfQuestions)
                , List.of(new Result(1, "> 40", "Result", "Description", 0),
                    new Result(1, "> 20", "Result", "Description", 0))
                , "");

        when(quizRepository.findQuizByQuizId(quizId)).thenReturn(Optional.of(quiz));

        // when
        Result result = quizService.calculateResult(dto);

        // then
        int range = Integer.parseInt(result.getRange().split(" ")[1]);
        assertTrue(result.getScore() > range);

    }

    @Test
    public void should_ThrowException_WhenScoreDoesNotMatchAnyResult() {
        // given
        int quizId = 3;
        int nOfQuestions = 5;
        QuizSubmissionDTO dto = new QuizSubmissionDTO(quizId,
                generateRandomChosenAnswersMap(nOfQuestions));

        Quiz quiz = new Quiz(null, quizId, "", ""
                , generateQuestionList(nOfQuestions)
                , List.of(new Result(1, "> 40", "Result", "Description", 0))
                , "");

        when(quizRepository.findQuizByQuizId(quizId)).thenReturn(Optional.of(quiz));

        // when
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                quizService.calculateResult(dto) );
        String actualMessage = exception.getMessage();

        // then
        String exceptedMessage = "No matching result found for the score";
        assertTrue(actualMessage.contains(exceptedMessage));

    }

    private List<Question> generateQuestionList(int n) {
        List<Question> list = new ArrayList<>();
        for (int i =1; i <= n; i++) {
            Map<Options, Integer> scores = generateRandomMapOfScores();
            list.add(new Question(i, "", null, scores));
        }
        return list;
    }
    private Map<Options, Integer> generateRandomMapOfScores() {
        Map<Options, Integer> scores = new HashMap<>();
        for (Options option : Options.values()) {
            Random random = new Random();
            int min = 3;
            int max = 6;

            int randomNumber = random.nextInt((max - min) + 1) + min;
            scores.put(option, randomNumber);
        }
        return scores;
    }

    private Map<Integer, Options> generateRandomChosenAnswersMap(int n) {
        Map<Integer, Options> answers = new HashMap<>();

        for (int i = 1; i <= n; i++) {
            Random random = new Random();

            int min = 0;
            int max = 2;
            int randChoice = random.nextInt((max - min) + 1) + min;
            Options option = null;
            switch (randChoice) {
                case 0 -> option = Options.a;
                case 1 -> option = Options.b;
                default -> option = Options.c;
            }

            answers.put(i, option);
        }

        return answers;
    }
}
