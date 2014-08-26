<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script language="javascript" src="<%=request.getContextPath() %>/js/jquery/jquery.min.js"></script> 
<script language="javascript" src="<%=request.getContextPath() %>/js/jquery/jquery.easyui.min.js"></script> 
<link href="<%=request.getContextPath() %>/css/demo.css" rel="stylesheet" type="text/css" />
	<style type="text/css">
		.dd-demo{
			width:60px;
			height:60px;
			border:2px solid #d3d3d3;
			position:absolute;
		}
		.proxy{
			border:1px dotted #333;
			width:60px;
			height:60px;
			text-align:center;
			background:#fafafa;
		}
		#dd1{
			background:#E0ECFF;
			left:20px;
			top:20px;
		}
		#dd2{
			background:#8DB2E3;
			left:100px;
			top:20px;
		}
		#dd3{
			background:#FBEC88;
			left:180px;
			top:20px;
		}
	</style>
	<script>
		$(function(){
			$('#dd1').draggable();
			$('#dd2').draggable({
				proxy:'clone'
			});
			$('#dd3').draggable({
				proxy:function(source){
					var p = $('<div class="proxy">proxy</div>');
					p.appendTo('body');
					return p;
				}
			});
		});
	</script>
</head>
<body>
	<div id="dd1" class="dd-demo"></div>
	<div id="dd2" class="dd-demo"></div>
	<div id="dd3" class="dd-demo"></div>
</body>
</html>