import React, { useState } from 'react'
import { api } from '../api/client'
import { useAuth } from '../auth/AuthContext'

export default function SearchPage() {
  const { token } = useAuth()
  const [source, setSource] = useState('LON')
  const [destination, setDestination] = useState('NYC')
  const [date, setDate] = useState('2025-11-01')
  const [flights, setFlights] = useState([])
  const [error, setError] = useState('')

  const onSearch = async (e) => {
    e.preventDefault()
    setError('')
    try {
      const list = await api(`/flights/search?source=${encodeURIComponent(source)}&destination=${encodeURIComponent(destination)}&date=${date}`, { token })
      setFlights(list)
    } catch (err) { setError(err.message) }
  }

  return (
    <div>
      <h2>Search Flights</h2>
      <form onSubmit={onSearch} style={{ display: 'flex', gap: 8, flexWrap: 'wrap', alignItems: 'center' }}>
        <input placeholder="From" value={source} onChange={e=>setSource(e.target.value)} />
        <input placeholder="To" value={destination} onChange={e=>setDestination(e.target.value)} />
        <input type="date" value={date} onChange={e=>setDate(e.target.value)} />
        <button type="submit">Search</button>
      </form>
      {error && <div style={{ color: 'crimson' }}>{error}</div>}
      <ul>
        {flights.map((f,i)=> (
          <li key={i}>{f.flightNumber} {f.source}→{f.destination} {f.departureTime}</li>
        ))}
      </ul>
    </div>
  )
}

