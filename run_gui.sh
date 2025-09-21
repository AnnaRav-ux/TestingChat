#!/bin/bash

echo "🐠 Запуск программы 'Аквариум' с графическим интерфейсом 🐠"
echo "=================================================="

# Проверяем наличие Java
if ! command -v java &> /dev/null; then
    echo "❌ Ошибка: Java не установлена или не найдена в PATH"
    exit 1
fi

# Проверяем наличие javac
if ! command -v javac &> /dev/null; then
    echo "❌ Ошибка: Java компилятор (javac) не найден"
    exit 1
fi

echo "✅ Java найдена: $(java -version 2>&1 | head -n 1)"

# Создаем директорию для классов
mkdir -p classes

# Компилируем все Java файлы
echo "🔨 Компиляция проекта..."
javac -d classes src/main/java/aquarium/*.java

if [ $? -eq 0 ]; then
    echo "✅ Компиляция успешна!"
    echo ""
    echo "🚀 Запуск графического интерфейса..."
    echo "   (Закройте окно программы для выхода)"
    echo ""
    
    # Запускаем GUI приложение
    java -cp classes aquarium.AquariumGUI
else
    echo "❌ Ошибка компиляции!"
    echo "Проверьте, что все файлы находятся в правильных местах."
    exit 1
fi