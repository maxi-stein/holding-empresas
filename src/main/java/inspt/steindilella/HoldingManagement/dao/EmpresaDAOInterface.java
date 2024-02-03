package inspt.steindilella.HoldingManagement.dao;

import inspt.steindilella.HoldingManagement.entity.*;

import java.util.List;

public interface EmpresaDAOInterface {

    Empresa getById(Integer id);
    Empresa getByIdConVendedores(Integer id);

    List<Empresa> getAll();
    List<AreasMercado> getAreasMercadoPorEmpresa(Integer id);
    List<Empleado> getVendedoresPorEmpresa(Integer id);
    List<Ciudad> getCiudadesPorEmpresa(Integer id);
    List<Asesor> getAsesoresPorEmpresa(Empresa empr);

    void save(Empresa empresa);
    void update(Empresa empresa);
    void delete(Integer id);

}
