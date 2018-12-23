package com.credit.service;

import com.credit.base.BaseService;
import com.credit.entity.LogInfo;

/**
 * Created by Administrator on 2018/12/23 0023.
 */
public interface LogInfoService extends BaseService<LogInfo> {
    void saveLogInfo(LogInfo logInfo);
}
