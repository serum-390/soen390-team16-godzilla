#!/bin/bash

PROJECT_ROOT=../
IMAGE_TAG="ghcr.io/serum-390/godzilla:latest"

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

run_container() {
    CONTAINER_NAME='godzilla-test'
    docker container rm -f "$CONTAINER_NAME"
    docker run -e SPRING_PROFILE=dev \
               --net=host \
               -d \
               --name "$CONTAINER_NAME" \
               -p 0.0.0.0:8080:8080 \
               "$IMAGE_TAG"
}

run_container_prod() {
    CONTAINER_NAME='godzilla-test'
    docker container rm -f "$CONTAINER_NAME"
    docker run -d \
               -e DB_HOST='godzilla-demo-db-2.ceg0j98uvizv.us-east-1.rds.amazonaws.com' \
			   -e DB_USERNAME=$(cat ../PRIVATE/aws/db-creds | head -n 1) \
			   -e DB_PASSWORD=$(cat ../PRIVATE/aws/db-creds | tail -n 1) \
               --name "$CONTAINER_NAME" \
               "$IMAGE_TAG"
}

main() {
    build_app
    build_image
#    run_container
    run_container_prod
}

[[ ${BASH_SOURCE[0]} == $0 ]] && main $@
