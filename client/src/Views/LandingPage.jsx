import React from "react";
import LoginButton from "./LoginButton"; // Your existing Google auth button

const LandingPage = () => {
    return (
        <div className="relative min-h-screen bg-gradient-to-br from-indigo-600 via-purple-600 to-pink-500 flex items-center justify-center px-6">
            {/* Background pattern */}
            <div className="absolute inset-0 overflow-hidden">
                <svg
                    className="absolute top-0 left-0 w-full h-full opacity-20"
                    xmlns="http://www.w3.org/2000/svg"
                    viewBox="0 0 1440 320"
                >
                    <path
                        fill="#ffffff"
                        fillOpacity="0.1"
                        d="M0,160L60,138.7C120,117,240,75,360,74.7C480,75,600,117,720,128C840,139,960,117,1080,106.7C1200,96,1320,96,1380,96L1440,96L1440,320L1380,320C1320,320,1200,320,1080,320C960,320,840,320,720,320C600,320,480,320,360,320C240,320,120,320,60,320L0,320Z"
                    ></path>
                </svg>
            </div>

            {/* Content card */}
            <div className="relative z-10 max-w-4xl w-full bg-white/90 backdrop-blur-lg rounded-3xl shadow-2xl p-10 flex flex-col md:flex-row items-center gap-10">
                {/* Left side - text */}
                <div className="md:w-1/2 text-center md:text-left">
                    <h1 className="text-4xl font-extrabold text-gray-800 mb-4 leading-tight">
                        Welcome to <span className="text-indigo-600">MemberBenefits</span>
                    </h1>
                    <p className="text-gray-600 mb-6 text-lg">
                        Manage your health claims, view your plan, and access exclusive
                        benefits. Sign in securely to get started.
                    </p>
                    <div className="flex justify-center md:justify-start">
                        <LoginButton />
                    </div>
                </div>

                {/* Right side - image */}
                <div className="md:w-1/2 flex justify-center">
                    <img
                        src="https://illustrations.popsy.co/white/login.svg"
                        alt="Member illustration"
                        className="w-full max-w-sm"
                    />
                </div>
            </div>
        </div>
    );
};

export default LandingPage;
