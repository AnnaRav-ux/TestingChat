package com.survey.ui;

import com.survey.model.*;
import com.survey.service.SurveyService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SurveyPanel extends JPanel {
    private MainFrame mainFrame;
    private SurveyService surveyService;
    private Survey survey;
    private JPanel questionsPanel;
    private JScrollPane scrollPane;
    private Map<Integer, JComponent> answerComponents;
    private JButton submitButton;
    private JButton logoutButton;
    private JButton viewResultsButton;

    public SurveyPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.surveyService = mainFrame.getSurveyService();
        this.survey = surveyService.getCurrentSurvey();
        this.answerComponents = new HashMap<>();
        
        setupUI();
    }

    private void setupUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(248, 250, 252));

        // Верхняя панель с заголовком и кнопками
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // Основная панель с вопросами
        questionsPanel = new JPanel();
        questionsPanel.setLayout(new BoxLayout(questionsPanel, BoxLayout.Y_AXIS));
        questionsPanel.setBackground(Color.WHITE);
        questionsPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        createQuestions();

        scrollPane = new JScrollPane(questionsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        // Нижняя панель с кнопками
        JPanel footerPanel = createFooterPanel();
        add(footerPanel, BorderLayout.SOUTH);
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(25, 118, 210));
        panel.setBorder(new EmptyBorder(15, 20, 15, 20));

        // Заголовок
        JLabel titleLabel = new JLabel(survey.getTitle());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        panel.add(titleLabel, BorderLayout.WEST);

        // Кнопки справа
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.setOpaque(false);

        logoutButton = new JButton("Выйти");
        logoutButton.setFont(new Font("Arial", Font.PLAIN, 12));
        logoutButton.setBackground(new Color(255, 255, 255, 30));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        logoutButton.setFocusPainted(false);

        if (mainFrame.getAuthService().isAdmin()) {
            viewResultsButton = new JButton("Результаты");
            viewResultsButton.setFont(new Font("Arial", Font.PLAIN, 12));
            viewResultsButton.setBackground(new Color(255, 255, 255, 30));
            viewResultsButton.setForeground(Color.WHITE);
            viewResultsButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
            viewResultsButton.setFocusPainted(false);
            viewResultsButton.addActionListener(e -> mainFrame.showResultsPanel());
            buttonsPanel.add(viewResultsButton);
        }

        logoutButton.addActionListener(e -> {
            mainFrame.getAuthService().logout();
            mainFrame.showLoginPanel();
        });

        buttonsPanel.add(logoutButton);
        panel.add(buttonsPanel, BorderLayout.EAST);

        return panel;
    }

    private void createQuestions() {
        List<Question> questions = survey.getQuestions();
        
        // Добавляем описание опроса
        JLabel descriptionLabel = new JLabel("<html><div style='width: 600px;'>" + 
            survey.getDescription() + "</div></html>");
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        descriptionLabel.setForeground(new Color(66, 66, 66));
        descriptionLabel.setBorder(new EmptyBorder(0, 0, 20, 0));
        questionsPanel.add(descriptionLabel);

        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            JPanel questionPanel = createQuestionPanel(question, i);
            questionsPanel.add(questionPanel);
            questionsPanel.add(Box.createVerticalStrut(20));
        }
    }

    private JPanel createQuestionPanel(Question question, int questionIndex) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(224, 224, 224), 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Текст вопроса
        JLabel questionLabel = new JLabel("<html><div style='width: 600px;'>" + 
            (questionIndex + 1) + ". " + question.getText() + 
            (question.isRequired() ? " <span style='color: red;'>*</span>" : "") + 
            "</div></html>");
        questionLabel.setFont(new Font("Arial", Font.BOLD, 14));
        questionLabel.setForeground(new Color(33, 33, 33));
        questionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(questionLabel);
        panel.add(Box.createVerticalStrut(15));

        // Компонент для ответа
        JComponent answerComponent = createAnswerComponent(question, questionIndex);
        answerComponent.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(answerComponent);
        answerComponents.put(questionIndex, answerComponent);

        return panel;
    }

    private JComponent createAnswerComponent(Question question, int questionIndex) {
        if ("multiple_choice".equals(question.getType())) {
            return createMultipleChoiceComponent(question, questionIndex);
        } else if ("text".equals(question.getType())) {
            return createTextComponent(question, questionIndex);
        }
        return new JLabel("Неизвестный тип вопроса");
    }

    private JComponent createMultipleChoiceComponent(Question question, int questionIndex) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        ButtonGroup buttonGroup = new ButtonGroup();
        List<String> options = question.getOptions();

        for (int i = 0; i < options.size(); i++) {
            JRadioButton radioButton = new JRadioButton(options.get(i));
            radioButton.setFont(new Font("Arial", Font.PLAIN, 13));
            radioButton.setForeground(new Color(66, 66, 66));
            radioButton.setOpaque(false);
            radioButton.setName(questionIndex + "_" + i);
            buttonGroup.add(radioButton);
            panel.add(radioButton);
            panel.add(Box.createVerticalStrut(5));
        }

        return panel;
    }

    private JComponent createTextComponent(Question question, int questionIndex) {
        JTextArea textArea = new JTextArea(4, 50);
        textArea.setFont(new Font("Arial", Font.PLAIN, 13));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        textArea.setName("text_" + questionIndex);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(600, 100));
        return scrollPane;
    }

    private JPanel createFooterPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(new Color(248, 250, 252));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        submitButton = new JButton("Отправить опрос");
        submitButton.setFont(new Font("Arial", Font.BOLD, 14));
        submitButton.setBackground(new Color(76, 175, 80));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);
        submitButton.setBorder(BorderFactory.createEmptyBorder(12, 30, 12, 30));
        submitButton.addActionListener(e -> submitSurvey());

        panel.add(submitButton);

        return panel;
    }

    private void submitSurvey() {
        // Проверяем обязательные вопросы
        List<Question> questions = survey.getQuestions();
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            if (question.isRequired()) {
                String answer = getAnswerForQuestion(i);
                if (answer == null || answer.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                        "Пожалуйста, ответьте на обязательный вопрос №" + (i + 1),
                        "Незаполненные поля",
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }
        }

        // Собираем ответы
        SurveyResponse response = new SurveyResponse(
            mainFrame.getAuthService().getCurrentUser().getUsername()
        );

        for (int i = 0; i < questions.size(); i++) {
            String answer = getAnswerForQuestion(i);
            if (answer != null && !answer.trim().isEmpty()) {
                response.addAnswer(i, answer);
            }
        }

        // Сохраняем ответ
        surveyService.addResponse(response);

        // Показываем окно успеха
        mainFrame.showSuccessPanel();
    }

    private String getAnswerForQuestion(int questionIndex) {
        Question question = survey.getQuestions().get(questionIndex);
        JComponent component = answerComponents.get(questionIndex);

        if ("multiple_choice".equals(question.getType())) {
            JPanel panel = (JPanel) component;
            for (Component comp : panel.getComponents()) {
                if (comp instanceof JRadioButton) {
                    JRadioButton radioButton = (JRadioButton) comp;
                    if (radioButton.isSelected()) {
                        return radioButton.getText();
                    }
                }
            }
        } else if ("text".equals(question.getType())) {
            JScrollPane scrollPane = (JScrollPane) component;
            JTextArea textArea = (JTextArea) scrollPane.getViewport().getView();
            return textArea.getText();
        }

        return null;
    }
}