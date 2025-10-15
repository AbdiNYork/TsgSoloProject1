import {Link, useNavigate} from "react-router-dom";

const Navbar = () => {
    const navigate = useNavigate()
    const handleLogout = () => {
        localStorage.removeItem('token')
        navigate('/')
    }

    return (
        <>
            <Link to='/dashboard'>Dashboard</Link>
            <Link to='/claims'>Claims</Link>
            <Link to='/claimDetail'>Claim details</Link>
            <button onClick={handleLogout}>Logout</button>
            <h1>Nav</h1>
        </>
    )
}

export default Navbar