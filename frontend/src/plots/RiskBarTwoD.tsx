import React from 'react';
import Plot from 'react-plotly.js';

class RiskBarTwoD extends React.Component {
    render() {
        return (
            <Plot
                data={[
                    {
                        x: [1, 2, 3],
                        y: [2, 6, 3],
                        type: 'scatter',
                        mode: 'lines+markers',
                        marker: {color: 'red'},
                    },
                    {type: 'bar', x: [1, 2, 3], y: [2, 5, 3]},
                ]}
                layout={{title: 'A Fancy Plot'}}
                style={{width: "45%", height: "35rem", overflowX: "scroll"}}
            />
        );
    }
}

export default RiskBarTwoD;