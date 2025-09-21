package aquarium;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Графический интерфейс для программы "Аквариум"
 */
public class AquariumGUI extends JFrame {
    private Aquarium aquarium;
    private JTextArea infoArea;
    private JList<String> fishList;
    private JList<String> equipmentList;
    private DefaultListModel<String> fishListModel;
    private DefaultListModel<String> equipmentListModel;
    
    public AquariumGUI() {
        initializeAquarium();
        initializeGUI();
        setupEventHandlers();
        refreshDisplay();
    }
    
    private void initializeAquarium() {
        // Создаем аквариум с начальными данными
        aquarium = new Aquarium("Мой домашний аквариум", 100.0, "Гостиная");
        
        // Добавляем несколько рыб для демонстрации
        aquarium.addFish(new TropicalFish("Немо", "Клоун", 6, 8.5, "соленая", 26));
        aquarium.addFish(new TropicalFish("Дори", "Хирург", 8, 12.0, "соленая", 25));
        aquarium.addFish(new Goldfish("Золотко", "Золотая рыбка", 24, 15.0, "золотой", true));
        aquarium.addFish(new Goldfish("Рыжик", "Обычная рыбка", 12, 10.0, "оранжевый", false));
        
        // Добавляем оборудование
        aquarium.addEquipment(new Equipment("Фильтр AquaClear", "Фильтр", true, 15.0));
        aquarium.addEquipment(new Equipment("Обогреватель Tetra", "Обогреватель", true, 50.0));
        aquarium.addEquipment(new Equipment("Помпа Eheim", "Помпа", false, 25.0));
    }
    
    private void initializeGUI() {
        setTitle("🐠 Управление Аквариумом 🐠");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        
        // Устанавливаем современный Look and Feel
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Главное меню
        createMenuBar();
        
        // Основная панель
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Левая панель - управление
        JPanel leftPanel = createControlPanel();
        
        // Центральная панель - информация
        JPanel centerPanel = createInfoPanel();
        
        // Правая панель - списки
        JPanel rightPanel = createListsPanel();
        
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(rightPanel, BorderLayout.EAST);
        
        add(mainPanel);
    }
    
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        // Меню "Файл"
        JMenu fileMenu = new JMenu("Файл");
        JMenuItem newAquariumItem = new JMenuItem("Новый аквариум");
        JMenuItem exitItem = new JMenuItem("Выход");
        
        newAquariumItem.addActionListener(e -> createNewAquarium());
        exitItem.addActionListener(e -> System.exit(0));
        
