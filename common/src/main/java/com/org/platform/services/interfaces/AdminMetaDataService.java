package com.org.platform.services.interfaces;

import com.org.platform.beans.UserMetaData;
import com.org.platform.requests.UserMetaDataRequest;

public interface AdminMetaDataService {
    UserMetaData upsertEmailIdAndAccessLevelInMetaData(UserMetaDataRequest userMetaDataRequest);
    String fetchAccessLevelForAnEmailId(String emailId);
}
