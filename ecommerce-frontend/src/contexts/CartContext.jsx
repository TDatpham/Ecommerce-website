import React, { createContext, useContext, useState, useEffect } from 'react';
import { customerApi } from '../services/api';
import { useAuth } from './AuthContext';

const CartContext = createContext();

export const useCart = () => {
  const context = useContext(CartContext);
  if (!context) {
    throw new Error('useCart must be used within a CartProvider');
  }
  return context;
};

export const CartProvider = ({ children }) => {
  const [cartItems, setCartItems] = useState([]);
  const [loading, setLoading] = useState(false);
  const { isAuthenticated } = useAuth();

  // Fetch cart items when user is authenticated
  useEffect(() => {
    if (isAuthenticated) {
      fetchCartItems();
    } else {
      setCartItems([]);
    }
  }, [isAuthenticated]);

  const fetchCartItems = async () => {
    try {
      setLoading(true);
      const response = await customerApi.getCartItems();
      setCartItems(response.data || []);
    } catch (error) {
      console.error('Error fetching cart items:', error);
      setCartItems([]);
    } finally {
      setLoading(false);
    }
  };

  const addToCart = async (productId) => {
    if (!isAuthenticated) {
      throw new Error('Please login to add items to cart');
    }

    try {
      setLoading(true);
      await customerApi.addToCart(productId);
      await fetchCartItems(); // Refresh cart items
      return { success: true };
    } catch (error) {
      return { 
        success: false, 
        error: error.response?.data?.message || 'Failed to add to cart' 
      };
    } finally {
      setLoading(false);
    }
  };

  const updateQuantity = async (cartItemId, type) => {
    try {
      setLoading(true);
      const response = await customerApi.updateCartItemQuantity(cartItemId, type);
      await fetchCartItems(); // Refresh cart items
      return { success: true, newQuantity: response.data };
    } catch (error) {
      return { 
        success: false, 
        error: error.response?.data?.message || 'Failed to update quantity' 
      };
    } finally {
      setLoading(false);
    }
  };

  const removeFromCart = async (cartItemId) => {
    try {
      setLoading(true);
      await customerApi.removeCartItem(cartItemId);
      await fetchCartItems(); // Refresh cart items
      return { success: true };
    } catch (error) {
      return { 
        success: false, 
        error: error.response?.data?.message || 'Failed to remove item' 
      };
    } finally {
      setLoading(false);
    }
  };

  const clearCart = async () => {
    try {
      setLoading(true);
      // Remove all items one by one
      for (const item of cartItems) {
        await customerApi.removeCartItem(item.cartItemId);
      }
      setCartItems([]);
      return { success: true };
    } catch (error) {
      return { 
        success: false, 
        error: error.response?.data?.message || 'Failed to clear cart' 
      };
    } finally {
      setLoading(false);
    }
  };

  // Calculate total price
  const getTotalPrice = () => {
    return cartItems.reduce((total, item) => {
      return total + (item.product?.price || 0) * (item.quantity || 0);
    }, 0);
  };

  // Calculate total items
  const getTotalItems = () => {
    return cartItems.reduce((total, item) => total + (item.quantity || 0), 0);
  };

  const value = {
    cartItems,
    loading,
    addToCart,
    updateQuantity,
    removeFromCart,
    clearCart,
    fetchCartItems,
    getTotalPrice,
    getTotalItems,
    isEmpty: cartItems.length === 0,
  };

  return (
    <CartContext.Provider value={value}>
      {children}
    </CartContext.Provider>
  );
};

