import {Outlet, Navigate} from "react-router-dom";


const PrivateRoute = () => {
    const isLoggedIn = !!localStorage.getItem('id_token')
    console.log(isLoggedIn)

    return isLoggedIn ? <Outlet /> : <Navigate to="/" />
}

export default PrivateRoute