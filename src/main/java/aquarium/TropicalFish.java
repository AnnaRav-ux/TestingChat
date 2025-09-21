package aquarium;

/**
 * Класс тропической рыбы, наследующий от Fish
 */
public class TropicalFish extends Fish {
    private String waterType; // тип воды (пресная/соленая)
    private int temperature; // предпочитаемая температура

    /**
     * Конструктор тропической рыбы
     * @param name имя рыбы
     * @param species вид рыбы
     * @param age возраст в месяцах
     * @param size размер в сантиметрах
     * @param waterType тип воды
     * @param temperature предпочитаемая температура
     */
    public TropicalFish(String name, String species, int age, double size, 
                       String waterType, int temperature) {
        super(name, species, age, size);
        this.waterType = waterType;
        this.temperature = temperature;
    }

    // Геттеры
    public String getWaterType() {
        return waterType;
    }

    public int getTemperature() {
        return temperature;
    }

    /**
     * Реализация абстрактного метода swim для тропической рыбы
     * @return строка с описанием плавания
     */
    @Override
    public String swim() {
        return String.format("%s плавает в %s воде при температуре %d°C", 
                           name, waterType, temperature);
    }

    @Override
    public String toString() {
        return String.format("%s, Тип воды: %s, Температура: %d°C", 
                           super.toString(), waterType, temperature);
    }

    /**
     * Проверка совместимости с другими тропическими рыбами
     * @param other другая тропическая рыба
     * @return true если рыбы совместимы
     */
    public boolean isCompatibleWith(TropicalFish other) {
        return this.waterType.equals(other.waterType) && 
               Math.abs(this.temperature - other.temperature) <= 2;
    }
}