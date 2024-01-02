package com.org.platform.mappers;

import com.org.platform.beans.CustomerAccount;
import com.org.platform.requests.CustomerAccountRequest;
import com.org.platform.requests.ProfileUpdateRequest;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PlatformEnhancedMapper {

    void populateCustomerAccountFromProfileUpdate(ProfileUpdateRequest profileUpdateRequest, @MappingTarget CustomerAccount customerAccount);
    @Mapping(target = "customerId", ignore = true)
    @Mapping(target = "emailId", ignore = true)
    void populateCustomerAccountFromCustomerAccountRequest(CustomerAccountRequest customerAccountRequest, @MappingTarget CustomerAccount customerAccount);

}
