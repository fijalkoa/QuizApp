package dev.fijalkoa.Quizes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    private int questionId;
    private String question;
    private Map<Options, String> options;
    private Map<Options, Integer> scores;
}
