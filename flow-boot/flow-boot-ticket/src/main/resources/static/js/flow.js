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

		$.ajax({
			url : app.setGetUrl(app.path + config.stepPageData, param),
			type : "GET",
			dataType : "json",
			success : function(result) {
				if (result.ecode == 0) {
					var data = result.data;

					$("[name=stepId]", $form).val(param.stepId);
					$("[name=entityId]", $form).val(param.entityId);
					$("[name=entityType]", $form).val(param.entityType);

					$.each(data,function(i,o){
						if (o.value != "") 
							$("[data-fpcid="+o.fpcId+"]",$form).val(o.value);
					});
				} else {
					layer.msg(result.message);
				}
				Loading.stop();
			},
			error : function(error) {
				layer.msg(error.responseJSON.message);
				Loading.stop();
			}
		});

		$(".confirm_btn button").click(confirmFn);
	}

	// 页面确认函数
	var confirmFn = function() {
		if (confirmCheck()) {
			$form = $form || $("form");
			Loading.start();
			$.ajax({
				url : app.path + config.stepConfirm,
				data : $form.serializeArray(),
				type : "POST",
				dataType : "json",
				success : function(result) {
					if (result.ecode == 0) {
						console.info(result.data);
					} else {
						layer.msg(result.message);
					}
					Loading.stop(() => {
						location.href = document.referrer;
					});
				},
				error : function(error) {
					layer.msg(error.responseJSON.message);
					Loading.stop();
				}
			});
		}
	}

	// 流程执行函数
	var executeFn = function(entityType, entityId, stepId) {
		var execute = {};
		execute.entityType = entityType;
		execute.entityId = entityId;
		execute.stepId = stepId;
		execute.address = "北京市昌平区融泽嘉园";
		execute.longitude = "116.400244";
		execute.latitude = "39.92556";

		Loading.start();

		$.ajax({
			url : app.path + config.stepExecute,
			data : execute,
			type : "POST",
			dataType : "json",
			success : function(result) {
				if (result.ecode == 0) {
					console.info(result.data);
				} else {
					layer.msg(result.message);
				}
				Loading.stop(() => {
					location.reload();
				});
			},
			error : function(error) {
				layer.msg(error.responseJSON.message);
				Loading.stop();
			}
		});

	}
	
	// 流程跳跃
	var jumpFn = function(entityType, entityId, stepId,jumpStepId) {
		var jump = {};
		jump.entityType = entityType;
		jump.entityId = entityId;
		jump.stepId = stepId;
		jump.jumpStepId = jumpStepId;
		
		Loading.start();
		
		$.ajax({
			url : app.path + config.stepJump,
			data : jump,
			type : "POST",
			dataType : "json",
			success : function(result) {
				if (result.ecode == 0) {
					console.info(result.data);
				} else {
					layer.msg(result.message);
				}
				Loading.stop(() => {
					location.reload();
				});
			},
			error : function(error) {
				layer.msg(error.responseJSON.message);
				Loading.stop();
			}
		});
	}
	
	// 回退
	var cancelFn = function(entityType, entityId, stepId) {
		var cancel = {};
		cancel.entityType = entityType;
		cancel.entityId = entityId;
		cancel.stepId = stepId;

		Loading.start();

		$.ajax({
			url : app.path + config.stepCancel,
			data : cancel,
			type : "POST",
			dataType : "json",
			success : function(result) {
				if (result.ecode == 0) {
					console.info(result.data);
				} else {
					layer.msg(result.message);
				}
				Loading.stop(() => {
					location.reload();
				});
			},
			error : function(error) {
				layer.msg(error.responseJSON.message);
				Loading.stop();
			}
		});
	}

	return {
		init : initFn,
		confirm : confirmFn,
		execute : executeFn,
		jump : jumpFn,
		cancel : cancelFn
	}
}();
