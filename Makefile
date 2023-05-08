build:

run:
	mvn exec:java -Dexec.mainClass="application.HelloFX" -X

test:
	mvn clean test

.PHONY: build run
