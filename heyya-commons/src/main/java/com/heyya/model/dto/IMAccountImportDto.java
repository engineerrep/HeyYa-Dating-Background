package com.heyya.model.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel
public class IMAccountImportDto {
    private String uid;
    private String nick;
    private String faceUrl;

    public IMAccountImportDto(String uid, String nick, String faceUrl) {
        this.uid = uid;
        this.nick = nick;
        this.faceUrl = faceUrl;
    }
}
