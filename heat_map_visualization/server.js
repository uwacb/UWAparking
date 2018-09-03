var express = require('express');
var path = require('path');
var mysql = require('mysql');
var bodyParser = require('body-parser');

var index = require('./routes/index');
var heatmap = require('./routes/heatmap');
var app = express();
var port = 3000;

//set up db connection
var con = mysql.createConnection({
	host: "106.14.213.85",
	user: "root",
	password: "X30c1993"
});

//conntection status display

con.connect(function(err){
	if (err) throw err;
	console.log("db Connected");
});

//View engine
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'ejs');
app.engine('html', require('ejs').renderFile);


//Set Static folder
app.use(express.static(path.join(__dirname, 'client')));

//Body Parser Middleware
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: false}));


app.use('/', index);
app.use('/api', heatmap);

//general network information about page

app.listen(port, function(){
	console.log('Server Started on port '+port);
});