import axios from "axios";

export async function getUserFromToken() {
    const token = localStorage.getItem("id_token")
    if(!token) return null
    console.log(token)

    try {
        const response = await axios.get("http://localhost:8080/api/auth/me", {
            headers: {
                "Authorization": `Bearer ${token}`
            }
        });
        return response.data
    } catch(err) {
            console.log("Cant find user: ", err)
            return null
    }



}