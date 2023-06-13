package com.heyya.http.pool.consts;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public final class CharsetUtil {
	
    public static final Charset UTF_16 = StandardCharsets.UTF_16;

    public static final Charset UTF_8 = StandardCharsets.UTF_8;

    public static final Charset ISO_8859_1 = StandardCharsets.ISO_8859_1;
    
    public static final Charset GBK = Charset.forName("GBK");
    
    public static final Charset GB2312 = Charset.forName("GB2312");
    
}
