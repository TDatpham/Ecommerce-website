import React, { useState } from 'react';
import {
  Container,
  Typography,
  Box,
  Paper,
  Button,
  Grid,
  Card,
  CardMedia,
  CardContent,
  CardActions,
  IconButton,
  Divider,
  Alert,
  CircularProgress,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
} from '@mui/material';
import {
  Add as AddIcon,
  Remove as RemoveIcon,
  Delete as DeleteIcon,
  ShoppingCart as ShoppingCartIcon,
  ShoppingBag as ShoppingBagIcon,
} from '@mui/icons-material';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import { useCart } from '../contexts/CartContext';

const Cart = () => {
  const navigate = useNavigate();
  const { isAuthenticated } = useAuth();
  const {
    cartItems,
    loading,
    updateQuantity,
    removeFromCart,
    getTotalPrice,
    getTotalItems,
    isEmpty,
  } = useCart();

  const [deleteDialogOpen, setDeleteDialogOpen] = useState(false);
  const [itemToDelete, setItemToDelete] = useState(null);
  const [updatingItem, setUpdatingItem] = useState(null);

  const formatPrice = (price) => {
    return new Intl.NumberFormat('vi-VN', {
      style: 'currency',
      currency: 'VND',
    }).format(price);
  };

  const handleQuantityChange = async (cartItemId, type) => {
    setUpdatingItem(cartItemId);
    try {
      const result = await updateQuantity(cartItemId, type);
      if (!result.success) {
        // Handle error - could show a snackbar or alert
        console.error('Failed to update quantity:', result.error);
      }
    } catch (error) {
      console.error('Error updating quantity:', error);
    } finally {
      setUpdatingItem(null);
    }
  };

  const handleRemoveItem = (cartItemId) => {
    setItemToDelete(cartItemId);
    setDeleteDialogOpen(true);
  };

  const confirmRemoveItem = async () => {
    if (itemToDelete) {
      try {
        const result = await removeFromCart(itemToDelete);
        if (!result.success) {
          console.error('Failed to remove item:', result.error);
        }
      } catch (error) {
        console.error('Error removing item:', error);
      }
    }
    setDeleteDialogOpen(false);
    setItemToDelete(null);
  };

  const handleCheckout = () => {
    if (!isAuthenticated) {
      navigate('/login');
      return;
    }
    navigate('/checkout');
  };

  const handleContinueShopping = () => {
    navigate('/');
  };

  if (!isAuthenticated) {
    return (
      <Container maxWidth="lg" sx={{ py: 4 }}>
        <Alert severity="info" sx={{ mb: 3 }}>
          Please login to view your cart.
        </Alert>
        <Button
          variant="contained"
          onClick={() => navigate('/login')}
          startIcon={<ShoppingCartIcon />}
        >
          Login to Continue
        </Button>
      </Container>
    );
  }

  if (loading && cartItems.length === 0) {
    return (
      <Container maxWidth="lg" sx={{ py: 4 }}>
        <Box display="flex" justifyContent="center" alignItems="center" minHeight="400px">
          <CircularProgress />
        </Box>
      </Container>
    );
  }

  if (isEmpty) {
    return (
      <Container maxWidth="lg" sx={{ py: 4 }}>
        <Paper
          elevation={3}
          sx={{
            p: 6,
            textAlign: 'center',
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
          }}
        >
          <ShoppingCartIcon sx={{ fontSize: 80, color: 'grey.400', mb: 2 }} />
          <Typography variant="h4" gutterBottom sx={{ fontWeight: 'bold' }}>
            Your Cart is Empty
          </Typography>
          <Typography variant="body1" color="text.secondary" sx={{ mb: 3 }}>
            Looks like you haven't added any items to your cart yet.
          </Typography>
          <Button
            variant="contained"
            size="large"
            onClick={handleContinueShopping}
            startIcon={<ShoppingBagIcon />}
            sx={{ px: 4, py: 1.5 }}
          >
            Start Shopping
          </Button>
        </Paper>
      </Container>
    );
  }

  return (
    <Container maxWidth="lg" sx={{ py: 4 }}>
      <Typography variant="h4" gutterBottom sx={{ fontWeight: 'bold', mb: 3 }}>
        Shopping Cart ({getTotalItems()} items)
      </Typography>

      <Grid container spacing={3}>
        {/* Cart Items */}
        <Grid item xs={12} md={8}>
          {cartItems.map((item) => (
            <Card key={item.cartItemId} sx={{ mb: 2 }}>
              <Grid container spacing={2} sx={{ p: 2 }}>
                <Grid item xs={12} sm={3}>
                  <CardMedia
                    component="img"
                    height="120"
                    image={item.product?.image || '/placeholder-product.jpg'}
                    alt={item.product?.productName}
                    sx={{ objectFit: 'cover', borderRadius: 1 }}
                  />
                </Grid>
                
                <Grid item xs={12} sm={6}>
                  <CardContent sx={{ p: 1 }}>
                    <Typography variant="h6" gutterBottom sx={{ fontWeight: 'bold' }}>
                      {item.product?.productName}
                    </Typography>
                    <Typography variant="body2" color="text.secondary" sx={{ mb: 1 }}>
                      {item.product?.productDescription}
                    </Typography>
                    <Typography variant="h6" color="primary" sx={{ fontWeight: 'bold' }}>
                      {formatPrice(item.product?.price || 0)}
                    </Typography>
                  </CardContent>
                </Grid>
                
                <Grid item xs={12} sm={3}>
                  <CardActions sx={{ flexDirection: 'column', alignItems: 'center', p: 1 }}>
                    {/* Quantity Controls */}
                    <Box sx={{ display: 'flex', alignItems: 'center', mb: 2 }}>
                      <IconButton
                        size="small"
                        onClick={() => handleQuantityChange(item.cartItemId, -1)}
                        disabled={updatingItem === item.cartItemId || item.quantity <= 1}
                      >
                        <RemoveIcon />
                      </IconButton>
                      
                      <Typography
                        variant="h6"
                        sx={{
                          mx: 2,
                          minWidth: '30px',
                          textAlign: 'center',
                          fontWeight: 'bold',
                        }}
                      >
                        {item.quantity}
                      </Typography>
                      
                      <IconButton
                        size="small"
                        onClick={() => handleQuantityChange(item.cartItemId, 1)}
                        disabled={updatingItem === item.cartItemId}
                      >
                        <AddIcon />
                      </IconButton>
                    </Box>

                    {/* Remove Button */}
                    <Button
                      variant="outlined"
                      color="error"
                      size="small"
                      startIcon={<DeleteIcon />}
                      onClick={() => handleRemoveItem(item.cartItemId)}
                      sx={{ width: '100%' }}
                    >
                      Remove
                    </Button>
                  </CardActions>
                </Grid>
              </Grid>
            </Card>
          ))}
        </Grid>

        {/* Order Summary */}
        <Grid item xs={12} md={4}>
          <Paper elevation={3} sx={{ p: 3, position: 'sticky', top: 20 }}>
            <Typography variant="h5" gutterBottom sx={{ fontWeight: 'bold' }}>
              Order Summary
            </Typography>
            
            <Divider sx={{ my: 2 }} />
            
            <Box sx={{ display: 'flex', justifyContent: 'space-between', mb: 1 }}>
              <Typography variant="body1">Subtotal:</Typography>
              <Typography variant="body1" sx={{ fontWeight: 'bold' }}>
                {formatPrice(getTotalPrice())}
              </Typography>
            </Box>
            
            <Box sx={{ display: 'flex', justifyContent: 'space-between', mb: 1 }}>
              <Typography variant="body1">Shipping:</Typography>
              <Typography variant="body1" sx={{ fontWeight: 'bold' }}>
                Free
              </Typography>
            </Box>
            
            <Box sx={{ display: 'flex', justifyContent: 'space-between', mb: 1 }}>
              <Typography variant="body1">Tax:</Typography>
              <Typography variant="body1" sx={{ fontWeight: 'bold' }}>
                {formatPrice(getTotalPrice() * 0.1)}
              </Typography>
            </Box>
            
            <Divider sx={{ my: 2 }} />
            
            <Box sx={{ display: 'flex', justifyContent: 'space-between', mb: 3 }}>
              <Typography variant="h6" sx={{ fontWeight: 'bold' }}>
                Total:
              </Typography>
              <Typography variant="h6" color="primary" sx={{ fontWeight: 'bold' }}>
                {formatPrice(getTotalPrice() * 1.1)}
              </Typography>
            </Box>
            
            <Button
              variant="contained"
              fullWidth
              size="large"
              onClick={handleCheckout}
              sx={{
                py: 1.5,
                fontSize: '1.1rem',
                fontWeight: 'bold',
                mb: 2,
              }}
            >
              Proceed to Checkout
            </Button>
            
            <Button
              variant="outlined"
              fullWidth
              onClick={handleContinueShopping}
              sx={{ py: 1.5 }}
            >
              Continue Shopping
            </Button>
          </Paper>
        </Grid>
      </Grid>

      {/* Delete Confirmation Dialog */}
      <Dialog
        open={deleteDialogOpen}
        onClose={() => setDeleteDialogOpen(false)}
        aria-labelledby="delete-dialog-title"
      >
        <DialogTitle id="delete-dialog-title">
          Remove Item from Cart
        </DialogTitle>
        <DialogContent>
          <Typography>
            Are you sure you want to remove this item from your cart?
          </Typography>
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setDeleteDialogOpen(false)}>
            Cancel
          </Button>
          <Button onClick={confirmRemoveItem} color="error" variant="contained">
            Remove
          </Button>
        </DialogActions>
      </Dialog>
    </Container>
  );
};

export default Cart;

