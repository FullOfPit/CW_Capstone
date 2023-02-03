import React from 'react';
import Plot from 'react-plotly.js';
import Risk from "../types/Risk";
import {Data} from "plotly.js";
import riskFactorEval from "../evaluation/riskFactorEval";

export default function RiskBarThreeD ({risks}: {risks: Risk[]}) {

    let dataList: Data[] = risks.map((risk) => {return ({
        type: "scatter3d",
        x: [risk.probability],
        y: [risk.frequency],
        z: [risk.healthHazard],
        name: risk.riskName.slice(0, 35),
        marker: {
            color: riskFactorEval(risk.healthHazard, risk.probability, risk.frequency).riskColor.toString()
        }
    })})

    return (
            <Plot
                data={dataList}
                layout={{
                    autosize: true,
                    width: 800,
                    scene:
                        {
                            xaxis: {title : "Probability"},
                            yaxis: {title : "Frequency"},
                            zaxis: {title : "Hazard to Health"}
                        }}}
                style={{width: "45%", height: "35rem", overflowX: "scroll"}}
            />
        );
}
