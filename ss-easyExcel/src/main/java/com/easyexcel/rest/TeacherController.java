package com.easyexcel.rest;


import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.nacos.api.model.v2.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.easyexcel.biz.ITeacherService;
import com.easyexcel.entity.StopPoint;
import com.easyexcel.entity.Teacher;
import com.easyexcel.listener.StopPointReadListener;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author sang
 * @since 2025-06-18
 */
@RestController
@RequestMapping("/easyExcel")
public class TeacherController {

    @Autowired
    private StopPointReadListener stopPointReadListener;

    @Autowired
    private ITeacherService teacherService;



    /**
     * excel同步导入接口
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/teacherSyncImport",method = RequestMethod.POST)
    public Result<String> importSyncExcel(@RequestParam("file") MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        List<Map<Integer, Object>> list = EasyExcel.read(inputStream)
                .sheet(0)
                .head(StopPoint.class)    // 映射实体类
                .headRowNumber(1)        // 表头行数
                .registerReadListener(stopPointReadListener)    // 注册监听器
                .doReadSync();    // 同步读取

        return Result.success();
    }



    /**
     * excel异步导入接口
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/teacherImport",method = RequestMethod.POST)
    public Result<String> importExcel(@RequestParam("file") MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();

        EasyExcel.read(inputStream)
                .sheet(0)
                .head(StopPoint.class)
                .registerReadListener(stopPointReadListener)
                .doRead(); // 异步读取

        return Result.success("异步导入中...");
    }

    /**
     * 导出教师数据为 Excel 文件
     *
     * @param response HttpServletResponse 用于输出文件流
     * @throws IOException IO 异常
     */
    @RequestMapping(value = "/teacherExport", method = RequestMethod.GET)
    public void exportTeacherToExcel(HttpServletResponse response) throws IOException {
        // 1. 查询需要导出的数据
        List<Teacher> teacherList = teacherService.list();

        // 2. 设置响应头，让浏览器识别这是一个文件下载
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        // 3. 编码文件名，防止中文乱码
        String fileName = URLEncoder.encode("教师列表", StandardCharsets.UTF_8)
                .replaceAll("\\+", "%20"); // 处理空格编码问题

        response.setHeader("Content-Disposition",
                "attachment; filename*=UTF-8''" + fileName + ".xlsx");

        // 4. 使用 EasyExcel 写入响应输出流
        EasyExcel.write(response.getOutputStream(), Teacher.class)
                .head(Teacher.class)
                .sheet("教师信息")
                .doWrite(teacherList);
    }


}
