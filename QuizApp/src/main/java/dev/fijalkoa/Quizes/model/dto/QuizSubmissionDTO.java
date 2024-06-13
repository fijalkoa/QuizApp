package dev.fijalkoa.Quizes.model.dto;

import dev.fijalkoa.Quizes.model.enums.Options;
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
