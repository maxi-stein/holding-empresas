package inspt.steindilella.HoldingManagement.service;

import inspt.steindilella.HoldingManagement.entity.Empleado;
import inspt.steindilella.HoldingManagement.entity.Empresa;
import inspt.steindilella.HoldingManagement.entity.Vendedor;

import java.time.LocalDate;
import java.util.Set;

public interface EmpleadoServiceInterface {
    Empleado getById(Integer id);
    Set<Empleado> getAll();
    Set<Empleado> getEmpleadosPorEmpresa(Integer id);

    LocalDate getFechaCaptado(Integer idPadre, Integer idCaptado);

    Set<Empresa> getEmpresasAsesoradas(Integer id);

    LocalDate getFechaAsesorEmpresa(Integer idAsesor, Integer idEmpresa);

    Set<Vendedor> getVendedoresCaptados(Integer idPadre);

    void save(Empleado emp);
    void update(Empleado emp);
    void delete(Empleado emp);

}
