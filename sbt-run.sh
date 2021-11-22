export SBT_OPTS="-Xss10m -XX:-UseGCOverheadLimit -Djava.net.ssl.trustStorePassword=changeit -Dplay.server.https.keyStore.path=/Users/armili/git/hicp-server/hicp-core/features/hicp/hicp-kernel-feature/jssecacerts -Dplay.server.https.keyStore.password=changeit"

sbt -jvm-debug 5015 run
