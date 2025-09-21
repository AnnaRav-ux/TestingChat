package aquarium;

/**
 * Демонстрационный класс для показа работы программы "Аквариум"
 */
public class AquariumDemo {
    public static void main(String[] args) {
        System.out.println("=== ДЕМОНСТРАЦИЯ ПРОГРАММЫ 'АКВАРИУМ' ===\n");
        
        // Создаем аквариум
        Aquarium aquarium = new Aquarium("Мой домашний аквариум", 100.0, "Гостиная");
        System.out.println("Создан аквариум: " + aquarium.getName());
        
        // Добавляем рыб
        System.out.println("\n--- Добавление рыб ---");
        
        // Тропические рыбы
        TropicalFish fish1 = new TropicalFish("Немо", "Клоун", 6, 8.5, "соленая", 26);
        TropicalFish fish2 = new TropicalFish("Дори", "Хирург", 8, 12.0, "соленая", 25);
        Goldfish fish3 = new Goldfish("Золотко", "Золотая рыбка", 24, 15.0, "золотой", true);
        Goldfish fish4 = new Goldfish("Рыжик", "Обычная рыбка", 12, 10.0, "оранжевый", false);
        
        aquarium.addFish(fish1);
        aquarium.addFish(fish2);
        aquarium.addFish(fish3);
        aquarium.addFish(fish4);
        
        // Добавляем оборудование
        System.out.println("\n--- Добавление оборудования ---");
        
        Equipment filter = new Equipment("Фильтр AquaClear", "Фильтр", true, 15.0);
        Equipment heater = new Equipment("Обогреватель Tetra", "Обогреватель", true, 50.0);
        Equipment pump = new Equipment("Помпа Eheim", "Помпа", false, 25.0);
        
        aquarium.addEquipment(filter);
        aquarium.addEquipment(heater);
        aquarium.addEquipment(pump);
        
        // Показываем информацию об аквариуме
        System.out.println("\n--- Информация об аквариуме ---");
        System.out.println(aquarium.toString());
        
        // Демонстрация плавания рыб
        System.out.println("\n--- Демонстрация плавания ---");
        for (Fish fish : aquarium.getAllFish()) {
            System.out.println(fish.swim());
        }
        
        // Проверка совместимости тропических рыб
        System.out.println("\n--- Проверка совместимости тропических рыб ---");
        if (fish1.isCompatibleWith(fish2)) {
            System.out.println("Немо и Дори совместимы - могут жить вместе");
        } else {
            System.out.println("Немо и Дори несовместимы");
        }
        
        // Проверка способностей золотых рыбок
        System.out.println("\n--- Способности золотых рыбок ---");
        System.out.println(fish3.getName() + ": " + fish3.getAbilities());
        System.out.println(fish4.getName() + ": " + fish4.getAbilities());
        
        // Управление оборудованием
        System.out.println("\n--- Управление оборудованием ---");
        System.out.println(pump.turnOn());
        System.out.println(filter.turnOff());
        
        // Проверка непротиворечивости
        System.out.println("\n--- Проверка непротиворечивости ---");
        aquarium.validateConsistency();
        
        // Статистика
        System.out.println("\n--- Статистика аквариума ---");
        System.out.println(aquarium.getStatistics());
        
        // Демонстрация поиска
        System.out.println("\n--- Поиск рыбы ---");
        Fish foundFish = aquarium.findFish("Немо");
        if (foundFish != null) {
            System.out.println("Найдена рыба: " + foundFish.toString());
        }
        
        // Демонстрация удаления
        System.out.println("\n--- Удаление рыбы ---");
        aquarium.removeFish("Рыжик");
        
        // Финальная статистика
        System.out.println("\n--- Финальная статистика ---");
        System.out.println(aquarium.getStatistics());
        
        System.out.println("\n=== ДЕМОНСТРАЦИЯ ЗАВЕРШЕНА ===");
    }
}