import React, { useEffect, useState } from "react";
import { getUserFromToken } from "../utils/auth";

const Dashboard = () => {
    const [user, setUser] = useState(null);

    useEffect(() => {
        getUserFromToken().then((data) => setUser(data));
    }, []);
    if (!user) return <h2>Loading...</h2>;
    return (
        <div>
            <h2>Welcome to the Dashboard {user.name}  {user.lastName}</h2>
            <p>Logged in as:  {user.email}</p>
        </div>
    );
};

export default Dashboard;
