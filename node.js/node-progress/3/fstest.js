var fs = require('fs');

/*
读取文件内容
*/
fs.open(
	'info.txt','r',
	function(err,handle){
		if(err){
			console.log('Error:'+err.code + ' message: ' + err.message);
			return;
		}
		var buf = new Buffer(100000);
		fs.read(handle,buf,0,100000,null,
		function(err,length){
				if(err){
					console.log('Error:'+err.code + ' message: ' + err.message);
					return;
				}
				console.log(buf.toString('utf-8',0,length));
				fs.close(handle,function(){});
			}
		);
	}
);

function FileObject(){
	this.filename = '';	
	this.file_exists = function(callback){
		var self = this;
		console.log("About to open : " + self.filename);
		fs.open(this.filename,'r',function(err,handle){
			if(err){
				console.log("Can't open:" + self.filename);
				callback(err);
				return;
			}
			fs.close(handle,function(){});
			callback(null,true);
		})
	}
};

var fo = new FileObject();
fo.filename = 'info.txt';
fo.file_exists(function(err,results){
	if(err){
		console.log(err);
		return ;
	}
	console.log("返回结果" + results);
	console.log('file exists!!!');
});


