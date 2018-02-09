<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/system/template/header.jsp"%>
<script type="text/javascript" src="${BasePath}/scripts/credit.js"></script>
<script type="text/javascript" src="${BasePath}/datasource/creidt-datasource.js"></script>
<script type="text/javascript" src="${BasePath}/datasource/consume-datasource.js"></script>
<link rel="stylesheet" type="text/css" href="${BasePath}/css/default/credit/CreditBill.css">

<body class="easyui-layout">
	<div data-options="region:'north'" style="height: 32px">
		<div style="margin-top: 3px;">
			<span class="item_intervel">
	           	<label>Transaction date:</label> 
	           	<input id="dateStart" class="easyui-datebox" style="width: 100px;"  data-options="onSelect:statistic"/>
	           	~ 
	           	<input id="dateEnd" class="easyui-datebox" style="width: 100px;" data-options="onSelect:statistic" />
			</span>
	          
	        <span class="item_intervel">
				<label>Card type：</label>
				<input id="cmbCardTypes" class="easyui-combobox" 
					data-options="data:credit_datasource.cardTypes,valueField:'id',textField:'text',onSelect:statistic"
					style="width: 130px;">
			</span>
	
	          <span class="item_intervel">
	          	<label>Consume type：</label> 
	          	<select id="cmbConsume" class="easyui-combotree" data-options="data:consume_datasource,onChange:statistic" multiple 
	          		style="width:200px;"></select>
	          </span>
	          
	          <span class="item_intervel">
	          	<input id="txtDemo" class="easyui-textbox" style="width:120px"> 
	          </span>
	          
	          <span class="item_intervel">
	              <a class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="statistic()">Statistic</a>  
	          </span>
          </div>
	</div>
	<div id="chartArea" data-options="region:'center',border:false">
		<div class="easyui-layout" fit="true">
	        <div data-options="region:'west',split:true" title="Pie" style="width:720px;">
	        	<div id="main" style="margin-top: 20px;"></div>
	        </div>
	        <div id="detailGridArea" data-options="region:'center',title:'Detail - monday'">
	        	<table id="datagrid"  fit="true"
					data-options="rownumbers:true,singleSelect:true,pagination:true,pageSize:50,border:false">
					<thead>
						<tr>
							<th data-options="field:'id',hidden:true">ID</th>
							<th data-options="field:'cardId',width:120">Card type</th>
							<th data-options="field:'transactionDate',width:80,align:'center',formatter:app.date.format">Book date</th>
							<th data-options="field:'balanceMoney',width:100,formatter:app.money.rmb">Balance money</th>
							<th data-options="field:'demoArea',width:420,align:'left'">Description</th>
						</tr>
					</thead>
				</table>
	        </div>
        </div>
	</div>
</body>	
<script type="text/javascript">
$(function(){
	$('#main').height($('#chartArea').height()-80);
	statistic();
	getCredits('monday');
});

function getParams(){
	//Transaction date
	var dateStart = $('#dateStart').datebox('getValue');
	var dateEnd = $('#dateEnd').datebox('getValue');
	if(dateStart!=null && dateStart!=''){
		dateStart = $.trim(dateStart);    
	}else{
		dateStart = '';
	}
	if(dateEnd!=null && dateEnd != ''){
		dateEnd = $.trim(dateEnd);    
	}else{
		dateEnd = '';
	}
	
	//Card type
	var cardtype = $('#cmbCardTypes').combobox('getValue');
	if(cardtype!=null && cardtype!="" && cardtype != "0"){
		cardtype = $.trim(cardtype);
	}else{
		cardtype = '';
	}
	
	//Consume type
	var consume = $('#cmbConsume').combotree('getValues');
	if(consume!=null && consume!=""){
		consume = $.trim(consume.join(','));
	}else{
		consume = '';
	}
	
	//demo
	var demoArea = $.trim($('#txtDemo').textbox('getValue'));
	var params = {'transactionDateStartStr':dateStart,'transactionDateEndStr':dateEnd,'consumptionType':'1','consumeID':consume,'cardTypeName':cardtype,'demoArea':demoArea};
	return params;
}

function statistic() {
	var params = getParams();
	credit.weekConsumeReport(params, show);
}
</script>

<script type="text/javascript">
function show(data){
	if(data.returnCode == 'success'){
		var json = null;
		try{
			json = $.parseJSON(data.returnMessage);
		}catch(e){
			json = null;
			app.messager.show({title:'Fail', msg: 'Parse return data fail!'});
			return;
		}
		if(json != null){
			var rows = json;
			var weekNames = new Array();
			var rowDatas = new Array();
			for (var i = 0; i < rows.length; i++) {
				var row = rows[i];
				var key = row.key;
				var value = parseFloat(row.value);
				weekNames.push(key);
				rowDatas.push({name: key, value: value});
			}
			render(weekNames,rowDatas);
		} else {
			app.messager.show({title : 'Fail', msg : data.returnMessage});
		}
	}
}

function render(weekNames,rowDatas){
	// 使用
	require([ 'echarts', 'echarts/chart/pie' , 'echarts/chart/funnel'
	], function(ec) {
		// 基于准备好的dom，初始化echarts图表
		var myChart = ec.init(document.getElementById('main'));

		option = {
		    title : {
		        text: 'Week Consume Statistic',
		        subtext: 'Money',
		        x:'center'
		    },
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		    legend: {
		        orient : 'vertical',
		        x : 'left',
		        data: weekNames
		    },
		    toolbox: {
		        show : true,
		        feature : {
		            mark : {show: true},
		            dataView : {show: true, readOnly: false},
		            magicType : {
		                show: true, 
		                type: ['pie', 'funnel'],
		                option: {
		                    funnel: {
		                        x: '25%',
		                        width: '50%',
		                        funnelAlign: 'left',
		                        max: 1548
		                    }
		                }
		            },
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    calculable : true,
		    series : [
		        {
		            name:'Week',
		            type:'pie',
		            radius : '55%',
		            center: ['50%', '60%'],
		            data:rowDatas
		        }
		    ]
		};
		
		myChart.setOption(option);
		myChart.on('click', function (param) {
			refreshCredits(param.name);
		});
	});
}
</script>

<script type="text/javascript">
var datagrid = '#datagrid';
function getCredits(weekName){
	var week = {'weekName': weekName}
	var params = getParams();
	var newParams = $.extend(week,params);
	$(datagrid).datagrid({
		method: 'post',
		queryParams : newParams,
		url: app_url + '/credit/getCredits'
	});
}

function refreshCredits(weekName){
	var week = {'weekName': weekName}
	var params = getParams();
	var newParams = $.extend(week,params);
	$(datagrid).datagrid('load',newParams);
	$("#detailGridArea").panel({'title':'Detail - ' + weekName});
}
</script>

<%@ include file="/system/template/footer.jsp"%>