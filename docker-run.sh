NETWORK_NAME=ceip
BACKEND_IMAGE_NAME=ceip-backend:17-04
FRONTEND_IMAGE_NAME=ceip-frontend:17-04
BACKEND_HOST_BIND=6080
FRONTEND_HOST_BIND=6070

# create the network
docker network create --attachable ${NETWORK_NAME}

# run the database
docker container run --name ceip-pg --network ${NETWORK_NAME} --hostname pg -v ${PWD}/data:/var/lib/postgresql/data -v ${PWD}/seeds.sql:/seeds.sql --env-file ${PWD}/backend/.env -d postgres

# build the production images
docker image build ${PWD}/backend -t ${BACKEND_IMAGE_NAME}
docker image build ${PWD}/frontend -t ${FRONTEND_IMAGE_NAME}

# configure the database context
sleep 10
docker container exec ceip-pg psql -U ceip -c "CREATE DATABASE \"ceip\";"
docker container exec ceip-pg psql -U ceip -c "GRANT ALL PRIVILEGES ON DATABASE \"ceip\" TO ceip;"

# run the backend
docker container run --name ceip-backend --network ${NETWORK_NAME} --env-file ${PWD}/backend/.env -p ${BACKEND_HOST_BIND}:8080 -d ${BACKEND_IMAGE_NAME}

# run the backend
docker container run --name ceip-frontend --network ${NETWORK_NAME} -p ${FRONTEND_HOST_BIND}:3000 -d ${FRONTEND_IMAGE_NAME}
