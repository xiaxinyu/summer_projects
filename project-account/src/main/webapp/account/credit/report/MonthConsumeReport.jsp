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
	           	<label>Year:</label> 
	           	<select id="cmbYear" class="easyui-combobox" style="width:80px;" data-options="onSelect:statistic">   
				    <option value="2020">2020</option> 
				    <option value="2019">2019</option>
				    <option value="2018">2018</option>
				    <option value="2017">2017</option>  
				    <option value="2016">2016</option>   
				    <option value="2015">2015</option>   
				    <option value="2014">2014</option>   
				    <option value="2013">2013</option>   
				</select>  
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
		<div id="main" style="margin-top: 20px;"></div>
	</div>
<div id="dialog" style='display: none;'>
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
</body>	
<script type="text/javascript">
var pieX = 320;
$(function(){
	$('#main').height($('#chartArea').height()-40);
	pieX = $('#chartArea').width() - pieX;
	selectYear();
	statistic();
});

function selectYear(){
	debugger
	var now = new Date();
	var year = now.getYear()+1900;
	$("#cmbYear").combobox('setValue',year);
}

function getParams(){
	//Year
	var year = $('#cmbYear').combobox('getValue');
	
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
	var params = {'year':year,'consumptionType':'1','consumeID':consume,'cardTypeName':cardtype,'demoArea':demoArea};
	return params;
}

function statistic() {
	var params = getParams();
	credit.monthConsumeReport(params, show);
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
			var keys = new Array();
			var values = new Array();
			var markPoints = new Array();
			for (var i = 0; i < rows.length; i++) {
				var row = rows[i];
				var key = row.key;
				var value = parseFloat(row.value);
				keys.push(key);
				values.push(value);
				markPoints.push({name: key, value: value ,xAxis: i, yAxis: value});
			}
			render(keys,values,markPoints);
		} else {
			app.messager.show({title : 'Fail', msg : data.returnMessage});
		}
	}
}

function render(keys,values,markPoints){
	// 使用
	require([ 'echarts', 'echarts/chart/line' , 'echarts/chart/bar', 'echarts/chart/pie'
	], function(ec) {
		// 基于准备好的dom，初始化echarts图表
		var myChart = ec.init(document.getElementById('main'));

		option = {
		  	title : {
		        text: 'Consume Line Statistic',
		        subtext: 'Money',
		        x:'left'
		    },
			tooltip : {
				trigger : 'axis'
			},
			legend : {
				data : [ 'Money' ]
			},
			toolbox : {
				show : true,
				feature : {
					dataView : {
						show : true,
						readOnly : false
					},
					magicType : {
						show : true,
						type : [ 'line', 'bar' ]
					},
					restore : {
						show : true
					},
					saveAsImage : {
						show : true
					}
				}
			},
			calculable : true,
			xAxis : [ {
				type : 'category',
				boundaryGap : false,
				data : keys,
				axisLabel: {
		            rotate: 60,
		            textStyle : {
		            	color : '#0000FF',
		            	align : 'left',
		            	fontWeight : 'bold',
		            	fontFamily : 'Arial'
		            }
		        }
			} ],
			grid: {
			    x: 40,
			    x2: 20,
			    y2: 100,
			},
			yAxis : [ {
				type : 'value',
				splitArea: { show: true }, 
				axisLabel : {
					formatter : '¥{value}'
				}
			} ],
			series : [ {
				type : 'line',
				data : values,
				itemStyle : {
					normal: {
	                	color: '#8FBC8F'
	               	}
				},
				markPoint : {
					itemStyle: {
	                   normal: {
	                	   color: '#2E8B57',
	                       borderColor: '#4876FF',
	                       borderWidth: 1
	                   }
	                },
					symbolSize : 16,
					data : markPoints
				},
				markLine : {
	                itemStyle : {
	                    normal: {
	                        color:'#FF0000',
	                        borderWidth:1,
	                        borderColor:'rgba(255,0,0,1)'
	                    }
	                },
					data : [ {
						type : 'average',
						name : 'Average'
					} ]
				}
			}]
		};
		myChart.setOption(option);
		myChart.on('click', function (param) {
			openDialog(param.name);
		});
	});
}
</script>

<script type="text/javascript">
var dialog = "#dialog";
var datagrid = "#datagrid";
var dialogFlag = false;

function openDialog(consumeName){
	$(dialog).css('display','');
	var title = 'Detail - ' + consumeName;
	if(!dialogFlag){
		$(dialog).dialog({    
		    title: title,    
		    width: 800,    
		    height: 480,    
		    closed: false,    
		    cache: false,    
		    modal: true   
		});
		getCredits(consumeName);
		dialogFlag = true;
	}else{
		refreshCredits(consumeName);
		$(dialog).dialog({title:title});
		$(dialog).dialog('refresh');  
	}
}

function getCredits(month){
	var week = {'month': month}
	var params = getParams();
	var newParams = $.extend(week,params);
	$(datagrid).datagrid({
		method: 'post',
		queryParams : newParams,
		url: app_url + '/credit/getCredits'
	});
}

function refreshCredits(month){
	var week = {'month': month}
	var params = getParams();
	var newParams = $.extend(week,params);
	$(datagrid).datagrid('load',newParams);
}
</script>

<%@ include file="/system/template/footer.jsp"%>