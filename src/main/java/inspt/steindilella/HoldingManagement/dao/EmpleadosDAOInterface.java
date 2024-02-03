package inspt.steindilella.HoldingManagement.dao;

import inspt.steindilella.HoldingManagement.entity.*;

import java.time.LocalDate;
import java.util.List;

public interface EmpleadosDAOInterface {

    Empleado getById(Integer id);
    List<Empleado> getAll();
    List<Empleado> getEmpleadosPorEmpresa(Integer id);

    List<Empresa> getEmpresasAsesoradas(Integer id);

    LocalDate getFechaAsesorEmpresa(Integer idAsesor, Integer idEmpresa);

    List<Vendedor> getVendedoresCaptados(Integer idPadre);
    LocalDate getFechaCaptado(Integer idPadre, Integer idCaptado);

    void save(Empleado emp);
    void update(Empleado emp);
    void delete(Empleado emp);


}
