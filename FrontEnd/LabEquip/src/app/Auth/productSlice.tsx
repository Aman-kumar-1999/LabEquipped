// redux/features/productSlice.js
import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import axiosInstance from "@/utils/axiosInstance";

// Async thunk for fetching products with pagination
type FetchProductsParams = {
  page?: number;
  size?: number;
  sortBy?: string;
  sortDir?: string;
};

export const fetchProducts = createAsyncThunk(
  "products/fetchProducts",
  async (
    { page = 0, size = 3, sortBy = "productName", sortDir = "ASC" }: FetchProductsParams,
    thunkAPI
  ) => {
    try {
      const response = await axiosInstance.get("/products", {
        params: { page, size, sortBy, sortDir },
      });
      return response.data; // your backend should return array/page of products
    } catch (error) {
      return thunkAPI.rejectWithValue(
        (error as any).response?.data || "Something went wrong"
      );
    }
  }
);

type ProductState = {
  items: any[];
  loading: boolean;
  error: null | unknown;
};

const initialState: ProductState = {
  items: [],
  loading: false,
  error: null,
};

const productSlice = createSlice({
  name: "products",
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchProducts.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchProducts.fulfilled, (state, action) => {
        state.loading = false;
        state.items = action.payload; // 👈 store products in state
      })
      .addCase(fetchProducts.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload;
      })
      .addDefaultCase((state) => {
        state.loading = false;
      })
      ;

  },
});
// export const {  } = productSlice.actions;
export default productSlice.reducer;
