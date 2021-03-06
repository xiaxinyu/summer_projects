<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/system/template/header.jsp"%>
<script type="text/javascript" src="${BasePath}/scripts/accumulation.js"></script>
<script type="text/javascript" src="${BasePath}/datasource/endowment-datasource.js"></script>
<script type="text/javascript" src="${BasePath}/plugins/easyui/datagrid-groupview.js"></script>

<style type="text/css">
    #fm{margin:0;padding:10px 30px;}
    .ftitle{font-size:14px;font-weight:bold;padding:5px 0;margin-bottom:10px;border-bottom:1px solid #ccc;}
    .fitem{margin-bottom:5px;}
    .fitem label{display:inline-block;width:120px;}
    .fitem input{width:160px;}
</style>
<body class="easyui-layout">
	<div data-options="region:'north'" style="height: 32px">
		<div style="margin-top: 3px;">
			<span class="item_intervel">
	           	<label>Transaction date:</label> 
	           	<input id="dateStart" class="easyui-datebox" style="width: 100px;"  data-options="onSelect:searchAccumulations"/> 
	           	~ 
	           	<input id="dateEnd" class="easyui-datebox" style="width: 100px;" data-options="onSelect:searchAccumulations" />
			</span>
	                
	        <span class="item_intervel">
				<label>Unit Name：</label>
				<input id="cmbUnitName" class="easyui-combobox" 
					data-options="data:endowment_datasource.units,valueField:'id',textField:'text',editable:false,onSelect:searchAccumulations"
					style="width: 250px;">
			</span>
	          
			<span class="item_intervel">
				<input id="txtDemo" class="easyui-textbox" style="width:120px"> 
			</span>
			
			<span class="item_intervel">
			    <a class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="searchAccumulations()">Search</a>  
			    <a class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="">Export</a>
			</span>
          </div>
	</div>
	
	<div data-options="region:'center',border:false">
		<table id="datagrid"  fit="true"
			data-options="rownumbers:true,singleSelect:true,pagination:true,pageSize:50">
			<thead>
				<tr>
					<th data-options="field:'id',hidden:true">ID</th>
					<th data-options="field:'unitNo',width:250,align:'left',formatter:fmt.unitNo,editor:{type:'combobox',options:{valueField:'id',textField:'text',data:endowment_datasource.units}}">Unit Name</th>
					<th data-options="field:'time',width:80,align:'center',editor:{type:'textbox'}">Time</th>
					<th data-options="field:'year',width:80,align:'center',hidden:true">Year</th>
					<th data-options="field:'payBase',width:100,align:'left',editor:{type:'numberbox',options:{precision:2}},hidden:true">Pay Base</th>
					<th data-options="field:'personalPay',width:100,align:'left',editor:{type:'numberbox',options:{precision:2}}">Personal Pay</th>
					<th data-options="field:'unitPay',width:100,align:'left',editor:{type:'numberbox',options:{precision:2}}">Unit Pay</th>
					<th data-options="field:'totalPay',width:100,align:'left',editor:{type:'numberbox',options:{precision:2}}">Total Pay</th>
					<th data-options="field:'personalReserved',width:100,align:'left',editor:{type:'numberbox',options:{precision:2}}">Personal Reserved</th>
					<th data-options="field:'demoArea',width:420,align:'left',editor:{type:'textbox'}">Description</th>
				</tr>
			</thead>
		</table>
	</div>
</body>

<div style="display: none;">
	<div id="dlg" class="easyui-dialog" style="width:400px;height:340px;padding:10px 20px" closed="true" buttons="#dlg-buttons">
	    <form id="fm" method="post" novalidate>
	        <div class="fitem">
	            <label>Unit Name:</label>
	            <input id="unitNo" name="unitNo" class="easyui-combobox" data-options="data:endowment_datasource.units,valueField:'id',textField:'text',editable:false,validType:'cmbSelect'" required="true">
	        </div>
	        <div class="fitem">
	            <label>Time:</label>
	            <input name="time" class="easyui-textbox" required="true">
	        </div>
	        <!-- <div class="fitem">
	            <label>Pay Base:</label>
	            <input name="payBase" class="easyui-numberbox" data-options="min:0,precision:2" required="true">
	        </div> -->
	        <div class="fitem">
	            <label>Personal Pay:</label>
	            <input name="personalPay" class="easyui-numberbox" data-options="min:0,precision:2" required="true">
	        </div>
	        <div class="fitem">
	            <label>Unit Pay:</label>
	            <input name="unitPay" class="easyui-numberbox" data-options="min:0,precision:2" required="true">
	        </div>
	        <div class="fitem">
	            <label>Total Pay:</label>
	            <input name="totalPay" class="easyui-numberbox" data-options="min:0,precision:2" required="true">
	        </div>
	        <div class="fitem">
	            <label>Personal Reserved:</label>
	            <input name="personalReserved" class="easyui-numberbox" data-options="min:0,precision:2" required="true">
	        </div>
	        <div class="fitem">
	            <label>Description:</label>
	            <input name="demoArea" class="easyui-textbox" >
	        </div>
	    </form>
	</div>
	<div id="dlg-buttons">
	    <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveAccumulation()" style="width:90px">Save</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')" style="width:90px">Cancel</a>
	</div>
</div>
    
<script type="text/javascript">
var datagrid = '#datagrid';
var dataform = '#dataform';
var editIndex = undefined;
var save_url = app_url + '/accumulation/add';
var query_url = app_url + '/accumulation/getAccumulations';
var dialog = '#dlg';
var form = 'form';
$(function() {
	$(datagrid).datagrid({
		method:'post',
		url: query_url, 
		groupField:'year',
		view: groupview,
		groupFormatter:function(value, rows){
			if(rows != null && rows.length > 0){
				var personalPays = 0,unitPays = 0,totalPays = 0,personalReserveds = 0;
				for(var i=0;i<rows.length;i++){
					personalPays += parseFloat(rows[i].personalPay);
					unitPays += parseFloat(rows[i].unitPay);
					totalPays += parseFloat(rows[i].totalPay);
					personalReserveds += parseFloat(rows[i].personalReserved);
				}
			}
			var space = "&nbsp;&nbsp;&nbsp;";
			return value + space + "Personal Pay:"+ Math.round(personalPays) + space + "Unit Pay:" + Math.round(unitPays) + space + "Total Pay:" + Math.round(totalPays)  + space + " Personal Reserve:" + Math.round(personalReserveds) + space + rows.length + "Item(s)";
		},
		onDblClickRow:function(rowIndex, rowData){
			$(this).datagrid('cancelEdit', editIndex);
			editIndex = rowIndex;
			$(this).datagrid('beginEdit', rowIndex);
		},
		onClickRow:function(rowIndex, rowData){
			$(this).datagrid('cancelEdit', editIndex);
		}
	});
	
	var pager = $(datagrid).datagrid('getPager'); // get the pager of datagrid
	pager.pagination({buttons : [ {iconCls : 'icon-add',handler : addAccumulation},
		            {iconCls : 'icon-copy',handler : copyAccumulation},
		            {iconCls : 'icon-cancel',handler : deleteAccumulation}, 
		            {iconCls : 'icon-save',handler : updateAccumulation} , 
		            {iconCls : 'icon-undo',handler : cancelEdit	}]
	});
});

$(function(){
	$.extend($.fn.validatebox.defaults.rules, {    
	    cmbSelect: {    
	        validator: function(value, param){  
	        	var flag = value != '' && value != '== Choice =='; 
	            return flag;    
	        },    
	        message: 'Please select one item'   
	    }    
	}); 
});

function addAccumulation(){
	$(dialog).dialog('open').dialog('center').dialog('setTitle','New Accumulation');
    $(form).form('clear');
}

function saveAccumulation(){
    $(form).form('submit',{
        url: save_url,
        onSubmit: function(){
        	var flag = $(this).form('validate');
	        return flag;
        },
        success: function(result){
            var result = eval('('+result+')');
            if (result.returnCode == 'success'){
            	$(dialog).dialog('close');        
                $(datagrid).datagrid('reload');   
            } else {
            	app.messager.show({title:'Fail', msg: result.returnMessage});
            }
        }
    });
}

function updateAccumulation() {
	var rowData = $(datagrid).datagrid('getSelected');
	var rowIndex = $(datagrid).datagrid('getRowIndex',rowData);
	
	var unitNo_Editor = $(datagrid).datagrid('getEditor', {index:rowIndex,field:'unitNo'});
	var unitNo = $(unitNo_Editor.target).combobox('getValue');
	
	var time_Editor = $(datagrid).datagrid('getEditor', {index:rowIndex,field:'time'});
	var time = $(time_Editor.target).textbox('getValue');
	
	var payBase_Editor = $(datagrid).datagrid('getEditor', {index:rowIndex,field:'payBase'});
	var payBase = $(payBase_Editor.target).numberbox('getValue');
	
	var personalPay_Editor = $(datagrid).datagrid('getEditor', {index:rowIndex,field:'personalPay'});
	var personalPay = $(personalPay_Editor.target).numberbox('getValue');
	
	var unitPay_Editor = $(datagrid).datagrid('getEditor', {index:rowIndex,field:'unitPay'});
	var unitPay = $(unitPay_Editor.target).numberbox('getValue');
	
	var totalPay_Editor = $(datagrid).datagrid('getEditor', {index:rowIndex,field:'totalPay'});
	var totalPay = $(totalPay_Editor.target).numberbox('getValue');
	
	var personalReserved_Editor = $(datagrid).datagrid('getEditor', {index:rowIndex,field:'personalReserved'});
	var personalReserved = $(personalReserved_Editor.target).numberbox('getValue');
	
	var demoArea_Editor = $(datagrid).datagrid('getEditor', {index:rowIndex,field:'demoArea'});
	var demoArea = $(demoArea_Editor.target).textbox('getValue');
	
	var params = {'id':rowData.id,'unitNo':unitNo,'time':time,'payBase':payBase,'personalPay':personalPay,'unitPay':unitPay,'totalPay':totalPay,'personalReserved':personalReserved,'demoArea':demoArea};
	accumulation.updateAccumulation(params, function(data){
    	if(data.returnCode == 'success'){
    		$(datagrid).datagrid('reload');
    	}else{
    		app.messager.show({title:'Fail', msg: data.returnMessage});
    	}
    });
}

function copyAccumulation(){
	var rowData = $(datagrid).datagrid("getSelected");
	if(rowData != null){
		var params = {'unitNo':rowData.unitNo,'time':rowData.time,'payBase':rowData.payBase,'personalPay':rowData.personalPay,'unitPay':rowData.unitPay,'totalPay':rowData.totalPay,'personalReserved':rowData.personalReserved,'demoArea':rowData.demoArea};
		accumulation.copyAccumulation(params, function(data){
	    	if(data.returnCode == 'success'){
	    		$(datagrid).datagrid('reload');
	    	}else{
	    		app.messager.show({title:'Fail', msg: data.returnMessage});
	    	}
	    });
	}else{
		app.messager.show({title:'Copy accumulation record', msg:'Please select one accumulation record!'});
	}
}

function deleteAccumulation(){
	var rowData = $(datagrid).datagrid("getSelected");
	if(rowData != null){
		$.messager.confirm('Confirm', 'Do you want to delete this accumulation record？', function(r){
			if (r){
				accumulation.deleteAccumulation({'id':rowData.id}, function(data){
			    	if(data.returnCode == 'success'){
			    		$(datagrid).datagrid('reload');
			    		app.messager.show({title:'Success', msg:'Success to delete this accumulation record!'});
			    	}else{
			    		app.messager.show({title:'Fail', msg: data.returnMessage});
			    	}
			    },function(e){
					app.messager.show({title:'Error', msg: e.message});
				})
			}
		});
	}else{
		app.messager.show({title:'Delete accumulation record', msg:'Please select one accumulation record!'});
	}
}
	
var fmt = {
	unitNo : function(value,row,index){
    	if (value == 0) {
	        return;
	 	}
		var units = endowment_datasource.units;
		for (var i = 0; i < units.length; i++) {
			if (units[i].id == value) {
		    	return units[i].text;
		    }
		}
	}
}

function cancelEdit() {
	var rows = $(datagrid).datagrid('getRows');
	for (var i = 0; i < rows.length; i++) {
		$(datagrid).datagrid('cancelEdit', i);
	}
}
</script>

<script type="text/javascript">
function searchAccumulations() {
	
}
</script>

<%@ include file="/system/template/footer.jsp"%>
