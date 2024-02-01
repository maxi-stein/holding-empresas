package inspt.steindilella.HoldingManagement.service;

import inspt.steindilella.HoldingManagement.entity.Empresa;

import java.util.List;

public interface EmpresaServiceInterface {
    Empresa getById(Integer id);
    List<Empresa> getAll();

    void save(Empresa empresa);
    void update(Empresa empresa);
    void delete(Integer id);
}
