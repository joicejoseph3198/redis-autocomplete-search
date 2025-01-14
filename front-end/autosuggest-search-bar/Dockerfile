# Use official Node.js image as the base
FROM node:20-alpine

# Set working directory
WORKDIR /app

# Copy package.json and install dependencies
COPY package*.json /app/
RUN npm install

# Copy the rest of your frontend code
COPY . /app/

# Expose the port your frontend is running on (Vite uses 5173 by default)
EXPOSE 5173

# Start the Vite development server, listen on all interfaces
CMD ["npm", "run", "dev", "--", "--host"]
