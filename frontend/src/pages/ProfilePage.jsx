import React, { useState } from 'react'
import { useAuth } from '../auth/AuthContext'

export default function ProfilePage() {
  const { user, userId, setUserId } = useAuth()
  const [id, setId] = useState(userId || '')

  const save = () => {
    const n = Number(id)
    if (!n) return
    setUserId(n)
  }

  return (
    <div>
      <h2>My Profile</h2>
      <div>Email: {user?.email}</div>
      <div>Role: {user?.role}</div>
      <div style={{ marginTop: 12 }}>
        <label>My User ID:&nbsp;</label>
        <input value={id} onChange={e=>setId(e.target.value)} style={{ width: 120 }} />
        <button onClick={save} style={{ marginLeft: 8 }}>Save</button>
        <div style={{ color: '#555' }}>
          Note: temporarily required for booking since API needs userId as query param.
        </div>
      </div>
    </div>
  )
}

