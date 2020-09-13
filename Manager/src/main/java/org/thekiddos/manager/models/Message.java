package org.thekiddos.manager.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
public class Message {
    @Id @GeneratedValue( strategy = GenerationType.AUTO )
    private Long id;

    @NonNull
    private String contents;

    // I may need to use these as a foreign keys and use classes for them in the future when things gets more complex but it will do for now
    @NonNull
    private String sender;
    @NonNull
    private String receiver;

    @NonNull @Column(name = "created_at")
    private LocalDateTime createdAt;
    @NonNull
    private boolean seen;

    public void setSeen() {
        seen = true;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        Message message = (Message) o;
        return id.equals( message.id );
    }

    @Override
    public int hashCode() {
        return Objects.hash( id );
    }
}
