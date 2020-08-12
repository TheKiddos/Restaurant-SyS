package org.thekiddos.manager.services;

import org.springframework.stereotype.Service;
import org.thekiddos.manager.api.mapper.TableMapper;
import org.thekiddos.manager.api.model.TableDTO;
import org.thekiddos.manager.models.Reservation;
import org.thekiddos.manager.models.Table;
import org.thekiddos.manager.repositories.Database;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ActiveTableServiceImpl implements ActiveTableService {
    private final TableMapper tableMapper;

    public ActiveTableServiceImpl( TableMapper tableMapper ) {
        this.tableMapper = tableMapper;
    }

    @Override
    public List<TableDTO> getActiveTables() {
        List<TableDTO> tables = new ArrayList<>();

        Database.getCurrentReservations().stream().filter( Reservation::isActive )
                .forEach( reservation ->
                        tables.add( tableMapper.tableToTableDTO( reservation.getTable() ) )
                );

        return tables;
    }

    @Override
    public TableDTO getActiveTableById( Long id ) {
        Table table = Database.getTableById( id );

        if ( isCurrentTableReservationActive( table ) ) {
            return tableMapper.tableToTableDTO( table );
        }

        return null;
    }

    private boolean isCurrentTableReservationActive( Table table ) {
        return table.isReserved( LocalDate.now() ) && Database.getCurrentReservationByTableId( table.getId() ).isActive();
    }
}
