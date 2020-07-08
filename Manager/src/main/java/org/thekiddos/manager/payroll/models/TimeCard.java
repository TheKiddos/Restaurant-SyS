package org.thekiddos.manager.payroll.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Table(name = "TIMECARD")
public class TimeCard implements Serializable {
    @EmbeddedId @NonNull
    private TimeCardId timeCardId;

    @NonNull
    private LocalTime timeWorked;

    public LocalDate getDate() {
        return timeCardId.getDate();
    }
}
