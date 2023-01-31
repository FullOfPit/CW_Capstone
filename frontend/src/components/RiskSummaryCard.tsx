import "./RiskSummaryCard.css"
import Risk from "../types/Risk";

export default function RiskSummaryCard({risk}:{risk: Risk})
{
    return (
        <div className={"RiskSumCard"}>
            <div className={"RiskSumCardName"}>
                <h5>{risk.riskName}</h5>
            </div>
            <div className={"RiskFactor"}>
                <h5>Risk Factor</h5>

            </div>
            <div className={"RiskMetrics"}>
                <p>{`Hazard to Health: ${risk.healthHazard}`}</p>
                <p>{`Probability: ${risk.probability}`}</p>
                <p>{`Frequency: ${risk.frequency}`}</p>
            </div>

        </div>
    )
}