import axios from "axios";

export async function getMemberDash() {
    const token = localStorage.getItem("id_token")
    if(!token) return null

    try {
        const response = await axios.get("http://localhost:8080/api/dashboard", {
            headers: {
                "Authorization": `Bearer ${token}`
            }
        });
        console.log(response.data)
        return response.data
    } catch(err) {
        console.log("Cant get member data: ", err)
        return null
    }



}