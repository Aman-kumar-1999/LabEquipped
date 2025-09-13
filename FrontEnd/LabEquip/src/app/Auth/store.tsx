import { configureStore } from "@reduxjs/toolkit";
import authReducer from "./authSlice";

const store = configureStore({
  reducer: {
    auth: authReducer,
  },
});

export default store;

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
// import { Provider } from 'react-redux';
// import { createStore, combineReducers, applyMiddleware } from 'redux';
// import thunk from 'redux-thunk';
// import { composeWithDevTools } from 'redux-devtools-extension';
// import authReducer from './authSlice';

// const rootReducer = combineReducers({
//   auth: authReducer,
// });

// const store = createStore(rootReducer, composeWithDevTools(applyMiddleware(thunk)));

// export type RootState = ReturnType<typeof rootReducer>;
// export type AppDispatch = typeof store.dispatch;

// export default store;