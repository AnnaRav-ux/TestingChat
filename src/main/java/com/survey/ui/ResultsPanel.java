package com.survey.ui;

import com.survey.model.*;
import com.survey.service.SurveyService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class ResultsPanel extends JPanel {
    private MainFrame mainFrame;
    private SurveyService surveyService;
    private Survey survey;
    private JTable resultsTable;
    private JLabel statsLabel;
    private JTextArea summaryArea;

    public ResultsPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.surveyService = mainFrame.getSurveyService();
        this.survey = surveyService.getCurrentSurvey();
        
        setupUI();
    }

    private void setupUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(248, 250, 252));

        // Верхняя панель с заголовком и кнопками
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // Основная панель с результатами
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Статистика
        JPanel statsPanel = createStatsPanel();
        contentPanel.add(statsPanel, BorderLayout.NORTH);

        // Таблица результатов
        JPanel tablePanel = createTablePanel();
        contentPanel.add(tablePanel, BorderLayout.CENTER);

        // Сводка
        JPanel summaryPanel = createSummaryPanel();
        contentPanel.add(summaryPanel, BorderLayout.SOUTH);

        add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(25, 118, 210));
        panel.setBorder(new EmptyBorder(15, 20, 15, 20));

        // Заголовок
        JLabel titleLabel = new JLabel("Результаты опроса");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        panel.add(titleLabel, BorderLayout.WEST);

        // Кнопки справа
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.setOpaque(false);

        JButton backButton = new JButton("Назад к опросу");
        backButton.setFont(new Font("Arial", Font.PLAIN, 12));
        backButton.setBackground(new Color(255, 255, 255, 30));
        backButton.setForeground(Color.WHITE);
        backButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> mainFrame.showSurveyPanel());

        JButton logoutButton = new JButton("Выйти");
        logoutButton.setFont(new Font("Arial", Font.PLAIN, 12));
        logoutButton.setBackground(new Color(255, 255, 255, 30));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        logoutButton.setFocusPainted(false);
        logoutButton.addActionListener(e -> {
            mainFrame.getAuthService().logout();
            mainFrame.showLoginPanel();
        });

        buttonsPanel.add(backButton);
        buttonsPanel.add(logoutButton);
        panel.add(buttonsPanel, BorderLayout.EAST);

        return panel;
    }

    private JPanel createStatsPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(0, 0, 20, 0));

        int totalResponses = surveyService.getTotalResponses();
        statsLabel = new JLabel("Всего ответов: " + totalResponses);
        statsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        statsLabel.setForeground(new Color(25, 118, 210));

        panel.add(statsLabel);

        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        // Создаем таблицу
        String[] columnNames = {"№", "Пользователь", "Время отправки", "Количество ответов"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        resultsTable = new JTable(model);
        resultsTable.setFont(new Font("Arial", Font.PLAIN, 12));
        resultsTable.setRowHeight(25);
        resultsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        resultsTable.getTableHeader().setBackground(new Color(240, 240, 240));
        resultsTable.setGridColor(new Color(224, 224, 224));
        resultsTable.setSelectionBackground(new Color(25, 118, 210, 50));

        // Заполняем таблицу данными
        List<SurveyResponse> responses = surveyService.getAllResponses();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

        for (int i = 0; i < responses.size(); i++) {
            SurveyResponse response = responses.get(i);
            Object[] row = {
                i + 1,
                response.getRespondentName(),
                response.getSubmissionTime().format(formatter),
                response.getAnswers().size()
            };
            model.addRow(row);
        }

        JScrollPane scrollPane = new JScrollPane(resultsTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(224, 224, 224), 1));
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createSummaryPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 0, 0, 0));

        JLabel summaryLabel = new JLabel("Сводка по ответам:");
        summaryLabel.setFont(new Font("Arial", Font.BOLD, 14));
        summaryLabel.setForeground(new Color(33, 33, 33));

        summaryArea = new JTextArea();
        summaryArea.setFont(new Font("Arial", Font.PLAIN, 12));
        summaryArea.setEditable(false);
        summaryArea.setLineWrap(true);
        summaryArea.setWrapStyleWord(true);
        summaryArea.setBackground(new Color(248, 250, 252));
        summaryArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JScrollPane scrollPane = new JScrollPane(summaryArea);
        scrollPane.setPreferredSize(new Dimension(600, 150));

        panel.add(summaryLabel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Генерируем сводку
        generateSummary();

        return panel;
    }

    private void generateSummary() {
        List<SurveyResponse> responses = surveyService.getAllResponses();
        List<Question> questions = survey.getQuestions();
        
        if (responses.isEmpty()) {
            summaryArea.setText("Пока нет ответов на опрос.");
            return;
        }

        StringBuilder summary = new StringBuilder();
        summary.append("ОБЩАЯ СВОДКА ПО ОПРОСУ\n");
        summary.append("========================\n\n");

        // Статистика по каждому вопросу
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            summary.append("Вопрос ").append(i + 1).append(": ").append(question.getText()).append("\n");
            
            if ("multiple_choice".equals(question.getType())) {
                Map<String, Integer> answerCounts = new java.util.HashMap<>();
                
                // Подсчитываем ответы
                for (SurveyResponse response : responses) {
                    String answer = response.getAnswer(i);
                    if (answer != null) {
                        answerCounts.put(answer, answerCounts.getOrDefault(answer, 0) + 1);
                    }
                }
                
                // Выводим статистику
                for (Map.Entry<String, Integer> entry : answerCounts.entrySet()) {
                    double percentage = (double) entry.getValue() / responses.size() * 100;
                    summary.append("  • ").append(entry.getKey()).append(": ")
                           .append(entry.getValue()).append(" (").append(String.format("%.1f", percentage)).append("%)\n");
                }
            } else if ("text".equals(question.getType())) {
                int answeredCount = 0;
                for (SurveyResponse response : responses) {
                    if (response.getAnswer(i) != null && !response.getAnswer(i).trim().isEmpty()) {
                        answeredCount++;
                    }
                }
                double percentage = (double) answeredCount / responses.size() * 100;
                summary.append("  • Ответили: ").append(answeredCount).append(" из ").append(responses.size())
                       .append(" (").append(String.format("%.1f", percentage)).append("%)\n");
            }
            
            summary.append("\n");
        }

        summaryArea.setText(summary.toString());
    }
}