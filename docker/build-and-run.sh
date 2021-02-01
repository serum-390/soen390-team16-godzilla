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
    docker run -d \
               --name "$CONTAINER_NAME" \
               -p 0.0.0.0:8080:8080 \
               "$IMAGE_TAG"
}

main() {
    build_app
    build_image
    run_container
}

[[ ${BASH_SOURCE[0]} == $0 ]] && main $@
