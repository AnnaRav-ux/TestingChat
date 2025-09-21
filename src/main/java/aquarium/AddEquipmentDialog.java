package aquarium;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Диалог для добавления оборудования
 */
public class AddEquipmentDialog extends JDialog {
    private Aquarium aquarium;
    private boolean okPressed = false;
    
    private JTextField nameField;
    private JComboBox<String> typeCombo;
    private JCheckBox isWorkingCheckBox;
    private JSpinner powerSpinner;
    
    public AddEquipmentDialog(Frame parent, Aquarium aquarium) {
        super(parent, "Добавить оборудование", true);
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
        
        // Выпадающий список для типа оборудования
        typeCombo = new JComboBox<>(new String[]{
            "Фильтр", "Обогреватель", "Помпа", "Компрессор", 
            "Освещение", "Термометр", "Другое"
        });
        typeCombo.setSelectedIndex(0);
        typeCombo.setEditable(true);
        
        isWorkingCheckBox = new JCheckBox("Оборудование работает");
        isWorkingCheckBox.setSelected(true);
        
        powerSpinner = new JSpinner(new SpinnerNumberModel(10.0, 0.1, 1000.0, 0.1));
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
        JLabel titleLabel = new JLabel("⚙️ Добавление оборудования");
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        titleLabel.setForeground(new Color(0, 150, 0));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 15, 0);
        mainPanel.add(titleLabel, gbc);
        
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Поля ввода
        gbc.gridx = 0; gbc.gridy = 1;
        mainPanel.add(new JLabel("Название:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(nameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        mainPanel.add(new JLabel("Тип оборудования:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(typeCombo, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        mainPanel.add(new JLabel("Мощность (Вт):"), gbc);
        gbc.gridx = 1;
        mainPanel.add(powerSpinner, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        mainPanel.add(isWorkingCheckBox, gbc);
        
        // Информационная панель
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBorder(BorderFactory.createTitledBorder("Типы оборудования"));
        infoPanel.setBackground(new Color(240, 248, 255));
        
        JLabel infoLabel = new JLabel("<html><body style='width: 300px'>" +
            "<b>Рекомендуемые типы:</b><br>" +
            "• <b>Фильтр</b> - очистка воды<br>" +
            "• <b>Обогреватель</b> - поддержание температуры<br>" +
            "• <b>Помпа</b> - циркуляция воды<br>" +
            "• <b>Компрессор</b> - аэрация<br>" +
            "• <b>Освещение</b> - подсветка аквариума<br>" +
            "• <b>Термометр</b> - контроль температуры" +
            "</body></html>");
        infoLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 11));
        
        infoPanel.add(infoLabel, BorderLayout.CENTER);
        
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 0, 0);
        mainPanel.add(infoPanel, gbc);
        
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
                addEquipment();
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
        // Автоматическое заполнение названия по типу
        typeCombo.addActionListener(e -> {
            if (nameField.getText().trim().isEmpty()) {
                String type = (String) typeCombo.getSelectedItem();
                if (type != null && !type.isEmpty()) {
                    nameField.setText(type + " " + getDefaultName(type));
                }
            }
        });
        
        // Изменение мощности в зависимости от типа
        typeCombo.addActionListener(e -> {
            String type = (String) typeCombo.getSelectedItem();
            if (type != null) {
                double defaultPower = getDefaultPower(type);
                powerSpinner.setValue(defaultPower);
            }
        });
    }
    
    private String getDefaultName(String type) {
        switch (type.toLowerCase()) {
            case "фильтр": return "AquaClear";
            case "обогреватель": return "Tetra";
            case "помпа": return "Eheim";
            case "компрессор": return "Air Pump";
            case "освещение": return "LED Light";
            case "термометр": return "Digital";
            default: return "Standard";
        }
    }
    
    private double getDefaultPower(String type) {
        switch (type.toLowerCase()) {
            case "фильтр": return 15.0;
            case "обогреватель": return 50.0;
            case "помпа": return 25.0;
            case "компрессор": return 5.0;
            case "освещение": return 20.0;
            case "термометр": return 1.0;
            default: return 10.0;
        }
    }
    
    private boolean validateInput() {
        String name = nameField.getText().trim();
        String type = (String) typeCombo.getSelectedItem();
        
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Введите название оборудования", 
                "Ошибка", JOptionPane.ERROR_MESSAGE);
            nameField.requestFocus();
            return false;
        }
        
        if (type == null || type.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Выберите тип оборудования", 
                "Ошибка", JOptionPane.ERROR_MESSAGE);
            typeCombo.requestFocus();
            return false;
        }
        
        // Проверяем, нет ли уже оборудования с таким именем
        if (aquarium.findEquipment(name) != null) {
            JOptionPane.showMessageDialog(this, "Оборудование с названием '" + name + "' уже существует", 
                "Ошибка", JOptionPane.ERROR_MESSAGE);
            nameField.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private void addEquipment() {
        String name = nameField.getText().trim();
        String type = (String) typeCombo.getSelectedItem();
        boolean isWorking = isWorkingCheckBox.isSelected();
        double power = (Double) powerSpinner.getValue();
        
        Equipment equipment = new Equipment(name, type, isWorking, power);
        boolean success = aquarium.addEquipment(equipment);
        
        if (success) {
            String status = isWorking ? "работает" : "не работает";
            String message = String.format("Оборудование '%s' успешно добавлено!\n\n" +
                "Тип: %s\n" +
                "Мощность: %.1f Вт\n" +
                "Статус: %s", name, type, power, status);
            
            JOptionPane.showMessageDialog(this, message, "Успех", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Не удалось добавить оборудование", 
                "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public boolean isOkPressed() {
        return okPressed;
    }
}