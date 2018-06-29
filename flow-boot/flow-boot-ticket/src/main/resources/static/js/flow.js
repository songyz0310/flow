/*流程核心Js*/
var flow = function() {
	var hasInit = false;
	return {
		init : function() {
//			var i = 2;
//			setInterval(function() {
//				Loading.destroy();
//				Loading.start(i);
//				i++;
//				i = (i == 12 ? 1 : i);
//			}, 3000);
			if (hasInit) {
				return;
			}
			hasInit = true;

			$.get(app.path + config.pageData + app.getUrlParam("entityId"),
					function(result) {
						Loading.stop();
					});
			return this;
		},
		confirm : function(name) {
			alert("尚未开发完成");
			console.info("confirm")
		},
		complete : function(name) {
			alert("尚未开发完成");
			console.info("complete")
		}
	}
}();
