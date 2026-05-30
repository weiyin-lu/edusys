package online.weiyin.edusys.controller.serviceManage;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.date.DateUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.util.StringUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import online.weiyin.edusys.common.Code;
import online.weiyin.edusys.common.Result;
import online.weiyin.edusys.entity.dto.request.StudentSearchServiceBasicDTO;
import online.weiyin.edusys.entity.dto.request.StudentSearchCountBasicDTO;
import online.weiyin.edusys.entity.dto.request.StudentUpdateCountAdminDTO;
import online.weiyin.edusys.entity.dto.response.StudentCountListBO;
import online.weiyin.edusys.entity.dto.response.StudentListCountVO;
import online.weiyin.edusys.entity.dto.request.StudentUpdateCountBasicDTO;
import online.weiyin.edusys.entity.dto.response.StudentListCountDateVO;
import online.weiyin.edusys.service.SourceService;
import online.weiyin.edusys.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import static com.mybatisflex.core.query.QueryMethods.column;
import static online.weiyin.edusys.entity.table.table.DepartmentTableDef.DEPARTMENT;

/**
 * @author 卢子昂
 * @version 1.0.0
 * @classname 业务管理：学生管理（考勤业务相关）
 * @description TODO
 */
@Tag(name = "业务管理：学生管理（考勤业务相关）")
@SaCheckLogin
@RestController
@RequestMapping("/api/sources/counts")
public class SourceCountController {

    @Autowired
    private SourceService sourceService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Operation(summary = "登记出勤/缺勤学生（区分上午、下午）", description = "[source.count.client]根据学生索引id记录出勤学生，上下午区分标准为中午12:00")
    @ApiResponse(responseCode = "data", description = "登记完成")
    @SaCheckPermission("source.count.client")
    @PutMapping("/setCount")
    public Result setCount(@RequestBody StudentUpdateCountBasicDTO dto) {
        // 数据源非空检查
        if (dto.getYear().isEmpty()) {
            return Result.failed("数据源无效", Code.NOT_FOUND);
        }
        // 组装键名
        String key = RedisUtil.getKey(dto.getYear());
        // 偏移量（默认上午）
        Long offset = 0L;
        // 设置一个含时区的时间对象
        Calendar timeSet = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        // 不偏移 上午出勤；10000+ 下午出勤
        if (DateUtil.date(timeSet).isPM()) {
            offset = 10000L;
        }
        // 录入
        for (Long indexId : dto.getIndexId()) {
            redisTemplate.opsForValue().setBit(key, indexId + offset, dto.getStatus());
        }
        return Result.success("考勤记录完成");
    }

    @Operation(summary = "获得指定连队的学生考勤信息", description = "[source.count.client]根据组织id获得学生信息，上下午区分标准为中午12:00")
    @ApiResponse(responseCode = "data", description = "无")
    @SaCheckPermission("source.count.client")
    @PostMapping("/getCountListByDept")
    public Result getCountListByDept(@RequestBody StudentSearchServiceBasicDTO dto) {
//        请求内容非空检查
        if (dto.getYear() == null) {
            return Result.failed("数据源无效", Code.NOT_FOUND);
        } else if (dto.getDeptId() == null) {
            return Result.failed("无可用组织", Code.NOT_FOUND);
        }
        // 组装键名
        String key = RedisUtil.getKey(dto.getYear());
//        构造查询条件
        QueryWrapper wrapper = QueryWrapper.create()
                .from(dto.getYear())
                .where(column("dept_id").like(dto.getDeptId()));
//        查询学生信息
        List<StudentListCountVO> list = sourceService.listAs(wrapper, StudentListCountVO.class);
//        解析redis数据获取当日上午和下午的考勤状态
        for (int i = 0; i < list.size(); i++) {
            // 解析上午信息
            Boolean amNormal = redisTemplate.opsForValue().getBit(key, list.get(i).getIndexId());
            Boolean amLeave = redisTemplate.opsForValue().getBit(key, list.get(i).getIndexId() + 20000);
            if (amLeave) {
                list.get(i).setAmStatus("请假");
            } else if (amNormal) {
                list.get(i).setAmStatus("出勤");
            } else {
                list.get(i).setAmStatus("缺勤");
            }
            // 解析下午信息
            Boolean pmNormal = redisTemplate.opsForValue().getBit(key, list.get(i).getIndexId() + 10000);
            Boolean pmLeave = redisTemplate.opsForValue().getBit(key, list.get(i).getIndexId() + 30000);
            if (pmLeave) {
                list.get(i).setPmStatus("请假");
            } else if (pmNormal) {
                list.get(i).setPmStatus("出勤");
            } else {
                list.get(i).setPmStatus("缺勤");
            }
        }
        return Result.success("连队信息获取成功", list);
    }

