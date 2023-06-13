package com.heyya.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heyya.code.CodeResponse;
import com.heyya.exception.SvcException;
import com.heyya.mapper.SubscribeEmailMapper;
import com.heyya.model.entity.SubscribeEmail;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional(readOnly = true)
public class SubscribeEmailService extends ServiceImpl<SubscribeEmailMapper, SubscribeEmail> {

    @Transactional
    public void save(String email) {
        SubscribeEmail subEmail = getOne(new LambdaQueryWrapper<SubscribeEmail>().eq(SubscribeEmail::getEmail, email));
        if (Objects.nonNull(subEmail)) {
            throw new SvcException(CodeResponse.EMAIL_EXISTS);
        }
        SubscribeEmail subscribeEmail = new SubscribeEmail();
        subscribeEmail.setEmail(email);
        save(subscribeEmail);
    }

}
