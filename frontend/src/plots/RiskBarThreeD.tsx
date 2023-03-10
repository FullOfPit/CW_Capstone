import React from 'react';
import Plot from 'react-plotly.js';
import Risk from "../types/Risk";
import {Data} from "plotly.js";
import riskFactorEvaluation from "../evaluation/riskFactorEvaluation";

export default function RiskBarThreeD ({risks}: {risks: Risk[]}) {

    let dataList: Data[] = risks.map((risk) => {return ({
        type: "scatter3d",
        y: [risk.probability],
        z: [risk.frequency],
        x: [risk.healthHazard],
        name: risk.riskName.slice(0, 35),
        marker: {
            color: riskFactorEvaluation(risk.healthHazard, risk.probability, risk.frequency).riskColor.toString()
        }
    })})

    return (
            <Plot
                data={dataList}
                layout={{
                    title:"Risk Factor Component Analysis",
                    autosize: true,
                    width: 800,
                    scene:
                        {
                            xaxis: {title : "Hazard to Health"},
                            yaxis: {title : "Probability"},
                            zaxis: {title : "Frequency"}
                        }}}
                style={{width: "55%", height: "35rem", overflowY: "scroll"}}
            />
        );
}
