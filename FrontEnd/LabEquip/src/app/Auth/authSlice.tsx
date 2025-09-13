import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import axios from "axios";

const BASE_URL = process.env.NEXT_PUBLIC_API_BASE_URL;

interface AuthState {
  token: string | null;
  loading: boolean;
  error: string | null;
}

const initialState: AuthState = {
  token: null,
  loading: false,
  error: null,
};

// Async thunk to call backend login API
export const loginUser = createAsyncThunk(
  "auth/loginUser",
  async (
    { username, password }: { username: string; password: string },
    { rejectWithValue }
  ) => {
    try {
      const res = await axios.post(`${BASE_URL}/auth/login`, { username, password });
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
        localStorage.setItem("jwtToken", action.payload.token);
      })
      .addCase(loginUser.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload as string;
      });
  },
});

export const { logout } = authSlice.actions;
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

