package easytests.options;

import easytests.models.AnswerModelInterface;
import easytests.services.*;

import java.util.List;


/**
 * @author rezenbekk
 */
public interface AnswersOptionsInterface extends OptionsInterface {
    
    void setAnswersService(AnswersServiceInterface answersService);

    void setQuestionsService(QuestionsServiceInterface questionsService);

    AnswersOptionsInterface withQuestion(QuestionsOptionsInterface questionsOptions);

    AnswerModelInterface withRelations(AnswerModelInterface answerModel);

    List<AnswerModelInterface> withRelations(List<AnswerModelInterface> answerModels);

    void saveWithRelations(AnswerModelInterface answerModel);

    void deleteWithRelations(AnswerModelInterface answerModel);
}