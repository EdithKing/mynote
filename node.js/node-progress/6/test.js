var http = require('http');
var fs = require('fs');


function handle_incoming_request(req,res){
	if(req.method.toLowerCase() == 'get' && 
	req.url.substring(0,9) == '/content/'){
		var val = req.url.substring(9);
		serve_static_file(val,res);
	}else{
		res.writeHead(404,{"Content-Type":"application/json"});
		var out = {error : "not_found",
			message:"'" + req.url + "' not found "};
		res.end(JSON.stringify(out) + "\n");
	}
}

function serve_static_file(file,res){
	var rs = fs.createReadStream(file);
	var ct = content_type_for_path(file);
	res.write(200,{"Content-Type":ct});

	rs.on('readable',function(){
		var d = rs.read();
		if(d){
			if(typeof d == 'string'){
				res.write(d);
			}
			else if (typeof d == 'object && d instanceof Buffer'){
				res.write(d.toString('utf8'));
			}
		}
	});
	rs.on('end',function(){
		res.end();
	});
}
function content_type_for_path(file){
	return "text/html";
}

s = http.createServer(handle_incoming_request);
s.listen(9090);
