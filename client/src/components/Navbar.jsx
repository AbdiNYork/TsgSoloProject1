import {Link, useNavigate} from "react-router-dom";

const Navbar = () => {
    const navigate = useNavigate()
    const handleLogout = () => {
        localStorage.removeItem('token')
        navigate('/')
    }

    return (
        // <header>
        //     <nav>
        //
        //     <Link to='/dashboard'>Dashboard</Link>
        //     <Link to='/claims'>Claims</Link>
        //     <Link to='/claimDetail'>Claim details</Link>
        //     <button onClick={handleLogout}>Logout</button>
        //
        //     </nav>
        //     <h1>Nav</h1>
        // </header>
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
                        to="/claimDetail"
                        className="text-gray-700 hover:bg-gray-100 px-4 py-2 rounded transition"
                    >
                        ClaimDetail
                    </Link>
                </nav>
            </div>

            <div className="p-6 border-t">
                <button
                    onClick={handleLogout}
                    className="w-full bg-red-500 hover:bg-red-600 text-white py-2 px-4 rounded transition"
                >
                    Sign Out
                </button>
            </div>
        </div>
    )
}

export default Navbar