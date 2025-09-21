# Диаграмма классов модели "Аквариум"

```
classDiagram
    class Aquarium {
        -String name
        -double volume
        -String location
        -List~Fish~ fish
        -List~Equipment~ equipment
        +Aquarium(String name, double volume, String location)
        +addFish(Fish fish) boolean
        +removeFish(String fishName) boolean
        +addEquipment(Equipment equipment) boolean
        +removeEquipment(String equipmentName) boolean
        +getAllFish() List~Fish~
        +getAllEquipment() List~Equipment~
        +validateConsistency() boolean
        +toString() String
    }
    
    class Fish {
        <<abstract>>
        #String name
        #String species
        #int age
        #double size
        +Fish(String name, String species, int age, double size)
        +getName() String
        +getSpecies() String
        +getAge() int
        +getSize() double
        +swim() String
        +toString() String
    }
    
    class TropicalFish {
        -String waterType
        -int temperature
        +TropicalFish(String name, String species, int age, double size, String waterType, int temperature)
        +getWaterType() String
        +getTemperature() int
        +swim() String
        +toString() String
    }
    
    class Goldfish {
        -String color
        -boolean isGolden
        +Goldfish(String name, String species, int age, double size, String color, boolean isGolden)
        +getColor() String
        +isGolden() boolean
        +swim() String
        +toString() String
    }
    
    class Equipment {
        -String name
        -String type
        -boolean isWorking
        -double power
        +Equipment(String name, String type, boolean isWorking, double power)
        +getName() String
        +getType() String
        +isWorking() boolean
        +getPower() double
        +turnOn() String
        +turnOff() String
        +toString() String
    }
    
    Fish <|-- TropicalFish : inheritance
    Fish <|-- Goldfish : inheritance
    Aquarium o-- Fish : aggregation
    Aquarium o-- Equipment : aggregation
```

## Описание классов:

1. **Fish** - абстрактный базовый класс для всех рыб
2. **TropicalFish** - тропическая рыба (наследование от Fish)
3. **Goldfish** - золотая рыбка (наследование от Fish)
4. **Equipment** - оборудование для аквариума
5. **Aquarium** - аквариум, содержащий рыб и оборудование (две агрегации)

## Агрегации:
- Aquarium содержит список Fish
- Aquarium содержит список Equipment

## Наследование:
- TropicalFish наследует от Fish
- Goldfish наследует от Fish