SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[productInfo]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[productInfo](
	[productCode] [varchar](100) NOT NULL,
	[productName] [varchar](500) NOT NULL,
	[benefitDate] [varchar](50) NOT NULL,
	[dueDate] [varchar](50) NOT NULL,
	[plannedBenefit] [varchar](10) NOT NULL,
	[isRoll] [varchar](10) NOT NULL,
	[firstOpenDay] [varchar](50) NULL,
	[rollIntervalSpan] [varchar](10) NULL,
	[rollBenefit] [varchar](10) NULL,
	[created] [varchar](10) NOT NULL CONSTRAINT [DF_productInfo_created]  DEFAULT ('no'),
PRIMARY KEY CLUSTERED 
(
	[productCode] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[PBMS_ROLE_INFO]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[PBMS_ROLE_INFO](
	[ROLE_ID] [int] IDENTITY(1,1) NOT NULL,
	[ROLE_NAME] [nvarchar](50) NULL,
	[AUTHORITY] [nvarchar](50) NULL,
	[USER_ID] [nvarchar](50) NULL,
	[ALTER_TIME] [datetime] NULL,
 CONSTRAINT [PK_PBMS_ROLE_INFO] PRIMARY KEY CLUSTERED 
(
	[ROLE_ID] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[PBMS_SERV_APPLY]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[PBMS_SERV_APPLY](
	[SEQ_NO] [int] IDENTITY(1,1) NOT NULL,
	[SER_ID] [int] NULL,
	[APPR_ID] [int] NULL,
	[STRU_ID] [nvarchar](50) NULL,
	[USER_ID] [int] NULL,
	[CLIENT_ID] [int] NULL,
	[PBCLIENT_NAME] [nvarchar](50) NULL,
	[MOBILE_PHONE] [int] NULL,
	[APPLY_TIME] [datetime] NULL,
	[APPLY_QUATT] [int] NULL,
	[FILE_URL1] [nvarchar](50) NULL,
	[FILE_URL2] [nvarchar](50) NULL,
	[REMARK] [nvarchar](50) NULL,
	[APPLY_STATUS] [nvarchar](50) NULL,
 CONSTRAINT [PK_PBMS_SERV_APPLY] PRIMARY KEY CLUSTERED 
(
	[SEQ_NO] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[users]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[users](
	[userId] [varchar](50) NOT NULL,
	[userName] [varchar](50) NOT NULL,
	[address] [varchar](255) NULL,
	[contact] [varchar](50) NULL,
	[sex] [varchar](10) NULL,
	[password] [varchar](50) NULL,
	[mail] [varchar](50) NULL,
	[enabled] [int] NULL,
	[business] [int] NOT NULL,
	[rank] [int] NULL,
	[departmentId] [varchar](50) NOT NULL,
	[departmentName] [varchar](50) NULL,
 CONSTRAINT [users_pk] PRIMARY KEY CLUSTERED 
(
	[userId] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Admin]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Admin](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[USER_ID] [int] NULL,
	[Username] [nvarchar](50) NULL,
	[Password] [nvarchar](50) NULL,
	[ROLE_ID] [int] NULL,
	[STRU_ID] [nvarchar](50) NULL,
	[TEL] [nvarchar](50) NULL,
	[MOBIL] [float] NULL,
	[EMAIL] [nvarchar](50) NULL,
	[LastLoginIP] [nvarchar](50) NULL,
	[LastLoginTime] [datetime] NULL,
 CONSTRAINT [PK_Admin] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Info]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Info](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[Topic] [ntext] NULL,
	[TCount] [int] NULL,
	[Option1] [nvarchar](255) NULL,
	[Option2] [nvarchar](255) NULL,
	[Option3] [nvarchar](255) NULL,
	[Option4] [nvarchar](255) NULL,
	[Option5] [nvarchar](255) NULL,
	[Option6] [nvarchar](255) NULL,
	[Option7] [nvarchar](255) NULL,
	[Option8] [nvarchar](255) NULL,
	[Option9] [nvarchar](255) NULL,
	[Option10] [nvarchar](255) NULL,
	[QuesType] [int] NULL,
	[Answer] [nvarchar](255) NULL,
	[Status] [int] NULL,
	[ContType] [int] NULL,
 CONSTRAINT [PK_Info] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[usersRole]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[usersRole](
	[id] [varchar](50) NOT NULL,
	[userId] [varchar](50) NOT NULL,
	[roleId] [varchar](50) NOT NULL,
	[business] [int] NOT NULL,
 CONSTRAINT [usersRole_pk] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[PBMS_STRU_VALUE]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[PBMS_STRU_VALUE](
	[STRU_ID] [nvarchar](50) NOT NULL,
	[TERM] [nvarchar](50) NOT NULL,
	[BEG_VALUE] [int] NULL,
	[SURP_VALUE] [int] NULL,
	[ALTER_TIME] [nvarchar](50) NULL,
 CONSTRAINT [PK_PBMS_STRU_VALUE] PRIMARY KEY CLUSTERED 
(
	[STRU_ID] ASC,
	[TERM] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[role]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[role](
	[roleId] [varchar](50) NOT NULL,
	[roleName] [varchar](50) NOT NULL,
	[type] [varchar](200) NULL,
	[business] [int] NOT NULL,
 CONSTRAINT [role_pk] PRIMARY KEY CLUSTERED 
(
	[roleId] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[PBMS_STRU_INFO]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[PBMS_STRU_INFO](
	[STRU_ID] [nvarchar](50) NOT NULL,
	[STRU_NAME] [nvarchar](50) NULL,
	[STRU_ADDR] [nvarchar](50) NULL,
	[STRU_TEL] [nvarchar](50) NULL,
	[ALTER_TIME] [datetime] NULL,
 CONSTRAINT [PK_PBMS_STRU_INFO] PRIMARY KEY CLUSTERED 
(
	[STRU_ID] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[rolePermission]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[rolePermission](
	[id] [varchar](50) NOT NULL,
	[roleId] [varchar](50) NOT NULL,
	[permissionId] [varchar](50) NOT NULL,
	[business] [int] NOT NULL,
 CONSTRAINT [rolePermission_pk] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[permission]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[permission](
	[permissionId] [varchar](50) NOT NULL,
	[permissionName] [varchar](50) NOT NULL,
	[url] [varchar](255) NULL,
	[imageUrl] [varchar](50) NULL,
	[type] [int] NOT NULL,
	[checked] [int] NULL,
	[parentId] [varchar](50) NULL,
	[business] [int] NOT NULL,
 CONSTRAINT [permission_pk] PRIMARY KEY CLUSTERED 
(
	[permissionId] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[department]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[department](
	[departmentId] [varchar](50) NOT NULL,
	[departmentName] [varchar](50) NOT NULL,
	[anoDepartmentId] [varchar](50) NULL,
	[anoDepartmentName] [varchar](50) NULL,
	[type] [varchar](10) NOT NULL,
	[remark] [varchar](300) NULL,
	[parentId] [varchar](50) NULL,
	[parentDepartmentName] [varchar](50) NULL,
 CONSTRAINT [department_pk] PRIMARY KEY CLUSTERED 
(
	[departmentId] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[PBMS_PHYSICAL_EXAM]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[PBMS_PHYSICAL_EXAM](
	[SEQ_NO] [int] NOT NULL,
	[CLIENT_NAME] [nvarchar](50) NULL,
	[IDCAR_NO] [int] NULL,
	[GENDER] [int] NULL,
	[SERV_DATE] [datetime] NULL,
	[EXAM_TYPE] [nvarchar](50) NULL,
 CONSTRAINT [PK_PBMS_PHYSICAL_EXAM] PRIMARY KEY CLUSTERED 
(
	[SEQ_NO] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[processIns]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[processIns](
	[businessId] [varchar](50) NOT NULL,
	[createUserId] [varchar](50) NULL,
	[createUserName] [varchar](50) NULL,
	[processInsId] [varchar](50) NULL,
	[processTypeName] [varchar](50) NULL,
	[status] [varchar](50) NULL,
	[signTime] [varchar](50) NULL,
	[createTime] [varchar](50) NULL,
	[topic] [varchar](300) NULL,
	[codeId] [varchar](255) NULL,
	[department] [varchar](255) NULL,
	[departmentId] [varchar](255) NULL,
	[startTime] [varchar](255) NULL,
	[completeTime] [varchar](255) NULL,
	[currentTaskId] [varchar](255) NULL,
	[isActivity] [varchar](255) NULL,
	[currentUserId] [varchar](50) NULL,
	[currentUserName] [varchar](50) NULL,
	[currentDepartmentId] [varchar](50) NULL,
	[currentDepartmentName] [varchar](50) NULL,
 CONSTRAINT [processIns_pk] PRIMARY KEY CLUSTERED 
(
	[businessId] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[PBMS_MENU_INFO]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[PBMS_MENU_INFO](
	[MENU_ID] [int] NOT NULL,
	[MENU_NAME] [nvarchar](50) NULL,
	[MENU_TYPE] [int] NULL,
	[MENU_PARENT] [int] NULL,
	[MENU_LINK] [nvarchar](50) NULL,
 CONSTRAINT [PK_PBMS_MENU_INFO] PRIMARY KEY CLUSTERED 
(
	[MENU_ID] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[taskIns]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[taskIns](
	[taskId] [varchar](255) NOT NULL,
	[processInsId] [varchar](255) NULL,
	[taskName] [varchar](255) NULL,
	[assignUserId] [varchar](255) NULL,
	[assignUserName] [varchar](255) NULL,
	[assignDepartment] [varchar](255) NULL,
	[assignDepartmentId] [varchar](255) NULL,
	[preUserId] [varchar](255) NULL,
	[preUserName] [varchar](255) NULL,
	[preDepartment] [varchar](255) NULL,
	[preDepartmentId] [varchar](255) NULL,
	[nextUserId] [varchar](255) NULL,
	[nextUserName] [varchar](255) NULL,
	[nextDepartment] [varchar](255) NULL,
	[nextDepartmentId] [varchar](255) NULL,
	[arriveTime] [varchar](255) NULL,
	[signTime] [varchar](255) NULL,
	[completeTime] [varchar](255) NULL,
	[status] [varchar](255) NULL,
	[isActivity] [varchar](255) NULL,
 CONSTRAINT [taskIns_pk] PRIMARY KEY CLUSTERED 
(
	[taskId] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[PBMS_HOSPITAL_REG]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[PBMS_HOSPITAL_REG](
	[SEQ_NO] [int] NOT NULL,
	[CLIENT_NAME] [nvarchar](50) NULL,
	[IDCAR_NO] [int] NULL,
	[GENDER] [int] NULL,
	[SERV_DATE] [datetime] NULL,
	[HOSPITAL] [int] NULL,
	[MEDICAL_LABOR] [int] NULL,
	[DOCTER] [nvarchar](50) NULL,
	[ILLNESS_DES] [nvarchar](50) NULL,
 CONSTRAINT [PK_PBMS_HOSPITAL_REG] PRIMARY KEY CLUSTERED 
(
	[SEQ_NO] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[redemptionInfo]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[redemptionInfo](
	[businessId] [varchar](255) NOT NULL,
	[contractId] [varchar](255) NULL,
	[codeId] [varchar](255) NULL,
	[remark] [varchar](255) NULL,
	[contractBusinessId] [varchar](255) NULL,
 CONSTRAINT [redemptionInfo_pk] PRIMARY KEY CLUSTERED 
(
	[businessId] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[PBMS_DIC_INFO]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[PBMS_DIC_INFO](
	[DIC_ID] [int] NOT NULL,
	[DIC_NAME] [nvarchar](50) NULL,
	[DIC_VALUE] [int] NOT NULL,
	[DIC_CONT] [nvarchar](50) NULL,
 CONSTRAINT [PK_PBMS_DIC_INFO] PRIMARY KEY CLUSTERED 
(
	[DIC_ID] ASC,
	[DIC_VALUE] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[financialProduct]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[financialProduct](
	[productId] [varchar](255) NOT NULL,
	[productCode] [varchar](255) NULL,
	[productName] [varchar](100) NULL,
	[benefitDate] [varchar](50) NULL,
	[dueDate] [varchar](50) NULL,
	[plannedBenefit] [varchar](50) NULL,
	[isRoll] [varchar](50) NULL,
	[openDay] [varchar](50) NULL,
	[redeemBegin] [varchar](50) NULL,
	[redeemEnd] [varchar](50) NULL,
	[rollBenefit] [varchar](50) NULL,
	[addTime] [varchar](255) NULL,
	[editTime] [varchar](255) NULL,
	[redemptionIntervalId] [varchar](255) NULL,
 CONSTRAINT [financialProduct_pk] PRIMARY KEY CLUSTERED 
(
	[productId] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[PBMS_BOARDING_LIST]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[PBMS_BOARDING_LIST](
	[SEQ_NO] [int] NOT NULL,
	[CLIENT_NAME] [nvarchar](50) NULL,
	[SERV_DATE] [datetime] NULL,
	[FLT_NO] [nvarchar](50) NULL,
	[TAKEOFF_TIME] [datetime] NULL,
	[ARRIVAL_TIME] [datetime] NULL,
	[PROVENACE] [nvarchar](50) NULL,
	[DESTINATION] [nvarchar](50) NULL,
	[PICK_TIME] [datetime] NULL,
	[PICK_ADDR] [nvarchar](50) NULL,
	[CAR_TYPE] [int] NULL,
	[LICENSE_NUMBER] [nvarchar](50) NULL,
	[CONSIGN_LUGGAGE] [int] NULL,
	[PEOPLE_NUM] [int] NULL,
 CONSTRAINT [PK_PBMS_BOARDING_LIST] PRIMARY KEY CLUSTERED 
(
	[SEQ_NO] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[productRedemptionInterval]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[productRedemptionInterval](
	[redemptionIntervalId] [varchar](255) NOT NULL,
	[productId] [varchar](255) NULL,
	[openDay] [varchar](255) NULL,
	[redeemBegin] [varchar](255) NULL,
	[redeemEnd] [varchar](255) NULL,
	[rollBenefit] [varchar](255) NULL,
	[redemptionStatus] [varchar](255) NULL,
 CONSTRAINT [productRedemptionInterval_pk] PRIMARY KEY CLUSTERED 
(
	[redemptionIntervalId] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[contract]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[contract](
	[contractId] [varchar](255) NOT NULL,
	[customeId] [varchar](50) NULL,
	[customeName] [varchar](50) NULL,
	[saleDate] [varchar](50) NULL,
	[productType] [varchar](50) NULL,
	[productCode] [varchar](50) NULL,
	[productName] [varchar](255) NULL,
	[money] [varchar](50) NULL,
	[signAccount] [varchar](50) NULL,
	[saleManager] [varchar](50) NULL,
	[businessManager] [varchar](50) NULL,
	[belongDepartment] [varchar](50) NULL,
	[signDepartment] [varchar](50) NULL,
	[codeId] [varchar](50) NULL,
	[businessId] [varchar](255) NULL,
	[belongDepartmentId] [varchar](255) NULL,
	[signDepartmentId] [varchar](255) NULL,
	[handStatus] [varchar](255) NULL,
	[redempStatus] [varchar](255) NULL,
	[noticeStatus] [varchar](255) NULL,
	[handDate] [varchar](255) NULL,
	[redempDate] [varchar](255) NULL,
	[redemptionIntervalId] [varchar](255) NULL,
	[handXinTuoDate] [varchar](255) NULL,
	[handBackDate] [varchar](255) NULL,
	[redempStartDate] [varchar](255) NULL,
	[redempConformDate] [varchar](255) NULL,
	[getContractDate] [varchar](255) NULL,
	[remark] [varchar](255) NULL,
	[addDate] [varchar](255) NULL,
	[modifyDate] [varchar](255) NULL,
	[operatorName] [varchar](255) NULL,
	[contractInfoStatus] [varchar](255) NULL,
 CONSTRAINT [contract_pk] PRIMARY KEY CLUSTERED 
(
	[contractId] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[notice]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[notice](
	[noticeId] [varchar](255) NOT NULL,
	[noticeType] [varchar](255) NULL,
	[noticeTitle] [varchar](255) NULL,
	[noticeArriveTime] [varchar](255) NULL,
	[noticeViewTime] [varchar](255) NULL,
	[noticeViewStatus] [varchar](255) NULL,
	[noticeDealTime] [varchar](255) NULL,
	[noticeDealStatus] [varchar](255) NULL,
	[assBusinessId] [varchar](255) NULL,
	[departmentId] [varchar](255) NULL,
	[noticeFlag] [varchar](255) NULL,
 CONSTRAINT [notice_pk] PRIMARY KEY CLUSTERED 
(
	[noticeId] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[PBMS_SERV_INFO]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[PBMS_SERV_INFO](
	[SER_ID] [int] NOT NULL,
	[SER_NAME] [nvarchar](50) NULL,
	[SER_DES] [nvarchar](50) NULL,
	[BIG_TYPE] [int] NULL,
	[SML_TYPE] [int] NULL,
	[SER_VALUE] [int] NULL,
	[SER_AMOUNT] [int] NULL,
	[BEG_TIME] [datetime] NULL,
	[END_TIME] [datetime] NULL,
	[SER_PIC] [nvarchar](50) NULL,
	[FILE_URL1] [nvarchar](50) NULL,
	[FILE_URL2] [nvarchar](50) NULL,
	[USER_ID] [nvarchar](50) NULL,
	[ALTER_TIME] [datetime] NULL,
 CONSTRAINT [PK_PBMS_SERV_INFO] PRIMARY KEY CLUSTERED 
(
	[SER_ID] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[tagInfo]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[tagInfo](
	[tagId] [varchar](255) NOT NULL,
	[prefix] [varchar](255) NULL,
	[startCode] [varchar](255) NULL,
	[endCode] [varchar](255) NULL,
	[path] [varchar](255) NULL,
	[printer] [varchar](255) NULL,
	[printDate] [varchar](255) NULL,
	[date] [varchar](255) NULL,
 CONSTRAINT [tagInfo_pk] PRIMARY KEY CLUSTERED 
(
	[tagId] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[PBMS_APPROVE_PARMT]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[PBMS_APPROVE_PARMT](
	[APPR_TYPE] [int] NOT NULL,
	[APPR_NAME] [nvarchar](50) NULL,
	[APPR_SWITCH] [int] NULL,
	[STEP_NUM] [int] NULL,
	[STEP_ROLE1] [nvarchar](50) NULL,
	[STEP_ROLE2] [nvarchar](50) NULL,
	[STEP_ROLE3] [nvarchar](50) NULL,
	[STEP_ROLE4] [nvarchar](50) NULL,
	[STEP_ROLE5] [nvarchar](50) NULL,
	[USER_ID] [nvarchar](50) NULL,
	[ALTER_TIME] [datetime] NULL,
 CONSTRAINT [PK_PBMS_APPROVE_PARMT] PRIMARY KEY CLUSTERED 
(
	[APPR_TYPE] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[attachInfo]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[attachInfo](
	[attachId] [varchar](255) NOT NULL,
	[businessId] [varchar](255) NULL,
	[attachName] [varchar](255) NULL,
 CONSTRAINT [attachInfo_pk] PRIMARY KEY CLUSTERED 
(
	[attachId] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[PBMS_APPROVE_INFO]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[PBMS_APPROVE_INFO](
	[APPR_ID] [int] IDENTITY(1,1) NOT NULL,
	[APPR_TYPE] [int] NULL,
	[APPR_STATUS] [int] NULL,
	[APPLY_NO] [int] NULL,
	[APPLY_USER_ID] [nvarchar](50) NULL,
	[CUR_USER_ID] [nvarchar](50) NULL,
	[APPLY_TIME] [nvarchar](50) NULL,
	[APPR_STEP] [int] NULL,
	[USER_ID1] [nvarchar](50) NULL,
	[APPR_TIME1] [nvarchar](50) NULL,
	[PROC_RESULT1] [int] NULL,
	[REMARK1] [nvarchar](50) NULL,
	[USER_ID2] [nvarchar](50) NULL,
	[APPR_TIME2] [nvarchar](50) NULL,
	[PROC_RESULT2] [int] NULL,
	[REMARK2] [nvarchar](50) NULL,
	[USER_ID3] [nvarchar](50) NULL,
	[APPR_TIME3] [nvarchar](50) NULL,
	[PROC_RESULT3] [int] NULL,
	[REMARK3] [nvarchar](50) NULL,
	[USER_ID4] [nvarchar](50) NULL,
	[APPR_TIME4] [nvarchar](50) NULL,
	[PROC_RESULT4] [int] NULL,
	[REMARK4] [nvarchar](50) NULL,
	[USER_ID5] [nvarchar](50) NULL,
	[APPR_TIME5] [nvarchar](50) NULL,
	[PROC_RESULT5] [int] NULL,
	[REMARK5] [nvarchar](50) NULL,
	[APPR_CONT] [ntext] NULL,
	[APPR_PARAM1] [nvarchar](50) NULL,
	[APPR_PARAM2] [nvarchar](50) NULL,
	[APPR_PARAM3] [nvarchar](50) NULL,
	[APPR_PARAM4] [nvarchar](50) NULL,
	[APPR_PARAM5] [nvarchar](50) NULL,
 CONSTRAINT [PK_PBMS_APPROVE_INFO] PRIMARY KEY CLUSTERED 
(
	[APPR_ID] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[redempBook]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[redempBook](
	[contractId] [varchar](255) NOT NULL,
	[productId] [varchar](255) NULL,
	[productCode] [varchar](255) NULL,
	[productName] [varchar](255) NULL,
	[benefitDate] [varchar](255) NULL,
	[dueDate] [varchar](255) NULL,
	[plannedBenefit] [varchar](255) NULL,
	[customeId] [varchar](255) NULL,
	[customeName] [varchar](255) NULL,
	[signAccount] [varchar](255) NULL,
	[money] [varchar](255) NULL,
	[belongDepartment] [varchar](255) NULL,
	[signDepartment] [varchar](255) NULL,
	[redempStartDate] [varchar](255) NULL,
	[redempConformDate] [varchar](255) NULL,
	[openDay] [varchar](255) NULL,
	[redeemBegin] [varchar](255) NULL,
	[redeemEnd] [varchar](255) NULL,
	[rollBenefit] [varchar](255) NULL,
	[redempStatus] [varchar](255) NULL,
 CONSTRAINT [redempBook_pk] PRIMARY KEY CLUSTERED 
(
	[contractId] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[contractRedempTime]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[contractRedempTime](
	[id] [varchar](255) NOT NULL,
	[contractId] [varchar](255) NULL,
	[openDay] [varchar](255) NULL,
	[redeemBegin] [varchar](255) NULL,
	[redeemEnd] [varchar](255) NULL,
	[rollBenefit] [varchar](255) NULL,
	[redemptionStatus] [varchar](255) NULL,
 CONSTRAINT [contractRedempTime_pk] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[JBPM4_DEPLOYMENT]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[JBPM4_DEPLOYMENT](
	[DBID_] [numeric](19, 0) NOT NULL,
	[NAME_] [text] NULL,
	[TIMESTAMP_] [numeric](19, 0) NULL,
	[STATE_] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[DBID_] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[JBPM4_EXECUTION]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[JBPM4_EXECUTION](
	[DBID_] [numeric](19, 0) NOT NULL,
	[CLASS_] [varchar](255) NOT NULL,
	[DBVERSION_] [numeric](10, 0) NOT NULL,
	[ACTIVITYNAME_] [varchar](255) NULL,
	[PROCDEFID_] [varchar](255) NULL,
	[HASVARS_] [numeric](1, 0) NULL,
	[NAME_] [varchar](255) NULL,
	[KEY_] [varchar](255) NULL,
	[ID_] [varchar](255) NULL,
	[STATE_] [varchar](255) NULL,
	[SUSPHISTSTATE_] [varchar](255) NULL,
	[PRIORITY_] [numeric](10, 0) NULL,
	[HISACTINST_] [numeric](19, 0) NULL,
	[PARENT_] [numeric](19, 0) NULL,
	[INSTANCE_] [numeric](19, 0) NULL,
	[SUPEREXEC_] [numeric](19, 0) NULL,
	[SUBPROCINST_] [numeric](19, 0) NULL,
	[PARENT_IDX_] [numeric](10, 0) NULL,
PRIMARY KEY CLUSTERED 
(
	[DBID_] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[ID_] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[JBPM4_HIST_PROCINST]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[JBPM4_HIST_PROCINST](
	[DBID_] [numeric](19, 0) NOT NULL,
	[DBVERSION_] [numeric](10, 0) NOT NULL,
	[ID_] [varchar](255) NULL,
	[PROCDEFID_] [varchar](255) NULL,
	[KEY_] [varchar](255) NULL,
	[START_] [datetime] NULL,
	[END_] [datetime] NULL,
	[DURATION_] [numeric](19, 0) NULL,
	[STATE_] [varchar](255) NULL,
	[ENDACTIVITY_] [varchar](255) NULL,
	[NEXTIDX_] [numeric](10, 0) NULL,
PRIMARY KEY CLUSTERED 
(
	[DBID_] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[JBPM4_HIST_TASK]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[JBPM4_HIST_TASK](
	[DBID_] [numeric](19, 0) NOT NULL,
	[DBVERSION_] [numeric](10, 0) NOT NULL,
	[EXECUTION_] [varchar](255) NULL,
	[OUTCOME_] [varchar](255) NULL,
	[ASSIGNEE_] [varchar](255) NULL,
	[PRIORITY_] [numeric](10, 0) NULL,
	[STATE_] [varchar](255) NULL,
	[CREATE_] [datetime] NULL,
	[END_] [datetime] NULL,
	[DURATION_] [numeric](19, 0) NULL,
	[NEXTIDX_] [numeric](10, 0) NULL,
	[SUPERTASK_] [numeric](19, 0) NULL,
PRIMARY KEY CLUSTERED 
(
	[DBID_] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[JBPM4_ID_GROUP]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[JBPM4_ID_GROUP](
	[DBID_] [numeric](19, 0) NOT NULL,
	[DBVERSION_] [numeric](10, 0) NOT NULL,
	[ID_] [varchar](255) NULL,
	[NAME_] [varchar](255) NULL,
	[TYPE_] [varchar](255) NULL,
	[PARENT_] [numeric](19, 0) NULL,
PRIMARY KEY CLUSTERED 
(
	[DBID_] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[JBPM4_ID_USER]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[JBPM4_ID_USER](
	[DBID_] [numeric](19, 0) NOT NULL,
	[DBVERSION_] [numeric](10, 0) NOT NULL,
	[ID_] [varchar](255) NULL,
	[PASSWORD_] [varchar](255) NULL,
	[GIVENNAME_] [varchar](255) NULL,
	[FAMILYNAME_] [varchar](255) NULL,
	[BUSINESSEMAIL_] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[DBID_] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[JBPM4_PROPERTY]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[JBPM4_PROPERTY](
	[KEY_] [varchar](255) NOT NULL,
	[VERSION_] [numeric](10, 0) NOT NULL,
	[VALUE_] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[KEY_] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[JBPM4_LOB]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[JBPM4_LOB](
	[DBID_] [numeric](19, 0) NOT NULL,
	[DBVERSION_] [numeric](10, 0) NOT NULL,
	[BLOB_VALUE_] [image] NULL,
	[DEPLOYMENT_] [numeric](19, 0) NULL,
	[NAME_] [text] NULL,
PRIMARY KEY CLUSTERED 
(
	[DBID_] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[JBPM4_DEPLOYPROP]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[JBPM4_DEPLOYPROP](
	[DBID_] [numeric](19, 0) NOT NULL,
	[DEPLOYMENT_] [numeric](19, 0) NULL,
	[OBJNAME_] [varchar](255) NULL,
	[KEY_] [varchar](255) NULL,
	[STRINGVAL_] [varchar](255) NULL,
	[LONGVAL_] [numeric](19, 0) NULL,
PRIMARY KEY CLUSTERED 
(
	[DBID_] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[JBPM4_SWIMLANE]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[JBPM4_SWIMLANE](
	[DBID_] [numeric](19, 0) NOT NULL,
	[DBVERSION_] [numeric](10, 0) NOT NULL,
	[NAME_] [varchar](255) NULL,
	[ASSIGNEE_] [varchar](255) NULL,
	[EXECUTION_] [numeric](19, 0) NULL,
PRIMARY KEY CLUSTERED 
(
	[DBID_] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[JBPM4_VARIABLE]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[JBPM4_VARIABLE](
	[DBID_] [numeric](19, 0) NOT NULL,
	[CLASS_] [varchar](255) NOT NULL,
	[DBVERSION_] [numeric](10, 0) NOT NULL,
	[KEY_] [varchar](255) NULL,
	[CONVERTER_] [varchar](255) NULL,
	[HIST_] [numeric](1, 0) NULL,
	[EXECUTION_] [numeric](19, 0) NULL,
	[TASK_] [numeric](19, 0) NULL,
	[LOB_] [numeric](19, 0) NULL,
	[DATE_VALUE_] [datetime] NULL,
	[DOUBLE_VALUE_] [float] NULL,
	[CLASSNAME_] [varchar](255) NULL,
	[LONG_VALUE_] [numeric](19, 0) NULL,
	[STRING_VALUE_] [varchar](255) NULL,
	[TEXT_VALUE_] [text] NULL,
	[EXESYS_] [numeric](19, 0) NULL,
PRIMARY KEY CLUSTERED 
(
	[DBID_] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[JBPM4_HIST_DETAIL]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[JBPM4_HIST_DETAIL](
	[DBID_] [numeric](19, 0) NOT NULL,
	[CLASS_] [varchar](255) NOT NULL,
	[DBVERSION_] [numeric](10, 0) NOT NULL,
	[USERID_] [varchar](255) NULL,
	[TIME_] [datetime] NULL,
	[HPROCI_] [numeric](19, 0) NULL,
	[HPROCIIDX_] [numeric](10, 0) NULL,
	[HACTI_] [numeric](19, 0) NULL,
	[HACTIIDX_] [numeric](10, 0) NULL,
	[HTASK_] [numeric](19, 0) NULL,
	[HTASKIDX_] [numeric](10, 0) NULL,
	[HVAR_] [numeric](19, 0) NULL,
	[HVARIDX_] [numeric](10, 0) NULL,
	[MESSAGE_] [text] NULL,
	[OLD_STR_] [varchar](255) NULL,
	[NEW_STR_] [varchar](255) NULL,
	[OLD_INT_] [numeric](10, 0) NULL,
	[NEW_INT_] [numeric](10, 0) NULL,
	[OLD_TIME_] [datetime] NULL,
	[NEW_TIME_] [datetime] NULL,
	[PARENT_] [numeric](19, 0) NULL,
	[PARENT_IDX_] [numeric](10, 0) NULL,
PRIMARY KEY CLUSTERED 
(
	[DBID_] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[JBPM4_HIST_VAR]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[JBPM4_HIST_VAR](
	[DBID_] [numeric](19, 0) NOT NULL,
	[DBVERSION_] [numeric](10, 0) NOT NULL,
	[PROCINSTID_] [varchar](255) NULL,
	[EXECUTIONID_] [varchar](255) NULL,
	[VARNAME_] [varchar](255) NULL,
	[VALUE_] [varchar](255) NULL,
	[HPROCI_] [numeric](19, 0) NULL,
	[HTASK_] [numeric](19, 0) NULL,
PRIMARY KEY CLUSTERED 
(
	[DBID_] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[JBPM4_HIST_ACTINST]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[JBPM4_HIST_ACTINST](
	[DBID_] [numeric](19, 0) NOT NULL,
	[CLASS_] [varchar](255) NOT NULL,
	[DBVERSION_] [numeric](10, 0) NOT NULL,
	[HPROCI_] [numeric](19, 0) NULL,
	[TYPE_] [varchar](255) NULL,
	[EXECUTION_] [varchar](255) NULL,
	[ACTIVITY_NAME_] [varchar](255) NULL,
	[START_] [datetime] NULL,
	[END_] [datetime] NULL,
	[DURATION_] [numeric](19, 0) NULL,
	[TRANSITION_] [varchar](255) NULL,
	[NEXTIDX_] [numeric](10, 0) NULL,
	[HTASK_] [numeric](19, 0) NULL,
PRIMARY KEY CLUSTERED 
(
	[DBID_] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[JBPM4_ID_MEMBERSHIP]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[JBPM4_ID_MEMBERSHIP](
	[DBID_] [numeric](19, 0) NOT NULL,
	[DBVERSION_] [numeric](10, 0) NOT NULL,
	[USER_] [numeric](19, 0) NULL,
	[GROUP_] [numeric](19, 0) NULL,
	[NAME_] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[DBID_] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[JBPM4_JOB]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[JBPM4_JOB](
	[DBID_] [numeric](19, 0) NOT NULL,
	[CLASS_] [varchar](255) NOT NULL,
	[DBVERSION_] [numeric](10, 0) NOT NULL,
	[DUEDATE_] [datetime] NULL,
	[STATE_] [varchar](255) NULL,
	[ISEXCLUSIVE_] [numeric](1, 0) NULL,
	[LOCKOWNER_] [varchar](255) NULL,
	[LOCKEXPTIME_] [datetime] NULL,
	[EXCEPTION_] [text] NULL,
	[RETRIES_] [numeric](10, 0) NULL,
	[PROCESSINSTANCE_] [numeric](19, 0) NULL,
	[EXECUTION_] [numeric](19, 0) NULL,
	[CFG_] [numeric](19, 0) NULL,
	[SIGNAL_] [varchar](255) NULL,
	[EVENT_] [varchar](255) NULL,
	[REPEAT_] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[DBID_] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[JBPM4_TASK]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[JBPM4_TASK](
	[DBID_] [numeric](19, 0) NOT NULL,
	[CLASS_] [char](1) NOT NULL,
	[DBVERSION_] [numeric](10, 0) NOT NULL,
	[NAME_] [varchar](255) NULL,
	[DESCR_] [text] NULL,
	[STATE_] [varchar](255) NULL,
	[SUSPHISTSTATE_] [varchar](255) NULL,
	[ASSIGNEE_] [varchar](255) NULL,
	[FORM_] [varchar](255) NULL,
	[PRIORITY_] [numeric](10, 0) NULL,
	[CREATE_] [datetime] NULL,
	[DUEDATE_] [datetime] NULL,
	[PROGRESS_] [numeric](10, 0) NULL,
	[SIGNALLING_] [numeric](1, 0) NULL,
	[EXECUTION_ID_] [varchar](255) NULL,
	[ACTIVITY_NAME_] [varchar](255) NULL,
	[HASVARS_] [numeric](1, 0) NULL,
	[SUPERTASK_] [numeric](19, 0) NULL,
	[EXECUTION_] [numeric](19, 0) NULL,
	[PROCINST_] [numeric](19, 0) NULL,
	[SWIMLANE_] [numeric](19, 0) NULL,
	[TASKDEFNAME_] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[DBID_] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[JBPM4_PARTICIPATION]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[JBPM4_PARTICIPATION](
	[DBID_] [numeric](19, 0) NOT NULL,
	[DBVERSION_] [numeric](10, 0) NOT NULL,
	[GROUPID_] [varchar](255) NULL,
	[USERID_] [varchar](255) NULL,
	[TYPE_] [varchar](255) NULL,
	[TASK_] [numeric](19, 0) NULL,
	[SWIMLANE_] [numeric](19, 0) NULL,
PRIMARY KEY CLUSTERED 
(
	[DBID_] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[usersRole_View]'))
EXEC dbo.sp_executesql @statement = N'CREATE VIEW [dbo].[usersRole_View]
AS
SELECT     dbo.usersRole.roleId, dbo.users.userId, dbo.users.username, dbo.users.address, dbo.users.contact, dbo.users.sex, dbo.users.password, dbo.users.mail, 
                      dbo.users.enabled, dbo.users.business, dbo.users.rank, dbo.users.departmentId, dbo.users.departmentName
FROM         dbo.users INNER JOIN
                      dbo.usersRole ON dbo.users.userId = dbo.usersRole.userId



' 
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[userDepartmentView]'))
EXEC dbo.sp_executesql @statement = N'CREATE VIEW [dbo].[userDepartmentView]
AS
SELECT     dbo.users.userId, dbo.users.username, dbo.users.address, dbo.users.contact, dbo.users.sex, dbo.users.password, dbo.users.mail, dbo.users.enabled, 
                      dbo.users.business, dbo.users.rank, dbo.users.departmentId, dbo.department.departmentName
FROM         dbo.users LEFT OUTER JOIN
                      dbo.department ON dbo.users.departmentId = dbo.department.departmentId



' 
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[departmentView]'))
EXEC dbo.sp_executesql @statement = N'CREATE VIEW [dbo].[departmentView]
AS
SELECT     dbo.department.departmentId, dbo.department.departmentName, dbo.department.anoDepartmentId, dbo.department.anoDepartmentName, dbo.department.type, 
                      dbo.department.remark, dbo.department.parentId, department_1.departmentName AS parentDepartmentName
FROM         dbo.department LEFT OUTER JOIN
                      dbo.department AS department_1 ON dbo.department.parentId = department_1.departmentId


' 
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[noticeView]'))
EXEC dbo.sp_executesql @statement = N'CREATE VIEW [dbo].[noticeView]
AS
SELECT     dbo.notice.noticeId, dbo.notice.noticeType, dbo.notice.noticeTitle, dbo.notice.noticeArriveTime, dbo.notice.noticeViewTime, dbo.notice.noticeViewStatus, 
                      dbo.notice.noticeDealTime, dbo.notice.noticeDealStatus, dbo.notice.assBusinessId, dbo.notice.departmentId, dbo.notice.noticeFlag, 
                      dbo.department.departmentName
FROM         dbo.notice INNER JOIN
                      dbo.department ON dbo.notice.departmentId = dbo.department.departmentId


' 
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[finishedProcessTask_View]'))
EXEC dbo.sp_executesql @statement = N'CREATE VIEW [dbo].[finishedProcessTask_View]
AS
SELECT     dbo.processIns.businessId, dbo.processIns.createUserId, dbo.processIns.createUserName, dbo.processIns.processTypeName, 
                      dbo.processIns.status AS processStatus, dbo.processIns.topic, dbo.processIns.department, dbo.processIns.startTime, dbo.processIns.isActivity AS processIsActivity, 
                      dbo.taskIns.taskId, dbo.taskIns.assignUserId, dbo.taskIns.preUserId, dbo.taskIns.preDepartment, dbo.taskIns.preUserName, dbo.taskIns.nextDepartment, 
                      dbo.taskIns.nextUserName, dbo.taskIns.arriveTime, dbo.taskIns.signTime, dbo.taskIns.isActivity AS taskIsActivity, dbo.taskIns.completeTime, dbo.taskIns.nextUserId, 
                      dbo.processIns.completeTime AS finishedTime
FROM         dbo.processIns INNER JOIN
                      dbo.taskIns ON dbo.processIns.processInsId = dbo.taskIns.processInsId



' 
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[completedProcessTask_View]'))
EXEC dbo.sp_executesql @statement = N'CREATE VIEW [dbo].[completedProcessTask_View]
AS
SELECT     dbo.processIns.businessId, dbo.taskIns.taskId, dbo.processIns.createUserName, dbo.processIns.processTypeName, dbo.processIns.status AS processStatus, 
                      dbo.processIns.topic, dbo.processIns.department, dbo.processIns.startTime, dbo.processIns.isActivity AS processIsActivity, dbo.taskIns.preDepartment, 
                      dbo.taskIns.nextDepartment, dbo.taskIns.arriveTime, dbo.taskIns.signTime, dbo.taskIns.completeTime, dbo.taskIns.isActivity AS taskIsActivity, 
                      dbo.taskIns.status AS taskStatus, dbo.taskIns.assignUserId, dbo.processIns.processInsId, dbo.taskIns.preUserName, dbo.taskIns.nextUserName, 
                      dbo.taskIns.preUserId, dbo.taskIns.nextUserId, dbo.processIns.createUserId, dbo.processIns.currentUserId, dbo.processIns.currentUserName, 
                      dbo.processIns.currentDepartmentId, dbo.processIns.currentDepartmentName
FROM         dbo.processIns INNER JOIN
                      dbo.taskIns ON dbo.processIns.processInsId = dbo.taskIns.processInsId


' 
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[waitDealProcessTask_View]'))
EXEC dbo.sp_executesql @statement = N'CREATE VIEW [dbo].[waitDealProcessTask_View]
AS
SELECT     dbo.taskIns.taskId, dbo.taskIns.processInsId, dbo.taskIns.taskName, dbo.taskIns.assignUserId, dbo.taskIns.assignDepartment, dbo.taskIns.preUserId, 
                      dbo.taskIns.preDepartment, dbo.taskIns.arriveTime, dbo.taskIns.status AS taskStatus, dbo.taskIns.isActivity AS taskIsActivity, dbo.processIns.createUserId, 
                      dbo.processIns.createUserName, dbo.processIns.businessId, dbo.processIns.processTypeName, dbo.processIns.status AS processStatus, 
                      dbo.processIns.department, dbo.processIns.topic, dbo.processIns.startTime, dbo.processIns.isActivity AS processIsActivity, dbo.processIns.createTime, 
                      dbo.taskIns.preUserName, dbo.processIns.signTime, dbo.processIns.codeId
FROM         dbo.processIns INNER JOIN
                      dbo.taskIns ON dbo.processIns.processInsId = dbo.taskIns.processInsId



' 
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[redempBookView]'))
EXEC dbo.sp_executesql @statement = N'CREATE VIEW [dbo].[redempBookView]
AS
SELECT     dbo.contract.contractId, dbo.financialProduct.productCode, dbo.financialProduct.productName, dbo.financialProduct.benefitDate, dbo.financialProduct.dueDate, 
                      dbo.financialProduct.plannedBenefit, dbo.contract.customeId, dbo.contract.customeName, dbo.contract.money, dbo.contract.signAccount, 
                      dbo.contract.belongDepartment, dbo.contract.signDepartment, dbo.contract.redempStartDate, dbo.contract.redempConformDate, dbo.contract.redempStatus, 
                      dbo.financialProduct.productId, dbo.contractRedempTime.openDay, dbo.contractRedempTime.redeemBegin, dbo.contractRedempTime.redeemEnd, 
                      dbo.contractRedempTime.rollBenefit
FROM         dbo.contract INNER JOIN
                      dbo.financialProduct ON dbo.contract.productCode = dbo.financialProduct.productCode LEFT OUTER JOIN
                      dbo.contractRedempTime ON dbo.contract.contractId = dbo.contractRedempTime.contractId
WHERE     (dbo.financialProduct.isRoll = ''是'')

' 
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[redemptionIdByProId_View]'))
EXEC dbo.sp_executesql @statement = N'CREATE VIEW [dbo].[redemptionIdByProId_View]
AS
SELECT     dbo.productRedemptionInterval.redemptionIntervalId, dbo.financialProduct.productId
FROM         dbo.financialProduct INNER JOIN
                      dbo.productRedemptionInterval ON dbo.financialProduct.productId = dbo.productRedemptionInterval.productId



' 
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[productDueDateView]'))
EXEC dbo.sp_executesql @statement = N'CREATE VIEW [dbo].[productDueDateView]
AS
SELECT     dbo.contract.customeId, dbo.contract.customeName, dbo.contract.belongDepartmentId, dbo.contract.signDepartmentId, dbo.contract.contractId, 
                      dbo.financialProduct.productCode, dbo.financialProduct.productName, dbo.financialProduct.dueDate, dbo.contract.redempStatus
FROM         dbo.contract INNER JOIN
                      dbo.financialProduct ON dbo.contract.productCode = dbo.financialProduct.productCode

' 
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[productList_View]'))
EXEC dbo.sp_executesql @statement = N'CREATE VIEW [dbo].[productList_View]
AS
SELECT     TOP (100) PERCENT dbo.financialProduct.productId, dbo.financialProduct.productCode, dbo.financialProduct.productName, dbo.financialProduct.benefitDate, 
                      dbo.financialProduct.dueDate, dbo.financialProduct.plannedBenefit, dbo.financialProduct.isRoll, dbo.productRedemptionInterval.openDay, 
                      dbo.productRedemptionInterval.redeemBegin, dbo.productRedemptionInterval.redeemEnd, dbo.productRedemptionInterval.rollBenefit, dbo.financialProduct.addTime, 
                      dbo.financialProduct.editTime, dbo.productRedemptionInterval.redemptionIntervalId
FROM         dbo.financialProduct INNER JOIN
                      dbo.productRedemptionInterval ON dbo.financialProduct.productId = dbo.productRedemptionInterval.productId
ORDER BY dbo.financialProduct.addTime DESC, dbo.productRedemptionInterval.openDay DESC



' 
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[contractView]'))
EXEC dbo.sp_executesql @statement = N'CREATE VIEW [dbo].[contractView]
AS
SELECT     dbo.contract.contractId, dbo.contract.customeId, dbo.contract.customeName, dbo.contract.saleDate, dbo.contract.productType, dbo.contract.money, 
                      dbo.contract.signAccount, dbo.contract.saleManager, dbo.contract.businessManager, dbo.contract.belongDepartment, dbo.contract.signDepartment, 
                      dbo.contract.codeId, dbo.contract.businessId, dbo.contract.belongDepartmentId, dbo.contract.signDepartmentId, dbo.contract.handStatus, dbo.contract.redempStatus, 
                      dbo.contract.noticeStatus, dbo.contract.handDate, dbo.contract.redempDate, dbo.contract.redemptionIntervalId, dbo.contract.handXinTuoDate, 
                      dbo.contract.handBackDate, dbo.contract.redempStartDate, dbo.contract.redempConformDate, dbo.contract.getContractDate, dbo.contract.remark, 
                      dbo.financialProduct.productCode, dbo.contract.addDate, dbo.contract.modifyDate, dbo.contract.operatorName, dbo.contract.contractInfoStatus, 
                      dbo.contract.productName
FROM         dbo.contract LEFT OUTER JOIN
                      dbo.financialProduct ON dbo.contract.productCode = dbo.financialProduct.productCode

' 
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[branchContractView]'))
EXEC dbo.sp_executesql @statement = N'CREATE VIEW [dbo].[branchContractView]
AS
SELECT     dbo.contract.contractId, dbo.contract.customeId, dbo.contract.customeName, dbo.contract.signAccount, dbo.contract.handStatus, dbo.contract.handDate, 
                      dbo.contract.getContractDate, dbo.contract.redempStatus, dbo.contract.redempStartDate, dbo.contract.redempConformDate, dbo.financialProduct.productId, 
                      dbo.financialProduct.productCode, dbo.financialProduct.productName, dbo.financialProduct.benefitDate, dbo.financialProduct.dueDate, 
                      dbo.financialProduct.plannedBenefit, dbo.financialProduct.isRoll, dbo.contract.redemptionIntervalId, dbo.contract.belongDepartmentId, dbo.contract.signDepartmentId, 
                      dbo.contract.contractInfoStatus, dbo.contractRedempTime.openDay, dbo.contractRedempTime.redeemBegin, dbo.contractRedempTime.redeemEnd, 
                      dbo.contractRedempTime.rollBenefit
FROM         dbo.contract INNER JOIN
                      dbo.financialProduct ON dbo.contract.productCode = dbo.financialProduct.productCode LEFT OUTER JOIN
                      dbo.contractRedempTime ON dbo.contract.contractId = dbo.contractRedempTime.contractId

' 
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[branchProductContractView]'))
EXEC dbo.sp_executesql @statement = N'CREATE VIEW [dbo].[branchProductContractView]
AS
SELECT     dbo.contract.contractId, dbo.contract.customeId, dbo.contract.customeName, dbo.contract.belongDepartment, dbo.contract.signDepartment, dbo.contract.redempStatus, 
                      dbo.financialProduct.productId, dbo.financialProduct.productCode, dbo.financialProduct.productName, dbo.financialProduct.dueDate, dbo.financialProduct.isRoll, 
                      dbo.productRedemptionInterval.redemptionIntervalId, dbo.productRedemptionInterval.openDay, dbo.productRedemptionInterval.redeemBegin, 
                      dbo.productRedemptionInterval.redeemEnd, dbo.contract.belongDepartmentId, dbo.contract.signDepartmentId, dbo.contract.contractInfoStatus, 
                      dbo.productRedemptionInterval.redemptionStatus
FROM         dbo.contract INNER JOIN
                      dbo.financialProduct ON dbo.contract.productCode = dbo.financialProduct.productCode LEFT OUTER JOIN
                      dbo.productRedemptionInterval ON dbo.financialProduct.productId = dbo.productRedemptionInterval.productId

' 
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[branchProductContractShowView]'))
EXEC dbo.sp_executesql @statement = N'CREATE VIEW [dbo].[branchProductContractShowView]
AS
SELECT     dbo.contract.contractId, dbo.contract.customeId, dbo.contract.customeName, dbo.contract.belongDepartment, dbo.contract.signDepartment, dbo.contract.redempStatus, 
                      dbo.financialProduct.productId, dbo.financialProduct.productCode, dbo.financialProduct.productName, dbo.financialProduct.dueDate, dbo.financialProduct.isRoll, 
                      dbo.financialProduct.openDay, dbo.financialProduct.redeemBegin, dbo.financialProduct.redeemEnd, dbo.financialProduct.rollBenefit, dbo.contract.productType
FROM         dbo.contract INNER JOIN
                      dbo.financialProduct ON dbo.contract.productCode = dbo.financialProduct.productCode

' 
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[contractProductView]'))
EXEC dbo.sp_executesql @statement = N'CREATE VIEW [dbo].[contractProductView]
AS
SELECT     dbo.contract.contractId, dbo.financialProduct.productId, dbo.financialProduct.isRoll, dbo.financialProduct.productCode, dbo.financialProduct.productName
FROM         dbo.contract INNER JOIN
                      dbo.financialProduct ON dbo.contract.productCode = dbo.financialProduct.productCode
WHERE     (dbo.financialProduct.isRoll = ''是'')

' 
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[contractRedempUpdateView]'))
EXEC dbo.sp_executesql @statement = N'CREATE VIEW [dbo].[contractRedempUpdateView]
AS
SELECT     dbo.contract.contractId, dbo.financialProduct.productId, dbo.contractRedempTime.openDay, dbo.contractRedempTime.redeemBegin, 
                      dbo.contractRedempTime.redeemEnd, dbo.contractRedempTime.rollBenefit
FROM         dbo.contractRedempTime INNER JOIN
                      dbo.contract ON dbo.contractRedempTime.contractId = dbo.contract.contractId INNER JOIN
                      dbo.financialProduct ON dbo.contract.productCode = dbo.financialProduct.productCode

' 
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[contractRedemption_View]'))
EXEC dbo.sp_executesql @statement = N'CREATE VIEW [dbo].[contractRedemption_View]
AS
SELECT     dbo.contract.contractId, dbo.contract.customeId, dbo.contract.customeName, dbo.contract.belongDepartmentId, dbo.contract.signDepartmentId, 
                      dbo.contract.redempStatus, dbo.financialProduct.productCode, dbo.financialProduct.productName, dbo.contractRedempTime.openDay, 
                      dbo.contractRedempTime.redeemBegin, dbo.contractRedempTime.redeemEnd, dbo.contractRedempTime.rollBenefit
FROM         dbo.contract INNER JOIN
                      dbo.financialProduct ON dbo.contract.productCode = dbo.financialProduct.productCode INNER JOIN
                      dbo.contractRedempTime ON dbo.contract.contractId = dbo.contractRedempTime.contractId

' 
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[RedempIntervalReportView]'))
EXEC dbo.sp_executesql @statement = N'CREATE VIEW [dbo].[RedempIntervalReportView]
AS
SELECT     dbo.productRedemptionInterval.redemptionIntervalId, dbo.productRedemptionInterval.productId, dbo.productRedemptionInterval.openDay, 
                      dbo.productRedemptionInterval.redeemBegin, dbo.productRedemptionInterval.redeemEnd, dbo.productRedemptionInterval.rollBenefit, 
                      dbo.productRedemptionInterval.redemptionStatus, dbo.contract.contractId
FROM         dbo.productRedemptionInterval LEFT OUTER JOIN
                      dbo.contract ON dbo.productRedemptionInterval.redemptionIntervalId = dbo.contract.redemptionIntervalId


' 
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[productContract_View]'))
EXEC dbo.sp_executesql @statement = N'CREATE VIEW [dbo].[productContract_View]
AS
SELECT     dbo.productList_View.productCode, dbo.productList_View.openDay, dbo.productList_View.redeemBegin, dbo.productList_View.redeemEnd, 
                      dbo.contract.belongDepartment, dbo.contract.signDepartment, dbo.contract.contractId, dbo.contract.addDate, dbo.contract.contractInfoStatus, dbo.contract.customeId, 
                      dbo.contract.customeName, dbo.contract.handStatus, dbo.contract.productName
FROM         dbo.productList_View INNER JOIN
                      dbo.contract ON dbo.productList_View.productCode = dbo.contract.productCode

' 
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[contractRedempView]'))
EXEC dbo.sp_executesql @statement = N'
/** modify on 2014/08/06
CREATE VIEW [dbo].[contractRedempView]
AS
SELECT     dbo.contract.contractId, dbo.contract.customeId, dbo.contract.customeName, dbo.contract.saleDate, dbo.contract.productType, dbo.contract.money, 
                      dbo.contract.signAccount, dbo.contract.saleManager, dbo.contract.businessManager, dbo.contract.belongDepartment, dbo.contract.signDepartment, 
                      dbo.contract.codeId, dbo.contract.businessId, dbo.contract.belongDepartmentId, dbo.contract.signDepartmentId, dbo.contract.handStatus, dbo.contract.redempStatus, 
                      dbo.contract.noticeStatus, dbo.contract.handDate, dbo.contract.redempDate, dbo.contract.redemptionIntervalId, dbo.contract.handXinTuoDate, 
                      dbo.contract.handBackDate, dbo.contract.redempStartDate, dbo.contract.redempConformDate, dbo.contract.remark, dbo.contract.getContractDate, 
                      dbo.contractProductView.productCode, dbo.contractProductView.productName, dbo.contract.addDate, dbo.contract.modifyDate, dbo.contract.operatorName, 
                      dbo.contract.contractInfoStatus
FROM         dbo.contract INNER JOIN
                      dbo.contractProductView ON dbo.contract.contractId = dbo.contractProductView.contractId
*/
CREATE VIEW [dbo].[contractRedempView]
AS
SELECT     dbo.contract.contractId, dbo.contract.customeId, dbo.contract.customeName, dbo.contract.saleDate, dbo.contract.productType, dbo.contract.money, 
                      dbo.contract.signAccount, dbo.contract.saleManager, dbo.contract.businessManager, dbo.contract.belongDepartment, dbo.contract.signDepartment, 
                      dbo.contract.codeId, dbo.contract.businessId, dbo.contract.belongDepartmentId, dbo.contract.signDepartmentId, dbo.contract.handStatus, dbo.contract.redempStatus, 
                      dbo.contract.noticeStatus, dbo.contract.handDate, dbo.contract.redempDate, dbo.contract.redemptionIntervalId, dbo.contract.handXinTuoDate, 
                      dbo.contract.handBackDate, dbo.contract.redempStartDate, dbo.contract.redempConformDate, dbo.contract.remark, dbo.contract.getContractDate, 
                      dbo.contractProductView.productCode, dbo.contractProductView.productName, dbo.contract.addDate, dbo.contract.modifyDate, dbo.contract.operatorName, 
                      dbo.contract.contractInfoStatus
FROM         dbo.contract INNER JOIN
                      dbo.contractProductView ON dbo.contract.contractId = dbo.contractProductView.contractId INNER JOIN
                      dbo.productRedemptionInterval ON dbo.contractProductView.productId = dbo.productRedemptionInterval.productId
WHERE     (dbo.productRedemptionInterval.redemptionStatus = ''正在赎回期'')

' 
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_EXEC_INSTANCE]') AND parent_object_id = OBJECT_ID(N'[dbo].[JBPM4_EXECUTION]'))
ALTER TABLE [dbo].[JBPM4_EXECUTION]  WITH CHECK ADD  CONSTRAINT [FK_EXEC_INSTANCE] FOREIGN KEY([INSTANCE_])
REFERENCES [dbo].[JBPM4_EXECUTION] ([DBID_])
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_EXEC_PARENT]') AND parent_object_id = OBJECT_ID(N'[dbo].[JBPM4_EXECUTION]'))
ALTER TABLE [dbo].[JBPM4_EXECUTION]  WITH CHECK ADD  CONSTRAINT [FK_EXEC_PARENT] FOREIGN KEY([PARENT_])
REFERENCES [dbo].[JBPM4_EXECUTION] ([DBID_])
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_EXEC_SUBPI]') AND parent_object_id = OBJECT_ID(N'[dbo].[JBPM4_EXECUTION]'))
ALTER TABLE [dbo].[JBPM4_EXECUTION]  WITH CHECK ADD  CONSTRAINT [FK_EXEC_SUBPI] FOREIGN KEY([SUBPROCINST_])
REFERENCES [dbo].[JBPM4_EXECUTION] ([DBID_])
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_EXEC_SUPEREXEC]') AND parent_object_id = OBJECT_ID(N'[dbo].[JBPM4_EXECUTION]'))
ALTER TABLE [dbo].[JBPM4_EXECUTION]  WITH CHECK ADD  CONSTRAINT [FK_EXEC_SUPEREXEC] FOREIGN KEY([SUPEREXEC_])
REFERENCES [dbo].[JBPM4_EXECUTION] ([DBID_])
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_HSUPERT_SUB]') AND parent_object_id = OBJECT_ID(N'[dbo].[JBPM4_HIST_TASK]'))
ALTER TABLE [dbo].[JBPM4_HIST_TASK]  WITH CHECK ADD  CONSTRAINT [FK_HSUPERT_SUB] FOREIGN KEY([SUPERTASK_])
REFERENCES [dbo].[JBPM4_HIST_TASK] ([DBID_])
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_GROUP_PARENT]') AND parent_object_id = OBJECT_ID(N'[dbo].[JBPM4_ID_GROUP]'))
ALTER TABLE [dbo].[JBPM4_ID_GROUP]  WITH CHECK ADD  CONSTRAINT [FK_GROUP_PARENT] FOREIGN KEY([PARENT_])
REFERENCES [dbo].[JBPM4_ID_GROUP] ([DBID_])
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_LOB_DEPLOYMENT]') AND parent_object_id = OBJECT_ID(N'[dbo].[JBPM4_LOB]'))
ALTER TABLE [dbo].[JBPM4_LOB]  WITH CHECK ADD  CONSTRAINT [FK_LOB_DEPLOYMENT] FOREIGN KEY([DEPLOYMENT_])
REFERENCES [dbo].[JBPM4_DEPLOYMENT] ([DBID_])
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_DEPLPROP_DEPL]') AND parent_object_id = OBJECT_ID(N'[dbo].[JBPM4_DEPLOYPROP]'))
ALTER TABLE [dbo].[JBPM4_DEPLOYPROP]  WITH CHECK ADD  CONSTRAINT [FK_DEPLPROP_DEPL] FOREIGN KEY([DEPLOYMENT_])
REFERENCES [dbo].[JBPM4_DEPLOYMENT] ([DBID_])
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_SWIMLANE_EXEC]') AND parent_object_id = OBJECT_ID(N'[dbo].[JBPM4_SWIMLANE]'))
ALTER TABLE [dbo].[JBPM4_SWIMLANE]  WITH CHECK ADD  CONSTRAINT [FK_SWIMLANE_EXEC] FOREIGN KEY([EXECUTION_])
REFERENCES [dbo].[JBPM4_EXECUTION] ([DBID_])
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_VAR_EXECUTION]') AND parent_object_id = OBJECT_ID(N'[dbo].[JBPM4_VARIABLE]'))
ALTER TABLE [dbo].[JBPM4_VARIABLE]  WITH CHECK ADD  CONSTRAINT [FK_VAR_EXECUTION] FOREIGN KEY([EXECUTION_])
REFERENCES [dbo].[JBPM4_EXECUTION] ([DBID_])
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_VAR_EXESYS]') AND parent_object_id = OBJECT_ID(N'[dbo].[JBPM4_VARIABLE]'))
ALTER TABLE [dbo].[JBPM4_VARIABLE]  WITH CHECK ADD  CONSTRAINT [FK_VAR_EXESYS] FOREIGN KEY([EXESYS_])
REFERENCES [dbo].[JBPM4_EXECUTION] ([DBID_])
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_VAR_LOB]') AND parent_object_id = OBJECT_ID(N'[dbo].[JBPM4_VARIABLE]'))
ALTER TABLE [dbo].[JBPM4_VARIABLE]  WITH CHECK ADD  CONSTRAINT [FK_VAR_LOB] FOREIGN KEY([LOB_])
REFERENCES [dbo].[JBPM4_LOB] ([DBID_])
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_VAR_TASK]') AND parent_object_id = OBJECT_ID(N'[dbo].[JBPM4_VARIABLE]'))
ALTER TABLE [dbo].[JBPM4_VARIABLE]  WITH CHECK ADD  CONSTRAINT [FK_VAR_TASK] FOREIGN KEY([TASK_])
REFERENCES [dbo].[JBPM4_TASK] ([DBID_])
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_HDETAIL_HACTI]') AND parent_object_id = OBJECT_ID(N'[dbo].[JBPM4_HIST_DETAIL]'))
ALTER TABLE [dbo].[JBPM4_HIST_DETAIL]  WITH CHECK ADD  CONSTRAINT [FK_HDETAIL_HACTI] FOREIGN KEY([HACTI_])
REFERENCES [dbo].[JBPM4_HIST_ACTINST] ([DBID_])
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_HDETAIL_HPROCI]') AND parent_object_id = OBJECT_ID(N'[dbo].[JBPM4_HIST_DETAIL]'))
ALTER TABLE [dbo].[JBPM4_HIST_DETAIL]  WITH CHECK ADD  CONSTRAINT [FK_HDETAIL_HPROCI] FOREIGN KEY([HPROCI_])
REFERENCES [dbo].[JBPM4_HIST_PROCINST] ([DBID_])
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_HDETAIL_HTASK]') AND parent_object_id = OBJECT_ID(N'[dbo].[JBPM4_HIST_DETAIL]'))
ALTER TABLE [dbo].[JBPM4_HIST_DETAIL]  WITH CHECK ADD  CONSTRAINT [FK_HDETAIL_HTASK] FOREIGN KEY([HTASK_])
REFERENCES [dbo].[JBPM4_HIST_TASK] ([DBID_])
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_HDETAIL_HVAR]') AND parent_object_id = OBJECT_ID(N'[dbo].[JBPM4_HIST_DETAIL]'))
ALTER TABLE [dbo].[JBPM4_HIST_DETAIL]  WITH CHECK ADD  CONSTRAINT [FK_HDETAIL_HVAR] FOREIGN KEY([HVAR_])
REFERENCES [dbo].[JBPM4_HIST_VAR] ([DBID_])
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_HVAR_HPROCI]') AND parent_object_id = OBJECT_ID(N'[dbo].[JBPM4_HIST_VAR]'))
ALTER TABLE [dbo].[JBPM4_HIST_VAR]  WITH CHECK ADD  CONSTRAINT [FK_HVAR_HPROCI] FOREIGN KEY([HPROCI_])
REFERENCES [dbo].[JBPM4_HIST_PROCINST] ([DBID_])
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_HVAR_HTASK]') AND parent_object_id = OBJECT_ID(N'[dbo].[JBPM4_HIST_VAR]'))
ALTER TABLE [dbo].[JBPM4_HIST_VAR]  WITH CHECK ADD  CONSTRAINT [FK_HVAR_HTASK] FOREIGN KEY([HTASK_])
REFERENCES [dbo].[JBPM4_HIST_TASK] ([DBID_])
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_HACTI_HPROCI]') AND parent_object_id = OBJECT_ID(N'[dbo].[JBPM4_HIST_ACTINST]'))
ALTER TABLE [dbo].[JBPM4_HIST_ACTINST]  WITH CHECK ADD  CONSTRAINT [FK_HACTI_HPROCI] FOREIGN KEY([HPROCI_])
REFERENCES [dbo].[JBPM4_HIST_PROCINST] ([DBID_])
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_HTI_HTASK]') AND parent_object_id = OBJECT_ID(N'[dbo].[JBPM4_HIST_ACTINST]'))
ALTER TABLE [dbo].[JBPM4_HIST_ACTINST]  WITH CHECK ADD  CONSTRAINT [FK_HTI_HTASK] FOREIGN KEY([HTASK_])
REFERENCES [dbo].[JBPM4_HIST_TASK] ([DBID_])
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_MEM_GROUP]') AND parent_object_id = OBJECT_ID(N'[dbo].[JBPM4_ID_MEMBERSHIP]'))
ALTER TABLE [dbo].[JBPM4_ID_MEMBERSHIP]  WITH CHECK ADD  CONSTRAINT [FK_MEM_GROUP] FOREIGN KEY([GROUP_])
REFERENCES [dbo].[JBPM4_ID_GROUP] ([DBID_])
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_MEM_USER]') AND parent_object_id = OBJECT_ID(N'[dbo].[JBPM4_ID_MEMBERSHIP]'))
ALTER TABLE [dbo].[JBPM4_ID_MEMBERSHIP]  WITH CHECK ADD  CONSTRAINT [FK_MEM_USER] FOREIGN KEY([USER_])
REFERENCES [dbo].[JBPM4_ID_USER] ([DBID_])
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_JOB_CFG]') AND parent_object_id = OBJECT_ID(N'[dbo].[JBPM4_JOB]'))
ALTER TABLE [dbo].[JBPM4_JOB]  WITH CHECK ADD  CONSTRAINT [FK_JOB_CFG] FOREIGN KEY([CFG_])
REFERENCES [dbo].[JBPM4_LOB] ([DBID_])
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_TASK_SUPERTASK]') AND parent_object_id = OBJECT_ID(N'[dbo].[JBPM4_TASK]'))
ALTER TABLE [dbo].[JBPM4_TASK]  WITH CHECK ADD  CONSTRAINT [FK_TASK_SUPERTASK] FOREIGN KEY([SUPERTASK_])
REFERENCES [dbo].[JBPM4_TASK] ([DBID_])
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_TASK_SWIML]') AND parent_object_id = OBJECT_ID(N'[dbo].[JBPM4_TASK]'))
ALTER TABLE [dbo].[JBPM4_TASK]  WITH CHECK ADD  CONSTRAINT [FK_TASK_SWIML] FOREIGN KEY([SWIMLANE_])
REFERENCES [dbo].[JBPM4_SWIMLANE] ([DBID_])
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_PART_SWIMLANE]') AND parent_object_id = OBJECT_ID(N'[dbo].[JBPM4_PARTICIPATION]'))
ALTER TABLE [dbo].[JBPM4_PARTICIPATION]  WITH CHECK ADD  CONSTRAINT [FK_PART_SWIMLANE] FOREIGN KEY([SWIMLANE_])
REFERENCES [dbo].[JBPM4_SWIMLANE] ([DBID_])
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_PART_TASK]') AND parent_object_id = OBJECT_ID(N'[dbo].[JBPM4_PARTICIPATION]'))
ALTER TABLE [dbo].[JBPM4_PARTICIPATION]  WITH CHECK ADD  CONSTRAINT [FK_PART_TASK] FOREIGN KEY([TASK_])
REFERENCES [dbo].[JBPM4_TASK] ([DBID_])
