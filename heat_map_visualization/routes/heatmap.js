var express = require('express');
var router = express.Router();

router.get('/heatmap', function(req, res, next)
{
	res.send('heatmap_api');

});

module.exports = router;
