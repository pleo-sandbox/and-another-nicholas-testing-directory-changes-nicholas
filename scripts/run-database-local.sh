#!/usr/bin/env bash
set -euo pipefail

CONTAINER_NAME="and-another-nicholas-testing-directory-changes-nicholas_database"
VOLUME_NAME="${CONTAINER_NAME}_volume"

POSTGRES_VERSION=17

# Check if the container is already running
if docker ps --format '{{.Names}}' | grep -q "^${CONTAINER_NAME}$"; then
    echo "Container ${CONTAINER_NAME} is already running."
else
    echo "Container ${CONTAINER_NAME} is not running. Starting a new container..."

    docker run --rm -d \
      --name ${CONTAINER_NAME} \
      -p 5432:5432 \
      -e POSTGRES_DB=andanothernicholastestingdirectorychangesnicholas \
      -e POSTGRES_USER=pleo \
      -e POSTGRES_PASSWORD=pleo \
      -v ${VOLUME_NAME}:/var/lib/postgresql/data \
      postgres:${POSTGRES_VERSION}
fi
