package inspt.steindilella.HoldingManagement.service;

import inspt.steindilella.HoldingManagement.entity.*;

import java.util.List;

public interface EmpresaServiceInterface {
    Empresa getById(Integer id);
    List<Empresa> getAll();
    List<AreasMercado> getAreasMercadoPorEmpresa(Integer id);
    List<Empleado> getVendedoresPorEmpresa(Integer id);
    List<Ciudad> getCiudadesPorEmpresa(Integer id);
    List<Asesor> getAsesoresPorEmpresa(Integer id);

    void agregarVendedorAEmpresa(Vendedor vendedor, Integer id);

    void save(Empresa empresa);
    void update(Empresa empresa);
    void delete(Integer id);


}
