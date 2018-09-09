<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=10, user-scalable=yes">
<title>Gov Hack Hospital UI</title>
	<script type="text/javascript">
		// Added base path
		var basePath = "${basePath}";
		//var basePath = "http://ip-172-31-3-213.ap-southeast-2.compute.internal:8100/health-iot-web";
		//var basePath = "http://localhost:18100/health-iot-web";
	</script>

     <link rel="stylesheet" type="text/css" href="../resources/build/classic/theme-triton/resources/theme-triton-all.css" />
	<script src="../resources/javascript/pixi.min.js"></script>
	
	<script type="text/javascript" src="../resources/build/ext-all-debug.js"></script>
	<script type="text/javascript" src="../resources/build/classic/theme-triton/theme-triton.js"></script>
    <script type="text/javascript" src="../resources/shared/options-toolbar.js"></script>
    <script type="text/javascript" src="../resources/app/main.js"></script>

    <style type="text/css">
        .icon-grid {
            background-image:url(../resources/shared/icons/fam/grid.png) !important;
        }
        .add {
            background-image:url(../resources/shared/icons/fam/add.gif) !important;
        }
        .option {
            background-image:url(../resources/shared/icons/fam/plugin.gif) !important;
        }
        .remove {
            background-image:url(../resources/shared/icons/fam/delete.gif) !important;
        }
        .save {
            background-image:url(../resources/shared/icons/save.gif) !important;
        }
        .x-grid-rowbody p {
            margin: .5em 0;
        }
    </style>
</head>
<body>
<script type="text/javascript">
    let type = "WebGL"
    if(!PIXI.utils.isWebGLSupported()){
      type = "canvas"
    }

    PIXI.utils.sayHello(type)
  </script>
	<div>
    <h1>Govhack Hospital UI</h1>
    </div>
    <div>
    <p>An initial user interface to show 
    <a href="../resources/app/main.js">main.js</a>.</p>
	</div>
	<div id="application"></div>
	<div id="hospital">
	<script type="text/javascript">
	//Create a Pixi Application
//let app = new PIXI.Application({width: 256, height: 256});


// let app = new PIXI.Application({ 
//     width: 600,         // default: 800
//     height: 400,        // default: 600
//     antialias: true,    // default: false
//     transparent: false, // default: false
//     resolution: 1       // default: 1
//   }
// );



// let line = new PIXI.Graphics();
// line.lineStyle(4, 0xFFFFFF, 1);
// line.moveTo(0, 0);
// line.lineTo(80, 50);
// line.x = 32;
// line.y = 32;
// app.stage.addChild(line);

//Add the canvas that Pixi automatically created for you to the HTML document


	</script>
	</div>
</body>
</html>
