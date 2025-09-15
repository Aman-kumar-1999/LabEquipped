// import { NextResponse } from "next/server";
// import type { NextRequest } from "next/server";
// import jwt from "jsonwebtoken";

// export function middleware(req: NextRequest) {
//   let token = localStorage.getItem("jwtToken");
//   if (!token) {
//     const cookieToken = cookieStore.get("token");
//     if (cookieToken instanceof Promise) {
//       // If cookieStore.get returns a Promise, await it
//       return cookieToken.then((cookie) => {
//         token = cookie?.value || "";
//         if (!token) {
//           return NextResponse.redirect(new URL("/login", req.url));
//         }
//         try {
//           const decoded: any = jwt.verify(token, process.env.JWT_SECRET!);

//           const path = req.nextUrl.pathname;

//           //Protect /admin routes
//           if (path.startsWith("/admin") && !decoded.user.roles[0].name.includes("SUPER_ADMIN")) {
//             return NextResponse.redirect(new URL("/unauthorized", req.url));
//           }

//           //Protect /super routes
//           if (path.startsWith("/super") && !decoded.user.roles[0].name.includes("SUPER_ADMIN")) {
//             return NextResponse.redirect(new URL("/unauthorized", req.url));
//           }

//           return NextResponse.next();
//         } catch (err) {
//           return NextResponse.redirect(new URL("/login", req.url));
//         }
//       });
//     } else {
//       token = (cookieToken as any)?.value || "";
//     }
//   }

//   if (!token) {
//     return NextResponse.redirect(new URL("/login", req.url));
//   }

//   try {
//     const decoded: any = jwt.verify(token, process.env.JWT_SECRET!);

//     const path = req.nextUrl.pathname;

//     // Protect /admin routes
//     // if (path.startsWith("/admin") && !decoded.user.roles[0].name.includes("SUPER_ADMIN")) {
//     //   return NextResponse.redirect(new URL("/unauthorized", req.url));
//     // }

//     // Protect /super routes
//     // if (path.startsWith("/super") && !decoded.user.roles[0].name.includes("SUPER_ADMIN")) {
//     //   return NextResponse.redirect(new URL("/unauthorized", req.url));
//     // }

//     return NextResponse.next();
//   } catch (err) {
//     return NextResponse.redirect(new URL("/login", req.url));
//   }
// }

// export const config = {
//   matcher: ["/admin/:path*", "/super/:path*"], // protect these routes
// };
