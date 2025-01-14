import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    host: true, // Expose the server to external networks
    port: 5173, // Ensure the port matches the one in your Dockerfile and docker-compose.yml
  },
})
