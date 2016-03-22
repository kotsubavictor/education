var express = require('express');
var MongoClient = require('mongodb').Db;
var Server = require('mongodb').Server;

var client = new MongoClient("test", new Server('localhost', 27017, {'native_parser': true}));
var app = express();
client.open(function(err, db) {
  app.get('/:name' , function(req, res) {
    db.collection('col').findOne({}, function(err, data) {
      if (err) {
        res.send(err, 404);
      }
      var result = {
        data: data,
        params: req.params,
        query: req.query,
        rawHeaders: req.rawHeaders,
        header: req.headers,
        httpVersion:req.httpVersion,
        method:req.method,
        rawTrailers:req.rawTrailers,
        statusCode:req.statusCode,
        statusMessage:req.statusMessage,
        url:req.url
      };

      res.send(result);
    });
  });

  app.get('*' , function(req, res) {
    res.send("Page was not found", 404);
  });


  app.listen(9999);
  console.log('start server');
});
// Example:
// http://localhost:9999/a?s=2&s=1&as=3&nd=4&js={a:1}


