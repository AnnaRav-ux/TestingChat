package aquarium;

/**
 * Абстрактный базовый класс для всех рыб в аквариуме
 */
public abstract class Fish {
    protected String name;
    protected String species;
    protected int age;
    protected double size; // размер в сантиметрах

    /**
     * Конструктор базового класса Fish
     * @param name имя рыбы
     * @param species вид рыбы
     * @param age возраст в месяцах
     * @param size размер в сантиметрах
     */
    public Fish(String name, String species, int age, double size) {
        this.name = name;
        this.species = species;
        this.age = age;
        this.size = size;
    }

    // Геттеры
    public String getName() {
        return name;
    }

    public String getSpecies() {
        return species;
    }

    public int getAge() {
        return age;
    }

    public double getSize() {
        return size;
    }

    /**
     * Абстрактный метод для плавания рыбы
     * @return строка с описанием плавания
     */
    public abstract String swim();

    @Override
    public String toString() {
        return String.format("Рыба: %s, Вид: %s, Возраст: %d мес., Размер: %.1f см", 
                           name, species, age, size);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Fish fish = (Fish) obj;
        return name.equals(fish.name) && species.equals(fish.species);
    }

    @Override
    public int hashCode() {
        return name.hashCode() + species.hashCode();
    }
}