// import {useState, useEffect} from "react";
// import {getUserFromToken} from "../utils/auth.jsx";
//
//
// const ClaimDetail = () => {
//     const [user, setUser] = useState(null)
//
//
//     useEffect(() => {
//         getUserFromToken().then((data) => setUser(data))
//     }, []);
//
//     if(!user) return null
//
//
//     return (
//         <div className="p-6">
//             <h1 className="text-3xl font-bold mb-6">Claim Details</h1>
//
//             <div className="grid grid-cols-1 md:grid-cols-2 gap-6 bg-white p-6 shadow rounded-lg">
//                 <div>
//                     <h2 className="text-lg font-semibold mb-1">Claim Submitted By</h2>
//                     <p className="text-gray-700">{user.name} {user.lastName}</p>
//                     <p className="text-gray-500">{user.email}</p>
//                 </div>
//                 <div>
//                     <h2 className="text-lg font-semibold mb-1">Date of Service</h2>
//                     <p className="text-gray-700">September 10, 2025</p>
//                 </div>
//                 <div>
//                     <h2 className="text-lg font-semibold mb-1">Amount Requested</h2>
//                     <p className="text-gray-700">$120.00</p>
//                 </div>
//                 <div>
//                     <h2 className="text-lg font-semibold mb-1">Status</h2>
//                     <span className="inline-block px-3 py-1 bg-red-100 text-red-800 rounded-full text-sm">
//             Denied
//           </span>
//                 </div>
//                 <div className="col-span-1 md:col-span-2">
//                     <h2 className="text-lg font-semibold mb-1">Notes</h2>
//                     <p className="text-gray-600">
//                         This claim was denied due to incomplete documentation.
//                     </p>
//                 </div>
//             </div>
//         </div>
//     )
// }
//

import React from 'react';

const claimDetailMock = {
    claimNumber: 'C-10421',
    providerName: 'River Clinic',
    serviceStartDate: '2025-08-29',
    serviceEndDate: '2025-08-29',
    status: 'Processed',
    statusHistory: [
        {
            status: 'Submitted',
            occurredAt: '2025-08-30T09:00:00Z',
        },
        {
            status: 'In Review',
            occurredAt: '2025-08-31T12:00:00Z',
        },
        {
            status: 'Processed',
            occurredAt: '2025-09-01T15:30:00Z',
        },
        {
            status: 'Paid',
            occurredAt: '2025-09-02T10:00:00Z',
        },
    ],
    financialSummary: {
        totalBilled: 300.0,
        totalAllowed: 200.0,
        planPaid: 155.0,
        memberResponsibility: 45.0,
    },
    lineItems: [
        {
            lineNumber: 1,
            cptCode: '99213',
            description: 'Office Visit, Est Pt',
            billedAmount: 150.0,
            allowedAmount: 100.0,
            deductibleApplied: 0.0,
            copayApplied: 25.0,
            coinsuranceApplied: 10.0,
            planPaid: 15.0,
            memberResponsibility: 15.0,
        },
        {
            lineNumber: 2,
            cptCode: '81002',
            description: 'Urinalysis',
            billedAmount: 150.0,
            allowedAmount: 100.0,
            deductibleApplied: 0.0,
            copayApplied: 0.0,
            coinsuranceApplied: 10.0,
            planPaid: 90.0,
            memberResponsibility: 10.0,
        },
    ],
    hasEOB: true,
};

