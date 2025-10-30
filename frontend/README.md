# Flight Booking Frontend (React + Vite)

A minimal React UI for your Spring Boot Flight Booking API.

## Features
- JWT login/register
- List all flights
- Search flights by source/destination/date
- Create booking (requires userId due to current API design)
- View my bookings
- Admin: create flight

## Dev server

1. Install deps

```bash
cd frontend
npm install
```

2. Start Vite dev server (proxies /api to http://localhost:8080)

```bash
npm run dev
```

Open http://localhost:5173

## API base & CORS
- During development, the Vite dev server proxies `/api` to `http://localhost:8080` (see `vite.config.js`). The frontend calls `/api/...` and avoids CORS.
- In production, set `VITE_API_BASE` to your backend URL (e.g., `https://api.example.com`) so the app calls that directly.

## Notes / current backend constraints
- Bookings POST requires `userId` as a query param. Enter and save your user id under the Profile page; bookings will use it.
- `/flights` and `/flights/search` may require Authorization depending on SecurityConfig; if they are public, you can remove the token requirement in the client calls.
- Date/time fields expect ISO strings (e.g., `2025-11-01T08:00:00`).

## Build
```bash
npm run build
npm run preview
```
