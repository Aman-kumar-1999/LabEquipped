"use client";

import { useSelector } from "react-redux";
import { RootState } from "../../Auth/store";
import { useRouter } from "next/navigation";
import { useEffect } from "react";

interface ProtectedRouteProps {
  allowedRoles: string[];
  children: React.ReactNode;
}

export default function ProtectedRoute({
  allowedRoles,
  children,
}: ProtectedRouteProps) {
  const router = useRouter();
  const { token, user } = useSelector((state: RootState) => state.auth);

  useEffect(() => {
    if (!token || !user) {
      router.push("/login"); // not logged in
    } else {
      console.log("User Roles:", user.roles[0].name);
      console.log("Allowed Roles:", allowedRoles);
      // Check if user has at least one of the allowed roles
      const hasAccess = user.roles.some((role) =>
        allowedRoles.includes(role.name)

      );
      // if (user.roles[0].name !== allowedRoles[0]) {
      //   router.push("/unauthorized"); // create a 403 page
      // }
      console.log("Has Access:", hasAccess);
      if (!hasAccess) {
        router.push("/unauthorized"); // create a 403 page
      }
    }
  }, [token, user, router, allowedRoles]);

  return <>{children}</>;
}
