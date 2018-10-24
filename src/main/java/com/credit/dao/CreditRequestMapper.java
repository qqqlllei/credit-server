package com.credit.dao;

import com.credit.base.BaseDao;
import com.credit.domain.entity.CreditRequest;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/10/20 0020.
 */
@Component
public interface CreditRequestMapper extends BaseDao<CreditRequest> {

    CreditRequest getCreditRequestByPhone(String phone);
}
