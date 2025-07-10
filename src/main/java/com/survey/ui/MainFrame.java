package com.survey.ui;

import com.survey.service.AuthService;
import com.survey.service.SurveyService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    private AuthService authService;
    private SurveyService surveyService;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    public MainFrame() {
        authService = new AuthService();
        surveyService = new SurveyService();
        
        setupFrame();
        setupUI();
        showLoginPanel();
    }

    private void setupFrame() {
        setTitle("Сервис для опросов - Университет");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Устанавливаем иконку приложения
        try {
            setIconImage(new ImageIcon(getClass().getResource("/images/icon.png")).getImage());
        } catch (Exception e) {
            // Иконка не найдена, используем стандартную
        }
    }

    private void setupUI() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        // Добавляем панели
        mainPanel.add(new LoginPanel(this), "LOGIN");
        mainPanel.add(new SurveyPanel(this), "SURVEY");
        mainPanel.add(new ResultsPanel(this), "RESULTS");
        mainPanel.add(new SuccessPanel(this), "SUCCESS");
        
        add(mainPanel);
    }

    public void showLoginPanel() {
        cardLayout.show(mainPanel, "LOGIN");
    }

    public void showSurveyPanel() {
        cardLayout.show(mainPanel, "SURVEY");
    }

    public void showResultsPanel() {
        cardLayout.show(mainPanel, "RESULTS");
    }

    public void showSuccessPanel() {
        cardLayout.show(mainPanel, "SUCCESS");
    }

    public AuthService getAuthService() {
        return authService;
    }

    public SurveyService getSurveyService() {
        return surveyService;
    }

    public static void main(String[] args) {
        // Устанавливаем Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}