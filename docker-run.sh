NETWORK_NAME=ceip
BACKEND_IMAGE_NAME=ceip-backend:17-04
BACKEND_CONFIG_PATH=backend/src/main/resources
PG_HOST_BIND=5432
BACKEND_HOST_BIND=8080

# create the network
docker network create --attachable ${NETWORK_NAME}

# run the database
docker container run --name ceip-pg --network ${NETWORK_NAME} --hostname pg -v ${PWD}/data:/var/lib/postgresql/data --env-file ${PWD}/backend/.env -p ${PG_HOST_BIND}:5432 -d postgres

# build the production image
cp ${BACKEND_CONFIG_PATH}/application.prod ${BACKEND_CONFIG_PATH}/application.conf
docker image build ${PWD}/backend -t ${BACKEND_IMAGE_NAME}

# configure the database context
sleep 10
docker container exec ceip-pg psql -U ceip -c "CREATE DATABASE \"ceip\";"
docker container exec ceip-pg psql -U ceip -c "GRANT ALL PRIVILEGES ON DATABASE \"ceip\" TO ceip;"

# run the backend
docker container run --name ceip-backend --network ${NETWORK_NAME} --env-file ${PWD}/backend/.env -p ${BACKEND_HOST_BIND}:8080 -d ${BACKEND_IMAGE_NAME}
