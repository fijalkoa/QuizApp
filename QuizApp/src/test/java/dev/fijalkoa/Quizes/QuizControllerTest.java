package dev.fijalkoa.Quizes;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(QuizController.class)
public class QuizControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuizService quizService;

    @Test
    public void shouldGetAllQuizzes() throws Exception {
        // given

        // when

        // then
        mockMvc.perform(get("/api/v1/quizzes"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("quizzes"));
    }

    @Test
    public void shouldGetSingleQuiz() throws Exception {
        // given
        int quizId = 4;
        Quiz quiz = new Quiz();
        when(quizService.singleQuiz(quizId)).thenReturn(quiz);

        // when

        // then
        mockMvc.perform(get("/api/v1/quizzes/{quizId}", quizId))
                .andExpect(status().isOk())
                .andExpect(view().name("quizForm"))
                .andExpect(model().attributeExists("quiz"));
    }
}
