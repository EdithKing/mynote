var http = require('http');
var fs = require('fs');

function load_album_list(callback){
	fs.readdir('../',function(err,files){
		if(err){
			callback(err);
			return;
		}
		callback(null,files);
	});
}
function hading_incoming_request(req,res){
	console.log('IMCOMING METHOD : ' + req.method + " " + req.url);
	load_album_list(function(err,result){
		if(err){
			res.writeHead(503,{'Content-Type':'application/json'});
			res.end(JSON.stringify(err)+'\n');
			return;
		}
		var out = {error : null,data:{album:result}};
		res.writeHead(200,{'Content-Type':'application/json'});
		res.end(JSON.stringify(out) + " \n");
	});
}
var s = http.createServer(hading_incoming_request);
s.listen(7999);

