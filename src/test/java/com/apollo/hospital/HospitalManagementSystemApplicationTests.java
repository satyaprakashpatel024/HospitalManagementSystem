package com.apollo.hospital;

import com.apollo.hospital.dtos.response.UserRespDTO;
import com.apollo.hospital.services.UserServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HospitalManagementSystemApplicationTests {

    @Autowired
    private UserServices userServices;

    @Test
    public void getAll(){
        UserRespDTO userByEmail = userServices.findUserByEmail("dr.lisa.wong@hospital.com");
        System.out.println(userByEmail);
    }
}
