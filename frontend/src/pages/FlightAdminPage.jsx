import React, { useState } from 'react'
import { api } from '../api/client'
import { useAuth } from '../auth/AuthContext'

export default function FlightAdminPage() {
  const { token } = useAuth()
  const [form, setForm] = useState({
    flightNumber: '', source: '', destination: '',
    departureTime: '', arrivalTime: '', price: '', seatsAvailable: 100
  })
  const [msg, setMsg] = useState('')

  const submit = async (e) => {
    e.preventDefault(); setMsg('')
    try {
      const body = {
        ...form,
        price: Number(form.price),
        seatsAvailable: Number(form.seatsAvailable)
      }
      const res = await api('/flights', { method: 'POST', token, body })
      setMsg(`Created flight ${res.flightNumber} (#${res.id})`)
    } catch (e) { setMsg(e.message) }
  }

  return (
    <div>
      <h2>Create Flight</h2>
      <form onSubmit={submit} style={{ display: 'grid', gap: 8, maxWidth: 560 }}>
        <input placeholder="Flight number" value={form.flightNumber} onChange={e=>setForm(f=>({...f, flightNumber: e.target.value}))} />
        <input placeholder="Source" value={form.source} onChange={e=>setForm(f=>({...f, source: e.target.value}))} />
        <input placeholder="Destination" value={form.destination} onChange={e=>setForm(f=>({...f, destination: e.target.value}))} />
        <label>Departure (ISO): <input placeholder="2025-11-01T08:00:00" value={form.departureTime} onChange={e=>setForm(f=>({...f, departureTime: e.target.value}))} /></label>
        <label>Arrival (ISO): <input placeholder="2025-11-01T11:00:00" value={form.arrivalTime} onChange={e=>setForm(f=>({...f, arrivalTime: e.target.value}))} /></label>
        <input placeholder="Price" type="number" step="0.01" value={form.price} onChange={e=>setForm(f=>({...f, price: e.target.value}))} />
        <input placeholder="Seats" type="number" value={form.seatsAvailable} onChange={e=>setForm(f=>({...f, seatsAvailable: e.target.value}))} />
        <button type="submit">Create</button>
      </form>
      {msg && <div style={{ marginTop: 8 }}>{msg}</div>}
    </div>
  )
}

