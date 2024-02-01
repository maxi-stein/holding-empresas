package inspt.steindilella.HoldingManagement.dao;

import inspt.steindilella.HoldingManagement.entity.AreasMercado;

import java.util.List;

public interface AreasMercadoDAOInterface {
    AreasMercado getById();
    List<AreasMercado> getAll();
    void save(AreasMercado area);
    void update(AreasMercado area);
    void delete(Integer id);

}
