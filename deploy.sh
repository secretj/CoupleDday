#!/bin/bash

# to do : blue green apply
echo "✅ Building image..."
docker build -t couple_dday .

echo "🛑 Stopping old container..."
docker stop couple_dday 2>/dev/null
docker rm couple_dday 2>/dev/null

echo "🚀 Starting new container..."
docker run --name couple_dday \
  --network dday-net \
  -p 8080:8080 \
  -p 5005:5005 \
  -e DB_USERNAME=secretj \
  -e DB_PASSWORD=test! \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/couple_dday \
  -e REDIS_HOST=redis \
  -e REDIS_PORT=6379 \
  -e REDIS_PASSWORD=test! \
  --restart unless-stopped \
  --env-file .env \
  -d couple_dday

echo "✅ Deployment complete."

# 컨테이너 상태 확인
echo "📊 Container status:"
docker ps | grep couple_dday

# 로그 확인 (선택사항)
echo "📋 Recent logs:"
docker logs --tail 10 couple_dday