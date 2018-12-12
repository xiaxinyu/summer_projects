<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="easyui-accordion" data-options="fit:true,border:false">
   <div title="Credit Card" data-options="iconCls:'icon-search'" style="overflow:auto;">
   	   <div class="menuspace"></div>
   	   <div style="width: 100%;text-align: center;">
       		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="text-align: left;width: 90%"
       		onclick="selectTab(this)" title="Credit Card - Detail" url="account/credit/CreditBill.jsp" >Detail</a>
       </div>
       <div class="menuspace"></div>
   	   <div style="width: 100%;text-align: center;">
       		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'"  style="text-align: left;width: 90%"
       		onclick="selectTab(this)" title="Data import" url="account/credit/CreidtUpload.jsp" >Data importer</a>
       </div>
       <div class="menuspace"></div>
   	   <div style="width: 100%;text-align: center;"> 
       		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'"  style="text-align: left;width: 90%"
       		onclick="selectTab(this)" title="Consume Line Report" url="account/credit/report/ConsumeLineReport.jsp" >Consume Line Report</a>
       </div>
       <div class="menuspace"></div>
   	   <div style="width: 100%;text-align: center;"> 
       		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'"  style="text-align: left;width: 90%"
       		onclick="selectTab(this)" title="Consume Pie  Report" url="account/credit/report/ConsumePieReport.jsp" >Consume Pie Report</a>
       </div>
       <div class="menuspace"></div>
   	   <div style="width: 100%;text-align: center;">
       		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'"  style="text-align: left;width: 90%"
       		onclick="selectTab(this)" title="Week Consume Report" url="account/credit/report/WeekConsumeReport.jsp" >Week Consume Report</a>
       </div>
       <div class="menuspace"></div>
   	   <div style="width: 100%;text-align: center;">
       		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'"  style="text-align: left;width: 90%"
       		onclick="selectTab(this)" title="Month Consume Report" url="account/credit/report/MonthConsumeReport.jsp" >Month Consume Report</a>
       </div>
       <div class="menuspace"></div>
   	   <div style="width: 100%;text-align: center;">
       		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'"  style="text-align: left;width: 90%"
       		onclick="selectTab(this)" title="Consume Map Report" url="account/credit/report/ConsumeMapReport.jsp" >Consume Map Report</a>
       </div>
   </div>
   <div title="Salary" data-options="iconCls:'icon-search'" style="overflow:auto;">
   	   <div class="menuspace"></div>
   	   <div style="width: 100%;text-align: center;">
       		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'"  style="text-align: left;width: 90%"
       		onclick="selectTab(this)" title="Salary-Detail" url="account/salary/Salary.jsp" >Detail</a>  
       </div>
   </div>
   <div title="Endowment insurance" data-options="iconCls:'icon-search'" style="overflow:auto;" >
	   <div class="menuspace"></div>
	   <div style="width: 100%;text-align: center;">
        	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'"  style="text-align: left;width: 90%"
        	onclick="selectTab(this)" title="Endowment - Detail" url="account/endowment/Endowment.jsp" >Detail</a>
       </div>
       <div style="width: 100%;text-align: center;">
        	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'"  style="text-align: left;width: 90%"
        	onclick="selectTab(this)" title="Endowment - Company Statistic" url="account/endowment/Endowment.jsp" >Company Statistic</a>
       </div>
       <div style="width: 100%;text-align: center;">
        	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'"  style="text-align: left;width: 90%"
        	onclick="selectTab(this)" title="Endowment - Year Statistic" url="account/endowment/Endowment.jsp" >Year Statistic</a>
       </div>
   </div>
   <div title="Accumulation funds " data-options="iconCls:'icon-search'" style="overflow:auto;" >
	   <div class="menuspace"></div>
	   <div style="width: 100%;text-align: center;">
        	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'"  style="text-align: left;width: 90%"
        	onclick="selectTab(this)" title="Accumulation - Detail" url="account/accumulation/accumulation.jsp" >Detail</a>
       </div>
   </div>
   <div title="Medical insurance" data-options="iconCls:'icon-search'" style="overflow:auto;" >
	   <div class="menuspace"></div>
	   <div style="width: 100%;text-align: center;">
        	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'"  style="text-align: left;width: 90%"
        	onclick="selectTab(this)" title="Medical - Detail" url="account/medical/Medical.jsp" >Detail</a>
       </div>
   </div>
   <div title="Unemployment insurance" data-options="iconCls:'icon-search'" style="overflow:auto;" >
	   <div class="menuspace"></div>
	   <div style="width: 100%;text-align: center;">
        	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'"  style="text-align: left;width: 90%"
        	onclick="selectTab(this)" title="Unemployment - Detail" url="account/unemployment/UnEmployment.jsp" >Detail</a>
       </div>
   </div>
</div>
<script type="text/javascript">
	/** 选择面板  */
	function selectTab(obj){
		var title = $(obj).attr("title");
		var url = $(obj).attr("url");
		initPanel(title,url);
	}
</script>