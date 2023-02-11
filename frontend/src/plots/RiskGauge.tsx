import React from 'react';
import Plot from 'react-plotly.js';
import Risk from "../types/Risk";
import riskFactorEvaluation from "../evaluation/riskFactorEvaluation";
import './RiskGauge.css'

export default function RiskGauge({risk}: {risk: Risk}) {

    let riskFactor = riskFactorEvaluation(risk.healthHazard, risk.probability, risk.frequency).riskFactor;

    const riskFactorValue = (factor: string) => {
        switch (factor) {
            case "Extreme Risk":
                return 4.5;
            case "High Risk":
                return 3.5;
            case "Moderate Risk":
                return 2.5;
            case "Low Risk":
                return 1.5;
            case "Negligible Risk":
                return 0.5;
            default:
                return 0;
        }
    }

        return (
            <Plot
                data={[
                    {
                        type: 'indicator',
                        mode: 'gauge',
                        title: {text: "Risk Factor"},
                        value: riskFactorValue(riskFactor),
                        gauge: {
                            axis: {range : [0, 5], tickwidth: 1, tickcolor: "black", visible: false},
                            bar: {color: "black", thickness: 0.05},
                            bgcolor: "white",
                            bordercolor: "gray",
                            steps: [
                                {range: [0, 1], color: "#009e60"},
                                {range: [1, 2], color: "#478778"},
                                {range: [2, 3], color: "#ffc300"},
                                {range: [3, 4], color: "#ff5733"},
                                {range: [4, 5], color: "#c70039"}
                            ],
                            threshold: {
                                line: {color: "black", width: 3},
                                thickness: 0.5,
                                value: riskFactorValue(riskFactor)
                            }
                        }
                    },
                ]}
                layout={{margin: {t:0, r:0, l:0, b:0}}}
                className={"RiskGauge"}
            />
        );
}