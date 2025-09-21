package aquarium;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Диалог для создания нового аквариума
 */
public class CreateAquariumDialog extends JDialog {
    private boolean okPressed = false;
    private Aquarium aquarium;
    
    private JTextField nameField;
    private JSpinner volumeSpinner;
    private JTextField locationField;
    
    public CreateAquariumDialog(Frame parent) {
        super(parent, "Создать новый аквариум", true);
        
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        
        setSize(400, 250);
        setLocationRelativeTo(parent);
    }
    
    private void initializeComponents() {
        // Поля ввода
        nameField = new JTextField(20);
        volumeSpinner = new JSpinner(new SpinnerNumberModel(50.0, 1.0, 1000.0, 0.1));
        locationField = new JTextField(20);
        
        // Устанавливаем значения по умолчанию
        nameField.setText("Мой аквариум");
        locationField.setText("Гостиная");
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Основная панель с полями
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Заголовок
        JLabel titleLabel = new JLabel("🏠 Создание нового аквариума");
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        titleLabel.setForeground(new Color(0, 100, 200));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 15, 0);
        mainPanel.add(titleLabel, gbc);
        
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Поля ввода
        gbc.gridx = 0; gbc.gridy = 1;
        mainPanel.add(new JLabel("Название аквариума:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(nameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        mainPanel.add(new JLabel("Объем (литры):"), gbc);
        gbc.gridx = 1;
        mainPanel.add(volumeSpinner, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        mainPanel.add(new JLabel("Местоположение:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(locationField, gbc);
        
        // Информационная панель
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBorder(BorderFactory.createTitledBorder("Рекомендации"));
        infoPanel.setBackground(new Color(255, 248, 220));
        
        JLabel infoLabel = new JLabel("<html><body style='width: 300px'>" +
            "<b>Советы по созданию аквариума:</b><br>" +
            "• Название должно быть уникальным<br>" +
            "• Объем влияет на количество рыб<br>" +
            "• Местоположение не должно совпадать с названием<br>" +
            "• Рекомендуемый объем: 50-200 литров" +
            "</body></html>");
        infoLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 11));
        
        infoPanel.add(infoLabel, BorderLayout.CENTER);
        
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 0, 0);
        mainPanel.add(infoPanel, gbc);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Панель кнопок
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton okButton = new JButton("Создать");
        JButton cancelButton = new JButton("Отмена");
        
        okButton.setPreferredSize(new Dimension(100, 30));
        cancelButton.setPreferredSize(new Dimension(100, 30));
        
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Обработчики событий
        okButton.addActionListener(e -> {
            if (validateInput()) {
                createAquarium();
                okPressed = true;
                dispose();
            }
        });
        
        cancelButton.addActionListener(e -> dispose());
        
        // Обработка Enter и Escape
        getRootPane().setDefaultButton(okButton);
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke("ESCAPE"), "cancel");
        getRootPane().getActionMap().put("cancel", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
    
    private void setupEventHandlers() {
        // Автоматическое заполнение местоположения
        nameField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (locationField.getText().trim().isEmpty()) {
                    String name = nameField.getText().trim();
                    if (!name.isEmpty()) {
                        locationField.setText("Гостиная");
                    }
                }
            }
        });
    }
    
    private boolean validateInput() {
        String name = nameField.getText().trim();
        String location = locationField.getText().trim();
        double volume = (Double) volumeSpinner.getValue();
        
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Введите название аквариума", 
                "Ошибка", JOptionPane.ERROR_MESSAGE);
            nameField.requestFocus();
            return false;
        }
        
        if (location.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Введите местоположение аквариума", 
                "Ошибка", JOptionPane.ERROR_MESSAGE);
            locationField.requestFocus();
            return false;
        }
        
        if (volume <= 0) {
            JOptionPane.showMessageDialog(this, "Объем должен быть положительным", 
                "Ошибка", JOptionPane.ERROR_MESSAGE);
            volumeSpinner.requestFocus();
            return false;
        }
        
        // Проверяем, что название и местоположение не совпадают
        if (name.equals(location)) {
            JOptionPane.showMessageDialog(this, 
                "Название аквариума не должно совпадать с местоположением", 
                "Ошибка", JOptionPane.ERROR_MESSAGE);
            nameField.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private void createAquarium() {
        String name = nameField.getText().trim();
        double volume = (Double) volumeSpinner.getValue();
        String location = locationField.getText().trim();
        
        aquarium = new Aquarium(name, volume, location);
        
        JOptionPane.showMessageDialog(this, 
            String.format("Аквариум '%s' успешно создан!\n\n" +
                "Объем: %.1f л\n" +
                "Местоположение: %s", name, volume, location), 
            "Успех", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public boolean isOkPressed() {
        return okPressed;
    }
    
    public Aquarium getAquarium() {
        return aquarium;
    }
}