type Risk = {
    id: string,
    projectId: string,
    riskName: string,
    riskDescription: string,
    riskReductionMeasures: string,
    healthHazard: number,
    probability: number,
    frequency: number
}

export default Risk;