import React, { useState } from 'react';
import {
  Card,
  CardMedia,
  CardContent,
  CardActions,
  Typography,
  Button,
  Box,
  Rating,
  Chip,
  IconButton,
  Tooltip,
  CircularProgress,
} from '@mui/material';
import {
  ShoppingCart as ShoppingCartIcon,
  Favorite as FavoriteIcon,
  FavoriteBorder as FavoriteBorderIcon,
  Visibility as VisibilityIcon,
} from '@mui/icons-material';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import { useCart } from '../contexts/CartContext';

const ProductCard = ({ product }) => {
  const navigate = useNavigate();
  const { isAuthenticated } = useAuth();
  const { addToCart } = useCart();
  const [isAddingToCart, setIsAddingToCart] = useState(false);
  const [isFavorite, setIsFavorite] = useState(false);

  const handleAddToCart = async (e) => {
    e.stopPropagation();
    
    if (!isAuthenticated) {
      navigate('/login');
      return;
    }

    setIsAddingToCart(true);
    try {
      const result = await addToCart(product.pId);
      if (result.success) {
        // Could show a success message here
      } else {
        // Could show an error message here
        console.error('Failed to add to cart:', result.error);
      }
    } catch (error) {
      console.error('Error adding to cart:', error);
    } finally {
      setIsAddingToCart(false);
    }
  };

  const handleToggleFavorite = (e) => {
    e.stopPropagation();
    setIsFavorite(!isFavorite);
    // TODO: Implement favorite functionality
  };

  const handleViewDetails = () => {
    navigate(`/product/${product.pId}`);
  };

  const formatPrice = (price) => {
    return new Intl.NumberFormat('vi-VN', {
      style: 'currency',
      currency: 'VND',
    }).format(price);
  };

  const getStockStatus = () => {
    if (product.isOutOfStock || product.stocks === 0) {
      return { label: 'Out of Stock', color: 'error' };
    } else if (product.stocks < 10) {
      return { label: 'Low Stock', color: 'warning' };
    }
    return { label: 'In Stock', color: 'success' };
  };

  const stockStatus = getStockStatus();

  return (
    <Card
      sx={{
        height: '100%',
        display: 'flex',
        flexDirection: 'column',
        transition: 'transform 0.2s ease-in-out, box-shadow 0.2s ease-in-out',
        '&:hover': {
          transform: 'translateY(-4px)',
          boxShadow: 4,
        },
        cursor: 'pointer',
      }}
      onClick={handleViewDetails}
    >
      {/* Product Image */}
      <Box sx={{ position: 'relative' }}>
        <CardMedia
          component="img"
          height="200"
          image={product.image || '/placeholder-product.jpg'}
          alt={product.productName}
          sx={{
            objectFit: 'cover',
            transition: 'transform 0.2s ease-in-out',
            '&:hover': {
              transform: 'scale(1.05)',
            },
          }}
        />
        
        {/* Stock Status Chip */}
        <Chip
          label={stockStatus.label}
          color={stockStatus.color}
          size="small"
          sx={{
            position: 'absolute',
            top: 8,
            left: 8,
            fontWeight: 'bold',
          }}
        />

        {/* Action Buttons */}
        <Box
          sx={{
            position: 'absolute',
            top: 8,
            right: 8,
            display: 'flex',
            flexDirection: 'column',
            gap: 1,
            opacity: 0,
            transition: 'opacity 0.2s ease-in-out',
            '.MuiCard-root:hover &': {
              opacity: 1,
            },
          }}
        >
          <Tooltip title="Quick View">
            <IconButton
              size="small"
              sx={{
                bgcolor: 'white',
                '&:hover': { bgcolor: 'grey.100' },
              }}
              onClick={(e) => {
                e.stopPropagation();
                handleViewDetails();
              }}
            >
              <VisibilityIcon fontSize="small" />
            </IconButton>
          </Tooltip>
          
          <Tooltip title={isFavorite ? 'Remove from Favorites' : 'Add to Favorites'}>
            <IconButton
              size="small"
              sx={{
                bgcolor: 'white',
                '&:hover': { bgcolor: 'grey.100' },
              }}
              onClick={handleToggleFavorite}
            >
              {isFavorite ? (
                <FavoriteIcon fontSize="small" color="error" />
              ) : (
                <FavoriteBorderIcon fontSize="small" />
              )}
            </IconButton>
          </Tooltip>
        </Box>
      </Box>

      {/* Product Info */}
      <CardContent sx={{ flexGrow: 1, pb: 1 }}>
        <Typography
          variant="h6"
          component="h3"
          sx={{
            fontWeight: 'bold',
            mb: 1,
            overflow: 'hidden',
            textOverflow: 'ellipsis',
            display: '-webkit-box',
            WebkitLineClamp: 2,
            WebkitBoxOrient: 'vertical',
            minHeight: '3em',
          }}
        >
          {product.productName}
        </Typography>

        <Typography
          variant="body2"
          color="text.secondary"
          sx={{
            mb: 2,
            overflow: 'hidden',
            textOverflow: 'ellipsis',
            display: '-webkit-box',
            WebkitLineClamp: 2,
            WebkitBoxOrient: 'vertical',
            minHeight: '2.5em',
          }}
        >
          {product.productDescription}
        </Typography>

        {/* Rating */}
        <Box sx={{ display: 'flex', alignItems: 'center', gap: 1, mb: 1 }}>
          <Rating
            value={product.rating || 0}
            precision={0.1}
            size="small"
            readOnly
          />
          <Typography variant="body2" color="text.secondary">
            ({product.ratingCount || 0})
          </Typography>
        </Box>

        {/* Price */}
        <Typography
          variant="h6"
          color="primary"
          sx={{ fontWeight: 'bold', mb: 1 }}
        >
          {formatPrice(product.price)}
        </Typography>

        {/* Stock Info */}
        {product.stocks > 0 && (
          <Typography variant="body2" color="text.secondary">
            {product.stocks} in stock
          </Typography>
        )}
      </CardContent>

      {/* Actions */}
      <CardActions sx={{ p: 2, pt: 0 }}>
        <Button
          variant="contained"
          fullWidth
          startIcon={
            isAddingToCart ? (
              <CircularProgress size={16} color="inherit" />
            ) : (
              <ShoppingCartIcon />
            )
          }
          disabled={isAddingToCart || product.isOutOfStock || product.stocks === 0}
          onClick={handleAddToCart}
          sx={{
            fontWeight: 'bold',
            textTransform: 'none',
          }}
        >
          {isAddingToCart
            ? 'Adding...'
            : product.isOutOfStock || product.stocks === 0
            ? 'Out of Stock'
            : 'Add to Cart'}
        </Button>
      </CardActions>
    </Card>
  );
};

export default ProductCard;

