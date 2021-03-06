package com.credit.base;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class PageParamHelper {

    public static final int DEFAULT_NUM_PER_PAGE = 10;

    public static PageParam getPageParam(HttpServletRequest request){
        Map<String, Object> bm = new HashMap<String, Object>();
        PageParam pageParam = new PageParam();
        Map<String, String[]> tmp = request.getParameterMap();
        if (tmp != null) {
            for (String key : tmp.keySet()) {
                key = key.trim();
                String[] values = tmp.get(key);
                Object obj = values.length == 1 ? values[0].trim() : values;
                bm.put(key, obj == null ? "" : obj);
            }
        }
        pageParam.setParams(bm);
        int currentPage = ((bm.get("currentPage") == null || ""
                .equals(bm.get("currentPage"))) ? 1 : Integer
                .parseInt(bm.get("currentPage").toString())); // 如果当前页为空则赋值1
        int pageSize = ((bm.get("pageSize") == null || ""
                .equals(bm.get("pageSize"))) ? DEFAULT_NUM_PER_PAGE : Integer
                .parseInt(bm.get("pageSize").toString())); // 如果每页记录数则为默认
        pageParam.setCurrentPage(currentPage);
        pageParam.setNumPerPage(pageSize);
        return pageParam;
    }

    public static PageParam getPageParam(Map<String,Object> param){
        PageParam pageParam = new PageParam();
        pageParam.setParams(param);
        int currentPage = ((param.get("currentPage") == null || ""
                .equals(param.get("currentPage"))) ? 1 : Integer
                .parseInt(param.get("currentPage").toString())); // 如果当前页为空则赋值1
        int numPerPage = ((param.get("numPerPage") == null || ""
                .equals(param.get("numPerPage"))) ? DEFAULT_NUM_PER_PAGE : Integer
                .parseInt(param.get("numPerPage").toString())); // 如果每页记录数则为默认
        pageParam.setCurrentPage(currentPage);
        pageParam.setNumPerPage(numPerPage);
        return pageParam;
    }


}
