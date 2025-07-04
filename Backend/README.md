# 📚 BookStore API

> **BookStore API** là backend service xây dựng bằng **Spring Boot**, cung cấp API cho hệ thống bán sách. Dự án hỗ trợ **JWT Authentication**, **CRUD**, **GraphQL**, và có kiến trúc sẵn sàng mở rộng cho nhiều module như quản lý người dùng, đơn hàng, thanh toán...

---

## 🚀 Demo

GraphQL Playground: `http://localhost:8080/graphql`

REST API : `http://localhost:8080/swagger-ui/`

---

## 🧰 Công nghệ sử dụng

- ☕ Spring Boot 3
- 🔐 Spring Security + JWT
- 🧪 Spring Data JPA
- 🗃️ MySQL
- 🧠 GraphQL (spring-boot-starter-graphql)
- 🧰 Gradle
- 🐳 Docker

---

## 📦 Tính năng chính

- ✅ Đăng ký / Đăng nhập với JWT
- 🔒 Bảo mật endpoint với Spring Security
- 📚 Quản lý sách (CRUD: thêm, sửa, xóa, tìm kiếm)
- 🧑‍💼 Quản lý người dùng
- 🛒 Quản lý đơn hàng, giỏ hàng, thanh toán
- 🔍 Tìm kiếm sách theo tiêu chí: tên, thể loại, tác giả, giá,...
- 🧬 GraphQL query/mutation song song với REST API

---

## 🗂️ Cấu trúc dự án

🛠️ Cài đặt & chạy
Yêu cầu:
Java 21+

Gradle

Cài đặt:
# Clone code
git clone https://github.com/NgocTai-NLUStudent/WebEco.git
cd WebEco
---
# Chạy app
./gradlew bootRun

🔐 Tài khoản mặc định
Tạo khi khởi động (dữ liệu seed hoặc REST/GraphQL mutation)

admin / admin

user  / user@

🔍 Ví dụ Request
REST:
GET /api/books

Authorization: Bearer <jwt-token>

GraphQL:
query {
  products {
    name
    price
  }
}

🐳 Docker 
docker build -t bookstore-api .

docker run -p 8080:8080 bookstore-api
---
📌 Định hướng phát triển

✅ REST + GraphQL song song


🛒 Thêm giỏ hàng, đơn hàng, payment VNPAY

📈 Dashboard doanh thu











