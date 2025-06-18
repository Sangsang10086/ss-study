package com.easyexcel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.exception.ExcelAnalysisStopException;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.read.listener.ReadListener;
import com.easyexcel.biz.ITeacherService;
import com.easyexcel.entity.ReadErrorModel;
import com.easyexcel.entity.StopPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class StopPointReadListener implements ReadListener<StopPoint> {
    private static final Logger logger = LoggerFactory.getLogger(StopPointReadListener.class);

    @Autowired
    private ITeacherService teacherService;

    /**
     * 每读取一行数据都会调用此方法
     * @param stopPoint
     * @param analysisContext
     */
    @Override
    public void invoke(StopPoint stopPoint, AnalysisContext analysisContext) {
        teacherService.insertBeach(stopPoint);
    }

    /**
     * 读取完成后调用此方法
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        logger.info("导入完成");
    }

    @Override
    public boolean hasNext(AnalysisContext context) {
        return ReadListener.super.hasNext(context);
    }

    /**
     * 解析异常时，会执行的方法
     * @param exception
     * @param context
     */
    @Override
    public void onException(Exception exception, AnalysisContext context) {
        //  ReadListener.super.onException(exception, context);
        ExcelDataConvertException dataConvertException = (ExcelDataConvertException) exception;
        //获取行的下标
        Integer rowIndex = dataConvertException.getRowIndex();
        //获取列的下标
        Integer columnIndex = dataConvertException.getColumnIndex();
        //数据
        String content = dataConvertException.getCellData().getStringValue();
        //原因
        String message = dataConvertException.getMessage();

        ReadErrorModel readErrorModel = new ReadErrorModel(message,rowIndex, columnIndex, content);
        throw new ExcelAnalysisStopException(String.valueOf(readErrorModel));//必须抛出

    }

    @Override
    public void invokeHead(Map<Integer, ReadCellData<?>> headMap, AnalysisContext context) {
        ReadListener.super.invokeHead(headMap, context);
    }

    @Override
    public void extra(CellExtra extra, AnalysisContext context) {
        ReadListener.super.extra(extra, context);
    }

}
