package online.weiyin.edusys.controller.serviceManage;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import online.weiyin.edusys.common.Result;
import online.weiyin.edusys.entity.dto.request.StudentSearchCountAuditBO;
import online.weiyin.edusys.entity.dto.request.StudentSearchCountAuditDTO;
import online.weiyin.edusys.entity.dto.request.StudentSearchCountFileBO;
import online.weiyin.edusys.entity.view.CountAuditChildView;
import online.weiyin.edusys.entity.view.CountAuditView;
import online.weiyin.edusys.service.SourceAuditService;
import online.weiyin.edusys.service.SourceService;
import online.weiyin.edusys.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mybatisflex.core.query.QueryMethods.column;
import static online.weiyin.edusys.entity.table.table.DepartmentTableDef.DEPARTMENT;

/**
 * @author 卢子昂
 * @version 1.0.0
 * @classname AuditCountController
 * @description 业务管理：考勤业务指标
 */
@Tag(name = "业务管理：考勤业务指标")
@RestController
@SaCheckLogin
@RequestMapping("/api/audit/count")
public class AuditCountController {

    @Autowired
    private SourceService sourceService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SourceAuditService sourceAuditService;

    @Operation(summary = "获取军训考勤指标", description = "[audit.count.show]获取考勤数据指标")
    @ApiResponse(responseCode = "data", description = "指标核算成功")
    @SaCheckPermission("audit.count.show")
    @PostMapping("/getCountAuditAtDept")
    public Result getCountAuditAtDept(@RequestBody StudentSearchCountAuditDTO dto) {
        // 准备必要对象
        // 构造一个视图响应对象
        CountAuditView view = new CountAuditView(0, 0, 0, 0,
                null, null, null);
        // 查询数据源表里的所有indexId、deptName、clazz，构造一个列表
        List<StudentSearchCountAuditBO> studentList = sourceService.queryChain()
                .select("student.index_id as index_id, " +
                        "student.clazz as clazz, " +
                        "student.academy as academy, " +
                        "case when student.dept_id is not null then dept_name else '未分配' end as dept_name")
                .from(dto.getYear()).as("student")
                .leftJoin(DEPARTMENT)
                .on(DEPARTMENT.DEPT_CODE.eq(column("student.dept_id")))
                .listAs(StudentSearchCountAuditBO.class);
        // 构造键名
        String key = RedisUtil.getKey(dto.getYear(), dto.getDate());
        // 构造临时的指标明细对象
        Map<String, CountAuditChildView> deptView = new HashMap<>();
        Map<String, CountAuditChildView> classView = new HashMap<>();
        Map<String, CountAuditChildView> academyView = new HashMap<>();
        // 保存时间段偏移量（默认是上午）
        Long normalOffset = 0L;
        Long leaveOffset = 20000L;
        if ("1".equals(dto.getAmOrPm())) {
            normalOffset = 10000L;
            leaveOffset = 30000L;
        }
        // 开始处理数据
        // 遍历每个学生在redis的存储状态，分别存储到不同的值里
        for (StudentSearchCountAuditBO bo : studentList) {
            // 获取指定偏移量下记录的信息
            Boolean normal = redisTemplate.opsForValue()
                    .getBit(key, bo.getIndexId() + normalOffset);
            Boolean leave = redisTemplate.opsForValue()
                    .getBit(key, bo.getIndexId() + leaveOffset);
            // 检查指标明细对象里是否已经存在当前的键，如果不存在创建一个
            if (!deptView.containsKey(bo.getDeptName())) {
                deptView.put(bo.getDeptName(), new CountAuditChildView(0, 0, 0, 0));
            }
            if (!classView.containsKey(bo.getClazz())) {
                classView.put(bo.getClazz(), new CountAuditChildView(0, 0, 0, 0));
            }
            if (!academyView.containsKey(bo.getAcademy())) {
                academyView.put(bo.getAcademy(), new CountAuditChildView(0, 0, 0, 0));
            }
            // 记录数据，请假>考勤>缺勤
            // 记录总数
            view.setTotal(view.getTotal() + 1);
            // 记录连队指标明细总数
            deptView.get(bo.getDeptName()).setTotal(deptView.get(bo.getDeptName()).getTotal() + 1);
            // 记录班级指标明细总数
            classView.get(bo.getClazz()).setTotal(classView.get(bo.getClazz()).getTotal() + 1);
            // 记录学院指标明细总数
            academyView.get(bo.getAcademy()).setTotal(academyView.get(bo.getAcademy()).getTotal() + 1);
            if (leave) {
                // 记录请假数
                view.setLeaveTotal(view.getLeaveTotal() + 1);
                // 记录连队指标明细请假数
                deptView.get(bo.getDeptName()).setLeaveTotal(deptView.get(bo.getDeptName()).getLeaveTotal() + 1);
                // 记录班级指标明细请假数
                classView.get(bo.getClazz()).setLeaveTotal(classView.get(bo.getClazz()).getLeaveTotal() + 1);
                // 记录学院指标明细请假数
                academyView.get(bo.getAcademy()).setLeaveTotal(academyView.get(bo.getAcademy()).getLeaveTotal() + 1);
            } else if (normal) {
                // 记录考勤数
                view.setCountTotal(view.getCountTotal() + 1);
                // 记录连队指标明细考勤数
                deptView.get(bo.getDeptName()).setCountTotal(deptView.get(bo.getDeptName()).getCountTotal() + 1);
                // 记录班级指标明细考勤数
                classView.get(bo.getClazz()).setCountTotal(classView.get(bo.getClazz()).getCountTotal() + 1);
                // 记录学院指标明细考勤数
                academyView.get(bo.getAcademy()).setCountTotal(academyView.get(bo.getAcademy()).getCountTotal() + 1);
            } else {
                // 记录请假数
                view.setNotCountTotal(view.getNotCountTotal() + 1);
                // 记录连队指标明细请假数
                deptView.get(bo.getDeptName()).setNotCountTotal(deptView.get(bo.getDeptName()).getNotCountTotal() + 1);
                // 记录学院指标明细考勤数
                academyView.get(bo.getAcademy()).setNotCountTotal(academyView.get(bo.getAcademy()).getNotCountTotal() + 1);
            }
        }
        // 把临时指标明细对象装填到视图对象里
        view.setDeptView(deptView);
        view.setClassView(classView);
        view.setAcademyView(academyView);

        return Result.success("指标核算成功", view);
    }

