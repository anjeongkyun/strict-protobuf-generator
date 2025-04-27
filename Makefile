DOCKER_COMPOSE=docker compose
SERVICE_NAME=strictproto-dev

build:
	$(DOCKER_COMPOSE) build

up:
	$(DOCKER_COMPOSE) up -d

down:
	$(DOCKER_COMPOSE) down

bash:
	$(DOCKER_COMPOSE) exec $(SERVICE_NAME) bash

example-protoc-run:
	$(DOCKER_COMPOSE) run --rm $(SERVICE_NAME) bash -c "\
		mkdir -p /workspace/generated && \
		chmod +x scripts/protoc-gen-strictproto && \
		protoc \
			--plugin=protoc-gen-strictproto=/workspace/scripts/protoc-gen-strictproto \
			--strictproto_out=/workspace/generated \
			--proto_path=/workspace/examples \
			/workspace/examples/Member.proto"
