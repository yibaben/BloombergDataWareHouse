up: build run

build:
	mvn clean install

run:
	docker-compose up -d