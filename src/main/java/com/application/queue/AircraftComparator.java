package com.application.queue;

import java.util.Comparator;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.springframework.stereotype.Component;

import com.application.entity.Aircraft;

/**
 * 
 * Comparator for aicrafts before adding it to the queue. The comparison is made in the following order:
 * - first compare aircraft types according to the precedence
 * - second compare aircraft sizes according to the precedence
 * - third compare their Ids
 */
@Component
public class AircraftComparator implements Comparator<Aircraft> {

    @Override
    public int compare(Aircraft a1, Aircraft a2) {
        return new CompareToBuilder()
                .append(a1.getAircraftType().ordinal(), a2.getAircraftType().ordinal())
                .append(a1.getAircraftSize().ordinal(), a2.getAircraftSize().ordinal())
                .append(a1.getAircraftId(), a2.getAircraftId())
                .toComparison();
    }

}
