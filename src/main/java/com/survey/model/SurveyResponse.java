package com.survey.model;

import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;

public class SurveyResponse {
    private String respondentName;
    private Map<Integer, String> answers; // questionIndex -> answer
    private LocalDateTime submissionTime;

    public SurveyResponse(String respondentName) {
        this.respondentName = respondentName;
        this.answers = new HashMap<>();
        this.submissionTime = LocalDateTime.now();
    }

    // Геттеры и сеттеры
    public String getRespondentName() {
        return respondentName;
    }

    public void setRespondentName(String respondentName) {
        this.respondentName = respondentName;
    }

    public Map<Integer, String> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<Integer, String> answers) {
        this.answers = answers;
    }

    public LocalDateTime getSubmissionTime() {
        return submissionTime;
    }

    public void setSubmissionTime(LocalDateTime submissionTime) {
        this.submissionTime = submissionTime;
    }

    public void addAnswer(int questionIndex, String answer) {
        this.answers.put(questionIndex, answer);
    }

    public String getAnswer(int questionIndex) {
        return this.answers.get(questionIndex);
    }
}