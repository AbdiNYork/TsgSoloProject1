import React, { useEffect, useState } from "react";
import {getMemberDash} from "../utils/dashboardApi.jsx";

const Dashboard = () => {
    const [user, setUser] = useState(null);

    useEffect(() => {
        getMemberDash().then((data) => setUser(data));
    }, []);
    if (!user) return <h2>Loading...</h2>;
    console.log(user)
    return (
        <div className="min-h-screen bg-gray-100 p-8">
            <div className="max-w-6xl mx-auto">
                <h1 className="text-4xl font-bold text-indigo-700 mb-8">
                    Welcome, {user.name} 👋
                </h1>

                <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
                    <div className="bg-white p-6 rounded-xl shadow hover:shadow-md transition">
                        <h2 className="text-lg font-semibold text-indigo-600">Name</h2>
                        <p className="text-gray-700 mt-2">
                            {user.name}
                        </p>
                    </div>

                    <div className="bg-white p-6 rounded-xl shadow hover:shadow-md transition">
                        <h2 className="text-lg font-semibold text-indigo-600">Email</h2>
                        <p className="text-gray-700 mt-2">{user.email}</p>
                    </div>

                    <div className="bg-white p-6 rounded-xl shadow hover:shadow-md transition">
                        <h2 className="text-lg font-semibold text-indigo-600">Membership</h2>
                        <p className="text-gray-700 mt-2">Active since Jan 2023</p>
                    </div>
                </div>

                <div className="mt-10 grid grid-cols-1 md:grid-cols-2 gap-6">
                    <div className="bg-indigo-100 p-6 rounded-xl shadow">
                        <h3 className="text-xl font-semibold text-indigo-700 mb-2">
                            Recent Activity
                        </h3>
                        <ul className="list-disc list-inside text-gray-700">
                            <li>Submitted claim for Dental</li>
                            <li>Updated contact info</li>
                            <li>Viewed plan details</li>
                        </ul>
                    </div>

                    <div className="bg-indigo-100 p-6 rounded-xl shadow">
                        <h3 className="text-xl font-semibold text-indigo-700 mb-2">
                            Recommendations
                        </h3>
                        <p className="text-gray-700">
                            You have unused wellness benefits. Consider booking a preventive checkup.
                        </p>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Dashboard;


