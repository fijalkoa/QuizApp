package dev.fijalkoa.Quizes;

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
}
