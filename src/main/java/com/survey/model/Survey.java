package com.survey.model;

import java.util.List;
import java.util.ArrayList;

public class Survey {
    private String title;
    private String description;
    private List<Question> questions;
    private List<SurveyResponse> responses;

    public Survey(String title, String description) {
        this.title = title;
        this.description = description;
        this.questions = new ArrayList<>();
        this.responses = new ArrayList<>();
    }

    // Геттеры и сеттеры
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<SurveyResponse> getResponses() {
        return responses;
    }

    public void setResponses(List<SurveyResponse> responses) {
        this.responses = responses;
    }

    public void addQuestion(Question question) {
        this.questions.add(question);
    }

    public void addResponse(SurveyResponse response) {
        this.responses.add(response);
    }
}