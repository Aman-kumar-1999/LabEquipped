


"use client";

import { useState, useEffect, useRef } from "react";
import Swal from "sweetalert2";
import Chart from "chart.js/auto";
import '../../css/style.css';
import '../../css/home_page.css';
import { useDispatch, useSelector } from "react-redux";
import { addItem, updateQuantity } from "@/app/Auth/cartSlice";
import { fetchProducts } from "@/app/Auth/productSlice";
import { AppDispatch, RootState } from "@/app/Auth/store";
import { FeaturedProducts } from "../FeaturedProducts";
import { Products } from "@/app/types/api";

type User = { name: string } | null;
type Product = {
  name: string;
  category: string;
  price: number;
  rating: number;
  date: string;
  sku: string;
};

const mockSuggestions = [
  "Microscope",
  "Centrifuge",
  "Pipette P200",
  "Reagent Set A",
  "Glass Beaker",
  "Analytical Balance",
  "Microcentrifuge Tubes",
  "Petri Dishes",
  "Spectrophotometer",
  "PCR Machine",
];

const tableData: Product[] = [
  { name: "High-Precision Pipette P200", category: "Pipettes", price: 129, rating: 4.7, date: "2025-07-01", sku: "PIP-P200" },
  { name: "Analytical Balance 0.1mg", category: "Balances", price: 899, rating: 4.5, date: "2025-06-22", sku: "BAL-01MG" },
  { name: "Compound Microscope X-200", category: "Microscopes", price: 1299, rating: 4.8, date: "2025-06-14", sku: "MIC-X200" },
  { name: "Centrifuge MiniSpin", category: "Centrifuges", price: 499, rating: 4.4, date: "2025-06-30", sku: "CEN-MINI" },
  { name: "Chemical Reagent Set A", category: "Reagents", price: 79, rating: 4.6, date: "2025-07-08", sku: "CR-SET-A" },
  { name: "Glass Beaker Set", category: "Glassware", price: 39, rating: 4.3, date: "2025-07-10", sku: "GBE-SET" },
];




