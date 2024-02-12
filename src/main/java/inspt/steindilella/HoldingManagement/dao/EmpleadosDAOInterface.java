package inspt.steindilella.HoldingManagement.dao;

import inspt.steindilella.HoldingManagement.entity.*;

import java.time.LocalDate;
import java.util.Set;

public interface EmpleadosDAOInterface {

    Empleado getById(Integer id);
    Set<Empleado> getAll();
    Set<Empleado> getEmpleadosPorEmpresa(Integer id);

    Set<Empresa> getEmpresasAsesoradas(Integer id);

    LocalDate getFechaAsesorEmpresa(Integer idAsesor, Integer idEmpresa);

    Set<VendedorCaptado> getVendedoresCaptados(Integer idPadre);
    Vendedor getCaptadorDelVendedor(Integer idCaptado);
    LocalDate getFechaCaptado(Integer idPadre, Integer idCaptado);

    void agregarVendedorCaptado(Integer idVendedor, Integer idVendedorCaptado, LocalDate fechaCaptado);

    void save(Empleado emp);
    void save(VendedorCaptado vc);
    void update(Empleado emp);
    void delete(Empleado emp);

    void savePass(Credencial password);
    void updatePass(Credencial password);
    void deletePass(Credencial password);

    String getPass(Empleado usuario);
    String getRol(Empleado usuario);
}
