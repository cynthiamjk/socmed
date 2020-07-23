package com.cynthia.socmed.comp;

import org.springframework.stereotype.Component;

@Component
public class PostMediaPlayer {

    public boolean isFromSoundcloud (StringBuffer sb, String s) {
       return (sb.toString().contains("soundcloud") && s.startsWith("src="));
    }

    public boolean isFromBeatPort(StringBuffer sb, String s) {
        return (sb.toString().contains("beatport") && s.startsWith("src="));
    }

    public String setSCEmbedLink (String s) {
            int lastInd = s.indexOf(">");
            String embed = s.substring(5, lastInd - 1);
            return embed;
        }

}
