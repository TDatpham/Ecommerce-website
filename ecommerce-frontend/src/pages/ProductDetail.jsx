import React, { useState, useEffect } from 'react';
import {
  Container,
  Grid,
  Typography,
  Box,
  Button,
  Card,
  CardMedia,
  Rating,
  Divider,
  Chip,
  Alert,
  CircularProgress,
  Tabs,
  Tab,
  Paper,
  IconButton,
  InputAdornment,
  TextField,
} from '@mui/material';
import {
  ShoppingCart as ShoppingCartIcon,
  Favorite as FavoriteIcon,
  FavoriteBorder as FavoriteBorderIcon,
  Share as ShareIcon,
  Add as AddIcon,
  Remove as RemoveIcon,
} from '@mui/icons-material';
import { useParams, useNavigate } from 'react-router-dom';
import { publicApi } from '../services/api';
import { useAuth } from '../contexts/AuthContext';
import { useCart } from '../contexts/CartContext';

const ProductDetail = () => {
  const { productId } = useParams();
  const navigate = useNavigate();
  const { isAuthenticated } = useAuth();
  const { addToCart } = useCart();

  const [product, setProduct] = useState(null);
  const [reviews, setReviews] = useState([]);
  const [loading, setLoading] = useState(true);
  const [reviewsLoading, setReviewsLoading] = useState(false);
  const [error, setError] = useState('');
  const [quantity, setQuantity] = useState(1);
  const [isFavorite, setIsFavorite] = useState(false);
  const [isAddingToCart, setIsAddingToCart] = useState(false);
  const [tabValue, setTabValue] = useState(0);

  useEffect(() => {
    const fetchProduct = async () => {
      try {
        setLoading(true);
        const response = await publicApi.getProductById(productId);
        setProduct(response.data);
      } catch (err) {
        setError('Failed to load product details');
        console.error('Error fetching product:', err);
      } finally {
        setLoading(false);
      }
    };

    if (productId) {
      fetchProduct();
    }
  }, [productId]);

  useEffect(() => {
    const fetchReviews = async () => {
      try {
        setReviewsLoading(true);
        const response = await publicApi.getProductReviews(productId, 0);
        setReviews(response.data.data || []);
      } catch (err) {
        console.error('Error fetching reviews:', err);
      } finally {
        setReviewsLoading(false);
      }
    };

    if (productId) {
      fetchReviews();
    }
  }, [productId]);

  const formatPrice = (price) => {
    return new Intl.NumberFormat('vi-VN', {
      style: 'currency',
      currency: 'VND',
    }).format(price);
  };

  const handleQuantityChange = (type) => {
    if (type === 'increase') {
      setQuantity(prev => Math.min(prev + 1, product.stocks));
    } else {
      setQuantity(prev => Math.max(prev - 1, 1));
    }
  };

  const handleAddToCart = async () => {
    if (!isAuthenticated) {
      navigate('/login');
      return;
    }

    setIsAddingToCart(true);
    try {
      const result = await addToCart(product.pId);
      if (result.success) {
        // Could show success message
        console.log('Added to cart successfully');
      } else {
        console.error('Failed to add to cart:', result.error);
      }
    } catch (error) {
      console.error('Error adding to cart:', error);
    } finally {
      setIsAddingToCart(false);
    }
  };

  const handleToggleFavorite = () => {
    setIsFavorite(!isFavorite);
    // TODO: Implement favorite functionality
  };

  const handleShare = () => {
    if (navigator.share) {
      navigator.share({
        title: product.productName,
        text: product.productDescription,
        url: window.location.href,
      });
    } else {
      navigator.clipboard.writeText(window.location.href);
      // Could show a toast notification
    }
  };

  const handleTabChange = (event, newValue) => {
    setTabValue(newValue);
  };

  if (loading) {
    return (
      <Container maxWidth="lg" sx={{ py: 4 }}>
        <Box display="flex" justifyContent="center" alignItems="center" minHeight="400px">
          <CircularProgress />
        </Box>
      </Container>
    );
  }

  if (error || !product) {
    return (
      <Container maxWidth="lg" sx={{ py: 4 }}>
        <Alert severity="error" sx={{ mb: 2 }}>
          {error || 'Product not found'}
        </Alert>
        <Button variant="contained" onClick={() => navigate('/')}>
          Back to Home
        </Button>
      </Container>
    );
  }

  return (
    <Container maxWidth="lg" sx={{ py: 4 }}>
      <Grid container spacing={4}>
        {/* Product Images */}
        <Grid item xs={12} md={6}>
          <Card>
            <CardMedia
              component="img"
              height="400"
              image={product.image || '/placeholder-product.jpg'}
              alt={product.productName}
              sx={{ objectFit: 'cover' }}
            />
          </Card>
        </Grid>

        {/* Product Info */}
        <Grid item xs={12} md={6}>
          <Box sx={{ mb: 2 }}>
            <Typography variant="h4" gutterBottom sx={{ fontWeight: 'bold' }}>
              {product.productName}
            </Typography>
            
            <Box sx={{ display: 'flex', alignItems: 'center', gap: 2, mb: 2 }}>
              <Rating
                value={product.rating || 0}
                precision={0.1}
                readOnly
                size="large"
              />
              <Typography variant="body1" color="text.secondary">
                ({product.ratingCount || 0} reviews)
              </Typography>
            </Box>

            <Typography variant="h4" color="primary" sx={{ fontWeight: 'bold', mb: 2 }}>
              {formatPrice(product.price)}
            </Typography>

            <Box sx={{ display: 'flex', gap: 1, mb: 3 }}>
              <Chip
                label={product.isOutOfStock || product.stocks === 0 ? 'Out of Stock' : 'In Stock'}
                color={product.isOutOfStock || product.stocks === 0 ? 'error' : 'success'}
                variant="outlined"
              />
              {product.stocks > 0 && (
                <Chip
                  label={`${product.stocks} available`}
                  color="info"
                  variant="outlined"
                />
              )}
            </Box>

            <Typography variant="body1" sx={{ mb: 3 }}>
              {product.productDescription}
            </Typography>

            {/* Quantity Selector */}
            <Box sx={{ display: 'flex', alignItems: 'center', gap: 2, mb: 3 }}>
              <Typography variant="h6">Quantity:</Typography>
              <Box sx={{ display: 'flex', alignItems: 'center', border: 1, borderColor: 'grey.300', borderRadius: 1 }}>
                <IconButton
                  onClick={() => handleQuantityChange('decrease')}
                  disabled={quantity <= 1}
                >
                  <RemoveIcon />
                </IconButton>
                <Typography sx={{ px: 2, minWidth: '40px', textAlign: 'center' }}>
                  {quantity}
                </Typography>
                <IconButton
                  onClick={() => handleQuantityChange('increase')}
                  disabled={quantity >= product.stocks}
                >
                  <AddIcon />
                </IconButton>
              </Box>
            </Box>

            {/* Action Buttons */}
            <Box sx={{ display: 'flex', gap: 2, mb: 3 }}>
              <Button
                variant="contained"
                size="large"
                startIcon={
                  isAddingToCart ? (
                    <CircularProgress size={20} color="inherit" />
                  ) : (
                    <ShoppingCartIcon />
                  )
                }
                disabled={isAddingToCart || product.isOutOfStock || product.stocks === 0}
                onClick={handleAddToCart}
                sx={{ flexGrow: 1, py: 1.5 }}
              >
                {isAddingToCart
                  ? 'Adding...'
                  : product.isOutOfStock || product.stocks === 0
                  ? 'Out of Stock'
                  : 'Add to Cart'}
              </Button>
              
              <IconButton
                color={isFavorite ? 'error' : 'default'}
                onClick={handleToggleFavorite}
                sx={{ border: 1, borderColor: 'grey.300' }}
              >
                {isFavorite ? <FavoriteIcon /> : <FavoriteBorderIcon />}
              </IconButton>
              
              <IconButton
                onClick={handleShare}
                sx={{ border: 1, borderColor: 'grey.300' }}
              >
                <ShareIcon />
              </IconButton>
            </Box>
          </Box>
        </Grid>

        {/* Product Details Tabs */}
        <Grid item xs={12}>
          <Paper sx={{ width: '100%' }}>
            <Tabs value={tabValue} onChange={handleTabChange} aria-label="product details tabs">
              <Tab label="Description" />
              <Tab label="Reviews" />
              <Tab label="Shipping" />
            </Tabs>
            
            <Box sx={{ p: 3 }}>
              {tabValue === 0 && (
                <Typography variant="body1">
                  {product.productDescription}
                </Typography>
              )}
              
              {tabValue === 1 && (
                <Box>
                  <Typography variant="h6" gutterBottom>
                    Customer Reviews
                  </Typography>
                  {reviewsLoading ? (
                    <Box display="flex" justifyContent="center" py={4}>
                      <CircularProgress />
                    </Box>
                  ) : reviews.length > 0 ? (
                    <Box>
                      {reviews.map((review, index) => (
                        <Box key={index} sx={{ mb: 3, pb: 2, borderBottom: 1, borderColor: 'grey.200' }}>
                          <Box sx={{ display: 'flex', alignItems: 'center', gap: 2, mb: 1 }}>
                            <Typography variant="subtitle1" sx={{ fontWeight: 'bold' }}>
                              {review.customer?.name || 'Anonymous'}
                            </Typography>
                            <Rating value={review.rating} readOnly size="small" />
                          </Box>
                          <Typography variant="body2" color="text.secondary" sx={{ mb: 1 }}>
                            {review.comment}
                          </Typography>
                        </Box>
                      ))}
                    </Box>
                  ) : (
                    <Typography variant="body2" color="text.secondary">
                      No reviews yet. Be the first to review this product!
                    </Typography>
                  )}
                </Box>
              )}
              
              {tabValue === 2 && (
                <Box>
                  <Typography variant="h6" gutterBottom>
                    Shipping Information
                  </Typography>
                  <Typography variant="body1" sx={{ mb: 2 }}>
                    We offer fast and reliable shipping to all locations.
                  </Typography>
                  <Typography variant="body2" color="text.secondary">
                    • Free shipping on orders over $50<br />
                    • Standard delivery: 3-5 business days<br />
                    • Express delivery: 1-2 business days<br />
                    • International shipping available
                  </Typography>
                </Box>
              )}
            </Box>
          </Paper>
        </Grid>
      </Grid>
    </Container>
  );
};

export default ProductDetail;

