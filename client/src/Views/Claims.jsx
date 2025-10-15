import {useState, useEffect} from "react";
import {getUserFromToken} from "../utils/auth.jsx";

const Claims = () => {

    const [user, setUser] = useState(null)

    useEffect(() => {
        getUserFromToken().then((data) => setUser(data))
    }, []);


    if(!user) return null

    return (
        <h2>Showing Claims for {user.name}</h2>
    )
}

export default Claims