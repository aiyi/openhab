To generate source code, go to src/main/resources/schema and run commands:

#xjc -episode model.episode model.xsd -d ..\..\java -p org.openhab.core.model

xjc items.xsd -extension items.xjb -d ..\..\java -b model.episode
