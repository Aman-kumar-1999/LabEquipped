// import { ProductCard } from './ProductCard';
import { Button } from '@/app/components/ui/button';
import { ArrowRight } from 'lucide-react';
import { Products } from '@/app/types/api';
import beakerImage from '@/assets/beaker-product.jpg';
import microscopeImage from '@/assets/microscope-product.jpg';
import spatulaImage from '@/assets/spatula-product.jpg';
import { ProductCard } from '../ProductCart/ProductCard';

import '@/app/css/product_card.css';
import { useState } from 'react';
import { useSelector } from 'react-redux';
import { RootState } from '@/app/Auth/store';


// Mock data matching API structure
const mockProducts: Products[] = [
    {
        vendorId: "809d8644-6209-4e90-bdff-6007a8f6ba45",
        date: { $date: "2023-07-25T00:00:00.000Z" },
        vendorName: "LabTech Solutions",
        vendorEmail: "sales@labtech.com",
        productName: "Professional Glass Beaker Set",
        productDescription: "High-quality borosilicate glass beakers with measurement markings. Perfect for laboratory experiments and chemical analysis.",
        category: "Glassware",
        brandName: "LabPro",
        productQuantity: 25,
        individualProductPrice: 450,
        TotalProductPrice: 3825,
        natePriceWithDiscount: 405,
        discountPercentage: 10,
        status: "confirmed",
        action: "Edit & Delete",
        imageName: "beaker-set.jpg",
        imagePath: "assets/featured_product_image_for_a_c_1_e61758df.jpg",
        bulkCode: "LB001",
        variationName: "250ml Set",
        variationId: "VAR001",
        gst: 18,
        hsn: "HI39269099",
        isVerified: "Yes",
        tierNo: "T001",
        containLiquid: "250ml",
        companyCode: "LP001",
        bulkPack: "no",
        bulkPrice: 0,
        productRating: "4.5"
    },
    {
        vendorId: "809d8644-6209-4e90-bdff-6007a8f6ba45",
        date: { $date: "2023-07-25T00:00:00.000Z" },
        vendorName: "Scientific Equipment Co.",
        vendorEmail: "info@sciequip.com",
        productName: "Digital Microscope 1000x",
        productDescription: "Advanced digital microscope with 1000x magnification, LED illumination, and USB connectivity for computer viewing.",
        category: "Instruments",
        brandName: "MicroVision",
        productQuantity: 8,
        individualProductPrice: 12500,
        TotalProductPrice: 88200,
        natePriceWithDiscount: 11000,
        discountPercentage: 12,
        status: "confirmed",
        action: "Edit & Delete",
        imageName: "microscope-digital.jpg",
        imagePath: "assets/featured_product_image_for_a_c_1_e61758df.jpg",
        bulkCode: "MV002",
        variationName: "1000x Digital",
        variationId: "VAR002",
        gst: 18,
        hsn: "HI90189022",
        isVerified: "Yes",
        tierNo: "T002",
        containLiquid: "N/A",
        companyCode: "MV002",
        bulkPack: "no",
        bulkPrice: 0,
        productRating: "4.8"
    },
    {
        vendorId: "809d8644-6209-4e90-bdff-6007a8f6ba45",
        date: { $date: "2023-07-25T00:00:00.000Z" },
        vendorName: "Lab Tools Direct",
        vendorEmail: "orders@labtools.com",
        productName: "Stainless Steel Spatula Set",
        productDescription: "Professional grade stainless steel spatulas for precise handling of chemicals and samples. Set of 5 different sizes.",
        category: "Tools",
        brandName: "SteelLab",
        productQuantity: 50,
        individualProductPrice: 280,
        TotalProductPrice: 1176,
        natePriceWithDiscount: 250,
        discountPercentage: 11,
        status: "confirmed",
        action: "Edit & Delete",
        imageName: "spatula-set.jpg",
        imagePath: "assets/featured_product_image_for_a_c_1_e61758df.jpg",
        bulkCode: "SL003",
        variationName: "5-piece Set",
        variationId: "VAR003",
        gst: 18,
        hsn: "HI73269099",
        isVerified: "Yes",
        tierNo: "T003",
        containLiquid: "N/A",
        companyCode: "SL003",
        bulkPack: "yes",
        bulkPrice: 220,
        productRating: "4.2"
    }
];

