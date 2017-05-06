package com.dang.crawler.core.fetcher.http;

import java.io.IOException;
import jcifs.ntlmssp.Type1Message;
import jcifs.ntlmssp.Type2Message;
import jcifs.ntlmssp.Type3Message;
import jcifs.util.Base64;
import org.apache.http.impl.auth.NTLMEngine;
import org.apache.http.impl.auth.NTLMEngineException;

public final class JCIFSEngine implements NTLMEngine {
    private static final int TYPE_1_FLAGS = -1610055676;

    public JCIFSEngine() {
    }

    public String generateType1Msg(String domain, String workstation) throws NTLMEngineException {
        Type1Message type1Message = new Type1Message(-1610055676, domain, workstation);
        return Base64.encode(type1Message.toByteArray());
    }

    public String generateType3Msg(String username, String password, String domain, String workstation, String challenge) throws NTLMEngineException {
        Type2Message type2Message;
        try {
            type2Message = new Type2Message(Base64.decode(challenge));
        } catch (IOException var10) {
            throw new NTLMEngineException("Invalid NTLM type 2 message", var10);
        }

        int type2Flags = type2Message.getFlags();
        int type3Flags = type2Flags & -196609;
        Type3Message type3Message = new Type3Message(type2Message, password, domain, username, workstation, type3Flags);
        return Base64.encode(type3Message.toByteArray());
    }
}