    @Operation(summary = "获取考勤详情文件", description = "[audit.count.download]生成考勤详情文件，包含学生详细信息")
    @ApiResponse(responseCode = "data", description = "指标核算成功")
    @SaCheckPermission("audit.count.download")
    @PostMapping("/getCountAuditFle")
    public Result getCountAuditFile(@RequestBody StudentSearchCountAuditDTO dto) {
        // 构造文件名
        String fileName = dto.getYear() + "-" +
                dto.getDate().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "-" +
                ("0".equals(dto.getAmOrPm()) ? "上午-" : "下午-") +
                "学生军训考勤情况明细" +
                ".xlsx";
        // 构造查询
        List<StudentSearchCountFileBO> fileBO = sourceService.queryChain()
                .select("academy, clazz, id, name, sexual, index_id, " +
                        "case when student.dept_id is not null then dept_name else '未分配' end as dept_name")
                .from(dto.getYear()).as("student")
                .leftJoin(DEPARTMENT)
                .on(DEPARTMENT.DEPT_CODE.eq(column("student.dept_id")))
                .listAs(StudentSearchCountFileBO.class);
        // 构造键名
        String key = RedisUtil.getKey(dto.getYear(), dto.getDate());
        // 保存时间段偏移量（默认是上午）
        Long normalOffset = 0L;
        Long leaveOffset = 20000L;
        if ("1".equals(dto.getAmOrPm())) {
            normalOffset = 10000L;
            leaveOffset = 30000L;
        }
        // 获取所有字段的考勤信息并装填到对象里
        for (int i = 0; i < fileBO.size(); i++) {
            // 获取指定偏移量下记录的信息
            Boolean normal = redisTemplate.opsForValue()
                    .getBit(key, fileBO.get(i).getIndexId() + normalOffset);
            Boolean leave = redisTemplate.opsForValue()
                    .getBit(key, fileBO.get(i).getIndexId() + leaveOffset);
            // 组装时间和时间段
            fileBO.get(i).setDate(dto.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            fileBO.get(i).setAmOrPm("0".equals(dto.getAmOrPm()) ? "上午" : "下午");
            // 组装考勤状态
            if (leave) {
                fileBO.get(i).setStatus("请假");
            } else if (normal) {
                fileBO.get(i).setStatus("出勤");
            } else {
                fileBO.get(i).setStatus("缺勤");
            }
        }
        // 把对象写入到文件
        String filePath = sourceAuditService.writeCountAuditFile(fileBO, fileName, dto.getIsNormal());
        return Result.success("文件导出成功", filePath);
    }
}
