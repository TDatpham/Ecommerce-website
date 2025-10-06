
# E-Commerce Application

An end-to-end E-Commerce application built using **Spring Boot** and **RESTful APIs** with advanced features for security, usability, and performance.

---

## 🚀 Features

### **Public Functionalities**
- 🔍 **Search Products**: Search for products using keywords.
- 🛒 **Filter & Sort Products**:
  - Filter by **minimum/maximum price** and **rating**.
  - Sort products by **price ascending** or **descending**.
- 📂 **View Categories**: Explore product categories.
- 📝 **Product Reviews**: View reviews and ratings for products.
- 🔑 **Password Reset with Email OTP Verification**: Secure password recovery process.
- 🔐 **User Sign-In**: JWT-based authentication for secure access.

### **Admin Functionalities**
- 🛠️ **Product & Category Management**:
  - Add, update, and manage products and categories.
- 📊 **User Analytics**: View the total number of registered users.

### **Customer Functionalities**
- 🛒 **Cart Management**:
  - Add/remove items from the cart.
  - Update item quantities.
- 💳 **Buy Products**: Secure product checkout and payment.
- ⭐ **Add Reviews**: Post reviews for purchased products.
- 📦 **Order History**: View order and cart history.

---

## 🌟 Advanced Features

### 🔒 **JWT-Based Authentication**
- Role-based access control for users (Admin/Customer/Public).
- Token expiration and refresh handling.

### 🔑 **Two-Step Verification**
- Added security with **email OTP verification** for sensitive actions like password changes.

### ⏳ **Backend Scheduling**
- Automated deletion of expired OTPs using Spring Scheduler.

### 💳 **Payment Gateway Integration**
- Integrated **RazorPay** for seamless and secure payments during checkout.

### 📄 **Pagination & Sorting**
- Optimized product search results with pagination and sorting for a better user experience.

### 📤 **Robust API Design**
- RESTful endpoints with proper request validation and error handling.
- Standardized response structure for consistent data delivery.

