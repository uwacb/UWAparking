var express = require('express');
var router = express.Router();
var mysql = require('mysql');

//Connection Pool method

var con_pool = mysql.createPool({
	host: "106.14.213.85",
	user: "root",
	password: "",
	database: "uwa_parking",
	port:"3306"

});

//var con = mysql.createConnection({
//	host: "106.14.213.85",
//	user: "root",
//	password: "X30c1993",
//	database: "uwaparking"
//}); tradinational connection


//sql statement execute
//collect all Heat points in the table;

//if error then throw it
// render result as data (json format)
var user_io = 'SELECT * FROM parking_record JOIN parkinglots_general WHERE parking_record.parkinglots_id=parkinglots_general.id;';

con_pool.getConnection(function(err, dbconnection){
	if (err) throw err;
	console.log("db Connected");
	dbconnection.query(user_io, function(err, result){
	if(err){
		console.log('[query]-:'+err);
	}else{
		router.get('/', function (req, res, next){
			dbconnection.query(user_io, function(err, result){
			console.log("query successfully");
			
			res.render('index',{
				title:"test json", //test
				data: result
			});
            
		    });
	    });
	dbconnection.release();
	console.log("db connection closed");
    }
    });
 });







module.exports = router;
