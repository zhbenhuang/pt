<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%	
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String businessId = request.getParameter("businessId");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">    
    <title>viewObjectInfo</title>
   	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=path %>/css/common.css" />
	<link rel="stylesheet" type="text/css" href="<%=path %>/css/main.css"/>
	<link rel="stylesheet" type="text/css" href="<%=path %>/css/dev.css" />
	<script type="text/javascript" src="<%=path %>/js/jquery-easyui-1.3.1/jquery-1.8.0.min.js"></script>
	<link rel="stylesheet" type="text/css" href="<%=path %>/js/jquery-easyui-1.3.1/themes/default/easyui.css" />
	<link rel="stylesheet" type="text/css" href="<%=path %>/js/jquery-easyui-1.3.1/themes/icon.css" />
	<script type="text/javascript" src="<%=path %>/js/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=path %>/js/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="<%=path %>/js/commons.js"></script>
	<script type="text/javascript" src="<%=path %>/js/LG.js"></script>
 </head>
 <body style="overflow:hidden">
 	<script type="text/javascript">
		$(function(){
			$('#Nnatural').combobox({
				editable:false,
				required:true,
				panelHeight:'auto'
			});
			$('#NcomCustomers').combobox({
				editable:false,
				required:true,
				panelHeight:'auto'
			});
			var businessId = <%=businessId%>;
			var params = {'businessId':businessId};
			$.ajax({
				type: 'post' ,
				url: 'furtherDevAction!showTaskAction.action',
				cache:false ,
				data:params,
				dataType:'json',
				success:function(result){
					$("#business").val(businessId);
					$("#Nagent").val(result.agentName);
					$("#Nbranch").val(result.branchBank);
					$("#Noriginal").val(result.originalAgentName);
					$("#NoriginalBranch").val(result.originalBranchBank);
					$("#Ncomp").val(result.composition);
					$("#NclientN").val(result.clientName);
					$("#NclientI").val(result.clientId);
					$("#NmoneyF").val(result.financialAssets);
					$("#NmoneyD").val(result.dayAverage);
					$("#NdateD").val(result.date);
					$("#Noths").val(result.others);
					$("#NmoneyA").val(result.account);
					$("#NdateT").val(result.time);
					$("#NdateBegin").val(result.dateBegin);
					$("#NdateEnd").val(result.dateEnd);
					$("#NmoneyS").val(result.saveBalance);
					$("#NmoneyW").val(result.wealthDaily);
					$("#Nws").val(result.wealthStake);
					$("#Noth").val(result.other);
					$("#NAcomp").val(result.afterDevCompsition);
					$("#yy").val(result.developerOpinion);
					$("#Naso").val(result.afterServiceOpinion);	
					$("#Nlo").val(result.leaderOpinion);			
					$('#Nnatural').combobox('setValue',result.naturalHouseHold);
					$('#NcomCustomers').combobox('setValue',result.comCustomers);				
					document.getElementById("innerframe").src="attachAction!listCheckNoticeAttach.action?businessId="+businessId;
				},
				error:function(result){
					LG.showError("提示信息","系统访问异常,请联系管理员!");
				}
			});
		});
	</script>
	<div id="lay" class="easyui-layout" style="width:100%;height:100%" >
   		<div region="center" style="overflow:auto;background-color:#E4F5EF;width:100%;height:100%">
    		<br/>
    		<form id="myformtwo" action="" method="post">
    			<input type="hidden" id="business" name="businessId"/>
    		 	<div class="title" align="center"><h4>异议流程审批表(<font style="color:red">*</font>必填)</h4></div>	
				<div align="center">
					<table width="90%" border="1" align="center" style="background-color:#E4F5EF;table-layout:fixed;">
						<tr>
						    <td width="20%" align="center">申请人<font style="color:red">*</font></td>
						    <td width="25%">&nbsp;&nbsp;<input type="text" id="Nagent" name="agentName" value="" readonly="readonly" style="background-color: #E1E6E9;width:90%;height:22px;border:1px solid #A4BED4;"/>&nbsp;</td>
						    <td width="20%" align="center">所属支行/团队/便民店<font style="color:red">*</font></td>
						    <td width="25%">&nbsp;&nbsp;<input type="text" id="Nbranch" name="branchBank" value="" readonly="readonly" style="background-color: #E1E6E9;width:90%;height:22px;border:1px solid #A4BED4;"/>&nbsp;</td>
					  	</tr>
						<tr>
						    <td align="center">&nbsp;原归属人：</td>
						    <td>&nbsp;&nbsp;<input type="text" id="Noriginal" name="originalAgentName" value="" readonly="readonly" style="background-color: #E1E6E9;width:90%;height:22px;border:1px solid #A4BED4;"/></td>
						    
						    <td align="center">是否自然户<font style="color:red">*</font></td>
						    <td>&nbsp;
						    	<select id="Nnatural" name="naturalHouseHold" style="background-color: #E1E6E9;width:167%;height:22px;border:1px solid #A4BED4;" disabled="disabled">
							    	<option value="Y">是</option>
							    	<option value="N">否</option>
						   		</select>
						    </td>
						    
						</tr>
						<tr>
						    <td align="center">&nbsp;原所属支行/团队/便民店</td>
						    <td>&nbsp;&nbsp;<input type="text" id="NoriginalBranch" name="originalBranchBank" value="" readonly="readonly" style="background-color: #E1E6E9;width:90%;height:22px;border:1px solid #A4BED4;"/></td>
						    <td align="center">原分成比例<font style="color:red">*</font></td>
						    <td>&nbsp;&nbsp;<input type="text" id="Ncomp" name="composition" readonly="readonly" value="" style="background-color: #E1E6E9;width:90%;height:22px;border:1px solid #A4BED4;"/></td>
						</tr>
						<tr>
						    <td align="center">客户姓名<font style="color:red">*</font></td>
						    <td>&nbsp;&nbsp;<input type="text" id="NclientN" name="clientName" value="" readonly="readonly" style="background-color: #E1E6E9;width:90%;height:22px;border:1px solid #A4BED4;"/></td>
						    <td align="center">客  户  号<font style="color:red">*</font></td>
						    <td>&nbsp;&nbsp;<input type="text" id="NclientI" name="clientId" value="" readonly="readonly" style="background-color: #E1E6E9;width:90%;height:22px;border:1px solid #A4BED4;"/></td>
						</tr>
						<tr>
						    <td colspan="4" align="center" class="weight">客户存量情况</td>
						</tr>
						<tr>
						    <td align="center">金融资产余额（万元）<font style="color:red">*</font></td>
						    <td>&nbsp;&nbsp;<input type="text" id="NmoneyF" name="financialAssets" value="" readonly="readonly" style="background-color: #E1E6E9;width:90%;height:22px;border:1px solid #A4BED4;"/></td>
						    <td align="center">季日均（万元）<font style="color:red">*</font></td>
						    <td>&nbsp;&nbsp;<input type="text" id="NmoneyD" name="dayAverage" value="" readonly="readonly" style="background-color: #E1E6E9;width:90%;height:22px;border:1px solid #A4BED4;"/></td>
						</tr>
						<tr>
						    <td align="center">开户日期<font style="color:red">*</font></td>
						    <td>&nbsp;&nbsp;<input type="text" id="NdateD" name="date"  value="" readonly="readonly" style="background-color: #E1E6E9;width:90%;height:22px;border:1px solid #A4BED4;"/></td>
						    <td align="center">&nbsp;是否商贷客户：</td>
						    <td>&nbsp;
						    	<select id="comCustomers" name="NcomCustomers" style="background-color: #E1E6E9;width:167%;" disabled="disabled">
							    	<option value="Y">是</option>
							    	<option value="N">否</option>
							    </select>
						    </td>
						</tr>
						<tr>
						    <td align="center">&nbsp;其他行业情况</td>
						    <td colspan="3">&nbsp;&nbsp;<textarea class="height" id="Noths" name="others" rows="2" style="background-color: #E1E6E9;width:96%;border:1px solid #A4BED4;" readonly="readonly"></textarea></td>
						</tr>
						<tr>
						    <td colspan="4" align="center" class="weight">客户客户资金到账备案</td>
						</tr>
						<tr>
						    <td align="center">入账金额（万元）<font style="color:red">*</font></td>
						    <td>&nbsp;<input type="text" id="NmoneyA" name="account" value="" readonly="readonly" style="background-color: #E1E6E9;width:90%;height:22px;border:1px solid #A4BED4;"/></td>
						    <td align="center">入账时间<font style="color:red">*</font></td>
						    <td>&nbsp;<input type="text"  id="NdateT" name="time" value="" readonly="readonly" style="background-color: #E1E6E9;width:90%;height:22px;border:1px solid #A4BED4;"/></td>
						</tr>
						<tr>
						    <td colspan="4" align="center" class="weight">拟深度开发规模</td>
						</tr>
						<tr>
						    <td align="center">开发期限<font style="color:red">*</font></td>
						    <td colspan="3">&nbsp;<input class="height" id="NdateBegin" name="dateBegin" readonly="readonly"
						        value="" style="background-color: #E1E6E9;width:32%;height:22px;border:1px solid #A4BED4;"/>&nbsp;&nbsp;至&nbsp;&nbsp;<input class="height" id="NdateEnd" name="dateEnd" readonly="readonly"
						        value="" style="background-color: #E1E6E9;width:32%;height:22px;border:1px solid #A4BED4;"/>
						    </td>
						</tr>
						
						<tr>
						    <td align="center">储蓄余额目标（万元）<font style="color:red">*</font></td>
						    <td>&nbsp;<input type="text" id="NmoneyS" name="saveBalance" value="" readonly="readonly" style="background-color: #E1E6E9;width:90%;height:22px;border:1px solid #A4BED4;"/></td>
						    <td align="center">财富日均目标（万元）<font style="color:red">*</font></td>
						    <td>&nbsp;<input type="text" id="NmoneyW" name="wealthDaily" value="" readonly="readonly" style="background-color: #E1E6E9;width:90%;height:22px;border:1px solid #A4BED4;"/></td>
						</tr>
						<tr>
						    <td align="center">&nbsp;财富资产目标（万元）</td>
						    <td colspan="3">&nbsp;<input type="text" id="Nws" name="wealthStake" value="" class="height" style="background-color: #E1E6E9;width:32%;height:22px;border:1px solid #A4BED4;" readonly="readonly"/></td>
						</tr>
						<tr>
						    <td align="center">&nbsp;其他</td>
						    <td colspan="3">&nbsp;<textarea class="height" id="Noth" name="other" rows="2" style="background-color: #E1E6E9;width:96%;border:1px solid #A4BED4;border:1px solid #A4BED4;" readonly="readonly"></textarea></td>
						</tr>
						<tr>
						    <td align="center">开发成功后分配比例<font style="color:red">*</font></td>
						    <td colspan="3">&nbsp;<textarea class="height" id="NAcomp" name="afterDevCompsition" readonly="readonly" rows="2" style="background-color: #E1E6E9;width:96%;border:1px solid #A4BED4;"></textarea></td>
						</tr>
						<tr>
						    <td align="center">&nbsp;异议人意见</td>		
						    <td colspan="3">&nbsp;<textarea class="height" id="yy" name="objecterOpinion" rows="2" readonly="readonly" style="background-color: #E1E6E9;width:96%;border:1px solid #A4BED4;"></textarea></td>
						</tr>
						<tr>
						    <td align="center">&nbsp;分行小微及小区售后服务管理部意见</td>
						    <td colspan="3">&nbsp;<textarea class="height" id="Naso" name="afterServiceOpinion" rows="2" readonly="readonly" style="background-color: #E1E6E9;width:96%;border:1px solid #A4BED4;" ></textarea></td>
						</tr>
						<tr>
						    <td align="center">&nbsp;售后服务部(总经理)意见</td>
						    <td colspan="3">&nbsp;<textarea class="height" id="Nlo" name="leaderOpinion" rows="2" readonly="readonly" style="background-color: #E1E6E9;width:96%;border:1px solid #A4BED4;"></textarea></td>
						</tr>
						<tr>
						    <td align="center">&nbsp;行领导意见</td>
						    <td colspan="3">&nbsp;<textarea class="height" rows="2" readonly="readonly" style="background-color: #E1E6E9;width:96%;border:1px solid #A4BED4;" ></textarea></td>
						</tr>
						<tr>
							<td  align="center">附件<br/>
								<font size="1.6">[ 支持文件和图片类型：<br/>".zip" , ".rar" , ".ppt" , ".doc" , "xlsx" , "txt" , "pdf", 
								 ".gif" , ".png" , ".jpg" , ".jpeg" , ".bmp" <br/>每个文件大小不能超过10M ]</font></td>
							<td align="left" colspan="3">
								<div id="nav-conbox">
									<div style="background:#E4F5EF">&nbsp;&nbsp;													
										<iframe style="overflow-x:hidden;background:#E4F5EF;width:96%" id='innerframe' name='innerframe' height="120" 
											frameborder="no" border="0" marginwidth="0" marginheight="0" allowtransparency="true"
											scrolling="auto">
										</iframe>
									</div>
								</div>
							</td>
						</tr>
					</table>
				</div>
    		</form>
   		</div>
	</div>
  </body>
</html>
