import React, { useEffect, useState } from "react";
import {getMemberDash} from "../utils/dashboardApi.jsx";
import ClaimsCard from "../components/ClaimsCard.jsx";
import PlanCard from "../components/PlanCard.jsx";
import AccumulatorCard from "../components/AccumulatorCard.jsx";
const Dashboard = () => {
    const [data, setData] = useState(null);

    useEffect(() => {
        getMemberDash().then((data) => setData(data));
    }, []);
    if (!data) return <h2>Loading...</h2>;

    return (
        <div className="p-6 grid grid-cols-1 md:grid-cols-3 gap-6">
            <PlanCard plan={data.plan} />
            <AccumulatorCard accumulators={data.accumulators} />
            <ClaimsCard claims={data.claims} />
        </div>
    );
};

export default Dashboard;
