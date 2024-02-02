package inspt.steindilella.HoldingManagement.dao;

import inspt.steindilella.HoldingManagement.entity.Empleado;
import inspt.steindilella.HoldingManagement.entity.Empresa;

import java.util.List;

public interface EmpleadosDAOInterface {

    Empleado getById(Integer id);
    List<Empleado> getAll();
    List<Empleado> getEmpleadosPorEmpresa(Integer id);

    List<Empresa> getEmpresasAsesoradas(Integer id);

    void save(Empleado emp);
    void update(Empleado emp);
    void delete(Empleado emp);

}
