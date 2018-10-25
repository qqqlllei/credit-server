package com.credit.service;

import com.credit.base.BaseService;
import com.credit.entity.CreditRequest;

/**
 * Created by 李雷 on 2018/10/24.
 */
public interface CreditRequestService extends BaseService<CreditRequest> {

    int saveCreditRequest(CreditRequest creditRequest);

    CreditRequest getCreditRequestByPhone(String phone);
}
