package org.thekiddos.manager.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.thekiddos.manager.repositories.Database;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

/**
 * This is used to classify and filter {@link Item}
 */
@Entity
@Getter
@NoArgsConstructor
@Table(name = "types")
public class Type {
    @Id
    private String name;

    /**
     * Used to get a type object if the type doesn't exists this method will automatically create it and save it in the Database
     * @param typeName The type you want to get/create
     * @return Type!
     */
    public static Type type( String typeName ) {
        Type type = Database.getTypeId( typeName );
        if ( type == null ) {
            type = new Type();
            type.name = typeName;
            Database.addType( type );
        }
        return type;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        Type type = (Type) o;
        return Objects.equals( name, type.name );
    }

    @Override
    public int hashCode() {
        return Objects.hash( name );
    }
}
