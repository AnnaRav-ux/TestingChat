package aquarium;

import java.util.Scanner;

/**
 * Главный класс приложения "Аквариум" с интерактивным меню
 */
public class AquariumApp {
    private static Aquarium aquarium;
    private static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        System.out.println("=== ПРОГРАММА УПРАВЛЕНИЯ АКВАРИУМОМ ===\n");
        
        // Создаем аквариум
        createAquarium();
        
        // Основное меню
        boolean running = true;
        while (running) {
            showMainMenu();
            int choice = getIntInput("Выберите действие: ");
            
            switch (choice) {
                case 1:
                    showAquariumInfo();
                    break;
                case 2:
                    fishMenu();
                    break;
                case 3:
                    equipmentMenu();
                    break;
                case 4:
                    aquarium.validateConsistency();
                    break;
                case 5:
                    showStatistics();
                    break;
                case 6:
                    testSpecialOperations();
                    break;
                case 0:
                    running = false;
                    System.out.println("До свидания!");
                    break;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
            
            if (running) {
                System.out.println("\nНажмите Enter для продолжения...");
                scanner.nextLine();
            }
        }
        
        scanner.close();
    }

    private static void createAquarium() {
        System.out.println("Создание нового аквариума:");
        String name = getStringInput("Введите название аквариума: ");
        double volume = getDoubleInput("Введите объем аквариума (л): ");
        String location = getStringInput("Введите местоположение аквариума: ");
        
        aquarium = new Aquarium(name, volume, location);
        System.out.println("Аквариум создан успешно!\n");
    }

    private static void showMainMenu() {
        System.out.println("\n=== ГЛАВНОЕ МЕНЮ ===");
        System.out.println("1. Показать информацию об аквариуме");
        System.out.println("2. Управление рыбами");
        System.out.println("3. Управление оборудованием");
        System.out.println("4. Проверить непротиворечивость");
        System.out.println("5. Показать статистику");
        System.out.println("6. Специальные операции");
        System.out.println("0. Выход");
    }

    private static void showAquariumInfo() {
        System.out.println("\n" + aquarium.toString());
    }

