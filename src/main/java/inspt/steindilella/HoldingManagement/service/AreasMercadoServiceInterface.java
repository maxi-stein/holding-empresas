package inspt.steindilella.HoldingManagement.service;

import inspt.steindilella.HoldingManagement.entity.AreasMercado;
import inspt.steindilella.HoldingManagement.entity.Empleado;

import java.util.Set;

public interface AreasMercadoServiceInterface {
    AreasMercado getById(Integer id);
    Set<AreasMercado> getAll();
    Set<Empleado> getAsesoresPorArea(Integer id);
    void save(AreasMercado area);
    void update(AreasMercado area);
    void delete(Integer id);
}
