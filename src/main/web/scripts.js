/**
 * Created by Stephen on 2016-07-01.
 */

var data = {title:'foo', body:'bar', userId:1};
$.ajax({
    url: 'http://localhost:8080/relay',
    type:"PUT",
    data: {
        url:"http://jsonplaceholder.typicode.com/posts/1",
        user: "hello",
        pass:"hello"
    },
    success: function(data) {
        document.write('<pre>'+JSON.stringify(JSON.parse(data), null, 2)+'</pre>');
    }
});