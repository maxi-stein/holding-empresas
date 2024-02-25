package inspt.steindilella.HoldingManagement.service;

import inspt.steindilella.HoldingManagement.entity.*;

import java.time.LocalDate;
import java.util.Set;

public interface EmpleadoServiceInterface {
    Empleado getById(Integer id);

    Set<Empleado> getAll();

    Set<Empleado> getUsuariosgetCredencialesAll();

    Set<Empleado> getEmpleadosPorEmpresa(Integer id);

    Set<Administrador> getAdministradores();

    Set<Asesor> getAsesores();

    Set<Vendedor> getVendedores();

    LocalDate getFechaCaptado(Integer idPadre, Integer idCaptado);

    Set<AsesorEmpresa> getEmpresasAsesoradas(Integer idAsesor);

    Empresa getEmpresaVendedor(Integer idVendedor);

    Set<AreasMercado> getAreasAsesoradasPorAsesor(Integer idAsesor);




    LocalDate getFechaAsesorEmpresa(Integer idAsesor, Integer idEmpresa);

    Set<VendedorCaptado> getVendedoresCaptados(Integer idPadre);

    void agregarVendedorCaptado(Integer idVendedor, Integer idVendedorCaptado, LocalDate fechaCaptado);

    void eliminarVendedorCaptado(Integer idVendedor, Integer idVendedorCaptado);

    void eliminarTodosLosVendedoresCaptados(Integer idVendedor);

    void cubrirAreaMercado(AreasMercado area, Integer idAsesor);

    void desvincularAreaMercado(AreasMercado areasMercado, Integer idAsesor);

    void save(Empleado emp);

    void update(Empleado emp);

    void delete(Integer id);

    void savePass(Credencial password);

    void updatePass(Credencial password);

    void deletePass(Credencial password);

    String getPass(Empleado usuario);

    Credencial getCredencial(Empleado usuario);

    String getRol(Empleado user);

    boolean vendedorEsCaptable(Integer idVendedor, Integer idVendedorCaptado);

    Vendedor getCaptador(Integer idVendedor);
}

