var Loading = function() {
	var startTime = null;
	var showInterval = 1500;
	var _content_div = null;
	
	return {
		start : function(type, color) {
			startTime = new Date();
			if (_content_div != null) {
				_content_div.style.display = "block";
				return;
			}
			if (type == null) {
				type = 1;
			}
			if (color == null) {
				color = "red";
			}

			var _load_div = document.createElement('div');

			switch (type) {
			case 1:
				_load_div.className = "sk-load sk-rotating-plane";
				break;
			case 2:
				_load_div.className = "sk-load sk-double-bounce";
				_load_div.innerHTML = '<div class="sk-child sk-double-bounce1"></div><div class="sk-child sk-double-bounce2"></div>';
				break;
			case 3:
				_load_div.className = "sk-load sk-wave";
				_load_div.innerHTML = '<div class="sk-rect sk-rect1"></div><div class="sk-rect sk-rect2"></div><div class="sk-rect sk-rect3"></div><div class="sk-rect sk-rect4"></div><div class="sk-rect sk-rect5"></div>';
				break;
			case 4:
				_load_div.className = "sk-load sk-wandering-cubes";
				_load_div.innerHTML = '<div class="sk-cube sk-cube1"></div><div class="sk-cube sk-cube2"></div>';
				break;
			case 5:
				_load_div.className = "sk-load sk-spinner sk-spinner-pulse";
				break;
			case 6:
				_load_div.className = "sk-load sk-chasing-dots";
				_load_div.innerHTML = '<div class="sk-child sk-dot1"></div><div class="sk-child sk-dot2"></div>';
				break;
			case 7:
				_load_div.className = "sk-load sk-three-bounce";
				_load_div.innerHTML = '<div class="sk-child sk-bounce1"></div><div class="sk-child sk-bounce2"></div><div class="sk-child sk-bounce3"></div>';
				break;
			case 8:
				_load_div.className = "sk-load sk-circle";
				_load_div.innerHTML = '<div class="sk-circle1 sk-child"></div><div class="sk-circle2 sk-child"></div><div class="sk-circle3 sk-child"></div><div class="sk-circle4 sk-child"></div><div class="sk-circle5 sk-child"></div><div class="sk-circle6 sk-child"></div><div class="sk-circle7 sk-child"></div><div class="sk-circle8 sk-child"></div><div class="sk-circle9 sk-child"></div><div class="sk-circle10 sk-child"></div><div class="sk-circle11 sk-child"></div><div class="sk-circle12 sk-child"></div>';
				break;
			case 9:
				_load_div.className = "sk-load sk-cube-grid";
				_load_div.innerHTML = '<div class="sk-cube sk-cube1"></div><div class="sk-cube sk-cube2"></div><div class="sk-cube sk-cube3"></div><div class="sk-cube sk-cube4"></div><div class="sk-cube sk-cube5"></div><div class="sk-cube sk-cube6"></div><div class="sk-cube sk-cube7"></div><div class="sk-cube sk-cube8"></div><div class="sk-cube sk-cube9"></div>';
				break;
			case 10:
				_load_div.className = "sk-load sk-fading-circle";
				_load_div.innerHTML = '<div class="sk-circle1 sk-circle"></div><div class="sk-circle2 sk-circle"></div><div class="sk-circle3 sk-circle"></div><div class="sk-circle4 sk-circle"></div><div class="sk-circle5 sk-circle"></div><div class="sk-circle6 sk-circle"></div><div class="sk-circle7 sk-circle"></div><div class="sk-circle8 sk-circle"></div><div class="sk-circle9 sk-circle"></div><div class="sk-circle10 sk-circle"></div><div class="sk-circle11 sk-circle"></div><div class="sk-circle12 sk-circle"></div>';
				break;
			case 11:
				_load_div.className = "sk-load sk-folding-cube";
				_load_div.innerHTML = '<div class="sk-cube1 sk-cube"></div><div class="sk-cube2 sk-cube"></div><div class="sk-cube4 sk-cube"></div><div class="sk-cube3 sk-cube"></div>';
				break;
			case 16:
				_load_div.className = "sk-load loader-16";
				break;

			}

			if (type == 8 || type == 10 || type == 11|| type == 16) {// 伪类样式渲染完在修改
				_load_div.style.color = color;
			} else {
				var array = _load_div.children;
				if (array.length > 0) {
					for (var i = 0; i < array.length; i++)
						array[i].style.backgroundColor = color;

				} else
					_load_div.style.backgroundColor = color;
			}

			_content_div = document.createElement('div');
			_content_div.className = "sk-content";
			_content_div.appendChild(_load_div);
			document.getElementsByTagName("html")[0].appendChild(_content_div);

		},
		stop : function(callback) {
			setTimeout(() => {
				_content_div.style.display = "none";
				(callback && typeof(callback)==="function") && callback();
			}, showInterval-( new Date() - startTime));
		},
		destroy : function() {
			if (_content_div != null) {
				document.getElementsByTagName("html")[0].removeChild(_content_div);
				_content_div = null;
			}
		}
	};
}();

// var i = 2;
// setInterval(function() {
// Loading.destroy();
// Loading.start(i);
// i++;
// i = (i == 12 ? 1 : i);
// }, 3000);
