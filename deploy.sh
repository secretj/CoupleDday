#!/bin/bash

# to do : blue green apply
echo "✅ Building image..."
docker build -t couple_dday .

echo "🛑 Stopping old container..."
docker stop couple_dday 2>/dev/null
docker rm couple_dday 2>/dev/null

echo "🚀 Starting new container..."
docker run --name couple_dday \
  -e DB_USERNAME=secretj \
  -e DB_PASSWORD=test! \
  --network container:mysql \
  -d couple_dday

echo "✅ Deployment complete."

