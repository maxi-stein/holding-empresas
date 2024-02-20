package inspt.steindilella.HoldingManagement.dao;

import inspt.steindilella.HoldingManagement.entity.*;
import jakarta.transaction.Transactional;

import java.util.Set;

public interface EmpresaDAOInterface {

    Empresa getById(Integer id);
    Empresa getEmpresaByVendedorId(Integer id);

    Set<Empresa> getAll();
    Set<AreasMercado> getAreasMercadoPorEmpresa(Integer id);
    Set<Vendedor> getVendedoresPorEmpresa(Integer id);
    Set<Ciudad> getCiudadesPorEmpresa(Integer id);
    Set<Asesor> getAsesoresPorEmpresa(Empresa empr);
    Set<AsesorEmpresa> getAsesoresPorEmpresaConFechaInicio(Empresa empr);

    void save(Empresa empresa);
    void save(AsesorEmpresa asesorEmpresa);

    void update(Empresa empresa);

    void delete(Integer id);

    @Transactional
    void desbloquear(Integer id);

    void delete(AsesorEmpresa asesorEmpresa);

}
