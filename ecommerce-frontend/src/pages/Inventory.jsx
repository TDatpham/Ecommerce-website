import React, { useEffect, useState } from 'react';
import { Container, Typography, Box, Paper, TextField, Button, Grid, Alert } from '@mui/material';
import api from '../services/api';

const INVENTORY_BASE = 'http://localhost:8081/inventory';

const Inventory = () => {
  const [items, setItems] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [form, setForm] = useState({ sku: '', name: '', quantity: 0 });

  const fetchItems = async () => {
    try {
      setLoading(true);
      const res = await fetch(INVENTORY_BASE);
      const data = await res.json();
      setItems(Array.isArray(data) ? data : []);
    } catch (_unusedError) {
      setError('Failed to load inventory');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => { fetchItems(); }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm(prev => ({ ...prev, [name]: name === 'quantity' ? Number(value) : value }));
  };

  const handleCreate = async (e) => {
    e.preventDefault();
    setError('');
    try {
      const res = await fetch(INVENTORY_BASE, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(form),
      });
      if (!res.ok) throw new Error('Create failed');
      setForm({ sku: '', name: '', quantity: 0 });
      fetchItems();
    } catch (_unusedError) {
      setError('Failed to create item');
    }
  };

  const adjust = async (sku, delta) => {
    setError('');
    try {
      const url = `${INVENTORY_BASE}/${encodeURIComponent(sku)}/adjust?delta=${delta}`;
      const res = await fetch(url, { method: 'POST' });
      if (!res.ok) throw new Error('Adjust failed');
      fetchItems();
    } catch (_unusedError) {
      setError('Failed to adjust quantity');
    }
  };

  return (
    <Container maxWidth="md" sx={{ py: 4 }}>
      <Typography variant="h4" gutterBottom sx={{ fontWeight: 'bold' }}>
        Inventory Management
      </Typography>

      {error && <Alert severity="error" sx={{ mb: 2 }}>{error}</Alert>}

      <Paper sx={{ p: 3, mb: 4 }}>
        <Typography variant="h6" sx={{ mb: 2, fontWeight: 'bold' }}>Create Item</Typography>
        <Box component="form" onSubmit={handleCreate} sx={{ display: 'flex', gap: 2, flexWrap: 'wrap' }}>
          <TextField label="SKU" name="sku" value={form.sku} onChange={handleChange} required />
          <TextField label="Name" name="name" value={form.name} onChange={handleChange} required />
          <TextField label="Quantity" name="quantity" type="number" value={form.quantity} onChange={handleChange} required sx={{ width: 140 }} />
          <Button type="submit" variant="contained">Create</Button>
        </Box>
      </Paper>

      <Paper sx={{ p: 3 }}>
        <Typography variant="h6" sx={{ mb: 2, fontWeight: 'bold' }}>Items</Typography>
        {loading ? (
          <Typography>Loading...</Typography>
        ) : (
          <Grid container spacing={2}>
            {items.map(item => (
              <Grid item xs={12} key={item.id}>
                <Box sx={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
                  <Box>
                    <Typography variant="subtitle1" sx={{ fontWeight: 'bold' }}>{item.name}</Typography>
                    <Typography variant="body2" color="text.secondary">SKU: {item.sku}</Typography>
                  </Box>
                  <Box sx={{ display: 'flex', alignItems: 'center', gap: 2 }}>
                    <Typography>Qty: {item.quantity}</Typography>
                    <Button variant="outlined" onClick={() => adjust(item.sku, -1)}>-1</Button>
                    <Button variant="outlined" onClick={() => adjust(item.sku, 1)}>+1</Button>
                  </Box>
                </Box>
              </Grid>
            ))}
          </Grid>
        )}
      </Paper>
    </Container>
  );
};

export default Inventory;