        fileMenu.add(newAquariumItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        
        // Меню "Рыбы"
        JMenu fishMenu = new JMenu("Рыбы");
        JMenuItem addTropicalItem = new JMenuItem("Добавить тропическую рыбу");
        JMenuItem addGoldfishItem = new JMenuItem("Добавить золотую рыбку");
        JMenuItem removeFishItem = new JMenuItem("Удалить рыбу");
        
        addTropicalItem.addActionListener(e -> showAddTropicalFishDialog());
        addGoldfishItem.addActionListener(e -> showAddGoldfishDialog());
        removeFishItem.addActionListener(e -> removeSelectedFish());
        
        fishMenu.add(addTropicalItem);
        fishMenu.add(addGoldfishItem);
        fishMenu.addSeparator();
        fishMenu.add(removeFishItem);
        
        // Меню "Оборудование"
        JMenu equipmentMenu = new JMenu("Оборудование");
        JMenuItem addEquipmentItem = new JMenuItem("Добавить оборудование");
        JMenuItem removeEquipmentItem = new JMenuItem("Удалить оборудование");
        JMenuItem toggleEquipmentItem = new JMenuItem("Включить/выключить");
        
        addEquipmentItem.addActionListener(e -> showAddEquipmentDialog());
        removeEquipmentItem.addActionListener(e -> removeSelectedEquipment());
        toggleEquipmentItem.addActionListener(e -> toggleSelectedEquipment());
        
        equipmentMenu.add(addEquipmentItem);
        equipmentMenu.addSeparator();
        equipmentMenu.add(removeEquipmentItem);
        equipmentMenu.add(toggleEquipmentItem);
        
        // Меню "Помощь"
        JMenu helpMenu = new JMenu("Помощь");
        JMenuItem aboutItem = new JMenuItem("О программе");
        JMenuItem validateItem = new JMenuItem("Проверить непротиворечивость");
        
        aboutItem.addActionListener(e -> showAboutDialog());
        validateItem.addActionListener(e -> validateConsistency());
        
        helpMenu.add(validateItem);
        helpMenu.addSeparator();
        helpMenu.add(aboutItem);
        
        menuBar.add(fileMenu);
        menuBar.add(fishMenu);
        menuBar.add(equipmentMenu);
        menuBar.add(helpMenu);
        
        setJMenuBar(menuBar);
    }
    
    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new TitledBorder("🎮 Управление"));
        panel.setPreferredSize(new Dimension(250, 0));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Кнопки управления рыбами
        JButton addTropicalBtn = new JButton("🐠 Добавить тропическую рыбу");
        JButton addGoldfishBtn = new JButton("🐟 Добавить золотую рыбку");
        JButton removeFishBtn = new JButton("❌ Удалить рыбу");
        JButton showFishInfoBtn = new JButton("ℹ️ Информация о рыбе");
        
        // Кнопки управления оборудованием
        JButton addEquipmentBtn = new JButton("⚙️ Добавить оборудование");
        JButton removeEquipmentBtn = new JButton("🗑️ Удалить оборудование");
        JButton toggleEquipmentBtn = new JButton("🔌 Включить/выключить");
        
        // Кнопки общих операций
        JButton refreshBtn = new JButton("🔄 Обновить");
        JButton validateBtn = new JButton("✅ Проверить");
        JButton statisticsBtn = new JButton("📊 Статистика");
        JButton demoBtn = new JButton("🎭 Демонстрация");
        
        // Добавляем кнопки
        gbc.gridy = 0;
        panel.add(addTropicalBtn, gbc);
        gbc.gridy++;
        panel.add(addGoldfishBtn, gbc);
        gbc.gridy++;
        panel.add(removeFishBtn, gbc);
        gbc.gridy++;
        panel.add(showFishInfoBtn, gbc);
        
        gbc.gridy++;
        panel.add(Box.createVerticalStrut(10), gbc);
        
        gbc.gridy++;
        panel.add(addEquipmentBtn, gbc);
        gbc.gridy++;
        panel.add(removeEquipmentBtn, gbc);
        gbc.gridy++;
        panel.add(toggleEquipmentBtn, gbc);
        
        gbc.gridy++;
        panel.add(Box.createVerticalStrut(10), gbc);
        
        gbc.gridy++;
        panel.add(refreshBtn, gbc);
        gbc.gridy++;
        panel.add(validateBtn, gbc);
        gbc.gridy++;
        panel.add(statisticsBtn, gbc);
        gbc.gridy++;
        panel.add(demoBtn, gbc);
        
        // Обработчики событий
        addTropicalBtn.addActionListener(e -> showAddTropicalFishDialog());
        addGoldfishBtn.addActionListener(e -> showAddGoldfishDialog());
        removeFishBtn.addActionListener(e -> removeSelectedFish());
        showFishInfoBtn.addActionListener(e -> showFishInfo());
        
        addEquipmentBtn.addActionListener(e -> showAddEquipmentDialog());
        removeEquipmentBtn.addActionListener(e -> removeSelectedEquipment());
        toggleEquipmentBtn.addActionListener(e -> toggleSelectedEquipment());
        
        refreshBtn.addActionListener(e -> refreshDisplay());
        validateBtn.addActionListener(e -> validateConsistency());
        statisticsBtn.addActionListener(e -> showStatistics());
        demoBtn.addActionListener(e -> runDemo());
        
        return panel;
    }
    
    private JPanel createInfoPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new TitledBorder("📋 Информация об аквариуме"));
        
        infoArea = new JTextArea(20, 40);
        infoArea.setEditable(false);
        infoArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        infoArea.setBackground(new Color(240, 248, 255));
        
        JScrollPane scrollPane = new JScrollPane(infoArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createListsPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1, 5, 5));
        panel.setBorder(new TitledBorder("📝 Списки"));
        panel.setPreferredSize(new Dimension(300, 0));
        
        // Список рыб
        fishListModel = new DefaultListModel<>();
        fishList = new JList<>(fishListModel);
        fishList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fishList.setCellRenderer(new FishListRenderer());
        
        JScrollPane fishScrollPane = new JScrollPane(fishList);
        fishScrollPane.setBorder(new TitledBorder("🐠 Рыбы"));
        
        // Список оборудования
        equipmentListModel = new DefaultListModel<>();
        equipmentList = new JList<>(equipmentListModel);
        equipmentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        equipmentList.setCellRenderer(new EquipmentListRenderer());
        
        JScrollPane equipmentScrollPane = new JScrollPane(equipmentList);
        equipmentScrollPane.setBorder(new TitledBorder("⚙️ Оборудование"));
        
        panel.add(fishScrollPane);
        panel.add(equipmentScrollPane);
        
        return panel;
    }
    
    private void setupEventHandlers() {
        // Двойной клик по рыбе
        fishList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                refreshInfoArea();
            }
        });
        
        // Двойной клик по оборудованию
        equipmentList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                refreshInfoArea();
            }
        });
    }
    
    private void refreshDisplay() {
        refreshFishList();
        refreshEquipmentList();
        refreshInfoArea();
    }
    
    private void refreshFishList() {
        fishListModel.clear();
        List<Fish> fish = aquarium.getAllFish();
        for (Fish f : fish) {
            fishListModel.addElement(f.getName());
        }
    }
    
    private void refreshEquipmentList() {
        equipmentListModel.clear();
        List<Equipment> equipment = aquarium.getAllEquipment();
        for (Equipment e : equipment) {
            equipmentListModel.addElement(e.getName());
        }
    }
    
    private void refreshInfoArea() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== АКВАРИУМ ===\n");
        sb.append("Название: ").append(aquarium.getName()).append("\n");
        sb.append("Объем: ").append(aquarium.getVolume()).append(" л\n");
        sb.append("Местоположение: ").append(aquarium.getLocation()).append("\n\n");
        
        // Информация о выбранной рыбе
        String selectedFish = fishList.getSelectedValue();
        if (selectedFish != null) {
            Fish fish = aquarium.findFish(selectedFish);
            if (fish != null) {
                sb.append("--- ВЫБРАННАЯ РЫБА ---\n");
                sb.append(fish.toString()).append("\n");
                sb.append("Плавание: ").append(fish.swim()).append("\n");
                
                if (fish instanceof Goldfish) {
                    Goldfish goldfish = (Goldfish) fish;
                    sb.append("Способности: ").append(goldfish.getAbilities()).append("\n");
                } else if (fish instanceof TropicalFish) {
                    TropicalFish tropicalFish = (TropicalFish) fish;
                    sb.append("Тип воды: ").append(tropicalFish.getWaterType()).append("\n");
                    sb.append("Температура: ").append(tropicalFish.getTemperature()).append("°C\n");
                }
                sb.append("\n");
            }
        }
        
        // Информация о выбранном оборудовании
        String selectedEquipment = equipmentList.getSelectedValue();
        if (selectedEquipment != null) {
            Equipment equipment = aquarium.findEquipment(selectedEquipment);
            if (equipment != null) {
                sb.append("--- ВЫБРАННОЕ ОБОРУДОВАНИЕ ---\n");
                sb.append(equipment.toString()).append("\n");
                sb.append("Статус: ").append(equipment.getStatus()).append("\n\n");
            }
        }
        
        // Общая информация
        sb.append("--- ОБЩАЯ ИНФОРМАЦИЯ ---\n");
        sb.append(aquarium.getStatistics()).append("\n");
        
        infoArea.setText(sb.toString());
    }
    
    // Диалоги для добавления рыб и оборудования
    private void showAddTropicalFishDialog() {
        AddTropicalFishDialog dialog = new AddTropicalFishDialog(this, aquarium);
        dialog.setVisible(true);
        if (dialog.isOkPressed()) {
            refreshDisplay();
        }
    }
    
    private void showAddGoldfishDialog() {
        AddGoldfishDialog dialog = new AddGoldfishDialog(this, aquarium);
        dialog.setVisible(true);
        if (dialog.isOkPressed()) {
            refreshDisplay();
        }
    }
    
    private void showAddEquipmentDialog() {
        AddEquipmentDialog dialog = new AddEquipmentDialog(this, aquarium);
        dialog.setVisible(true);
        if (dialog.isOkPressed()) {
            refreshDisplay();
        }
    }
    
    // Методы управления
    private void removeSelectedFish() {
        String selectedFish = fishList.getSelectedValue();
        if (selectedFish != null) {
            int result = JOptionPane.showConfirmDialog(this,
                "Удалить рыбу '" + selectedFish + "'?",
                "Подтверждение удаления",
                JOptionPane.YES_NO_OPTION);
            
            if (result == JOptionPane.YES_OPTION) {
                aquarium.removeFish(selectedFish);
                refreshDisplay();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Выберите рыбу для удаления");
        }
    }
    
    private void removeSelectedEquipment() {
        String selectedEquipment = equipmentList.getSelectedValue();
        if (selectedEquipment != null) {
            int result = JOptionPane.showConfirmDialog(this,
                "Удалить оборудование '" + selectedEquipment + "'?",
                "Подтверждение удаления",
                JOptionPane.YES_NO_OPTION);
            
            if (result == JOptionPane.YES_OPTION) {
                aquarium.removeEquipment(selectedEquipment);
                refreshDisplay();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Выберите оборудование для удаления");
        }
    }
    
    private void toggleSelectedEquipment() {
        String selectedEquipment = equipmentList.getSelectedValue();
        if (selectedEquipment != null) {
            Equipment equipment = aquarium.findEquipment(selectedEquipment);
            if (equipment != null) {
                String result = equipment.isWorking() ? equipment.turnOff() : equipment.turnOn();
                JOptionPane.showMessageDialog(this, result);
                refreshDisplay();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Выберите оборудование для управления");
        }
    }
    
    private void showFishInfo() {
        String selectedFish = fishList.getSelectedValue();
        if (selectedFish != null) {
            Fish fish = aquarium.findFish(selectedFish);
            if (fish != null) {
                JOptionPane.showMessageDialog(this, fish.toString(), 
                    "Информация о рыбе", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Выберите рыбу для просмотра информации");
        }
    }
    
    private void validateConsistency() {
        boolean isValid = aquarium.validateConsistency();
        String message = isValid ? 
            "✅ Проверка непротиворечивости пройдена успешно!" :
            "❌ Обнаружены проблемы с непротиворечивостью данных";
        
        JOptionPane.showMessageDialog(this, message, "Проверка непротиворечивости", 
            isValid ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.WARNING_MESSAGE);
    }
    
    private void showStatistics() {
        String stats = aquarium.getStatistics();
        JOptionPane.showMessageDialog(this, stats, "Статистика аквариума", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void runDemo() {
        DemoDialog dialog = new DemoDialog(this, aquarium);
        dialog.setVisible(true);
    }
    
    private void createNewAquarium() {
        CreateAquariumDialog dialog = new CreateAquariumDialog(this);
        dialog.setVisible(true);
        if (dialog.isOkPressed()) {
            aquarium = dialog.getAquarium();
            refreshDisplay();
        }
    }
    
    private void showAboutDialog() {
        String about = "🐠 Программа управления аквариумом 🐠\n\n" +
                      "Демонстрирует принципы ООП:\n" +
                      "• Наследование (TropicalFish, Goldfish → Fish)\n" +
                      "• Агрегации (Aquarium содержит Fish и Equipment)\n" +
                      "• CRUD операции\n" +
                      "• Проверка непротиворечивости\n\n" +
                      "Версия: 1.0\n" +
                      "Java: " + System.getProperty("java.version");
        
        JOptionPane.showMessageDialog(this, about, "О программе", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    // Кастомные рендереры для списков
    private class FishListRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            
            if (value != null) {
                Fish fish = aquarium.findFish(value.toString());
                if (fish != null) {
                    String icon = fish instanceof Goldfish ? "🐟" : "🐠";
                    setText(icon + " " + value.toString());
                }
            }
            
            return this;
        }
    }
    
    private class EquipmentListRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            
            if (value != null) {
                Equipment equipment = aquarium.findEquipment(value.toString());
                if (equipment != null) {
                    String icon = equipment.isWorking() ? "🟢" : "🔴";
                    setText(icon + " " + value.toString());
                }
            }
            
            return this;
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new AquariumGUI().setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, 
                    "Ошибка при запуске приложения: " + e.getMessage(),
                    "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}