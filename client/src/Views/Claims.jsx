import { useState, useEffect } from "react";
import axios from "axios";
import {Link} from "react-router-dom";

export default function Claims() {
    // Filters
    const [claimNumber, setClaimNumber] = useState("");
    const [providerName, setProviderName] = useState("");
    const [statuses, setStatuses] = useState([]);
    const [startDate, setStartDate] = useState("");
    const [endDate, setEndDate] = useState("");

    // Pagination
    const [page, setPage] = useState(0);
    const [pageSize, setPageSize] = useState(10);
    const [totalPages, setTotalPages] = useState(0);

    // Data
    const [claims, setClaims] = useState([]);
    const [loading, setLoading] = useState(false);

    // Status options
    const statusOptions = ["PAID", "IN_REVIEW", "PROCESSED", "DENIED"];

    const fetchClaims = async () => {
        setLoading(true);
        const token = localStorage.getItem("id_token");
        try {
            const params = {
                claimNumber: claimNumber || undefined,
                providerName: providerName || undefined,
                statuses: statuses.length ? statuses : undefined,
                startDate: startDate || undefined,
                endDate: endDate || undefined,
                page,
                size: pageSize,
                sort: "receivedDate,desc",
            };


            const res = await axios.get("http://localhost:8080/api/claims", {
                params,
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            });
            setClaims(res.data.content);
            setTotalPages(res.data.totalPages);
        } catch (err) {
            console.error(err);
            setClaims([]);
            setTotalPages(0);
        } finally {
            setLoading(false);
        }
    };

    // Fetch when filters or pagination change
    useEffect(() => {
        fetchClaims();
    }, [page, pageSize]);

    const handleFilterSubmit = (e) => {
        e.preventDefault();
        setPage(0); // Reset to first page when filters change
        fetchClaims();
    };

    return (
        <div className="p-6">
            <h1 className="text-2xl font-bold mb-4">Claims</h1>

            {/* Filters */}
            <form
                className="flex flex-wrap gap-4 mb-4 items-end"
                onSubmit={handleFilterSubmit}
            >
                <div>
                    <label className="block text-sm font-medium">Claim #</label>
                    <input
                        type="text"
                        value={claimNumber}
                        onChange={(e) => setClaimNumber(e.target.value)}
                        className="border rounded px-2 py-1"
                    />
                </div>

                <div>
                    <label className="block text-sm font-medium">Provider</label>
                    <input
                        type="text"
                        value={providerName}
                        onChange={(e) => setProviderName(e.target.value)}
                        className="border rounded px-2 py-1"
                    />
                </div>

                <div>
                    <label className="block text-sm font-medium">Status</label>
                    <select
                        multiple
                        value={statuses}
                        onChange={(e) =>
                            setStatuses(
                                Array.from(e.target.selectedOptions, (option) => option.value)
                            )
                        }
                        className="border rounded px-2 py-1 h-24"
                    >
                        {statusOptions.map((s) => (
                            <option key={s} value={s}>
                                {s}
                            </option>
                        ))}
                    </select>
                </div>

                <div>
                    <label className="block text-sm font-medium">Start Date</label>
                    <input
                        type="date"
                        value={startDate}
                        onChange={(e) => setStartDate(e.target.value)}
                        className="border rounded px-2 py-1"
                    />
                </div>

                <div>
                    <label className="block text-sm font-medium">End Date</label>
                    <input
                        type="date"
                        value={endDate}
                        onChange={(e) => setEndDate(e.target.value)}
                        className="border rounded px-2 py-1"
                    />
                </div>

                <button
                    type="submit"
                    className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600"
                >
                    Apply
                </button>
            </form>

            {/* Table */}
            <div className="overflow-x-auto">
                <table className="min-w-full border border-gray-200">
                    <thead className="bg-gray-100">
                    <tr>
                        <th className="border px-4 py-2 text-left">Claim #</th>
                        <th className="border px-4 py-2 text-left">Service Dates</th>
                        <th className="border px-4 py-2 text-left">Provider</th>
                        <th className="border px-4 py-2 text-left">Status</th>
                        <th className="border px-4 py-2 text-left">Member Responsibility</th>
                    </tr>
                    </thead>
                    <tbody>
                    {loading ? (
                        <tr>
                            <td colSpan={5} className="text-center p-4">
                                Loading...
                            </td>
                        </tr>
                    ) : claims.length === 0 ? (
                        <tr>
                            <td colSpan={5} className="text-center p-4">
                                No claims found
                            </td>
                        </tr>
                    ) : (
                        claims.map((c) => (
                            <tr
                                key={c.claimNumber}
                                className="hover:bg-gray-50 cursor-pointer"
                            >
                                <Link
                                    key={c.claimNumber}
                                    to={`/claims/${c.claimNumber}`}
                                    className="block p-4 border rounded hover:bg-gray-50"
                                >
                                <td className="border px-4 py-2">{c.claimNumber}</td>
                                </Link>
                                <td className="border px-4 py-2">
                                    {c.serviceStartDate} - {c.serviceEndDate}
                                </td>
                                <td className="border px-4 py-2">{c.providerName}</td>
                                <td className="border px-4 py-2">{c.status}</td>
                                <td className="border px-4 py-2">${c.memberResponsibility}</td>

                            </tr>
                        ))
                    )}
                    </tbody>
                </table>
            </div>

            {/* Pagination */}
            <div className="flex justify-between items-center mt-4">
                <div>
                    <label>
                        Page Size:{" "}
                        <select
                            value={pageSize}
                            onChange={(e) => setPageSize(Number(e.target.value))}
                            className="border rounded px-2 py-1"
                        >
                            <option value={10}>10</option>
                            <option value={25}>25</option>
                        </select>
                    </label>
                </div>

                <div className="flex gap-2">
                    <button
                        disabled={page === 0}
                        onClick={() => setPage(page - 1)}
                        className="px-3 py-1 border rounded disabled:opacity-50"
                    >
                        Previous
                    </button>
                    <span>
            Page {page + 1} of {totalPages}
          </span>
                    <button
                        disabled={page + 1 >= totalPages}
                        onClick={() => setPage(page + 1)}
                        className="px-3 py-1 border rounded disabled:opacity-50"
                    >
                        Next
                    </button>
                </div>
            </div>
        </div>
    );
}
