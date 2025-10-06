import axios from 'axios';

// Base URL for the Spring Boot backend
const API_BASE_URL = 'http://localhost:8080/app';

// Create axios instance with default config
const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Request interceptor to add auth token
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Response interceptor to handle errors
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

// Public API endpoints
export const publicApi = {
  // Search products
  searchProducts: (query, page = 0) => 
    api.get(`/search?q=${query}&page=${page}`),
  
  // Search with rating filter
  searchProductsByRating: (query, rating, page = 0) => 
    api.get(`/search/rating?q=${query}&rating=${rating}&page=${page}`),
  
  // Search with price filter
  searchProductsByPrice: (query, minPrice, maxPrice, page = 0) => 
    api.get(`/search/price?q=${query}&min=${minPrice}&max=${maxPrice}&page=${page}`),
  
  // Search with price sort (ascending)
  searchProductsByPriceAsc: (query, page = 0) => 
    api.get(`/search/asc?q=${query}&page=${page}`),
  
  // Search with price sort (descending)
  searchProductsByPriceDesc: (query, page = 0) => 
    api.get(`/search/desc?q=${query}&page=${page}`),
  
  // Get product by ID
  getProductById: (productId) => 
    api.get(`/search/product/${productId}`),
  
  // Get product reviews
  getProductReviews: (productId, page = 0) => 
    api.get(`/search/reviews/${productId}?page=${page}`),
  
  // Get categories
  getCategories: () => 
    api.get('/category'),
  
  // Get products by category
  getProductsByCategory: (categoryId, page = 0) => 
    api.get(`/category/product/${categoryId}?page=${page}`),
  
  // Forgot password
  forgotPassword: (email) => 
    api.post('/forgot-password', { email }),
  
  // Reset password
  resetPassword: (data) => 
    api.post('/reset-password', data),
};

// Customer API endpoints
export const customerApi = {
  // Register
  register: (customerData) => 
    api.post('/customer/register', customerData),
  
  // Update profile
  updateProfile: (profileData) => 
    api.put('/customer/update', profileData),
  
  // Get profile
  getProfile: () => 
    api.get('/customer/profile'),
  
  // Change password
  changePassword: (passwordData) => 
    api.post('/customer/change-password', passwordData),
  
  // Verify OTP for password change
  verifyChangePasswordOTP: (otpData) => 
    api.post('/customer/change-password/verify-otp', otpData),
  
  // Create order
  createOrder: (orderData) => 
    api.post('/customer/order/create/', orderData),
  
  // Confirm order
  confirmOrder: (orderId) => 
    api.post('/customer/product/buy/', { orderId }),
  
  // Add review
  addReview: (productId, review) => 
    api.post(`/customer/product/rating/${productId}`, review),
  
  // Cart operations
  addToCart: (productId) => 
    api.post(`/customer/cart/${productId}`),
  
  getCartItems: () => 
    api.get('/customer/cart'),
  
  updateCartItemQuantity: (cartItemId, type) => 
    api.put(`/customer/cart/${cartItemId}?type=${type}`),
  
  removeCartItem: (cartItemId) => 
    api.delete(`/customer/cart/${cartItemId}`),
  
  // Get orders
  getOrders: () => 
    api.get('/customer/orders'),
};

// Admin API endpoints
export const adminApi = {
  // Register admin
  register: (adminData) => 
    api.post('/admin/register', adminData),
  
  // Add category
  addCategory: (categoryData) => 
    api.post('/admin/category', categoryData),
  
  // Add product
  addProduct: (categoryId, productData) => 
    api.post(`/admin/product/${categoryId}`, productData),
  
  // Update product
  updateProduct: (productData) => 
    api.put('/admin/product/update', productData),
  
  // Get total users
  getTotalUsers: () => 
    api.get('/admin/users'),
};

// Auth API endpoints
export const authApi = {
  // Login (this would be handled by Spring Security)
  login: (credentials) => 
    api.post('/login', credentials),
};

export default api;
