./consul agent -dev # 1. запуск consul
# 2. запуск service1 на портах 26556 и 26557
sudo ./standalone.sh -Dconsul_url=http://localhost:8500 # 3. запуск service2 в директории bin на портах 26554 и 26555
./opt/homebrew/opt/haproxy/bin/haproxy -f /opt/homebrew/etc/haproxy.cfg # 4. запуск haproxy (файл конфигурации надо создать самостоятельно)

curl localhost:8500/v1/agent/services # получить список сервисов в consul