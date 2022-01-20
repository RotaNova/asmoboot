package com.rotanava.framework.util;

import com.rotanava.framework.code.ErrorCode;
import com.rotanava.framework.model.BaseDTO;
import com.rotanava.framework.model.BaseVO;
import com.rotanava.framework.code.RetData;

import java.text.SimpleDateFormat;
import java.util.List;


public class BuildUtil {



    public static RetData buildErrorTextResult(String resultMsg) {
        return new RetData(201, null, resultMsg);
    }





    /**
     * @return void
     * @Description 只操作beginTime和EndTime防止时间问题。
     * @Author yangsu
     * @Date 20:02 2020/5/29
     * @Param [baseDTO]
     **/
    public static void buildDateDTO(BaseDTO baseDTO) {
        String beginTime = "";
        String endTime = "";
        String[] array = baseDTO.getDateArray();
        if (array != null && array.length == 2) {
            beginTime = array[0];
            endTime = array[1];
        }
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (!DateUtil.comparisonTime(beginTime, endTime, sf)) {
            beginTime = Date8Util.getStartTimeStr("yyyy-MM-dd HH:mm:ss");
            endTime =  Date8Util.getEndTimeStr("yyyy-MM-dd HH:mm:ss");
        }

        //如果array为空的话，则填充
        if (array==null||array.length==0){
            baseDTO.setDateArray(new String[]{beginTime,endTime});
        }


        baseDTO.setBeginTime(beginTime);
        baseDTO.setEndTime(endTime);
    }


    public static<T> BaseVO<T> buildListVO( T data){
        BaseVO<T> vo = new BaseVO<>();
        vo.setData(data);
        return vo;
    }

    public static<T> BaseVO<T> buildListVO(Long count, List<T> data){
        BaseVO<T> vo = new BaseVO<>();
        vo.setTotal(count);
        vo.setRecords(data);
        return vo;
    }

    public static<T> BaseVO buildListVO(Long count, List<T> data,String [] dateArray){
        BaseVO vo = new BaseVO();
        vo.setTotal(count);
        vo.setRecords(data);
        vo.setDataArray(dateArray);
        return vo;
    }

    public static RetData buildSuccessResult() {
        return new RetData(200, "请求成功");
    }



    public static RetData buildErrorResult(ErrorCode code) {
//        System.out.println(code);
        return new RetData(code.getCode(), null, code.getMsg());
    }




    public static RetData buildParamError() {
        return new RetData(407, null, "参数解析失败,请检查传参");
    }

    public static RetData buildError(Integer code,String msg) {
        return new RetData(code, null, msg);
    }

    public static RetData buildParamError(String msg) {
        return new RetData(407, null, msg);
    }







}
