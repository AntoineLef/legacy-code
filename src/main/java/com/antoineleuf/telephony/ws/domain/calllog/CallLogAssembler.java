package com.antoineleuf.telephony.ws.domain.calllog;

import com.antoineleuf.telephony.ws.api.calllog.dto.CallLogDto;

public class CallLogAssembler {
  public CallLogDto create(CallLog callLog) {
    return new CallLogDto(callLog.id(), callLog.telephoneNumber(), callLog.date(), callLog.durationInSeconds());
  }
}
