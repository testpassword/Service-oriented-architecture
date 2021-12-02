./wildfly-19.0.0.Final/bin/add-user.sh # для доступа к панели администрирования
cp helios.p12 wildfly-19.0.0.Final/standalone/configuration
./wildfly-19.0.0.Final/bin/standalone.sh -Dservice1_url=https://localhost:26557 -Dssl_cert=helios.p12  -Dssl_pass=helios