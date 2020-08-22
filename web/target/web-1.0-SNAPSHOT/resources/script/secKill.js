//javaScript 模块化
var secKill = {
    //秒杀相关ajax的url
    URL:{
        now: function () {
            return "/web/secKill/time/now";
        },
        exposer: function (secKillId) {
            return "/web/secKill/"+secKillId+"/exposer";
        },
        execution: function (secKillId,md5) {
            return "/web/secKill/"+secKillId+"/"+md5+"/execution"
        }
    },
    //详情页秒杀逻辑
    validatePhone: function(phone){
        if(phone && phone.length == 11 && !isNaN(phone)){
            return true;
        }else{
            return false;
        }
    },
    handleSecKill: function(secKillId,node){
        node.hide().html("<button class='btn btn-primary btn-lg' id='killBtn'>开始秒杀</button>")
        $.post(secKill.URL.exposer(secKillId),{},function (data) {
            if(data && data['success']){
                var exposer = data['data'];
                if(exposer.exposed){
                    var killUrl = secKill.URL.execution(secKillId,exposer.md5);
                    //绑定一次点击事件
                    $("#killBtn").one("click",function(){
                        $(this).addClass("disabled");
                        $.post(killUrl,{},function (data) {
                            if(data && data['success']){
                                var result = data.data;
                                var state = result.state;
                                var stateInfo = result.stateInfo;
                                node.html("<span class='label label-success'>"+stateInfo+"</span>");
                            }
                        });
                    });
                    node.show();
                }else{
                    var now = exposer.now;
                    var start = exposer.start;
                    var end = exposer.end;
                    secKill.countdown1(secKillId,now,start,end);
                }
            }else{
                console.log(data);
            }
        })
    },
    countdown1: function(secKillId,nowTime,startTime,endTime){
        var secKillBox = $("#secKill-box");
        if(nowTime > endTime){
            secKillBox.html("秒杀结束!");
        }else if(nowTime < startTime){
            var killTime = new Date(startTime+1000);
            secKillBox.countdown(killTime,function (event) {
                var format = event.strftime("秒杀计时：%D天 %H时 %M分 %S秒");
                secKillBox.html(format);
            }).on("finish.countdown",function () {
                secKill.handleSecKill(secKillId,secKillBox);
            })
        }else{
            secKill.handleSecKill(secKillId,secKillBox);
        }
    },
    detail:{
        //详情页初始化
        init: function(param){
            //手机验证和登录，计时交互
            //cookie中查找手机号
            var killPhone = $.cookie("killPhone");
            var startTime = param.startTime;
            var endTime = param.endTime;
            var secKillId = param.secKillId;
            if(! secKill.validatePhone(killPhone)) {
                var killPhoneModal = $("#killPhoneModal");
                killPhoneModal.modal({
                    //显示弹出层
                    show: true,
                    //禁止位置关闭
                    backdrop: "static",
                    keyboard: false
                });
                $("#killPhoneBtn").click(function () {
                    var inputPhone = $("#killPhoneKey").val();
                    if(secKill.validatePhone(inputPhone)){
                        $.cookie("killPhone",inputPhone,{expires:7,path:"/web/secKill"});
                        window.location.reload();
                    }else{
                        $("#killPhoneMessage").hide().html('<label class="label label-danger">请重新确认手机号</label>').show(300);
                    }
                });
            }else{
                //已经登录，计时交互
                $.get(secKill.URL.now(),{},function (data) {
                    if(data && data['success']){
                        var nowTime = data['data'];
                        //判断时间
                        secKill.countdown1(secKillId,nowTime,startTime,endTime);
                    }else{
                        console.log(data);
                    }
                });
            }
        }
    }
}