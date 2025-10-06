package com.app.service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.dtos.ChangePassWord;
import com.app.dtos.ChangePassWordOTP;
import com.app.dtos.CustomerUpdate;
import com.app.dtos.OrderId;
import com.app.dtos.PaymentInfo;
import com.app.dtos.ProductBuyData;
import com.app.exceptions.CartException;
import com.app.exceptions.CustomerException;
import com.app.exceptions.OrderException;
import com.app.exceptions.ProductException;
import com.app.model.Admin;
import com.app.model.Cart;
import com.app.model.Cart_Item;
import com.app.model.Customer;
import com.app.model.OrderStatus;
import com.app.model.Order_Item;
import com.app.model.Orders;
import com.app.model.Otp;
import com.app.model.PaymentStatus;
import com.app.model.PaymentType;
import com.app.model.Product;
import com.app.model.Review;
import com.app.repo.AdminRepo;
import com.app.repo.CartItemRepo;
import com.app.repo.CartRepo;
import com.app.repo.CustomerRepo;
import com.app.repo.OrderItemRepo;
import com.app.repo.OrdersRepo;
import com.app.repo.OtpRepo;
import com.app.repo.ProductRepo;
import com.app.utils.FieldValidationUtil;
import com.razorpay.*;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private AdminRepo adminRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ProductRepo productRepo;

	@Autowired
	private OrdersRepo ordersRepo;

	@Autowired
	private OrderItemRepo orderItemRepo;

	@Autowired
	private CartItemRepo cartItemRepo;

	@Autowired
	private CartRepo cartRepo;

	@Autowired
	private OtpRepo otpRepo;

	@Autowired
	private EmailService emailService;

	@Value("${pmt.key_id}")
	private String key_id;
	@Value("${pmt.key_secret}")
	private String key_secret;

	@Override
	public Customer customerProfile() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();

		return customerRepo.findByEmail(userName).get();

	}

	@Override
	public String registerCustomer(Customer customer) {

		Optional<Admin> optAdmin = adminRepo.findByEmail(customer.getEmail());
		if (optAdmin.isPresent()) {
			throw new CustomerException("duplicate email !");
		}

		Optional<Customer> optCustomer = customerRepo.findByEmail(customer.getEmail());
		if (optCustomer.isPresent()) {
			throw new CustomerException("duplicate email !");
		}
		
		// send registration confirmation message to customer
		String to = customer.getEmail();
		String from = "kumarpawanm8085@gmail.com";

		String subject = "Registration Successful";
		String text = "You have registered successfully";
		
		boolean sent = emailService.send(to, from, subject, text);

		if (!sent) {

			throw new CustomerException("Invalid email address.");
		}

		customer.setRole("CUSTOMER");
		customer.setPassword(passwordEncoder.encode(customer.getPassword()));

		// create new Cart
		Cart cart = new Cart();
		customer.setCart(cart);
		cart.setCustomer(customer);

		// create new Orders
		Orders orders = new Orders();
		customer.setOrders(orders);
		orders.setCustomer(customer);

		customerRepo.save(customer);

		return "You have Registerd Successfully";
	}

	@Override
	public String updateCustomerProfile(CustomerUpdate customerData) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();

		// ! no need to use optional this method will only get call when customer is
		// authenticated
		Customer customer = customerRepo.findByEmail(userName).get();

		boolean update = false;

		if (customerData.getName() != null) {
			
			System.out.println(customerData.getName());
			// validate customer name
			FieldValidationUtil.validateMinSize(customerData.getName(), 3, "Name");
			customer.setName(customerData.getName());
			update = true;

		}

		if (customerData.getCity() != null) {

			// validate customer city
			FieldValidationUtil.validateMinSize(customerData.getName(), 3, "City");
			customer.setCity(customerData.getCity());
			update = true;
		}

		if (update) {
			customerRepo.save(customer);
			return "Your profile has been updated";
		}
		return "Nothing updated!";
	}

	@Override
	public String changePasswordRequest(ChangePassWord changePassWord) throws CustomerException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();

		if (passwordEncoder.matches(changePassWord.getOldPassword(),
				customerRepo.findByEmail(username).get().getPassword())) {

			// note ! gotta make below email dynamic
			String to = "pawankumarsahu1022@gmail.com";
			String from = "kumarpawanm8085@gmail.com";

			String subject = "Password Change OTP..";

			String otp = getOtp();

			while (otpRepo.findByOtpAndUser(otp, username).isPresent()) {
				otp = getOtp();
			}
			String text = "YOUR OTP : " + otp;
			boolean sent = emailService.send(to, from, subject, text);

			if (sent) {
				Otp o = new Otp();
				o.setOtp(otp);
				o.setUser(username);
				o.setExpiryTime(LocalDateTime.now().plusMinutes(5));
				otpRepo.save(o);
				return "We have sent an otp in your email address";
			}
			throw new CustomerException("Something went wrong! try again");
		}

		throw new CustomerException("Wrong old password");
	}

	public boolean isExpired(LocalDateTime expiryTime) {
		return LocalDateTime.now().isAfter(expiryTime); // Check if current time is after expiry time
	}

	public String getOtp() {
		Random random = new Random();
		StringBuilder otp = new StringBuilder();
		otp.append(random.nextInt(10) + "");
		otp.append(random.nextInt(10) + "");
		otp.append(random.nextInt(10) + "");
		otp.append(random.nextInt(10) + "");
		otp.append(random.nextInt(10) + "");
		otp.append(random.nextInt(10) + "");
		return otp.toString();
	}

	@Override
	public String verifyChangePasswordWithOtp(ChangePassWordOTP changePassWord) throws CustomerException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();

		if (passwordEncoder.matches(changePassWord.getOldPassword(),
				customerRepo.findByEmail(username).get().getPassword())) {

			Optional<Otp> otp = otpRepo.findByOtpAndUser(changePassWord.getOtp(), username);
			if (otp.isPresent()) {

				Otp myOtp = otp.get();

				// check otp expiration
				if (isExpired(myOtp.getExpiryTime())) {

					// remove otp from db if it is expired
					otpRepo.delete(myOtp);
					throw new CustomerException("This Otp has expired already!");
				}

				customerRepo.findByEmail(username).get()
						.setPassword(passwordEncoder.encode(changePassWord.getNewPassword()));

				// remove otp from db after used
				otpRepo.delete(myOtp);

				return "Password changed successfully!";
			}
			throw new CustomerException("Invalid Otp!");

		}

		throw new CustomerException("Wrong old password");
	}

	@Override
	public Order_Item createOrder(ProductBuyData productBuyData) throws ProductException, RazorpayException {

		Optional<Product> optProduct = productRepo.findById(productBuyData.getProductId());
		if (optProduct.isEmpty()) {
			throw new ProductException("Invalid Product Id!");
		}

		Product product = optProduct.get();

		// check if product out of stock
		if (product.getIsOutOfStock()) {
			throw new ProductException("Product out of stock !");
		}

		// check if quantity is more than available stock
		if (productBuyData.getQuantity() > product.getStocks()) {
			throw new ProductException("Only " + product.getStocks() + " Products are left");
		}

		// get current customer from authentication obj
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Customer customer = customerRepo.findByEmail(authentication.getName()).get();

		// create order
		Order_Item orderItem = new Order_Item();
		orderItem.setProductId(productBuyData.getProductId());
		orderItem.setUser(customer.getEmail());
		orderItem.setQuantity(productBuyData.getQuantity());
		orderItem.setImage(product.getImage());
		orderItem.setPrice(product.getPrice());
		orderItem.setProduct(product.getProductName());

		orderItem.setPaymentStatus(PaymentStatus.PENDING);
		orderItem.setPaymentType(productBuyData.getPaymentType());

		if (productBuyData.getPaymentType() == PaymentType.ONLINE) {

			Integer amount = product.getPrice() * productBuyData.getQuantity();
			RazorpayClient razorpayClient = new RazorpayClient(key_id, key_secret);

			JSONObject options = new JSONObject();
			options.put("amount", amount * 100); // 100 paisa
			options.put("currency", "INR");
			options.put("receipt", "txn_123456");
			Order order = razorpayClient.Orders.create(options);
			System.out.println(order.toJson());

			PaymentInfo paymentInfo = new PaymentInfo();
			paymentInfo.setAmount((Integer) order.toJson().get("amount"));
			paymentInfo.setCurrency((String) order.toJson().get("currency"));
			paymentInfo.setId((String) order.toJson().get("id"));
			paymentInfo.setStatus((String) order.toJson().get("status"));

			orderItem.setPaymentInfo(paymentInfo);

		}

		orderItemRepo.save(orderItem);
		customer.getOrders().getOrderItems().add(orderItem); // add order to current customer
		ordersRepo.save(customer.getOrders());
		System.out.println(orderItem);

		return orderItem;
	}

	@Override
	public String confirmOrder(OrderId orderId) {

		Optional<Order_Item> optOrdeItem = orderItemRepo.findById(orderId.getOrderId());
		if (optOrdeItem.isEmpty())
			throw new OrderException("Invalid order id !");

		Order_Item orderItem = optOrdeItem.get();

		// get current customer from authentication obj
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Customer customer = customerRepo.findByEmail(authentication.getName()).get();

		// check if order belongs to customer or not
		if (!orderItem.getUser().equals(customer.getEmail())) {
			throw new CustomerException("This order does not belong to you");
		}

		Optional<Product> optProduct = productRepo.findById(orderItem.getProductId());
		if (optProduct.isEmpty()) {
			throw new ProductException("Invalid Product Id " + orderItem.getProductId());
		}

		if (orderItem.getOrderStatus() != OrderStatus.PENDING) {
			throw new OrderException("This product was already ordered!");
		}

		Product product = optProduct.get();

		// check if product out of stock
		if (product.getIsOutOfStock()) {
			throw new ProductException("Product out of stock !");
		}

		// check if quantity is more than available stock
		if (orderItem.getQuantity() > product.getStocks()) {
			throw new ProductException("Only " + product.getStocks() + " Products are left");
		}

		// reduce product stock with quantity and increase sold count;
		product.setStocks(product.getStocks() - orderItem.getQuantity());
		product.setSold(product.getSold() + orderItem.getQuantity());

		// if product stock becomes 0 save outOfStock true
		if (product.getStocks() == 0) {
			product.setIsOutOfStock(true);
		}

		if (orderItem.getPaymentType() == PaymentType.ONLINE)
			orderItem.setPaymentStatus(PaymentStatus.PAID);

		ordersRepo.save(customer.getOrders());
		productRepo.save(product);

		return "Your Product will be delivered within a week";
	}

	@Override
	public String addReview(Long productId, Review review) {

		Optional<Product> optProduct = productRepo.findById(productId);

		if (optProduct.isEmpty()) {

			throw new ProductException("Invalid Product id : " + productId);
		}

		Product product = optProduct.get();

		// get current customer from authentication obj
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		Customer customer = customerRepo.findByEmail(authentication.getName()).get();

		// check if product belongs to customer order or not
		List<Order_Item> orderItems = orderItemRepo.findOrderItemByUserAndProductId(customer.getEmail(), productId);
		if (orderItems.size() == 0)
			throw new ProductException("You haven't bought this Product !");

		// set rating status, customer name, city to review object
		review.setRatingStatus(getRatingStatus(review.getRating()));
		review.setCustomerName(customer.getName());
		review.setCustomerCity(customer.getCity());

		product.getReviews().add(review); // add review to product

		// update rating information of product

		product.setRatingCount(product.getRatingCount() + 1);
		product.setRatingSum(product.getRatingSum() + review.getRating());
		product.setRating(Double.parseDouble(formatRating(product.getRatingCount(), product.getRatingSum()))); // update
																												// product
																												// rating

		productRepo.save(product);

		return "Thanks for your review";
	}

	String getRatingStatus(int rating) {

		if (rating == 1) {
			return "Very Bad";
		} else if (rating == 2) {
			return "Bad";
		} else if (rating == 3) {
			return "Good";
		} else if (rating == 4) {
			return "Very Good";
		} else {
			return "Excellent";
		}
	}

	public String formatRating(long ratingCount, long ratingSum) {
		if (ratingCount == 0) {
			return "0.0"; // No ratings, return 0.0
		}

		double averageRating = (double) ratingSum / ratingCount;
		DecimalFormat df = new DecimalFormat("0.0"); // Round to one decimal place
		return df.format(averageRating);
	}

	@Override
	public String addToCart(Long productId) {

		Optional<Product> optProduct = productRepo.findById(productId);
		if (optProduct.isEmpty())
			throw new ProductException("Invalid Product Id : " + productId);

		// get current customer from authentication obj
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		Customer customer = customerRepo.findByEmail(authentication.getName()).get();

		// check if product is already there in cart or not
		Optional<Cart_Item> optCartItem = cartItemRepo.findByProductIdAndUser(productId, customer.getEmail());
		if (optCartItem.isPresent()) {
			throw new CartException("Product is already there in cart ");
		}

		// check cart size limit is 20
		if (customer.getCart().getCartItems().size() == 20)
			throw new CartException("Cart is full !");

		// create new Cart
		Cart_Item cartItem = new Cart_Item();
		cartItem.setProductId(productId);
		cartItem.setUser(customer.getEmail());
		cartItem.setQuantity(1);
		cartItem.setImage(optProduct.get().getImage());
		cartItem.setPrice(optProduct.get().getPrice());
		cartItem.setProduct(optProduct.get().getProductName());

		customer.getCart().getCartItems().add(cartItem);
		cartRepo.save(customer.getCart());

		return "Product added to cart";

	}

	@Override
	public List<Cart_Item> getAllCartItem() {

		// get principal object from security context
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		return customerRepo.findByEmail(userName).get().getCart().getCartItems();

	}

	@Override
	public int updateCartItemQuantity(Long cartItemId, int quantityUpdateType) {

		// *quantityUpdateType 0 means decrease quantity by 1 and 1 means increase
		// quantity by one

		// check for valid cartItem Id
		Optional<Cart_Item> optCartItem = cartItemRepo.findById(cartItemId);
		if (optCartItem.isEmpty()) {
			throw new CartException("Invalid cartItem Id : " + cartItemId);
		}

		Cart_Item cartItem = optCartItem.get();

		// get current customer from authentication obj
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Customer customer = customerRepo.findByEmail(authentication.getName()).get();

		// check if cart Item belongs to current customer or not
		if (!cartItem.getUser().equals(customer.getEmail())) {
			throw new CartException("Invalid cart item id");
		}

		// return current quantity if quantityUpdateType is wrong
		if (quantityUpdateType < 0 || quantityUpdateType > 1)
			return cartItem.getQuantity();

		if (quantityUpdateType == 0) {

			// check if quantity becomes 0 while decreasing
			cartItem.setQuantity(cartItem.getQuantity() - 1 > 0 ? cartItem.getQuantity() - 1 : cartItem.getQuantity());

		} else if (quantityUpdateType == 1) {

			cartItem.setQuantity(cartItem.getQuantity() + 1);
		}

		cartItemRepo.save(cartItem);
		return cartItem.getQuantity();

	}

	@Override
	public String removeCartItem(Long cartItemId) {
		Optional<Cart_Item> optCartItem = cartItemRepo.findById(cartItemId);
		if (optCartItem.isEmpty()) {
			throw new CartException("Wrong cart Item id!");

		}

		// get current customer from authentication obj
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Customer customer = customerRepo.findByEmail(authentication.getName()).get();

		// check if cart Item belongs to current customer or not
		Cart_Item cartItem = optCartItem.get();
		if (!cartItem.getUser().equals(customer.getEmail())) {
			throw new CartException("Wrong cart Item id!");
		}

		customer.getCart().getCartItems().remove(cartItem);

		cartRepo.save(customer.getCart());

		return "Item sucessfully removed from your cart";
	}

	@Override
	public List<Order_Item> getAllOrderItems(int page) {

		// get principal object from security context
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();

		return customerRepo.findByEmail(userName).get().getOrders().getOrderItems();
	}

}
