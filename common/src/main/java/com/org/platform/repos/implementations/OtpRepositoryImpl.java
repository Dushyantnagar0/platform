package com.org.platform.repos.implementations;

import com.org.platform.beans.EmailOtpBean;
import com.org.platform.repos.interfaces.OtpRepository;
import com.org.platform.services.interfaces.PlatformMongoService;
import com.org.platform.services.interfaces.PlatformRedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import static com.org.platform.utils.Constants.PLATFORM_GLOBAL_DB;

@Slf4j
@Repository
@Component
@RequiredArgsConstructor
public class OtpRepositoryImpl implements OtpRepository {

    private final PlatformRedisService platformRedisService;

    @Override
    public void saveEmailOtpBean(String refId, EmailOtpBean emailOtpBean) {
        platformRedisService.save(refId, emailOtpBean);
        platformRedisService.setExpire(refId, 900);
    }

    @Override
    public EmailOtpBean getEmailOtpBeanByRefId(String refId) {
        return (EmailOtpBean) platformRedisService.get(refId);
    }
}
