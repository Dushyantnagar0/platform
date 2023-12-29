package com.org.platform.services.implementations;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.org.platform.beans.UserMetaData;
import com.org.platform.repos.interfaces.AdminMetaDataRepository;
import com.org.platform.requests.UserMetaDataRequest;
import com.org.platform.services.interfaces.AdminMetaDataService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.nonNull;

@Slf4j
@Component
public class AdminMetaDataServiceImpl implements AdminMetaDataService {

    @Autowired
    private AdminMetaDataRepository adminMetaDataRepository;
    private static LoadingCache<String, UserMetaData> userMetaDataLoadingCache;

    public AdminMetaDataServiceImpl() {
        userMetaDataLoadingCache = CacheBuilder.newBuilder()
                .expireAfterWrite(24, TimeUnit.HOURS)
                .build(
                        new CacheLoader<>() {
                            @Override
                            public UserMetaData load(@NotNull String emailId) {
                                log.info("loading cache is null so fetching form DB : {}", emailId);
                                return adminMetaDataRepository.fetchUserMetaDataFromEmailId(emailId);
                            }
                        }
                );
    }


    public UserMetaData upsertEmailIdAndAccessLevelInMetaData(UserMetaDataRequest userMetaDataRequest) {
        if(nonNull(userMetaDataRequest)) {
            UserMetaData userMetaData = new UserMetaData(userMetaDataRequest.getEmailId(), userMetaDataRequest.getUserAccessType().name());
            return adminMetaDataRepository.upsertUserMetaData(userMetaData);
        }
        return null;
    }

    public String fetchAccessLevelForAnEmailId(String emailId) {
//        UserMetaData userMetaData = adminMetaDataRepository.fetchUserMetaDataFromEmailId(emailId);
        UserMetaData userMetaData = fetchUserMetaDataCached(emailId);
        if(nonNull(userMetaData)) return userMetaData.getUserAccessType();
        return null;
    }

    public UserMetaData fetchUserMetaDataCached(String emailId) {
        log.info("from loading cache : {}", emailId);
        return userMetaDataLoadingCache.getUnchecked(emailId);
    }
}
