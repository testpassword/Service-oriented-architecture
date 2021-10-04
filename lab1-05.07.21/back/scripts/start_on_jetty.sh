JETTY_DIR=~/Desktop/jetty-distribution-9.4.43.v20210629
cp ../target/back-1.0-ULTIMATE.war $JETTY_DIR/webapps/
java -jar $JETTY_DIR/start.jar -Ddatabase=jdbc:postgresql://postgres:root@localhost:5432/soa1 -Djetty.port=9090