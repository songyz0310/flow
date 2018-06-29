var app = function() {
	return {
		host : "",
		contextPath : '',
		path : "",
		pageData : {},
		urlParam : [],
		init : function(host, contextPath, pageData) {
			this.host = host == null ? "" : host;
			this.contextPath = contextPath == null ? "" : contextPath;
			this.path = this.host + this.contextPath;
			this.pageData = pageData == null ? {} : pageData;

			var search = location.search;
			if (search.indexOf("?") == 0 && search.indexOf("=") > 1) {
				this.urlParam = unescape(search).substring(1, search.length)
						.split("&");
			}
		},
		getUrlParam : function(name) {
			var value = "";
			if (this.urlParam.length > 0) {
				var isFound = false;
				var i = 0;
				while (i < this.urlParam.length && !isFound) {
					if (this.urlParam[i].indexOf("=") > 0
							&& this.urlParam[i].split("=")[0].toLowerCase() == name
									.toLowerCase()) {
						value = this.urlParam[i].split("=")[1];
						isFound = true
						break;
					}
					i++;
				}
			}
			return value;
		}
	}
}();
