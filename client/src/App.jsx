import LoginButton from "./Views/LoginButton.jsx";
import {Router, Routes, Route, BrowserRouter} from "react-router-dom";
import Layout from "./components/Layout.jsx";
import Navbar from "./components/Navbar.jsx";
import PrivateRoute from "./components/PrivateRoute.jsx";
import Dashboard from "./Views/Dashboard.jsx";
import Claims from "./Views/Claims.jsx";
import ClaimDetail from "./Views/ClaimDetail.jsx";
import LandingPage from "./Views/LandingPage.jsx";


const App = () => {
    return (



            <Routes>
                {/* Public login route */}
                <Route path="/" element={<LandingPage />} />

                {/* Protected routes (require auth + show navbar) */}
                <Route element={<PrivateRoute />}>
                    <Route element={<Layout />}>
                        <Route path="/dashboard" element={<Dashboard />} />
                        <Route path="/claims" element={<Claims />} />
                        <Route path="/claims/:id" element={<ClaimDetail />} />
                    </Route>
                </Route>
            </Routes>





    )
}

export default App