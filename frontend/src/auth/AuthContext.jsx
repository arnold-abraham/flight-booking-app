import React, { createContext, useContext, useEffect, useMemo, useState } from 'react'

const AuthCtx = createContext(null)

function decodeJwt(token) {
  try {
    const [, payload] = token.split('.')
    const json = atob(payload.replace(/-/g, '+').replace(/_/g, '/'))
    return JSON.parse(decodeURIComponent(escape(json)))
  } catch {
    return null
  }
}

export function AuthProvider({ children }) {
  const [token, setToken] = useState(() => localStorage.getItem('token') || '')
  const [user, setUser] = useState(() => {
    if (!token) return null
    const p = decodeJwt(token)
    return p ? { email: p.sub, role: p.role, exp: p.exp } : null
  })
  const [userId, setUserId] = useState(() => {
    const v = localStorage.getItem('userId')
    return v ? Number(v) : null
  })

  useEffect(() => {
    if (token) localStorage.setItem('token', token)
    else localStorage.removeItem('token')
  }, [token])

  useEffect(() => {
    if (userId) localStorage.setItem('userId', String(userId))
    else localStorage.removeItem('userId')
  }, [userId])

  const login = (t) => {
    setToken(t)
    const p = decodeJwt(t)
    setUser(p ? { email: p.sub, role: p.role, exp: p.exp } : null)
  }
  const logout = () => { setToken(''); setUser(null); setUserId(null) }

  const value = useMemo(() => ({ token, user, userId, setUserId, login, logout }), [token, user, userId])

  return <AuthCtx.Provider value={value}>{children}</AuthCtx.Provider>
}

export function useAuth() {
  const ctx = useContext(AuthCtx)
  if (!ctx) throw new Error('useAuth must be used within AuthProvider')
  return ctx
}

