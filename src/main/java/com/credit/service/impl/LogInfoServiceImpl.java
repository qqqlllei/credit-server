package com.credit.service.impl;

import com.credit.base.BaseDao;
import com.credit.base.BaseServiceImpl;
import com.credit.dao.LogInfoMapper;
import com.credit.entity.LogInfo;
import com.credit.service.LogInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2018/12/23 0023.
 */
@Service("logInfoService")
public class LogInfoServiceImpl extends BaseServiceImpl<LogInfo> implements LogInfoService{

    @Autowired
    private LogInfoMapper logInfoMapper;

    @Override
    protected BaseDao getBaseDao() {
        return logInfoMapper;
    }

    @Override
    public void saveLogInfo(LogInfo logInfo) {
        logInfoMapper.saveLogInfo(logInfo);
    }
}
