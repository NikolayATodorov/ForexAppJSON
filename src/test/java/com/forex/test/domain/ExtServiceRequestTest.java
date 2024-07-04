package com.forex.test.domain;

import static com.forex.test.domain.ExtServiceRequestTestSamples.getExtServiceRequestSample1;
import static com.forex.test.domain.ExtServiceRequestTestSamples.getExtServiceRequestSample2;
import static org.assertj.core.api.Assertions.assertThat;

import com.forex.test.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ExtServiceRequestTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExtServiceRequest.class);
        ExtServiceRequest extServiceRequest1 = getExtServiceRequestSample1();
        ExtServiceRequest extServiceRequest2 = new ExtServiceRequest();
        assertThat(extServiceRequest1).isNotEqualTo(extServiceRequest2);

        extServiceRequest2.setId(extServiceRequest1.getId());
        assertThat(extServiceRequest1).isEqualTo(extServiceRequest2);

        extServiceRequest2 = getExtServiceRequestSample2();
        assertThat(extServiceRequest1).isNotEqualTo(extServiceRequest2);
    }
}
