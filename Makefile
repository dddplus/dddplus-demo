package:clean
	@mvn package

package-plugin:clean
	@mvn package -Pplugin

clean:
	@mvn clean

run:package
	@java -jar order-center-cp/cp-oc-main/target/dddplus-demo.jar

run-plugin:package-plugin
	@java -jar order-center-cp/cp-oc-main/target/dddplus-demo.jar 9090 plugin

depgraph:
	@mvn install
	@mvn com.github.ferstl:depgraph-maven-plugin:aggregate -DcreateImage=true -DreduceEdges=false -Dscope=compile "-Dincludes=*:*"
	@open target/dependency-graph.png
