package dev.fijalkoa.Quizes;

import lombok.Data;

import java.util.Map;

@Data
public class QuizSubmissionDTO {
    private int quizId;
    private Map<Integer, Options> answers;
}
