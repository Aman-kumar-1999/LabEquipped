// src/utils/axiosInstance.ts
import axios from "axios";
import store from "../app/Auth/store"; // import your redux store
import useLoadingStore from "../store/loadingStore";

const axiosInstance = axios.create({
  baseURL: process.env.NEXT_PUBLIC_API_BASE_URL || "http://localhost:5001/api", // Base URL
  headers: {
    "Content-Type": "application/json",
  },
});

// Add token automatically to every request and handle loading state
axiosInstance.interceptors.request.use((config) => {
  const token = store.getState().auth.token; // Get token from Redux
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  
  // Start loading
  useLoadingStore.getState().startLoading();
  return config;
});

// Handle response and loading state
axiosInstance.interceptors.response.use(
  (response) => {
    // Stop loading on successful response
    useLoadingStore.getState().stopLoading();
    return response;
  },
  (error) => {
    // Stop loading on error
    useLoadingStore.getState().stopLoading();
    return Promise.reject(error);
  }
);

export default axiosInstance;
