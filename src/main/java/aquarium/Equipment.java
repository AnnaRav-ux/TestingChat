package aquarium;

/**
 * Класс оборудования для аквариума
 */
public class Equipment {
    private String name; // название оборудования
    private String type; // тип оборудования (фильтр, обогреватель, компрессор и т.д.)
    private boolean isWorking; // работает ли оборудование
    private double power; // мощность в ваттах

    /**
     * Конструктор оборудования
     * @param name название оборудования
     * @param type тип оборудования
     * @param isWorking работает ли оборудование
     * @param power мощность в ваттах
     */
    public Equipment(String name, String type, boolean isWorking, double power) {
        this.name = name;
        this.type = type;
        this.isWorking = isWorking;
        this.power = power;
    }

    // Геттеры
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public boolean isWorking() {
        return isWorking;
    }

    public double getPower() {
        return power;
    }

    // Сеттеры
    public void setWorking(boolean working) {
        this.isWorking = working;
    }

    /**
     * Включить оборудование
     * @return строка с результатом операции
     */
    public String turnOn() {
        if (isWorking) {
            return String.format("%s уже включен", name);
        } else {
            isWorking = true;
            return String.format("%s включен", name);
        }
    }

    /**
     * Выключить оборудование
     * @return строка с результатом операции
     */
    public String turnOff() {
        if (!isWorking) {
            return String.format("%s уже выключен", name);
        } else {
            isWorking = false;
            return String.format("%s выключен", name);
        }
    }

    /**
     * Получить статус оборудования
     * @return строка со статусом
     */
    public String getStatus() {
        return String.format("%s (%s) - %s, мощность: %.1f Вт", 
                           name, type, isWorking ? "работает" : "не работает", power);
    }

    @Override
    public String toString() {
        return String.format("Оборудование: %s, Тип: %s, Статус: %s, Мощность: %.1f Вт", 
                           name, type, isWorking ? "работает" : "не работает", power);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Equipment equipment = (Equipment) obj;
        return name.equals(equipment.name) && type.equals(equipment.type);
    }

    @Override
    public int hashCode() {
        return name.hashCode() + type.hashCode();
    }
}