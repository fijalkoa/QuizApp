package dev.fijalkoa.Quizes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/v1/quizzes")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @GetMapping
    public String getAllQuizzes(Model model) {
        List<Quiz> quizzes = quizService.allQuizzes();
        model.addAttribute("quizzes", quizzes);
        return "index";
    }

    @GetMapping("/{quizId}")
    public ResponseEntity<Optional<Quiz>> getSingleQuiz(@PathVariable int quizId) {
        return new ResponseEntity<>(quizService.singleQuiz(quizId), HttpStatus.OK);
    }

}
