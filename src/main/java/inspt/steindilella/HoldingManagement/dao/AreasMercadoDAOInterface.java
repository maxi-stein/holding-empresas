package inspt.steindilella.HoldingManagement.dao;

import inspt.steindilella.HoldingManagement.entity.AreasMercado;
import inspt.steindilella.HoldingManagement.entity.Empleado;

import java.util.Set;

public interface AreasMercadoDAOInterface {
    AreasMercado getById(Integer id);
    Set<AreasMercado> getAll();
    Set<Empleado> getAsesoresPorArea(Integer id);
    void save(AreasMercado area);
    void update(AreasMercado area);
    void delete(Integer id);

}
