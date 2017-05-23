/**
 * 검수 수행창 객체를 정의한다.
 * 
 * @author yijun.so
 * @date 2017. 04
 * @version 0.01
 */
var gitbuilder;
if (!gitbuilder)
	gitbuilder = {};
if (!gitbuilder.ui)
	gitbuilder.ui = {};
gitbuilder.ui.Validation = $.widget("gitbuilder.validation", {
	widnow : undefined,
	webSocket : undefined,
	sendValiDefFlag : false,
	valiDef : undefined,
	optDef : undefined,
	layerDef : undefined,
	tbody : undefined,
	message : undefined,
	file : undefined,
	tree : undefined,
	ws : false,
	wsfirst : true,
	info : $("<div>").addClass("well").text("Ready"),
	bar : $("<div>").addClass("progress-bar").attr({
		"role" : "progressbar",
		"aria-valuemin" : 0,
		"aria-valuemax" : 100,
		"aria-valuenow" : 0
	}).css({
		"width" : "0",
		"min-width" : "2em"
	}).text("0%"),
	options : {
		layerDefinition : undefined,
		optionDefinition : undefined,
		updateLayerDef : undefined,
		updateOptionDef : undefined,
		validatorURL : undefined,
		appendTo : "body"
	},
	_create : function() {		
		this.layerDef = this.options.layerDefinition;
		this.optDef = this.options.optionDefinition;

		var that = this;
		this._on(false, this.element, {
			click : function(event) {
				if (event.target === that.element[0]) {
					that.open();
				}
			}
		});
		var xSpan = $("<span>").attr({
			"aria-hidden" : true
		}).html("&times;");
		var xButton = $("<button>").attr({
			"type" : "button",
			"data-dismiss" : "modal",
			"aria-label" : "Close"
		}).html(xSpan);
		this._addClass(xButton, "close");

		var htag = $("<h4>");
		htag.text("Validation");
		this._addClass(htag, "modal-title");

		var header = $("<div>").append(xButton).append(htag);
		this._addClass(header, "modal-header");
		/*
		 * 
		 * 
		 * header end
		 * 
		 * 
		 */
		var listhead = $("<div>").text("Layer List");
		this._addClass(listhead, "panel-heading");
		this.tree = $("<div>").attr({
			"id" : "gitlayers"
		});
		var listbody = $("<div>").css({
			"min-height" : "260px",
			"max-height" : "500px",
			"padding" : 0,
			"overflow-y" : "auto"
		}).append(this.tree);
		this._addClass(listbody, "panel-body");
		$(this.tree).jstree({
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
			"plugins" : [ "search", "state", "types" ]
		});
		$(this.tree).on('select_node.jstree', function(e, data) {
			var i, j, r = [];
			for (i = 0, j = data.selected.length; i < j; i++) {
				if (data.instance.get_node(data.selected[i]).type === "n_ngi_group" || data.instance.get_node(data.selected[i]).type === "n_dxf_group") {
					r.push(data.instance.get_node(data.selected[i]).text);
				} else {
					data.instance.deselect_node(data.instance.get_node(data.selected[i]));
					that.updateLayerList(r);
				}
			}
			if (r.length > 0) {
				that.updateValidationDef(r);
				that.updateLayerList(r);
			}
		});
		var layerlist = $("<div>").append(listhead).append(listbody);
		this._addClass(layerlist, "panel");
		this._addClass(layerlist, "panel-default");
		var uleft = $("<div>").append(layerlist);
		this._addClass(uleft, "col-md-5");

		var infohead = $("<div>").text("Layer Information");
		this._addClass(infohead, "panel-heading");
		var prog = $("<div>").append(this.bar);
		this._addClass(prog, "progress");
		var infobody = $("<div>").append(this.info).append(prog);
		this._addClass(infobody, "panel-body");
		var layerInfo = $("<div>").append(infohead).append(infobody);
		this._addClass(layerInfo, "panel");
		this._addClass(layerInfo, "panel-default");

		var tdhead1 = $("<td>").text("#");
		var tdhead2 = $("<td>").text("Name");
		var tdhead3 = $("<td>").text("Accuracy");
		var trhead = $("<tr>").append(tdhead1).append(tdhead2).append(tdhead3);
		var thead = $("<thead>").append(trhead);
		that.tbody = $("<tbody>");
		var tb = $("<table>").append(thead).append(that.tbody);
		this._addClass(tb, "table");
		this._addClass(tb, "table-striped");
		this._addClass(tb, "text-center");
		var infobody2 = $("<div>").append(tb);
		this._addClass(infobody2, "panel-body");
		var layerInfo2 = $("<div>").append(infobody2);
		this._addClass(layerInfo2, "panel");
		this._addClass(layerInfo2, "panel-default");
		var uright = $("<div>").append(layerInfo).append(layerInfo2);
		this._addClass(uright, "col-md-7");
		var row = $("<div>").append(uleft).append(uright);
		this._addClass(row, "row");
		var body = $("<div>").append(row);
		this._addClass(body, "modal-body");
		/*
		 * 
		 * 
		 * body end
		 * 
		 * 
		 */
		var closeBtn = $("<button>").attr({
			"type" : "button",
			"data-dismiss" : "modal"
		});
		this._addClass(closeBtn, "btn");
		this._addClass(closeBtn, "btn-default");
		$(closeBtn).text("Close");

		var okBtn = $("<button>").attr({
			"type" : "button"
		});
		this._addClass(okBtn, "btn");
		this._addClass(okBtn, "btn-primary");
		$(okBtn).text("Start");

		this._on(false, okBtn, {
			click : function(event) {
				if (event.target === okBtn[0]) {
					that.start();
				}
			}
		});

		var footer = $("<div>").append(closeBtn).append(okBtn);
		this._addClass(footer, "modal-footer");
		/*
		 * 
		 * 
		 * footer end
		 * 
		 * 
		 */
		var content = $("<div>").append(header).append(body).append(footer);
		this._addClass(content, "modal-content");

		var dialog = $("<div>").html(content);
		this._addClass(dialog, "modal-dialog");
		this._addClass(dialog, "modal-lg");

		this.window = $("<div>").hide().attr({
			tabIndex : -1,
			role : "dialog",
		}).html(dialog);
		this._addClass(this.window, "modal");
		this._addClass(this.window, "fade");

		this.window.appendTo(this._appendTo());
		this.window.modal({
			backdrop : "static",
			keyboard : true,
			show : false,
		});
	},
	_init : function() {
		var that = this;
		this.setMessage("Ready");
		this.setProgress(0);
		$(this.tree).jstree("refesh");
	},
	setLayerDefinition : function(obj) {
		this.layerDef = obj;
	},
	getLayerDefinition : function() {
		var result;
		if (typeof this.layerDef === "function") {
			result = this.layerDef();
		} else if (typeof this.layerDef === "object") {
			result = this.layerDef;
		} else {
			console.error("invalid type");
			return;
		}
		return result;
	},
	setOptionDefinition : function(obj) {
		this.optDef = obj;
	},
	getOptionDefinition : function() {
		var result;
		if (typeof this.optDef === "function") {
			result = this.optDef();
		} else if (typeof this.optDef === "object") {
			result = this.optDef;
		} else {
			console.error("invalid type");
			return;
		}
		return result;
	},
	setValidationDefinition : function(obj) {
		this.valiDef = obj;
	},
	getValidationDefinition : function() {
		return this.valiDef;
	},
	disconnect : function() {
		this.webSocket.close();
	},
	start : function() {
		var that = this;
		 if (!!this.valiDef) {
			 console.log(this.valiDef);
			 $.ajax({
					url : that.options.validatorURL,
					type : "POST",
					contentType : "application/json; charset=UTF-8",
					cache : false,
					async : true,
					data : JSON.stringify(this.valiDef),
					beforeSend : function() { // 호출전실행
// loadImageShow();
					},
					traditional : true,
					success : function(data, textStatus, jqXHR) {
						console.log(data);
						if (!data.ErrorLayer && !data["Publising ErrorLayer"]) {
							that.setMessage("No errors. Not published.");
							that.setProgress(0);
						} else if(data.ErrorLayer && data["Publising ErrorLayer"]){
							that.setMessage("Validation complete");
							that.setProgress(100);
						} else if(data.ErrorLayer && !data["Publising ErrorLayer"]){
							that.setMessage("Error detected. Not published.");
							that.setProgress(0);
						}
					}
				});
		}
	},
	setProgress : function(figure) {
		var int = parseInt(figure);
		$(this.bar).attr({"aria-valuenow" : int}).css({
			"width" : int+"%"}).text(int+"%");
	},
	getProgress : function() {
		return $(this.bar).attr("aria-valuenow");
	},
	setMessage : function(msg) {
		$(this.info).text(msg);
	},
	updateLayerList : function(names) {
		$(this.tbody).empty();
		for (var i = 0; i < names.length; i++) {
			var td1 = $("<td>").text((i + 1));
			var td2 = $("<td>").text(names[i]);
			var td3 = $("<td>");
			var tr = $("<tr>").append(td1).append(td2).append(td3);
			$(this.tbody).append(tr);
		}
	},
	updateValidationDef : function(names) {
		if (!Array.isArray(names)) {
			console.error("Invalid type");
			return;
		} else {
			if (names.length === 0) {
				console.error("no elements");
				return;
			}
		}
		var totalObj = {};
		var layerColl = {
			"collectionName" : names
		};
		var ldef = this.getLayerDefinition();
		var odef = this.getOptionDefinition();
		var lkeys = Object.keys(ldef);
		var layers = [];

		var typeValidate = [];
		for (var i = 0; i < lkeys.length; i++) {
			if (ldef[lkeys[i]].area) {
				layerColl["neatLineLayer"] = ldef[lkeys[i]].code + "_" + (ldef[lkeys[i]].geom.toUpperCase());
			}
			// 타입 벨리데이트 내부의 레이어스
			var tLayers = [];
			var codes = ldef[lkeys[i]].code;
			for (var j = 0; j < codes.length; j++) {
				var name = codes[j] + "_" + (ldef[lkeys[i]].geom.toUpperCase());
				layers.push(name);
				tLayers.push(name);
			}
			var tvObj = {
				"typeName" : lkeys[i],
				"layers" : tLayers,
				"weight" : ldef[lkeys[i]].weight
			};
			if (odef.hasOwnProperty(lkeys[i])) {
				tvObj["option"] = odef[lkeys[i]];
			}
			typeValidate.push(tvObj);
		}
		layerColl["layers"] = layers;
		totalObj["layerCollections"] = layerColl;
		totalObj["typeValidate"] = typeValidate;
// console.log(totalObj);
		this.valiDef = totalObj;
		// return totalObj;
	},
	open : function() {
		this.window.modal('show');
		this._init();
		var arr = $(this.tree).jstree("get_selected");
		var i, j, r = [];
		for (i = 0, j = arr.length; i < j; i++) {
			if ($(this.tree).jstree("get_node", arr[i]).type === "n_ngi_group" || $(this.tree).jstree("get_node", arr[i]).type === "n_dxf_group") {
				r.push($(this.tree).jstree("get_node", arr[i]).text);
			} else {
				$(this.tree).jstree("deselect_node", $(this.tree).jstree("get_node", arr[i]));
				this.updateLayerList(r);
			}
		}
		if (r.length > 0) {
			this.updateValidationDef(r);
			this.updateLayerList(r);
		}
	},
	close : function() {
		this.window.modal('hide');
	},
	destroy : function() {
		this.element.off("click");
		$(this.window).find("button").off("click");
		$(this.window).find("input").off("change").off("load");
		this.window = undefined;
	},
	_appendTo : function() {
		var element = this.options.appendTo;
		if (element && (element.jquery || element.nodeType)) {
			return $(element);
		}
		return this.document.find(element || "body").eq(0);
	},
	_removeClass : function(element, keys, extra) {
		return this._toggleClass(element, keys, extra, false);
	},

	_addClass : function(element, keys, extra) {
		return this._toggleClass(element, keys, extra, true);
	},

	_toggleClass : function(element, keys, extra, add) {
		add = (typeof add === "boolean") ? add : extra;
		var shift = (typeof element === "string" || element === null), options = {
			extra : shift ? keys : extra,
			keys : shift ? element : keys,
			element : shift ? this.element : element,
			add : add
		};
		options.element.toggleClass(this._classes(options), add);
		return this;
	},

	_on : function(suppressDisabledCheck, element, handlers) {
		var delegateElement;
		var instance = this;

		// No suppressDisabledCheck flag, shuffle arguments
		if (typeof suppressDisabledCheck !== "boolean") {
			handlers = element;
			element = suppressDisabledCheck;
			suppressDisabledCheck = false;
		}

		// No element argument, shuffle and use this.element
		if (!handlers) {
			handlers = element;
			element = this.element;
			delegateElement = this.widget();
		} else {
			element = delegateElement = $(element);
			this.bindings = this.bindings.add(element);
		}

		$.each(handlers, function(event, handler) {
			function handlerProxy() {

				// Allow widgets to customize the disabled
				// handling
				// - disabled as an array instead of boolean
				// - disabled class as method for disabling
				// individual parts
				if (!suppressDisabledCheck && (instance.options.disabled === true || $(this).hasClass("ui-state-disabled"))) {
					return;
				}
				return (typeof handler === "string" ? instance[handler] : handler).apply(instance, arguments);
			}

			// Copy the guid so direct unbinding works
			if (typeof handler !== "string") {
				handlerProxy.guid = handler.guid = handler.guid || handlerProxy.guid || $.guid++;
			}

			var match = event.match(/^([\w:-]*)\s*(.*)$/);
			var eventName = match[1] + instance.eventNamespace;
			var selector = match[2];

			if (selector) {
				delegateElement.on(eventName, selector, handlerProxy);
			} else {
				element.on(eventName, handlerProxy);
			}
		});
	},

	_off : function(element, eventName) {
		eventName = (eventName || "").split(" ").join(this.eventNamespace + " ") + this.eventNamespace;
		element.off(eventName).off(eventName);

		// Clear the stack to avoid memory leaks (#10056)
		this.bindings = $(this.bindings.not(element).get());
		this.focusable = $(this.focusable.not(element).get());
		this.hoverable = $(this.hoverable.not(element).get());
	}
});