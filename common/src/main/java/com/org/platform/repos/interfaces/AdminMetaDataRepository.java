package com.org.platform.repos.interfaces;

import com.org.platform.beans.UserMetaData;

public interface AdminMetaDataRepository {
    UserMetaData upsertUserMetaData(UserMetaData userMetaData);
    UserMetaData fetchUserMetaDataFromEmailId(String emailId);
}
