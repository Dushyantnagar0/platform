package com.org.platform.mappers;


import com.org.platform.beans.CustomerAccount;
import com.org.platform.responses.CustomerAccountResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PlatformMapper {

    CustomerAccountResponse convertToCustomerAccountResponse(CustomerAccount customerAccount);
}