    @Operation(summary = "查询考勤情况",
            description = "[source.count.show]查询某一时间范围内特定条件学生的考勤情况")
    @ApiResponse(responseCode = "data", description = "学生信息列表")
    @SaCheckPermission("source.count.show")
    @PostMapping("/getCountListDetail")
    public Result getCountListDetail(@RequestBody StudentSearchCountBasicDTO dto) {
        // 关系数据库查询部分
        Page<StudentListCountDateVO> page = new Page<>(dto.getPageNumber(), dto.getPageSize());
        QueryWrapper query = QueryWrapper.create()
                .from(dto.getYear()).as("student")
                .where(column("academy").like(dto.getAcademy(), StringUtil::isNotBlank))
                .where(column("clazz").like(dto.getClazz(), StringUtil::isNotBlank))
                .where(column("name").like(dto.getName(), StringUtil::isNotBlank))
                .where(column("sexual").like(dto.getSexual(), StringUtil::isNotBlank))
                .where(column("id").eq(dto.getId(), StringUtil::isNotBlank))
                .where(column("dept_id").like(dto.getDeptId(), StringUtil::isNotBlank))
                .leftJoin(DEPARTMENT).on(DEPARTMENT.DEPT_CODE.eq(column("student.dept_id")));
        Page<StudentListCountDateVO> sourcePage = sourceService.pageAs(page, query, StudentListCountDateVO.class);
        // 非关系数据库查询部分
        // 按照一般情况，第一个时间是开始时间，第二个是结束时间
        LocalDate startDate = dto.getSearchDate().get(0);
        LocalDate endDate = dto.getSearchDate().get(1);
        // 遍历每日产生的条目，组装考勤数据
        // 先循环数据对象降低调用不同对象的次数，提高一些效率
        for (int i = 0; i < sourcePage.getRecords().size(); i++) {
            //待传入的考勤信息集合
            List<StudentCountListBO> countList = new ArrayList<>();
            // 根据传入的时间遍历每一天
            for (LocalDate nowDate = startDate;
                 nowDate.isBefore(endDate) || nowDate.isEqual(endDate);
                 nowDate = nowDate.plusDays(1)) {
                // 获取当天键名
                String nowKey = RedisUtil.getKey(dto.getYear(), nowDate);
                // 解析上午信息
                Boolean amNormal = redisTemplate.opsForValue()
                        .getBit(nowKey, sourcePage.getRecords().get(i).getIndexId());
                Boolean amLeave = redisTemplate.opsForValue()
                        .getBit(nowKey, sourcePage.getRecords().get(i).getIndexId() + 20000);
                // 解析下午信息
                Boolean pmNormal = redisTemplate.opsForValue()
                        .getBit(nowKey, sourcePage.getRecords().get(i).getIndexId() + 10000);
                Boolean pmLeave = redisTemplate.opsForValue()
                        .getBit(nowKey, sourcePage.getRecords().get(i).getIndexId() + 30000);
                // 创建单日待装填对象
                StudentCountListBO studentCountListBO = new StudentCountListBO();
                studentCountListBO.setDate(nowDate);
                // 装填上午信息
                if (amLeave) {
                    studentCountListBO.setAmStatus("请假");
                } else if (amNormal) {
                    studentCountListBO.setAmStatus("出勤");
                } else {
                    studentCountListBO.setAmStatus("缺勤");
                }
                // 装填下午信息
                if (pmLeave) {
                    studentCountListBO.setPmStatus("请假");
                } else if (pmNormal) {
                    studentCountListBO.setPmStatus("出勤");
                } else {
                    studentCountListBO.setPmStatus("缺勤");
                }
                // 把单日考勤信息压入考勤信息列表
                countList.add(studentCountListBO);
            }
            // 装填每一个条目的考勤信息列表
            sourcePage.getRecords().get(i).setCountStatus(countList);
        }

        // 响应
        return Result.success("查询成功", sourcePage);

    }

    @Operation(summary = "变更考勤状态",
            description = "[source.count.status]为选中学生批量变更特定日期和时间段的考勤状态")
    @ApiResponse(responseCode = "data", description = "无")
    @SaCheckPermission("source.count.status")
    @PostMapping("/updateStudentCount")
    public Result updateStudentCount(@RequestBody StudentUpdateCountAdminDTO dto) {
        // 组装键名
        String key = RedisUtil.getKey(dto.getYear(), dto.getDate());

        // 考勤默认偏移量（默认上午）
        Long normalOffset = 0L;
        // 请假默认偏移量（默认上午）
        Long leaveOffset = 20000L;
        // 检查如果是下午修改偏移量
        if("1".equals(dto.getAmOrPm())) {
            normalOffset = 10000L;
            leaveOffset = 30000L;
        }

        // 根据传入的考勤状态录入
        if("0".equals(dto.getNewCountStatus())) {
            // 缺勤
            for (Long indexId : dto.getIndexId()) {
                redisTemplate.opsForValue().setBit(key, indexId + normalOffset, false);
            }
        } else if ("1".equals(dto.getNewCountStatus())) {
            // 出勤
            for (Long indexId : dto.getIndexId()) {
                redisTemplate.opsForValue().setBit(key, indexId + normalOffset, true);
            }
        } else if("2".equals(dto.getNewCountStatus())) {
            // 请假
            for (Long indexId : dto.getIndexId()) {
                redisTemplate.opsForValue().setBit(key, indexId + leaveOffset, true);
            }
        }

        return Result.success("考勤变更完成");
    }
}
