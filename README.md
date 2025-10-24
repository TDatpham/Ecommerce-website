## Hướng dẫn chạy dự án

Dự án gồm 2 service Spring Boot:
- `Ecom/`: ứng dụng e-commerce chính (mặc định chạy cổng 8080)
- `inventory-service/`: microservice quản lý tồn kho (mặc định chạy cổng 8081, dùng H2 in-memory)

### Yêu cầu môi trường
- Java 17 (Temurin/OpenJDK 17)
- Maven (đã kèm `mvnw`/`mvnw.cmd`, không cần cài đặt thêm)
- Docker (tuỳ chọn, nếu muốn chạy bằng container)

### Cấu trúc thư mục
- `Ecom/`: mã nguồn, `pom.xml`, `Dockerfile`
- `inventory-service/`: mã nguồn, `pom.xml`, `Dockerfile`

---

## Chạy nhanh bằng Maven
Mở 2 terminal riêng cho từng service.

### 1) Chạy `inventory-service` (cổng 8081)
Service này đã cấu hình H2 in-memory sẵn, chỉ cần chạy:
```bash
cd inventory-service
./mvnw spring-boot:run
```
Windows:
```bash
cd inventory-service
mvnw.cmd spring-boot:run
```

### 2) Chạy `Ecom` (cổng 8080)
`Ecom` cần cấu hình database. Chọn 1 trong 2 cách:

- Cách A: Dùng H2 tạm thời cho môi trường local
  - Tạo file `Ecom/src/main/resources/application.properties` với nội dung mẫu:
    ```properties
    server.port=8080
    spring.h2.console.enabled=true
    spring.datasource.url=jdbc:h2:mem:ecomdb;MODE=MySQL;DATABASE_TO_LOWER=TRUE;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
    spring.datasource.driver-class-name=org.h2.Driver
    spring.datasource.username=sa
    spring.datasource.password=
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=false
    ```
  - Chạy:
    ```bash
    cd Ecom
    ./mvnw spring-boot:run
    ```

- Cách B: Dùng MySQL thật (khuyến nghị cho phát triển/triển khai)
  - Đảm bảo có MySQL đang chạy và tạo sẵn database, ví dụ `ecom`.
  - Cấu hình qua biến môi trường (ví dụ khi chạy local):
    ```bash
    export SPRING_DATASOURCE_URL="jdbc:mysql://localhost:3306/ecom?useSSL=false&serverTimezone=UTC"
    export SPRING_DATASOURCE_USERNAME="root"
    export SPRING_DATASOURCE_PASSWORD="<your_password>"
    export SPRING_JPA_HIBERNATE_DDL_AUTO="update"
    cd Ecom && ./mvnw spring-boot:run
    ```

Lưu ý thêm (tuỳ chọn, chỉ khi dùng các chức năng tương ứng):
- Gửi email: đặt `SPRING_MAIL_USERNAME`, `SPRING_MAIL_PASSWORD`
- Thanh toán Razorpay: đặt `RAZORPAY_KEY_ID`, `RAZORPAY_KEY_SECRET`

---

## Build JAR
Thực hiện trong từng thư mục service.

- `Ecom/`:
  ```bash
  cd Ecom
  ./mvnw -DskipTests package
  # File JAR sẽ nằm trong thư mục target/
  ```

- `inventory-service/`:
  ```bash
  cd inventory-service
  ./mvnw -DskipTests package
  # Sinh ra target/inventory-service-0.0.1-SNAPSHOT.jar
  ```

---

## Chạy bằng Docker (tuỳ chọn)
Xây dựng image cho từng service rồi chạy.

- Build images:
  ```bash
  docker build -t ecom:latest Ecom
  docker build -t inventory-service:latest inventory-service
  ```

- Chạy containers:
  - `inventory-service` (cổng 8081):
    ```bash
    docker run --rm -p 8081:8081 inventory-service:latest
    ```
  - `Ecom` (cổng 8080) — cần DB (ví dụ MySQL chạy ngoài):
    ```bash
    docker run --rm -p 8080:8080 \
      -e SPRING_DATASOURCE_URL="jdbc:mysql://host.docker.internal:3306/ecom?useSSL=false&serverTimezone=UTC" \
      -e SPRING_DATASOURCE_USERNAME="root" \
      -e SPRING_DATASOURCE_PASSWORD="<your_password>" \
      -e SPRING_JPA_HIBERNATE_DDL_AUTO="update" \
      ecom:latest
    ```
    Trên Linux, thay `host.docker.internal` bằng IP/hostname của máy chạy MySQL.

---

## Kiểm tra nhanh API (inventory-service)
Các endpoint chính:
- `POST /inventory` tạo item `{sku, name, quantity}`
- `GET /inventory` liệt kê tất cả
- `GET /inventory/{sku}` lấy chi tiết theo SKU
- `POST /inventory/{sku}/adjust?delta=N` điều chỉnh số lượng (N có thể âm)

---

## Gợi ý khắc phục sự cố
- Nếu `Ecom` báo lỗi thiếu DataSource: hãy cấu hình H2 (Cách A) hoặc đặt biến môi trường MySQL (Cách B).
- Kiểm tra phiên bản Java: đảm bảo `java -version` hiển thị 17.
- Cổng đã bị chiếm dụng: đổi `server.port` hoặc dừng service đang chạy cổng đó.
