$(function(){
	//登录页点击管理员登录入口按钮效果
	$(".loginEntranceBtn").click(function(){
		$(this).hide();
		$(".loginBox").animate({"width":491},500);
		$(".tasteBox").animate({"marginLeft":176},500);
		return false;
	});
	
	//下拉框效果
	$(".comboboxHandle").click(function(){
		$(this).closest(".combobox").toggleClass("beOpened");
		$(this).next().toggle();
	});
	$(".comboboxBox a").click(function(){
		var tempHtml = $(this).html();
		$(this).closest(".combobox").find(".comboboxHandle").attr("value",$(this).attr("value"));
		$(this).closest(".combobox").find(".comboboxHandle").html(tempHtml);
		$(this).closest(".combobox").toggleClass("beOpened");
		$(this).closest(".comboboxBox").toggle();

		return false;
	});
	
	//点击空白区域关闭相关弹窗
	$(document).click(function(e){
		var $target = $(e.target);
		if($target.closest(".combobox").size() === 0){
			$(".beOpened").removeClass("beOpened");
			$(".comboboxBox").hide();
		}			  
	});
	
	//表格隔行换色
	$(".grid tbody tr:even").css("backgroundColor","#ebf2fc");
	
	//图片中的小叉子点击效果
	$(".removePicBtn").click(function(){
		$(this).closest("dd").remove();
		return false;
	});
	
	//左侧导航点击效果
	$(".leftSide a").click(function(){
		$(this).parent().addClass("slt").siblings().removeClass("slt");
		//return false;
	});
	
	//顶部logo鼠标经过效果
	$(".logoBox").hover(function(){
		$(this).find(".logoDia").fadeIn();
	},function(){
		$(this).find(".logoDia").fadeOut();
	});
	
	//点击新建模型弹出提示弹窗
	$(".createModelBtn").click(function(){
		$(".overlay").fadeIn();
		$(".tipDia").fadeIn();
//		return false;
	});

	//点击弹出提示弹窗中的关闭按钮和确定按钮
	$(".closeDia,.tipDiaSureBtn").click(function(){
		$(".overlay").fadeOut();
		$(".tipDia").fadeOut();
		return false;
	});

})


//主图片展示区域的图片缩放方法
function imgScale(id,fnc){
	$("#"+id).find('img').get(0).onload = function () {
		//console.log($("#"+id).width());
		//console.log($("#"+id).height());
		//console.log($("#"+id+" img").width());
		//console.log($("#"+id+" img").height());
		var img = $("#"+id).find('img').eq(0);
		img.removeAttr('style');
		
		var containerW = $("#"+id).width();
		var containerH = $("#"+id).height();
		var imgW = $("#"+id+" img").width();
		var imgH = $("#"+id+" img").height();
			
		
		var r =  containerW / containerH;
		//获取图片宽高呗
		var imgProportion = img.eq(0).width() / img.eq(0).height();
		var offset = {"direction":"","value":""};
		
		$(".faceImg_2 img").fadeIn();
		
		if (imgProportion > r) {
			img.css({'width':'100%'});
			img.css({"marginTop":(containerH-$("#"+id+" img").height())/2+"px"});
			offset = {"direction":"top","value":parseInt(img.css("marginTop"))};
		} else{
			img.css({'height':  containerH+'px'});
			img.css({"marginLeft":(containerW-$("#"+id+" img").width())/2+"px"});
			offset = {"direction":"left","value":parseInt(img.css("marginLeft"))};
		}
		//原始图片和缩放后图片的宽度（或者也可以是高度）的比例
		var scalePer = imgW/$("#"+id+" img").width();
		if(fnc){
			fnc(scalePer,offset,id);
		}
	};
}

//修改 主图片展示区域的图片缩放方法
function imgScaleNew(id,fnc,data){
	$("#"+id).find('img').get(0).onload = function () {
		//console.log($("#"+id).width());
		//console.log($("#"+id).height());
		//console.log($("#"+id+" img").width());
		//console.log($("#"+id+" img").height());
		var img = $("#"+id).find('img').eq(0);
		img.removeAttr('style');
		
		var containerW = $("#"+id).width();
		var containerH = $("#"+id).height();
		var imgW = $("#"+id+" img").width();
		var imgH = $("#"+id+" img").height();
			
		
		var r =  containerW / containerH;
		//获取图片宽高呗
		var imgProportion = img.eq(0).width() / img.eq(0).height();
		var offset = {"direction":"","value":""};
		
		if (imgProportion > r) {
			img.css({'width':'100%'});
			img.css({"marginTop":(containerH-$("#"+id+" img").height())/2+"px"});
			offset = {"direction":"top","value":parseInt(img.css("marginTop"))};
		} else{
			img.css({'height':  containerH+'px'});
			img.css({"marginLeft":(containerW-$("#"+id+" img").width())/2+"px"});
			offset = {"direction":"left","value":parseInt(img.css("marginLeft"))};
		}
		//原始图片和缩放后图片的宽度（或者也可以是高度）的比例
		var scalePer = imgW/$("#"+id+" img").width();
		if(fnc){
			fnc(scalePer,offset,id,data);
			//清空原始数据
			data=[];
		}
	};
}




//计算遮罩的宽度
function calculateWidth(arg){
	return arg.coordinate[1][0] - arg.coordinate[0][0];
}

//计算遮罩的高度度
function calculateHeight(arg){
	return arg.coordinate[2][1] - arg.coordinate[1][1];
}

//计算遮罩的top
function calculateTop(arg){
	return arg.coordinate[0][1];
}

//计算遮罩的left
function calculateLeft(arg){
	return arg.coordinate[0][0];
}

//根据缩放比例，计算遮罩新位置和尺寸的方法
function calculateNewData(numerator,scalePer){
	return numerator/scalePer;
}

//判断遮罩层的鼠标经过效果弹窗是否已经超出了图片可视区域的最右侧，如果是，将right设置为0
//判断遮罩层的鼠标经过效果弹窗是否已经超出了图片可视区域的最上侧，如果是，则将这个框在下方显示
//鼠标经过遮罩，显示隐藏的提示框
function judgeShadowLocation(){
	var shadowBoxDom = $(".imgShadow .shadowBox");
	var shadowBoxNum = shadowBoxDom.size();
	for(var j = 0 ; j < shadowBoxNum ; j++){
		if(shadowBoxDom.eq(j).position().left + shadowBoxDom.eq(j).find(".shadowTip").width() > $(".imgShadow").width()){
			shadowBoxDom.eq(j).find(".shadowTip").css({"left":"auto","right":"0px"});
		}
		if(shadowBoxDom.eq(j).position().top < 57){
			shadowBoxDom.eq(j).find(".shadowTip").css({"top":"73px"});
			shadowBoxDom.eq(j).find(".guideicon").css({"top":"-19px"}).addClass("guideicon_1"); //改52px
		}
	}

	//鼠标经过遮罩，显示隐藏的提示框
	var time_1 = null;
	var time_2 = null;
	$(".shadowBox").hover(function(){
		var that = this;
		time_1 = setTimeout(function(){$(that).find(".shadowTip").show().end().find(".guideicon").show();},500);
	},function(){
		var that = this;
		clearTimeout(time_1);
		time_2 = setTimeout(function(){$(that).find(".shadowTip").hide().end().find(".guideicon").hide();},500)
		$(that).find(".shadowTip").hover(function(){
			clearTimeout(time_2);
			$(this).show();
		},function(){
			$(this).hide().prev(".shadowBg").find(".guideicon").hide();;
		});
	});

}
	