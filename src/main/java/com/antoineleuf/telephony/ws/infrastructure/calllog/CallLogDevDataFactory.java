package com.antoineleuf.telephony.ws.infrastructure.calllog;

import java.util.List;

import com.antoineleuf.telephony.ws.domain.calllog.CallLog;

public class CallLogDevDataFactory {

  public List<CallLog> createMockData() {
    CallLog callLog1 = new CallLog("1", "514-999-0000", "2016-07-31T16:45:00Z", 65);
    CallLog callLog2 = new CallLog("2", "418-682-3092", "2016-06-31T15:29:00Z", 99);
    CallLog callLog3 = new CallLog("3", "581-671-0992", "2016-07-30T08:32:33Z", 22);

    return List.of(callLog1, callLog2, callLog3);
  }
}
