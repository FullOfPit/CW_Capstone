import "./RiskSummaryCard.css"

export default function RiskSummaryCard({
    id,
    projectId,
    riskName,
    healthHazard,
    probability,
    frequency
}:{
    id: string,
    projectId: string,
    riskName: string,
    healthHazard: number,
    probability: number,
    frequency: number
}) {
    return (
        <div className={"RiskSumCard"}>
            <div className={"RiskSumCardName"}>
                <h4>{riskName}</h4>
            </div>
            <div className={"RiskFactor"}>
                <h4>Risk Factor</h4>

            </div>
            <div className={"RiskMetrics"}>
                <p>{`Hazard to Health: ${healthHazard}`}</p>
                <p>{`Probability: ${probability}`}</p>
                <p>{`Frequency: ${frequency}`}</p>
            </div>


        </div>

    )
}