package org.thekiddos.manager.services;

import org.thekiddos.manager.api.model.TableDTO;

import java.util.List;

public interface ActiveTableService {
    List<TableDTO> getActiveTables();

    TableDTO getActiveTableById( Long id );
}
