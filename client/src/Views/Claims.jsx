import {useState, useEffect} from "react";
import {getUserFromToken} from "../utils/auth.jsx";

const Claims = () => {

    const [user, setUser] = useState(null)

    useEffect(() => {
        getUserFromToken().then((data) => setUser(data))
    }, []);

    const claim = {
        type: "Chiropractor Visit",
        dateOfService: "September 10, 2025",
        amount: "$120.00",
        status: "Denied",
        reason:
            "This claim was denied due to incomplete documentation. Please resubmit with itemized bill.",
    };

    const statusColor = {
        Approved: "bg-green-500",
        Pending: "bg-yellow-500",
        Denied: "bg-red-500",
    };

    if(!user) return null

    return (
        <div className="min-h-screen bg-gradient-to-br from-indigo-100 via-white to-indigo-200 p-8">
            <div className="max-w-6xl mx-auto">
                <h1 className="text-4xl font-extrabold text-indigo-700 mb-8">
                    Claim Details
                </h1>

                <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
                    {/* Claim Info Card */}
                    <div className="md:col-span-2 bg-white shadow-xl rounded-xl p-8 border-l-8 border-indigo-500">
                        <h2 className="text-2xl font-semibold mb-4 text-indigo-700">
                            {claim.type}
                        </h2>
                        <div className="grid grid-cols-1 sm:grid-cols-2 gap-6">
                            <div>
                                <p className="text-gray-500 text-sm uppercase mb-1">
                                    Date of Service
                                </p>
                                <p className="text-lg font-medium">{claim.dateOfService}</p>
                            </div>
                            <div>
                                <p className="text-gray-500 text-sm uppercase mb-1">
                                    Amount Requested
                                </p>
                                <p className="text-lg font-medium">{claim.amount}</p>
                            </div>
                            <div>
                                <p className="text-gray-500 text-sm uppercase mb-1">Status</p>
                                <span
                                    className={`inline-block px-3 py-1 text-sm text-white font-semibold rounded-full ${statusColor[claim.status]}`}
                                >
                  {claim.status}
                </span>
                            </div>
                        </div>

                        <div className="mt-8">
                            <p className="text-gray-500 text-sm uppercase mb-2">Notes</p>
                            <p className="text-gray-700 leading-relaxed">{claim.reason}</p>
                        </div>
                    </div>

                    {/* Member Info Sidebar */}
                    <div className="bg-white shadow-lg rounded-xl p-6 flex flex-col justify-between border-l-4 border-indigo-300">
                        <div>
                            <h3 className="text-xl font-semibold text-indigo-600 mb-4">
                                Member Info
                            </h3>
                            <p className="text-sm text-gray-500">Name</p>
                            <p className="text-lg font-medium">
                                {user.name} {user.lastName}
                            </p>

                            <p className="text-sm text-gray-500 mt-4">Email</p>
                            <p className="text-lg font-medium">{user.email}</p>
                        </div>

                        <div className="mt-6">
                            <button className="w-full bg-indigo-600 hover:bg-indigo-700 text-white py-2 px-4 rounded-lg font-semibold transition duration-200">
                                Contact Support
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Claims