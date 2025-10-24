import {Link} from "react-router-dom";

const ClaimsCard  = ({claims}) => {
   return <div className="bg-white p-5 rounded shadow flex flex-col justify-between">
        <div>
            <h3 className="text-lg font-semibold mb-3">Recent Claims</h3>
            <ul className="space-y-2 text-gray-700">
                {claims.slice(0, 5).map(c => (
                    <Link
                    key={c.claimNumber}
                    to={`/claims/${c.claimNumber}`}
                    className="block p-4 border rounded hover:bg-gray-50"
                    >

                    <li key={c.claimNumber} className="flex justify-between border-b pb-1">
                        <span>#{c.claimNumber} {c.status}</span>
                        <span>${c.totalMemberResponsibility || '—'}</span>
                    </li>

                    </Link>
                ))}
            </ul>
        </div>

        <div className="mt-4">
            <Link to={"/claims"}>
            <button className="text-sm text-blue-600 hover:underline">
                View All Claims
            </button>
            </Link>
        </div>
    </div>

}
export default ClaimsCard