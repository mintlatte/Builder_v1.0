/**
 * 편집툴 객체를 정의한다.
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
gitbuilder.ui.EditingTool = $.widget("gitbuilder.editingtool", {
	window : undefined,
	options : {
		geoserverURL : undefined,
		map : undefined,
		user : undefined,
		selected : undefined,
		appendTo : "body"
	},
	map : undefined,
	layer : undefined,
	interaction : {
//		select : ,
//		draw : ,
//		move : ,
//		rotate : ,
//		modify : ,
//		remove : 
	},
	_create : function() {
		var that = this;
		this._on(false, this.element, {
			click : function(event) {
				if (event.target === that.element[0]) {
					that.open();
				}
			}
		});
		this.map = this.options.map;

		$(document).on("click", ".gitbuilder-editingtool-sel", function() {
			var type = that.layer.get("type");
			that.select(type);
		});
		var i1 = $("<i>").addClass("fa").addClass("fa-mouse-pointer").attr("aria-hidden", true);
		var selectBtn = $("<button>").css({
			"width" : "40px",
			"height" : "40px",
			"margin" : "5px 5px 0 5px"
		}).addClass("btn").addClass("btn-default").addClass("gitbuilder-editingtool-sel").append(i1);
		var float1 = $("<div>").css({
			"float" : "left"
		}).append(selectBtn);

		var i2 = $("<i>").addClass("fa").addClass("fa-pencil").attr("aria-hidden", true);
		var drawBtn = $("<button>").css({
			"width" : "40px",
			"height" : "40px",
			"margin" : "5px 5px 0 5px"
		}).addClass("btn").addClass("btn-default").addClass("gitbuilder-editingtool-dra").append(i2);
		var float2 = $("<div>").css({
			"float" : "left"
		}).append(drawBtn);

		var i3 = $("<i>").addClass("fa").addClass("fa-arrows").attr("aria-hidden", true);
		var moveBtn = $("<button>").css({
			"width" : "40px",
			"height" : "40px",
			"margin" : "5px 5px 0 5px"
		}).addClass("btn").addClass("btn-default").addClass("gitbuilder-editingtool-mov").append(i3);
		var float3 = $("<div>").css({
			"float" : "left"
		}).append(moveBtn);

		var i4 = $("<i>").addClass("fa").addClass("fa-repeat").attr("aria-hidden", true);
		var rotateBtn = $("<button>").css({
			"width" : "40px",
			"height" : "40px",
			"margin" : "5px 5px 0 5px"
		}).addClass("btn").addClass("btn-default").addClass("gitbuilder-editingtool-rot").append(i4);
		var float4 = $("<div>").css({
			"float" : "left"
		}).append(rotateBtn);

		var i5 = $("<i>").addClass("fa").addClass("fa-wrench").attr("aria-hidden", true);
		var modiBtn = $("<button>").css({
			"width" : "40px",
			"height" : "40px",
			"margin" : "5px 5px 0 5px"
		}).addClass("btn").addClass("btn-default").addClass("gitbuilder-editingtool-mod").append(i5);
		var float5 = $("<div>").css({
			"float" : "left"
		}).append(modiBtn);

		var i6 = $("<i>").addClass("fa").addClass("fa-eraser").attr("aria-hidden", true);
		var delBtn = $("<button>").css({
			"width" : "40px",
			"height" : "40px",
			"margin" : "5px 5px 5px 5px"
		}).addClass("btn").addClass("btn-default").addClass("gitbuilder-editingtool-del").append(i6);
		var float6 = $("<div>").css({
			"float" : "left"
		}).append(delBtn);

		var pbd = $("<div>").css({
			"padding" : 0
		}).addClass("panel-body").append(float1).append(float2).append(float3).append(float4).append(float5).append(float6);
		var phd = $("<div>").css("padding", 0).addClass("panel-heading").text("　");
		var pdf = $("<div>").addClass("panel").addClass("panel-default").append(phd).append(pbd);
		this.window = $("<div>").css({
			"width" : "50px",
			// "height" : "400px",
			"top" : "100px",
			"right" : 0,
			"position" : "absolute",
			"z-Index" : "999",
		}).append(pdf);

		$("body").append(this.window);
		$(this.window).hide();
		$(this.window).draggable({
			appendTo : "body",
		});
	},
	_init : function() {
		var that = this;
	},
	select : function(type) {
		if (type === "Vector") {
			console.log("vector-select");
		} else if (type === "ImageTile") {
			console.log("image-select");
		}
	},
	open : function() {
		if (typeof this.options.selected === "function") {
			var layers = this.options.selected();
			if (layers.length === 1 && !(layers[0] instanceof ol.layer.Group)) {
				this.layer = layers[0];
				$(this.window).show();
			} else {
				console.error("select a layer");
			}
		}
	},
	close : function() {
		$(this.window).hide();
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