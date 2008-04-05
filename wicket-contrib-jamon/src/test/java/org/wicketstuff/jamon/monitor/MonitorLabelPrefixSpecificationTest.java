package org.wicketstuff.jamon.monitor;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.wicketstuff.jamon.monitor.MonitorLabelPrefixSpecification;



public class MonitorLabelPrefixSpecificationTest {
    
    private MonitorLabelPrefixSpecification specification;

    @Test
    public void shouldBeSatisfiedWhenMonitorStartWithLabelOfSpecification() {
        specification = new MonitorLabelPrefixSpecification("t");
        assertThat(specification.isSatisfiedBy(new TestMonitor("test1")), is(true));
        assertThat(specification.isSatisfiedBy(new TestMonitor("Test1")), is(true));
        assertThat(specification.isSatisfiedBy(new TestMonitor("monitor")), is(false));
    }
}
