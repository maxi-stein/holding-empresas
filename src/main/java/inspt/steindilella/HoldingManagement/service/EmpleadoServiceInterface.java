package inspt.steindilella.HoldingManagement.service;

import inspt.steindilella.HoldingManagement.entity.Empleado;

import java.util.List;

public interface EmpleadoServiceInterface {
    Empleado getById(Integer id);
    List<Empleado> getAll();
    List<Empleado> getEmpleadosPorEmpresa(Integer id);
    void save(Empleado emp);
    void update(Empleado emp);
    void delete(Empleado emp);

}
