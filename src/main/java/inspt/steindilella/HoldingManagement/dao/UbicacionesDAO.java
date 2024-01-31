package inspt.steindilella.HoldingManagement.dao;

import inspt.steindilella.HoldingManagement.entity.Ciudad;
import inspt.steindilella.HoldingManagement.entity.Pais;

public interface UbicacionesDAO {
    void save(Pais pais,Ciudad ciudad);
}
