"use client";

import { useSelector, useDispatch } from "react-redux";
import { RootState } from "../Auth/store";
import {
    removeItem,
    updateQuantity,
    setRole,
    clearCart,
} from "../Auth/cartSlice";
import Link from "next/link";

export default function CartPage() {
    const { items, role } = useSelector((state: RootState) => state.cart);
    const dispatch = useDispatch();

    const subtotal = items.reduce((acc, item) => acc + item.price * item.quantity, 0);
    const tax = subtotal * 0.08;
    const shipping = subtotal > 0 ? 15 : 0;
    const total = subtotal + tax + shipping;

    return (
        <main className="py-4">
            <div className="l-container">
                {/* Breadcrumb */}
                <nav aria-label="breadcrumb" className="mb-3">
                    <ol className="breadcrumb mb-0">
                        <li className="breadcrumb-item">
                            <Link href="/">Home</Link>
                        </li>
                        <li className="breadcrumb-item active">Shopping Cart</li>
                    </ol>
                </nav>

                <div className="c-steps mb-4">
                    <div aria-current="step" className="c-step is-active">
                        <span className="c-step__dot">
                        </span>
                        Cart
                    </div>
                    <div className="c-step">
                        <span className="c-step__dot">
                        </span>
                        Shipping
                    </div>
                    <div className="c-step">
                        <span className="c-step__dot">
                        </span>
                        Payment
                    </div>
                    <div className="c-step">
                        <span className="c-step__dot">
                        </span>
                        Review
                    </div>
                </div>

                {/* Role Selection */}
                {/* <div className="d-flex align-items-center gap-3 mb-3">
                    <span className="fw-bold">Your Role:</span>
                    <select
                        value={role}
                        onChange={(e) =>
                            dispatch(setRole(e.target.value as "B2B" | "B2C"))
                        }
                    >
                        <option value="B2B">B2B Customer</option>
                        <option value="B2C">B2C Customer</option>
                    </select>
                </div> */}

                <div className="x-toolbar mb-3">
                    <div className="d-flex align-items-center gap-2">
                        <span className="fw-bold">
                            Your role:
                        </span>
                        <span className="x-badge" id="roleBadge">
                            <i className="fa-solid fa-building me-1">
                            </i>
                            <span className="role-text">
                                {role} Customer
                            </span>
                        </span>
                        <div className="vr mx-2">
                        </div>
                        <label className="x-label mb-0" htmlFor="roleSelect">
                            Simulate role
                        </label>
                        <select
                            className="form-select form-select-sm w-auto" id="roleSelect"
                            value={role}
                            onChange={(e) =>
                                dispatch(setRole(e.target.value as "B2B" | "B2C"))
                            }
                        >
                            <option value="B2B">B2B Customer</option>
                            <option value="B2C">B2C Customer</option>
                        </select>
                    </div>
                    <div className="d-flex align-items-center gap-2">
                        <div className="x-search">
                            <i className="fa-solid fa-magnifying-glass text-muted">
                            </i>
                            <input aria-label="Filter cart items" id="filterInput" placeholder="Filter items..."
                                type="search" />
                        </div>
                        <select aria-label="Sort cart items" className="form-select form-select-sm w-auto" id="sortSelect">
                            <option value="default">
                                Sort: Default
                            </option>
                            <option value="name-asc">
                                Name A→Z
                            </option>
                            <option value="price-asc">
                                Price Low→High
                            </option>
                            <option value="price-desc">
                                Price High→Low
                            </option>
                        </select>
                    </div>
                </div>

                <div className="row g-4">
                    {/* Cart Items */}
                    <div className="col-12 col-lg-8">
                        {items.length === 0 ? (

                            
                            <section className="x-card">
                                <div className="x-card__body l-center text-center">
                                    <img alt="Empty cart illustration" className=""
                                       
                                        src="assets/illustration_for_empty_cart_st_3_fc0b92e0.png" style={{ width: '63%', height: 'auto' }} />
                                    <h3 className="h5">
                                        Your cart is empty
                                    </h3>
                                    <p className="text-muted">
                                        Looks like you haven’t added anything yet. Explore our catalog and find the right tools
                                        for your lab.
                                    </p>
                                    <Link className="x-btn x-btn--primary" href="/">
                                        <i className="fa-solid fa-flask-vial me-2">
                                        </i>
                                        Continue Shopping
                                    </Link>
                                </div>
                            </section>
                        ) : (
                            <div className="x-card h-100">
                                <header className="x-card__header">
                                    <div className="d-flex align-items-center gap-2">
                                        <h2 className="h5 mb-0">
                                            Your Cart
                                        </h2>
                                        <span className="text-muted" id="itemsCountLabel">
                                            ({items.length} items)
                                        </span>
                                    </div>
                                    <div className="text-muted small">
                                        Prices shown reflect your pricing tier
                                    </div>
                                </header>
                                <div className="x-card__body">
                                    <div className="vstack gap-3" id="cartList">
                                        {items.map((item, index) => (
                                            <article className="cart-item border rounded p-3"
                                                key={index}
                                                data-id="SKU-PIP-1000"
                                                data-name="Precision Pipette 1-10mL" data-out="false" data-price="129.00"
                                                data-stock="12">
                                                <div className="row g-3 align-items-center">
                                                    <div className="col-3 col-md-2">
                                                        <img alt="Precision Pipette image" className="img-fluid rounded cart-thumb"
                                                           
                                                            // src={item.image} 
                                                            
                                                            />
                                                    </div>
                                                    <div className="col-9 col-md-5">
                                                        <h3 className="h6 mb-1">
                                                            {item.name}

                                                        </h3>
                                                        <div className="text-muted small">
                                                            SKU: {item.sku}
                                                        </div>
                                                        <div className="mt-1">
                                                            <span className="badge rounded-pill bg-success">
                                                                <i className="fa-solid fa-circle-check me-1">
                                                                </i>
                                                                {item.outOfStock ? (
                                                                    <span className="badge bg-danger">Out of Stock</span>
                                                                ) : (
                                                                    <span className="badge bg-success">In Stock</span>
                                                                )}
                                                            </span>
                                                        </div>
                                                    </div>
                                                    <div className="col-12 col-md-2">
                                                        <div className="unit-price fw-bold" data-currency="USD">
                                                            $
                                                            {item.price.toFixed(2)}
                                                        </div>
                                                        <div className="text-muted small">
                                                            Unit price
                                                        </div>
                                                    </div>
                                                    <div className="col-12 col-md-2">
                                                        <div
                                                            className="d-flex align-items-center justify-content-md-end gap-2 qty-control">
                                                            <button aria-label="Decrease quantity"
                                                                className="btn btn-outline-secondary btn-sm qty-decrement"
                                                                onClick={() =>
                                                                    dispatch(
                                                                        updateQuantity({
                                                                            id: item.id,
                                                                            quantity: Math.max(item.quantity - 1, 1),
                                                                        })
                                                                    )
                                                                }>
                                                                <i className="fa-solid fa-minus">
                                                                </i>
                                                            </button>
                                                            <input
                                                                type="number"
                                                                value={item.quantity}
                                                                min={1}
                                                                onChange={(e) =>
                                                                    dispatch(
                                                                        updateQuantity({
                                                                            id: item.id,
                                                                            quantity: parseInt(e.target.value),
                                                                        })
                                                                    )
                                                                }
                                                                className="form-control form-control-sm text-center"
                                                                style={{ width: "50px" }}
                                                            />
                                                            <button aria-label="Increase quantity"
                                                                className="btn btn-outline-secondary btn-sm qty-increment"
                                                                onClick={() =>
                                                                    dispatch(
                                                                        updateQuantity({
                                                                            id: item.id,
                                                                            quantity: item.quantity + 1,
                                                                        })
                                                                    )
                                                                }>
                                                                <i className="fa-solid fa-plus">
                                                                </i>
                                                            </button>
                                                        </div>
                                                        <div className="text-end mt-2">
                                                            <button className="btn btn-link text-danger p-0 remove-item"
                                                                onClick={() => dispatch(removeItem(item.id))}
                                                            >
                                                                <i className="fa-regular fa-trash-can me-1">
                                                                </i>
                                                                Remove
                                                            </button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </article>
                                        ))}
                                    </div>
                                </div>
                            </div>
                        )}
                    </div>

                    {/* Order Summary */}
                    <div className="col-12 col-lg-4">
                        <aside className="x-card summary-sticky">
                            <header className="x-card__header">
                                <h2 className="h5 mb-0">
                                    Order Summary
                                </h2>
                            </header>
                            <div className="x-card__body">
                                <div className="c-totals">
                                    <div className="c-totals__row">
                                        <span>
                                            Subtotal
                                        </span>
                                        <span id="subtotalValue">
                                            ${subtotal.toFixed(2)}
                                        </span>
                                    </div>
                                    <div className="c-totals__row">
                                        <span>
                                            Estimated tax
                                        </span>
                                        <span id="taxValue">
                                            ${tax.toFixed(2)}
                                        </span>
                                    </div>
                                    <div className="c-totals__row">
                                        <span>
                                            Shipping
                                        </span>
                                        <span id="shippingValue">
                                            ${shipping.toFixed(2)}
                                        </span>
                                    </div>
                                    <hr className="hr" />
                                    <div className="c-totals__row c-totals__grand">
                                        <span>
                                            Total
                                        </span>
                                        <span id="totalValue">
                                            ${total.toFixed(2)}
                                        </span>
                                    </div>
                                </div>
                                <div className="mt-3 small text-muted">
                                    Taxes and shipping are estimates. Final amounts are calculated at checkout.
                                </div>
                            </div>
                            <footer className="x-card__footer">
                                <div className="d-grid gap-2">
                                    <button className="x-btn x-btn--primary" id="checkoutBtn">
                                        <i className="fa-solid fa-lock me-2">
                                        </i>
                                        Proceed to Checkout
                                    </button>

                                    {role === "B2B" && (
                                        <button id="quoteBtn"

                                            className="btn btn-secondary"
                                            onClick={() => {
                                                alert("Quote request submitted!");
                                                dispatch(clearCart());
                                            }}>
                                            <i className="fa-regular fa-file-lines me-2">
                                            </i>
                                            Request a Quote
                                        </button>
                                    )}


                                    <Link className="x-btn x-btn--link" href="./product_listing.html"
                                        id="continueShopping">
                                        <i className="fa-solid fa-arrow-left me-2">
                                        </i>
                                        Continue Shopping
                                    </Link>
                                </div>
                                <div className="mt-3 small text-danger d-none" id="validationNote">
                                    <i className="fa-solid fa-triangle-exclamation me-1">
                                    </i>
                                    Some items are out of stock or exceed available quantity. Please adjust your cart to
                                    proceed to checkout.
                                </div>
                            </footer>
                        </aside>

                    </div>
                </div>
            </div>
        </main>
    );
}
