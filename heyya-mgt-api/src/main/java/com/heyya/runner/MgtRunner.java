package com.heyya.runner;


import com.heyya.model.dto.StaffPersistDto;
import com.heyya.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class MgtRunner implements ApplicationRunner {

    @Autowired
    private StaffService staffService;

    @Override
    public void run(ApplicationArguments args) {
        if (Objects.isNull(staffService.findByAccount("admin"))) {
            StaffPersistDto dto = new StaffPersistDto();
            dto.setAccount("admin");
            dto.setPassword("123456");
            staffService.save(dto);
        }
    }

}
