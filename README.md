## Необходимое окружение :
- Git
- Java 11
- ОС : **Windows 10 64-bit**
- IDE : **IntelliJ IDEA**
- Браузер : **Google Chrome**, **Mozilla Firefox**, **Microsoft Edge**


## Запуск :
1. Клонирование репозитория командой _git clone_
2. Запуск тестов
   _./gradlew clean test_
3. Генерация отчета Allure командой _./gradlew allureServe_


## Системные свойства :
- Для запуска тестов в определенном окружении : _-Denvironment=stg_ ("freeze", "stg").
По умолчанию "freeze"
- Для запуска тестов с определенной БД cab_lab : _-Dcab.lab.db.name=cab_lab_collection_ ("freeze_cab_lab_collection", "cab_lab_collection", "test_cab_lab_collection").
По умолчанию "freeze_cab_lab_collection"
- Для запуска тестов в headless-режиме : _-Dselenide.headless=true_ (поддержка "true", "false")
По умолчанию "false"

## Документация :
[Тест-кейсы](https://testit.smuit.ru/projects/8271/tests?isolatedSection=32d71e85-1002-4bea-a286-827d707e3a07)
