# Ecommerce-website

Một dự án trang web thương mại điện tử (Java).

Mô tả

Dự án này là một ứng dụng web thương mại điện tử được phát triển bằng Java. README này cung cấp hướng dẫn cơ bản để thiết lập, chạy và đóng góp vào dự án. Vui lòng cập nhật phần cấu hình cụ thể (ví dụ: cơ sở dữ liệu, biến môi trường) theo thực tế của dự án.

Tính năng chính

- Quản lý sản phẩm
- Giỏ hàng và thanh toán
- Quản lý người dùng và phân quyền
- Tìm kiếm và lọc sản phẩm

Yêu cầu

- Java 11+ (khuyến nghị Java 17)
- Maven hoặc Gradle (tùy theo cấu hình dự án)
- Cơ sở dữ liệu (ví dụ: MySQL, PostgreSQL)

Cài đặt & chạy

1. Clone repository:

   git clone https://github.com/TDatpham/Ecommerce-website.git
   cd Ecommerce-website

2. Cấu hình

   - Tạo hoặc chỉnh sửa file cấu hình (ví dụ: application.properties hoặc application.yml) với thông tin cơ sở dữ liệu và biến môi trường cần thiết.

3. Build và chạy

   Nếu dự án sử dụng Maven:

       ./mvnw clean package
       ./mvnw spring-boot:run  (nếu là Spring Boot)

   Hoặc sử dụng Maven hệ thống:

       mvn clean package
       mvn spring-boot:run

   Nếu dự án sử dụng Gradle:

       ./gradlew build
       ./gradlew bootRun

4. Mở trình duyệt và truy cập http://localhost:8080 (hoặc cổng đã cấu hình).

Cấu hình cơ sở dữ liệu mẫu

- MySQL:

  spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce
  spring.datasource.username=root
  spring.datasource.password=secret

Chạy migration (nếu có)

- Nếu dự án sử dụng Flyway hoặc Liquibase, chạy migration tương ứng trước khi khởi động ứng dụng.

Kiểm thử

- Thực thi các test:

    mvn test    (hoặc ./mvnw test)
    ./gradlew test

Đóng góp

Rất hoan nghênh các PR và issue. Vui lòng đọc CONTRIBUTING.md (nếu có) và tuân theo các quy tắc commit của dự án.

Liên hệ

- Tác giả: TDatpham
- Repo: https://github.com/TDatpham/Ecommerce-website

Giấy phép

Chưa có giấy phép cụ thể — thêm LICENSE nếu bạn muốn công khai điều kiện sử dụng.

Ghi chú

- README này là template cơ bản. Nếu bạn muốn, tôi có thể mở rộng README với phần Kiến trúc, Luồng dữ liệu, Sơ đồ ER của cơ sở dữ liệu, hướng dẫn deploy (Docker / Kubernetes) hoặc ví dụ API chi tiết.