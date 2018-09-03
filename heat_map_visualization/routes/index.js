var express = require('express');
var router = express.Router();
var mysql = require('mysql');

var con = mysql.createConnection({
	host: "106.14.213.85",
	user: "root",
	password: "X30c1993",
	database: "uwaparking"
});

con.connect(function(err){
	if (err) throw err;
	console.log("db Connected");
});

//sql statement execute
var user_io = 'SELECT * FROM user_InOut';



con.query(user_io, function(err, result){
	if(err){
		console.log('[query]-:'+err);
	}else{
		router.get('/', function (req, res, next){
			res.render('index',{
				title:"test json",
				data: result
			});
		});
	}
})



module.exports = router;
