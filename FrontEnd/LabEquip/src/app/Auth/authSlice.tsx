import axiosInstance from "@/utils/axiosInstance";
import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import axios from "axios";

const BASE_URL = process.env.NEXT_PUBLIC_API_BASE_URL;


// One menu item
export interface MenuItem {
  id: number;
  icon: string;
  label: string;
  href: string;
  allowedRoles: Role[];
  visibleFor: string;
  orderIndex: number;
  enabled: boolean;
}

// Role type
export interface Role {
  id: number;
  name: string;
  isEnabled: boolean;
}

// User type
export interface User {
  id: number;
  username: string;
  email: string;
  firstName: string;
  lastName: string;
  businessType: string;
  roles: Role[];
}

interface AuthState {
  token: string | null;
  user: User | null;
  menu: MenuItem[];
  loading: boolean;
  error: string | null;
}

const initialState: AuthState = {
  token: null,
  user: null,
  menu: [],
  loading: false,
  error: null,
};

// Async thunk to call backend login API
export const loginUser = createAsyncThunk(
  "auth",
  async (
    { username, password }: { username: string; password: string },
    { rejectWithValue }
  ) => {
    try {
      const res = await axiosInstance.post(`/auth/login`, { username, password });
      return res.data; // expects { token: "...", role: "admin" }
    } catch (err: any) {
      return rejectWithValue(err.response?.data?.message || "Login failed");
    }
  }
);

const authSlice = createSlice({
  name: "auth",
  initialState,
  reducers: {
    logout: (state) => {
      state.token = null;
      localStorage.removeItem("jwtToken");
      localStorage.clear();
    },
    isLoginedIn: (state) => {
      if (typeof window !== "undefined") {
        const token = localStorage.getItem("jwtToken");
        if (token) state.token = token;
      }
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(loginUser.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(loginUser.fulfilled, (state, action) => {
        state.loading = false;
        state.token = action.payload.token;
        state.user = action.payload.user_details.user;
        state.menu = action.payload.user_details.sidebar;

        // persist token in localStorage if needed
        localStorage.setItem("jwtToken", action.payload.token);
        cookieStore.set("token", action.payload.token);
        // Persist user details in localStorage
        localStorage.setItem("user", JSON.stringify(action.payload.user_details.user));
        // Persist menu in localStorage
        localStorage.setItem("menu", JSON.stringify(action.payload.user_details.sidebar));

      })
      .addCase(loginUser.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload as string;
      });
  },
});

export const { logout, isLoginedIn } = authSlice.actions;
export default authSlice.reducer;



// 'use client';


// import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
// import axios from "axios";

// // 🔹 Thunk for login API call
// export const loginUser = createAsyncThunk(
//   "auth/loginUser",
//   async ({ username, password }: { username: string; password: string }, { rejectWithValue }) => {
//     try {
//       const response = await axios.post("/api/auth/login", { username, password });
//       // backend should return { token: "...jwt..." }
//       return response.data;
//     } catch (err: any) {
//       return rejectWithValue(err.response?.data || "Login failed");
//     }
//   }
// );

// const authSlice = createSlice({
//   name: "auth",
//   initialState: {
//     token: null as string | null,
//     loading: false,
//     error: null as string | null,
//   },
//   reducers: {
//     logout: (state) => {
//       state.token = null;
//       localStorage.removeItem("jwtToken");
//     },
//     loadTokenFromStorage: (state) => {
//       if (typeof window !== "undefined") {
//         const token = localStorage.getItem("jwtToken");
//         if (token) state.token = token
//       }
//     },
    
//   },
//   extraReducers: (builder) => {
//     builder
//       .addCase(loginUser.pending, (state) => {
//         state.loading = true;
//         state.error = null;
//       })
//       .addCase(loginUser.fulfilled, (state, action) => {
//         state.loading = false;
//         state.token = action.payload.token;
//         // persist token in localStorage if needed
//         localStorage.setItem("jwtToken", action.payload.token);
//       })
//       .addCase(loginUser.rejected, (state, action) => {
//         state.loading = false;
//         state.error = action.payload as string | null;
//       });
//   },
// });

// export const { logout, loadTokenFromStorage } = authSlice.actions;
// export default authSlice.reducer;

