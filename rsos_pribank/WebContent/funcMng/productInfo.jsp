<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>理财产品信息管理</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="css/common.css" />
	<link rel="stylesheet" type="text/css" href="css/main.css"/>
	<link type="text/css" rel="stylesheet" href="css/dev.css" />
	<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.1/themes/default/easyui.css" />
	<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.1/themes/icon.css" />
	<script type="text/javascript" src="js/jquery-easyui-1.3.1/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="js/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="js/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="js/jquery-easyui-1.3.1/plugins/datagrid-detailview.js"></script>
	<script type="text/javascript" src="js/commons.js"></script>
	<script type="text/javascript" src="js/json2.js"></script>
	<script type="text/javascript" src="js/vobCombox.js"></script>
	<script type="text/javascript" src="js/LG.js"></script>
	<script type="text/javascript">
			var flag ;	
			$(function(){
				
				$.ajaxSettings.traditional=true; 
				/**
				 *	初始化数据表格  
				 */
				$('#t_product').datagrid({
						title:'产品列表' ,
						fit:true ,
						height:450 ,
						url:'productAction!getlist.action' ,
						fitColumns:true ,  
						striped: true ,					//隔行变色特性 
						loadMsg: '数据正在加载,请耐心的等待...' ,
						rownumbers:true ,
						pagination: true , 
						nowrap: false,
						pageSize: 10 ,
						pageList:[10,20,50] ,
						onLoadSuccess: function (data){					
							LG.showMsg("查询结果提示",data.retCode,data.message,false);
						},
						onLoadError: function (xhr,data){
							showErrorMessage(xhr);
						},
						frozenColumns:[[				//冻结列特性 ,不要与fitColumns 特性一起使用 
							{field:'ck' ,width:50,checkbox: true}
						]],
						columns:[[
							{field:'productId' ,title:'产品ID' ,hidden: true},
							{field:'productCode' ,title:'产品编码' ,width:120,formatter:function(value,row,index){
      								return '<a href="javascript:void(0)" onclick="viewProduct(\'' + row.productId+ '\')">'+row.productCode+'</a>'}},
							{field:'productName' ,title:'产品名称' ,width:200},
							{field:'benefitDate' ,title:'起息日' ,width:100},
							{field:'dueDate' ,title:'到期日' ,width:100 },
							{field:'plannedBenefit' ,title:'预期收益' ,width:100},
							{field:'isRoll' ,title:'是否滚动' ,width:80},
							{field:'openDay' ,title:'开放日' ,width:100},
							{field:'redeemBegin' ,title:'赎回开始时间' ,width:100},
							{field:'redeemEnd' ,title:'赎回结束时间' ,width:100},
							{field:'rollBenefit' ,title:'滚动期收益(%)' ,width:100}
						]] ,
						view: detailview,
						detailFormatter:function(index,row){
							return '<div><table id="ddv-' + index + '"></table></div>';
						},
						onExpandRow: function(index,row){ 
							var productId = row.productId;
				            $('#ddv-'+index).datagrid({  
				                url: 'productAction!getRedemptionByProductId.action?productId='+productId,
				                fitColumns:true,  
				                rownumbers:true,  
				                loadMsg:'',  
				                height:'auto',  
				                columns:[[  
				            		{field:'redemptionIntervalId',title:'赎回区间ID',hidden: true},
									{field:'openDay',title:'开放日',width:80},
									{field:'redeemBegin',title:'赎回开始时间',width:100},
									{field:'redeemEnd',title:'赎回结束时间',width:100},
									{field:'rollBenefit',title:'滚动期收益(%)',width:80},
									{field:'redemptionStatus',title:'赎回期状态',width:80,
										formatter:function(value , record , index){
											if(value == '赎回区间日期错误'){
												return '<span style=color:red;>'+value+'</span>' ;
											}else{
												return value;
											}
										}
									}
				                ]],  
				                onResize:function(){  
				                   $('#t_product').datagrid('fixDetailRowHeight',index);  
				                },  
				                onLoadSuccess:function(){  
				                   setTimeout(function(){  
				                        $('#t_product').datagrid('fixDetailRowHeight',index);  
				                    },0);  
				                },
				                onLoadError: function (xhr,data){
									showErrorMessage(xhr);
								}
				            });  
				            $('#t_product').datagrid('fixDetailRowHeight',index);  
				        },
						toolbar:[
							{
								text:'初始化产品' , 
								iconCls:'icon-my-document_into' , 
								handler:importProducts
							},{
								text:'录入产品' ,
								iconCls:'icon-my-add' , 
								handler:addProduct
							},{
								text:'修改产品' ,
								iconCls:'icon-my-edit' , 
								handler:editProduct
							},{
								text:'删除产品' ,
								iconCls:'icon-my-delete' , 
								handler:deleteProducts			
							}
						]
					});
					
					/**
					 *  提交表单方法
					 */
					$('#save').click(function(){
						var isRoll= $('#isRoll').val();						
						if($('#myform').form('validate')&&isRoll!=''){							
							var productId = $('#productId').val();
							var productCode = $('#productCode').val();
							var productName = $('#productName').val();
							var benefitDate = $('#benefitDate').datebox("getValue");
							var dueDate = $('#dueDate').datebox("getValue");
							var plannedBenefit = $('#plannedBenefit').val();
							var isRoll= $('#isRoll').val();
							var productObject = {
								productId:productId,
								productCode:productCode,
								productName:productName,
								benefitDate:benefitDate,
								dueDate:dueDate,
								plannedBenefit:plannedBenefit,
								isRoll:isRoll
							};
							var inserted = new Object();
							var deleted = new Object();
							var updated = new Object();							
							if (endEditing()){
			                	if($('#dg').datagrid('getChanges').length>0){
			                		inserted = $('#dg').datagrid('getChanges','inserted');
			                		deleted = $('#dg').datagrid('getChanges','deleted');
			                		updated = $('#dg').datagrid('getChanges','updated');
			                	}
							}
							
							$.ajax({
								type: 'post' ,
								url: flag=='add'?'productAction!saveProduct.action':'productAction!updateProduct.action',
							    cache:false ,
								data:{
									productObject:JSON.stringify(productObject),
									inserted:JSON.stringify(inserted),
									deleted:JSON.stringify(deleted),
									updated:JSON.stringify(updated)
								},
								dataType:'json' ,
								success:function(result){
									$.messager.alert('提示信息',result.message,'info',function(){
										if(result.retCode=='A000000'){
											$('#mydialog').dialog('close'); 
											$('#t_product').datagrid('reload');
										}
									});
								}
							});
						}else {								
							LG.showError("提示信息","数据验证不通过,不能保存!");
						}
					});

					/**
					 * 关闭窗口方法
					 */
					$('#btn2').click(function(){
						$('#mydialog').dialog('close');
						$('#t_product').datagrid('reload');
					});
					
					$('#searchbtn').click(function(){
						$('#t_product').datagrid('load' ,serializeForm($('#mysearch')));
					});
					
					$('#clearbtn').click(function(){
						$('#mysearch').form('clear');
						$('#t_product').datagrid('load' ,{});
					});
					
					$.fn.datebox.defaults.formatter = function(date){
		        		var y = date.getFullYear();
		        		var m = date.getMonth()+1;
		       			var d = date.getDate();
		        		return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
		    		};
		    		$.extend($.fn.datagrid.defaults.editor, {
					     datetimebox: {//datetimebox就是你要自定义editor的名称
					         init: function(container, options){
					             var input = $('<input class="easyui-datebox">').appendTo(container);
					             return input.datetimebox({
					                 formatter:function(date){
					                     var y = date.getFullYear();
		        						 var m = date.getMonth()+1;
		       			  			     var d = date.getDate();
		        						 return y.toString()+(m<10?('0'+m.toString()):m.toString())+(d<10?('0'+d.toString()):d.toString());
					                 }
					             });
					         },
					         getValue: function(target){
					             return $(target).parent().find('input.combo-value').val();
					        },
					         setValue: function(target, value){
					             $(target).datetimebox("setValue",value);
					         },
					         resize: function(target, width){
					            var input = $(target);
					            if ($.boxModel == true){
					                 input.width(width - (input.outerWidth() - input.width()));
					            } else {
					                 input.width(width);
					             }
					         }
					     }
					});
		    });
			function importProducts(){
				$('#mydialogImport').dialog('open').dialog("refresh","funcMng/importProduct.jsp");
			}
			function saveImport(){
				var isRoll= $('#isRollImport').val();
				if($('#myformImport').form('validate')&&isRoll!=''){
					var firstOpenDay = $('#firstOpenDay').datebox("getValue");
					var rollIntervalSpan = $('#rollIntervalSpan').val();
					var rollBenefit = $('#rollBenefit').val();
					if(isRoll=='是'){
						if(firstOpenDay==''||rollIntervalSpan==''||rollBenefit==''){
							LG.showWarn("提示信息","产品是滚动期产品，必须填写:首次开放日,滚动区间跨度,滚动期收益!");
							return;
						}
					}
					var productCode = $('#productCodeImport').val();
					var productName = $('#productNameImport').val();
					var benefitDate = $('#benefitDateImport').datebox("getValue");
					var dueDate = $('#dueDateImport').datebox("getValue");
					var plannedBenefit = $('#plannedBenefitImport').val();
					var productObject = {
						productCode:productCode,
						productName:productName,
						benefitDate:benefitDate,
						dueDate:dueDate,
						plannedBenefit:plannedBenefit,
						isRoll:isRoll,
						firstOpenDay:firstOpenDay,
						rollIntervalSpan:rollIntervalSpan,
						rollBenefit:rollBenefit
					};
					$.ajax({
						type: 'post' ,
						url: 'productAction!importProductAction.action',
					    cache:false ,
						data:{
							productObject:JSON.stringify(productObject)
						},
						dataType:'json' ,
						success:function(result){
							$.messager.alert('提示信息',result.message,'info',function(){
								if(result.retCode=='A000000'){
									$('#mydialogImport').dialog('close'); 
									$('#t_product').datagrid('reload');
								}
							});
						}
					});
				}else {								
					LG.showError("提示信息","数据验证不通过,不能保存!");
				}
			}
			function closeImport(){
				$('#mydialogImport').dialog('close');
			}
			
			function addProduct(){
				flag = 'add';
				$('#productCode').removeAttr("readonly");
				$('#mydialog').dialog({title:'录入产品'});
				$('#myform').get(0).reset();
				$('#mydialog').dialog('open');
				$('#dg').datagrid({
					 singleSelect: true,
		             toolbar: '#tb',
		             url: 'productAction!getRedemptionList.action',
		             method: 'post',
		             onClickRow: onClickRow,
		             fitColumns:true ,  
					 striped: true ,					//隔行变色特性
		             rownumbers:true ,
		             pagination: true , 
					 pageSize: 10 ,
					 pageList:[10,20,50],
					 columns:[[
						 {field:'redemptionIntervalId',title:'赎回区间ID',hidden: true},
					 	 {field:'openDay',title:'开放日',width:150,align:'center',editor:{type:'datebox',options:{required:true,missingMessage:"开放日不能为空"}}},
						 {field:'redeemBegin',title:'赎回开始时间',width:150,align:'center',editor:{type:'datebox',options:{required:true,missingMessage:"赎回开始时间不能为空"}}},
						 {field:'redeemEnd',title:'赎回结束时间',width:150,align:'center',editor:{type:'datebox',options:{required:true,missingMessage:"赎回结束时间不能为空"}}},
						 {field:'rollBenefit',title:'滚动期收益(%)',width:150,align:'center',editor:{type:'numberbox',options:{precision:2,min:0,max:100,required:true,missingMessage:"收益不可为空,且不能为负数",invalidMessage:"收益不可为负数!"}}},
						 {field:'redemptionStatus',title:'赎回期状态',width:150,align:'center'}
					]]
				});
			}
			
			function editProduct(){
				flag = 'edit';
				$('#productCode').attr("readonly","readonly");
				var arr =$('#t_product').datagrid('getSelections');
				if(arr.length != 1){
					LG.showWarn("提示信息","只能选择一行记录进行修改!");
				} else {
					$('#mydialog').dialog({
						title:'修改产品'
					});
					$('#mydialog').dialog('open'); //打开窗口
					$('#myform').get(0).reset();   //清空表单数据 
					$('#myform').form('load',{	   //调用load方法把所选中的数据load到表单中,非常方便
						productId:arr[0].productId ,
						productCode:arr[0].productCode ,
						productName:arr[0].productName ,
						benefitDate:arr[0].benefitDate ,
						dueDate:arr[0].dueDate,
						plannedBenefit:arr[0].plannedBenefit ,
						isRoll:arr[0].isRoll,
						openDay:arr[0].openDay,
						redeemBegin:arr[0].redeemBegin,
						redeemEnd:arr[0].redeemEnd,
						rollBenefit:arr[0].rollBenefit
					});
					var productId = arr[0].productId;
					$('#dg').datagrid({
						 singleSelect: true,
			             toolbar: '#tb',
			             url: 'productAction!getRedemptionList.action?productId='+productId,
			             method: 'post',
			             onClickRow: onClickRow,
			             fitColumns:true ,  
						 striped: true ,					//隔行变色特性
			             rownumbers:true ,
			             onLoadSuccess: function (data){					
							LG.showMsg("查询结果提示",data.retCode,data.message,false);
						 },
						 onLoadError: function (xhr,data){
							showErrorMessage(xhr);
						 },
			             pagination: true , 
						 pageSize: 10 ,
						 pageList:[10,20,50],
						 columns:[[
							 {
								field:'redemptionIntervalId',title:'赎回区间ID',hidden: true
						 	 },{
								field:'openDay',title:'开放日',width:150,align:'center',editor:{type:'datebox',options:{required:true,missingMessage:"开放日不能为空"}}
							 },{
								field:'redeemBegin',title:'赎回开始时间',width:150,align:'center',editor:{type:'datebox',options:{required:true,missingMessage:"赎回开始时间不能为空"}}
							 },{
								field:'redeemEnd',title:'赎回结束时间',width:150,align:'center',editor:{type:'datebox',options:{required:true,missingMessage:"赎回结束时间不能为空"}}
							 },{
							    field:'rollBenefit',title:'滚动期收益(%)',width:150,align:'center',editor:{type:'numberbox',options:{precision:2,min:0,max:100,required:true,missingMessage:"收益不可为空,且不能为负数",invalidMessage:"收益不可为负数!"}}
							 },{
								field:'redemptionStatus',title:'赎回期状态',width:150,align:'center',
								formatter:function(value , record , index){
									if(value == '赎回区间日期错误'){
										return '<span style=color:red;>'+value+'</span>' ;
									}else{
										return value;
									}
								}
							 }
						]]
					});
				}
			}
			
			function deleteProducts(){
				var arr =$('#t_product').datagrid('getSelections');
				var productIds = new Array();
				if(arr.length <=0){
					LG.showWarn("提示信息","至少选择一行记录进行删除!");
				} else {
					$.messager.confirm('提示信息' , '确认删除?' , function(r){
						if(r){
							for(var i =0 ;i<arr.length;i++){
								productIds[i] = arr[i].productId;
							}
							$.ajax({
								type: 'post' ,
								url: 'productAction!deleteProduct.action',
								cache:false ,
								data:{
									"productIds":productIds
								},
								dataType:'json' ,
								success:function(result){
									$.messager.alert('提示信息',result.message,'info',function(){
										//刷新datagrid 
										$('#t_product').datagrid('reload');
										//2 清空idField   
										$('#t_product').datagrid('unselectAll');
									});
								}
							});
						}
					});
				}
			}					
			
			
			//js方法：序列化表单 			
			function serializeForm(form){
				var obj = {};
				$.each(form.serializeArray(),function(index){
					if(obj[this['name']]){
						obj[this['name']] = obj[this['name']] + ','+this['value'];
					} else {
						obj[this['name']] =this['value'];
					}
				});
				return obj;
			}
			function viewProduct(productId){
				$('#mydialogProduct').dialog('open').dialog("refresh","funcMng/productView.jsp?productId='"+productId+"'");
			}
			$(function(){
			    $('#openDayBegin').datebox({
		            formatter: function (date) {
		                var y = date.getFullYear();
		                var m = date.getMonth() + 1;
		                var d = date.getDate();
		                return y.toString() + (m < 10 ? ("0" + m.toString()) : m.toString()) + (d < 10 ? ("0" + d.toString()) : d.toString());
		            }
			    });
			    $('#redeemBegin').datebox({
		            formatter: function (date) {
		                var y = date.getFullYear();
		                var m = date.getMonth() + 1;
		                var d = date.getDate();
		                return y.toString() + (m < 10 ? ("0" + m.toString()) : m.toString()) + (d < 10 ? ("0" + d.toString()) : d.toString());
		            }
			        });
			    $('#redeemEnd').datebox({
		            formatter: function (date) {
		                var y = date.getFullYear();
		                var m = date.getMonth() + 1;
		                var d = date.getDate();
		                return y.toString() + (m < 10 ? ("0" + m.toString()) : m.toString()) + (d < 10 ? ("0" + d.toString()) : d.toString());
		            }
			    });
			    $('#openDayEnd').datebox({
		            formatter: function (date) {
		                var y = date.getFullYear();
		                var m = date.getMonth() + 1;
		                var d = date.getDate();
		                return y.toString() + (m < 10 ? ("0" + m.toString()) : m.toString()) + (d < 10 ? ("0" + d.toString()) : d.toString());
		            }
			    });
			    $('#benefitDate').datebox({
		            formatter: function (date) {
		                var y = date.getFullYear();
		                var m = date.getMonth() + 1;
		                var d = date.getDate();
		                return y.toString() + (m < 10 ? ("0" + m.toString()) : m.toString()) + (d < 10 ? ("0" + d.toString()) : d.toString());
		            }
			    });
			    $('#dueDate').datebox({
		            formatter: function (date) {
		                var y = date.getFullYear();
		                var m = date.getMonth() + 1;
		                var d = date.getDate();
		                return y.toString() + (m < 10 ? ("0" + m.toString()) : m.toString()) + (d < 10 ? ("0" + d.toString()) : d.toString());
		            }
			    });
			    
			    $('#isRoll').change(function(){
			    	var isRoll = $('#isRoll').attr('value');
			    	if(isRoll=='是'){
			    		$.messager.alert('提示信息','需要添加赎回区间!','info');
			    	}
			    });
			    $.extend($.fn.validatebox.defaults.rules,{
			  		TimeCheck:{
			    		validator:function(value,param){   
			     			var s = $("input[name="+param[0]+"]").val();
			     			return value>s;
			    		},
			    		message:'不可大于起始时间'
			   		}
			  });
			});
			
			var editIndex = undefined;
	        function endEditing(){
	            if (editIndex == undefined){return true;}
	            if ($('#dg').datagrid('validateRow', editIndex)){
	                $('#dg').datagrid('endEdit', editIndex);
	                editIndex = undefined;
	                return true;
	            } else {
	                return false;
	            }
	        }
	        function onClickRow(index){
	            if (editIndex != index){
	                if (endEditing()){
	                    $('#dg').datagrid('selectRow', index)
	                            .datagrid('beginEdit', index);
	                    editIndex = index;
	                } else {
	                    $('#dg').datagrid('selectRow', editIndex);
	                }
	            }
	        }
	        function append(){
	        	var isRoll = $('#isRoll').attr('value');
	        	if(isRoll==''){
	        		LG.showWarn("提示信息","请先确定产品是否滚动!");
	        	}else if(isRoll=='否'){
	        		LG.showWarn("提示信息","该产品非滚动产品,不可添加滚动区间!");
	        	}else if(endEditing()){
	                $('#dg').datagrid('appendRow',{});
	                editIndex = $('#dg').datagrid('getRows').length-1;
	                $('#dg').datagrid('selectRow', editIndex)
	                        .datagrid('beginEdit', editIndex);
	            }
	        }
	        function removeit(){
	        	var isRoll = $('#isRoll').attr('value');
	        	if(isRoll==''){
	        		LG.showWarn("提示信息","请先确定产品是否滚动!");
	        	}else if(isRoll=='否'){
	        		LG.showWarn("提示信息","该产品非滚动产品,该操作无效!");
	        	}else {
	        		if (editIndex == undefined){return;}
	        		else{
	        			$.messager.confirm('提示消息','确定要删除该赎回区间?',function(r){
		        			if(r){
					            $('#dg').datagrid('cancelEdit', editIndex)
					                    .datagrid('deleteRow', editIndex);
					            editIndex = undefined;
		        			}
		        		});
	        		}
	        	}
	        }
	        function accept(){
	            if (endEditing()){
	                if($('#dg').datagrid('getChanges').length===0){return;}
	                else{
	                	var inserted = $('#dg').datagrid('getChanges','inserted');
	                	var deleted = $('#dg').datagrid('getChanges','deleted');
	                	var updated = $('#dg').datagrid('getChanges','updated');
	                	$.ajax({
	                		type: 'post' ,
	                		url:'editProduct!addOpenDay.action',
	                		dataType:'json',
	                		cache:false ,
	                		data:{
	                			inserted:JSON.stringify(inserted),
	                			deleted:JSON.stringify(deleted),
	                			updated:JSON.stringify(updated)
	                		},
	                		success:function(result){
								$.messager.alert('提示信息','OK','确定',function(){
									$('#dg').datagrid('acceptChanges');
									$('#dg').datagrid('reload');
								});
							} ,
							error:function(result){
								$.messager.alert('提示信息',result.message,'确定');
							}
	                	});
	                	
	                }
	            }
	        }
	        function reject(){
	        	var isRoll = $('#isRoll').attr('value');
	        	if(isRoll==''){
	        		LG.showWarn("提示信息","请先确定产品是否滚动!");
	        	}else if(isRoll=='否'){
	        		LG.showWarn("提示信息","该产品非滚动产品,该操作无效!");
	        	}else {
	        		$.messager.confirm('提示消息','该操作会取消当前页面对赎回区间的所有更改,确定要撤销?',function(r){
	        			if(r){
	        				$('#dg').datagrid('rejectChanges');
	            			editIndex = undefined;
	        			}
	        		});
	        	}
	        }
	</script>
  </head>
  
  <body style="overflow:hidden">
	<div id="lay" class="easyui-layout" style="width: 100%;height:100%" >
		<div region="north" title="产品信息查询" collapsed=false style="height:90%;overflow:hidden">
			<form id="mysearch" method="post">
				<table width="100%">
					<tr>
						<td width="20%">&nbsp;产品编码:&nbsp;<input name="productCode"  class="easyui-validatebox" value="" style="width:55%;height:22px;border:1px solid #A4BED4;"/></td>
						<td width="40%">&nbsp;开&nbsp;放&nbsp;&nbsp;日:&nbsp;<input id="openDayBegin"  name="openDayBegin" class="easyui-datebox" value="" style="width:120%"/>
							&nbsp;到&nbsp;<input name="openDayEnd" id="openDayEnd" class="easyui-datebox" value="" style="width:120%"/></td>
						<td width="15%"></td>
						<td width="20%"></td>
					</tr>						
					<tr>
						<td width="20%">&nbsp;产品名称:&nbsp;<input name="productName" class="easyui-validatebox"  value="" style="width:55%;height:22px;border:1px solid #A4BED4;"/></td>
						<td width="40%">&nbsp;赎回区间:&nbsp;<input name="redeemBegin" id="redeemBegin" class="easyui-datebox" value="" style="width:120%"/> 							
							&nbsp;到&nbsp;<input name="redeemEnd" id="redeemEnd" class="easyui-datebox" value="" style="width:120%"/></td>
						<td width="15%" align="left"><a href="javascript:void(0)" id="searchbtn" class="easyui-linkbutton">查询</a> <a href="javascript:void(0)" id="clearbtn" class="easyui-linkbutton">重置</a></td>
						<td width="20%"></td>
					</tr>
				</table>				   	
			</form>
		</div>
		<div region="center" >
			<table id="t_product"></table>
		</div>
	</div>
  			
	<div id="mydialog" title="录入产品信息" modal=true  draggable=true class="easyui-dialog" closed=true style="width:800px;height:500px;overflow:hidden">
  		<div id="lay" class="easyui-layout" style="width:100%;height:100%" >
   		<div region="north" align="center" style="background-color:#E4F5EF;width:100%;height:30%;overflow:hidden">
			<table>
				<tr>
					<td colspan="2">
						<a id="save" href="javascript:void(0)" class="easyui-linkbutton">保存</a>
					<a id="btn2" href="javascript:void(0)" class="easyui-linkbutton">关闭</a>
					</td>
				</tr> 
			</table>  					 					    					    					    					    					    					    					    					
   		</div>
   		<div region="center" style="overflow:auto;background-color:#E4F5EF;width:100%;height:100%">
    		<br/>
    		<form id="myform" action="" method="post">
    				<input type="hidden" id="productId" name="productId" value="" />
    				<div class="title" align="center" ><table><tr><td><h4>理财产品信息表(<font style="color:red">*</font>必填)</h4></td></tr></table></div>
    				<table width="90%" border="1" align="center" style="background-color:#E4F5EF">
    					<tr>
    						<td width="20%" align="center">产品编码&nbsp;<font color="red">*</font></td>
    						<td width="25%"><input type="text" id="productCode" name="productCode" class="easyui-validatebox" required=true missingMessage="产品编码不能为空" value="" style="width:100%;height:22px;border:1px solid #A4BED4;"/></td>
    						<td width="20%" align="center">产品名称&nbsp;<font color="red">*</font></td>
    						<td width="25%" ><input type="text" id="productName" name="productName" class="easyui-validatebox" required=true missingMessage="产品名称不能为空" value="" style="width:100%;height:22px;border:1px solid #A4BED4;"/></td>
    					</tr>
    					<tr>
    						<td align="center">起息日&nbsp;<font color="red">*</font></td>
    						<td><input type="text" id="benefitDate" name="benefitDate" class="easyui-datebox" required=true missingMessage="起息日不能为空"  value="" style="width:200%;height:22px;border:1px solid #A4BED4;"/></td>
    						<td align="center">到期日&nbsp;<font color="red">*</font></td>
    						<td><input type="text" id="dueDate" name="dueDate" value="" class="easyui-datebox" required=true missingMessage="到期日不能为空" validType="TimeCheck['benefitDate']" invalidMessage="到期日必须大于起息日" style="width:200%;height:22px;border:1px solid #A4BED4;"/></td>
    					</tr>
    					<tr>
    						<td align="center">预期收益(%)&nbsp;<font color="red">*</font></td>
    						<td><input type="text" id="plannedBenefit" name="plannedBenefit" class="easyui-numberbox" min="0" max="100" precision="2" required=true missingMessage="收益不可为空,且不能为负数" invalidMessage="收益不可为负数！" value="" style="width:100%;height:22px;border:1px solid #A4BED4;"/></td>
    						<td align="center">是否滚动&nbsp;<font color="red">*</font></td>
	    					<td>
	    						<span style="height:22px;border:1px solid #A4BED4;">
	    						<SELECT id="isRoll" name="isRoll" style="width:100%">
									<option value=""></option>
									<option value="是">是</option>
									<option value="否">否</option>
								</SELECT>
								</span>
							</td>
    					</tr>
    					
    					<tr>
    						<td colspan="4">
    							<table id="dg" class="easyui-datagrid" align="center" border="1" title="添加赎回区间" style="height:auto;background-color:#E4F5EF">
							    </table>
    						</td>
    					</tr>  					 					    					    					    					    					    					    					    					
    				</table>
    				<table width="90%" align="center" style="background-color:#E4F5EF">
    					<tr>
    						<td class="fontStyle"><font style="color:red;font-size:12px">注:&nbsp;到期日晚于起息日;&nbsp;开放日晚于赎回结束时间,赎回结束时间晚于赎回开始时间;&nbsp;赎回区间不能重叠</font></td>
    					</tr>
    				</table>
    		</form>
    	</div>
   	</div>
   	<div id="tb" style="height:auto">
       <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-my-add',plain:true" onclick="append()">添加</a>
       <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-my-delete',plain:true" onclick="removeit()">删除</a>
       <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="reject()">撤销</a>
       </div>	
	</div>
	
	<div id="mydialogImport" title="初始化产品信息" modal=true  draggable=true class="easyui-dialog" closed=true style="width:800px;height:500px;overflow:hidden">
	</div>
	<div id="mydialogProduct" title="查看产品信息" modal=false  draggable=true class="easyui-dialog" closed=true style="width:800px;height:500px">
	</div>
  </body>
</html>
