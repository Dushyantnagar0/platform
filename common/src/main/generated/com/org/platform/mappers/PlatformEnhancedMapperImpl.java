package com.org.platform.mappers;

import com.org.platform.beans.CustomerAccount;
import com.org.platform.requests.CustomerAccountRequest;
import com.org.platform.requests.ProfileUpdateRequest;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-28T02:20:25+0530",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.2 (Oracle Corporation)"
)
@Component
public class PlatformEnhancedMapperImpl implements PlatformEnhancedMapper {

    @Override
    public void populateCustomerAccountFromProfileUpdate(ProfileUpdateRequest profileUpdateRequest, CustomerAccount customerAccount) {
        if ( profileUpdateRequest == null ) {
            return;
        }

        if ( profileUpdateRequest.getPhoneNumber() != null ) {
            customerAccount.setPhoneNumber( profileUpdateRequest.getPhoneNumber() );
        }
        if ( profileUpdateRequest.getFirstName() != null ) {
            customerAccount.setFirstName( profileUpdateRequest.getFirstName() );
        }
        if ( profileUpdateRequest.getLastName() != null ) {
            customerAccount.setLastName( profileUpdateRequest.getLastName() );
        }
        if ( profileUpdateRequest.getAddress1() != null ) {
            customerAccount.setAddress1( profileUpdateRequest.getAddress1() );
        }
        if ( profileUpdateRequest.getAddress2() != null ) {
            customerAccount.setAddress2( profileUpdateRequest.getAddress2() );
        }
        if ( profileUpdateRequest.getCity() != null ) {
            customerAccount.setCity( profileUpdateRequest.getCity() );
        }
        if ( profileUpdateRequest.getZipCode() != null ) {
            customerAccount.setZipCode( profileUpdateRequest.getZipCode() );
        }
    }

    @Override
    public void populateCustomerAccountFromCustomerAccountRequest(CustomerAccountRequest customerAccountRequest, CustomerAccount customerAccount) {
        if ( customerAccountRequest == null ) {
            return;
        }

        if ( customerAccountRequest.getPhoneNumber() != null ) {
            customerAccount.setPhoneNumber( customerAccountRequest.getPhoneNumber() );
        }
        if ( customerAccountRequest.getUserAccessType() != null ) {
            customerAccount.setUserAccessType( customerAccountRequest.getUserAccessType().name() );
        }
        if ( customerAccountRequest.getFirstName() != null ) {
            customerAccount.setFirstName( customerAccountRequest.getFirstName() );
        }
        if ( customerAccountRequest.getLastName() != null ) {
            customerAccount.setLastName( customerAccountRequest.getLastName() );
        }
        if ( customerAccountRequest.getAddress1() != null ) {
            customerAccount.setAddress1( customerAccountRequest.getAddress1() );
        }
        if ( customerAccountRequest.getAddress2() != null ) {
            customerAccount.setAddress2( customerAccountRequest.getAddress2() );
        }
        if ( customerAccountRequest.getCity() != null ) {
            customerAccount.setCity( customerAccountRequest.getCity() );
        }
        if ( customerAccountRequest.getZipCode() != null ) {
            customerAccount.setZipCode( customerAccountRequest.getZipCode() );
        }
    }
}
