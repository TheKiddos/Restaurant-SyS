package org.thekiddos.manager.services;

import org.thekiddos.manager.api.model.TableDTO;

import java.util.List;

/**
 * Used to manage requests for active {@link org.thekiddos.manager.models.Table}s and deliver them to the REST API
 */
public interface ActiveTableService {
    /**
     * @return a List of active {@link TableDTO} which are {@link org.thekiddos.manager.models.Table}s that are currently occupied by {@link org.thekiddos.manager.models.Customer}s
     */
    List<TableDTO> getActiveTables();

    /**
     * @param id Active {@link org.thekiddos.manager.models.Table} id
     * @return an active {@link TableDTO} which is a {@link org.thekiddos.manager.models.Table} that is currently occupied by a {@link org.thekiddos.manager.models.Customer}
     */
    TableDTO getActiveTableById( Long id );
}
