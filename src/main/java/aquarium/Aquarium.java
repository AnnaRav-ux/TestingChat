package aquarium;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс аквариума, содержащий рыб и оборудование (две агрегации)
 */
public class Aquarium {
    private String name; // название аквариума
    private double volume; // объем в литрах
    private String location; // местоположение
    private List<Fish> fish; // список рыб (агрегация 1)
    private List<Equipment> equipment; // список оборудования (агрегация 2)

    /**
     * Конструктор аквариума
     * @param name название аквариума
     * @param volume объем в литрах
     * @param location местоположение
     */
    public Aquarium(String name, double volume, String location) {
        this.name = name;
        this.volume = volume;
        this.location = location;
        this.fish = new ArrayList<>();
        this.equipment = new ArrayList<>();
    }

    // Геттеры
    public String getName() {
        return name;
    }

    public double getVolume() {
        return volume;
    }

    public String getLocation() {
        return location;
    }

    public List<Fish> getAllFish() {
        return new ArrayList<>(fish); // возвращаем копию для безопасности
    }

    public List<Equipment> getAllEquipment() {
        return new ArrayList<>(equipment); // возвращаем копию для безопасности
    }

    // CRUD операции для рыб
    /**
     * Добавить рыбу в аквариум
     * @param fish рыба для добавления
     * @return true если рыба добавлена успешно
     */
    public boolean addFish(Fish fish) {
        if (fish == null) {
            System.out.println("Ошибка: нельзя добавить null рыбу");
            return false;
        }
        
        // Проверяем, нет ли уже рыбы с таким именем
        if (this.fish.stream().anyMatch(f -> f.getName().equals(fish.getName()))) {
            System.out.println("Ошибка: рыба с именем '" + fish.getName() + "' уже существует");
            return false;
        }
        
        this.fish.add(fish);
        System.out.println("Рыба " + fish.getName() + " добавлена в аквариум");
        return true;
    }

    /**
     * Удалить рыбу из аквариума по имени
     * @param fishName имя рыбы для удаления
     * @return true если рыба удалена успешно
     */
    public boolean removeFish(String fishName) {
        if (fishName == null || fishName.trim().isEmpty()) {
            System.out.println("Ошибка: имя рыбы не может быть пустым");
            return false;
        }
        
        boolean removed = fish.removeIf(f -> f.getName().equals(fishName));
        if (removed) {
            System.out.println("Рыба " + fishName + " удалена из аквариума");
        } else {
            System.out.println("Рыба с именем '" + fishName + "' не найдена");
        }
        return removed;
    }

    /**
     * Найти рыбу по имени
     * @param fishName имя рыбы
     * @return рыба или null если не найдена
     */
    public Fish findFish(String fishName) {
        return fish.stream()
                  .filter(f -> f.getName().equals(fishName))
                  .findFirst()
                  .orElse(null);
    }

    // CRUD операции для оборудования
    /**
     * Добавить оборудование в аквариум
     * @param equipment оборудование для добавления
     * @return true если оборудование добавлено успешно
     */
    public boolean addEquipment(Equipment equipment) {
        if (equipment == null) {
            System.out.println("Ошибка: нельзя добавить null оборудование");
            return false;
        }
        
        // Проверяем, нет ли уже оборудования с таким именем
        if (this.equipment.stream().anyMatch(e -> e.getName().equals(equipment.getName()))) {
            System.out.println("Ошибка: оборудование с именем '" + equipment.getName() + "' уже существует");
            return false;
        }
        
        this.equipment.add(equipment);
        System.out.println("Оборудование " + equipment.getName() + " добавлено в аквариум");
        return true;
    }

    /**
     * Удалить оборудование из аквариума по имени
     * @param equipmentName имя оборудования для удаления
     * @return true если оборудование удалено успешно
     */
    public boolean removeEquipment(String equipmentName) {
        if (equipmentName == null || equipmentName.trim().isEmpty()) {
            System.out.println("Ошибка: имя оборудования не может быть пустым");
            return false;
        }
        
        boolean removed = equipment.removeIf(e -> e.getName().equals(equipmentName));
        if (removed) {
            System.out.println("Оборудование " + equipmentName + " удалено из аквариума");
        } else {
            System.out.println("Оборудование с именем '" + equipmentName + "' не найдено");
        }
        return removed;
    }

