package com.heyya.model.enums;

public enum ImSendMsgEnum {

    ADD_BLOCK("ADD_BLOCK"),
    DELETE_BLOCK("DELETE_BLOCK"),
    SEND_MSG("SEND_MSG"),
    BATCH_SEND_MSG("BATCH_SEND_MSG"),
    ACCOUNT_IMPORT("ACCOUNT_IMPORT"),
    OFFLINE_PUSH_INFO("OFFLINE_PUSH_INFO");

    private ImSendMsgEnum(String name) {
        this.name = name;
    }

    private String name;

}
