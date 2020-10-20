package com.inet.codebase.controller.demonstrate;

import com.inet.codebase.service.BlogService;
import com.inet.codebase.utils.ArchivesUtils;
import com.inet.codebase.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 归档模块
 * @author HCY
 * @since 2020-10-04
 */

@RestController
@RequestMapping("/pigeonhole")
@CrossOrigin
@Api(value = "用户模块",tags = {"归档的用户接口"},description = "用户模块")
public class Pigeonhole {
    @Resource
    private BlogService blogService;

    /**
     * 归档请求,查看写博客得日期和博客数目
     * 格式 XXXX年XX月 XX条
     * @author HCY
     * @since 2020-10-04
     * @return Result风格的对象
     */
    @ApiOperation("归档请求,查看写博客得日期和博客数目")
    @GetMapping("/list")
    public Result GetList(){
        Map<String, Object> map = blogService.CheckTheArchive();
        return new Result(map , "归档请求" , 100);
    }
}
