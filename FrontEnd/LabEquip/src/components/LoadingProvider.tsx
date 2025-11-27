import LoadingOverlay from './LoadingOverlay';
import useLoadingStore from '../store/loadingStore';

type LoadingProviderProps = {
  children: React.ReactNode;
};

const LoadingProvider = ({ children }: LoadingProviderProps) => {
  const loading = useLoadingStore((state: { loading: boolean }) => state.loading);

  return (
    <>
      {children}
      <LoadingOverlay isLoading={loading} />
    </>
  );
};

export default LoadingProvider;