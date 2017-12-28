# mPlayer音乐播放器
[Github主页](https://github.com/0936zz/mplayer) | [下载地址](http://0936zz.github.io/mplayer/download.html) | [插件主页](http://0936zz.github.io/mplayer) | [演示地址](http://0936zz.github.io/mplayer/demo.html)

![截图][1]

***
## 使用方法
请在您的网页中插入以下内容
```html 
<script>
new Mplayer({
    containerSelector:'body',
	songList:[
    	[
        	{
        		"basic":true,
        		"name":"专辑名/列表名",
        		"singer":"列表公用歌手",
        		"img":"列表公用图片"
        	},
        	{
        		"name":"歌名",
        		"img":"本歌曲图片",
        		"src":"歌曲路径",
        		"lrc":"lrc歌词,每句用'\n'隔开"
        	}
        	// 可以添加更多歌曲
        ]
        // 可以添加更多列表
    ]
});
</script>
```
更多参数请参考[文档](http://0936zz.github.io/mplayer/documentation.html)
***

> ## 优点
> 
> 1. 歌词显示功能
> 2. 三种播放模式切换
> 3. 多列表支持
> 4. 易于使用，不用写任何HTML和CSS，简单配置就能使用
> 5. 超高度自定义，完全可以自己编写CSS覆盖原有样式

***

## 更新日志

* V1.3.3 *
1. 新增大量配置项
2. 减小CSS文件体积，压缩版仅5KB
3. 增加静音功能

* V1.2.3
1. 重写代码逻辑

* V1.2.1
1. 修复一处bug

* V1.2.0
1. 增强用户体验
2. 优化代码
3. 新增是否自动播放

<span style="color:red">* 代表重大更新</span>

## 即将更新
1. 兼容IE8+
其他功能待定


[1]:http://0936zz.github.io/mplayer/static/screenshot.png