package rsos.framework.constant;

public class StaticVariable {
	
	public static String UNCREATED = "未创建";	//流程用户组未创建
	public static String CREATED = "已创建";	//流程用户组已创建
	
	public static String APPLIED = "新建";	//已提交
	
	//用于记录流程实例状态
	public static String SAVED = "新建未提交";		//保存未提交
	public static String WAITSIGN = "待签收";
	public static String WAITCHECK = "待审核";
	public static String BACK = "退回";
	public static String COMPLETE = "完成";
	public static String CANCEL = "拒绝";
	public static String BACKSIGN = "退回待签收";
	public static String WRITECHECK = "修改待审核";
	public static String WAITWRITE = "待补资料";
	
	//用于合同和赎回流程的实例状态
	public static String SUBMITXINTUO = "合同提交信托";
	public static String XINTUOBACK = "信托返回";
	public static String XINTUOCONFIRM = "信托确认";
	public static String WAITSUBMIT = "待提交信托";
	public static String WAITBACK = "待信托返回";
	public static String WAITCONFIRM = "待信托确认";
	public static String WAITGET = "合同待领取";
	public static String WAITGETTED = "合同已领取";
	//记录流程是否走完
	public static String END = "END";
	public static String UNEND = "UNEND";
	
	//记录任务当前完成情况
	public static String SIGNTIME = "暂未签收";
	public static String COMPLETETIME = "尚未完成";
	public static String COMPLETED = "COMPLETED";	//已处理
	public static String UNCOMPLETED = "UNCOMPLETED";//未处理
	
	//用于客户冻结
	public static String FREEZE = "冻结";
	public static String UNFREEZE = "解冻";
	public static String CLIENTFULL = "客户信息完整";
	public static String CLIENTUNFULL = "客户信息不全";
	
	//用于流程
	public static String PASS = "审核通过";
	public static String REJECT = "驳回";
	public static String SIGN = "签收";
	public static String ENDCANCEL = "取消";
	public static String RESUBMIT = "重新提交";
	
	public static String PREEND = "办结";
	
	public static String SUBMITXT = "提交信托";
	public static String XTBACK = "信托返回";
	public static String GETCONTRACT = "领取合同";
	
	public static String FURTHER = "深度开发流程";
	public static String OBJECT = "异议审批流程";
	public static String CONTRACT = "理财产品合同流程";
	public static String REDEMPTION = "理财产品赎回流程";
	
	public static String taskName = "市场人员补资料";
	public static String taskNameBranch = "支行行长审核";
	public static String taskName0 = "小微与销售服务部(调户岗)初审";
	public static String taskName1 = "售后服务部审核(综合岗)审核";
	public static String taskName2 = "售后服务部(总经理)审核";
	
	public static String taskNameNew = "支行理财经理补资料";
	
	//操作反馈信息
	public static String SUCCESS = "success";
	public static String FAILE = "faile";
	
	//用于表明业务是否达标
	public static String PASSCHECK = "通过";
	public static String UNPASSCHECK = "未通过";
	public static String CHECKING = "审核中";
	
	public static String DABIAO = "达标";
	public static String UNDABIAO = "未达标";
	
	//用于标记理财合同的状态
	public static String UNCONTRACTHAND = "录入未移交";
	public static String CONTRACTHAND ="合同已移交";
	public static String CONTRACTBACK = "合同已返回";
	public static String CONTRACTGET = "合同已领取";
	
	public static String FULL = "合同信息完整";
	public static String UNFULL = "合同信息不全";
	
	//用于标记理财合同的赎回情况
	public static String UNCONTRACTRED = "产品未赎回";
	public static String INCONTRACTRED = "产品赎回中";
	public static String CONFIRMRED = "赎回确认";
	public static String CONTRACTRED = "产品已赎回";
	
	//用于标记是否生成通知
	public static String UNNOTICE = "逾期未通知";
	public static String NOTICE = "逾期已通知";
	
	//通知类型
	public static String CONTRACTALARM = "合同即将逾期";
	public static String GETCONTRACTNOTICE = "领取合同通知";
	public static String PROREDEMPING = "产品进入赎回期";
	public static String PRODEADLINE = "理财产品到期";
	public static String CUSTOMEREDEMPED = "客户已赎回";
	
	public static String OBJECTNOTICE = "异议通知";
	
	public static String SCANED = "已查阅";
	public static String UNSCANED = "未查阅";
	public static String DEALED = "已处理"; 
	public static String UNDEAL = "未处理";
	
	//赎回区间状态
	public static String PASTREDEMPTION = "已过赎回期";
	public static String INREDEMPTION = "正在赎回期";
	public static String STEPINGREDEMPTION = "即将开始赎回";
	public static String PASTOPENDAY = "已过开放日";
	public static String WRONGREDEMP = "赎回区间日期错误";
	public static String NOREDEMP = "无赎回区间";
	
	//记录合同的查看权限
	public static String NOTICEONE = "1";	//归属支行或签约支行的理财经理可见
	public static String NOTICETWO = "2";	//所有理财经理可见
	
	
	
	
	
	
	
}
