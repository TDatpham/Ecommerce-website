# E-Commerce Frontend

A modern React-based frontend application for the E-Commerce platform, built with Material-UI and React Router.

## Features

- 🛍️ **Product Catalog**: Browse and search products with advanced filtering
- 🛒 **Shopping Cart**: Add, remove, and manage cart items
- 👤 **User Authentication**: Login, register, and profile management
- 📱 **Responsive Design**: Mobile-first design that works on all devices
- 🎨 **Modern UI**: Clean and intuitive interface using Material-UI
- 🔍 **Advanced Search**: Search products with filters and sorting options
- ⭐ **Product Reviews**: View and add product reviews
- 📦 **Order Management**: Track orders and order history

## Tech Stack

- **React 19** - Frontend framework
- **Vite** - Build tool and development server
- **Material-UI (MUI)** - UI component library
- **React Router** - Client-side routing
- **Axios** - HTTP client for API calls
- **TanStack Query** - Data fetching and caching
- **Context API** - State management

## Getting Started

### Prerequisites

- Node.js (version 16 or higher)
- npm or yarn
- Backend API running on `http://localhost:8080`

### Installation

1. Clone the repository:
```bash
git clone <repository-url>
cd ecommerce-frontend
```

2. Install dependencies:
```bash
npm install
```

3. Start the development server:
```bash
npm run dev
```

4. Open your browser and navigate to `http://localhost:5173`

### Available Scripts

- `npm run dev` - Start development server
- `npm run build` - Build for production
- `npm run preview` - Preview production build
- `npm run lint` - Run ESLint

## Project Structure

```
src/
├── components/          # Reusable UI components
│   ├── Header.jsx      # Navigation header
│   ├── Footer.jsx      # Site footer
│   └── ProductCard.jsx # Product display card
├── contexts/           # React contexts for state management
│   ├── AuthContext.jsx # Authentication state
│   └── CartContext.jsx # Shopping cart state
├── pages/              # Page components
│   ├── Home.jsx        # Homepage
│   ├── Login.jsx       # Login page
│   ├── Register.jsx    # Registration page
│   ├── Cart.jsx        # Shopping cart page
│   ├── ProductDetail.jsx # Product details page
│   ├── Categories.jsx  # Categories page
│   └── Search.jsx      # Search page
├── services/           # API services
│   └── api.js          # API client configuration
├── hooks/              # Custom React hooks
├── utils/              # Utility functions
└── App.jsx             # Main application component
```

## API Integration

The frontend communicates with the Spring Boot backend through RESTful APIs:

- **Public APIs**: Product search, categories, reviews
- **Customer APIs**: Authentication, cart, orders, profile
- **Admin APIs**: Product management, user management

### API Endpoints

- Base URL: `http://localhost:8080/app`
- Authentication: JWT token-based
- Content Type: `application/json`

## Features Overview

### Authentication
- User registration and login
- JWT token management
- Protected routes
- Password reset functionality

### Product Management
- Product listing with pagination
- Advanced search and filtering
- Product details with reviews
- Category-based browsing

### Shopping Cart
- Add/remove products
- Quantity management
- Persistent cart state
- Checkout process

### User Experience
- Responsive design
- Loading states
- Error handling
- Smooth animations

## Configuration

### Environment Variables

Create a `.env` file in the root directory:

```env
VITE_API_BASE_URL=http://localhost:8080/app
VITE_APP_NAME=E-Commerce Store
```

### Theme Customization

The application uses Material-UI theming. You can customize the theme in `src/App.jsx`:

```javascript
const theme = createTheme({
  palette: {
    primary: {
      main: '#1976d2',
    },
    secondary: {
      main: '#dc004e',
    },
  },
});
```

## Deployment

### Build for Production

```bash
npm run build
```

The build files will be generated in the `dist` directory.

### Deploy to Vercel

1. Install Vercel CLI:
```bash
npm i -g vercel
```

2. Deploy:
```bash
vercel
```

### Deploy to Netlify

1. Build the project:
```bash
npm run build
```

2. Upload the `dist` folder to Netlify

## Contributing

1. Fork the repository
2. Create a feature branch: `git checkout -b feature-name`
3. Commit changes: `git commit -am 'Add feature'`
4. Push to branch: `git push origin feature-name`
5. Submit a pull request

## License

This project is licensed under the MIT License.

## Support

For support and questions, please contact the development team or create an issue in the repository.