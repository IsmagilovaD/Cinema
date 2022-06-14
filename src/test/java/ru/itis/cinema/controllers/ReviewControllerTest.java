package ru.itis.cinema.controllers;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import ru.itis.cinema.dto.ReviewDto;
import ru.itis.cinema.dto.ReviewForm;
import ru.itis.cinema.dto.ReviewsPage;
import ru.itis.cinema.repositories.BlackListRepository;
import ru.itis.cinema.services.ReviewService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ReviewController.class)
@DisplayName("ReviewController is working when ...")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ReviewControllerTest {

    private static final ReviewDto REVIEW_ONE = ReviewDto.builder()
            .filmName("THE BATMAN")
            .authorName("Dinara Ismagilova")
            .text("Very spectacular film! I liked it!")
            .build();

    private static final ReviewDto REVIEW_TWO = ReviewDto.builder()
            .filmName("MORBIUS")
            .authorName("Dinara Ismagilova")
            .text("Everything about this movie is bad, I don't even know where to start.")
            .build();

    private static final List<ReviewDto> REVIEW_LIST = Arrays.asList(REVIEW_ONE, REVIEW_TWO);

    private static final ReviewDto CREATED_REVIEW = ReviewDto.builder()
            .filmName("THE BATMAN")
            .authorName("Dinara Ismagilova")
            .text("Very spectacular film! I liked it!")
            .build();

    private static final ReviewForm NEW_REVIEW = ReviewForm.builder()
            .filmName("THE BATMAN")
            .text("Very spectacular film! I liked it!")
            .build();


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private BlackListRepository blackListRepository;

    @Value("${test.token}")
    private String token;

    @BeforeEach
    void setUp() {
        when(reviewService.getReviews(1L, 0)).thenReturn(ReviewsPage.builder()
                .reviews(REVIEW_LIST)
                .totalPages(1).build());

        when(reviewService.postReview(NEW_REVIEW, token.substring("Bearer ".length()))).thenReturn(CREATED_REVIEW);
    }

    @Test
    public void return_403_without_token() throws Exception {
        mockMvc.perform(post("/review")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"filmName\": \"THE BATMAN\",\n" +
                                "  \"text\": \"Very spectacular film! I liked it!\"\n" +
                                "}"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void return_400_when_post_review_with_blank_fields() throws Exception {
        mockMvc.perform(post("/review")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"filmName\": \"\",\n" +
                                "  \"text\": \"\"\n" +
                                "}"))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void post_valid_review() throws Exception {
        mockMvc.perform(post("/review")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"filmName\" : \"THE BATMAN\",\n" +
                                "  \"text\" : \"Very spectacular film! I liked it!\"\n" +
                                "}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("filmName", is("THE BATMAN")))
                .andExpect(jsonPath("authorName", is("Dinara Ismagilova")))
                .andExpect(jsonPath("text", is("Very spectacular film! I liked it!")))
                .andDo(print());
    }

    @Test
    public void return_reviews_on_0_page() throws Exception {
        mockMvc.perform(get("/review/1")
                        .param("page", "0")
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("reviews", hasSize(2)))
                .andExpect(jsonPath("totalPages", is(1)))
                .andExpect(jsonPath("reviews[0].filmName", is("THE BATMAN")))
                .andExpect(jsonPath("reviews[0].authorName", is("Dinara Ismagilova")))
                .andExpect(jsonPath("reviews[0].text", is("Very spectacular film! I liked it!")))
                .andExpect(jsonPath("reviews[1].filmName", is("MORBIUS")))
                .andExpect(jsonPath("reviews[1].authorName", is("Dinara Ismagilova")))
                .andExpect(jsonPath("reviews[1].text", is("Everything about this movie is bad, I don't even know where to start.")));
    }


}