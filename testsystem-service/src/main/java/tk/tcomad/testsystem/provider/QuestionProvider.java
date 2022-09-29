package tk.tcomad.testsystem.provider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tk.tcomad.testsystem.exception.NotFoundException;
import tk.tcomad.testsystem.model.domain.Question;
import tk.tcomad.testsystem.model.domain.Test;
import tk.tcomad.testsystem.model.mapper.TestMapper;
import tk.tcomad.testsystem.repository.TestRepository;

@Service
@RequiredArgsConstructor
public class QuestionProvider {

    @NonNull
    private final TestRepository testRepository;
    @NonNull
    private final TestMapper testMapper;

    public List<Question> getQuestionsByTestId(String testId) {
        Test test = testRepository.findById(testId)
                                  .map(testMapper::toDomain)
                                  .orElseThrow(() -> new NotFoundException("Test not found"));

        List<Question> questions = new ArrayList<>(test.getQuestions());
        Collections.shuffle(questions);

        return questions.stream()
                        .limit(test.getQuestionsNumber())
                        .peek(this::removeCorrectAnswers)
                        .collect(Collectors.toList());
    }

    private void removeCorrectAnswers(Question question) {
        question.getAnswers()
                .forEach(answer -> answer.setCorrect(false));
    }
}
