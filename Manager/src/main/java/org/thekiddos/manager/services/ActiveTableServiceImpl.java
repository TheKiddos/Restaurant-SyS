package org.thekiddos.manager.services;

import org.springframework.stereotype.Service;
import org.thekiddos.manager.api.mapper.TableMapper;
import org.thekiddos.manager.api.model.TableDTO;
import org.thekiddos.manager.models.Table;
import org.thekiddos.manager.repositories.Database;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ActiveTableServiceImpl implements ActiveTableService {
    private TableMapper tableMapper;

    public ActiveTableServiceImpl( TableMapper tableMapper ) {
        this.tableMapper = tableMapper;
    }

    @Override
    public List<TableDTO> getActiveTables() {
        List<TableDTO> tables = new ArrayList<>();

        Database.getCurrentReservations().stream().filter( reservation -> reservation.isActive() )
                .forEach( reservation ->
                        tables.add( tableMapper.tableToTableDTO( reservation.getTable() ) )
                );

        return tables;
    }

    @Override
    public TableDTO getActiveTableById( Long id ) {
        Table table = Database.getTableById( id );

        if ( table.isReserved( LocalDate.now() ) && Database.getCurrentReservationByTableId( table.getId() ).isActive() ) {
            return tableMapper.tableToTableDTO( table );
        }

        return null;
    }
}
