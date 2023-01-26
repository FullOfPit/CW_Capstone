import Menu from "../components/Menu";

type risk = {
    id: string,
    projectId: string,
    riskName: string,
    riskDescription: string,
    riskReductionMeasures: string[],
    healthHazard: number,
    probability: number,
    frequency: number
}

export default function RiskDetails() {


    return (

        <div className={"ScreenLimit"}>

            <Menu/>

            <h4>Risk Detail Page</h4>
            <div>
                <h4>Risk factor</h4>
                <input placeholder={"Risk Factor Name"}></input>
                <input placeholder={"Risk Factor description"}></input>
            </div>
            <div>
                <h4>Risk Reduction measures</h4>
                <input placeholder={"Please enter specific details on how the above mentioned risk factor will be "}></input>
            </div>
        </div>


    );
}