export default function LandingPage() {

  const dispatch = useDispatch<AppDispatch>();
  // 

  // ---- User state ----
  const [user, setUser] = useState<User>(null);

  // ---- Cart state ----
  const [cartCount, setCartCount] = useState<number>(0);

  // ---- Search state ----
  const [query, setQuery] = useState("");
  const [suggestions, setSuggestions] = useState<string[]>([]);

  // ---- Table state ----
  const [sortKey, setSortKey] = useState<keyof Product>("name");
  const [sortDir, setSortDir] = useState<"asc" | "desc">("asc");
  const [filter, setFilter] = useState("");
  const [page, setPage] = useState(1);
  const pageSize = 6;

  // const canvasRef = useRef<HTMLCanvasElement | null>(null);
  // const [loading, setLoading] = useState(true);

  // ---- Load user/cart from storage ----
  useEffect(() => {
    const savedUser = localStorage.getItem("user");
    if (savedUser) setUser(JSON.parse(savedUser));

    const savedCart = localStorage.getItem("cartCount") || "0";
    setCartCount(parseInt(savedCart, 10));

    // Welcome modal once
    if (!sessionStorage.getItem("welcome_shown")) {
      Swal.fire({
        title: "Welcome to LabEquip",
        text: "Browse featured categories and products. Create a B2B account for institutional pricing.",
        icon: "info",
        confirmButtonText: "Got it",
        confirmButtonColor: "#000080",
      });
      sessionStorage.setItem("welcome_shown", "1");
    }

    // Chart render
    const timer = setTimeout(() => {
      const ctx = document.getElementById("visitsChart") as HTMLCanvasElement;
      if (ctx) {
        new Chart(ctx, {
          type: "line",
          data: {
            labels: ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"],
            datasets: [
              {
                label: "Visits",
                data: [120, 180, 150, 220, 260, 240, 300],
                borderColor: "#000080",
                backgroundColor: "rgba(0,0,128,0.1)",
                fill: true,
              },
            ],
          },
        });
      }
    }, 800);
    return () => clearTimeout(timer);
  }, []);

  // ---- Cart functions ----
  const handleAddToCart = (id: string) => {
    const count = cartCount + 1;
    setCartCount(count);
    localStorage.setItem("cartCount", String(count));

    Swal.fire({
      icon: "success",
      title: "Added to cart",
      text: `Product ${id} was added to your cart.`,
      timer: 1200,
      showConfirmButton: false,
    });
  };

  const handleLogout = () => {
    setUser(null);
    localStorage.removeItem("user");
    Swal.fire({
      icon: "success",
      title: "Logged out",
      timer: 1200,
      showConfirmButton: false,
    });
  };

  // ---- Search logic ----
  useEffect(() => {
    if (!query.trim()) {
      setSuggestions([]);
      return;
    }
    const results = mockSuggestions.filter(s =>
      s.toLowerCase().includes(query.toLowerCase())
    ).slice(0, 6);
    setSuggestions(results);
  }, [query]);

  // ---- Table logic ----
  const filtered = tableData.filter(r =>
    r.name.toLowerCase().includes(filter.toLowerCase())
  );
  const sorted = filtered.sort((a, b) => {
    let va: any = a[sortKey];
    let vb: any = b[sortKey];
    if (typeof va === "string") va = va.toLowerCase();
    if (typeof vb === "string") vb = vb.toLowerCase();
    if (va < vb) return sortDir === "asc" ? -1 : 1;
    if (va > vb) return sortDir === "asc" ? 1 : -1;
    return 0;
  });
  const pageData = sorted.slice((page - 1) * pageSize, page * pageSize);

  // Access state from Redux store
  const { items, loading, error } = useSelector(
    (state: RootState) => state.products
  );

  //const dispatch = useDispatch();
  //const { product, loading, error } = useSelector((state: any) => state.product);

  useEffect(() => {
    if (items.length === 0) {
      dispatch(fetchProducts({ page: 0, size: 5, sortBy: "productName", sortDir: "ASC" }));
    }
  }, [dispatch]);

  if (loading) return <p>Loading product...</p>;
  // if (error) return <p>Error: {typeof error === "string" ? error : "An error occurred."}</p>;
  if (!items) return <p>No product found</p>;

  const handleAddToCart1 = (product: Products) => {
    console.log('Adding to cart:', product.productName);
    // TODO: Integrate with cart API
  };

  const handleViewProduct = (product: Products) => {
    console.log('Viewing product:', product.productName);
    // TODO: Navigate to product detail page
  };

  return (


    <main style={{ marginTop: "-80px" }} className="pb-5" id="main">

      {/* <div aria-atomic="true" aria-live="polite" className="x-toast-stack" id="toastStack">
      </div> */}
      {/* <!-- Hero Section (Bootstrap Carousel) --> */}
      <section className="pt-0">
        <div aria-label="Promotional Carousel" className="carousel slide" data-bs-ride="carousel" id="heroCarousel">
          <div className="carousel-indicators">
            <button aria-current="true" aria-label="Slide 1" className="active" data-bs-slide-to="0"
              data-bs-target="#heroCarousel" type="button">
            </button>
            <button aria-label="Slide 2" data-bs-slide-to="1" data-bs-target="#heroCarousel" type="button">
            </button>
            <button aria-label="Slide 3" data-bs-slide-to="2" data-bs-target="#heroCarousel" type="button">
            </button>
          </div>
          <div className="carousel-inner">
            <div className="carousel-item active">
              <img alt="Lab equipment promotion slide 1" className="d-block w-100 object-fit-cover"

                // src="assets/hero_image_for_research_and_di_1_96344ba9.jpg" 
                src="assets/landing.png"
              />
              <div className="carousel-caption text-start hero-caption">
                {/* <h1 className="display-5 fw-bold">
                  Equip Your Discovery
                </h1> */}
                {/* <p className="lead">
                  Microscopes, centrifuges, pipettes, reagents and more—engineered for precision.
                </p> */}
                {/* <a className="btn x-btn x-btn--primary btn-lg" href="./product_listing.html">
                  Shop All Products
                </a> */}
              </div>
            </div>
            <div className="carousel-item">
              <img alt="Lab equipment promotion slide 2" className="d-block w-100 object-fit-cover"

                src="assets/landing1.png" />
              <div className="carousel-caption text-start hero-caption">
                {/* <h2 className="fw-bold">
                  Trusted by B2B &amp; B2C Scientists
                </h2>
                <p className="mb-3">
                  Institutional pricing tiers, quotes, and self‑service account tools.
                </p>
                <a className="btn x-btn x-btn--secondary btn-lg" href="./register_page.html">
                  Create an Account
                </a> */}
              </div>
            </div>
            <div className="carousel-item">
              <img alt="Lab equipment promotion slide 3" className="d-block w-100 object-fit-cover"

                src="assets/landing2.png" />
              <div className="carousel-caption text-start hero-caption">
                {/* <h2 className="fw-bold">
                  Bulk Orders &amp; Quotes
                </h2>
                <p className="mb-3">
                  Request quotes and manage approvals for your lab with ease.
                </p>
                <a className="btn x-btn x-btn--primary btn-lg" href="./quote_history.html">
                  View Quotes
                </a> */}
              </div>
            </div>
          </div>
          <button className="carousel-control-prev" data-bs-slide="prev" data-bs-target="#heroCarousel" type="button">
            <span aria-hidden="true" className="carousel-control-prev-icon">
            </span>
            <span className="visually-hidden">
              Previous
            </span>
          </button>
          <button className="carousel-control-next" data-bs-slide="next" data-bs-target="#heroCarousel" type="button">
            <span aria-hidden="true" className="carousel-control-next-icon">
            </span>
            <span className="visually-hidden">
              Next
            </span>
          </button>
        </div>
      </section>
      {/* <!-- Featured Categories --> */}
      <section className="l-container my-5">
        <div className="d-flex justify-content-between align-items-center mb-3">
          <h3 className="mb-0">
            Featured Categories
          </h3>
          <a className="x-btn x-btn--link" href="./product_listing.html">
            Browse All
          </a>
        </div>
        <div className="row g-4">
          <div className="col-6 col-md-4 col-lg-3">
            <a aria-label="Browse Pipettes &amp; Tips" className="card category-card h-100 text-reset"
              href="./product_listing.html?category=Pipettes">
              <img alt="Pipettes &amp; Tips" className="card-img-top"

                src="assets/category_image_for_pipettes_an_1_f12d964e.jpg" />
              <div className="card-body">
                <h5 className="card-title mb-0">
                  Pipettes &amp; Tips
                </h5>
              </div>
            </a>
          </div>
          <div className="col-6 col-md-4 col-lg-3">
            <a aria-label="Browse Lab Glassware" className="card category-card h-100 text-reset"
              href="./product_listing.html?category=Glassware">
              <img alt="Lab Glassware" className="card-img-top"

                src="assets/category_image_for_lab_glasswa_1_9587d348.jpg" />
              <div className="card-body">
                <h5 className="card-title mb-0">
                  Lab Glassware
                </h5>
              </div>
            </a>
          </div>
          <div className="col-6 col-md-4 col-lg-3">
            <a aria-label="Browse Microscopes" className="card category-card h-100 text-reset"
              href="./product_listing.html?category=Microscopes">
              <img alt="Microscopes" className="card-img-top"
                src="assets/hero_image_for_research_and_di_4_fb27ef2f.jpg" />
              <div className="card-body">
                <h5 className="card-title mb-0">
                  Microscopes
                </h5>
              </div>
            </a>
          </div>
          <div className="col-6 col-md-4 col-lg-3">
            <a aria-label="Browse Reagents" className="card category-card h-100 text-reset"
              href="./product_listing.html?category=Reagents">
              <img alt="Chemical Reagents" className="card-img-top"

                src="assets/hero_image_for_research_and_di_5_c03f0c35.png" />
              <div className="card-body">
                <h5 className="card-title mb-0">
                  Chemical Reagents
                </h5>
              </div>
            </a>
          </div>

        </div>
      </section>
      {/* <!-- Featured Products (Carousel multi-card) --> */}


      <section className="l-container my-5">
        <div className="d-flex justify-content-between align-items-center mb-3">
          <h3 className="mb-0">
            Featured Products
          </h3>
          <a className="x-btn x-btn--link" href="./product_listing.html">
            View More
          </a>
        </div>

        <div className="carousel slide" data-bs-ride="carousel" id="featuredProductsCarousel">
          <div className="carousel-inner">
            <div className="carousel-item active">
              <div className="row g-4">
                {loading ? <p>Loading product...</p> :
                  error ? <p>Error: {typeof error === "string" ? error : "An error occurred."}</p> :
                  !items ? <p>No product found</p> : null
                }
                {items.map((product: any) => (
                  <div key={product.productId} className="col-12 col-md-4">
                  <FeaturedProducts key={product.productId} 
                    product={product}
                    onAddToCart={() => handleAddToCart1(product)}
                    onViewProduct={handleViewProduct}
                  />
                  </div>
                  // <li key={product.productId} className="border p-2 my-1">
                  //   {product.productName} — {product.vendorName}
                  // </li>
                  // <div key={product.productId} className="col-12 col-md-4">
                  //   <div className="card product-card h-100">
                  //     <a className="text-reset" href="./product_details.html?sku=CR-SET-A">
                  //       <img alt="Chemical Reagent Set A" className="card-img-top"

                  //         src="assets/featured_product_image_for_a_c_1_e61758df.jpg" />

                  //     </a>

                  //     <div className="card-body d-flex flex-column">
                  //       <h5 className="card-title mb-1">

                  //         {product.productName}
                  //       </h5>
                  //       <p className="text-muted mb-2">

                  //         {product.category}
                  //       </p>
                  //       <div className="mt-auto d-flex justify-content-between align-items-center">
                  //         <span className="fw-bold">

                  //           ₹ {product.individualProductPrice}
                  //         </span>
                  //         <button className=" x-btn x-btn--primary btn-sm add-to-cart"
                  //           data-product-id="CR-SET-A"
                  //           onClick={() =>
                  //             dispatch(
                  //               addItem({
                  //                 id: `${product.productId}`,
                  //                 quantity: product.productQuantity,
                  //                 name: `${product.productName}`,
                  //                 sku: `${product.status}`,
                  //                 price: product.individualProductPrice,
                  //                 stock: product.productQuantity <= 0 ? 0 : 10,
                  //                 outOfStock: product.productQuantity >= 0 ? true : false,
                  //                 image: `${product.imagePath}`
                  //               })
                  //             )
                  //           }
                  //         >
                  //           <i className="fa-solid fa-cart-plus me-1">
                  //           </i>
                  //           Add
                  //         </button>
                  //       </div>
                  //     </div>
                  //   </div>
                  // </div>
                ))}
              </div>
            </div>
            {/* <div className="carousel-item active">
              <div className="row g-4">
                
              </div>
            </div> */}
            {/* <!-- More carousel-item divs for additional slides --> */}
          </div>
          <button
            className="carousel-control-prev"
            type="button"
            data-bs-target="#featuredProductsCarousel"
            data-bs-slide="prev"
          >
            <span className="carousel-control-prev-icon" aria-hidden="true"></span>
            <span className="visually-hidden">Previous</span>
          </button>
          <button
            className="carousel-control-next"
            type="button"
            data-bs-target="#featuredProductsCarousel"
            data-bs-slide="next"
          >
            <span className="carousel-control-next-icon" aria-hidden="true"></span>
            <span className="visually-hidden">Next</span>
          </button>
        </div>
      </section>
      {/* <!-- Insights (Chart.js) --> */}
      <section className="l-container my-5">
        <div className="row g-4 align-items-stretch">
          <div className="col-12 col-lg-7">
            <div className="x-card h-100">
              <div className="x-card__header">
                <div className="d-flex align-items-center gap-2">
                  <i className="fa-solid fa-chart-line text-primary">
                  </i>
                  <h5 className="mb-0">
                    Weekly Visits
                  </h5>
                </div>
                <span className="x-badge">
                  <i className="fa-regular fa-clock">
                  </i>
                  <small className="ms-1">
                    Last 7 days
                  </small>
                </span>
              </div>


              {/* Chart */}
              <div className="x-card__body">
                <canvas aria-label="Line chart showing weekly visits" id="visitsChart" height={280}></canvas>
              </div>
            </div>
          </div>
          <div className="col-12 col-lg-5">
            <div className="c-cta h-100">
              <h5 className="mb-2">
                Save with Institutional Pricing
              </h5>
              <p className="mb-3 text-muted">
                Are you purchasing for a university, hospital, or biotech company? Register as a B2B
                customer to unlock tiered pricing and quotes.
              </p>
              <div className="">
                <a className="x-btn x-btn--primary" href="./register_page.html">
                  <i className="fa-solid fa-user-plus me-1">
                  </i>
                  Register B2B
                </a>
                &nbsp;
                <a className="x-btn x-btn--secondary" href="./login_page.html">
                  <i className="fa-solid fa-right-to-bracket me-1">
                  </i>
                  Login
                </a>
              </div>
            </div>
          </div>
        </div>
      </section>
      {/* <!-- Popular Products (Table with filtering/sorting) --> */}
      <section className="l-container my-5">
        <div className="d-flex justify-content-between align-items-center mb-3">
          <h3 className="mb-0">
            Popular Products
          </h3>
          <div className="d-flex gap-2 table-controls">
            <input aria-label="Filter products by name" className="form-control" id="tableSearch"
              placeholder="Filter by name..." type="text" />
            <select aria-label="Filter by category" className="form-select" id="categoryFilter">
              <option value="">
                All Categories
              </option>
              <option value="Pipettes">
                Pipettes
              </option>
              <option value="Microscopes">
                Microscopes
              </option>
              <option value="Centrifuges">
                Centrifuges
              </option>
              <option value="Reagents">
                Reagents
              </option>
              <option value="Glassware">
                Glassware
              </option>
              <option value="Balances">
                Balances
              </option>
            </select>
          </div>
        </div>
        <div className="x-table overflow-auto">
          <table className="table mb-0" id="popularTable">
            <thead>
              <tr>
                <th className="sortable" data-sort="name" scope="col">
                  Product
                  <i className="fa-solid fa-sort ms-1">
                  </i>
                </th>
                <th className="sortable" data-sort="category" scope="col">
                  Category
                  <i className="fa-solid fa-sort ms-1">
                  </i>
                </th>
                <th className="sortable text-end" data-sort="price" scope="col">
                  Price
                  <i className="fa-solid fa-sort ms-1">
                  </i>
                </th>
                <th className="sortable text-center" data-sort="rating" scope="col">
                  Rating
                  <i className="fa-solid fa-sort ms-1">
                  </i>
                </th>
                <th className="sortable" data-sort="date" scope="col">
                  Added Date
                </th>
                <th className="text-end" scope="col">
                  Action
                </th>
              </tr>
            </thead>
            <tbody>
              {/* <!-- Rows populated by JS --> */}
              {pageData.map((r, i) => (
                <tr key={i} >
                  <td className="sortable" data-sort="name" scope="col">
                    {r.name}
                  </td>
                  <td className="sortable" data-sort="category" scope="col">
                    {r.category}
                  </td>
                  <td className="sortable text-end" data-sort="price" scope="col">
                    ${r.price.toFixed(2)}
                  </td>
                  <td className="sortable text-center" data-sort="rating" scope="col">
                    {r.rating.toFixed(1)} ⭐
                  </td>
                  <td className="sortable" data-sort="date" scope="col">
                    {new Date(r.date).toLocaleDateString("en-GB", {
                      year: "numeric",
                      month: "2-digit",
                      day: "2-digit",
                    })}
                  </td>
                  <td className="text-end " scope="col">

                    <button
                      onClick={() => handleAddToCart(r.sku)}
                      className="x-btn x-btn--secondary btn-sm "
                    >
                      <i className="fa-solid fa-cart-plus me-1">
                      </i>
                      View
                    </button>
                    &nbsp;
                    &nbsp;
                    <button
                      onClick={() => handleAddToCart(r.sku)}
                      className="x-btn x-btn--primary btn-sm "
                    >
                      <i className="fa-solid fa-cart-plus me-1">
                      </i>
                      Add
                    </button>
                  </td>
                </tr>
              ))}

            </tbody>
          </table>
        </div>
        <div className="d-flex justify-content-between align-items-center mt-2">
          <small className="text-muted" id="tableInfo">
            Showing 0 to 0 of 0 entries
          </small>
          <nav aria-label="Table pagination">
            <ul className="pagination pagination-sm mb-0" id="tablePagination">
              {/* <!-- Pagination populated by JS --> */}
            </ul>
          </nav>
        </div>
      </section>
      
    </main>
  )
}


