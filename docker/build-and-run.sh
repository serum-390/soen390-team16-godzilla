#!/bin/bash

PROJECT_ROOT=../
IMAGE_TAG="ghcr.io/serum-390/godzilla:latest"
NETWORK='godzilla_network'
CONTAINER_NAME='godzilla-test'

build_app() (
    cd "$PROJECT_ROOT"
    ./mvnw clean package
)

build_image() (
    docker build --tag "$IMAGE_TAG" \
                 -f "godzilla.dockerfile" \
                 "$PROJECT_ROOT"
    docker image prune -f
)

run_postgres_and_pgadmin() (
    cd "$PROJECT_ROOT"
    docker-compose down
    docker-compose up -d
)

run_container() {
    docker run -d \
               -e SPRING_PROFILE=dev \
               -e DB_HOST=postgres \
               --network "$NETWORK" \
               --name "$CONTAINER_NAME" \
               -p 0.0.0.0:8080:8080 \
               "$IMAGE_TAG"
}

main() {
    docker container rm -f "$CONTAINER_NAME"
    run_postgres_and_pgadmin
    build_app
    build_image
    run_container
}

[[ ${BASH_SOURCE[0]} == $0 ]] && main $@
