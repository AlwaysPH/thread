package com.ph.thread.activeObject.resue;

import java.util.concurrent.Future;

public interface SampleObject {
    Future<String> doSayHello();
}
