import {GoogleLogin} from "@react-oauth/google";
import axios from "axios"
import {useNavigate} from "react-router-dom";
import {useState} from "react";
const LoginButton = () => {

    const [userData, setUserData] = useState([null])
    const navigate = useNavigate()
    const handleSuccess = async (credResponse) => {
        const idToken = credResponse.credential;
        localStorage.setItem("Token", credResponse)

        // send to backend
        try {
            const response = await axios.get("http://localhost:8080/api/auth/me", {
                headers: {
                    Authorization: `Bearer ${idToken}`,
                }
            })
            console.log("User info from backend: ", response.data)
            setUserData(response.data)
            setTimeout(() => {
                navigate('/dashboard')
            },500)
        } catch(error) {
            console.log("Auth failed: ", error)
        }



    }

    const handleError = () => {
            console.log("Login failed!")
    }

    return (
        <div>
            <GoogleLogin onSuccess={handleSuccess} onError={handleError} useOneTap />
            {userData && (
                <div>
                    <h1>Welcome {userData.name}</h1>
                    <p>Email:  {userData.email}</p>
                    <p></p>
                </div>
            )}
        </div>


    )
}

export default LoginButton