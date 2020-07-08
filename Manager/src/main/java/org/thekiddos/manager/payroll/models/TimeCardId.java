package org.thekiddos.manager.payroll.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeCardId implements Serializable {
    @Column(name = "HOURLY_CLASSIFICATION_ID")
    private Long hourlyClassificationId;
    private LocalDate date;

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        TimeCardId that = (TimeCardId) o;
        return hourlyClassificationId.equals( that.hourlyClassificationId ) &&
                date.equals( that.date );
    }

    @Override
    public int hashCode() {
        return Objects.hash( hourlyClassificationId, date );
    }
}
