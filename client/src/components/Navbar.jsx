// import {Link, useNavigate} from "react-router-dom";
// import {useEffect, useState} from "react";
// import {getUserFromToken} from "../utils/auth.jsx";
//
// const Navbar = () => {
//     const [user, setUser] = useState(null)
//     const navigate = useNavigate()
//     const handleLogout = () => {
//         localStorage.removeItem('token')
//         navigate('/')
//     }
//
//     useEffect(() => {
//         getUserFromToken().then((data) => setUser(data))
//     }, []);
//
// return <>
//
//         <div className="w-64 h-screen bg-white border-r shadow-sm flex flex-col justify-between">
//             <div className="p-6">
//                 <h2 className="text-2xl font-semibold text-gray-800 mb-8">Claimify</h2>
//
//                 <nav className="flex flex-col space-y-4">
//                     <Link
//                         to="/dashboard"
//                         className="text-gray-700 hover:bg-gray-100 px-4 py-2 rounded transition"
//                     >
//                         Dashboard
//                     </Link>
//                     <Link
//                         to="/claims"
//                         className="text-gray-700 hover:bg-gray-100 px-4 py-2 rounded transition"
//                     >
//                         Claims
//                     </Link>
//                     <Link
//                         to="/claims/C-15342"
//                         className="text-gray-700 hover:bg-gray-100 px-4 py-2 rounded transition"
//                     >
//                         ClaimDetail
//                     </Link>
//                 </nav>
//             </div>
//
//             <div className="p-6 border-t">
//                 <button
//                     onClick={handleLogout}
//                     className="w-full bg-red-500 hover:bg-red-600 active:bg-red-700 text-white font-semibold py-2.5 px-5 rounded-lg shadow-md hover:shadow-lg transition-all duration-200 focus:outline-none focus:ring-2 focus:ring-red-400 focus:ring-offset-2"
//                 >
//                     Sign Out
//                 </button>
//
//             </div>
//         </div>
//     </>
//
// }
//
// export default Navbar
import { Link, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import { getUserFromToken } from "../utils/auth.jsx";

const Navbar = () => {
    const [user, setUser] = useState(null);
    const navigate = useNavigate();

    const handleLogout = () => {
        localStorage.removeItem("token");
        navigate("/");
    };

    useEffect(() => {
        getUserFromToken().then((data) => {
            console.log("Decoded user:", data);
            setUser(data);
        });
    }, []);

    return (
        <div className="w-64 h-screen bg-white border-r shadow-sm flex flex-col justify-between">
            <div className="p-6">
                <h2 className="text-2xl font-semibold text-gray-800 mb-8">Claimify</h2>

                <nav className="flex flex-col space-y-4">
                    <Link
                        to="/dashboard"
                        className="text-gray-700 hover:bg-gray-100 px-4 py-2 rounded transition"
                    >
                        Dashboard
                    </Link>
                    <Link
                        to="/claims"
                        className="text-gray-700 hover:bg-gray-100 px-4 py-2 rounded transition"
                    >
                        Claims
                    </Link>
                    <Link
                        to="/claims/C-15342"
                        className="text-gray-700 hover:bg-gray-100 px-4 py-2 rounded transition"
                    >
                        ClaimDetail
                    </Link>
                </nav>
            </div>

            {/* User section */}
            <div className="p-6 border-t flex items-center justify-between">
                <div className="flex items-center space-x-3">
                    {/* Simple avatar circle */}
                    <div className="w-10 h-10 bg-purple-500 text-white flex items-center justify-center rounded-full font-bold">
                        {(user?.name?.[0]?.toUpperCase()) ?? "?"}
                    </div>
                    <div>
                        <p className="text-sm font-semibold text-gray-800">
                            {user?.name || "Loading..."}
                        </p>
                        <p className="text-xs text-gray-500 truncate max-w-[120px]">
                            {user?.email || ""}
                        </p>
                    </div>
                </div>
            </div>


            {/* Sign out button */}
            <div className="p-6 border-t">
                <button
                    onClick={handleLogout}
                    className="w-full bg-red-500 hover:bg-red-600 active:bg-red-700 text-white font-semibold py-2.5 px-5 rounded-lg shadow-md hover:shadow-lg transition-all duration-200 focus:outline-none focus:ring-2 focus:ring-red-400 focus:ring-offset-2"
                >
                    Sign Out
                </button>
            </div>
        </div>
    );
};

export default Navbar;
