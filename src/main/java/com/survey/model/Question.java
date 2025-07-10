package com.survey.model;

import java.util.List;
import java.util.ArrayList;

public class Question {
    private String text;
    private List<String> options;
    private String type; // "multiple_choice", "text", "rating"
    private boolean required;

    public Question(String text, String type, boolean required) {
        this.text = text;
        this.type = type;
        this.required = required;
        this.options = new ArrayList<>();
    }

    public Question(String text, List<String> options, String type, boolean required) {
        this.text = text;
        this.options = options;
        this.type = type;
        this.required = required;
    }

    // Геттеры и сеттеры
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public void addOption(String option) {
        this.options.add(option);
    }
}