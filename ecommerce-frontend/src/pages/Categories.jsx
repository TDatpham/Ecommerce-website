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
  Paper,
} from '@mui/material';
import {
  Category as CategoryIcon,
  ArrowForward as ArrowForwardIcon,
} from '@mui/icons-material';
import { useNavigate } from 'react-router-dom';
import { publicApi } from '../services/api';

const Categories = () => {
  const navigate = useNavigate();
  const [categories, setCategories] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchCategories = async () => {
      try {
        setLoading(true);
        const response = await publicApi.getCategories();
        setCategories(response.data || []);
      } catch (err) {
        setError('Failed to load categories. Please try again later.');
        console.error('Error fetching categories:', err);
      } finally {
        setLoading(false);
      }
    };

    fetchCategories();
  }, []);

  const handleCategoryClick = (categoryId) => {
    navigate(`/category/${categoryId}`);
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
      {/* Header */}
      <Box sx={{ mb: 4, textAlign: 'center' }}>
        <Typography
          variant="h3"
          component="h1"
          gutterBottom
          sx={{
            fontWeight: 'bold',
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'center',
            gap: 2,
          }}
        >
          <CategoryIcon color="primary" />
          Product Categories
        </Typography>
        <Typography variant="h6" color="text.secondary" sx={{ maxWidth: '600px', mx: 'auto' }}>
          Explore our wide range of product categories and find exactly what you're looking for
        </Typography>
      </Box>

      {/* Categories Grid */}
      {categories.length > 0 ? (
        <Grid container spacing={3}>
          {categories.map((category) => (
            <Grid item xs={12} sm={6} md={4} lg={3} key={category.cId}>
              <Card
                sx={{
                  height: '100%',
                  display: 'flex',
                  flexDirection: 'column',
                  transition: 'transform 0.2s ease-in-out, box-shadow 0.2s ease-in-out',
                  cursor: 'pointer',
                  '&:hover': {
                    transform: 'translateY(-8px)',
                    boxShadow: 6,
                  },
                }}
                onClick={() => handleCategoryClick(category.cId)}
              >
                <CardMedia
                  component="img"
                  height="200"
                  image={category.image || '/placeholder-category.jpg'}
                  alt={category.category}
                  sx={{
                    objectFit: 'cover',
                    transition: 'transform 0.2s ease-in-out',
                    '&:hover': {
                      transform: 'scale(1.05)',
                    },
                  }}
                />
                <CardContent sx={{ flexGrow: 1, p: 3 }}>
                  <Typography
                    variant="h6"
                    component="h3"
                    gutterBottom
                    sx={{
                      fontWeight: 'bold',
                      textAlign: 'center',
                      mb: 2,
                    }}
                  >
                    {category.category}
                  </Typography>
                  
                  <Box sx={{ textAlign: 'center' }}>
                    <Button
                      variant="contained"
                      endIcon={<ArrowForwardIcon />}
                      sx={{
                        fontWeight: 'bold',
                        textTransform: 'none',
                        px: 3,
                        py: 1,
                      }}
                    >
                      Explore
                    </Button>
                  </Box>
                </CardContent>
              </Card>
            </Grid>
          ))}
        </Grid>
      ) : (
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
          <CategoryIcon sx={{ fontSize: 80, color: 'grey.400', mb: 2 }} />
          <Typography variant="h5" gutterBottom sx={{ fontWeight: 'bold' }}>
            No Categories Available
          </Typography>
          <Typography variant="body1" color="text.secondary" sx={{ mb: 3 }}>
            We're working on adding more categories. Please check back later.
          </Typography>
          <Button
            variant="contained"
            onClick={() => navigate('/')}
            sx={{ px: 4, py: 1.5 }}
          >
            Back to Home
          </Button>
        </Paper>
      )}

      {/* Additional Info */}
      <Box sx={{ mt: 6, textAlign: 'center' }}>
        <Paper
          elevation={1}
          sx={{
            p: 4,
            bgcolor: 'grey.50',
            borderRadius: 2,
          }}
        >
          <Typography variant="h5" gutterBottom sx={{ fontWeight: 'bold' }}>
            Can't Find What You're Looking For?
          </Typography>
          <Typography variant="body1" color="text.secondary" sx={{ mb: 3 }}>
            Use our search feature to find specific products or browse all our products
          </Typography>
          <Box sx={{ display: 'flex', gap: 2, justifyContent: 'center', flexWrap: 'wrap' }}>
            <Button
              variant="outlined"
              onClick={() => navigate('/search')}
              sx={{ px: 3, py: 1 }}
            >
              Search Products
            </Button>
            <Button
              variant="contained"
              onClick={() => navigate('/products')}
              sx={{ px: 3, py: 1 }}
            >
              View All Products
            </Button>
          </Box>
        </Paper>
      </Box>
    </Container>
  );
};

export default Categories;

