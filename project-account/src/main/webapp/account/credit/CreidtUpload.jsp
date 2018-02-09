<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/system/template/header.jsp"%>
<script type="text/javascript" src="${BasePath}/plugins/uploadify/jquery.uploadify.min.js" ></script>
<link rel="stylesheet" type="text/css" href="${BasePath}/plugins/uploadify/uploadify.css">
 <body>
 <input type="file" id="uploadify" name="uploadify">
    <div id="fileQueue"></div>
    <a href="javascript:$('#uploadify').uploadify('upload','*')">Strart upload</a>
    <a href="javascript:$('#uploadify').uploadify('cancel')">Cancel upload</a>

<script type="text/javascript">		
	$(document).ready(function() {
		$("#uploadify").uploadify({  
			'auto'           : false, //選完文件後是否自動上傳 
			'swf'            : '${BasePath}/plugins/uploadify/uploadify.swf',
			'uploader'       : '${BasePath}/credit/upload',
			'queueID'        : 'fileQueue',//隊列id,用來展示上傳進度的  
			'queueSizeLimit' : 3,  //同時上傳文件的個數
			'fileTypeDesc'   : 'upload credit bill',  
			'fileTypeExts'   : '*.xls;*.txt;', //控制可上傳文件的擴展名
			'multi'          : true,  //允許多文件上傳
			'buttonText'     : 'upload credit bill',//按鈕上的文字
			'fileSizeLimit' : '200MB', //設置單個文件大小限制 
			'fileObjName' : 'uploadify',  //<input type="file"/>的name
			'method' : 'post',
			'removeCompleted' : true,//上傳完成後自動刪除隊列
			'onUploadSuccess' : function(file, data, response){//單個文件上傳成功觸發
	                               //data就是action中返回來的數據
                                       文件傳上去後,你可以把文件的名稱等信息寫回來, 把值賦给隱藏的input,再提交過去將文件的路徑等其他信息存到數據庫,實現異步上傳,而且你還可以在這裏動態創建一個刪除按鈕, 點擊時發送ajax請求,把文件名發送過去,把剛上傳的視頻刪了
			},
			'onQueueComplete' : function(){//所有文件上傳完成
				
			}
		});  		    
	});			
</script>
</body>
<%@ include file="/system/template/footer.jsp"%>
