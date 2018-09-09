Ext.define('HIW.HospitalPanel', {
	extend : 'Ext.panel.Panel',
	header : {},
	header : true,
	html : '<div id="pixi"></div>',
	initComponent : function() {
		this.callParent();

		this.firstTabChange = true;

		var me = this;
	},
	somethingElse : function() {
		console.log("doing something else");
		this.creatorPanel.hide();
		this.searchPanel.show();
	},
	doTabChange : function() {
		if (this.firstTabChange) {
			var me = this;

			console.log("First tab change in hospital tab");
			this.firstTabChange = false;

			// var DomEl = this.getEl();
			// get Id
			// var DomElId = DomEl.id;

			let app = new PIXI.Application({
				width : 600, // default: 800
				height : 600, // default: 600
				antialias : true, // default: false
				transparent : false, // default: false
				resolution : 1
			// default: 1
			});
			this.pixiApp = app;

			Ext.Ajax.request({
				url : basePath + '/floor/aaa_0',
				withCredentials : false,
				useDefaultXhrHeader : false,
				method : 'GET',
				// params: params,
				callback : function(options, success, response) {
					console.log("Received: " + response);

					var jsonData = Ext.util.JSON.decode(response.responseText);

					me.drawCorners(jsonData);

				}
			});

			// document.body.appendChild(app.view);

			var element = document.getElementById("pixi");
			element.appendChild(app.view);
			this.updateLayout();

		}
	},
	drawCorners : function(jsonData) {
		var lastPoint = null;

		var scale = 2.5;

		var path = [];

		for (var i = 0; i < jsonData.corners.length; i++) {
			var point = jsonData.corners[i];

			path.push(point.x / scale);
			path.push(point.y / scale);

		}

		var graphics = new PIXI.Graphics();
		graphics.lineStyle(1, 0xFFFFFF, 1);
		graphics.beginFill(0x0000ff, 1);
		graphics.drawPolygon(path);
		graphics.endFill();
		this.pixiApp.stage.addChild(graphics);

		for (var i = 0; i < jsonData.rooms.length; i++) {
			path = [];



			var room = jsonData.rooms[i];

			
			var xSum = 0;
			var ySum = 0;
			
			for (var j = 0; j < room.corners.length; j++) {
				var point = room.corners[j];

				path.push(point.x / scale);
				path.push(point.y / scale);

				
			}
			
			var buttonText = new PIXI.Text(room.name, {
				fontFamily : 'Arial',
				fontSize : 10,
				fill : "white"
				//align : 'right'
			});
			buttonText.resolution = 2;
			//buttonText.anchor.set(0.5, 0.5);
			buttonText.position.set(room.center.x / scale, room.center.y / scale);
			
			var graphics = new PIXI.Graphics();
			graphics.lineStyle(1, 0xFFFFFF, 1);
			graphics.beginFill(0xaaaaaa, 1);
			graphics.drawPolygon(path);
			graphics.endFill();
			graphics.interactive = true;
			graphics.roomId = room.id;
			graphics.room = room;
			graphics.click = function(ev) {
				console.log("Clicked " + ev.target.roomId);
				
				var me = this;
				
				var myForm = new Ext.form.Panel({
				    width: 500,
				    height: 400,
				    title: 'Room Details ' + ev.target.roomId,
				    floating: true,
				    closable : true
				});
				
				var patients = Ext.create('Ext.data.Store', {
			        fields: ['id', 'nameAndDob'],
			        data : [
			            {"id":"1", "nameAndDob":"Alex Alexis 1900-01-01"},
			            {"id":"2", "nameAndDob":"James James 1900-01-01"},
			            {"id":"3", "nameAndDob":"Bill Billson 1900-01-01"}
			        ]
			    });
				
				var patient = Ext.create("Ext.form.field.ComboBox", {
					padding : '5 5 5 5',
					width : 400,
					fieldLabel: 'Patient',
				    store: patients,
				    queryMode: 'local',
				    displayField: 'nameAndDob',
				    valueField: 'id',
				});
				myForm.add(patient);
				me.patient = patient;
				
				// Add combo boxes for linked devices.
				var ecgLink = Ext.create("Ext.form.field.Checkbox", {
					fieldLabel : "Link to ECG AAABBCCC-" + ev.target.roomId,
					padding : '5 5 5 5'
				});
				myForm.add(ecgLink);
				me.ecgLink = ecgLink;

				// Add combo boxes for linked devices.
				var heartMonitor = Ext.create("Ext.form.field.Checkbox", {
					fieldLabel : "Link to Heart Monitor AS-" + ev.target.room.name,
					padding : '5 5 5 5'
				});
				myForm.add(heartMonitor);
				me.heartMonitor = heartMonitor;
			
				me.room = ev.target.room;
				
				var button = Ext.create("Ext.button.Button", {
					text : 'Update',
					margin : 5,
					padding : '5 5 5 5',
					wrappingForm : myForm,
					handler : function() {
						var patientField = me.patient;
						var patientId = patientField.selected;
						console.log("We have selected " + patientId);
						
						Ext.Ajax.request({
							url : basePath + '/room/' + me.room.hospitalId + '/' + me.room.floorId + '/' + me.room.id, // where you wanna post
							method : 'POST',
							success : function(fp, o) {
								var responseText = fp.responseText;
								//me.handleAjaxSuccess(me, responseText);
								Ext.Msg.alert('Success', 'Room Updated');
							}, // function called on success
							failure : function(fp, o) {
								// TODO Show cause
								Ext.Msg.alert('Success', 'Room Updated');
							},
							//params
							jsonData : {
								patientId : patientId
								
							}
						// your json data
						});
						
						var win = Ext.WindowManager.getActive();
						if (win) {
						win.close();
						}
					}
				});
				myForm.add(button);
				
				
				
				myForm.show();
			};

			this.pixiApp.stage.addChild(graphics);
			this.pixiApp.stage.addChild(buttonText);
			//graphics.addChild(buttonText);
		}

	}
});