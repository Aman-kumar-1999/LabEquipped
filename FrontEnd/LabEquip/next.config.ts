import type { NextConfig } from "next";

const nextConfig: NextConfig = {
  env: {
    NEXT_PUBLIC_AI_SUGGESTIONS_API: `${process.env.NEXT_PUBLIC_API_BASE_URL}/products/ai/suggestions`
    
  }
};

export default nextConfig;
