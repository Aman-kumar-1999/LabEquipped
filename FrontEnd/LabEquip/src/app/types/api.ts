export interface User {
  id: number;
  firstName: string;
  lastName: string;
  username: string;
  email: string;
  businessType: string;
  roles: Role[];
}

export interface Role {
  id: number;
  name: string;
  isEnabled: boolean;
}

export interface SidebarItem {
  key: string;
  label: string;
  url: string;
  orderIndex: number;
}

export interface LoginResponse {
  user_details: {
    user: User;
    sidebar: SidebarItem[];
  };
  token: string;
}

export interface Products {
  productId?: string | number;
  vendorId: string;
  date: { $date: string };
  vendorName: string;
  vendorEmail: string;
  productName: string;
  productDescription: string;
  category: string;
  brandName: string;
  productQuantity: number;
  individualProductPrice: number;
  TotalProductPrice: number;
  natePriceWithDiscount: number;
  discountPercentage: number;
  status: string;
  action: string;
  imageName: string;
  imagePath: string;
  bulkCode: string;
  variationName: string;
  variationId: string;
  gst: number;
  hsn: string;
  isVerified: string;
  tierNo: string;
  containLiquid: string;
  companyCode: string;
  bulkPack: string;
  bulkPrice: number;
  productRating: string;
}

export interface CartItem {
  _id: { $oid: string };
  productId: string;
  vendorId: string;
  customerName: string;
  date: { $date: string };
  email: string;
  phone: string;
  address: string;
  vendorName: string;
  vendorEmail: string;
  productName: string;
  productDescription: string;
  category: string;
  brandName: string;
  productQuantity: number;
  individualProductPrice: number;
  TotalProductPrice: number;
  natePriceWithDiscount: number;
  discountPercentage: number;
  status: string;
  action: string;
  imageName: string;
  imagePath: string;
  bulkCode: string;
  variationName: string;
  variationId: string;
  gst: number;
  hsn: string;
  isVerified: string;
  tierNo: string;
  containLiquid: string;
}

export interface Order {
  _id: string;
  productId: string;
  customerName: string;
  vendorId: string;
  date: { $date: string };
  email: string;
  phone: string;
  address: string;
  vendorName: string;
  vendorEmail: string;
  productName: string;
  productDescription: string;
  category: string;
  brandName: string;
  productQuantity: number;
  individualProductPrice: number;
  TotalProductPrice: number;
  natePriceWithDiscount: number;
  discountPercentage: number;
  status: string;
  action: string;
  imageName: string;
  imagePath: string;
  bulkCode: string;
  variationName: string;
  variationId: string;
  gst: number;
  hsn: string;
  isVerified: string;
  tierNo: string;
  containLiquid: string;
  transactionId: string;
  _class: string;
}