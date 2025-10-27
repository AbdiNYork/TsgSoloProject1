
import React from "react";
import LoginButton from "./LoginButton"; // Your existing Google auth button

const LandingPage = () => {
    return (
        <div className="relative min-h-screen bg-white flex items-center justify-center px-6">
            {/* Content card */}
            <div className="max-w-4xl w-full bg-neutral-900 rounded-3xl shadow-2xl p-10 flex flex-col md:flex-row items-center gap-10">
                {/* Left side - text */}
                <div className="md:w-1/2 text-center md:text-left">
                    <h1 className="text-4xl font-extrabold text-white mb-4 leading-tight">
                        Welcome to{" "}
                        <span className="text-purple-500">MemberBenefits</span>
                    </h1>
                    <p className="text-gray-300 mb-6 text-lg">
                        Manage your health claims, view your plan, and access exclusive
                        benefits. Sign in securely to get started.
                    </p>
                    <div className="flex justify-center md:justify-start">
                        <LoginButton />
                    </div>
                </div>
            </div>
        </div>
    );
};

export default LandingPage;

