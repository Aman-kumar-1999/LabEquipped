# 1️⃣ Use the official Node image to build
FROM node:18-alpine AS builder

WORKDIR /app

# Copy dependency files
COPY package*.json ./

# Install deps
RUN npm ci

# Copy all source
COPY . .

# Build for production
RUN npm run build

# 2️⃣ Serve the app using a lightweight image
FROM node:18-alpine AS runner

WORKDIR /app

# Copy build artifacts
COPY --from=builder /app ./

# Expose port 3000 (Next.js default)
EXPOSE 3000

# Start Next.js in production mode
CMD ["npm", "start"]
