package dev.fijalkoa.Quizes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        Quiz quiz = quizService.singleQuiz(quizId);
        model.addAttribute("quiz", quiz);
        return "quizForm";
    }

    @PostMapping("/submit")
    public String submitQuiz(@ModelAttribute QuizSubmissionDTO submissionDTO, Model model) {
        Result result = quizService.calculateResult(submissionDTO);
        Quiz quiz = quizService.singleQuiz(submissionDTO.getQuizId());
        model.addAttribute("result", result);
        model.addAttribute("quiz", quiz);
        return "score";
    }

}
