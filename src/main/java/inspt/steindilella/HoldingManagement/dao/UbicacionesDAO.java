package inspt.steindilella.HoldingManagement.dao;

import inspt.steindilella.HoldingManagement.entity.Ciudad;
import inspt.steindilella.HoldingManagement.entity.Pais;

public interface PaisDAO {
    void save(Pais pais);
    void save(Ciudad ciudad);
}
