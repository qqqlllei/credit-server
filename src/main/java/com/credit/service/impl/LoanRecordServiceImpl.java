package com.credit.service.impl;
import com.credit.dao.LoanRecordMapper;
import com.credit.base.BaseDao;
import com.credit.service.LoanRecordService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.credit.entity.LoanRecord;
import com.credit.base.BaseServiceImpl;

/**
 *  LoanRecordServiceImpl
 */
@Service("loanRecordService")
public class LoanRecordServiceImpl extends BaseServiceImpl<LoanRecord> implements LoanRecordService {


    @Autowired
    private LoanRecordMapper loanRecordMapper;

    @Override
    protected BaseDao getBaseDao() {
        return this.loanRecordMapper;
    }




}
