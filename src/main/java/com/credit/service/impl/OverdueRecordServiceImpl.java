package com.credit.service.impl;
import com.credit.dao.OverdueRecordMapper;
import com.credit.base.BaseDao;
import com.credit.service.OverdueRecordService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.credit.entity.OverdueRecord;
import com.credit.base.BaseServiceImpl;

/**
 *  OverdueRecordServiceImpl
 */
@Service("overdueRecordService")
public class OverdueRecordServiceImpl extends BaseServiceImpl<OverdueRecord> implements OverdueRecordService {



    @Autowired
    private OverdueRecordMapper overdueRecordMapper;

    @Override
    protected BaseDao getBaseDao() {
        return this.overdueRecordMapper;
    }




}
