import React, { useState } from 'react';
import {
  AppBar,
  Toolbar,
  Typography,
  Button,
  IconButton,
  Badge,
  Box,
  InputBase,
  Menu,
  MenuItem,
  Avatar,
  Divider,
} from '@mui/material';
import {
  Search as SearchIcon,
  ShoppingCart as ShoppingCartIcon,
  Person as PersonIcon,
  Menu as MenuIcon,
  Home as HomeIcon,
  Category as CategoryIcon,
  Login as LoginIcon,
  Logout as LogoutIcon,
  AccountCircle as AccountCircleIcon,
} from '@mui/icons-material';
import { useNavigate, useLocation } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import { useCart } from '../contexts/CartContext';
import InventoryIcon from '@mui/icons-material/Warehouse';

const Header = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const { user, isAuthenticated, logout } = useAuth();
  const { getTotalItems } = useCart();
  
  const [searchQuery, setSearchQuery] = useState('');
  const [anchorEl, setAnchorEl] = useState(null);
  const [mobileMenuAnchor, setMobileMenuAnchor] = useState(null);

  const handleSearch = (e) => {
    e.preventDefault();
    if (searchQuery.trim()) {
      navigate(`/search?q=${encodeURIComponent(searchQuery.trim())}`);
      setSearchQuery('');
    }
  };

  const handleProfileMenuOpen = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleProfileMenuClose = () => {
    setAnchorEl(null);
  };

  const handleMobileMenuOpen = (event) => {
    setMobileMenuAnchor(event.currentTarget);
  };

  const handleMobileMenuClose = () => {
    setMobileMenuAnchor(null);
  };

  const handleLogout = () => {
    logout();
    handleProfileMenuClose();
    navigate('/');
  };

  const handleProfileClick = () => {
    navigate('/profile');
    handleProfileMenuClose();
  };

  const handleOrdersClick = () => {
    navigate('/orders');
    handleProfileMenuClose();
  };

  const isMenuOpen = Boolean(anchorEl);
  const isMobileMenuOpen = Boolean(mobileMenuAnchor);

  const renderProfileMenu = (
    <Menu
      anchorEl={anchorEl}
      open={isMenuOpen}
      onClose={handleProfileMenuClose}
      anchorOrigin={{
        vertical: 'bottom',
        horizontal: 'right',
      }}
      transformOrigin={{
        vertical: 'top',
        horizontal: 'right',
      }}
    >
      <MenuItem onClick={handleProfileClick}>
        <AccountCircleIcon sx={{ mr: 1 }} />
        Profile
      </MenuItem>
      <MenuItem onClick={handleOrdersClick}>
        <ShoppingCartIcon sx={{ mr: 1 }} />
        Orders
      </MenuItem>
      <Divider />
      <MenuItem onClick={handleLogout}>
        <LogoutIcon sx={{ mr: 1 }} />
        Logout
      </MenuItem>
    </Menu>
  );

  const renderMobileMenu = (
    <Menu
      anchorEl={mobileMenuAnchor}
      open={isMobileMenuOpen}
      onClose={handleMobileMenuClose}
      anchorOrigin={{
        vertical: 'bottom',
        horizontal: 'left',
      }}
      transformOrigin={{
        vertical: 'top',
        horizontal: 'left',
      }}
    >
      <MenuItem onClick={() => { navigate('/'); handleMobileMenuClose(); }}>
        <HomeIcon sx={{ mr: 1 }} />
        Home
      </MenuItem>
      <MenuItem onClick={() => { navigate('/categories'); handleMobileMenuClose(); }}>
        <CategoryIcon sx={{ mr: 1 }} />
        Categories
      </MenuItem>
      <MenuItem onClick={() => { navigate('/cart'); handleMobileMenuClose(); }}>
        <ShoppingCartIcon sx={{ mr: 1 }} />
        Cart ({getTotalItems()})
      </MenuItem>
      <MenuItem onClick={() => { navigate('/inventory'); handleMobileMenuClose(); }}>
        <InventoryIcon sx={{ mr: 1 }} />
        Inventory
      </MenuItem>
      {!isAuthenticated ? (
        <MenuItem onClick={() => { navigate('/login'); handleMobileMenuClose(); }}>
          <LoginIcon sx={{ mr: 1 }} />
          Login
        </MenuItem>
      ) : (
        <MenuItem onClick={handleLogout}>
          <LogoutIcon sx={{ mr: 1 }} />
          Logout
        </MenuItem>
      )}
    </Menu>
  );

  return (
    <AppBar position="sticky" sx={{ bgcolor: 'primary.main' }}>
      <Toolbar>
        {/* Mobile menu button */}
        <IconButton
          edge="start"
          color="inherit"
          aria-label="menu"
          onClick={handleMobileMenuOpen}
          sx={{ display: { xs: 'block', md: 'none' }, mr: 1 }}
        >
          <MenuIcon />
        </IconButton>

        {/* Logo */}
        <Typography
          variant="h6"
          component="div"
          sx={{
            flexGrow: { xs: 1, md: 0 },
            mr: { md: 4 },
            cursor: 'pointer',
            fontWeight: 'bold',
          }}
          onClick={() => navigate('/')}
        >
          E-Commerce
        </Typography>

        {/* Search bar - hidden on mobile */}
        <Box
          component="form"
          onSubmit={handleSearch}
          sx={{
            display: { xs: 'none', md: 'flex' },
            flexGrow: 1,
            maxWidth: 600,
            mx: 2,
          }}
        >
          <Box
            sx={{
              position: 'relative',
              borderRadius: 1,
              backgroundColor: 'rgba(255, 255, 255, 0.1)',
              '&:hover': {
                backgroundColor: 'rgba(255, 255, 255, 0.15)',
              },
              width: '100%',
            }}
          >
            <Box
              sx={{
                padding: '0 16px',
                height: '100%',
                position: 'absolute',
                pointerEvents: 'none',
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'center',
              }}
            >
              <SearchIcon />
            </Box>
            <InputBase
              placeholder="Search products..."
              value={searchQuery}
              onChange={(e) => setSearchQuery(e.target.value)}
              sx={{
                color: 'inherit',
                '& .MuiInputBase-input': {
                  padding: '8px 8px 8px 40px',
                  transition: 'width 0.2s',
                  width: '100%',
                },
              }}
            />
          </Box>
        </Box>

        {/* Desktop navigation */}
        <Box sx={{ display: { xs: 'none', md: 'flex' }, alignItems: 'center', gap: 1 }}>
          <Button
            color="inherit"
            startIcon={<HomeIcon />}
            onClick={() => navigate('/')}
            sx={{ 
              color: location.pathname === '/' ? 'secondary.main' : 'inherit',
              fontWeight: location.pathname === '/' ? 'bold' : 'normal'
            }}
          >
            Home
          </Button>
          <Button
            color="inherit"
            startIcon={<CategoryIcon />}
            onClick={() => navigate('/categories')}
            sx={{ 
              color: location.pathname === '/categories' ? 'secondary.main' : 'inherit',
              fontWeight: location.pathname === '/categories' ? 'bold' : 'normal'
            }}
          >
            Categories
          </Button>
          <Button
            color="inherit"
            startIcon={<InventoryIcon />}
            onClick={() => navigate('/inventory')}
            sx={{ 
              color: location.pathname === '/inventory' ? 'secondary.main' : 'inherit',
              fontWeight: location.pathname === '/inventory' ? 'bold' : 'normal'
            }}
          >
            Inventory
          </Button>
        </Box>

        {/* Cart button */}
        <IconButton
          color="inherit"
          onClick={() => navigate('/cart')}
          sx={{ ml: 1 }}
        >
          <Badge badgeContent={getTotalItems()} color="secondary">
            <ShoppingCartIcon />
          </Badge>
        </IconButton>

        {/* User menu */}
        {isAuthenticated ? (
          <IconButton
            color="inherit"
            onClick={handleProfileMenuOpen}
            sx={{ ml: 1 }}
          >
            <Avatar sx={{ width: 32, height: 32, bgcolor: 'secondary.main' }}>
              {user?.name?.charAt(0)?.toUpperCase() || <PersonIcon />}
            </Avatar>
          </IconButton>
        ) : (
          <Button
            color="inherit"
            startIcon={<LoginIcon />}
            onClick={() => navigate('/login')}
            sx={{ ml: 1 }}
          >
            Login
          </Button>
        )}

        {renderProfileMenu}
        {renderMobileMenu}
      </Toolbar>
    </AppBar>
  );
};

export default Header;

