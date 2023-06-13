package com.heyya.model.vo;


import com.heyya.model.enums.SignType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public class UserSigninVo {

    @ApiModelProperty("用户信息")
    private UserVo user;

    @ApiModelProperty("token")
    private String token;

    @ApiModelProperty("登录/注册")
    private SignType signType;

    public static UserSigninVOBuilder builder() {
        return new UserSigninVOBuilder();
    }

    @Override
    public String toString() {
        return "UserSigninVO(user=" + this.getUser() + ",  token=" + this.getToken() + ")";
    }

    public static class UserSigninVOBuilder {

        private UserVo user;
        private String imUrl;
        private String token;
        private SignType signType;

        UserSigninVOBuilder() {
        }

        public UserSigninVOBuilder user(final UserVo user) {
            Assert.notNull(user, "User cannot be empty!");
            this.user = user;
            return this;
        }

        public UserSigninVOBuilder imUrl(final String imUrl) {
            this.imUrl = imUrl;
            return this;
        }

        public UserSigninVOBuilder token(final String token) {
            Assert.notNull(token, "Token cannot be empty!");
            this.token = token;
            return this;
        }

        public UserSigninVOBuilder signType(final SignType signType) {
            Assert.notNull(signType, "SignType cannot be empty!");
            this.signType = signType;
            return this;
        }

        public UserSigninVo build() {
            return new UserSigninVo(this.user, this.token,this.signType);
        }

        @Override
        public String toString() {
            return "UserSigninVO.UserSigninVOBuilder(user=" + this.user + ",  token=" + this.token + ")";
        }
    }

}
