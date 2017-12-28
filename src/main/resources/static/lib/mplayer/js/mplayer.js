/**
 * 创建mplayer播放器
 * @param {object} settings 设置信息
 */
if (typeof jQuery != 'function') {
	throw new Error("mPlayer插件需要jQuery支持");
}
(function ($) {
	function MPlayer (settings) {
		$this = this;
		$this.settings = {
			playMode:0,
			playList:0,
			playSong:5,
			autoPlay:false,
			playRotate:true,
			useDefaultStyle:true,
			lrcHeight:160,
		};
		$this.init(settings);
		$this.dataList();
		$this.bindEvents();
		$this.play($this.settings.playList,$this.settings.playSong);
	}
	// 格式化数据
	MPlayer.prototype.dataList = function(){
		$this = this;
		$this.songArr = [];
		for (var i = 0; i < $this.settings.songList.length; i++) {
			var basicSinger = '',
				basicImage = '';
				$this.songArr.push([]);
			for (var j = 0; j < $this.settings.songList[i].length; j++) {
				if ($this.settings.songList[i][j].basic) {
					// 添加列表到页面
					var listName = $this.settings.songList[i][j].name;
					$this.doms.list.append($('<li>').html(listName));
					basicSinger = $this.settings.songList[i][j].singer || '';
					basicImage = $this.settings.songList[i][j].img || '';
					$this.songArr[i].listName = listName;
				} else {
					var singerName = $this.settings.songList[i][j].singer || basicSinger,
						songImg = $this.settings.songList[i][j].img || basicImage,
						songName = $this.settings.songList[i][j].name;
					$this.songArr[i].push({
						'singer':singerName,
						'image':songImg,
						'name':songName,
						'src':$this.settings.songList[i][j].src,
						'lrc':$this.settings.songList[i][j].lrc
					});
				}
			}
		}
	};

	// 初始化
	MPlayer.prototype.init = function(settings){
		$this = this;
		$.extend($this.settings,settings);
		if (!$this.settings.containerSelector) {
			throw new Error('您未填写容器选择器(containerSelector)配置');
		}
		if (!$this.settings.songList) {
			throw new Error('您未填写歌曲列表(songList)配置');
		}
		// 输出html
		$($this.settings.containerSelector).append('<div id="mp-container" class="mp"><div class="mp-playbox"><div id="mp-cover" class="mp-cover"><img src="#" alt="cover" id="mp-cover-img"></div><div class="mp-playing-name" id="mp-playing-name"><h2>歌名<small>歌手</small></h2></div><div class="mp-progress"><div class="mp-progress-total" id="mp-play-progress-total"><div class="mp-progress-current" id="mp-play-progress-current"></div></div><div class="mp-progress-time"><span id="mp-play-progress-currenttime">00:00</span>/<span id="mp-play-progress-alltime">00:00</span></div></div><div class="mp-control"><div class="mp-buttonbox"><a href="javascript:void(0);" class="mp-button mp-hover" id="mp-button-prev">&#xe90b;</a><a href="javascript:void(0);" class="mp-button mp-hover mp-button-play" id="mp-button-play">&#xe908;</a><a href="javascript:void(0);" class="mp-button mp-hover" id="mp-button-next">&#xe90c;</a></div><div id="mp-modebox" class="mp-modebox"><a href="javascript:void(0);" class="mp-mode-button mp-hover">&#xe040;</a><a href="javascript:void(0);" class="mp-mode-button mp-hover">&#xe041;</a><a href="javascript:void(0);" class="mp-mode-button mp-hover">&#xe043;</a></div><a href="javascript:void(0);" class="mp-menu-switch mp-hover" id="mp-menu-switch" >&#xe901;</a><div class="mp-volbox"><a href="javascript:void(0);" class="mp-vol-mute mp-icon mp-hover" id="mp-vol-mute">&#xe903;</a><input type="range" id="mp-vol-range" class="mp-vol-range" min="0" max="100" value="100"></div></div></div><div id="mp-lrcbox" class="mp-lrc-box"><ul id="mp-lrc-wrap" class="mp-lrc-wrap"></ul></div><div class="mp-listbox"><div id="mp-list-select" class="mp-list-select"><h3 class="mp-list-title">播放列表</h3><ul id="mp-list-select-ul" class="mp-list-select-ul"></ul></div><ul id="mp-list" class="mp-list"></ul></div></div>');
		// 初始化dom元素
		$this.doms = {
			mp:$('#mp-container'),
			lrc:$('#mp-lrc-wrap'),
			lrcBox:$('#mp-lrcbox'),
			listtitle:$('#mp-list-select h3'),
			title:$('#mp-playing-name'),
			list:$('#mp-list-select-ul'),
			musicList:$('#mp-list'),
			process:$('#mp-play-progress-current'),
			bufferProcess:$('#mp-play-progress-buffer'),
			totalProcess:$('#mp-play-progress-total'),
			cover:$('#mp-cover-img'),
			coverBox:$('#mp-cover'),
			playbutton:$('#mp-button-play'),
			prevbutton:$('#mp-button-prev'),
			nextbutton:$('#mp-button-next'),
			modebox:$('#mp-modebox'),
			modebuttons:$('#mp-modebox').find('a'),
			listSelect:$('#mp-list-select-ul'),
			volume:$('#mp-vol-mute'),
			volRange:$('#mp-vol-range')
		};
		// 设置播放模式
		$this.doms.modebuttons.eq($this.settings.playMode).addClass('mp-deep');
		// 创建audio标签
		$this.audiodom = $('<audio></audio>').attr({
			'preload':'preload',
			'data-playmode':$this.settings.playMode,
			'data-currentlist':$this.settings.playList,
			'data-currentsong':$this.settings.playSong,
			'data-currentLrc':0,
			'data-displayList':0
		});
		// 工具函数
		$this.tools = {
			rand:function (min,max) {
				if (!max) {
					max = min;
					min = 0;
				}
				var r = 0;
				do {
					r = Math.round(Math.random() * max);
				} while(r < min);
				return r;
			},
			fillByZero:function (num,digit) {
				num = String(num);
				for (var i = num.length; i < digit; i++) {
					num = '0' + num;
				}
				return num;
			}
		};
		if ($this.settings.useDefaultStyle) {
			// 设置style
			var lrcHeight = $this.settings.lrcHeight;
			$this.doms.lrcBox.height(lrcHeight);
			var mpHeight = 160 + lrcHeight;
			$this.doms.mp.height(mpHeight);
			var margin = Math.max((lrcHeight - 40) / 2,0);
			$this.doms.lrc.css({
				marginTop:margin,
				marginBottom:margin
			});
			var listHeight = Math.min(mpHeight - 100,400);
			$('head').append('<style>.mp-list-on .mp-listbox{height:' + listHeight + 'px}</style>');
			$this.doms.musicList.height(listHeight - 40);
		}
	};



	MPlayer.prototype.bindEvents = function(){
		$this = this;
		$this.audiodom.bind('canplay', function () {
			if ($this.settings.autoPlay) {
				$(this).get(0).play();
			}
			$this.settings.autoPlay = true;
			var duration = $(this).prop('duration');
			$this.setTime(duration,'mp-play-progress-alltime');
			if ($this.settings.canPlay) {
				var l = parseInt($this.audiodom.attr('data-currentList'));
				var s = parseInt($this.audiodom.attr('data-currentSong'));
				var data = $this.songArr[l][s];
				$this.settings.canPlay({
					name:data.name,
					singer:data.singer,
					duration:duration
				});
			}
		}).bind('play', function() {
			if ($this.settings.playRotate) {
				$this.doms.coverBox.addClass('mp-cover-animate');
			}
			$this.doms.playbutton.html('&#xe909;');
		}).bind('pause', function() {
			$this.doms.coverBox.removeClass('mp-cover-animate');
			$this.doms.playbutton.html('&#xe908;');
		}).bind('ended', function() {
			$this.toNext();
		}).bind('timeupdate', function(event) {
			var timeArr = $this.audiodom.prop('timeArr');
			var currentTime = Math.round($(this).prop('currentTime')*1000);
			// 更新歌词
			for (var i = 0; i < timeArr.length; i++) {
				if (currentTime <= timeArr[i]) {
					break;
				}
			}
			i--;
			if (parseInt($this.audiodom.attr('data-currentLrc')) !== i) {
				$this.audiodom.attr('data-currentLrc', i);
				$this.doms.lrcBox.animate({scrollTop: i*32}, 500);
				$('#mp-lrc-wrap').find('.mp-lrc-current').removeClass('mp-lrc-current');
				$('#mp-lrc-'+timeArr[i]).addClass('mp-lrc-current');
			}
			// 更新进度条
			$this.doms.process.css('width', $(this).prop('currentTime')/$(this).prop('duration')*100+'%');
			// 更新显示时间
			$this.setTime($(this).prop('currentTime'),'mp-play-progress-currenttime');
		});
		// 播放按钮
		$this.doms.playbutton.on('click', function() {
			if ($this.audiodom.get(0).paused) {
				$this.audiodom.get(0).play();
			} else {
				$this.audiodom.get(0).pause();
			}
		});
		// 上一首
		$this.doms.prevbutton.on('click', function() {
			var mode = parseInt($this.audiodom.attr('data-playmode'));
			var s = parseInt($this.audiodom.attr('data-currentsong'));
			var l = parseInt($this.audiodom.attr('data-currentlist'));
			if (mode == 2) {
				$this.rand(l,s);
			} else {
				$this.prev(l,s);
			}
		});
		// 下一首
		$this.doms.nextbutton.on('click', function() {
			var mode = parseInt($this.audiodom.attr('data-playmode'));
			var s = parseInt($this.audiodom.attr('data-currentsong'));
			var l = parseInt($this.audiodom.attr('data-currentlist'));
			if (mode == 2) {
				$this.rand(l,s);
			} else {
				$this.next(l,s);
			}
		});
		// 切换模式
		$this.doms.modebox.on('click', 'a', function() {
			$this.doms.modebuttons.removeClass('mp-deep');
			$(this).addClass('mp-deep');
			$this.audiodom.attr('data-playmode', $(this).index());
		});
		// 调整进度
		$this.doms.totalProcess.click(function(event) {
			var x = event.offsetX;
			var percent = x/$(this).width();
			percent = percent > 100 ? 100 : percent;
			var alltime = $this.audiodom.prop('duration');
			$this.audiodom.prop('currentTime', alltime * percent);
		});
		// 音量调节
		$this.doms.volRange.on('change', function() {
			var vol = $(this).val()/100;
			$this.audiodom.prop('volume', vol);
			if (vol >= 0.8) {
				ico = '&#xe903';
			} else if (vol >= 0.3 && vol < 0.8) {
				ico = '&#xe904';
			} else if (vol > 0 && vol < 0.3) {
				ico = '&#xe905';
			} else if (vol === 0) {
				ico = '&#xe906';
			}
			$this.doms.volume.html(ico);
		});
		// 静音
		$this.doms.volume.on('click', function() {
			if ($this.doms.volume.hasClass('mp-disabled')) {
				$this.doms.volume.removeClass('mp-disabled');
				$this.doms.volRange.removeAttr('disabled').trigger('change');
			} else {
				$this.doms.volume.addClass('mp-disabled');
				$this.doms.volume.html('&#xe907');
				$this.audiodom.prop('volume', 0);
				$this.doms.volRange.attr('disabled','disabled');
			}
		});
		// 列表开关
		$('#mp-menu-switch').on('click', function() {
			$this.doms.mp.toggleClass('mp-list-on');
		});
		// 显示列表
		$this.doms.listtitle.on('click', function() {
			$('#mp-list-select').toggleClass('mp-list-select-on');
		});
		// 切换列表
		$this.doms.listSelect.on('click', 'li', function() {
			$this.changeList($(this).index());
			$(this).parent().parent().removeClass('mp-list-select-on');
		});
		// 切换歌曲
		$this.doms.musicList.on('click', 'li', function() {
			$this.doms.mp.removeClass('mp-list-on');
			$this.play(parseInt($this.audiodom.attr('data-displayList')),$(this).index());
		});
	};


	/**
	 * 播放指定音乐
	 * @param  {number} list 列表id
	 * @param  {number} song 歌曲id
	 */
	MPlayer.prototype.play = function(list,song){
		$this = this;
		var currentSong = $this.songArr[list][song];
		$this.audiodom.attr({
			src: currentSong.src,
			'data-currentsong': song,
			'data-currentlist':list
		});
		$this.doms.lrcBox.animate({scrollTop: 0}, 500);
		$this.doms.cover.attr('src', currentSong.image);
		$this.doms.title.html('<h2>'+ currentSong.name + '<small>'+ currentSong.singer +'</small></h2>');
		$this.changeList(list);
		$this.setLrc(currentSong.lrc);
		$this.audiodom.attr('data-currentLrc', 0);
		if ($this.settings.beforePlay) {
			$this.settings.beforePlay(currentSong);
		}
	};


	/**
	 * 更换显示列表
	 * @param  {number} list 列表序号
	 */
	MPlayer.prototype.changeList = function (list) {
		$this = this;
		$this.doms.listtitle.html($this.songArr[list].listName);
		$this.doms.musicList.html('');
		for (var i = 0; i < $this.songArr[list].length; i++) {
			$this.doms.musicList.append($('<li>').html($this.songArr[list][i].name + ' - ' + $this.songArr[list][i].singer).addClass('mp-list-song'));
		}
		$this.audiodom.attr('data-displayList', list);
		if (parseInt($this.audiodom.attr('data-currentlist')) === list) {
			var song = $this.audiodom.attr('data-currentsong');
			$('#mp-list').find('li').eq(song).addClass('mp-list-song-current');
		}
	};

	/**
	 * 设置显示时间
	 * @param {float} time 时间(s)
	 * @param {string} ele  显示元素id
	 */
	MPlayer.prototype.setTime = function (time,ele) {
		$this = this;
		var tools = $this.tools;
		var min = tools.fillByZero(parseInt(time/60),2);
		time -= min*60;
		var second = tools.fillByZero(Math.round(time),2);
		$('#'+ele).html(min+':'+second);
	};

	/**
	 * 设置显示歌词
	 * @param {string} lrc 歌词字符串
	 */
	MPlayer.prototype.setLrc = function (lrc) {
		$this = this;
		// 匹配歌词的正则表达式
		var reg = /\[(\d{2}):(\d{2})\.(\d{2})\]([^\n\r]*)/g;
		var lrcObj = {};
		while (true) {
			var result = reg.exec(lrc);
			if (!result) {
				break;
			}
			var time = Math.round((parseInt(result[1])*60 + parseInt(result[2]) + parseInt(result[3])/100)*1000);
			lrcObj[time] = $.trim(result[4]);
		}
		var timeArr = [];
		// 将歌词输入到歌词框
		$this.doms.lrc.html('');
		for (var ctime in lrcObj) {
			$this.doms.lrc.append($('<li>').html(lrcObj[ctime] || " ").addClass('mp-lrc').attr('id', 'mp-lrc-'+ctime));
			timeArr.push(parseInt(ctime));
		}
		$this.doms.lrc.children().eq(0).addClass('mp-lrc-current');
		$this.audiodom.prop('timeArr', timeArr);
	};

	// 上一曲
	MPlayer.prototype.prev = function (l,s) {
		$this = this;
		s--;
		if (s < 0) {
			s = $this.songArr[l].length-1;
		}
		$this.play(l,s);
	};
	// 下一曲
	MPlayer.prototype.next = function (l,s) {
		$this = this;
		s++;
		if (s >= $this.songArr[l].length) {
			s = 0;
		}
		$this.play(l,s);
	};
	// 随机播放
	MPlayer.prototype.rand = function (l,s) {
		$this = this;
		var ss = s;
		do{
			s = $this.tools.rand($this.songArr[l].length-1);
		} while (s === ss);
		
		$this.play(l,s);
	};
	// 单曲循环
	MPlayer.prototype.repeat = function (l,s) {
		$this = this;
		$this.play(l,s);
	};
	// 正常下一首
	MPlayer.prototype.toNext = function () {
		$this = this;
		var mode = parseInt($this.audiodom.attr('data-playmode'));
		var s = parseInt($this.audiodom.attr('data-currentsong'));
		var l = parseInt($this.audiodom.attr('data-currentlist'));
		switch (mode) {
			case 1:
				$this.repeat(l,s);
				break;
			case 2:
				$this.rand(l,s);
				break;
			default:
				$this.next(l,s);
				break;
		}
	};
	window.MPlayer = MPlayer;
})(jQuery);

