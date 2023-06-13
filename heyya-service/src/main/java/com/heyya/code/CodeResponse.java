package com.heyya.code;

import com.heyya.model.resp.Response;
import com.heyya.model.vo.StaffSigninVo;

public final class CodeResponse {
    public static final Response<Object> FAILED = Response.failed(8500, "Processing failed!");
    public static final Response FILE_NOT_EMPTY = Response.failed(1000, "The file size cannot be zero!");
    public static final Response FILE_UPLOAD_FAIL = Response.failed(1001, "The file upload failed!");
    public static final Response ACCOUNT_EXISTS = Response.failed(1002, "Account number already exists!");
    public static final Response EMAIL_CODE_ERR = Response.failed(1004, "Invalid verification code.");
    public static final Response BLACKENED = Response.failed(1005, "Blackened.");
    public static final Response DEL_ACCOUNT = Response.failed(1006, "Account deletion failed.");
    public static final Response SET_MAIN_VIDEO = Response.failed(1007, "Cannot be set as the main video if the audit fails.");
    public static final Response VIDEO_NOT_PRIVATE = Response.failed(1008, "The main video cannot be set as private.");
    public static final Response<StaffSigninVo> LOGIN_FAILED = Response.failed(8401, "Account or password error!");
    public static final Response<StaffSigninVo> EMAIL_EXISTS = Response.failed(1009, "Appointment succeeded! We‘re coming soon...");

}
