package org.wicketstuff.jamon.monitor;

import java.io.Serializable;

import com.jamonapi.Monitor;

/**
 * Specification interface used by the {@link JamonRepository}.
 * Implementations are responsible to check whether or not a {@link Monitor}
 * satisfies this specification or not.
 * Implementations should also be {@link Serializable}.
 * 
 * @author lars
 *
 */
public interface MonitorSpecification extends Serializable {
    /**
     * Does the given {@link Monitor} satisfies this {@link MonitorSpecification}. 
     * @param monitor The {@link Monitor} to check
     * @return <code>true</code> if satistied, <code>false</code> if not.
     */
    public boolean isSatisfiedBy(Monitor monitor);
}