    /**
     * Найти оборудование по имени
     * @param equipmentName имя оборудования
     * @return оборудование или null если не найдено
     */
    public Equipment findEquipment(String equipmentName) {
        return equipment.stream()
                       .filter(e -> e.getName().equals(equipmentName))
                       .findFirst()
                       .orElse(null);
    }

    /**
     * Проверка на непротиворечивость полей аквариума
     * @return true если все поля непротиворечивы
     */
    public boolean validateConsistency() {
        boolean isValid = true;
        
        // Проверка 1: название аквариума не должно быть пустым
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Ошибка: название аквариума не может быть пустым");
            isValid = false;
        }
        
        // Проверка 2: объем должен быть положительным
        if (volume <= 0) {
            System.out.println("Ошибка: объем аквариума должен быть положительным");
            isValid = false;
        }
        
        // Проверка 3: местоположение не должно быть пустым
        if (location == null || location.trim().isEmpty()) {
            System.out.println("Ошибка: местоположение аквариума не может быть пустым");
            isValid = false;
        }
        
        // Проверка 4: название и местоположение не должны совпадать
        if (name != null && location != null && name.equals(location)) {
            System.out.println("Ошибка: название аквариума не должно совпадать с местоположением");
            isValid = false;
        }
        
        // Проверка 5: проверка уникальности имен рыб
        long uniqueFishNames = fish.stream().map(Fish::getName).distinct().count();
        if (uniqueFishNames != fish.size()) {
            System.out.println("Ошибка: найдены дублирующиеся имена рыб");
            isValid = false;
        }
        
        // Проверка 6: проверка уникальности имен оборудования
        long uniqueEquipmentNames = equipment.stream().map(Equipment::getName).distinct().count();
        if (uniqueEquipmentNames != equipment.size()) {
            System.out.println("Ошибка: найдены дублирующиеся имена оборудования");
            isValid = false;
        }
        
        // Проверка 7: проверка совместимости тропических рыб
        List<TropicalFish> tropicalFish = fish.stream()
                                             .filter(f -> f instanceof TropicalFish)
                                             .map(f -> (TropicalFish) f)
                                             .collect(Collectors.toList());
        
        for (int i = 0; i < tropicalFish.size(); i++) {
            for (int j = i + 1; j < tropicalFish.size(); j++) {
                if (!tropicalFish.get(i).isCompatibleWith(tropicalFish.get(j))) {
                    System.out.println("Предупреждение: тропические рыбы " + 
                                     tropicalFish.get(i).getName() + " и " + 
                                     tropicalFish.get(j).getName() + " могут быть несовместимы");
                }
            }
        }
        
        if (isValid) {
            System.out.println("Проверка непротиворечивости пройдена успешно");
        }
        
        return isValid;
    }

    /**
     * Получить статистику аквариума
     * @return строка со статистикой
     */
    public String getStatistics() {
        long tropicalCount = fish.stream().filter(f -> f instanceof TropicalFish).count();
        long goldfishCount = fish.stream().filter(f -> f instanceof Goldfish).count();
        long workingEquipment = equipment.stream().filter(Equipment::isWorking).count();
        
        return String.format("Статистика аквариума '%s':\n" +
                           "- Объем: %.1f л\n" +
                           "- Местоположение: %s\n" +
                           "- Всего рыб: %d (тропических: %d, золотых рыбок: %d)\n" +
                           "- Всего оборудования: %d (работающего: %d)",
                           name, volume, location, fish.size(), tropicalCount, goldfishCount,
                           equipment.size(), workingEquipment);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== АКВАРИУМ ===\n");
        sb.append("Название: ").append(name).append("\n");
        sb.append("Объем: ").append(volume).append(" л\n");
        sb.append("Местоположение: ").append(location).append("\n\n");
        
        sb.append("--- РЫБЫ ---\n");
        if (fish.isEmpty()) {
            sb.append("Рыб нет\n");
        } else {
            for (Fish f : fish) {
                sb.append("- ").append(f.toString()).append("\n");
            }
        }
        
        sb.append("\n--- ОБОРУДОВАНИЕ ---\n");
        if (equipment.isEmpty()) {
            sb.append("Оборудования нет\n");
        } else {
            for (Equipment e : equipment) {
                sb.append("- ").append(e.toString()).append("\n");
            }
        }
        
        return sb.toString();
    }
}