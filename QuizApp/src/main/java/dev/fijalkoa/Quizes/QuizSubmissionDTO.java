package dev.fijalkoa.Quizes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class QuizSubmissionDTO {
    private int quizId;
    private Map<Integer, Options> answers;
}
