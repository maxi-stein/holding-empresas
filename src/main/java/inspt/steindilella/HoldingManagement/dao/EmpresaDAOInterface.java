package inspt.steindilella.HoldingManagement.dao;

import inspt.steindilella.HoldingManagement.entity.*;

import java.util.List;

public interface EmpresaDAOInterface {

    Empresa getById(Integer id);

    List<Empresa> getAll();
    List<AreasMercado> getAreasMercadoPorEmpresa(Integer id);
    List<Empleado> getVendedoresPorEmpresa(Integer id);
    List<Ciudad> getCiudadesPorEmpresa(Integer id);
    List<Asesor> getAsesoresPorEmpresa(Empresa empr);
    List<AsesorEmpresa> getAsesoresPorEmpresaConFechaInicio(Empresa empr);

    void save(Empresa empresa);
    void save(AsesorEmpresa asesorEmpresa);

    void update(Empresa empresa);

    void delete(Integer id);
    void delete(AsesorEmpresa asesorEmpresa);

}