interface FeaturedProductsProps {
    onAddToCart?: (product: Products) => void;
    onViewProduct?: (product: Products) => void;
    product?: Products;
}

export const FeaturedProducts = ({ onAddToCart, onViewProduct, product }: FeaturedProductsProps) => {
    const [isHovered, setIsHovered] = useState(false);
    // const { items, loading, error } = useSelector(
    // (state: RootState) => state.products



    return (
        <div className="card-product">
            <span className="discount" style={{ background: 1, color: '#ffcc00' }}>
                <i className="fa-solid fa-tag me-1">
                </i>

                <span className='overlap-discount'>
                   
                    {/* Example: use the first product's discountPercentage */}
                   {/* -{items[0]?.discountPercentage ?? mockProducts[0].discountPercentage}% */}
                   -{product?.discountPercentage}%
                </span>

            </span>


            <span className="wishlist" style={{ background: 0 }}>
                <i className="fa-solid fa-heart">
                </i>
            </span>
            <img style={{ width: '320px', height: '200px', padding: '0px', objectFit: 'cover' }}
            // src="assets/featured_product_image_for_a_c_1_e61758df.jpg"
            src={product?.imagePath}
            alt="Microscope" className='card-image' />


            {/* Buttons on hover */}
            <div className="hover-buttons">
                {/* <button>View</button> */}
                <button
                    //   onClick={() => handleAddToCart(r.sku)}
                    className="x-btn x-btn--neutral btn-sm "
                >
                    <i className="fa-solid fa-eye me-1">
                    </i>
                    View
                </button>
                {/* <button>🛒  Add</button> */}
                <button
                    //   onClick={() => handleAddToCart(r.sku)}
                    className="x-btn x-btn--success btn-sm "
                >
                    <i className="fa-solid fa-cart-plus me-1">
                    </i>
                    Add
                </button>
            </div>

            <div className="card-content">
                <div className="category">
                    Instruments
                    {product?.category}
                </div>
                <div className="brand">{product?.brandName}</div>
                <div className="title">{product?.productName}</div>
                {/* <div className="desc">Advanced digital microscope with 1000x magnification, LED illumination, and USB connectivity.</div> */}
                <div className="rating">
                    {/* ⭐⭐⭐⭐⭐ (4.8) */}
              
                    ⭐⭐⭐⭐⭐ ({product?.productRating})
                    
                    </div>
                <div>
                    <span className="price">₹11,000</span>
                    <span className="old-price">₹12,500</span>
                </div>
                <div className="save">Save ₹1,500</div>
                <div className="stock">Stock: 8 units</div>
                <span className="verified">Verified</span>
            </div>

            <button className="add-cart"
                style={{
                    backgroundColor: isHovered ? "var(--color-success)" : "var(--color-neutral)",
                    //background: "var(--color-success)",
                    color: "white",
                    // padding: "10px 20px",
                    border: "none",
                    // borderRadius: "8px",
                    cursor: "pointer",
                    transition: "0.3s ease",
                }}
                onMouseEnter={() => setIsHovered(true)}
                onMouseLeave={() => setIsHovered(false)}
            ><i className="fa-solid fa-cart-plus me-1">
                </i>Add to Cart</button>
        </div>
        // <section className="py-16 bg-muted/30">
        //   <div className="container mx-auto px-4">
        //     {/* Section Header */}
        //     <div className="text-center space-y-4 mb-12">
        //       <h2 className="text-3xl lg:text-4xl font-bold text-foreground">
        //         Featured Products
        //       </h2>
        //       <p className="text-lg text-muted-foreground max-w-2xl mx-auto">
        //         Discover our most popular laboratory equipment, trusted by professionals worldwide
        //         for precision and reliability.
        //       </p>
        //     </div>

        //     {/* Products Grid */}
        //     <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-8 mb-12">
        //       {mockProducts.map((product, index) => (
        //         <ProductCard
        //           key={index}
        //           product={product}
        //           onAddToCart={onAddToCart}
        //           onViewDetails={onViewProduct}
        //         />
        //       ))}
        //     </div>

        //     {/* View All Button */}
        //     <div className="text-center">
        //       <Button variant="professional" size="lg" className="group">
        //         View All Products
        //         <ArrowRight className="ml-2 h-5 w-5 group-hover:translate-x-1 transition-transform" />
        //       </Button>
        //     </div>
        //   </div>
        // </section>
    );
};