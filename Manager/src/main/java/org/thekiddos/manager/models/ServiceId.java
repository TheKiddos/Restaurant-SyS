package org.thekiddos.manager.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceId implements Serializable {
    private Long customerId;
    private LocalDate date;

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        ServiceId serviceId = (ServiceId) o;
        return customerId.equals( serviceId.customerId ) &&
                date.equals( serviceId.date );
    }

    @Override
    public int hashCode() {
        return Objects.hash( customerId, date );
    }
}
