package aquarium;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * Диалог для демонстрации возможностей программы
 */
public class DemoDialog extends JDialog {
    private Aquarium aquarium;
    private JTextArea demoArea;
    private JButton nextStepButton;
    private JButton runAllButton;
    private JButton closeButton;
    private int currentStep = 0;
    
    private final String[] demoSteps = {
        "Демонстрация плавания всех рыб",
        "Проверка совместимости тропических рыб",
        "Проверка способностей золотых рыбок",
        "Управление оборудованием",
        "Проверка непротиворечивости",
        "Статистика аквариума"
    };
    
    public DemoDialog(Frame parent, Aquarium aquarium) {
        super(parent, "Демонстрация программы", true);
        this.aquarium = aquarium;
        
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        
        setSize(600, 500);
        setLocationRelativeTo(parent);
        
        // Начинаем демонстрацию
        showWelcome();
    }
    
    private void initializeComponents() {
        demoArea = new JTextArea(20, 50);
        demoArea.setEditable(false);
        demoArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        demoArea.setBackground(new Color(240, 248, 255));
        
        nextStepButton = new JButton("Следующий шаг");
        runAllButton = new JButton("Запустить все");
        closeButton = new JButton("Закрыть");
        
        nextStepButton.setPreferredSize(new Dimension(120, 30));
        runAllButton.setPreferredSize(new Dimension(120, 30));
        closeButton.setPreferredSize(new Dimension(100, 30));
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Заголовок
        JLabel titleLabel = new JLabel("🎭 Демонстрация программы 'Аквариум'");
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        titleLabel.setForeground(new Color(0, 100, 200));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        add(titleLabel, BorderLayout.NORTH);
        
        // Область демонстрации
        JScrollPane scrollPane = new JScrollPane(demoArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Результаты демонстрации"));
        
        add(scrollPane, BorderLayout.CENTER);
        
        // Панель кнопок
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(nextStepButton);
        buttonPanel.add(runAllButton);
        buttonPanel.add(closeButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void setupEventHandlers() {
        nextStepButton.addActionListener(e -> runNextStep());
        runAllButton.addActionListener(e -> runAllSteps());
        closeButton.addActionListener(e -> dispose());
        
        // Обработка Escape
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke("ESCAPE"), "close");
        getRootPane().getActionMap().put("close", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
    
    private void showWelcome() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ДЕМОНСТРАЦИЯ ПРОГРАММЫ 'АКВАРИУМ' ===\n\n");
        sb.append("Добро пожаловать в демонстрацию возможностей программы!\n\n");
        sb.append("Эта программа демонстрирует принципы ООП:\n");
        sb.append("• Наследование (TropicalFish, Goldfish → Fish)\n");
        sb.append("• Агрегации (Aquarium содержит Fish и Equipment)\n");
        sb.append("• CRUD операции\n");
        sb.append("• Проверка непротиворечивости\n\n");
        sb.append("Нажмите 'Следующий шаг' для пошаговой демонстрации\n");
        sb.append("или 'Запустить все' для полной демонстрации.\n\n");
        sb.append("==========================================\n\n");
        
        demoArea.setText(sb.toString());
        currentStep = 0;
    }
    
    private void runNextStep() {
        if (currentStep >= demoSteps.length) {
            showCompletion();
            return;
        }
        
        String step = demoSteps[currentStep];
        appendToDemo("--- " + step + " ---\n");
        
        switch (currentStep) {
            case 0:
                demonstrateSwimming();
                break;
            case 1:
                checkTropicalCompatibility();
                break;
            case 2:
                checkGoldfishAbilities();
                break;
            case 3:
                manageEquipment();
                break;
            case 4:
                validateConsistency();
                break;
            case 5:
                showStatistics();
                break;
        }
        
        currentStep++;
        
        if (currentStep >= demoSteps.length) {
            nextStepButton.setText("Завершить");
        }
    }
    
    private void runAllSteps() {
        demoArea.setText("");
        showWelcome();
        currentStep = 0;
        
        // Запускаем все шаги подряд
        for (int i = 0; i < demoSteps.length; i++) {
            runNextStep();
            try {
                Thread.sleep(500); // Небольшая пауза между шагами
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        showCompletion();
    }
    
    private void demonstrateSwimming() {
        List<Fish> fish = aquarium.getAllFish();
        if (fish.isEmpty()) {
            appendToDemo("Рыб в аквариуме нет.\n");
        } else {
            appendToDemo("Демонстрация плавания:\n");
            for (Fish f : fish) {
                appendToDemo("• " + f.swim() + "\n");
            }
        }
        appendToDemo("\n");
    }
    
    private void checkTropicalCompatibility() {
        List<Fish> fish = aquarium.getAllFish();
        List<TropicalFish> tropicalFish = fish.stream()
            .filter(f -> f instanceof TropicalFish)
            .map(f -> (TropicalFish) f)
            .toList();
        
        if (tropicalFish.size() < 2) {
            appendToDemo("Недостаточно тропических рыб для проверки совместимости.\n");
        } else {
            appendToDemo("Проверка совместимости тропических рыб:\n");
            for (int i = 0; i < tropicalFish.size(); i++) {
                for (int j = i + 1; j < tropicalFish.size(); j++) {
                    TropicalFish fish1 = tropicalFish.get(i);
                    TropicalFish fish2 = tropicalFish.get(j);
                    boolean compatible = fish1.isCompatibleWith(fish2);
                    appendToDemo(String.format("• %s и %s - %s\n", 
                        fish1.getName(), fish2.getName(), 
                        compatible ? "совместимы" : "несовместимы"));
                }
            }
        }
        appendToDemo("\n");
    }
    
    private void checkGoldfishAbilities() {
        List<Fish> fish = aquarium.getAllFish();
        List<Goldfish> goldfish = fish.stream()
            .filter(f -> f instanceof Goldfish)
            .map(f -> (Goldfish) f)
            .toList();
        
        if (goldfish.isEmpty()) {
            appendToDemo("Золотых рыбок в аквариуме нет.\n");
        } else {
            appendToDemo("Способности золотых рыбок:\n");
            for (Goldfish gf : goldfish) {
                appendToDemo("• " + gf.getName() + ": " + gf.getAbilities() + "\n");
            }
        }
        appendToDemo("\n");
    }
    
    private void manageEquipment() {
        List<Equipment> equipment = aquarium.getAllEquipment();
        if (equipment.isEmpty()) {
            appendToDemo("Оборудования в аквариуме нет.\n");
        } else {
            appendToDemo("Управление оборудованием:\n");
            for (Equipment e : equipment) {
                String action = e.isWorking() ? e.turnOff() : e.turnOn();
                appendToDemo("• " + action + "\n");
            }
        }
        appendToDemo("\n");
    }
    
    private void validateConsistency() {
        appendToDemo("Проверка непротиворечивости:\n");
        boolean isValid = aquarium.validateConsistency();
        appendToDemo(isValid ? 
            "✅ Проверка пройдена успешно!\n" : 
            "❌ Обнаружены проблемы с данными\n");
        appendToDemo("\n");
    }
    
    private void showStatistics() {
        appendToDemo("Статистика аквариума:\n");
        appendToDemo(aquarium.getStatistics() + "\n");
        appendToDemo("\n");
    }
    
    private void showCompletion() {
        appendToDemo("==========================================\n");
        appendToDemo("🎉 ДЕМОНСТРАЦИЯ ЗАВЕРШЕНА! 🎉\n\n");
        appendToDemo("Все функции программы успешно продемонстрированы:\n");
        appendToDemo("✅ Наследование классов\n");
        appendToDemo("✅ Агрегации объектов\n");
        appendToDemo("✅ CRUD операции\n");
        appendToDemo("✅ Проверка непротиворечивости\n");
        appendToDemo("✅ Специальные операции\n\n");
        appendToDemo("Спасибо за использование программы 'Аквариум'!\n");
        
        nextStepButton.setEnabled(false);
        runAllButton.setEnabled(false);
    }
    
    private void appendToDemo(String text) {
        demoArea.append(text);
        demoArea.setCaretPosition(demoArea.getDocument().getLength());
    }
}