# ShopSphere – Java Microservices Deployment Lab

A beginner-friendly, five-service Spring Boot project for practicing:

`Local → GitHub → GitHub Actions → Docker → Amazon ECR → Amazon EKS → Argo CD`

## Services

| Service | Port | Main endpoints |
|---|---:|---|
| API Gateway | 8080 | `GET /api/products`, `GET /api/inventory/{id}`, `POST /api/orders` |
| Product | 8081 | `GET /api/products`, `GET /api/products/{id}` |
| Inventory | 8082 | `GET /api/inventory/{productId}` |
| Payment | 8083 | `POST /api/payments` |
| Order | 8084 | `POST /api/orders`, `GET /api/orders/{id}` |

This is a deployment-training application, not a production commerce platform. Data is in-memory and resets when a service restarts. Payment is simulated.

## Prerequisites

- JDK 17
- Maven 3.9+
- Git
- Docker Desktop (for the container stage)

Check installations:

```powershell
java -version
mvn -version
git --version
docker version
```

## 1. Build all services locally

From the repository root:

```powershell
mvn clean package
```

## 2. Run one service locally

Example:

```powershell
cd product-service
mvn spring-boot:run
```

Open `http://localhost:8081/api/products` and `http://localhost:8081/actuator/health`.

Stop with Ctrl+C. Repeat for other services using their configured ports.

## 3. Run all services with Docker Compose

From the repository root:

```powershell
mvn clean package
docker compose up --build
```

Test the gateway:

```powershell
curl.exe http://localhost:8080/api/products
curl.exe http://localhost:8080/api/inventory/101
curl.exe -X POST http://localhost:8080/api/orders -H "Content-Type: application/json" -d "{\"productId\":101,\"quantity\":2}"
```

Stop:

```powershell
docker compose down
```

## 4. GitHub / CI-CD / AWS / Kubernetes

The deployment manifests and pipeline will be added in guided stages after local build and Docker tests pass. Do not create AWS resources yet. We will use GitHub OIDC (no long-lived AWS access keys), ECR repositories, EKS, and an Argo CD Application watching the GitOps directory.

## Suggested learning sequence

1. Build and test locally.
2. Run the five services with Docker Compose.
3. Create a GitHub repository and push the source.
4. Add GitHub Actions for Maven tests and Docker image builds.
5. Configure AWS OIDC and push images to ECR.
6. Provision EKS with Terraform.
7. Add Kubernetes Deployments, Services, probes, and resource requests/limits.
8. Install Argo CD and connect it to the GitOps folder.
9. Change code, push, and observe the GitOps deployment.
10. Add monitoring, rollback practice, and cleanup.

## Important cost warning

EKS control plane and supporting AWS resources can incur charges even when workloads are idle. We will estimate costs before provisioning and destroy the lab infrastructure when finished.
