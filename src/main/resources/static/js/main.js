// index.html
// 屏幕切到移动端的时候要把导航栏的弹出形式改一下
$('#toggleMenu').click(function () {
    $('.nav-item').toggleClass('my-mobile-hide')
});

// blog.html
// 赞赏功能的弹出效果
$('#payBtn').popup({
    popup: $('.payCls.popup'),
    on: 'hover',
    position: 'bottom center'
});

// about.html
// 个人联系方式的弹出
$('.gmailCls').popup();
$('.qqCls').popup();
$('.weixinCls').popup();
$('.githubCls').popup();
