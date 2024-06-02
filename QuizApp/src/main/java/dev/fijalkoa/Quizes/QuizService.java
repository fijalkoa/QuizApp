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
}
