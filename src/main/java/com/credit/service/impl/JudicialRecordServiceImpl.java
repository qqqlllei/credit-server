package com.credit.service.impl;
import com.credit.dao.JudicialRecordMapper;
import com.credit.base.BaseDao;
import com.credit.service.JudicialRecordService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.credit.entity.JudicialRecord;
import com.credit.base.BaseServiceImpl;

/**
 *  JudicialRecordServiceImpl
 */
@Service("judicialRecordService")
public class JudicialRecordServiceImpl extends BaseServiceImpl<JudicialRecord> implements JudicialRecordService {


    @Autowired
    private JudicialRecordMapper judicialRecordMapper;

    @Override
    protected BaseDao getBaseDao() {
        return this.judicialRecordMapper;
    }



}
