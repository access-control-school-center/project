infra:
	docker-compose up -d
	@sleep 10
	docker-compose exec pg psql -c "CREATE DATABASE \"ceip\";"
	docker-compose exec pg psql -c "GRANT ALL PRIVILEGES ON DATABASE \"ceip\" TO root;"

clear:
	docker-compose down