function ClaimDetail({ claimNumber, onBack }) {
    const data = claimDetailMock;

    // Format dates
    const formatDate = (date) => new Date(date).toLocaleDateString();
    const formatDateTime = (dt) => new Date(dt).toLocaleString();
    const formatCurrency = (val) => `$${val?.toFixed(2) || '—'}`;

    return (
        <div className="p-6 max-w-5xl mx-auto space-y-6">
            <button
                onClick={onBack}
                className="text-blue-600 underline hover:text-blue-800"
            >
                &larr; Back to Claims
            </button>

            <h2 className="text-2xl font-bold">Claim Detail #{data.claimNumber}</h2>

            <section className="bg-white p-4 rounded shadow space-y-2">
                <h3 className="font-semibold text-lg mb-2">Basic Info</h3>
                <p>
                    <strong>Provider:</strong> {data.providerName}
                </p>
                <p>
                    <strong>Service Dates:</strong> {formatDate(data.serviceStartDate)} -{' '}
                    {formatDate(data.serviceEndDate)}
                </p>
                <p>
                    <strong>Status:</strong> {data.status}
                </p>
            </section>

            <section className="bg-white p-4 rounded shadow space-y-2">
                <h3 className="font-semibold text-lg mb-2">Status History</h3>
                <ul className="list-disc pl-5 space-y-1 text-gray-700">
                    {data.statusHistory.map(({ status, occurredAt }) => (
                        <li key={occurredAt}>
                            <span className="font-semibold">{status}</span> -{' '}
                            <time dateTime={occurredAt}>{formatDateTime(occurredAt)}</time>
                        </li>
                    ))}
                </ul>
            </section>

            <section className="bg-white p-4 rounded shadow">
                <h3 className="font-semibold text-lg mb-2">Financial Summary</h3>
                <table className="w-full text-left border-collapse">
                    <tbody>
                    <tr>
                        <td className="py-1 px-2 font-semibold">Total Billed</td>
                        <td className="py-1 px-2">{formatCurrency(data.financialSummary.totalBilled)}</td>
                    </tr>
                    <tr className="bg-gray-50">
                        <td className="py-1 px-2 font-semibold">Total Allowed</td>
                        <td className="py-1 px-2">{formatCurrency(data.financialSummary.totalAllowed)}</td>
                    </tr>
                    <tr>
                        <td className="py-1 px-2 font-semibold">Plan Paid</td>
                        <td className="py-1 px-2">{formatCurrency(data.financialSummary.planPaid)}</td>
                    </tr>
                    <tr className="bg-gray-50">
                        <td className="py-1 px-2 font-semibold">Member Responsibility</td>
                        <td className="py-1 px-2">{formatCurrency(data.financialSummary.memberResponsibility)}</td>
                    </tr>
                    </tbody>
                </table>
            </section>

            <section className="bg-white p-4 rounded shadow">
                <h3 className="font-semibold text-lg mb-2">Line Items</h3>
                <table className="w-full text-left border-collapse border">
                    <thead className="bg-gray-100">
                    <tr>
                        <th className="px-2 py-1 border">#</th>
                        <th className="px-2 py-1 border">CPT Code</th>
                        <th className="px-2 py-1 border">Description</th>
                        <th className="px-2 py-1 border">Billed</th>
                        <th className="px-2 py-1 border">Allowed</th>
                        <th className="px-2 py-1 border">Deductible</th>
                        <th className="px-2 py-1 border">Copay</th>
                        <th className="px-2 py-1 border">Coinsurance</th>
                        <th className="px-2 py-1 border">Plan Paid</th>
                        <th className="px-2 py-1 border">Member Resp.</th>
                    </tr>
                    </thead>
                    <tbody>
                    {data.lineItems.map((line) => (
                        <tr key={line.lineNumber} className="even:bg-gray-50">
                            <td className="border px-2 py-1 text-center">{line.lineNumber}</td>
                            <td className="border px-2 py-1">{line.cptCode}</td>
                            <td className="border px-2 py-1">{line.description}</td>
                            <td className="border px-2 py-1 text-right">{formatCurrency(line.billedAmount)}</td>
                            <td className="border px-2 py-1 text-right">{formatCurrency(line.allowedAmount)}</td>
                            <td className="border px-2 py-1 text-right">{formatCurrency(line.deductibleApplied)}</td>
                            <td className="border px-2 py-1 text-right">{formatCurrency(line.copayApplied)}</td>
                            <td className="border px-2 py-1 text-right">{formatCurrency(line.coinsuranceApplied)}</td>
                            <td className="border px-2 py-1 text-right">{formatCurrency(line.planPaid)}</td>
                            <td className="border px-2 py-1 text-right">{formatCurrency(line.memberResponsibility)}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </section>

            {data.hasEOB && (
                <div>
                    <a
                        href="#"
                        className="text-blue-600 underline hover:text-blue-800"
                        download={`EOB-${data.claimNumber}.pdf`}
                    >
                        Download EOB PDF
                    </a>
                </div>
            )}
        </div>
    );
}


export default ClaimDetail