package inspt.steindilella.HoldingManagement.service;

import inspt.steindilella.HoldingManagement.entity.AreasMercado;
import inspt.steindilella.HoldingManagement.entity.Ciudad;
import inspt.steindilella.HoldingManagement.entity.Empleado;
import inspt.steindilella.HoldingManagement.entity.Empresa;

import java.util.List;

public interface EmpresaServiceInterface {
    Empresa getById(Integer id);
    List<Empresa> getAll();
    List<AreasMercado> getAreasMercadoPorEmpresa(Integer id);
    List<Empleado> getVendedoresPorEmpresa(Integer id);
    List<Ciudad> getCiudadesPorEmpresa(Integer id);
    List<Empleado> getAsesoresPorEmpresa(Integer id);

    void save(Empresa empresa);
    void update(Empresa empresa);
    void delete(Integer id);


}
