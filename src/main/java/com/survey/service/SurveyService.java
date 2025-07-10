package com.survey.service;

import com.survey.model.*;
import java.util.List;
import java.util.ArrayList;

public class SurveyService {
    private Survey currentSurvey;

    public SurveyService() {
        initializeDefaultSurvey();
    }

    private void initializeDefaultSurvey() {
        // Создаем опрос для студентов университета
        currentSurvey = new Survey(
            "Опрос удовлетворенности учебным процессом",
            "Данный опрос направлен на улучшение качества образования в нашем университете. " +
            "Ваши ответы помогут нам сделать учебный процесс более эффективным и комфортным."
        );

        // Добавляем вопросы
        currentSurvey.addQuestion(new Question(
            "Как вы оцениваете качество преподавания в целом?",
            List.of("Отлично", "Хорошо", "Удовлетворительно", "Плохо"),
            "multiple_choice",
            true
        ));

        currentSurvey.addQuestion(new Question(
            "Насколько комфортно вы чувствуете себя в университете?",
            List.of("Очень комфортно", "Комфортно", "Нейтрально", "Дискомфортно", "Очень дискомфортно"),
            "multiple_choice",
            true
        ));

        currentSurvey.addQuestion(new Question(
            "Какой факультет вы считаете наиболее перспективным для развития?",
            List.of("Технический", "Гуманитарный", "Экономический", "Медицинский", "Юридический"),
            "multiple_choice",
            false
        ));

        currentSurvey.addQuestion(new Question(
            "Какие улучшения вы хотели бы видеть в библиотеке?",
            List.of("Больше электронных ресурсов", "Улучшение условий для работы", "Расширение фонда", "Нет необходимости в улучшениях"),
            "multiple_choice",
            false
        ));

        currentSurvey.addQuestion(new Question(
            "Оцените качество технического оснащения аудиторий",
            List.of("Отлично", "Хорошо", "Удовлетворительно", "Плохо"),
            "multiple_choice",
            true
        ));

        currentSurvey.addQuestion(new Question(
            "Дополнительные комментарии и предложения:",
            "text",
            false
        ));
    }

    public Survey getCurrentSurvey() {
        return currentSurvey;
    }

    public void addResponse(SurveyResponse response) {
        currentSurvey.addResponse(response);
    }

    public List<SurveyResponse> getAllResponses() {
        return currentSurvey.getResponses();
    }

    public int getTotalResponses() {
        return currentSurvey.getResponses().size();
    }
}