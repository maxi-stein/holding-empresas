package inspt.steindilella.HoldingManagement.service;

import inspt.steindilella.HoldingManagement.entity.*;

import java.time.LocalDate;
import java.util.Set;

public interface EmpleadoServiceInterface {
    Empleado getById(Integer id);
    Set<Empleado> getAll();
    Set<Empleado> getEmpleadosPorEmpresa(Integer id);
    Set<Administrador> getAdministradores();

    LocalDate getFechaCaptado(Integer idPadre, Integer idCaptado);

    Set<Empresa> getEmpresasAsesoradas(Integer id);

    LocalDate getFechaAsesorEmpresa(Integer idAsesor, Integer idEmpresa);

    Set<VendedorCaptado> getVendedoresCaptados(Integer idPadre);

    void agregarVendedorCaptado(Integer idVendedor, Integer idVendedorCaptado, LocalDate fechaCaptado);
    void cubrirAreaMercado(AreasMercado area, Integer idAsesor);
    void DesvincularAreaMercado(AreasMercado areasMercado, Integer idAsesor);

    void save(Empleado emp);
    void update(Empleado emp);
    void delete(Integer id);

    void savePass(Credencial password);
    void updatePass(Credencial password);
    void deletePass(Credencial password);

    String getPass(Empleado usuario);

    String getRol(Empleado user);
}
