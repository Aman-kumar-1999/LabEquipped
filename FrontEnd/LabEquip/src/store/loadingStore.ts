import { create } from 'zustand';

interface LoadingState {
  loading: boolean;
  pendingRequests: number;
  startLoading: () => void;
  stopLoading: () => void;
}

type State = {
  loading: boolean;
  pendingRequests: number;
}

const useLoadingStore = create<LoadingState>((set) => ({
  loading: false,
  pendingRequests: 0,
  startLoading: () =>
    set((state: State) => ({
      pendingRequests: state.pendingRequests + 1,
      loading: true,
    })),
  stopLoading: () =>
    set((state: State) => ({
      pendingRequests: Math.max(0, state.pendingRequests - 1),
      loading: state.pendingRequests - 1 > 0,
    })),
}));

export default useLoadingStore;