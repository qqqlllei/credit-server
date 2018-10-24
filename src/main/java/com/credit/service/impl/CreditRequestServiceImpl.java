package com.credit.service.impl;

import com.credit.base.BaseDao;
import com.credit.base.BaseServiceImpl;
import com.credit.dao.CreditRequestMapper;
import com.credit.domain.entity.CreditRequest;
import com.credit.service.CreditRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by Administrator on 2018/10/20 0020.
 */
@Service("creditRequestService")
public class CreditRequestServiceImpl extends BaseServiceImpl<CreditRequest> implements CreditRequestService {

    @Autowired
    private CreditRequestMapper creditRequestMapper;

    public int saveCreditRequest(CreditRequest creditRequest){
        creditRequest.setId(UUID.randomUUID().toString());
        return creditRequestMapper.insert(creditRequest);
    }

    public CreditRequest getCreditRequestByPhone(String phone) {
        return creditRequestMapper.getCreditRequestByPhone(phone);
    }

    @Override
    protected BaseDao getBaseDao() {
        return creditRequestMapper;
    }

}
