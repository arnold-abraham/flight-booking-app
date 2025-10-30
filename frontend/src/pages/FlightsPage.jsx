import React, { useEffect, useState } from 'react'
import { useAuth } from '../auth/AuthContext'
import { api } from '../api/client'

export default function FlightsPage() {
  const { token, userId } = useAuth()
  const [flights, setFlights] = useState([])
  const [error, setError] = useState('')
  const [booking, setBooking] = useState({ flightId: null, seats: 1 })
  const [msg, setMsg] = useState('')

  useEffect(() => {
    api('/flights', { token }).then(setFlights).catch(e => setError(e.message))
  }, [token])

  const book = async (flightId) => {
    setMsg('')
    if (!userId) { setMsg('Set your user id in Profile page first.'); return }
    try {
      await api(`/bookings?userId=${userId}`, { method: 'POST', token, body: { flightId, seats: booking.seats } })
      setMsg('Booked! Check My Bookings page.')
    } catch (e) { setMsg(e.message) }
  }

  return (
    <div>
      <h2>All Flights</h2>
      {error && <div style={{ color: 'crimson' }}>{error}</div>}
      {msg && <div style={{ color: 'green' }}>{msg}</div>}
      <table border="1" cellPadding="6">
        <thead>
          <tr>
            <th>#</th><th>Flight</th><th>From</th><th>To</th><th>Departs</th><th>Arrives</th><th>Price</th><th>Seats</th><th>Action</th>
          </tr>
        </thead>
        <tbody>
          {flights.map((f, i) => (
            <tr key={i}>
              <td>{f.id}</td>
              <td>{f.flightNumber}</td>
              <td>{f.source}</td>
              <td>{f.destination}</td>
              <td>{f.departureTime}</td>
              <td>{f.arrivalTime}</td>
              <td>{f.price}</td>
              <td>{f.seatsAvailable}</td>
              <td>
                <input type="number" min="1" value={booking.flightId===f.id?booking.seats:1}
                  onChange={e=>setBooking({ flightId: f.id, seats: Number(e.target.value) })}
                  style={{ width: 60, marginRight: 8 }} />
                <button onClick={() => book(f.id)}>Book</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  )
}

