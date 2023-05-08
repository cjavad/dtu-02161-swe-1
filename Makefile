build:

run:
	mvn exec:java -Dexec.mainClass="application.HelloFX" -X

test:
	mvn test

.PHONY: build run
