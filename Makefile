DOCKER_COMPOSE=docker compose
SERVICE_NAME=strictproto-dev

build:
	$(DOCKER_COMPOSE) build --no-cache

up:
	$(DOCKER_COMPOSE) up -d

down:
	$(DOCKER_COMPOSE) down

bash:
	$(DOCKER_COMPOSE) exec $(SERVICE_NAME) bash

generate-strictproto:
	$(DOCKER_COMPOSE) run --rm $(SERVICE_NAME) bash scripts/generate-strictproto.sh

generate-java:
	$(DOCKER_COMPOSE) run --rm $(SERVICE_NAME) bash scripts/generate-java.sh

generate-all: generate-strictproto generate-java
