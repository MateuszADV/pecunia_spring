

var ctx = document.getElementById('myChart').getContext('2d');
var chartData = document.getElementById('chart').getAttribute('value');
// var chartData = [[${chartData}]];
var labels = chartData.chart.labels;
var datasets  = chartData.chart.datasets;
var options = chartData.chart.options;
console.log(chartData)
new Chart(ctx, {
    type: 'bar',
    data: {
        labels: labels,
        datasets: [datasets]
    },
    options: options
});