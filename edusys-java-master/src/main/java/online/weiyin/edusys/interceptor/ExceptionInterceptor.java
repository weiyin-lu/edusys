package online.weiyin.edusys.interceptor;

import cn.dev33.satoken.exception.SaTokenException;
import online.weiyin.edusys.common.Code;
import online.weiyin.edusys.common.Result;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @ClassName ExceptionInterceptor
 * @Description 全局异常拦截
 * @Version 1.0.0
 * @Time 2023/11/05 下午 07:27
 * @Author 卢子昂
 */
@RestControllerAdvice
public class ExceptionInterceptor {

    /**
     * 鉴权相关异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(SaTokenException.class)
    public Result authorizeHandler(SaTokenException e) {
        return Result.failed(e.getMessage(), Code.AUTHORIZE_ERROR);
    }

    /**
     * 业务操作异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public Result authorizeHandler(RuntimeException e) {
        if (e instanceof DuplicateKeyException) {
            e.printStackTrace();
            return Result.failed("信息/账号已经存在", Code.INSERT_ERROR);
        } else if (e instanceof IllegalArgumentException) {
            e.printStackTrace();
            return Result.failed("无效的数据源", Code.SERVER_ERROR);
        } else if (e instanceof NullPointerException) {
            e.printStackTrace();
            return Result.failed("无效条件", Code.INPUT_ERROR);
        } else {
            e.printStackTrace();
            return Result.failed(e.getMessage(), Code.SERVER_ERROR);
        }
    }

    /**
     * 通用异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Result defaultHandler(Exception e) {
        e.printStackTrace();
        return Result.failed("请联系系统维护人员查看问题", Code.SERVER_ERROR);
    }
}
