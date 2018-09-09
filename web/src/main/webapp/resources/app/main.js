console.log(Ext.getVersion('extjs'));
Ext.Loader.setPath('HIW', '../resources/cmp/HIW');
//Ext.exclude('Ext.tree.*').require('*');
Ext.Loader.setConfig({
	enabled : true
});

Ext.Boot.setConfig({
	disableCaching : false
});

var currentPath = basePath;
var grid1 = null;
var wrapper = null;

//var endPointHost = "http://localhost:8080";
//var endPointHost = "http://toot-cdsw.lab1.com:8080";


Ext.require([ 'HIW.Login' ]);

Ext.onReady(function() {

	Ext.create('HIW.Login');
	

	
});
