import Dashboard from "../Views/Dashboard.jsx";
const PlanCard = ({plan}) => {
    return (
        <div className="bg-white p-5 rounded shadow">
            <h3 className="text-lg font-semibold mb-3">Active Plan</h3>
            <ul className="space-y-1 text-gray-700">
                <li>• {plan.name}</li>
                <li>• Network: {plan.networkName}</li>
                <li>• Coverage {plan.planYear}</li>
            </ul>
        </div>
    )
}

export default PlanCard