    private static void fishMenu() {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\n=== УПРАВЛЕНИЕ РЫБАМИ ===");
            System.out.println("1. Добавить тропическую рыбу");
            System.out.println("2. Добавить золотую рыбку");
            System.out.println("3. Удалить рыбу");
            System.out.println("4. Найти рыбу");
            System.out.println("5. Показать всех рыб");
            System.out.println("0. Назад");
            
            int choice = getIntInput("Выберите действие: ");
            
            switch (choice) {
                case 1:
                    addTropicalFish();
                    break;
                case 2:
                    addGoldfish();
                    break;
                case 3:
                    removeFish();
                    break;
                case 4:
                    findFish();
                    break;
                case 5:
                    showAllFish();
                    break;
                case 0:
                    inMenu = false;
                    break;
                default:
                    System.out.println("Неверный выбор.");
            }
        }
    }

    private static void addTropicalFish() {
        System.out.println("\n--- Добавление тропической рыбы ---");
        String name = getStringInput("Имя рыбы: ");
        String species = getStringInput("Вид рыбы: ");
        int age = getIntInput("Возраст (месяцев): ");
        double size = getDoubleInput("Размер (см): ");
        String waterType = getStringInput("Тип воды (пресная/соленая): ");
        int temperature = getIntInput("Предпочитаемая температура (°C): ");
        
        TropicalFish fish = new TropicalFish(name, species, age, size, waterType, temperature);
        aquarium.addFish(fish);
    }

    private static void addGoldfish() {
        System.out.println("\n--- Добавление золотой рыбки ---");
        String name = getStringInput("Имя рыбки: ");
        String species = getStringInput("Вид рыбки: ");
        int age = getIntInput("Возраст (месяцев): ");
        double size = getDoubleInput("Размер (см): ");
        String color = getStringInput("Цвет рыбки: ");
        boolean isGolden = getBooleanInput("Является ли золотой? (да/нет): ");
        
        Goldfish fish = new Goldfish(name, species, age, size, color, isGolden);
        aquarium.addFish(fish);
    }

    private static void removeFish() {
        System.out.println("\n--- Удаление рыбы ---");
        String name = getStringInput("Имя рыбы для удаления: ");
        aquarium.removeFish(name);
    }

    private static void findFish() {
        System.out.println("\n--- Поиск рыбы ---");
        String name = getStringInput("Имя рыбы для поиска: ");
        Fish fish = aquarium.findFish(name);
        if (fish != null) {
            System.out.println("Найдена рыба: " + fish.toString());
            System.out.println("Плавание: " + fish.swim());
            
            if (fish instanceof Goldfish) {
                Goldfish goldfish = (Goldfish) fish;
                System.out.println("Способности: " + goldfish.getAbilities());
            }
        } else {
            System.out.println("Рыба не найдена.");
        }
    }

    private static void showAllFish() {
        System.out.println("\n--- Все рыбы в аквариуме ---");
        var fish = aquarium.getAllFish();
        if (fish.isEmpty()) {
            System.out.println("Рыб в аквариуме нет.");
        } else {
            for (int i = 0; i < fish.size(); i++) {
                System.out.println((i + 1) + ". " + fish.get(i).toString());
            }
        }
    }

    private static void equipmentMenu() {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\n=== УПРАВЛЕНИЕ ОБОРУДОВАНИЕМ ===");
            System.out.println("1. Добавить оборудование");
            System.out.println("2. Удалить оборудование");
            System.out.println("3. Найти оборудование");
            System.out.println("4. Включить/выключить оборудование");
            System.out.println("5. Показать все оборудование");
            System.out.println("0. Назад");
            
            int choice = getIntInput("Выберите действие: ");
            
            switch (choice) {
                case 1:
                    addEquipment();
                    break;
                case 2:
                    removeEquipment();
                    break;
                case 3:
                    findEquipment();
                    break;
                case 4:
                    toggleEquipment();
                    break;
                case 5:
                    showAllEquipment();
                    break;
                case 0:
                    inMenu = false;
                    break;
                default:
                    System.out.println("Неверный выбор.");
            }
        }
    }

    private static void addEquipment() {
        System.out.println("\n--- Добавление оборудования ---");
        String name = getStringInput("Название оборудования: ");
        String type = getStringInput("Тип оборудования: ");
        boolean isWorking = getBooleanInput("Работает ли оборудование? (да/нет): ");
        double power = getDoubleInput("Мощность (Вт): ");
        
        Equipment equipment = new Equipment(name, type, isWorking, power);
        aquarium.addEquipment(equipment);
    }

    private static void removeEquipment() {
        System.out.println("\n--- Удаление оборудования ---");
        String name = getStringInput("Название оборудования для удаления: ");
        aquarium.removeEquipment(name);
    }

    private static void findEquipment() {
        System.out.println("\n--- Поиск оборудования ---");
        String name = getStringInput("Название оборудования для поиска: ");
        Equipment equipment = aquarium.findEquipment(name);
        if (equipment != null) {
            System.out.println("Найдено оборудование: " + equipment.toString());
            System.out.println("Статус: " + equipment.getStatus());
        } else {
            System.out.println("Оборудование не найдено.");
        }
    }

    private static void toggleEquipment() {
        System.out.println("\n--- Управление оборудованием ---");
        String name = getStringInput("Название оборудования: ");
        Equipment equipment = aquarium.findEquipment(name);
        if (equipment != null) {
            System.out.println("Текущий статус: " + equipment.getStatus());
            boolean turnOn = getBooleanInput("Включить оборудование? (да/нет): ");
            if (turnOn) {
                System.out.println(equipment.turnOn());
            } else {
                System.out.println(equipment.turnOff());
            }
        } else {
            System.out.println("Оборудование не найдено.");
        }
    }

    private static void showAllEquipment() {
        System.out.println("\n--- Все оборудование в аквариуме ---");
        var equipment = aquarium.getAllEquipment();
        if (equipment.isEmpty()) {
            System.out.println("Оборудования в аквариуме нет.");
        } else {
            for (int i = 0; i < equipment.size(); i++) {
                System.out.println((i + 1) + ". " + equipment.get(i).toString());
            }
        }
    }

    private static void showStatistics() {
        System.out.println("\n" + aquarium.getStatistics());
    }

    private static void testSpecialOperations() {
        System.out.println("\n=== СПЕЦИАЛЬНЫЕ ОПЕРАЦИИ ===");
        System.out.println("1. Демонстрация плавания всех рыб");
        System.out.println("2. Проверка совместимости тропических рыб");
        System.out.println("3. Проверка способностей золотых рыбок");
        System.out.println("4. Управление всем оборудованием");
        
        int choice = getIntInput("Выберите операцию: ");
        
        switch (choice) {
            case 1:
                demonstrateSwimming();
                break;
            case 2:
                checkTropicalCompatibility();
                break;
            case 3:
                checkGoldfishAbilities();
                break;
            case 4:
                manageAllEquipment();
                break;
            default:
                System.out.println("Неверный выбор.");
        }
    }

    private static void demonstrateSwimming() {
        System.out.println("\n--- Демонстрация плавания ---");
        var fish = aquarium.getAllFish();
        if (fish.isEmpty()) {
            System.out.println("Рыб в аквариуме нет.");
        } else {
            for (Fish f : fish) {
                System.out.println(f.swim());
            }
        }
    }

    private static void checkTropicalCompatibility() {
        System.out.println("\n--- Проверка совместимости тропических рыб ---");
        var fish = aquarium.getAllFish();
        var tropicalFish = fish.stream()
                              .filter(f -> f instanceof TropicalFish)
                              .map(f -> (TropicalFish) f)
                              .toList();
        
        if (tropicalFish.size() < 2) {
            System.out.println("Недостаточно тропических рыб для проверки совместимости.");
        } else {
            for (int i = 0; i < tropicalFish.size(); i++) {
                for (int j = i + 1; j < tropicalFish.size(); j++) {
                    TropicalFish fish1 = tropicalFish.get(i);
                    TropicalFish fish2 = tropicalFish.get(j);
                    boolean compatible = fish1.isCompatibleWith(fish2);
                    System.out.println(fish1.getName() + " и " + fish2.getName() + 
                                     " - " + (compatible ? "совместимы" : "несовместимы"));
                }
            }
        }
    }

    private static void checkGoldfishAbilities() {
        System.out.println("\n--- Способности золотых рыбок ---");
        var fish = aquarium.getAllFish();
        var goldfish = fish.stream()
                          .filter(f -> f instanceof Goldfish)
                          .map(f -> (Goldfish) f)
                          .toList();
        
        if (goldfish.isEmpty()) {
            System.out.println("Золотых рыбок в аквариуме нет.");
        } else {
            for (Goldfish gf : goldfish) {
                System.out.println(gf.getName() + ": " + gf.getAbilities());
            }
        }
    }

    private static void manageAllEquipment() {
        System.out.println("\n--- Управление всем оборудованием ---");
        var equipment = aquarium.getAllEquipment();
        if (equipment.isEmpty()) {
            System.out.println("Оборудования в аквариуме нет.");
        } else {
            boolean turnOn = getBooleanInput("Включить все оборудование? (да/нет): ");
            for (Equipment e : equipment) {
                if (turnOn) {
                    System.out.println(e.turnOn());
                } else {
                    System.out.println(e.turnOff());
                }
            }
        }
    }

    // Вспомогательные методы для ввода
    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите целое число.");
            }
        }
    }

    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите число.");
            }
        }
    }

    private static boolean getBooleanInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("да") || input.equals("yes") || input.equals("true") || input.equals("1")) {
                return true;
            } else if (input.equals("нет") || input.equals("no") || input.equals("false") || input.equals("0")) {
                return false;
            } else {
                System.out.println("Ошибка: введите 'да' или 'нет'.");
            }
        }
    }
}