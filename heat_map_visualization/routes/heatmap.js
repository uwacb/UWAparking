//Designed by Chong Xie 21885263
var express = require('express');
var router = express.Router();
var mysql = require('mysql');

var con_pool = mysql.createPool({
	host: "106.14.213.85",
	user: "root",
	password: "",
	database: "uwa_parking",
	port:"3306"

});

var parking_general_query = "SELECT * from parkinglots_general";



//current page

router.get('/checking', function(req, res, next){
    con_pool.getConnection(function(err, dbconnection){
    dbconnection.query(parking_general_query, function(err, result){   
	res.render('management',{
		//title:"test json",
		data: result
	    });
	//console.log(result);
	dbconnection.release();
    });
    });
 });

//jump back to heat map href

router.get('/adminview', function(req, res, next)
{
	res.render('adminview');

});

module.exports = router;

//get history information

router.get("/history", function(req, res, next)
{
	res.send("history")

});

router.get("/del", function(req, res, next){
	console.log(req.query);
    var del_index_arr=req.query.parking_name_index;
    for (var i=0; i<del_index_arr.length;i++){
    	if (del_index_arr[i].indexOf("del")>-1){
    		var del_index=del_index_arr[i].replace("del","");
    		console.log(del_index);
    	}
    }

    //console.log(del_index);
	//res.redirect("checking");
	//console.log(req.query.parking_name_index);
	var del_query="DELETE FROM parkinglots_general where id="+del_index;
	//console.log(del_query);
	con_pool.getConnection(function(err, dbconnection){
		if (err) throw err;
		dbconnection.query(del_query, function(err, result){
			if(err){
				console.log('[query]-:'+err);
				res.send("operation failed!");
			}else{
				res.send("you have deleted a record!");

			}
		})
	});

	});

router.get("/edit", function(req, res, next){
   var edit_index_arr=req.query.parking_name_index;
   console.log(req.query);
       for (var i=0; i<edit_index_arr.length;i++){
    	        if (edit_index_arr[i].indexOf("edit")>-1){
    	        	    var edit_index=edit_index_arr[i].replace("edit","");
    		       
    	            }
            }
   var edit_query="select * from parkinglots_general where id="+edit_index;

    con_pool.getConnection(function(err, dbconnection){
    	if (err) throw err;
    	dbconnection.query(edit_query, function(err,result){
    		if(err){
    			console.log('[query]-:'+err);
				res.send("operation failed!");
    		}else{
    		
    			res.render("update",{data:result});
    			console.log(result);
    		}
    	});
    });
});

router.get("/create", function(req, res, next)
{
	res.render("create");

});

router.get("/submit", function(req, res, next)
{   
	//var admin_insert_query = "insert into parkinglots_general (`Name`,`Capacity`) values (null,?,?,?,?,?,?,?,?,?,?)"
	var admin_insert_query = "insert into parkinglots_general values (null,?,?,?,?,?,?,?,?,?,?)"
	value = [req.query.p_name, req.query.p_nw,req.query.p_se, req.query.p_sw, req.query.p_ne,req.query.p_capacity,(req.query.p_capacity/2),(req.query.p_capacity/2),req.query.p_rl,req.query.p_ticket];
	
	con_pool.getConnection(function(err, dbconnection){
		if (err) throw err;
		dbconnection.query(admin_insert_query,value, function(err, result){
			if(err){
				console.log('[query]-:'+err);
				res.send("operation failed!");
			}else{
				res.send("you have added a new record!");

			}
		})
	});
});


router.get("/edit_submit", function(req, res, next)
{   
	//var admin_insert_query = "insert into parkinglots_general (`Name`,`Capacity`) values (null,?,?,?,?,?,?,?,?,?,?)"
	var admin_update_query = "UPDATE parkinglots_general SET `name`=?, `CoorNW`=?, `CoorSE`=?, `CoorSW`=?, `CoorNe`=?, `Capacity`=?, `yellow_permission`=?, `red_permission`=?, `represent_loc`=?, `tickets`=? WHERE `id`=?";
	value = [req.query.p_name, req.query.p_nw,req.query.p_se, req.query.p_sw, req.query.p_ne,req.query.p_capacity,(req.query.p_capacity/2),(req.query.p_capacity/2),req.query.p_rl,req.query.p_ticket,req.query.p_id];
	
	con_pool.getConnection(function(err, dbconnection){
		if (err) throw err;
		dbconnection.query(admin_update_query,value, function(err, result){
			if(err){
				console.log('[query]-:'+err);
				res.send("operation failed!");
			}else{
				res.send("you have updated a record!");

			}
		})
	});
});


module.exports = router;
