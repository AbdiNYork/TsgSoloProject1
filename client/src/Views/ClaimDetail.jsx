import {useState, useEffect} from "react";
import {getUserFromToken} from "../utils/auth.jsx";


const ClaimDetail = () => {
    const [user, setUser] = useState(null)


    useEffect(() => {
        getUserFromToken().then((data) => setUser(data))
    }, []);

    if(!user) return null

    return (
        <h2>Claim details will be here for {user.email}</h2>
    )
}

export default ClaimDetail