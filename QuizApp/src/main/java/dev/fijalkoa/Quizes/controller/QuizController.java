package dev.fijalkoa.Quizes.controller;

import dev.fijalkoa.Quizes.model.dto.QuizSubmissionDTO;
import dev.fijalkoa.Quizes.model.entity.Quiz;
import dev.fijalkoa.Quizes.model.entity.Result;
import dev.fijalkoa.Quizes.service.EmailService;
import dev.fijalkoa.Quizes.service.QuizService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.List;

@Controller
@RequestMapping("/api/v1/quizzes")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @Lazy
    @Autowired
    private EmailService emailService;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @GetMapping
    public String getAllQuizzes(Model model) {
        List<Quiz> quizzes = quizService.allQuizzes();
        model.addAttribute("quizzes", quizzes);
        return "index";
    }
    @GetMapping("/{quizId}")
    public String getSingleQuiz(@PathVariable int quizId, HttpSession session, Model model) {
        Quiz quiz = quizService.singleQuiz(quizId);
        session.setAttribute("quiz", quiz);
        model.addAttribute("quiz", quiz);
        return "quizForm";
    }

    @PostMapping("/submit")
    public String submitQuiz(@ModelAttribute QuizSubmissionDTO submissionDTO, HttpSession session, Model model) {
        Result result = quizService.calculateResult(submissionDTO);
        session.setAttribute("result", result);
        model.addAttribute("result", result);
        model.addAttribute("quiz", session.getAttribute("quiz"));
        return "score";
    }

    @PostMapping("/sendResultEmail")
    public String sendResultEmail(HttpSession session, @RequestParam String userEmail, @RequestParam String name)
            throws MessagingException {
        Quiz quiz = (Quiz) session.getAttribute("quiz");
        Result result = (Result) session.getAttribute("result");

        Context context = new Context();
        context.setVariable("quiz", quiz);
        context.setVariable("result", result);

        String htmlContent = templateEngine.process("mail.html", context);

        String subject = name.toUpperCase() + ", your '" + quiz.getTitle() + "' test results!";

        emailService.sendHtmlMessage(userEmail, subject, htmlContent);
        return "redirect:/api/v1/quizzes";
    }

}
