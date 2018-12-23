package com.credit.dao;

import com.credit.base.BaseDao;
import com.credit.entity.LogInfo;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/12/23 0023.
 */
@Component
public interface LogInfoMapper extends BaseDao<LogInfo> {
    void saveLogInfo(LogInfo logInfo);
}
