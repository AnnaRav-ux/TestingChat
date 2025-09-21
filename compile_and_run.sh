#!/bin/bash

echo "=== Компиляция проекта Аквариум ==="

# Создаем директорию для скомпилированных классов
mkdir -p classes

# Компилируем все Java файлы
echo "Компиляция Java файлов..."
javac -d classes src/main/java/aquarium/*.java

if [ $? -eq 0 ]; then
    echo "Компиляция успешна!"
    echo ""
    echo "=== Запуск программы ==="
    java -cp classes aquarium.AquariumApp
else
    echo "Ошибка компиляции!"
    exit 1
fi