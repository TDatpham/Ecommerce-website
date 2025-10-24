# Inventory Service

A simple Spring Boot microservice for managing product inventory.

## Endpoints
- `POST /inventory` create item `{sku, name, quantity}`
- `GET /inventory` list all items
- `GET /inventory/{sku}` get item by SKU
- `POST /inventory/{sku}/adjust?delta=N` adjust quantity by delta (can be negative)

## Run locally
```bash
./mvnw spring-boot:run
```

## Build
```bash
./mvnw -DskipTests package
```

## Docker
```bash
docker build -t inventory-service:latest .
```
