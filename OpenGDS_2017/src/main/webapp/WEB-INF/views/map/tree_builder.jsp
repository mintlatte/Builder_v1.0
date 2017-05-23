<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/views/common/common3.jsp" />
<title>Builder Test 2017</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
html {
	position: relative;
	min-height: 100%;
	overflow: hidden;
}

#builderHeader {
	border-radius: 0;
	margin-bottom: 0;
}

#builderContent {
	padding: 0;
}

#builderLayer {
	float: left;
	width: 380px;
	max-width: 380px;
	padding: 8px;
}

#bind {
	float: left;
}

#builderMap {
	
}

#builderBaseMap {
	position: relative;
	top: -906px;
	left: 0;
}

#builderFooter {
	min-height: 30px;
	line-height: 30px;
	margin-bottom: 0;
}

#builderLayerGeoServerPanel {
	margin-bottom: 16px;
}

#builderLayerClientPanel {
	margin-bottom: 0;
}

.gitbuilder-layer-panel {
	padding: 0;
	overflow-y: auto;
}
</style>

</head>
<body>

	<nav id="builderHeader"
		class="navbar navbar-toggleable-md navbar-default fixed-top">

		<ul class="nav navbar-nav">
			<li><a href="#" id="newVector" title="Vector"><i
					class="fa fa-file-o fa-lg" aria-hidden="true"></i> New</a></li>
			<li><a href="#" id="uploadFile" title="Upload File"
				onclick="gitbuilder.ui.NewFileWindow()"><i
					class="fa fa-file-o fa-lg" aria-hidden="true"></i> File</a></li>

			<li class="dropdown"><a href="#" class="dropdown-toggle"
				data-toggle="dropdown" role="button" aria-expanded="false"
				title="Save"><i class="fa fa-floppy-o fa-lg" aria-hidden="true"></i>
					Save</a>
				<ul class="dropdown-menu" role="menu">
					<li><a href="#">as a SHP</a></li>
					<li><a href="#">to Server</a></li>
				</ul></li>

			<li><a href="#" title="Edit" id="edit"><i
					class="fa fa-pencil-square-o fa-lg" aria-hidden="true"></i> Edit</a></li>
			<li><a href="#" title="Base map" id="changeBase"><i
					class="fa fa-map-o fa-lg" aria-hidden="true"></i> Base Map</a></li>
			<li><a href="#" title="Layer Definition" id="layerDefinition"><i
					class="fa fa-tags" aria-hidden="true"></i> Layer Definition</a></li>
			<li><a href="#" title="Validating Option" id="validDefinition"><i
					class="fa fa-sliders" aria-hidden="true"></i> Validating Option</a></li>
			<li><a href="#" title="Validation" id="validation"><i
					class="fa fa-search fa-lg" aria-hidden="true"></i> Validation</a></li>
			<li><a href="#" title="QA Edit" id="qaedit"><i
					class="fa fa-object-group fa-lg" aria-hidden="true"></i> QA Edit</a></li>
			<li class="dropdown"><a href="#" class="dropdown-toggle"
				data-toggle="dropdown" role="button" aria-expanded="false"
				title="Validation Result"><i class="fa fa-list-alt fa-lg"
					aria-hidden="true"></i> Result</a>
				<ul class="dropdown-menu" role="menu">
					<li><a href="#">Error Navigator</a></li>
					<li><a href="#">Error Report</a></li>
				</ul></li>

			<li class="dropdown"><a href="#" class="dropdown-toggle"
				data-toggle="dropdown" role="button" aria-expanded="false"
				title="ToolBox"><i class="fa fa-calculator fa-lg"
					aria-hidden="true"></i> ToolBox</a>
				<ul class="dropdown-menu" role="menu">
					<li><a href="#">CRS Transformation</a></li>
					<li><a href="#">Spatial Operation</a></li>
				</ul></li>

			<li class="dropdown"><a href="#" class="dropdown-toggle"
				data-toggle="dropdown" role="button" aria-expanded="false"
				title="History"><i class="fa fa-history fa-lg"
					aria-hidden="true"></i> History</a>
				<ul class="dropdown-menu" role="menu">
					<li><a href="#">History</a></li>
					<li role="presentation" class="divider"></li>
					<li><a href="#">Download History</a></li>
					<li><a href="#">Upload History</a></li>
				</ul></li>

			<li><a href="#" title="Information"><i
					class="fa fa-info-circle fa-lg" aria-hidden="true"></i> Information</a></li>
		</ul>
	</nav>

	<div id="builderContent" class="container-fluid">
		<div id="builderLayer">
			<div id="builderLayerGeoServerPanel" class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">GeoServer</h3>
				</div>
				<div class="panel-body gitbuilder-layer-panel">
					<div id="builderServerLayer"></div>
				</div>
			</div>
			<div id="builderLayerClientPanel" class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">Layer</h3>
				</div>
				<div class="panel-body gitbuilder-layer-panel">
					<div id="builderClientLayer"></div>
				</div>
			</div>
		</div>
		<div id="bind">
			<div id="builderMap"></div>
			<div id="builderBaseMap"></div>
		</div>
	</div>
	<nav id="builderFooter" class="navbar navbar-default">
		<div class="container-fluid">
			<span class="text-muted">OpenGDS Builder/Validator</span>
		</div>
	</nav>

	<script type="text/javascript">
		var projection = ol.proj.get('EPSG:5186');

		var view = new ol.View({
			center : ol.proj.fromLonLat([ 37.41, 8.82 ]),
			zoom : 4
		});

		var map = new ol.Map({
			target : 'builderMap',
			layers : [],
			view : view,
			controls : [ new ol.control.Zoom(), new ol.control.ZoomSlider() ]
		});

		var map2 = new ol.Map({
			target : 'builderBaseMap',
			controls : [],
			layers : [],
			view : view
		});

		var gitrnd = {
			resize : function() {
				var winHeight = $(window).innerHeight();
				var conHeight = winHeight - ($("#builderHeader").outerHeight(true) + $("#builderFooter").outerHeight(true));
				var winWidth = $(window).innerWidth();
				var mapWidth = winWidth - ($("#builderLayer").outerWidth(true));
				$("#builderLayer").outerHeight(conHeight);
				$("#bind").outerWidth(mapWidth).outerHeight(conHeight);
				$("#builderMap").outerWidth(mapWidth).outerHeight(conHeight);
				$("#builderBaseMap").outerWidth(mapWidth).outerHeight(conHeight);
				var str = "-" + conHeight + "px";
				$("#builderBaseMap").css("top", str);
				$("#builderBaseMap").find(".ol-viewport").css("z-index", 1);
				$("#builderMap").find(".ol-viewport").css("z-index", 2);
				var listHeight = $("#builderLayer").innerHeight() / 2 - 16;
				// 				38은 패널 헤더의 높이
				var treeHeight = listHeight - 38;
				$(".gitbuilder-layer-panel").outerHeight(treeHeight);
				$("#builderLayerGeoServerPanel").outerHeight(listHeight);
				$("#builderLayerClientPanel").outerHeight(listHeight);
				map.updateSize();
				map2.updateSize();
			},
			setProjection : function(code, name, proj4def, bbox) {
				if (code === null || name === null || proj4def === null || bbox === null) {
					map.setView(new ol.View({
						projection : 'EPSG:3857',
						center : [ 0, 0 ],
						zoom : 1
					}));
					return;
				}

				var newProjCode = 'EPSG:' + code;
				proj4.defs(newProjCode, proj4def);
				var newProj = ol.proj.get(newProjCode);
				var fromLonLat = ol.proj.getTransform('EPSG:4326', newProj);

				// very approximate calculation of projection extent
				var extent = ol.extent.applyTransform([ bbox[1], bbox[2], bbox[3], bbox[0] ], fromLonLat);
				newProj.setExtent(extent);
				var newView = new ol.View({
					projection : newProj
				});
				map.setView(newView);
				map2.setView(newView);
				newView.fit(extent);
			},
			search : function(query) {
				fetch('https://epsg.io/?format=json&q=' + query).then(function(response) {
					return response.json();
				}).then(function(json) {
					var results = json['results'];
					if (results && results.length > 0) {
						for (var i = 0, ii = results.length; i < ii; i++) {
							var result = results[i];
							if (result) {
								var code = result['code'], name = result['name'], proj4def = result['proj4'], bbox = result['bbox'];
								if (code && code.length > 0 && proj4def && proj4def.length > 0 && bbox && bbox.length == 4) {
									gitrnd.setProjection(code, name, proj4def, bbox);
									return;
								}
							}
						}
					}
					gitrnd.setProjection(null, null, null, null);
				});
			}

		}

		gitrnd.search("5186");

		$(window).resize(function() {
			gitrnd.resize();
		});

		$(document).ready(function() {
			gitrnd.resize();
		});

		$('#builderClientLayer').jstreeol3({
			"core" : {
				"map" : map,
				"animation" : 0,
				"themes" : {
					"stripes" : true
				},
			}
		});

		$("#edit").editingtool({
			geoserverURL : "http://175.116.181.42:9990/geoserver/",
			map : map,
			user : "admin",
			selected : function() {
				return $('#builderClientLayer').jstreeol3("get_selected_layer");
			}
		});

		$("#changeBase").changebase({
			map : map2
		});

		$("#layerDefinition").layerdefinition({
			definition : {
				"RoadBoundary" : {
					"code" : [ "A0010000" ],
					"geom" : "polygon",
					"area" : false,
					"weight" : 50
				},
				"NeatLine" : {
					"code" : [ "H0010000" ],
					"geom" : "linestring",
					"area" : true,
					"weight" : 50
				}
			}
		});

		$("#validDefinition").optiondefinition({
			updateLayerDef : function() {
				return $("#layerDefinition").layerdefinition("getDefinition");
			},
			definition : {
				"RoadBoundary" : {
					"SmallArea" : {
						"figure" : "0.1"
					},
					"EntityDuplicated" : true
				},
				"NeatLine" : {
					"EntityDuplicated" : true,
					"SmallLength" : {
						"figure" : "0.1"
					},
					"SelfEntity" : {
						"relation" : [ "NeatLine" ]
					}
				}
			}
		});

		$("#validation").validation({
			validatorURL : "validator/validate.ajax",
			layerDefinition : function() {
				return $("#layerDefinition").layerdefinition("getDefinition");
			},
			optionDefinition : function() {
				return $("#validDefinition").optiondefinition("getDefinition");
			}
		});

		$("#qaedit").qaedit({
			map : map,
			user : "admin"
		});

		$("#builderServerLayer").jstree({
			"core" : {
				"animation" : 0,
				"check_callback" : true,
				"themes" : {
					"stripes" : true
				},
				'data' : {
					'url' : function() {
						return 'geoserver2/getGeolayerCollectionTree.ajax';
					}
				}
			},
			"geoserver" : {
				"map" : map,
				"user" : "admin"
			},
			"types" : {
				"#" : {
					"valid_children" : [ "default" ]
				},
				"default" : {
					"icon" : "fa fa-file-o",
					"valid_children" : [ "default" ]
				},
				"normal" : {
					"icon" : "fa fa-folder-o",
					"valid_children" : [ "n_group" ]
				},
				"n_group" : {
					"icon" : "fa fa-folder-o",
					"valid_children" : [ "n_layer" ]
				},
				"n_layer" : {
					"icon" : "fa fa-file-image-o",
					"valid_children" : []
				},
				"error" : {
					"icon" : "fa fa-folder-o",
					"valid_children" : [ "e_group", "e_piece" ]
				},
				"e_group" : {
					"icon" : "fa fa-folder-o",
					"valid_children" : [ "e_layer" ]
				},
				"e_piece" : {
					"icon" : "fa fa-folder-o",
					"valid_children" : [ "e_layer_p" ]
				},
				"e_layer" : {
					"icon" : "fa fa-file-image-o",
					"valid_children" : []
				},
				"e_layer_p" : {
					"icon" : "fa fa-file-image-o",
					"valid_children" : []
				},
				"generalization" : {
					"icon" : "fa fa-folder-o",
					"valid_children" : [ "g_layer" ]
				},
				"g_layer" : {
					"icon" : "fa fa-file-image-o",
					"valid_children" : []
				}
			},
			"contextmenu" : {
				select_node : true,
				show_at_node : true,
				items : function(o, cb) { // Could be an object directly
					return {
						"import" : {
							"separator_before" : true,
							"icon" : "fa fa-download",
							"separator_after" : true,
							"label" : "Import",
							"action" : false,
							"submenu" : {
								"image" : {
									"separator_before" : false,
									"icon" : "fa fa-file-image-o",
									"separator_after" : false,
									"label" : "Image",
									"action" : function(data) {
										var inst = $.jstree.reference(data.reference), obj = inst.get_node(data.reference);
										if (obj.type === "n_layer") {
											var arr = inst.get_selected();
											if (inst.get_node(inst.get_parent(obj)).type === "n_group") {
												var wmsInfo = {
													"refer" : inst,
													"arr" : arr,
													"parent" : inst.get_parent(obj)
												}
												inst.import_image(wmsInfo);
											}
										} else if (obj.type === "n_group") {
											var arr = inst.get_selected();
											var arr2 = [];
											for (var i = 0; i < arr.length; i++) {
												arr2.push(inst.get_node(arr[i]).text);
											}
											inst.import_group(arr2);
										}
									}
								},
								"vector" : {
									"separator_before" : false,
									"icon" : "fa fa-file-excel-o",
									"separator_after" : false,
									"label" : "Vector",
									"action" : function(data) {
										var inst = $.jstree.reference(data.reference), obj = inst.get_node(data.reference);
										inst.import_vector();
									}
								}
							}
						},
						"info" : {
							"separator_before" : false,
							"icon" : "fa fa-info-circle",
							"separator_after" : false,
							"_disabled" : false, // (this.check("rename_node",
							// data.reference,
							// this.get_parent(data.reference),
							// "")),
							"label" : "Information",
							/*
							 * ! "shortcut" : 113, "shortcut_label" : 'F2',
							 * "icon" : "glyphicon glyphicon-leaf",
							 */
							"action" : function(data) {
								var inst = $.jstree.reference(data.reference), obj = inst.get_node(data.reference);
								console.log("Not yet(layer info)");
							}
						}
					};
				}
			},
			"plugins" : [ "contextmenu", "search", "state", "types", "geoserver" ]
		});
	</script>

</body>
</html>