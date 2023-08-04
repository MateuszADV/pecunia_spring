console.log("TEST PRZESYłania zmiennych");
// const name = document.getElementById('test');
// console.log(name);
const val = document.getElementById('reportName').getAttribute('value');
// console.log(val);

const chartUrl = "http://localhost:8080/api/v1/report/?report=" + val;

fetch(chartUrl)
    .then(function(response) {
        response.json().then(data => {
           console.log(data);
        })
    });