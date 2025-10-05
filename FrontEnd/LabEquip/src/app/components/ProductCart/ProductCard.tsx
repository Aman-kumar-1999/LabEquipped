import { useState } from 'react';
import { Button } from '@/app/components/ui/button';
import { Badge } from '@/app/components/ui/badge';
import { Card, CardContent, CardFooter } from '@/app/components/ui/card';
import { Star, ShoppingCart, Heart, Eye } from 'lucide-react';
import { Products } from '@/app/types/api';

interface ProductCardProps {
  product: Products;
  onAddToCart?: (product: Products) => void;
  onViewDetails?: (product: Products) => void;
}

export const ProductCard = ({ product, onAddToCart, onViewDetails }: ProductCardProps) => {
  const [isWishlisted, setIsWishlisted] = useState(false);
  const discountAmount = product.individualProductPrice - product.natePriceWithDiscount;
  const rating = parseFloat(product.productRating);

  return (
    <Card className="group relative overflow-hidden transition-all hover:shadow-lg hover:shadow-primary/10">
      {/* Discount Badge */}
      {product.discountPercentage > 0 && (
        <Badge className="absolute top-3 left-3 z-10 bg-accent text-accent-foreground">
          -{product.discountPercentage}%
        </Badge>
      )}

      {/* Wishlist Button */}
      <Button
        variant="ghost"
        size="icon"
        className="absolute top-3 right-3 z-10 h-8 w-8 opacity-0 group-hover:opacity-100 transition-opacity bg-background/80 hover:bg-background"
        onClick={() => setIsWishlisted(!isWishlisted)}
      >
        <Heart className={`h-4 w-4 ${isWishlisted ? 'fill-current text-red-500' : ''}`} />
      </Button>

      {/* Product Image */}
      <div className="relative overflow-hidden bg-muted/30">
        <img
          src={product.imagePath !== "No" ? product.imagePath : '/placeholder-product.jpg'}
          alt={product.productName}
          className="w-full h-48 object-cover transition-transform group-hover:scale-105"
          onError={(e) => {
            e.currentTarget.src = '/placeholder-product.jpg';
          }}
        />
        
        {/* Quick Actions */}
        <div className="absolute inset-0 bg-black/40 opacity-0 group-hover:opacity-100 transition-opacity flex items-center justify-center gap-2">
          <Button
            variant="secondary"
            size="sm"
            onClick={() => onViewDetails?.(product)}
          >
            <Eye className="h-4 w-4 mr-1" />
            View
          </Button>
          <Button
            variant="default"
            size="sm"
            onClick={() => onAddToCart?.(product)}
          >
            <ShoppingCart className="h-4 w-4 mr-1" />
            Add
          </Button>
        </div>
      </div>

      <CardContent className="p-4 space-y-3">
        {/* Category & Brand */}
        <div className="flex items-center justify-between text-xs text-muted-foreground">
          <span className="bg-muted px-2 py-1 rounded-md">{product.category}</span>
          <span>{product.brandName}</span>
        </div>

        {/* Product Name */}
        <h3 className="font-semibold text-foreground line-clamp-2 group-hover:text-primary transition-colors">
          {product.productName}
        </h3>

        {/* Description */}
        <p className="text-sm text-muted-foreground line-clamp-2">
          {product.productDescription}
        </p>

        {/* Rating */}
        <div className="flex items-center gap-1">
          {[...Array(5)].map((_, i) => (
            <Star
              key={i}
              className={`h-3 w-3 ${
                i < rating
                  ? 'fill-amber-400 text-amber-400'
                  : 'text-muted-foreground'
              }`}
            />
          ))}
          <span className="text-xs text-muted-foreground ml-1">({rating})</span>
        </div>

        {/* Pricing */}
        <div className="space-y-1">
          <div className="flex items-center gap-2">
            <span className="text-lg font-bold text-foreground">
              ₹{product.natePriceWithDiscount.toLocaleString()}
            </span>
            {product.discountPercentage > 0 && (
              <span className="text-sm text-muted-foreground line-through">
                ₹{product.individualProductPrice.toLocaleString()}
              </span>
            )}
          </div>
          {product.discountPercentage > 0 && (
            <p className="text-xs text-success font-medium">
              Save ₹{discountAmount.toLocaleString()}
            </p>
          )}
        </div>

        {/* Stock Info */}
        <div className="flex items-center justify-between text-xs">
          <span className="text-muted-foreground">
            Stock: {product.productQuantity} units
          </span>
          <Badge variant={product.isVerified === "Yes" ? "default" : "secondary"} className="text-xs">
            {product.isVerified === "Yes" ? "Verified" : "Pending"}
          </Badge>
        </div>
      </CardContent>

      <CardFooter className="p-4 pt-0">
        <Button
          className="w-full"
          onClick={() => onAddToCart?.(product)}
          disabled={product.productQuantity === 0}
        >
          <ShoppingCart className="h-4 w-4 mr-2" />
          {product.productQuantity > 0 ? 'Add to Cart' : 'Out of Stock'}
        </Button>
      </CardFooter>
    </Card>
  );
};