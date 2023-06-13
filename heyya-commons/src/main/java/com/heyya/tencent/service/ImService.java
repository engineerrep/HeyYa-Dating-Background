package com.heyya.tencent.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.ImmutableList;
import com.heyya.model.dto.BaseIMSendMsgDto;
import com.heyya.model.dto.IMAccountImportDto;
import com.heyya.model.dto.IMMsgDto;
import com.heyya.model.enums.ImSendMsgEnum;
import com.heyya.model.im.MsgBodyBaen;
import com.heyya.tencent.req.*;
import com.heyya.tools.utils.TxImUserSigGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ImService {

    private final String IM_ADMIN_USER = "admin";

    private final String IM_SDK_APP_ID = "123";

    private final String IM_ADMIN_USER_SIG = TxImUserSigGenerator.genSig(IM_ADMIN_USER);

    @Autowired
    private TencentClient tencentClient;

    @Async("asyncServiceExecutor")
    public void imSendMsg(@SuppressWarnings("rawtypes") BaseIMSendMsgDto im) {
        BaseReq req = createReq(im.getImSendMsgEnum(), im.getContent());
        if (StringUtils.isNotEmpty(im.getFromUserId())) {
            req.setUserSig(TxImUserSigGenerator.genSig(im.getFromUserId()));
        } else {
            req.setUserSig(IM_ADMIN_USER_SIG);
        }
        req.setIdentifier(IM_ADMIN_USER);
        req.setSdkappid(IM_SDK_APP_ID);
        if (StringUtils.isNotBlank(req.getUserSig())) {
            tencentClient.sendMsg(req);
        }
    }

    private <T> BaseReq createReq(ImSendMsgEnum imSendMsgEnum, T content) {
        switch (imSendMsgEnum) {
            case ADD_BLOCK:
                return addBlockReq(content);
            case DELETE_BLOCK:
                return deleteBlockReq(content);
            case SEND_MSG:
                return sendMsg(content);
            case BATCH_SEND_MSG:
                return batchSendMsg(content);
            case ACCOUNT_IMPORT:
                return accountImport(content);
            case OFFLINE_PUSH_INFO:
                return sendOfflinePushInfo(content);
            default:
                throw new NullPointerException();
        }
    }

    private <T> BaseReq accountImport(T content) {
        IMAccountImportDto dto = JSON.parseObject(JSON.toJSONString(content), new TypeReference<IMAccountImportDto>() {
        });
        AccountImportReq req = new AccountImportReq();
        req.setUserSig(IM_ADMIN_USER_SIG);
        req.setIdentifier(IM_ADMIN_USER);
        req.setSdkappid(IM_SDK_APP_ID);
        req.setUserId(dto.getUid());
        req.setNickName(dto.getNick());
        req.setAvatar(dto.getFaceUrl());
        return req;
    }

    private <T> BaseReq batchSendMsg(T content) {
        IMMsgDto im = JSON.parseObject(JSON.toJSONString(content), new TypeReference<IMMsgDto>() {
        });
        BatchSendReq req = new BatchSendReq();
        req.setUserSig(im.getImSig());
        req.setIdentifier(im.getImAdminUser());
        req.setSdkappid(im.getImSdkAppId());
        req.setSender(im.getFromUserId());
        req.setReceivers(im.getTargets());
        MsgBodyBaen msgBody = new MsgBodyBaen(im);
        req.setBody(ImmutableList.of(msgBody));
        OfflinePushInfo opi = new OfflinePushInfo();
        opi.setTitle(im.getTitle());
        opi.setContent(im.getContent());
        opi.setPushFlag(0);
        opi.setApnInfo(new ApnInfo(im.getTitle()));
        req.setOfflinePushInfo(opi);
        return req;
    }

    private <T> BaseReq sendMsg(T content) {
        IMMsgDto im = JSON.parseObject(JSON.toJSONString(content), new TypeReference<IMMsgDto>() {
        });
        BatchSendReq req = new BatchSendReq();
        req.setReceivers(im.getTargets());
        MsgBodyBaen msgBody = new MsgBodyBaen(im);
        req.setBody(ImmutableList.of(msgBody));
        OfflinePushInfo opi = new OfflinePushInfo();
        opi.setTitle(im.getTitle());
        opi.setContent(im.getContent());
        opi.setPushFlag(0);
        opi.setApnInfo(new ApnInfo(im.getTitle()));
        req.setOfflinePushInfo(opi);
        return req;
    }

    private <T> BaseReq addBlockReq(T content) {
        IMMsgDto im = JSON.parseObject(JSON.toJSONString(content), new TypeReference<IMMsgDto>() {
        });
        AddBlackListReq req = new AddBlackListReq();
        req.setBlackIds(ImmutableList.of(im.getBeBlockId().toString()));
        req.setUid(im.getUserId().toString());
        req.setUserSig(im.getImSig());
        req.setIdentifier(im.getImAdminUser());
        req.setSdkappid(im.getImSdkAppId());
        return req;
    }

    private <T> BaseReq deleteBlockReq(T content) {
        IMMsgDto im = JSON.parseObject(JSON.toJSONString(content), new TypeReference<IMMsgDto>() {
        });
        DelBlackListReq req = new DelBlackListReq();
        req.setBlackIds(ImmutableList.of(im.getBeBlockId().toString()));
        req.setUid(im.getUserId().toString());
        req.setUserSig(im.getImSig());
        req.setIdentifier(im.getImAdminUser());
        req.setSdkappid(im.getImSdkAppId());
        return req;
    }

    private <T> BaseReq sendOfflinePushInfo(T content) {
        IMMsgDto im = JSON.parseObject(JSON.toJSONString(content), new TypeReference<IMMsgDto>() {
        });
        BatchSendReq req = new BatchSendReq();
        OfflinePushInfo opi = new OfflinePushInfo();
        opi.setTitle(im.getTitle());
        opi.setContent(im.getContent());
        opi.setPushFlag(0);
        opi.setApnInfo(new ApnInfo(im.getTitle()));
        req.setOfflinePushInfo(opi);
        return req;
    }

}
