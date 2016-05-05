source "$HOME/.jenv/bin/jenv-init.sh"
jenv use java 1.7.0_75
mvn clean package
scp ./target/sonar-lean-test-pyramid-plugin-1.0.1-SNAPSHOT.jar root@sonar-server:/root/
