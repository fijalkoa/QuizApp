package dev.fijalkoa.Quizes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;
    public List<Quiz> allQuizzes() {
        return quizRepository.findAll();
    }

    public Optional<Quiz> singleQuiz(int quizId) {
        return quizRepository.findQuizByQuizId(quizId);
    }

    public Result calculateResult(QuizSubmissionDTO submission) {
        Optional<Quiz> quizOptional = quizRepository.findQuizByQuizId(submission.getQuizId());
        if (quizOptional.isEmpty()) {
            throw new IllegalArgumentException("Quiz not found");
        }
        Quiz quiz = quizOptional.get();
        int totalScore = 0;

        for (Question question : quiz.getQuestions()) {
            Options selectedOption = submission.getAnswers().get(question.getQuestionId());
            if (selectedOption != null) {
                totalScore += question.getScores().get(selectedOption);
            }
        }

        for (Result result : quiz.getResults()) {
            if (totalScore > Integer.parseInt(result.getRange().split(" ")[1])) {
                result.setScore(totalScore);
                return result;
            }
        }

        System.out.println("total score: " + totalScore);
        throw new IllegalArgumentException("No matching result found for the score");
    }
}
