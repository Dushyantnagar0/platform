package com.org.platform.mappers;

import com.org.platform.beans.CustomerAccount;
import com.org.platform.responses.CustomerAccountResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-28T02:20:25+0530",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.2 (Oracle Corporation)"
)
@Component
public class PlatformMapperImpl implements PlatformMapper {

    @Override
    public CustomerAccountResponse convertToCustomerAccountResponse(CustomerAccount customerAccount) {
        if ( customerAccount == null ) {
            return null;
        }

        CustomerAccountResponse customerAccountResponse = new CustomerAccountResponse();

        customerAccountResponse.setEmailId( customerAccount.getEmailId() );
        customerAccountResponse.setCustomerId( customerAccount.getCustomerId() );
        customerAccountResponse.setPhoneNumber( customerAccount.getPhoneNumber() );
        customerAccountResponse.setUserAccessType( customerAccount.getUserAccessType() );
        customerAccountResponse.setFirstName( customerAccount.getFirstName() );
        customerAccountResponse.setLastName( customerAccount.getLastName() );
        customerAccountResponse.setAddress1( customerAccount.getAddress1() );
        customerAccountResponse.setAddress2( customerAccount.getAddress2() );
        customerAccountResponse.setCity( customerAccount.getCity() );
        customerAccountResponse.setZipCode( customerAccount.getZipCode() );

        return customerAccountResponse;
    }
}
