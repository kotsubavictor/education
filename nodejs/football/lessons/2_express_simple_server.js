var express = require('express');
app = express();

app.get('/' , function(req, res) {
  res.send("Http server.");
});

app.get('*' , function(req, res) {
  res.send("Page was not found", 404);
});

app.listen(9999);
console.log('start server');
