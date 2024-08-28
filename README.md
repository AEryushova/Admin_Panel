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
- Для запуска тестов в определенном браузере : _"-Dselenide.browser=firefox"_ ("chrome", "firefox", "edge").
  По умолчанию "chrome"
- Для запуска тестов в определенном окружении : _"-Denvironment=freeze"_ ("freeze", "stg").
По умолчанию "stg"
- Для запуска тестов в headless-режиме : _"-Dselenide.headless=true"_ ("false", "true").
  По умолчанию "true"
- Для запуска тестов с определенной БД cab_lab : _"-Dcab.lab.db.name=freeze_cab_lab_collection"_ ("freeze_cab_lab_collection", "cab_lab_collection").
По умолчанию "cab_lab_collection"


## Документация :
[Тест-кейсы](https://testit.smuit.ru/projects/8271/tests?isolatedSection=32d71e85-1002-4bea-a286-827d707e3a07)
