import AdminLeftSidebar from "./left-sidebar";
import '../css/admin_users.css'
import ProtectedRoute from "../components/ProtectedRoute";
import UserSidebar from "./left-sidebar";
// import '../css/admin_dashboard.css'
export default function AdminLayout({
    children,
}: {
    children: React.ReactNode;
}) {

    return (<>
        <main className="mx-4 my-4" id="main">
            <ProtectedRoute allowedRoles={["BUYER"]}>
                <div className="container-fluid">
                    <div className="row g-4 admin-layout">
                        <div className="col-12 col-lg-2">
                            {/* <AdminLeftSidebar /> */}
                            <UserSidebar />
                        </div>
                        <div className="col-12 col-lg-10">
                            {children}
                        </div>
                    </div>

                    {/* <main className="l-container my-4" id="main">
                    <div className="container-fluid">
                        {children}
                    </div>
                </main> */}
                </div>

            </ProtectedRoute>

        </main>

        {/* <main className="l-container my-4" id="main">
            <div className="container-fluid">
                <div className="row g-4 admin-layout">

                    <AdminLeftSidebar />
                    <main className="l-container my-4" id="main">
                        <div className="container-fluid">
                            {children}
                        </div>
                    </main>
                </div>
            </div>
        </main> */}

    </>)
}