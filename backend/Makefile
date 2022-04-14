infra:
	docker-compose up -d
	@sleep 10
	docker-compose exec pg psql -c "CREATE DATABASE \"ceip\";"
	docker-compose exec pg psql -c "GRANT ALL PRIVILEGES ON DATABASE \"ceip\" TO root;"

seed:
	docker-compose exec pg psql -d ceip -c "INSERT INTO credentials (nusp, password_hash) VALUES ('1234567', '$2a$12$gqIkt78KXVjxknlG0x4kKOSq.97U.aO9gfVl1Qda..CqilOTRE7He');"
	docker-compose exec pg psql -d ceip -c "INSERT INTO people (name, document_type, document_value, shot_date) VALUES ('André', 'RG', '12.123.123-1', '02-03-2022');"
	docker-compose exec pg psql -d ceip -c "INSERT INTO people (name, document_type, document_value, shot_date) VALUES ('Maria', 'CPF', '123.123.123-12', '09-03-2022');"
	docker-compose exec pg psql -d ceip -c "INSERT INTO people (name, document_type, document_value, shot_date) VALUES ('Marcelo', 'CPF', '124.124.124-12', '15-03-2022');"
	docker-compose exec pg psql -d ceip -c "INSERT INTO people (name, document_type, document_value, shot_date) VALUES ('Marcela', 'RG', '12.124.124-2', '28-03-2022');"

clear:
	docker-compose down