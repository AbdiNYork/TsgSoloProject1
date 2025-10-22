
const AccumulatorCard = ({accumulators}) => {
    return <div className="bg-white p-4 rounded shadow w-full">
        <h2 className="text-lg font-semibold text-gray-800 mb-4">Accumulators</h2>

        {accumulators
            .filter(acc => acc.tier === 'IN_NETWORK')
            .map((acc, index) => {
                const progress = acc.usedAmount / acc.limitAmount;
                const percentage = Math.min(progress * 100, 100).toFixed(0);

                return (
                    <div key={index} className="mb-4">
                        <div className="flex justify-between text-sm text-gray-600 mb-1">
                            <span>{acc.type.replace('_', ' ')}</span>
                            <span>${acc.usedAmount} / ${acc.limitAmount}</span>
                        </div>
                        <div className="bg-gray-200 h-2 rounded">
                            <div
                                className={`h-2 rounded ${
                                    progress > 1 ? 'bg-red-500' : 'bg-blue-500'
                                }`}
                                style={{ width: `${percentage}%` }}
                            />
                        </div>
                    </div>
                );
            })}
    </div>



}

export default AccumulatorCard