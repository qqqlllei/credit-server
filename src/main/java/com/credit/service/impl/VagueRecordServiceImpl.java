package com.credit.service.impl;
import com.credit.dao.VagueRecordMapper;
import com.credit.base.BaseDao;
import com.credit.service.VagueRecordService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.credit.entity.VagueRecord;
import com.credit.base.BaseServiceImpl;

/**
 *  VagueRecordServiceImpl
 */
@Service("vagueRecordService")
public class VagueRecordServiceImpl extends BaseServiceImpl<VagueRecord> implements VagueRecordService {



    @Autowired
    private VagueRecordMapper vagueRecordMapper;

    @Override
    protected BaseDao getBaseDao() {
        return this.vagueRecordMapper;
    }


}
