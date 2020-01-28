var http = require('http');


function handing_incoming_request(req,res){
	console.log('imcoming request : ' + req.method + ' ' + req.url);
	res.writeHead(200,{
		'Content-Type':'application/json',
	});
	res.end(JSON.stringify({error:null}) + '\n');
}
var s = http.createServer(handing_incoming_request);
s.listen(9709);


