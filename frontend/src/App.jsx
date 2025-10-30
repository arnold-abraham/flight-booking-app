import React from 'react'
import { Routes, Route, Navigate } from 'react-router-dom'
import NavBar from './components/NavBar'
import LoginPage from './pages/LoginPage'
import RegisterPage from './pages/RegisterPage'
import FlightsPage from './pages/FlightsPage'
import SearchPage from './pages/SearchPage'
import MyBookingsPage from './pages/MyBookingsPage'
import ProfilePage from './pages/ProfilePage'
import FlightAdminPage from './pages/FlightAdminPage'
import NotFound from './pages/NotFound'
import PrivateRoute from './components/PrivateRoute'

export default function App() {
  return (
    <div style={{ fontFamily: 'system-ui, -apple-system, Segoe UI, Roboto, Ubuntu, Cantarell, Noto Sans, Helvetica Neue, Arial' }}>
      <NavBar />
      <div style={{ maxWidth: 960, margin: '1rem auto', padding: '0 1rem' }}>
        <Routes>
          <Route path="/" element={<Navigate to="/flights" replace />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/register" element={<RegisterPage />} />
          <Route path="/flights" element={<FlightsPage />} />
          <Route path="/search" element={<SearchPage />} />
          <Route path="/bookings" element={<PrivateRoute><MyBookingsPage /></PrivateRoute>} />
          <Route path="/profile" element={<PrivateRoute><ProfilePage /></PrivateRoute>} />
          <Route path="/admin/flights/new" element={<PrivateRoute role="ADMIN"><FlightAdminPage /></PrivateRoute>} />
          <Route path="*" element={<NotFound />} />
        </Routes>
      </div>
    </div>
  )
}

