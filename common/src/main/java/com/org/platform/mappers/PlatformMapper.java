package com.org.platform.mappers;


import com.org.platform.beans.CustomerAccount;
import com.org.platform.requests.ProfileUpdateRequest;
import com.org.platform.responses.CustomerAccountResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
public interface PlatformMapper {

    void populateCustomerAccountFromProfileUpdate(ProfileUpdateRequest profileUpdateRequest, @MappingTarget CustomerAccount customerAccount);

    CustomerAccountResponse convertToCustomerAccountResponse(CustomerAccount customerAccount);
}
