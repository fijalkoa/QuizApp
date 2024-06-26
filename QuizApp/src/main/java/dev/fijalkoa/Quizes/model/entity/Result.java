package dev.fijalkoa.Quizes.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {

    private int rangeId;
    private String range;
    private String result;
    private String description;
    private int score;
}
