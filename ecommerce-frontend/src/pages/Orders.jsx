import React, { useState, useEffect } from 'react';
import {
  Container,
  Typography,
  Box,
  Paper,
  Grid,
  Card,
  CardContent,
  CardActions,
  Button,
  Chip,
  Divider,
  Alert,
  CircularProgress,
  Accordion,
  AccordionSummary,
  AccordionDetails,
  List,
  ListItem,
  ListItemText,
  ListItemAvatar,
  Avatar,
} from '@mui/material';
import {
  ExpandMore as ExpandMoreIcon,
  ShoppingBag as ShoppingBagIcon,
  Receipt as ReceiptIcon,
  LocalShipping as LocalShippingIcon,
  CheckCircle as CheckCircleIcon,
  Pending as PendingIcon,
  Cancel as CancelIcon,
} from '@mui/icons-material';
import { useAuth } from '../contexts/AuthContext';
import { customerApi } from '../services/api';
import { useNavigate } from 'react-router-dom';

const Orders = () => {
  const { isAuthenticated } = useAuth();
  const navigate = useNavigate();
  
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    if (!isAuthenticated) {
      navigate('/login');
      return;
    }
    
    fetchOrders();
  }, [isAuthenticated, navigate]);

  const fetchOrders = async () => {
    try {
      setLoading(true);
      const response = await customerApi.getOrders();
      setOrders(response.data || []);
    } catch (err) {
      setError('Failed to load orders. Please try again later.');
      console.error('Error fetching orders:', err);
    } finally {
      setLoading(false);
    }
  };

  const formatPrice = (price) => {
    return new Intl.NumberFormat('vi-VN', {
      style: 'currency',
      currency: 'VND',
    }).format(price);
  };

  const formatDate = (dateString) => {
    return new Date(dateString).toLocaleDateString('en-US', {
      year: 'numeric',
      month: 'long',
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit',
    });
  };

  const getStatusColor = (status) => {
    switch (status?.toLowerCase()) {
      case 'delivered':
      case 'completed':
        return 'success';
      case 'pending':
      case 'processing':
        return 'warning';
      case 'cancelled':
        return 'error';
      case 'shipped':
        return 'info';
      default:
        return 'default';
    }
  };

  const getStatusIcon = (status) => {
    switch (status?.toLowerCase()) {
      case 'delivered':
      case 'completed':
        return <CheckCircleIcon />;
      case 'pending':
      case 'processing':
        return <PendingIcon />;
      case 'cancelled':
        return <CancelIcon />;
      case 'shipped':
        return <LocalShippingIcon />;
      default:
        return <ReceiptIcon />;
    }
  };

  const handleReorder = (order) => {
    // TODO: Implement reorder functionality
    console.log('Reorder:', order);
  };

  const handleTrackOrder = (order) => {
    // TODO: Implement order tracking
    console.log('Track order:', order);
  };

  if (!isAuthenticated) {
    return null;
  }

  if (loading) {
    return (
      <Container maxWidth="lg" sx={{ py: 4 }}>
        <Box display="flex" justifyContent="center" alignItems="center" minHeight="400px">
          <CircularProgress />
        </Box>
      </Container>
    );
  }

  if (error) {
    return (
      <Container maxWidth="lg" sx={{ py: 4 }}>
        <Alert severity="error" sx={{ mb: 2 }}>
          {error}
        </Alert>
      </Container>
    );
  }

  return (
    <Container maxWidth="lg" sx={{ py: 4 }}>
      <Typography variant="h4" gutterBottom sx={{ fontWeight: 'bold', mb: 3 }}>
        My Orders
      </Typography>

      {orders.length === 0 ? (
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
          <ShoppingBagIcon sx={{ fontSize: 80, color: 'grey.400', mb: 2 }} />
          <Typography variant="h5" gutterBottom sx={{ fontWeight: 'bold' }}>
            No Orders Yet
          </Typography>
          <Typography variant="body1" color="text.secondary" sx={{ mb: 3 }}>
            You haven't placed any orders yet. Start shopping to see your orders here.
          </Typography>
          <Button
            variant="contained"
            onClick={() => navigate('/')}
            sx={{ px: 4, py: 1.5 }}
          >
            Start Shopping
          </Button>
        </Paper>
      ) : (
        <Box>
          {orders.map((order, index) => (
            <Accordion key={order.orderId || index} sx={{ mb: 2 }}>
              <AccordionSummary
                expandIcon={<ExpandMoreIcon />}
                aria-controls={`order-${order.orderId}-content`}
                id={`order-${order.orderId}-header`}
              >
                <Grid container spacing={2} alignItems="center">
                  <Grid item xs={12} sm={3}>
                    <Typography variant="subtitle1" sx={{ fontWeight: 'bold' }}>
                      Order #{order.orderId}
                    </Typography>
                    <Typography variant="body2" color="text.secondary">
                      {formatDate(order.orderDate || new Date())}
                    </Typography>
                  </Grid>
                  
                  <Grid item xs={12} sm={3}>
                    <Typography variant="body2" color="text.secondary">
                      Total Amount
                    </Typography>
                    <Typography variant="h6" sx={{ fontWeight: 'bold' }}>
                      {formatPrice(order.totalAmount || 0)}
                    </Typography>
                  </Grid>
                  
                  <Grid item xs={12} sm={3}>
                    <Typography variant="body2" color="text.secondary">
                      Status
                    </Typography>
                    <Chip
                      icon={getStatusIcon(order.status)}
                      label={order.status || 'Unknown'}
                      color={getStatusColor(order.status)}
                      size="small"
                    />
                  </Grid>
                  
                  <Grid item xs={12} sm={3}>
                    <Typography variant="body2" color="text.secondary">
                      Items
                    </Typography>
                    <Typography variant="body1" sx={{ fontWeight: 'bold' }}>
                      {order.orderItems?.length || 0} item(s)
                    </Typography>
                  </Grid>
                </Grid>
              </AccordionSummary>
              
              <AccordionDetails>
                <Box sx={{ width: '100%' }}>
                  {/* Order Items */}
                  <Typography variant="h6" gutterBottom sx={{ fontWeight: 'bold' }}>
                    Order Items
                  </Typography>
                  
                  <List>
                    {order.orderItems?.map((item, itemIndex) => (
                      <ListItem key={itemIndex} sx={{ px: 0 }}>
                        <ListItemAvatar>
                          <Avatar
                            src={item.product?.image}
                            alt={item.product?.productName}
                            variant="rounded"
                          />
                        </ListItemAvatar>
                        <ListItemText
                          primary={item.product?.productName}
                          secondary={
                            <Box>
                              <Typography variant="body2" color="text.secondary">
                                Quantity: {item.quantity}
                              </Typography>
                              <Typography variant="body2" color="text.secondary">
                                Price: {formatPrice(item.product?.price || 0)}
                              </Typography>
                            </Box>
                          }
                        />
                        <Typography variant="h6" sx={{ fontWeight: 'bold' }}>
                          {formatPrice((item.product?.price || 0) * (item.quantity || 0))}
                        </Typography>
                      </ListItem>
                    ))}
                  </List>

                  <Divider sx={{ my: 2 }} />

                  {/* Order Summary */}
                  <Grid container spacing={2}>
                    <Grid item xs={12} sm={6}>
                      <Typography variant="h6" gutterBottom sx={{ fontWeight: 'bold' }}>
                        Order Summary
                      </Typography>
                      <Box sx={{ display: 'flex', justifyContent: 'space-between', mb: 1 }}>
                        <Typography variant="body2">Subtotal:</Typography>
                        <Typography variant="body2">
                          {formatPrice(order.subtotal || 0)}
                        </Typography>
                      </Box>
                      <Box sx={{ display: 'flex', justifyContent: 'space-between', mb: 1 }}>
                        <Typography variant="body2">Shipping:</Typography>
                        <Typography variant="body2">
                          {formatPrice(order.shippingCost || 0)}
                        </Typography>
                      </Box>
                      <Box sx={{ display: 'flex', justifyContent: 'space-between', mb: 1 }}>
                        <Typography variant="body2">Tax:</Typography>
                        <Typography variant="body2">
                          {formatPrice(order.tax || 0)}
                        </Typography>
                      </Box>
                      <Divider sx={{ my: 1 }} />
                      <Box sx={{ display: 'flex', justifyContent: 'space-between' }}>
                        <Typography variant="h6" sx={{ fontWeight: 'bold' }}>
                          Total:
                        </Typography>
                        <Typography variant="h6" sx={{ fontWeight: 'bold' }}>
                          {formatPrice(order.totalAmount || 0)}
                        </Typography>
                      </Box>
                    </Grid>
                    
                    <Grid item xs={12} sm={6}>
                      <Typography variant="h6" gutterBottom sx={{ fontWeight: 'bold' }}>
                        Shipping Address
                      </Typography>
                      <Typography variant="body2" color="text.secondary">
                        {order.shippingAddress || 'No address provided'}
                      </Typography>
                    </Grid>
                  </Grid>

                  <Divider sx={{ my: 2 }} />

                  {/* Order Actions */}
                  <Box sx={{ display: 'flex', gap: 2, flexWrap: 'wrap' }}>
                    <Button
                      variant="outlined"
                      startIcon={<ReceiptIcon />}
                      onClick={() => handleReorder(order)}
                    >
                      Reorder
                    </Button>
                    
                    {order.status?.toLowerCase() === 'shipped' && (
                      <Button
                        variant="outlined"
                        startIcon={<LocalShippingIcon />}
                        onClick={() => handleTrackOrder(order)}
                      >
                        Track Order
                      </Button>
                    )}
                    
                    <Button
                      variant="contained"
                      onClick={() => {
                        // TODO: Implement view order details
                        console.log('View order details:', order);
                      }}
                    >
                      View Details
                    </Button>
                  </Box>
                </Box>
              </AccordionDetails>
            </Accordion>
          ))}
        </Box>
      )}
    </Container>
  );
};

export default Orders;
