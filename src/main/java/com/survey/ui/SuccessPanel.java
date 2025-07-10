package com.survey.ui;

import javax.swing.*;
import java.awt.*;

public class SuccessPanel extends JPanel {
    private MainFrame mainFrame;
    private JButton backToSurveyButton;
    private JButton logoutButton;

    public SuccessPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setupUI();
    }

    private void setupUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 248, 255));

        // Создаем градиентный фон
        setOpaque(false);
        setLayout(new GridBagLayout());

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);

        // Иконка успеха
        JLabel iconLabel = new JLabel("✓");
        iconLabel.setFont(new Font("Arial", Font.BOLD, 80));
        iconLabel.setForeground(new Color(76, 175, 80));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Заголовок
        JLabel titleLabel = new JLabel("Опрос успешно отправлен!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(new Color(25, 118, 210));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Подзаголовок
        JLabel subtitleLabel = new JLabel("Спасибо за ваши ответы!");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(66, 66, 66));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Описание
        JLabel descriptionLabel = new JLabel("<html><div style='width: 400px; text-align: center;'>" +
            "Ваши ответы помогут нам улучшить качество образования в университете. " +
            "Мы ценим ваше время и мнение!</div></html>");
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        descriptionLabel.setForeground(new Color(66, 66, 66));
        descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Панель с кнопками
        JPanel buttonsPanel = createButtonsPanel();

        // Добавляем компоненты
        contentPanel.add(Box.createVerticalStrut(50));
        contentPanel.add(iconLabel);
        contentPanel.add(Box.createVerticalStrut(30));
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(subtitleLabel);
        contentPanel.add(Box.createVerticalStrut(30));
        contentPanel.add(descriptionLabel);
        contentPanel.add(Box.createVerticalStrut(40));
        contentPanel.add(buttonsPanel);
        contentPanel.add(Box.createVerticalStrut(50));

        add(contentPanel);
    }

    private JPanel createButtonsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        panel.setOpaque(false);

        // Кнопка "Пройти еще раз"
        backToSurveyButton = new JButton("Пройти опрос еще раз");
        backToSurveyButton.setFont(new Font("Arial", Font.BOLD, 14));
        backToSurveyButton.setBackground(new Color(25, 118, 210));
        backToSurveyButton.setForeground(Color.WHITE);
        backToSurveyButton.setFocusPainted(false);
        backToSurveyButton.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
        backToSurveyButton.addActionListener(e -> mainFrame.showSurveyPanel());

        // Кнопка "Выйти"
        logoutButton = new JButton("Выйти");
        logoutButton.setFont(new Font("Arial", Font.PLAIN, 14));
        logoutButton.setBackground(new Color(255, 255, 255));
        logoutButton.setForeground(new Color(66, 66, 66));
        logoutButton.setFocusPainted(false);
        logoutButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(12, 25, 12, 25)
        ));
        logoutButton.addActionListener(e -> {
            mainFrame.getAuthService().logout();
            mainFrame.showLoginPanel();
        });

        // Hover эффекты
        backToSurveyButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backToSurveyButton.setBackground(new Color(21, 101, 192));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                backToSurveyButton.setBackground(new Color(25, 118, 210));
            }
        });

        logoutButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                logoutButton.setBackground(new Color(245, 245, 245));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                logoutButton.setBackground(new Color(255, 255, 255));
            }
        });

        panel.add(backToSurveyButton);
        panel.add(logoutButton);

        return panel;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        
        // Создаем градиентный фон
        GradientPaint gradient = new GradientPaint(
            0, 0, new Color(240, 248, 255),
            0, getHeight(), new Color(255, 255, 255)
        );
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        
        g2d.dispose();
    }
}