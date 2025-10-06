import React, { useState, useEffect } from 'react';
import {
  Container,
  Typography,
  Box,
  Grid,
  Card,
  CardMedia,
  CardContent,
  Button,
  CircularProgress,
  Alert,
  Chip,
} from '@mui/material';
import {
  ArrowForward as ArrowForwardIcon,
  Star as StarIcon,
  TrendingUp as TrendingUpIcon,
} from '@mui/icons-material';
import { useNavigate } from 'react-router-dom';
import { publicApi } from '../services/api';
import ProductCard from '../components/ProductCard';

const Home = () => {
  const navigate = useNavigate();
  const [categories, setCategories] = useState([]);
  const [featuredProducts, setFeaturedProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        setLoading(true);
        
        // Fetch categories
        const categoriesResponse = await publicApi.getCategories();
        setCategories(categoriesResponse.data.slice(0, 6)); // Show only first 6 categories
        
        // Fetch featured products (you might want to create a specific endpoint for this)
        const productsResponse = await publicApi.searchProducts('', 0);
        setFeaturedProducts(productsResponse.data.data.slice(0, 8)); // Show only first 8 products
        
      } catch (err) {
        setError('Failed to load data. Please try again later.');
        console.error('Error fetching data:', err);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, []);

  const handleCategoryClick = (categoryId) => {
    navigate(`/category/${categoryId}`);
  };

  const handleViewAllProducts = () => {
    navigate('/products');
  };

  const handleViewAllCategories = () => {
    navigate('/categories');
  };

  if (loading) {
    return (
      <Box
        display="flex"
        justifyContent="center"
        alignItems="center"
        minHeight="400px"
      >
        <CircularProgress />
      </Box>
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
    <Box>
      {/* Hero Section */}
      <Box
        sx={{
          background: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)',
          color: 'white',
          py: 8,
          textAlign: 'center',
        }}
      >
        <Container maxWidth="lg">
          <Typography
            variant="h2"
            component="h1"
            gutterBottom
            sx={{
              fontWeight: 'bold',
              mb: 2,
              fontSize: { xs: '2.5rem', md: '3.5rem' },
            }}
          >
            Welcome to Our Store
          </Typography>
          <Typography
            variant="h5"
            component="p"
            sx={{
              mb: 4,
              opacity: 0.9,
              maxWidth: '600px',
              mx: 'auto',
            }}
          >
            Discover amazing products at unbeatable prices. Shop with confidence
            and enjoy fast, reliable delivery.
          </Typography>
          <Button
            variant="contained"
            size="large"
            endIcon={<ArrowForwardIcon />}
            onClick={handleViewAllProducts}
            sx={{
              bgcolor: 'white',
              color: 'primary.main',
              px: 4,
              py: 1.5,
              fontSize: '1.1rem',
              fontWeight: 'bold',
              '&:hover': {
                bgcolor: 'grey.100',
              },
            }}
          >
            Shop Now
          </Button>
        </Container>
      </Box>

      <Container maxWidth="lg" sx={{ py: 6 }}>
        {/* Categories Section */}
        <Box sx={{ mb: 6 }}>
          <Box
            sx={{
              display: 'flex',
              justifyContent: 'space-between',
              alignItems: 'center',
              mb: 3,
            }}
          >
            <Typography
              variant="h4"
              component="h2"
              sx={{ fontWeight: 'bold', display: 'flex', alignItems: 'center', gap: 1 }}
            >
              <TrendingUpIcon color="primary" />
              Categories
            </Typography>
            <Button
              variant="outlined"
              onClick={handleViewAllCategories}
              endIcon={<ArrowForwardIcon />}
            >
              View All
            </Button>
          </Box>

          <Grid container spacing={3}>
            {categories.map((category) => (
              <Grid item xs={6} sm={4} md={2} key={category.cId}>
                <Card
                  sx={{
                    textAlign: 'center',
                    cursor: 'pointer',
                    transition: 'transform 0.2s ease-in-out',
                    '&:hover': {
                      transform: 'translateY(-4px)',
                    },
                  }}
                  onClick={() => handleCategoryClick(category.cId)}
                >
                  <CardMedia
                    component="img"
                    height="120"
                    image={category.image || '/placeholder-category.jpg'}
                    alt={category.category}
                    sx={{ objectFit: 'cover' }}
                  />
                  <CardContent sx={{ p: 2 }}>
                    <Typography
                      variant="subtitle1"
                      sx={{ fontWeight: 'bold', fontSize: '0.9rem' }}
                    >
                      {category.category}
                    </Typography>
                  </CardContent>
                </Card>
              </Grid>
            ))}
          </Grid>
        </Box>

        {/* Featured Products Section */}
        <Box sx={{ mb: 6 }}>
          <Box
            sx={{
              display: 'flex',
              justifyContent: 'space-between',
              alignItems: 'center',
              mb: 3,
            }}
          >
            <Typography
              variant="h4"
              component="h2"
              sx={{ fontWeight: 'bold', display: 'flex', alignItems: 'center', gap: 1 }}
            >
              <StarIcon color="primary" />
              Featured Products
            </Typography>
            <Button
              variant="outlined"
              onClick={handleViewAllProducts}
              endIcon={<ArrowForwardIcon />}
            >
              View All
            </Button>
          </Box>

          <Grid container spacing={3}>
            {featuredProducts.map((product) => (
              <Grid item xs={12} sm={6} md={4} lg={3} key={product.pId}>
                <ProductCard product={product} />
              </Grid>
            ))}
          </Grid>
        </Box>

        {/* Features Section */}
        <Box sx={{ mb: 6 }}>
          <Typography
            variant="h4"
            component="h2"
            sx={{ fontWeight: 'bold', textAlign: 'center', mb: 4 }}
          >
            Why Choose Us?
          </Typography>
          <Grid container spacing={4}>
            <Grid item xs={12} md={4}>
              <Box sx={{ textAlign: 'center' }}>
                <Box
                  sx={{
                    width: 80,
                    height: 80,
                    borderRadius: '50%',
                    bgcolor: 'primary.main',
                    display: 'flex',
                    alignItems: 'center',
                    justifyContent: 'center',
                    mx: 'auto',
                    mb: 2,
                  }}
                >
                  <Typography variant="h4" color="white">
                    🚚
                  </Typography>
                </Box>
                <Typography variant="h6" sx={{ fontWeight: 'bold', mb: 1 }}>
                  Fast Delivery
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  Get your orders delivered quickly and safely to your doorstep.
                </Typography>
              </Box>
            </Grid>
            <Grid item xs={12} md={4}>
              <Box sx={{ textAlign: 'center' }}>
                <Box
                  sx={{
                    width: 80,
                    height: 80,
                    borderRadius: '50%',
                    bgcolor: 'primary.main',
                    display: 'flex',
                    alignItems: 'center',
                    justifyContent: 'center',
                    mx: 'auto',
                    mb: 2,
                  }}
                >
                  <Typography variant="h4" color="white">
                    💎
                  </Typography>
                </Box>
                <Typography variant="h6" sx={{ fontWeight: 'bold', mb: 1 }}>
                  Quality Products
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  We offer only the highest quality products from trusted brands.
                </Typography>
              </Box>
            </Grid>
            <Grid item xs={12} md={4}>
              <Box sx={{ textAlign: 'center' }}>
                <Box
                  sx={{
                    width: 80,
                    height: 80,
                    borderRadius: '50%',
                    bgcolor: 'primary.main',
                    display: 'flex',
                    alignItems: 'center',
                    justifyContent: 'center',
                    mx: 'auto',
                    mb: 2,
                  }}
                >
                  <Typography variant="h4" color="white">
                    🛡️
                  </Typography>
                </Box>
                <Typography variant="h6" sx={{ fontWeight: 'bold', mb: 1 }}>
                  Secure Shopping
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  Your data and payments are protected with industry-standard security.
                </Typography>
              </Box>
            </Grid>
          </Grid>
        </Box>
      </Container>
    </Box>
  );
};

export default Home;
