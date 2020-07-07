package org.thekiddos.manager.converters;

import org.thekiddos.manager.models.Type;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class TypeConverter implements AttributeConverter<Type, Integer> {
    @Override
    public Integer convertToDatabaseColumn( Type type ) {
        return type == null ? null : type.ordinal();
    }

    @Override
    public Type convertToEntityAttribute( Integer ordinal ) {
        return ordinal == null ? null : Type.fromOrdinal( ordinal );
    }
}
