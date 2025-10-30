import React from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { useAuth } from '../auth/AuthContext'

export default function NavBar() {
  const { user, logout } = useAuth()
  const nav = useNavigate()
  const onLogout = () => { logout(); nav('/login') }

  return (
    <nav style={{ borderBottom: '1px solid #ddd', padding: '0.5rem 1rem', display: 'flex', gap: '1rem' }}>
      <Link to="/flights">Flights</Link>
      <Link to="/search">Search</Link>
      {user && <Link to="/bookings">My Bookings</Link>}
      {user && <Link to="/profile">Profile</Link>}
      {user?.role === 'ADMIN' && <Link to="/admin/flights/new">Add Flight</Link>}
      <div style={{ marginLeft: 'auto' }}>
        {user ? (
          <>
            <span style={{ marginRight: 8 }}>{user.email} ({user.role})</span>
            <button onClick={onLogout}>Logout</button>
          </>
        ) : (
          <>
            <Link to="/login">Login</Link>
            <span> | </span>
            <Link to="/register">Register</Link>
          </>
        )}
      </div>
    </nav>
  )
}

