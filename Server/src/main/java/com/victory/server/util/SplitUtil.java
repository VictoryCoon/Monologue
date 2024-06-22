package com.victory.server.util;

import com.victory.server.repository.MsgRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;

@Slf4j
public class SplitUtil {
    Environment ENV;
    //private final int bufferSize = Integer.parseInt(ENV.getProperty("buffer.size"));
    public MsgRepository splitInfoMsgRepository(StringBuilder sb){
        MsgRepository msg = null;
        if(10879 != sb.length()){
            log.info("Buffer Size is incorrect - {} : {}",10879,sb.length());
            throw new RuntimeException();
        }else{
            int charAt=0;
            msg = new MsgRepository();
            msg.setMsgId(sb.substring(charAt,charAt+=20));
            msg.setMsgDt(sb.substring(charAt,charAt+=8));
            msg.setUsrId(sb.substring(charAt,charAt+=16).trim());
            msg.setSendType(sb.substring(charAt,charAt+=1).charAt(0));
            msg.setMsgType(sb.substring(charAt,charAt+=1).charAt(0));
            msg.setMsgTitle(sb.substring(charAt,charAt+=64).trim());
            msg.setMsgContent(sb.substring(charAt,charAt+=2000).trim());
            msg.setMsgPayload(sb.substring(charAt,charAt+=4000).trim());
            msg.setMsgFulltext(sb.substring(charAt,charAt+=4000).trim());
            msg.setTokenPrivate(sb.substring(charAt,charAt+=256).trim());
            msg.setTokenPublic(sb.substring(charAt,charAt+=256).trim());
            msg.setImageUrl(sb.substring(charAt,charAt+=256).trim());
            msg.setSound(sb.substring(charAt,charAt+=1).charAt(0));
        }
        return msg;
    }
}
