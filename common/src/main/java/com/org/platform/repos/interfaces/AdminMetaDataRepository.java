package com.org.platform.repos.interfaces;

import com.org.platform.beans.UserMetaData;

import java.util.List;

public interface AdminMetaDataRepository {
    UserMetaData upsertUserMetaData(UserMetaData userMetaData);
    UserMetaData fetchUserMetaDataFromEmailId(String emailId);
    List<UserMetaData> fetchAllUserMetaData();

}
