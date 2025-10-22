import {useParams, useNavigate, data} from 'react-router-dom';
import { useEffect, useState } from 'react';
import {getClaimDetail} from "../utils/ClaimDetailsApi.jsx";

const ClaimDetail = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const [claim, setClaim] = useState(null);

   useEffect(() => {
       if(id) {
            getClaimDetail(id).then((data) => setClaim(data))
           console.log(data)
       }
   },[id])


    if (!claim) return <div className="p-4">Loading claim...</div>;

    return (
        <div className="max-w-6xl mx-auto p-6 space-y-6 text-sm text-gray-800">
            {/* Header */}
            <div className="grid grid-cols-3 gap-4 border-b pb-2">
                <div className="font-bold text-lg">Claim #{claim.claimNumber}</div>
                <div>Provider: <span className="font-medium">{claim.providerName || '—'}</span></div>
                <div>Service: {claim.serviceStartDate} – {claim.serviceEndDate}</div>
            </div>

            {/* Status & Timeline */}
            <div className="flex flex-col gap-2">
                <div>Status: <span className="font-medium">{claim.status}</span></div>
                <div className="flex items-center space-x-2 text-xs text-gray-500">
                    {["SUBMITTED", "IN_REVIEW", "PROCESSED", "PAID"].map((step, i) => (
                        <div key={step} className="flex items-center space-x-1">
              <span className={`px-2 py-0.5 rounded-full border ${claim.status === step ? 'bg-blue-500 text-white' : ''}`}>
                {step.replace("_", " ")}
              </span>
                            {i < 3 && <span>—</span>}
                        </div>
                    ))}
                </div>
            </div>

            {/* Financial Summary */}
            <div>
                <h2 className="font-semibold border-b mb-2 pb-1">Financial Summary</h2>
                <div className="grid grid-cols-2 sm:grid-cols-4 gap-4 text-sm">
                    <div>• Total Billed: <span className="font-medium">${claim.totalBilled.toFixed(2)}</span></div>
                    <div>• Allowed Amount: <span className="font-medium">${claim.totalAllowed.toFixed(2)}</span></div>
                    <div>• Plan Paid: <span className="font-medium">${claim.totalPlanPaid.toFixed(2)}</span></div>
                    <div>• Member Responsibility: <span className="font-medium">${claim.totalMemberResponsibility.toFixed(2)}</span></div>
                </div>
            </div>

            {/* Line Items Table */}
            <div>
                <h2 className="font-semibold border-b mb-2 pb-1">Line Items</h2>
                <div className="overflow-x-auto">
                    <table className="min-w-full text-sm border border-gray-200">
                        <thead className="bg-gray-100 text-left">
                        <tr>
                            <th className="px-3 py-2 border">CPT</th>
                            <th className="px-3 py-2 border">Description</th>
                            <th className="px-3 py-2 border">Billed</th>
                            <th className="px-3 py-2 border">Allowed</th>
                            <th className="px-3 py-2 border">Ded</th>
                            <th className="px-3 py-2 border">Copay</th>
                            <th className="px-3 py-2 border">Coins</th>
                            <th className="px-3 py-2 border">You</th>
                        </tr>
                        </thead>
                        <tbody>
                        {claim.claimLines.map((line, idx) => (
                            <tr key={idx} className="border-t">
                                <td className="px-3 py-1 border">{line.cptCode}</td>
                                <td className="px-3 py-1 border">{line.description}</td>
                                <td className="px-3 py-1 border">${line.billedAmount.toFixed(2)}</td>
                                <td className="px-3 py-1 border">${line.allowedAmount.toFixed(2)}</td>
                                <td className="px-3 py-1 border">${line.deductibleApplied.toFixed(2)}</td>
                                <td className="px-3 py-1 border">${line.copayApplied.toFixed(2)}</td>
                                <td className="px-3 py-1 border">${line.coinsuranceApplied.toFixed(2)}</td>
                                <td className="px-3 py-1 border">${line.memberResponsibility.toFixed(2)}</td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>
            </div>

            {/* Footer Buttons */}
            <div className="flex justify-between pt-4 border-t">
                <button
                    onClick={() => navigate(-1)}
                    className="text-blue-600 underline hover:text-blue-800"
                >
                    ← Back to Claims
                </button>
                <button className="bg-blue-600 text-white px-4 py-1.5 rounded hover:bg-blue-700">
                    Download EOB PDF
                </button>
            </div>
        </div>
    );
};

export default ClaimDetail;
