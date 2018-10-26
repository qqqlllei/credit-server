package com.credit.service.impl;
import com.credit.base.BaseDao;
import com.credit.base.BaseServiceImpl;
import com.credit.dao.CrossEventMapper;
import com.credit.entity.CrossEvent;
import com.credit.service.CrossEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 *  CrossEventServiceImpl
 */
@Service("crossEventService")
public class CrossEventServiceImpl extends BaseServiceImpl<CrossEvent> implements CrossEventService {


    @Autowired
    private CrossEventMapper crossEventMapper;

    @Override
    protected BaseDao getBaseDao() {
        return this.crossEventMapper;
    }


}
