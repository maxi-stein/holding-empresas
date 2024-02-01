package inspt.steindilella.HoldingManagement.dao;

import inspt.steindilella.HoldingManagement.entity.Empresa;

import java.util.List;

public interface EmpresaDAOInterface {

    Empresa getById(Integer id);
    List<Empresa> getAll();

    void save(Empresa empresa);
    void update(Empresa empresa);
    void delete(Integer id);

}
