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
//collect all User in out information
var user_io = 'SELECT * FROM user_InOut';

//if error then throw it
// render result as data (json format)
con.query(user_io, function(err, result){
	if(err){
		console.log('[query]-:'+err);
	}else{
		router.get('/', function (req, res, next){
			console.log("query successfully");
			res.render('index',{
				title:"test json", //test
				data: result
			});
        console.log(result[10]);
		});
	}
	con.end();
	console.log("db connection closed")
})




module.exports = router;
