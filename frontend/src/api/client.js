const API_BASE = import.meta.env.VITE_API_BASE || '/api'

export async function api(path, { method = 'GET', token, body, headers } = {}) {
  const res = await fetch(`${API_BASE}${path}`, {
    method,
    headers: {
      'Content-Type': body ? 'application/json' : undefined,
      ...(token ? { Authorization: `Bearer ${token}` } : {}),
      ...(headers || {})
    },
    body: body ? JSON.stringify(body) : undefined,
    credentials: 'same-origin'
  })
  if (!res.ok) {
    let msg = `${res.status} ${res.statusText}`
    try { const data = await res.json(); msg += `: ${JSON.stringify(data)}` } catch {}
    throw new Error(msg)
  }
  const ct = res.headers.get('content-type') || ''
  return ct.includes('application/json') ? res.json() : res.text()
}

