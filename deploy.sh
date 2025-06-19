echo "🛑 Stopping existing services..."
docker-compose down

echo "✅ Building and starting services..."
docker-compose up --build -d

echo "✅ Deployment complete."

# 서비스 상태 확인
echo "📊 Services status:"
docker-compose ps

# 앱 로그 확인 (선택사항)
echo "📋 Recent app logs:"
docker-compose logs --tail 10 app