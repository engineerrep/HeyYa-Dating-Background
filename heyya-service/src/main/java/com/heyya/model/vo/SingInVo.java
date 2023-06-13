package com.heyya.model.vo;


import com.heyya.config.auth.UserToken;
import com.heyya.config.auth.UserTokenUtils;
import com.heyya.constans.ImKeyConts;
import com.heyya.model.enums.AccountType;
import com.heyya.model.enums.SignType;
import com.heyya.tools.utils.TxImUserSigGenerator;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

@Data
public class SingInVo extends BaseVo {
    @ApiModelProperty("用户对象")
    private UserVo user;

    @ApiModelProperty("用户账号")
    private String account;

    @ApiModelProperty("token")
    private String token;

    @ApiModelProperty("im的sig")
    private String sig;

    @ApiModelProperty("结果类型: SIGNIN - 登录，SIGNUP - 注册")
    private SignType signType;

    @ApiModelProperty("注册、登录平台")
    private AccountType type;

    public String getToken() {
        UserToken token = new UserToken();
        token.setId(Long.valueOf(user.getId()));
        return UserTokenUtils.create(token);
    }

    public String getSig() {
        if (Objects.nonNull(user) && StringUtils.isNotEmpty(user.getId())) {
            return TxImUserSigGenerator.genSig(ImKeyConts.IM_KEY + user.getId());
        }
        return null;
    }
}
