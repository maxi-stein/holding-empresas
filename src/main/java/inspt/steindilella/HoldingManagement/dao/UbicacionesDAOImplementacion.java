package inspt.steindilella.HoldingManagement.dao;

import inspt.steindilella.HoldingManagement.entity.Ciudad;
import inspt.steindilella.HoldingManagement.entity.Pais;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UbicacionesDAOImplementacion implements UbicacionesDAO{

    private EntityManager entityManager;

    @Autowired
    public UbicacionesDAOImplementacion(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(Pais pais, Ciudad ciudad) {
        entityManager.persist(pais);
        entityManager.persist(ciudad);
    }
}
