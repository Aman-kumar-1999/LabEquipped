

import { Metadata } from "next";
import Link from "next/link";

export const metadata: Metadata = {
  title: "403 Unauthorized",
};

const UnauthorizedPage = () => {
  return (
    <>
      <h1 style={{ color: "#8394" }}>403 Unauthorized</h1>
      <section className="error-container">
        <span className="four">
          <span className="screen-reader-text">4</span>
        </span>
        <span className="zero">
          <span className="screen-reader-text">0</span>
        </span>
        <span className="three">
          <span className="screen-reader-text">3</span>
        </span>
      </section>
      <p style={{ textAlign: "center", marginTop: "1rem", color: "#555" }}>
        Sorry, you don’t have permission to access this page.
      </p>
      <div className="link-container">
        <Link href={"/"} className="more-link">
          Go Back Home
        </Link>
      </div>
    </>
  );
};

export default UnauthorizedPage;
