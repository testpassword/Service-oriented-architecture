# Spring и SSL

## Генерация ключа

1. Сгенерируй ключ командой, где вместо `$NAME` ваш домен, а вместо `$PASS` пароль для `keystore`-файла.
```shell
keytool -genkeypair -alias $NAME -keyalg RSA -keysize 4096 -storetype PKCS12 -keystore $NAME.p12 -validity 3650 -storepass $PASS
```

## Настройка для входящих подключений

2. Полученный файл положи в `src/main/resources`.

3. В `application.properties` нужно указать параметры конфигурации сервера (не забудь заменить переменные на свои значения):
```
server.ssl.enabled=true
server.ssl.key-store=classpath:$NAME.p12
server.ssl.key-store-password=$PASS
server.ssl.key-store-type=pkcs12
server.ssl.key-alias=$NAME
server.ssl.key-password=$PASS
```

4. Создай класс конфигурации:
```kotlin
@Configuration class SecurityConfig {
    @Bean fun filterChain(http: HttpSecurity): SecurityFilterChain =
        http
            .csrf()
            .disable()
            .requiresChannel { it.anyRequest().requiresSecure() }
            .authorizeRequests { it.anyRequest().permitAll() }
            .build()
}
```

## Полезные ссылки

- https://www.thomasvitale.com/https-spring-boot-ssl-certificate/
