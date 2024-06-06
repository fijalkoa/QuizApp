package dev.fijalkoa.Quizes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "quizzes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Quiz {

    @Id
    private ObjectId id;
    private int quizId;
    private String title;
    private String description;
    private List<Question> questions;
    private List<Result> results;
    private String image;
}
