var express = require('express');
var router = express.Router();

router.get('/', function(req, res, next) //root index will be localhost:3000/api/....
{
	res.send("hello");

});

module.exports = router;
