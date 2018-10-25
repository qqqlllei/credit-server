package com.credit.service.impl;
import com.credit.dao.NamelistRecordMapper;
import com.credit.base.BaseDao;
import com.credit.service.NamelistRecordService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.credit.entity.NamelistRecord;
import com.credit.base.BaseServiceImpl;

/**
 *  NamelistRecordServiceImpl
 */
@Service("namelistRecordService")
public class NamelistRecordServiceImpl extends BaseServiceImpl<NamelistRecord> implements NamelistRecordService {



    @Autowired
    private NamelistRecordMapper namelistRecordMapper;

    @Override
    protected BaseDao getBaseDao() {
        return this.namelistRecordMapper;
    }




}
