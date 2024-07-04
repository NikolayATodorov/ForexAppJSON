package com.forex.test.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ExtServiceRequestTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ExtServiceRequest getExtServiceRequestSample1() {
        return new ExtServiceRequest().id(1L).serviceName("serviceName1").requestId("requestId1").clientId("clientId1");
    }

    public static ExtServiceRequest getExtServiceRequestSample2() {
        return new ExtServiceRequest().id(2L).serviceName("serviceName2").requestId("requestId2").clientId("clientId2");
    }

    public static ExtServiceRequest getExtServiceRequestRandomSampleGenerator() {
        return new ExtServiceRequest()
            .id(longCount.incrementAndGet())
            .serviceName(UUID.randomUUID().toString())
            .requestId(UUID.randomUUID().toString())
            .clientId(UUID.randomUUID().toString());
    }
}
