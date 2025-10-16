import {Router} from "react-router-dom";
import {Outlet} from "react-router-dom";
import Navbar from "./Navbar.jsx";
const Layout = () => {

    return (
        <div className="flex">
            <Navbar />
            <main className="flex-1">
                <Outlet />
            </main>
        </div>



    )
}

export default Layout