package dev.fijalkoa.Quizes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String getSingleQuiz(@PathVariable int quizId, Model model) {
        Optional<Quiz> quiz = quizService.singleQuiz(quizId);
        model.addAttribute("quiz", quiz.get());
        return "quizForm";
    }

    @PostMapping("/submit")
    public String submitQuiz(@ModelAttribute QuizSubmissionDTO submissionDTO, Model model) {
        Result result = quizService.calculateResult(submissionDTO);
        Optional<Quiz> quiz = quizService.singleQuiz(submissionDTO.getQuizId());
        model.addAttribute("result", result);
        model.addAttribute("quiz", quiz.get());
        return "score";
    }
}
