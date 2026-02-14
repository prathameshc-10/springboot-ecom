# 🛒 Spring Boot E-Commerce Backend

A scalable RESTful backend application built using Spring Boot for managing products, users, shopping cart, and order processing.  
This project demonstrates clean architecture, secure authentication, transaction management, and database optimization.

---

## 🚀 Features

- Product management (CRUD operations)
- Shopping cart functionality
- Order placement and tracking
- Transaction management using @Transactional
- Pagination, sorting, and filtering
- Global exception handling
- Layered architecture (Controller → Service → Repository)

---

## 🛠 Tech Stack

- Java 17  
- Spring Boot    
- Spring Data JPA  
- Hibernate ORM  
- PostgreSQL  
- Maven  

---

## 📂 Project Structure

```
src/main/java
└── com.example.springecom
    ├── controller
    ├── model
    ├── repository
    └── service
```

---

## 📌 API Endpoints

### 📦 Products

| Method | Endpoint              | Description            |
|--------|----------------------|------------------------|
| GET    | /products            | Get all products       |
| GET    | /products/{id}       | Get product by ID      |
| GET    | /product/{id}/image  | Get product image      |
| GET    | /product/search      | Search product         |
| POST   | /product             | Create product         |
| PUT    | /product/{id}        | Update product         |
| DELETE | /product/{id}        | Delete product         |

---

### 📦 Orders

| Method | Endpoint         | Description           |
|--------|-----------------|-----------------------|
| POST   | /orders/place   | Place new order       |
| GET    | /orders         | Get user orders       |
| GET    | /orders/{id}    | Get order by ID       |

---

## ⚙️ How to Run Locally

1. Clone the repository

```
git clone https://github.com/prathameshc-10/springboot-ecom.git
```

2. Configure database in `application.properties`

```
spring.datasource.url=jdbc:postgresql://localhost:5432/ecomdb
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

3. Build the project

```
mvn clean install
```

4. Run the application

```
mvn spring-boot:run
```

5. The application will start at:

```
http://localhost:8080
```

---

## 📈 Performance & Design

- Optimized queries using pagination & sorting  
- Normalized relational database schema  
- Proper entity relationships (One-to-Many, Many-to-One)  
- Clean layered MVC architecture  

---

## 🔮 Future Enhancements

1. Payment gateway integration  
2. Email notifications  
3. Swagger/OpenAPI documentation    
4. Redis caching  
5. Docker containerization  
6. Deployment on AWS / Azure  

---

## 📜 License

This project is licensed under the MIT License.

---

## 👨‍💻 Author

Prathamesh Chaudhari  
