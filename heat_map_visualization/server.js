//Designed by Chong Xie 21885263
//This is for displaying what db collection from users include locations, parking lots inforamtion
//A heat map will be used for guiding users

var express = require('express');
var path = require('path');
var mysql = require('mysql');
var bodyParser = require('body-parser');

var index = require('./routes/index');
var heatmap = require('./routes/heatmap');
var app = express();
var port = 3000;


//View engine  
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'ejs');
app.engine('html', require('ejs').renderFile);


//Set Static folder
app.use(express.static(path.join(__dirname, 'public')));

//Body Parser Middleware
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: false}));

//distribute requests to the assigned routers
app.use('/', index);
app.use('/api', heatmap);

//general network information about page

app.listen(port, function(){
	console.log('Server Started on port '+port);
});