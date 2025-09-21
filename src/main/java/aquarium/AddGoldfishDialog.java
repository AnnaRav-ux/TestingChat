package aquarium;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Диалог для добавления золотой рыбки
 */
public class AddGoldfishDialog extends JDialog {
    private Aquarium aquarium;
    private boolean okPressed = false;
    
    private JTextField nameField;
    private JTextField speciesField;
    private JSpinner ageSpinner;
    private JSpinner sizeSpinner;
    private JTextField colorField;
    private JCheckBox isGoldenCheckBox;
    
    public AddGoldfishDialog(Frame parent, Aquarium aquarium) {
        super(parent, "Добавить золотую рыбку", true);
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
        colorField = new JTextField(20);
        isGoldenCheckBox = new JCheckBox("Является золотой рыбкой");
        isGoldenCheckBox.setSelected(true);
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
        JLabel titleLabel = new JLabel("🐟 Добавление золотой рыбки");
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        titleLabel.setForeground(new Color(255, 165, 0));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 15, 0);
        mainPanel.add(titleLabel, gbc);
        
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Поля ввода
        gbc.gridx = 0; gbc.gridy = 1;
        mainPanel.add(new JLabel("Имя рыбки:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(nameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        mainPanel.add(new JLabel("Вид рыбки:"), gbc);
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
        mainPanel.add(new JLabel("Цвет рыбки:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(colorField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        mainPanel.add(isGoldenCheckBox, gbc);
        
        // Информационная панель
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBorder(BorderFactory.createTitledBorder("Информация"));
        infoPanel.setBackground(new Color(255, 248, 220));
        
        JLabel infoLabel = new JLabel("<html><body style='width: 300px'>" +
            "<b>Золотые рыбки:</b><br>" +
            "• Могут исполнять желания (если старше 12 месяцев)<br>" +
            "• Обычные рыбки не имеют особых способностей<br>" +
            "• Цвет может быть любым: золотой, оранжевый, красный и т.д." +
            "</body></html>");
        infoLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 11));
        
        infoPanel.add(infoLabel, BorderLayout.CENTER);
        
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2;
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
                        speciesField.setText("Золотая рыбка");
                    }
                }
            }
        });
        
        // Автоматическое заполнение цвета
        colorField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (colorField.getText().trim().isEmpty()) {
                    colorField.setText(isGoldenCheckBox.isSelected() ? "золотой" : "оранжевый");
                }
            }
        });
        
        // Изменение цвета при изменении типа рыбки
        isGoldenCheckBox.addActionListener(e -> {
            if (colorField.getText().trim().isEmpty()) {
                colorField.setText(isGoldenCheckBox.isSelected() ? "золотой" : "оранжевый");
            }
        });
    }
    
    private boolean validateInput() {
        String name = nameField.getText().trim();
        String species = speciesField.getText().trim();
        String color = colorField.getText().trim();
        
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Введите имя рыбки", "Ошибка", JOptionPane.ERROR_MESSAGE);
            nameField.requestFocus();
            return false;
        }
        
        if (species.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Введите вид рыбки", "Ошибка", JOptionPane.ERROR_MESSAGE);
            speciesField.requestFocus();
            return false;
        }
        
        if (color.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Введите цвет рыбки", "Ошибка", JOptionPane.ERROR_MESSAGE);
            colorField.requestFocus();
            return false;
        }
        
        // Проверяем, нет ли уже рыбы с таким именем
        if (aquarium.findFish(name) != null) {
            JOptionPane.showMessageDialog(this, "Рыбка с именем '" + name + "' уже существует", 
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
        String color = colorField.getText().trim();
        boolean isGolden = isGoldenCheckBox.isSelected();
        
        Goldfish fish = new Goldfish(name, species, age, size, color, isGolden);
        boolean success = aquarium.addFish(fish);
        
        if (success) {
            String message = "Золотая рыбка '" + name + "' успешно добавлена!";
            if (isGolden && age >= 12) {
                message += "\n\n✨ Эта рыбка может исполнять желания!";
            }
            
            JOptionPane.showMessageDialog(this, message, "Успех", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Не удалось добавить рыбку", 
                "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public boolean isOkPressed() {
        return okPressed;
    }
}