import type { NextConfig } from "next";

const nextConfig: NextConfig = {
  env: {
    NEXT_PUBLIC_AI_SUGGESTIONS_API: 'http://localhost:5001/api/products/ai/suggestions'
    
  }
};

export default nextConfig;
