package inspt.steindilella.HoldingManagement.service;

import inspt.steindilella.HoldingManagement.entity.*;

import java.time.LocalDate;
import java.util.Set;

public interface EmpleadoServiceInterface {
    Empleado getById(Integer id);

    Set<Empleado> getAll();

    Set<Empleado> getEmpleadosPorEmpresa(Integer id);

    Set<Administrador> getAdministradores();

    Set<Asesor> getAsesores();

    Set<Vendedor> getVendedores();

    LocalDate getFechaCaptado(Integer idPadre, Integer idCaptado);

    Set<AsesorEmpresa> getEmpresasAsesoradas(Integer idAsesor);

    Set<AreasMercado> getAreasAsesoradasPorAsesor(Integer idAsesor);




    LocalDate getFechaAsesorEmpresa(Integer idAsesor, Integer idEmpresa);

    Set<VendedorCaptado> getVendedoresCaptados(Integer idPadre);

    void agregarVendedorCaptado(Integer idVendedor, Integer idVendedorCaptado, LocalDate fechaCaptado);

    void cubrirAreaMercado(AreasMercado area, Integer idAsesor);

    void desvincularAreaMercado(AreasMercado areasMercado, Integer idAsesor);

    void save(Empleado emp);

    void update(Empleado emp);

    void delete(Integer id);

    void savePass(Credencial password);

    void updatePass(Credencial password);

    void deletePass(Credencial password);

    String getPass(Empleado usuario);

    String getRol(Empleado user);

    boolean vendedorEsCaptable(Integer idVendedor, Integer idVendedorCaptado);
}

