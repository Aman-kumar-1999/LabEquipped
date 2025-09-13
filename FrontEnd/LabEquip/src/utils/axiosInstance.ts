// src/utils/axiosInstance.ts
import axios from "axios";
import store  from "../app/Auth/store"; // import your redux store

const axiosInstance = axios.create({
  baseURL: process.env.NEXT_PUBLIC_API_BASE_URL || "http://localhost:5001", // Base URL
  headers: {
    "Content-Type": "application/json",
  },
});

// Add token automatically to every request
axiosInstance.interceptors.request.use((config) => {
  const token = store.getState().auth.token; // Get token from Redux
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export default axiosInstance;
