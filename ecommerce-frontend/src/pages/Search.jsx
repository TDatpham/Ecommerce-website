import React, { useState, useEffect } from 'react';
import {
  Container,
  Typography,
  Box,
  Grid,
  TextField,
  Button,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  Chip,
  CircularProgress,
  Alert,
  Pagination,
  Paper,
  InputAdornment,
} from '@mui/material';
import {
  Search as SearchIcon,
  FilterList as FilterListIcon,
  Sort as SortIcon,
} from '@mui/icons-material';
import { useSearchParams, useNavigate } from 'react-router-dom';
import { publicApi } from '../services/api';
import ProductCard from '../components/ProductCard';

const Search = () => {
  const [searchParams, setSearchParams] = useSearchParams();
  const navigate = useNavigate();
  
  const [searchQuery, setSearchQuery] = useState(searchParams.get('q') || '');
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [totalItems, setTotalItems] = useState(0);
  
  // Filters
  const [sortBy, setSortBy] = useState('relevance');
  const [minPrice, setMinPrice] = useState('');
  const [maxPrice, setMaxPrice] = useState('');
  const [minRating, setMinRating] = useState('');

  useEffect(() => {
    if (searchQuery) {
      performSearch();
    }
  }, [searchQuery, page, sortBy, minPrice, maxPrice, minRating]);

  const performSearch = async () => {
    try {
      setLoading(true);
      setError('');
      
      let response;
      
      // Apply filters based on selected options
      if (minRating) {
        response = await publicApi.searchProductsByRating(searchQuery, parseInt(minRating), page);
      } else if (minPrice || maxPrice) {
        const min = minPrice ? parseInt(minPrice) : 0;
        const max = maxPrice ? parseInt(maxPrice) : 999999;
        response = await publicApi.searchProductsByPrice(searchQuery, min, max, page);
      } else if (sortBy === 'price-asc') {
        response = await publicApi.searchProductsByPriceAsc(searchQuery, page);
      } else if (sortBy === 'price-desc') {
        response = await publicApi.searchProductsByPriceDesc(searchQuery, page);
      } else {
        response = await publicApi.searchProducts(searchQuery, page);
      }
      
      setProducts(response.data.data || []);
      setTotalPages(response.data.pageInfo?.totalPages || 0);
      setTotalItems(response.data.pageInfo?.totalElements || 0);
      
    } catch (err) {
      setError('Failed to search products. Please try again.');
      console.error('Search error:', err);
    } finally {
      setLoading(false);
    }
  };

  const handleSearch = (e) => {
    e.preventDefault();
    if (searchQuery.trim()) {
      setPage(0);
      setSearchParams({ q: searchQuery.trim() });
    }
  };

  const handlePageChange = (event, value) => {
    setPage(value - 1);
    window.scrollTo({ top: 0, behavior: 'smooth' });
  };

  const clearFilters = () => {
    setSortBy('relevance');
    setMinPrice('');
    setMaxPrice('');
    setMinRating('');
    setPage(0);
  };

  const hasActiveFilters = sortBy !== 'relevance' || minPrice || maxPrice || minRating;

  return (
    <Container maxWidth="lg" sx={{ py: 4 }}>
      {/* Search Header */}
      <Box sx={{ mb: 4 }}>
        <Typography variant="h4" gutterBottom sx={{ fontWeight: 'bold' }}>
          Search Products
        </Typography>
        
        {/* Search Form */}
        <Paper elevation={2} sx={{ p: 3, mb: 3 }}>
          <Box component="form" onSubmit={handleSearch} sx={{ display: 'flex', gap: 2, alignItems: 'center' }}>
            <TextField
              fullWidth
              variant="outlined"
              placeholder="Search for products..."
              value={searchQuery}
              onChange={(e) => setSearchQuery(e.target.value)}
              InputProps={{
                startAdornment: (
                  <InputAdornment position="start">
                    <SearchIcon />
                  </InputAdornment>
                ),
              }}
            />
            <Button
              type="submit"
              variant="contained"
              size="large"
              sx={{ px: 4, py: 1.5 }}
            >
              Search
            </Button>
          </Box>
        </Paper>

        {/* Results Summary */}
        {searchQuery && (
          <Typography variant="body1" color="text.secondary" sx={{ mb: 2 }}>
            {loading ? 'Searching...' : `Found ${totalItems} results for "${searchQuery}"`}
          </Typography>
        )}
      </Box>

      {searchQuery && (
        <Grid container spacing={3}>
          {/* Filters Sidebar */}
          <Grid item xs={12} md={3}>
            <Paper elevation={2} sx={{ p: 3 }}>
              <Box sx={{ display: 'flex', alignItems: 'center', gap: 1, mb: 3 }}>
                <FilterListIcon />
                <Typography variant="h6" sx={{ fontWeight: 'bold' }}>
                  Filters
                </Typography>
              </Box>

              {/* Sort By */}
              <FormControl fullWidth sx={{ mb: 3 }}>
                <InputLabel>Sort By</InputLabel>
                <Select
                  value={sortBy}
                  label="Sort By"
                  onChange={(e) => setSortBy(e.target.value)}
                >
                  <MenuItem value="relevance">Relevance</MenuItem>
                  <MenuItem value="price-asc">Price: Low to High</MenuItem>
                  <MenuItem value="price-desc">Price: High to Low</MenuItem>
                </Select>
              </FormControl>

              {/* Price Range */}
              <Typography variant="subtitle1" sx={{ fontWeight: 'bold', mb: 2 }}>
                Price Range
              </Typography>
              <Box sx={{ display: 'flex', gap: 1, mb: 3 }}>
                <TextField
                  label="Min Price"
                  type="number"
                  value={minPrice}
                  onChange={(e) => setMinPrice(e.target.value)}
                  size="small"
                  sx={{ flex: 1 }}
                />
                <TextField
                  label="Max Price"
                  type="number"
                  value={maxPrice}
                  onChange={(e) => setMaxPrice(e.target.value)}
                  size="small"
                  sx={{ flex: 1 }}
                />
              </Box>

              {/* Rating Filter */}
              <FormControl fullWidth sx={{ mb: 3 }}>
                <InputLabel>Minimum Rating</InputLabel>
                <Select
                  value={minRating}
                  label="Minimum Rating"
                  onChange={(e) => setMinRating(e.target.value)}
                >
                  <MenuItem value="">Any Rating</MenuItem>
                  <MenuItem value="4">4+ Stars</MenuItem>
                  <MenuItem value="3">3+ Stars</MenuItem>
                  <MenuItem value="2">2+ Stars</MenuItem>
                  <MenuItem value="1">1+ Stars</MenuItem>
                </Select>
              </FormControl>

              {/* Clear Filters */}
              {hasActiveFilters && (
                <Button
                  variant="outlined"
                  fullWidth
                  onClick={clearFilters}
                  sx={{ mt: 2 }}
                >
                  Clear Filters
                </Button>
              )}
            </Paper>
          </Grid>

          {/* Products Grid */}
          <Grid item xs={12} md={9}>
            {loading ? (
              <Box display="flex" justifyContent="center" alignItems="center" minHeight="400px">
                <CircularProgress />
              </Box>
            ) : error ? (
              <Alert severity="error" sx={{ mb: 2 }}>
                {error}
              </Alert>
            ) : products.length > 0 ? (
              <>
                <Grid container spacing={3}>
                  {products.map((product) => (
                    <Grid item xs={12} sm={6} lg={4} key={product.pId}>
                      <ProductCard product={product} />
                    </Grid>
                  ))}
                </Grid>

                {/* Pagination */}
                {totalPages > 1 && (
                  <Box sx={{ display: 'flex', justifyContent: 'center', mt: 4 }}>
                    <Pagination
                      count={totalPages}
                      page={page + 1}
                      onChange={handlePageChange}
                      color="primary"
                      size="large"
                    />
                  </Box>
                )}
              </>
            ) : (
              <Paper
                elevation={2}
                sx={{
                  p: 6,
                  textAlign: 'center',
                  display: 'flex',
                  flexDirection: 'column',
                  alignItems: 'center',
                }}
              >
                <SearchIcon sx={{ fontSize: 80, color: 'grey.400', mb: 2 }} />
                <Typography variant="h5" gutterBottom sx={{ fontWeight: 'bold' }}>
                  No Products Found
                </Typography>
                <Typography variant="body1" color="text.secondary" sx={{ mb: 3 }}>
                  Try adjusting your search terms or filters to find what you're looking for.
                </Typography>
                <Button
                  variant="contained"
                  onClick={clearFilters}
                  sx={{ px: 4, py: 1.5 }}
                >
                  Clear Filters
                </Button>
              </Paper>
            )}
          </Grid>
        </Grid>
      )}

      {/* No Search Query */}
      {!searchQuery && (
        <Paper
          elevation={2}
          sx={{
            p: 6,
            textAlign: 'center',
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
          }}
        >
          <SearchIcon sx={{ fontSize: 80, color: 'grey.400', mb: 2 }} />
          <Typography variant="h5" gutterBottom sx={{ fontWeight: 'bold' }}>
            Start Your Search
          </Typography>
          <Typography variant="body1" color="text.secondary" sx={{ mb: 3 }}>
            Enter a search term above to find products in our store.
          </Typography>
        </Paper>
      )}
    </Container>
  );
};

export default Search;
