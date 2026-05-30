-- MySQL dump 10.13  Distrib 5.7.36, for Win64 (x86_64)
--
-- Host: localhost    Database: edusys
-- ------------------------------------------------------
-- Server version	8.0.33

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `studentsource_1999`
--

DROP TABLE IF EXISTS `studentsource_1999`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `studentsource_1999` (
  `academy` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '学院',
  `clazz` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '班级',
  `card_id` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '身份证号',
  `name` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '姓名',
  `sexual` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '性别',
  `id` varchar(100) COLLATE utf8mb4_general_ci NOT NULL COMMENT '学号',
  `description` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `check_status` tinyint DEFAULT '0' COMMENT '服装登记状态 0=未登记、1=已登记',
  `height` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '身高',
  `weight` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '体重',
  `phone` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '手机号',
  `shoe_size` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '鞋码',
  `receive_status` tinyint NOT NULL DEFAULT '0' COMMENT '服装领取状态 0=未领取 1=已领取',
  `index_id` int NOT NULL AUTO_INCREMENT COMMENT '数据索引ID',
  `dept_id` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '所在连队（组织机构）',
  PRIMARY KEY (`index_id`)
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `studentsource_1999`
--

LOCK TABLES `studentsource_1999` WRITE;
/*!40000 ALTER TABLE `studentsource_1999` DISABLE KEYS */;
INSERT INTO `studentsource_1999` VALUES
('机械工程学院','车辆22-1','000000200001010001','学生01','男','2024001','北',0,NULL,NULL,NULL,NULL,0,1,'TEST1'),
('机械工程学院','车辆22-3','000000200001010002','学生02','男','2024002','北',0,NULL,NULL,NULL,NULL,0,2,'TEST1'),
('机械工程学院','机自22-3','000000200001010003','学生03','男','2024003','北',0,NULL,NULL,NULL,NULL,0,3,'TEST1'),
('土木工程学院','交通22-2','000000200001010004','学生04','男','2024004','北',0,NULL,NULL,NULL,NULL,0,4,'TEST1'),
('机械工程学院','车辆22-1','000000200001010005','学生05','男','2024005','北',0,NULL,NULL,NULL,NULL,0,5,'TEST1'),
('土木工程学院','土木22-8','000000200001020001','学生06','男','2024006','北',0,NULL,NULL,NULL,NULL,0,6,'TEST1'),
('土木工程学院','建筑 22-3','000000200001020002','学生07','女','2024007','北',0,NULL,NULL,NULL,NULL,0,7,'TEST1'),
('应用技术与经济管理学院','财管22-2','000000200001020003','学生08','女','2024008','北',0,NULL,NULL,NULL,NULL,0,8,'TEST1'),
('传媒与艺术学院','广电22-1','000000200001020004','学生09','女','2024009','北',0,NULL,NULL,NULL,NULL,0,9,'TEST1'),
('矿业学院','采矿 22-1','000000200001020005','学生10','男','2024010','南',0,NULL,NULL,NULL,NULL,0,10,'TEST1'),
('矿业学院','资勘22-1','000000200001030001','学生11','男','2024011','南',0,NULL,NULL,NULL,NULL,0,11,'TEST1'),
('理学院','生工22-1','000000200001030002','学生12','男','2024012','南',0,NULL,NULL,NULL,NULL,0,12,'TEST1'),
('外国语学院','英语22-3','000000200001030003','学生13','女','2024013','南',0,NULL,NULL,NULL,NULL,0,13,'TEST1'),
('力学与工程学院','能科23-1','000000200001030004','学生14','男','2024014','南',0,NULL,NULL,NULL,NULL,0,14,'TEST1'),
('公共管理与法学院','法学23-2','000000200001030005','学生15','男','2024015','南',0,NULL,NULL,NULL,NULL,0,15,'TEST1'),
('力学与工程学院','工力23-3','000000200001040001','学生16','男','2024016','南',0,NULL,NULL,NULL,NULL,0,16,'TEST1'),
('矿业学院','智采23-1','000000200001040002','学生17','男','2024017','南',0,NULL,NULL,NULL,NULL,0,17,'TEST1'),
('力学与工程学院','能科23-1','000000200001040003','学生18','男','2024018','南',0,NULL,NULL,NULL,NULL,0,18,'TEST1'),
('矿业学院','采矿23-1','000000200001040004','学生19','男','2024019','南',0,NULL,NULL,NULL,NULL,0,19,'TEST1'),
('力学与工程学院','理力23-1','000000200001040005','学生20','男','2024020','南',0,NULL,NULL,NULL,NULL,0,20,'TEST1');
/*!40000 ALTER TABLE `studentsource_1999` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `studentsource_score_1999`
--

DROP TABLE IF EXISTS `studentsource_score_1999`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `studentsource_score_1999` (
  `id` varchar(100) COLLATE utf8mb4_general_ci NOT NULL COMMENT '学号',
  `group_score` float(10,2) NOT NULL DEFAULT '0.00' COMMENT '队列动作',
  `weapon_score` float(10,2) NOT NULL DEFAULT '0.00' COMMENT '轻武器射击',
  `tactical_score` float(10,2) NOT NULL DEFAULT '0.00' COMMENT '战术动作',
  `fight_score` float(10,2) NOT NULL DEFAULT '0.00' COMMENT '格斗基础',
  `rescue_score` float(10,2) NOT NULL DEFAULT '0.00' COMMENT '战场救护',
  `nuclear_score` float(10,2) NOT NULL DEFAULT '0.00' COMMENT '核生化防护',
  `run_score` float(10,2) NOT NULL DEFAULT '0.00' COMMENT '行军拉练',
  `count_score` float(10,2) NOT NULL DEFAULT '0.00' COMMENT '出勤考核'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `studentsource_score_1999`
--

LOCK TABLES `studentsource_score_1999` WRITE;
/*!40000 ALTER TABLE `studentsource_score_1999` DISABLE KEYS */;
INSERT INTO `studentsource_score_1999` VALUES
('2024001',0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00),
('2024002',0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00),
('2024003',0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00),
('2024004',0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00),
('2024005',0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00),
('2024006',0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00),
('2024007',0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00),
('2024008',0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00),
('2024009',0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00),
('2024010',0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00),
('2024011',0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00),
('2024012',0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00),
('2024013',0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00),
('2024014',0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00),
('2024015',0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00),
('2024016',0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00),
('2024017',0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00),
('2024018',0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00),
('2024019',0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00),
('2024020',0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00);
/*!40000 ALTER TABLE `studentsource_score_1999` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_department`
--

DROP TABLE IF EXISTS `sys_department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_department` (
  `row_key` int NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `dept_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '组织编码',
  `dept_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '组织名称',
  `superior` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '上级组织',
  PRIMARY KEY (`row_key`),
  UNIQUE KEY `ss_department_un` (`dept_code`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='组织机构信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_department`
--

LOCK TABLES `sys_department` WRITE;
/*!40000 ALTER TABLE `sys_department` DISABLE KEYS */;
INSERT INTO `sys_department` VALUES (1,'MAIN','军训指挥部',NULL),(10,'ZXB','助训办','MAIN'),(11,'GH','新国护','MAIN'),(12,'L1','一连','MAIN'),(13,'L1P1','一连一排','L1'),(15,'L1P2','一连二排','L1'),(17,'L1P3','一连三排','L1'),(18,'L1P4','一连四排','L1'),(19,'L1P5','一连五排','L1'),(20,'L1P6','一连六排','L1'),(22,'L1P7','一连七排','L1'),(23,'L1P8','一连八排','L1'),(24,'L1P9','一连九排','L1'),(25,'L1P10','一连十排','L1'),(26,'L2','二连','MAIN'),(27,'L2P1','二连一排','L2'),(28,'L2P2','二连二排','L2'),(29,'L2P3','二连三排','L2'),(30,'L2P4','二连四排','L2'),(31,'L2P5','二连五排','L2'),(32,'L2P6','二连六排','L2'),(33,'L2P7','二连七排','L2'),(34,'L2P8','二连八排','L2'),(35,'L2P9','二连九排','L2'),(36,'L2P10','二连十排','L2'),(37,'TEST1','教官集训-国护','MAIN'),(38,'TEST2','教官集训-军协','MAIN');
/*!40000 ALTER TABLE `sys_department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dictionary`
--

DROP TABLE IF EXISTS `sys_dictionary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_dictionary` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `dic_id` varchar(100) COLLATE utf8mb4_general_ci NOT NULL COMMENT '标准集id',
  `description` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '标准集含义',
  `raw_value` varchar(100) COLLATE utf8mb4_general_ci NOT NULL COMMENT '原始值',
  `parse_value` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '映射值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='字典映射表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dictionary`
--

LOCK TABLES `sys_dictionary` WRITE;
/*!40000 ALTER TABLE `sys_dictionary` DISABLE KEYS */;
INSERT INTO `sys_dictionary` VALUES (1,'shoes','鞋码转换表','34','220'),(2,'shoes','鞋码转换表','35','225'),(3,'shoes','鞋码转换表','36','230'),(4,'shoes','鞋码转换表','37','235'),(5,'shoes','鞋码转换表','38','240'),(6,'shoes','鞋码转换表','39','245'),(7,'shoes','鞋码转换表','40','250'),(8,'shoes','鞋码转换表','41','255'),(9,'shoes','鞋码转换表','42','260'),(10,'shoes','鞋码转换表','43','265'),(11,'shoes','鞋码转换表','44','270'),(12,'shirt','短袖转换表','140','100'),(13,'shirt','短袖转换表','144','105'),(14,'shirt','短袖转换表','148','110'),(15,'shirt','短袖转换表','152','115'),(16,'shirt','短袖转换表','156','120'),(17,'shirt','短袖转换表','160','125'),(18,'shirt','短袖转换表','164','130'),(19,'shirt','短袖转换表','168','135'),(20,'shirt','短袖转换表','172','140'),(21,'shirt','短袖转换表','176','145'),(22,'shirt','短袖转换表','180','150'),(23,'shirt','短袖转换表','184','155'),(24,'shirt','短袖转换表','188','160'),(25,'shirt','短袖转换表','190','特号'),(26,'cloth','外套转换表','150','1号'),(27,'cloth','外套转换表','160','2号'),(28,'cloth','外套转换表','165','3号'),(29,'cloth','外套转换表','170','4号'),(30,'cloth','外套转换表','175','5号'),(31,'cloth','外套转换表','180','6号'),(32,'cloth','外套转换表','185','7号'),(33,'cloth','外套转换表','188','特号'),(36,'score','队列动作','groupScore','0'),(37,'score','轻武器射击','weaponScore','0'),(38,'score','战术动作','tacticalScore','0'),(39,'score','格斗基础','fightScore','0'),(40,'score','战场救护','rescueScore','0'),(41,'score','核生化防护','nuclearScore','0'),(42,'score','行军拉练','runScore','0');
/*!40000 ALTER TABLE `sys_dictionary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_menu` (
  `row_key` int NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `menu_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单id',
  `url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单路径',
  `component_path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '组件路径',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '菜单描述',
  `is_delete` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除标识',
  PRIMARY KEY (`row_key`),
  UNIQUE KEY `ss_menu_un` (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='菜单信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu`
--

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` VALUES (1,'all','/index/**','*.vue','所有菜单权限','0'),(2,'system-deptConfig','/index/system-deptConfig','DeptConfig.vue','系统配置管理-部门信息配置','0'),(3,'system-roleConfig','/index/system-roleConfig','RoleConfig.vue','系统配置管理-角色信息配置','0'),(4,'system-menuConfig','/index/system-menuConfig','MenuConfig.vue','系统配置管理-菜单信息配置','0'),(5,'system-permissionConfig','/index/system-permissionConfig','PermissionConfig.vue','系统配置管理-权限信息配置','0'),(6,'humanManage-worker','/index/humanManage-worker','Worker.vue','人员管理-工作人员信息管理','0'),(7,'humanManage-student','/index/humanManage-student','Student.vue','人员管理-学生信息管理','0'),(8,'serviceCloth-cloth','/index/service-cloth','Cloth.vue','服装业务管理-服装分发管理','0'),(9,'serviceCloth-clothAudit','/index/service-clothAudit','ClothAudit.vue','服装业务管理-服装领取指标大屏','0'),(10,'serviceCloth-dictionary','/index/service-dictionary','Dictionary.vue','服装业务管理-字典管理','0'),(11,'serviceTrain-distribute','/index/serviceTrain-distribute','Distribute.vue','军训业务管理-人员分配管理','0'),(12,'serviceTrain-file','/index/serviceTrain-file','File.vue','军训业务管理-指导文件公示','0'),(13,'serviceTrain-count','/index/serviceTrain-count','Count.vue','军训业务管理-考勤综合管理','0'),(14,'serviceTrain-score','/index/serviceTrain-score','Score.vue','军训业务管理-军训成绩核算','0'),(15,'serviceTrain-countAudit','/index/serviceTrain-countAudit','CountAudit.vue','军训业务管理-军训考勤指标大屏','0');
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_permission`
--

DROP TABLE IF EXISTS `sys_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_permission` (
  `row_key` int NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `permission_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限id',
  `permission_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限描述',
  `is_delete` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除标识',
  PRIMARY KEY (`row_key`),
  UNIQUE KEY `permission_un` (`permission_id`)
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='权限信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_permission`
--

LOCK TABLES `sys_permission` WRITE;
/*!40000 ALTER TABLE `sys_permission` DISABLE KEYS */;
INSERT INTO `sys_permission` VALUES (1,'*','[慎重使用]系统所有权限','0'),(2,'admin.dept.*','系统：组织信息配置全部功能','0'),(3,'admin.dept.add','系统：组织信息配置-增','0'),(4,'admin.dept.remove','系统：组织信息配置-删','0'),(5,'admin.dept.show','系统：组织信息配置-查','0'),(6,'admin.dept.update','系统：组织信息配置-改','0'),(7,'admin.menu.*','系统：菜单信息配置全部功能','0'),(8,'admin.menu.add','系统：菜单信息配置-增','0'),(9,'admin.menu.remove','系统：菜单信息配置-删','0'),(10,'admin.menu.show','系统：菜单信息配置-查','0'),(11,'admin.menu.update','系统：菜单信息配置-改','0'),(12,'admin.permission.*','系统：权限信息配置全部功能','0'),(13,'admin.permission.add','系统：权限信息配置-增','0'),(14,'admin.permission.show','系统：权限信息配置-查','0'),(15,'admin.relevance.menu.*','系统边界：配置角色菜单全部功能','0'),(16,'admin.relevance.menu.add','系统边界：赋予角色菜单使用权','0'),(17,'admin.relevance.menu.remove','系统边界：移除角色的菜单使用权','0'),(18,'admin.relevance.menu.show','系统边界：查看角色现在可用的菜单','0'),(19,'admin.relevance.permission.*','系统边界：配置角色权限全部功能','0'),(20,'admin.relevance.permission.add','系统边界：赋予角色特定权限','0'),(21,'admin.relevance.permission.remove','系统边界：移除角色特定权限','0'),(22,'admin.relevance.permission.show','系统边界：查看角色现在拥有的权限','0'),(23,'admin.role.*','系统：角色信息配置全部功能','0'),(24,'admin.role.add','系统：角色信息配置-增','0'),(25,'admin.role.remove','系统：角色信息配置-删','0'),(26,'admin.role.show','系统：角色信息配置-查','0'),(27,'admin.role.update','系统：角色信息配置-改','0'),(53,'source.basic.upload','数据源录入','0'),(54,'source.basic.show','数据源相关基本信息检索','0'),(55,'source.basic.change','数据源学生增/删','0'),(56,'source.cloths.status','服装业务：变更领取状态','0'),(57,'source.count.client','考勤业务：教官客户端操作','0'),(58,'source.count.show','考勤业务：考勤相关信息检索','0'),(59,'source.count.status','考勤业务：变更考勤状态','0'),(62,'source.score.client','成绩业务：教官客户端操作','0'),(63,'source.score.show','成绩业务：成绩相关信息检索','0'),(64,'source.score.change','成绩业务：变更考核项目','0'),(65,'source.score.update','成绩业务：修改学生成绩','0'),(66,'source.score.download','成绩业务：下载成绩详情文件','0'),(67,'source.cloths.dictionary','服装业务：转换字典管理','0'),(68,'distribute.student','分配业务：学生分配','0'),(69,'distribute.user','分配业务：教官分配','0'),(70,'file.upload','指导文件上传','0'),(71,'audit.count.show','指标：军训考勤指标查看','0'),(72,'audit.count.download','指标：军训考勤指标下载','0'),(73,'audit.cloths.show','指标：服装分发指标查看','0'),(74,'user.basic.upload','工作人员账号录入','0'),(75,'user.basic.show','工作人员基本信息检索','0'),(76,'user.basic.change','工作人员基础信息删/改','0'),(77,'user.info.update','工作人员个人信息修改','0');
/*!40000 ALTER TABLE `sys_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role` (
  `row_key` int NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `role_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色id',
  `role_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色描述',
  `is_delete` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除标识',
  PRIMARY KEY (`row_key`),
  UNIQUE KEY `role_un` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='角色信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES (1,'administrator','超级管理员','0'),(12,'ClothManager','服装分发负责人','0'),(13,'NormalManager','教官','0'),(14,'SpecialManager','特殊教官','0'),(16,'Manager','领队','0'),(17,'Admin','系统管理员','0');
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_menu_relevance`
--

DROP TABLE IF EXISTS `sys_role_menu_relevance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role_menu_relevance` (
  `row_key` int NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `role_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色id',
  `menu_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单id',
  PRIMARY KEY (`row_key`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='角色-菜单关联关系';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_menu_relevance`
--

LOCK TABLES `sys_role_menu_relevance` WRITE;
/*!40000 ALTER TABLE `sys_role_menu_relevance` DISABLE KEYS */;
INSERT INTO `sys_role_menu_relevance` VALUES (16,'administrator','all'),(17,'Admin','system-roleConfig'),(18,'Admin','humanManage-worker'),(19,'Admin','humanManage-student'),(20,'Admin','serviceCloth-cloth'),(21,'Admin','serviceCloth-clothAudit'),(22,'Admin','serviceCloth-dictionary'),(23,'Admin','serviceTrain-distribute'),(24,'Admin','serviceTrain-file'),(25,'Admin','serviceTrain-count'),(26,'Admin','serviceTrain-score'),(27,'Admin','serviceTrain-countAudit'),(28,'NormalManager','serviceTrain-file'),(29,'ClothManager','serviceTrain-file'),(30,'ClothManager','serviceCloth-cloth'),(31,'ClothManager','serviceCloth-clothAudit'),(32,'ClothManager','serviceCloth-dictionary'),(33,'SpecialManager','serviceTrain-distribute'),(34,'SpecialManager','serviceCount-file'),(35,'SpecialManager','serviceTrain-count'),(36,'SpecialManager','serviceTrain-countAudit'),(37,'SpecialManager','serviceTrain-score');
/*!40000 ALTER TABLE `sys_role_menu_relevance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_permission_relevance`
--

DROP TABLE IF EXISTS `sys_role_permission_relevance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role_permission_relevance` (
  `row_key` int NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `role_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色id',
  `permission_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限id',
  PRIMARY KEY (`row_key`)
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='角色-权限关联关系';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_permission_relevance`
--

LOCK TABLES `sys_role_permission_relevance` WRITE;
/*!40000 ALTER TABLE `sys_role_permission_relevance` DISABLE KEYS */;
INSERT INTO `sys_role_permission_relevance` VALUES (1,'administrator','*'),(26,'Admin','admin.relevance.menu.*'),(27,'Admin','admin.relevance.permission.*'),(28,'Admin','admin.role.*'),(29,'Admin','source.basic.upload'),(30,'Admin','source.basic.show'),(31,'Admin','source.basic.change'),(32,'Admin','source.cloths.status'),(33,'Admin','source.count.client'),(34,'Admin','source.count.show'),(35,'Admin','source.count.status'),(36,'Admin','source.score.client'),(37,'Admin','source.score.show'),(38,'Admin','source.score.change'),(39,'Admin','source.score.update'),(40,'Admin','source.score.download'),(41,'Admin','source.score.dictionary'),(42,'Admin','distribute.student'),(43,'Admin','distribute.user'),(44,'Admin','file.upload'),(45,'Admin','audit.count.show'),(46,'Admin','audit.count.download'),(47,'Admin','audit.cloths.show'),(48,'Admin','user.basic.upload'),(49,'Admin','user.basic.show'),(50,'Admin','user.basic.change'),(51,'Admin','user.info.update'),(52,'NormalManager','user.info.update'),(53,'NormalManager','source.count.client'),(54,'NormalManager','source.score.client'),(55,'NormalManager','source.basic.show'),(56,'ClothManager','user.info.update'),(57,'ClothManager','source.cloths.dictionary'),(58,'ClothManager','source.cloths.status'),(59,'ClothManager','source.basic.show'),(60,'ClothManager','audit.cloths.show'),(61,'SpecialManager','source.basic.show'),(62,'SpecialManager','source.count.client'),(63,'SpecialManager','source.count.show'),(64,'SpecialManager','source.count.status'),(65,'SpecialManager','source.score.client'),(66,'SpecialManager','source.score.show'),(67,'SpecialManager','source.score.change'),(68,'SpecialManager','source.score.update'),(69,'SpecialManager','distribute.student'),(70,'SpecialManager','audit.count.show'),(71,'SpecialManager','audit.count.download'),(72,'SpecialManager','audit.cloths.show'),(73,'SpecialManager','user.info.update');
/*!40000 ALTER TABLE `sys_role_permission_relevance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_source_operate_record`
--

DROP TABLE IF EXISTS `sys_source_operate_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_source_operate_record` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `year` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '入学年份，同时为数据源生成表名',
  `operate_user` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建者',
  `operate_dt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `description` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='数据源操作记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_source_operate_record`
--

LOCK TABLES `sys_source_operate_record` WRITE;
/*!40000 ALTER TABLE `sys_source_operate_record` DISABLE KEYS */;
INSERT INTO `sys_source_operate_record` VALUES (80,'1999','admin','2024-05-11 20:02:11','1999');
/*!40000 ALTER TABLE `sys_source_operate_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user` (
  `row_key` int NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `user_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户id',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  PRIMARY KEY (`row_key`),
  UNIQUE KEY `user_un` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES
(1,'admin','[REDACTED]'),
(2,'demo_instructor','[REDACTED]'),
(4,'demo_admin','[REDACTED]'),
(6,'demo_student','[REDACTED]');
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_info`
--

DROP TABLE IF EXISTS `sys_user_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user_info` (
  `row_key` int NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `user_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户id',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '认证用户' COMMENT '姓名',
  `phone` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '联系电话',
  `department` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '组织机构',
  `post` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '岗位及其他',
  `sexual` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '性别',
  `image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '头像图片',
  PRIMARY KEY (`row_key`),
  UNIQUE KEY `ss_userInfo_un` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户个人信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_info`
--

LOCK TABLES `sys_user_info` WRITE;
/*!40000 ALTER TABLE `sys_user_info` DISABLE KEYS */;
INSERT INTO `sys_user_info` VALUES
(1,'admin','系统管理员','13800000000','MAIN','系统管理员','男','/image/default.jpg'),
(2,'demo_instructor','演示教官','13800000001','L1','军训教官','男','/image/default.jpg'),
(16,'demo_admin','演示管理员','13800000002','TEST1','军训教官','男','/image/default.jpg'),
(18,'demo_student','演示学生','13800000003','MAIN','军训教官','男','/image/default.jpg');
/*!40000 ALTER TABLE `sys_user_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_role_relevance`
--

DROP TABLE IF EXISTS `sys_user_role_relevance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user_role_relevance` (
  `row_key` int NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `user_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户id',
  `role_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色id',
  PRIMARY KEY (`row_key`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户-角色关联关系';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_role_relevance`
--

LOCK TABLES `sys_user_role_relevance` WRITE;
/*!40000 ALTER TABLE `sys_user_role_relevance` DISABLE KEYS */;
INSERT INTO `sys_user_role_relevance` VALUES
(1,'admin','administrator'),
(33,'demo_instructor','SpecialManager'),
(34,'demo_admin','SpecialManager');
/*!40000 ALTER TABLE `sys_user_role_relevance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'edusys'
--
/*!50003 DROP FUNCTION IF EXISTS `get_dept_list` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `get_dept_list`(in_id varchar(100)) RETURNS varchar(1000) CHARSET utf8mb4 COLLATE utf8mb4_general_ci
    READS SQL DATA
begin

   -- 定义变量

    declare ids varchar(1000) default '';

    declare tempids varchar(1000);

    set tempids = in_id;

   -- 如果tempids无法再获取到值，就说明递归已经结束

    while tempids is not null do

          -- 每次循环把上一次的查询结果拼接到ids返回值中

           set ids = CONCAT_WS(',',ids,tempids);

          -- 拼接dept_code，条件:记录的superior字段包含在本次查询的tempids中（find_in_set返回不为0），并将结果覆盖到tempids中

           select GROUP_CONCAT(d.dept_code) into tempids from sys_department d where FIND_IN_SET(d.superior ,tempids)>0;

        end while;

   -- 返回逗号分割的dept_code字符串，在外侧传入字符串查询

    return ids;

end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-15 20:57:53
