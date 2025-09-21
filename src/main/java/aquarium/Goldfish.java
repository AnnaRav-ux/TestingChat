package aquarium;

/**
 * Класс золотой рыбки, наследующий от Fish
 */
public class Goldfish extends Fish {
    private String color; // цвет рыбки
    private boolean isGolden; // является ли золотой

    /**
     * Конструктор золотой рыбки
     * @param name имя рыбы
     * @param species вид рыбы
     * @param age возраст в месяцах
     * @param size размер в сантиметрах
     * @param color цвет рыбки
     * @param isGolden является ли золотой
     */
    public Goldfish(String name, String species, int age, double size, 
                   String color, boolean isGolden) {
        super(name, species, age, size);
        this.color = color;
        this.isGolden = isGolden;
    }

    // Геттеры
    public String getColor() {
        return color;
    }

    public boolean isGolden() {
        return isGolden;
    }

    /**
     * Реализация абстрактного метода swim для золотой рыбки
     * @return строка с описанием плавания
     */
    @Override
    public String swim() {
        String goldenText = isGolden ? "золотая" : "обычная";
        return String.format("%s %s рыбка плавает в аквариуме", name, goldenText);
    }

    @Override
    public String toString() {
        return String.format("%s, Цвет: %s, Золотая: %s", 
                           super.toString(), color, isGolden ? "да" : "нет");
    }

    /**
     * Проверка, может ли рыбка исполнять желания (только золотые)
     * @return true если может исполнять желания
     */
    public boolean canGrantWishes() {
        return isGolden && age >= 12; // только золотые рыбки старше года
    }

    /**
     * Получить описание способностей рыбки
     * @return строка с описанием способностей
     */
    public String getAbilities() {
        if (canGrantWishes()) {
            return "Может исполнять желания";
        } else if (isGolden) {
            return "Золотая рыбка, но еще слишком молода";
        } else {
            return "Обычная рыбка";
        }
    }
}