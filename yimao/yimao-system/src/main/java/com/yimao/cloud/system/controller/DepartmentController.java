package com.yimao.cloud.system.controller;

import com.yimao.cloud.pojo.dto.user.DepartmentDTO;
import com.yimao.cloud.pojo.vo.PageVO;
import com.yimao.cloud.system.feign.UserFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 部门控制器
 *
 * @author Zhang Bo
 * @date 2018/11/7.
 */
@RestController
@Slf4j
@Api(tags = "DepartmentController")
public class DepartmentController {

    @Resource
    private UserFeign userFeign;

    /**
     * 新增部门
     *
     * @param dto 部门信息
     * @return
     */
    @RequestMapping(value = "/dept", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "新增部门", notes = "新增部门")
    @ApiImplicitParam(name = "dto", value = "部门信息", required = true, dataType = "DepartmentDTO", paramType = "body")
    public Object save(@RequestBody DepartmentDTO dto) {
        userFeign.saveDepartment(dto);
        return ResponseEntity.noContent().build();
    }

    /**
     * 删除某个部门
     *
     * @param id 部门ID
     */
    @RequestMapping(value = "/dept/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除某个部门", notes = "删除某个部门")
    @ApiImplicitParam(name = "id", value = "部门ID", required = true, dataType = "Long", paramType = "path")
    public Object remove(@PathVariable("id") Integer id) {
        userFeign.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * 更新某个部门信息
     *
     * @param dto 部门信息
     * @return
     */
    @RequestMapping(value = "/dept", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "更新某个部门信息", notes = "更新某个部门信息")
    @ApiImplicitParam(name = "dto", value = "部门信息", required = true, dataType = "DepartmentDTO", paramType = "body")
    public Object update(@RequestBody DepartmentDTO dto) {
        userFeign.updateDepartment(dto);
        return ResponseEntity.noContent().build();
    }

    /**
     * 查询部门列表
     *
     * @param pageNum  第几页
     * @param pageSize 每页大小
     * @param name     角色名
     * @return
     */
    @RequestMapping(value = "/depts/{pageNum}/{pageSize}", method = RequestMethod.GET)
    @ApiOperation(value = "分页查询部门信息", notes = "分页查询部门信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "name", value = "部门名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sysType", value = "所属系统：2-翼猫业务管理系统；3-净水设备互动广告系统；", required = true, dataType = "Long", paramType = "query"),
    })
    public Object page(@PathVariable(value = "pageNum") Integer pageNum,
                       @PathVariable(value = "pageSize") Integer pageSize,
                       @RequestParam(value = "name", required = false) String name,
                       @RequestParam(value = "sysType") Integer sysType) {
        PageVO<DepartmentDTO> page = userFeign.getDepartmentPageByParams(pageNum, pageSize, name, sysType);
        return ResponseEntity.ok(page);
    }

}