<!---
- 🎥 **Project Overview Video**:  [click here](https://drive.google.com/file/d/1XXhl1-LW8z3Q9iWlPymOpFeuIzd-KmGe/view?usp=sharing).
- 🎥 **Project Demo Video**:  [click here](https://drive.google.com/file/d/1t2BASf5sNGW4Nak7Cm05eEaErl4flt8H/view?usp=sharing).
--->

## 🛠️ **Tech Stack**
- **Backend**: Spring Boot, Spring Security, Hibernate, MySQL.
- **Frontend**: HTML, CSS, JavaScript.
- **Security**: JWT Authentication, Email OTP Verification.
- **Payment**: RazorPay Payment Gateway.
- **Scheduling**: Spring Scheduler for task automation.

---

## 📂 **Project Structure**
```
Ecommerce
├── src
│   └── main
│       ├── java
│       │   └── com
│       │       └── app
│       │           ├── config               # Configuration package
│       │           ├── controller           # REST controllers package
│       │           ├── dtos                 # Data Transfer Objects package
│       │           ├── exceptions           # Custom exceptions package
│       │           ├── model                # JPA entities package
│       │           ├── repo                 # Repositories package
│       │           ├── services             # Service layer package
│       │           └── utils                # Utility classes package
│       └── resources
│           └── application.properties       # Application configuration
├── src
│   └── test                                 # Test package
└── pom.xml                                  # Maven build configuration

```

---


# API Documentation
###  User Sign-In

- **URL**: `/app/signIn`
- **Method**: `GET`
- **Description**: Authenticate any user (e.g., customer, admin) using Basic Authentication and provide a token for secure access.

### Request Headers

```http
Authorization: Basic base64encoded(email:password)
```
---
## Customer Endpoints

### 1. Customer Registration

- **URL**: `/app/customer/register`
- **Method**: `POST`
- **Description**: Create a customer account.

#### Request Body

```json
{
  "name": "username",
  "email": "user@gmail.com",
  "password": "user1234",
  "city": "usercity"
} 
```
### 2. Customer Profile

- **URL**: `/app/customer/profile`
- **Method**: `GET`
- **Description**: Fetches the profile details of the authenticated customer.
- **Authentication**: Requires JWT token in the request header for authentication.


#### Request Headers

| Key           | Type   | Description                    |
|---------------|--------|--------------------------------|
| `Authorization` | `JWT Token` | Bearer token for authentication |



## 3. Customer Change Password Process

This process involves two steps to securely change a customer's password.

---

### STEP 1: Request OTP for Password Change

- **URL**: `/app/customer/change-password`
- **Method**: `POST`
- **Description**: Verifies the current password. If the password is valid, sends otp to user's registered email for verification.
- **Authentication**: Requires JWT token in the request header for authentication.

#### Request Headers

| Key             | Type        | Description                      |
|-----------------|-------------|----------------------------------|
| `Authorization` | `JWT Token` | Bearer token for authentication  |

#### Request Body

```json
{
  "oldPassword": "userOldPassword"
}
```

### STEP 2: Verify OTP and Change Password

- **URL**: `/app/customer/change-password/verify-otp`
- **Method**: `POST`
- **Description**: This request verifies the OTP and changes the customer's password. The request requires the old password, new password and otp
- **Authentication**: Requires JWT token in the request header for authentication.

#### Request Headers

| Key             | Type        | Description                      |
|-----------------|-------------|----------------------------------|
| `Authorization` | `JWT Token` | Bearer token for authentication  |

#### Request Body

```json
{
  "oldPassword": "userOldPassword",
  "newPassword": "userNewPassword",
  "otp": "123456"
}
```





### 4. Create Order

- **URL**: `app/customer/order/create`
- **Method**: `POST`
- **Description**: Allows a customer to create order.
- **Authentication**: Requires JWT token in the request header for authentication.

#### Request Headers

| Key           | Type   | Description                    |
|---------------|--------|--------------------------------|
| `Authorization` | `JWT Token` | Bearer token for authentication |

#### Request Body

```json
{
  "productId": "12345",
  "quantity": 1,
  "paymentType": "CASH_ON_DELIVERY"
}

```
### Note

- **`paymentType`**: Accepted values are:
  - `"CASH_ON_DELIVERY"`
  - `"ONLINE"`


### 5. Confirm Order

- **URL**: `app/customer/product/buy`
- **Method**: `POST`
- **Description**: Allows a customer to Confirm and buy the Product.
- **Authentication**: Requires JWT token in the request header for authentication.

#### Request Headers

| Key           | Type   | Description                    |
|---------------|--------|--------------------------------|
| `Authorization` | `JWT Token` | Bearer token for authentication |

#### Request Body

```json
{
  "orderId" : 235
}

```

### 6. Product Review

- **URL**: `/app/customer/product/rating/{productId}`
- **Method**: `POST`
- **Description**: Allows customer to add review of product.
- **Authentication**: Requires JWT token in the request header for authentication.

#### Request Headers

| Key           | Type   | Description                    |
|---------------|--------|--------------------------------|
| `Authorization` | `JWT Token` | Bearer token for authentication |

#### Request Parameters

| Parameter | Type     | Required | Description                                                      |
|-----------|----------|----------|------------------------------------------------------------------|
| `productId`       | `long` | Yes      | The ID of the product to which the review will be added.          |


#### Request Body

```json
{
  "rating": 5,
  "description": "user review..."
}

```

### 7. Add to Cart 

- **URL**: `/app/customer/cart/{productId}`
- **Method**: `POST`
- **Description**: Allows customer to add products to cart.
- **Authentication**: Requires JWT token in the request header for authentication.

#### Request Headers

| Key           | Type   | Description                    |
|---------------|--------|--------------------------------|
| `Authorization` | `JWT Token` | Bearer token for authentication |

#### Request Parameters

| Parameter | Type     | Required | Description                                                      |
|-----------|----------|----------|------------------------------------------------------------------|
| `productId`       | `long` | Yes      | The ID of the product to be added to the cart.          |


### 8. Get all cart items

- **URL**: `/app/customer/cart`
- **Method**: `GET`
- **Description**: Retrieve cart items of authenticated customer.
- **Authentication**: Requires JWT token in the request header for authentication.

#### Request Headers

| Key           | Type   | Description                    |
|---------------|--------|--------------------------------|
| `Authorization` | `JWT Token` | Bearer token for authentication

### 9. Update cart item

- **URL**: `/app/customer/cart/{carItemId}`
- **Method**: `PUT`
- **Description**: Updates cart item .
- **Authentication**: Requires JWT token in the request header for authentication.

#### Request Headers

| Key           | Type   | Description                    |
|---------------|--------|--------------------------------|
| `Authorization` | `JWT Token` | Bearer token for authentication |

#### Request Parameters

| Parameter | Type     | Required | Description                                                      |
|-----------|----------|----------|------------------------------------------------------------------|
| `carItemId`       | `long` | Yes      | The ID of the cart item to update its quantity.          |

### 10. Get all order items

- **URL**: `/app/customer/orders`
- **Method**: `GET`
- **Description**: Retrieve order items of authenticated customer.
- **Authentication**: Requires JWT token in the request header for authentication.

#### Request Headers

| Key           | Type   | Description                    |
|---------------|--------|--------------------------------|
| `Authorization` | `JWT Token` | Bearer token for authentication 


&nbsp;

## Admin Endpoints
### 1. Admin Registration

- **URL**: `/app/admin/register`
- **Method**: `POST`
- **Description**: Create a admin account.

#### Request Body

```json
{
  "name": "admin_name",
  "email": "admin@gmail.com",
  "password": "admin1234",
  "city": "admin_city"
} 
```
### 2. Add Product Category

- **URL**: `/app/admin/category`
- **Method**: `POST`
- **Description**: End point for creating product category.

#### Request Headers

| Key           | Type   | Description                    |
|---------------|--------|--------------------------------|
| `Authorization` | `JWT Token` | Bearer token for authentication

#### Request Body

```json
{
  "image": "electronics.jpg",
  "category" : "Electronics"
} 
```
### 3. Add Product 

- **URL**: `/app/admin/product/{categoryId}`
- **Method**: `POST`
- **Description**: End point for adding product in their respective category.

#### Request Headers

| Key           | Type   | Description                    |
|---------------|--------|--------------------------------|
| `Authorization` | `JWT Token` | Bearer token for authentication

#### Request Parameters

| Parameter | Type     | Required | Description                                                      |
|-----------|----------|----------|------------------------------------------------------------------|
| `categoryId`       | `long` | Yes      | The ID of the category to which the product will be added.          |

#### Request Body

```json
{
  "productName" : "macbook ",
  "productDescription": "macbook air m1 2020",
  "price": 85000,
  "image": "macbook.jpg",
  "stocks": 60,
  
} 
```
### 5. Update Product 

- **URL**: `/app/admin/product/update`
- **Method**: `PUT`
- **Description**: End point for updating product details. Only include the fields to be updated.

#### Request Headers

| Key           | Type   | Description                    |
|---------------|--------|--------------------------------|
| `Authorization` | `JWT Token` | Bearer token for authentication

#### Request Body (Partial Update Supported)

```json
{
  "productName": "macbook",
  "productDescription": "macbook air m1 2020",
  "price": 85000,
  "image": "macbook.jpg",
  "stocks": 60
}
```
### Notes:

- **Include only the fields you want to update.**  
- For example:
  - To update the price:
    ```json
    { "price": 85000 }
    ```
  - To update the stock:
    ```json
    { "stocks": 60 }
    ```


### 6. Total users

- **URL**: `/app/admin/users`
- **Method**: `GET`
- **Description**: End point for Retrieveing total number of users.

#### Request Headers

| Key           | Type   | Description                    |
|---------------|--------|--------------------------------|
| `Authorization` | `JWT Token` | Bearer token for authentication


&nbsp;

## Public Endpoints
### 1. Search Products

- **URL**: `/app/search`
- **Method**: `GET`
- **Description**: Fetch products based on a search query string. The response is paginated and includes a list of products matching the search term.

## Request Parameters

| Parameter | Type     | Required | Description                                                      |
|-----------|----------|----------|------------------------------------------------------------------|
| `q`       | `String` | Yes      | Search query string for product names or descriptions.          |
| `page`    | `int`    | Yes      | Page number for pagination  |

### **Usage Example**

### Request
```
GET /app/search?q=earbuds&page=1
```

### Response
```json
{
  "data": [
    {
      "pId": 101,
      "productName": "Wireless Earbuds",
      "productDescription": "Bluetooth 5.0, Noise-Canceling",
      "price": 2999,
      "rating": 4.5,
      "image": "earbuds.jpg",
      "stocks": 50,
      "sold": 150,
      "isOutOfStock": false
    }
  ],
  "pageInfo": {
    "currentPage": 1,
    "totalPages": 1,
    "totalRecords": 1,
    "recordPerPage": 20
  }
}
```


### 2. Filter Products by Rating

- **URL**: `/app/search/rating`
- **Method**: `GET`
- **Description**: Fetch products filtered by a minimum rating value and search key. The response includes products matching the given rating criteria..

### **Request Parameters**

| Parameter | Type     | Required | Description                                     |
|-----------|----------|----------|-------------------------------------------------|
| `q`       | `String` | Yes      | Search query string for product names or descriptions. |
| `rating`  | `int`    | Yes      | Minimum rating to filter products, rating must be an integer between 1 and 5 (inclusive)." |
| `page`    | `int`    | Yes      | Page number for pagination    |

### **Usage Example**
### Request
```
GET /app/search/rating?rating=4&q=earbuds&page=1
```

### Response
```json
{
  "data": [
    {
      "pId": 101,
      "productName": "Wireless Earbuds",
      "productDescription": "Bluetooth 5.0, Noise-Canceling",
      "price": 2999,
      "rating": 4.5,
      "image": "earbuds.jpg",
      "stocks": 50,
      "sold": 150,
      "isOutOfStock": false
    }
  ],
  "pageInfo": {
    "currentPage": 1,
    "totalPages": 1,
    "totalRecords": 1,
    "recordPerPage": 20
  }
}
```

## 3. Filter Products by Price Range

- **URL**: `/app/search/price`
- **Method**: `GET`
- **Description**: Fetch products filtered within a specified price range. The response includes products whose prices fall between the given minimum and maximum values.

### **Request Parameters**

| Parameter | Type     | Required | Description                                      |
|-----------|----------|----------|--------------------------------------------------|
| `min`     | `int`    | Yes      | Minimum price for filtering products. Must be greater equal to 0    |
| `max`     | `int`    | Yes      | Maximum price for filtering products. Should be greater than max           |
| `q`       | `String` | Yes      | Search query string for product names or descriptions. |
| `page`    | `int`    | Yes      | Page number for pagination |

### **Usage Example**

#### Request
```
GET /app/search/price?min=1000&max=5000&q=electronics&page=1
```

#### Response
```json
{
  "data": [
    {
      "pId": 102,
      "productName": "Smartphone",
      "productDescription": "64GB Storage, Dual SIM",
      "price": 4500,
      "rating": 4.3,
      "image": "smartphone.jpg",
      "stocks": 30,
      "sold": 200,
      "isOutOfStock": false
    },
    {
      "pId": 103,
      "productName": "Headphones",
      "productDescription": "Wireless, Over-Ear",
      "price": 3000,
      "rating": 4.0,
      "image": "headphones.jpg",
      "stocks": 20,
      "sold": 120,
      "isOutOfStock": false
    }
  ],
  "pageInfo": {
    "currentPage": 1,
    "totalPages": 1,
    "totalRecords": 2,
    "recordPerPage": 20
  }
}
```

## 4. Sort Products by Price (Ascending)

- **URL**: `/app/search/asc`
- **Method**: `GET`
- **Description**: Fetch products sorted by price in ascending order. The response includes products sorted from the lowest to the highest price.

### **Request Parameters**

| Parameter | Type     | Required | Description                                      |
|-----------|----------|----------|--------------------------------------------------|
| `q`       | `String` | Yes      | Search query string for product names or descriptions. |
| `page`    | `int`    | Yes      | Page number for pagination  |

### **Usage Example**

#### Request
```
GET /app/search/asc?q=electronics&page=1
```

#### Response
```json
{
  "data": [
    {
      "pId": 103,
      "productName": "Headphones",
      "productDescription": "Wireless, Over-Ear",
      "price": 3000,
      "rating": 4.0,
      "image": "headphones.jpg",
      "stocks": 20,
      "sold": 120,
      "isOutOfStock": false
    },
    {
      "pId": 102,
      "productName": "Smartphone",
      "productDescription": "64GB Storage, Dual SIM",
      "price": 4500,
      "rating": 4.3,
      "image": "smartphone.jpg",
      "stocks": 30,
      "sold": 200,
      "isOutOfStock": false
    }
  ],
  "pageInfo": {
    "currentPage": 1,
    "totalPages": 1,
    "totalRecords": 2,
    "recordPerPage": 20
  }
}
```

## 5. Sort Products by Price (Descending)

- **URL**: `/app/search/desc`
- **Method**: `GET`
- **Description**: Fetch products sorted by price in descending order. The response includes products sorted from the highest to the lowest price.

### **Request Parameters**

| Parameter | Type     | Required | Description                                      |
|-----------|----------|----------|--------------------------------------------------|
| `q`       | `String` | Yes      | Search query string for product names or descriptions. |
| `page`    | `int`    | Yes      | Page number for pagination  |

### **Usage Example**

#### Request
```
GET /app/search/desc?q=electronics&page=1
```

#### Response
```json
{
  "data": [
    {
      "pId": 102,
      "productName": "Smartphone",
      "productDescription": "64GB Storage, Dual SIM",
      "price": 4500,
      "rating": 4.3,
      "image": "smartphone.jpg",
      "stocks": 30,
      "sold": 200,
      "isOutOfStock": false
    },
    {
      "pId": 103,
      "productName": "Headphones",
      "productDescription": "Wireless, Over-Ear",
      "price": 3000,
      "rating": 4.0,
      "image": "headphones.jpg",
      "stocks": 20,
      "sold": 120,
      "isOutOfStock": false
    }
  ],
  "pageInfo": {
    "currentPage": 1,
    "totalPages": 1,
    "totalRecords": 2,
    "recordPerPage": 20
  }
}
```

## 6. Get Product Reviews

- **URL**: `/app/search/reviews/{productId}`
- **Method**: `GET`
- **Description**: Fetch reviews for a specific product by its ID. The response includes customer reviews for the specified product.

### **Request Parameters**

| Parameter   | Type     | Required | Description                                      |
|-------------|----------|----------|--------------------------------------------------|
| `productId` | `Long`   | Yes      | The ID of the product for which reviews are requested. |
| `page`      | `int`    | Yes      | Page number for pagination  |

### **Usage Example**

#### Request
```
GET /app/search/reviews/101?page=1
```

#### Response
```json
{
  "data": [
    {
      "reviewId": 1,
      "ratingStatus": "Excellent",
      "description": "Amazing product!",
      "rating": 5,
      "customerName": "Pawan",
      "customerCity": "Balod"
    },
    
    {
      "reviewId": 2,
      "ratingStatus": "Good",
      "description": "The product broke after a week of use.",
      "rating": 2,
      "customerName": "Pawan",
      "customerCity": "Banglore"
    }
   
  ],
  "pageInfo": {
    "currentPage": 1,
    "totalPages": 1,
    "totalRecords": 2,
    "recordPerPage": 20
  }
}
```

## 7. Get Product by ID

- **URL**: `/app/search/product/{pId}`
- **Method**: `GET`
- **Description**: Fetch a product's details based on its unique ID.

### **Request Parameters**

| Parameter | Type     | Required | Description                                      |
|-----------|----------|----------|--------------------------------------------------|
| `pId`     | `Long`   | Yes      | The unique ID of the product to retrieve.        |

### **Usage Example**

#### Request
```
GET /app/search/product/101
```

#### Response
```json
{
  "pId": 101,
  "productName": "Wireless Earbuds",
  "productDescription": "Bluetooth 5.0, Noise-Canceling",
  "price": 2999,
  "rating": 4.5,
  "image": "earbuds.jpg",
  "stocks": 50,
  "sold": 150,
  "isOutOfStock": false
}
```
## 8. Forgot Password Request

- **URL**: `/app/forgot-password`
- **Method**: `POST`
- **Description**: Initiates a password reset process by sending an OTP to the user's registered email address. The user will need this OTP to reset their password.

### **Request Body**

| Field   | Type     | Required | Description                                      |
|---------|----------|----------|--------------------------------------------------|
| `email` | `String` | Yes      | The email address associated with the user's account. |

### **Usage Example**

#### Request
```
POST /app/forgot-password
```

**Request Body:**
```json
{
  "email": "user@example.com"
}
```

#### Response
```json
{
  "message": "OTP sent successfully to the provided email address."
}
```

## 9. Reset Password Request

- **URL**: `/app/reset-password`
- **Method**: `POST`
- **Description**: Resets the user's password using the provided OTP. The user must provide the email, the OTP received, and the new password.

### **Request Body**

| Field        | Type     | Required | Description                                                |
|--------------|----------|----------|------------------------------------------------------------|
| `email`      | `String` | Yes      | The email address associated with the user's account.      |
| `otp`        | `String` | Yes      | The one-time password (OTP) sent to the user's email.      |
| `newPassword`| `String` | Yes      | The new password the user wants to set for their account.  |

### **Usage Example**

#### Request
```
POST /app/reset-password
```

**Request Body:**
```json
{
  "email": "user@example.com",
  "otp": "123456",
  "newPassword": "newSecurePassword123"
}
```

#### Response
```json
{
  "message": "Your password has been successfully reset"
}
```




