package inspt.steindilella.HoldingManagement.dao;

import inspt.steindilella.HoldingManagement.entity.*;

import java.time.LocalDate;
import java.util.Set;

public interface EmpleadosDAOInterface {

    Empleado getById(Integer id);
    Set<Empleado> getAll();
    Set<Administrador> getAdministradores();
    Set<Asesor> getAsesores();
    Set<Empleado> getEmpleadosPorEmpresa(Integer id);

    Set<AsesorEmpresa> getEmpresasAsesoradas(Integer id);
    Set<AreasMercado> getAreasAsesoradasPorAsesor(Integer idAsesor);
    LocalDate getFechaAsesorEmpresa(Integer idAsesor, Integer idEmpresa);

    Set<VendedorCaptado> getVendedoresCaptados(Integer idPadre);
    Vendedor getCaptadorDelVendedor(Integer idCaptado);
    LocalDate getFechaCaptado(Integer idPadre, Integer idCaptado);

    void agregarVendedorCaptado(Integer idVendedor, Integer idVendedorCaptado, LocalDate fechaCaptado);

    void save(Empleado emp);
    void save(VendedorCaptado vc);
    void update(Empleado emp);
    void delete(Integer id);

    void savePass(Credencial password);
    void updatePass(Credencial password);
    void deletePass(Credencial password);

    String getPass(Empleado usuario);
    String getRol(Empleado usuario);
}
