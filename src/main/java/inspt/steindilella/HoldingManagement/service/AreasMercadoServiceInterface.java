package inspt.steindilella.HoldingManagement.service;

import inspt.steindilella.HoldingManagement.entity.AreasMercado;

import java.util.List;

public interface AreasMercadoServiceInterface {
    AreasMercado getById();
    List<AreasMercado> getAll();
    void save(AreasMercado area);
    void update(AreasMercado area);
    void delete(Integer id);
}
