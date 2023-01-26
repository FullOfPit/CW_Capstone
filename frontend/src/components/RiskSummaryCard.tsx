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
        <div>
            <div>
                <h4>Such Danger, Much Risk, Wow!</h4>
                <button>Edit</button>
                <button>Delete</button>
            </div>
            <div>
                <h4>{id}</h4>
                <h4>{projectId}</h4>
                <h4>{riskName}</h4>
                <h4>{healthHazard}</h4>
                <h4>{probability}</h4>
                <h4>{frequency}</h4>
            </div>


        </div>

    )
}