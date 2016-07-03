/*
 * Stephen Yang
 * RestAPIRelay Test Module (Scripts)
 */


$.ajax({
    url: 'http://localhost:8080/relay',
    type: "GET",
    data: {
        url: "http://jsonplaceholder.typicode.com/posts"
    },
    success: function (data) {
        document.write('<pre>' + JSON.stringify(JSON.parse(data), null, 2) + '</pre>');
    }
});