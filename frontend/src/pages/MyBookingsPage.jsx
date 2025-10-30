import React, { useEffect, useState } from 'react'
import { api } from '../api/client'
import { useAuth } from '../auth/AuthContext'

export default function MyBookingsPage() {
  const { token, userId } = useAuth()
  const [rows, setRows] = useState([])
  const [error, setError] = useState('')

  useEffect(() => {
    if (!userId) return
    api(`/bookings/user/${userId}`, { token }).then(setRows).catch(e=>setError(e.message))
  }, [token, userId])

  return (
    <div>
      <h2>My Bookings</h2>
      {!userId && <div style={{ color: '#555' }}>Set your user id on Profile page to load your bookings.</div>}
      {error && <div style={{ color: 'crimson' }}>{error}</div>}
      <table border="1" cellPadding="6">
        <thead>
          <tr>
            <th>#</th><th>Flight</th><th>Seats</th><th>Status</th><th>Total</th><th>Booked At</th>
          </tr>
        </thead>
        <tbody>
          {rows.map((b,i)=> (
            <tr key={i}>
              <td>{b.id}</td>
              <td>{b.flightId}</td>
              <td>{b.seats}</td>
              <td>{b.status}</td>
              <td>{b.totalPrice}</td>
              <td>{b.bookingTime}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  )
}

