
const reportName = document.getElementById('chart').getAttribute('value');
// console.log(val);

const root = location.protocol + '//' + location.host;
console.log(root);
const chartUrl = "http://localhost:8080/api/v1/report/?report=" + reportName;

fetch(chartUrl)
    .then(function(response) {
        response.json().then(data => {
            var ctx = document.getElementById('myChart').getContext('2d');
            // var chartData = [[${chartData}]];
            var chartData = data;
            var labels = chartData.chart.labels;
            var datasets  = chartData.chart.datasets;
            var options = chartData.chart.options;
            console.log("Report Name - " + reportName);
            console.log(chartData)
            new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: labels,
                    datasets: [datasets]
                },
                options: options
            });
        })
    });

