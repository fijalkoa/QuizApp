package dev.fijalkoa.Quizes.service;

import dev.fijalkoa.Quizes.model.dto.QuizSubmissionDTO;
import dev.fijalkoa.Quizes.model.entity.Question;
import dev.fijalkoa.Quizes.model.entity.Quiz;
import dev.fijalkoa.Quizes.model.entity.Result;
import dev.fijalkoa.Quizes.model.enums.Options;
import dev.fijalkoa.Quizes.repository.QuizRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {

    private static final Logger logger = LoggerFactory.getLogger(QuizService.class);

    @Autowired
    private QuizRepository quizRepository;
    public List<Quiz> allQuizzes() {
        try {
            List<Quiz> quizzes = quizRepository.findAll();
            logger.info("Found " + quizzes.size() + " quizzes");
            return quizzes;
        } catch (Exception e) {
            logger.error("Error occurred while fetching quizzes", e);
            throw new RuntimeException("Unable to fetch quizzes at this time. " +
                    "Check your connection with database", e);
        }

    }

    public Quiz singleQuiz(int quizId) {
        return quizRepository.findQuizByQuizId(quizId)
                .orElseThrow(() -> new IllegalArgumentException("Quiz with id: " + quizId + " not found"));
    }

    public Result calculateResult(QuizSubmissionDTO submission) {
        Quiz quiz = quizRepository.findQuizByQuizId(submission.getQuizId())
                .orElseThrow(() -> new IllegalArgumentException("Quiz not found"));

        int totalScore = calculateTotalScore(submission, quiz.getQuestions());

        for (Result result : quiz.getResults()) {
            if (totalScore > Integer.parseInt(result.getRange().split(" ")[1])) {
                result.setScore(totalScore);
                return result;
            }
        }

        throw new IllegalArgumentException("No matching result found for the score");
    }

    private int calculateTotalScore(QuizSubmissionDTO submission, List<Question> questions) {
        int totalScore = 0;
        for (Question question : questions) {
            Options selectedOption = submission.getAnswers().get(question.getQuestionId());
            if (selectedOption != null) {
                totalScore += question.getScores().get(selectedOption);
            }
        }
        return totalScore;
    }
}
