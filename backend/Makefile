build_dev:
	@cp ./src/main/resources/application.dev-docker ./src/main/resources/application.conf
	docker-compose build

build_prod:
	@cp ./src/main/resources/application.prod ./src/main/resources/application.conf
	docker-compose build

infra:
	docker-compose up pg pgadmin -d
	@sleep 10
	docker-compose exec pg psql -U ceip -c "GRANT ALL PRIVILEGES ON DATABASE \"ceip\" TO ceip;"

run:
	docker-compose up -d

clear:
	docker-compose down
