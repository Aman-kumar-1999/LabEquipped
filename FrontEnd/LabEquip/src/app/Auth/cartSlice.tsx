// store/cartSlice.ts
import { createSlice, PayloadAction } from "@reduxjs/toolkit";

export interface CartItem {
  id: string;
  name: string;
  price: number;
  stock: number;
  quantity: number;
  outOfStock: boolean;
  sku:string;
  image: string;
}

interface CartState {
  items: CartItem[];
  role: "B2B" | "B2C";
}

const initialState: CartState = {
  items: [],
  role: "B2B",
};

const cartSlice = createSlice({
  name: "cart",
  initialState,
  reducers: {
    addItem(state, action: PayloadAction<CartItem>) {
      state.items.push(action.payload);
    },
    removeItem(state, action: PayloadAction<string>) {
      state.items = state.items.filter((item) => item.id !== action.payload);
    },
    updateQuantity(
      state,
      action: PayloadAction<{ id: string; quantity: number }>
    ) {
      const item = state.items.find((i) => i.id === action.payload.id);
      if (item) item.quantity = action.payload.quantity;
    },
    setRole(state, action: PayloadAction<"B2B" | "B2C">) {
      state.role = action.payload;
    },
    clearCart(state) {
      state.items = [];
    },
  },
});

export const { addItem, removeItem, updateQuantity, setRole, clearCart } =
  cartSlice.actions;
export default cartSlice.reducer;
