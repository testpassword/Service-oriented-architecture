# JAX-RS и Wildfly

## Генерация ключа

1. Сгенерируй ключ командой, где вместо `$NAME` ваш домен, а вместо `$PASS` пароль для `keystore`-файла.
```shell
keytool -genkeypair -alias $NAME -keyalg RSA -keysize 4096 -storetype jks -keystore $NAME.jks -validity 3650 -storepass $PASS
```

## Настройка для исходящий подключений

2. Создай `JAX-RS` клиент, используя код:
```kotlin
fun getHttpClient(): Client =
    ClientBuilder
        .newBuilder()
        .trustStore(
            KeyStore
                .getInstance(KeyStore.getDefaultType())
                .apply {
                    load(FileInputStream(System.getProperty("ssl_cert")), System.getProperty("ssl_pass").toCharArray())
                }
        )
        .hostnameVerifier { _, _ -> true }
        .build()
```

Параметры `ssl_cert` (путь до `keystore`-файла) и `ssl_pass` (пароль `keystore`) задаются как аргументы виртуальной 
машины при запуске контейнера сервлетов, к примеру:
```shell
./wildfly-19.0.0.Final/bin/standalone.sh -Dservice1_url=https://localhost:8081 -Dssl_cert=$CERT_PATH  -Dssl_pass=$PASS
```

## Настройка для входящих подключений

3. Скачай `Wildfly` версии `19`: на актуальной на момент написания инструкции (`25`) SSL почему-то не работает

4. Обрети абсолютную власть над файлами сервера:
```shell
chmod -R 777 wildfly-19.0.0.Final
```

5. Положи `keystore` в директорию `wildfly-19.0.0.Final/standalone/configuration`

6. Отредактируй файл конфигурации сервера `wildfly-19.0.0.Final/standalone/configuration/standalone.xml`: 
   1. Внутри элемента `<security-realms>` добавь `<security-realm>` для `ssl`-подключений:
   ```xml
    <security-realm name="ssl-realm">
        <server-identities>
            <ssl>
                <keystore path="$CERT_PATH" keystore-password="$PASS" alias="$NAME"/>
            </ssl>
        </server-identities>
   </security-realm>
    ```
   2. Создай прослушиватель для `https`-подключений внутри элемента `<server name="default-server">`:
   ```xml
    <https-listener name="default-ssl" socket-binding="https" security-realm="ssl-realm" enable-http2="true"/>
    ```
   3. Удали стандартный `http`-прослушиватель для запрета небезопасных подключений (надо по заданию так сделать):
   ```xml
    <http-listener name="default" socket-binding="http" redirect-socket="https" enable-http2="true"/>
    ```
   4. Также можно изменить порт для безопасных подключений в 
   `<socket-binding-group name="standard-sockets" default-interface="public" port-offset="${jboss.socket.binding.port-offset:0}">` для избежания конфликтов на helios:
   ```xml
   <socket-binding name="https" port="${jboss.https.port:8443}"/>
    ```

7. Сервис будет доступен по адресу `https:/localhost:8443` **ПОДКЛЮЧАТЬСЯ НАДО ИМЕННО К ПОРТУ, УКАЗАННОМУ КАК ПОРТ ДЛЯ SSL**

## Полезные ссылки

- https://www.youtube.com/watch?v=IcVKA0vaFkc&t=230s
- https://javadev.ru/https/ssl-keystore-java/