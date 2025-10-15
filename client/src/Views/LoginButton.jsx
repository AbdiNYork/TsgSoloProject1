import {GoogleLogin} from "@react-oauth/google";
import axios from "axios"
import {useNavigate} from "react-router-dom";
const LoginButton = () => {

    const navigate = useNavigate()
    const handleSuccess = async (credResponse) => {
        const idToken = credResponse.credential;
        localStorage.setItem("id_token", idToken)

        // send to backend
        try {
            const response = await axios.get("http://localhost:8080/api/auth/me", {
                headers: {
                    Authorization: `Bearer ${idToken}`,
                }
            })
            const user = response.data
            navigate('/dashboard', {state: {user}})
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
        </div>


    )
}

export default LoginButton