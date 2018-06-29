/*流程核心Js*/
var flow = function() {
	var hasInit = false;
	var $form = null;

	// 初始化函数
	var initFn = function() {
		if (hasInit)
			return false;

		hasInit = true;
		$form = $("form");

		var param = {};
		param.stepId = app.getUrlParam("stepId");
		param.entityId = app.getUrlParam("entityId");
		param.entityType = app.getUrlParam("entityType");

		var url = app.setGetUrl(app.path + config.pageData, param);
		$.get(url, function(result) {
			if (result.ecode == 0) {
				var data = result.data;

				$("[name=stepId]", $form).val(param.stepId);
				$("[name=entityId]", $form).val(param.entityId);
				$("[name=entityType]", $form).val(param.entityType);

				console.info(data);
			} else {
				alert(result.message);
			}
			Loading.stop();
		});
		$(".confirm_btn button").click(confirmFn);
	}

	// 页面确认函数
	var confirmFn = function() {

		if (confirmCheck()) {

		}

		Loading.start();
		console.info($form);

	}

	// 流程执行函数
	var completeFn = function() {
		Loading.start();
		console.info("complete")
	}

	var confirmCheck = function() {
		var check = true;
		$("[name][data-required=true]", $form).each(function() {
			if ($.trim(this.value) == "") {
				check = false;
				return true;
			}
		});
		return check;
	}

	return {
		init : initFn,
		confirm : confirmFn,
		complete : completeFn
	}
}();
