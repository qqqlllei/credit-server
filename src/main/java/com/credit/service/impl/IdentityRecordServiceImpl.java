package com.credit.service.impl;
import com.credit.dao.IdentityRecordMapper;
import com.credit.base.BaseDao;
import com.credit.service.IdentityRecordService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.credit.entity.IdentityRecord;
import com.credit.base.BaseServiceImpl;
/**
 *  IdentityRecordServiceImpl
 */
@Service("identityRecordService")
public class IdentityRecordServiceImpl extends BaseServiceImpl<IdentityRecord> implements IdentityRecordService {


    @Autowired
    private IdentityRecordMapper identityRecordMapper;

    @Override
    protected BaseDao getBaseDao() {
        return this.identityRecordMapper;
    }



}
