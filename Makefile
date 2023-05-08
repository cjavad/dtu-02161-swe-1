build:

run:
	mvn exec:java -Dexec.mainClass="application.HelloFX"

test:
	mvn test

.PHONY: build run
