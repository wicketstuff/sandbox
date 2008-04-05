package org.wicketstuff.jamon.monitor;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.wicketstuff.jamon.monitor.MonitorLabelSpecification;


public class MonitorLabelSpecificationTest {
    private MonitorLabelSpecification specification;

    @Test
    public void shouldBeSatisfiedIfLabelIsSame() {
        specification = new MonitorLabelSpecification("mon1");
        assertThat(specification.isSatisfiedBy(new TestMonitor("mon1")), is(true));
        assertThat(specification.isSatisfiedBy(new TestMonitor("Mon1")), is(false));
        assertThat(specification.isSatisfiedBy(new TestMonitor("mon2")), is(false));
    }
}
