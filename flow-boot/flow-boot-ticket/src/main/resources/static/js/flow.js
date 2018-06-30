/*流程核心Js*/
var flow = function() {
	var hasInit = false;
	var $form = null;

	// 参数校验
	var confirmCheck = function() {
		var check = true;
		// 非空校验
		$("[name][data-required=true]", $form).each(function() {
			if ($.trim(this.value) == "") {
				check = false;
				layer.msg(this.getAttribute("placeholder"));
				return true;
			}
		});
		return check;
	}

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
				layer.msg(result.message);
			}
			Loading.stop();
		});
		$(".confirm_btn button").click(confirmFn);
	}

	// 页面确认函数
	var confirmFn = function() {
		if (confirmCheck()) {
			$form = $form || $("form");
			$.post(app.path + config.confirm, $form.serializeArray(), function(
					result) {
				if (result.ecode == 0) {
					console.info(result.data);
				} else {
					layer.msg(result.message);
				}
				Loading.stop();
			});
		}
	}

	// 流程执行函数
	var executeFn = function(entityType, entityId, stepId) {
		Loading.start();
		layer.msg("execute");
	}

	return {
		init : initFn,
		confirm : confirmFn,
		execute : executeFn
	}
}();
