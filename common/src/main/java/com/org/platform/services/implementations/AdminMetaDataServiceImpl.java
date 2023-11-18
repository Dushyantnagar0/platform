package com.org.platform.services.implementations;

import com.org.platform.beans.UserMetaData;
import com.org.platform.repos.interfaces.AdminMetaDataRepository;
import com.org.platform.requests.UserMetaDataRequest;
import com.org.platform.services.interfaces.AdminMetaDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static java.util.Objects.nonNull;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdminMetaDataServiceImpl implements AdminMetaDataService {

    private final AdminMetaDataRepository adminMetaDataRepository;

    public UserMetaData upsertEmailIdAndAccessLevelInMetaData(UserMetaDataRequest userMetaDataRequest) {
        if(nonNull(userMetaDataRequest)) {
            UserMetaData userMetaData = new UserMetaData(userMetaDataRequest.getEmailId(), userMetaDataRequest.getUserAccessType().name());
            return adminMetaDataRepository.upsertUserMetaData(userMetaData);
        }
        return null;
    }

    public String fetchAccessLevelForAnEmailId(String emailId) {
        UserMetaData userMetaData = adminMetaDataRepository.fetchUserMetaDataFromEmailId(emailId);
        if(nonNull(userMetaData)) return userMetaData.getUserAccessType();
        return null;
    }
}
