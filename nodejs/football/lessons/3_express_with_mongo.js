var express = require('express');
var MongoClient = require('mongodb').Db;
var Server = require('mongodb').Server;

var client = new MongoClient("test", new Server('localhost', 27017, {'native_parser': true}));
var app = express();
client.open(function(err, db) {
  app.get('/' , function(req, res) {
    db.collection('col').findOne({}, function(err, data) {
      if (err) {
        res.send(err, 404);
      }

      res.send(data);
    });
  });

  app.get('*' , function(req, res) {
    res.send("Page was not found", 404);
  });


  app.listen(9999);
  console.log('start server');
});


