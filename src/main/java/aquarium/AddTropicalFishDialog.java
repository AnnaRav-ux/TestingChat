package aquarium;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Диалог для добавления тропической рыбы
 */
public class AddTropicalFishDialog extends JDialog {
    private Aquarium aquarium;
    private boolean okPressed = false;
    
    private JTextField nameField;
    private JTextField speciesField;
    private JSpinner ageSpinner;
    private JSpinner sizeSpinner;
    private JComboBox<String> waterTypeCombo;
    private JSpinner temperatureSpinner;
    
    public AddTropicalFishDialog(Frame parent, Aquarium aquarium) {
        super(parent, "Добавить тропическую рыбу", true);
        this.aquarium = aquarium;
        
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        
        setSize(400, 300);
        setLocationRelativeTo(parent);
    }
    
    private void initializeComponents() {
        // Поля ввода
        nameField = new JTextField(20);
        speciesField = new JTextField(20);
        ageSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 120, 1));
        sizeSpinner = new JSpinner(new SpinnerNumberModel(1.0, 0.1, 50.0, 0.1));
        
        // Выпадающий список для типа воды
        waterTypeCombo = new JComboBox<>(new String[]{"пресная", "соленая"});
        waterTypeCombo.setSelectedIndex(0);
        
        // Спиннер для температуры
        temperatureSpinner = new JSpinner(new SpinnerNumberModel(25, 15, 35, 1));
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
        JLabel titleLabel = new JLabel("🐠 Добавление тропической рыбы");
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        titleLabel.setForeground(new Color(0, 100, 200));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 15, 0);
        mainPanel.add(titleLabel, gbc);
        
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Поля ввода
        gbc.gridx = 0; gbc.gridy = 1;
        mainPanel.add(new JLabel("Имя рыбы:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(nameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        mainPanel.add(new JLabel("Вид рыбы:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(speciesField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        mainPanel.add(new JLabel("Возраст (месяцев):"), gbc);
        gbc.gridx = 1;
        mainPanel.add(ageSpinner, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4;
        mainPanel.add(new JLabel("Размер (см):"), gbc);
        gbc.gridx = 1;
        mainPanel.add(sizeSpinner, gbc);
        
        gbc.gridx = 0; gbc.gridy = 5;
        mainPanel.add(new JLabel("Тип воды:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(waterTypeCombo, gbc);
        
        gbc.gridx = 0; gbc.gridy = 6;
        mainPanel.add(new JLabel("Температура (°C):"), gbc);
        gbc.gridx = 1;
        mainPanel.add(temperatureSpinner, gbc);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Панель кнопок
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton okButton = new JButton("Добавить");
        JButton cancelButton = new JButton("Отмена");
        
        okButton.setPreferredSize(new Dimension(100, 30));
        cancelButton.setPreferredSize(new Dimension(100, 30));
        
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Обработчики событий
        okButton.addActionListener(e -> {
            if (validateInput()) {
                addFish();
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
        // Автоматическое заполнение вида по умолчанию
        nameField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (speciesField.getText().trim().isEmpty()) {
                    String name = nameField.getText().trim();
                    if (!name.isEmpty()) {
                        speciesField.setText("Тропическая рыба");
                    }
                }
            }
        });
    }
    
    private boolean validateInput() {
        String name = nameField.getText().trim();
        String species = speciesField.getText().trim();
        
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Введите имя рыбы", "Ошибка", JOptionPane.ERROR_MESSAGE);
            nameField.requestFocus();
            return false;
        }
        
        if (species.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Введите вид рыбы", "Ошибка", JOptionPane.ERROR_MESSAGE);
            speciesField.requestFocus();
            return false;
        }
        
        // Проверяем, нет ли уже рыбы с таким именем
        if (aquarium.findFish(name) != null) {
            JOptionPane.showMessageDialog(this, "Рыба с именем '" + name + "' уже существует", 
                "Ошибка", JOptionPane.ERROR_MESSAGE);
            nameField.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private void addFish() {
        String name = nameField.getText().trim();
        String species = speciesField.getText().trim();
        int age = (Integer) ageSpinner.getValue();
        double size = (Double) sizeSpinner.getValue();
        String waterType = (String) waterTypeCombo.getSelectedItem();
        int temperature = (Integer) temperatureSpinner.getValue();
        
        TropicalFish fish = new TropicalFish(name, species, age, size, waterType, temperature);
        boolean success = aquarium.addFish(fish);
        
        if (success) {
            JOptionPane.showMessageDialog(this, 
                "Тропическая рыба '" + name + "' успешно добавлена!", 
                "Успех", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, 
                "Не удалось добавить рыбу", 
                "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public boolean isOkPressed() {
        return okPressed;
    }
}