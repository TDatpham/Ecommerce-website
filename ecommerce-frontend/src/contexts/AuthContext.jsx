/* eslint react-refresh/only-export-components: off */
import React, { createContext, useContext, useState, useEffect } from 'react';
import { publicApi, customerApi } from '../services/api';

const AuthContext = createContext();

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);
  const [token, setToken] = useState(localStorage.getItem('token'));

  useEffect(() => {
    const initAuth = async () => {
      const storedToken = localStorage.getItem('token');
      const storedUser = localStorage.getItem('user');
      
      if (storedToken && storedUser) {
        setToken(storedToken);
        setUser(JSON.parse(storedUser));
        
        // Verify token is still valid by fetching user profile
        try {
          const response = await customerApi.getProfile();
          setUser(response.data);
        } catch (_unusedError) {
          // Token is invalid, clear storage
          localStorage.removeItem('token');
          localStorage.removeItem('user');
          setToken(null);
          setUser(null);
        }
      }
      setLoading(false);
    };

    initAuth();
  }, []);

  const login = async (credentials) => {
    try {
      // This would typically be handled by Spring Security
      // For now, we'll simulate a login process
      const response = await fetch('http://localhost:8080/app/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(credentials),
      });

      if (response.ok) {
        const data = await response.json();
        const { token: authToken, user: userData } = data;
        
        localStorage.setItem('token', authToken);
        localStorage.setItem('user', JSON.stringify(userData));
        setToken(authToken);
        setUser(userData);
        return { success: true };
      } else {
        const error = await response.json();
        return { success: false, error: error.message || 'Login failed' };
      }
    } catch (_unusedError) {
      return { success: false, error: 'Network error' };
    }
  };

  const register = async (userData) => {
    try {
      const response = await customerApi.register(userData);
      return { success: true, message: response.data };
    } catch (e) {
      return { 
        success: false, 
        error: e.response?.data?.message || 'Registration failed' 
      };
    }
  };

  const logout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    setToken(null);
    setUser(null);
  };

  const updateProfile = async (profileData) => {
    try {
      const response = await customerApi.updateProfile(profileData);
      const updatedUser = { ...user, ...profileData };
      setUser(updatedUser);
      localStorage.setItem('user', JSON.stringify(updatedUser));
      return { success: true, message: response.data };
    } catch (e) {
      return { 
        success: false, 
        error: e.response?.data?.message || 'Update failed' 
      };
    }
  };

  const changePassword = async (passwordData) => {
    try {
      const response = await customerApi.changePassword(passwordData);
      return { success: true, message: response.data };
    } catch (e) {
      return { 
        success: false, 
        error: e.response?.data?.message || 'Password change failed' 
      };
    }
  };

  const forgotPassword = async (email) => {
    try {
      const response = await publicApi.forgotPassword(email);
      return { success: true, message: response.data };
    } catch (e) {
      return { 
        success: false, 
        error: e.response?.data?.message || 'Request failed' 
      };
    }
  };

  const resetPassword = async (resetData) => {
    try {
      const response = await publicApi.resetPassword(resetData);
      return { success: true, message: response.data };
    } catch (e) {
      return { 
        success: false, 
        error: e.response?.data?.message || 'Reset failed' 
      };
    }
  };

  const value = {
    user,
    token,
    loading,
    login,
    register,
    logout,
    updateProfile,
    changePassword,
    forgotPassword,
    resetPassword,
    isAuthenticated: !!token && !!user,
  };

  return (
    <AuthContext.Provider value={value}>
      {children}
    </AuthContext.Provider>
  );
};

