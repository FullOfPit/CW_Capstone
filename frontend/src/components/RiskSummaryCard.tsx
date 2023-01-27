import "./RiskSummaryCard.css"

export default function RiskSummaryCard({
    projectId,
    riskName,
    healthHazard,
    probability,
    frequency
}:{
    projectId: string,
    riskName: string,
    healthHazard: number,
    probability: number,
    frequency: number
}) {
    return (
        <div className={"RiskSumCard"}>
            <div className={"RiskSumCardName"}>
                <h5>{riskName}</h5>
            </div>
            <div className={"RiskFactor"}>
                <h5>Risk Factor</h5>

            </div>
            <div className={"RiskMetrics"}>
                <p>{`Hazard to Health: ${healthHazard}`}</p>
                <p>{`Probability: ${probability}`}</p>
                <p>{`Frequency: ${frequency}`}</p>
            </div>

        </div>
    )
}