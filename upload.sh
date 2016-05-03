source "$HOME/.jenv/bin/jenv-init.sh"
jenv use java 1.7.0_75
mvn clean package
scp ./target/sonar-test-pyramid-plugin-1.0.0-SNAPSHOT.jar root@120.27.104.224:/root/
