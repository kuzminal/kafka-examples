# Kafka Examples Project

Этот проект демонстрирует различные примеры работы с Apache Kafka на Java. Проект включает в себя примеры использования продюсеров, консьюмеров, сериализаторов, десериализаторов, партиционеров, интерсепторов и других компонентов Kafka.

## Структура проекта

### Администрирование Kafka

- `src/main/java/ru/kuzmin/producer/admin/Admin.java` - реализует использование Kafka Admin API для получения списка топиков

### Продюсер (Producer)

- `src/main/java/ru/kuzmin/producer/producer/Producer.java` - реализует два метода отправки сообщений:
  - `produceCountry()` - отправка строковых сообщений в топик CustomerCountry
  - `produceCustomer()` - отправка объектов Customer с использованием кастомного сериализатора и партиционера

### Консьюмер (Consumer)

- `src/main/java/ru/kuzmin/producer/consumer/Consumer.java` - реализует потребление сообщений из топика customerCountries с ручным подтверждением (commitSync)

### Сериализация/Десериализация

- `src/main/java/ru/kuzmin/producer/serializer/CustomerSerializer.java` - кастомный сериализатор, преобразующий объект Customer в байтовый массив по формату:
  - 4 байта для ID
  - 4 байта для длины имени
  - N байт для имени в UTF-8
  
- `src/main/java/ru/kuzmin/producer/deserializer/CustomerDeserializer.java` - кастомный десериализатор, восстанавливающий объект Customer из байтового массива

### Кастомные компоненты

- `src/main/java/ru/kuzmin/producer/partitioner/BananaPartitioner.java` - кастомный партиционер, который:
  - Помещает все сообщения с ключом "Banana" в последний партишн
  - Остальные сообщения распределяет по партишнам с помощью murmur2 хеширования
  
- `src/main/java/ru/kuzmin/producer/interceptor/CountingProducerInterceptor.java` - интерцептор продюсера, который:
  - Подсчитывает количество отправленных и подтвержденных сообщений
  - Регулярно выводит статистику по интервалу (настраивается через конфигурацию)

## Зависимости

- Apache Kafka (версия 3.5.0)
- Gson (версия 2.10.1) - для работы с JSON
- json-smart (версия 2.4.10) - для работы с JSON

## Сценарии запуска

### Предварительные требования

1. Установленный Docker и Docker Compose
2. Установленный JDK 17
3. Установленный Maven

### Запуск Kafka и Zookeeper

```bash
    # Запуск инфраструктуры Kafka с помощью Docker Compose
    docker-compose up -d
```

Проверьте, что контейнеры запущены:
```bash
    docker ps
```

### Сборка проекта

```bash
    # Сборка проекта с помощью Maven
    mvn clean package
```

### Запуск приложений

```bash
    # Запуск продюсера
    java -cp target/kafka_client-1.0-SNAPSHOT.jar ru.kuzmin.producer.App
    
    # Запуск консьюмера
    java -cp target/kafka_client-1.0-SNAPSHOT.jar ru.kuzmin.producer.consumer.Consumer
```

### Остановка инфраструктуры

```bash
    # Остановка и удаление контейнеров
    docker-compose down
```
