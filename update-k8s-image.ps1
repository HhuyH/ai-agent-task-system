$tag = Read-Host "Enter image tag"

kubectl set image deployment/backend-deployment `
backend=hhuyh/ai-agent-backend:$tag

kubectl rollout status deployment/backend-deployment