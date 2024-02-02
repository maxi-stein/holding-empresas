package inspt.steindilella.HoldingManagement.dao;

import inspt.steindilella.HoldingManagement.entity.AreasMercado;
import inspt.steindilella.HoldingManagement.entity.Ciudad;
import inspt.steindilella.HoldingManagement.entity.Empleado;
import inspt.steindilella.HoldingManagement.entity.Empresa;

import java.util.List;

public interface EmpresaDAOInterface {

    Empresa getById(Integer id);
    List<Empresa> getAll();
    List<AreasMercado> getAreasMercadoPorEmpresa(Integer id);
    List<Empleado> getVendedoresPorEmpresa(Integer id);
    List<Ciudad> getCiudadesPorEmpresa(Integer id);

    void save(Empresa empresa);
    void update(Empresa empresa);
    void delete(Integer id);

}
