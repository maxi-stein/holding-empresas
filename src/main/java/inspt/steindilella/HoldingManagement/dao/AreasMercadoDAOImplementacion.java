package inspt.steindilella.HoldingManagement.dao;

import inspt.steindilella.HoldingManagement.entity.AreasMercado;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class AreasMercadoDAOImplementacion implements AreasMercadoDAO {

    private EntityManager entityManager;
    @Autowired
    public AreasMercadoDAOImplementacion(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public AreasMercado getById() {
        return null;
    }

    @Override
    public List<AreasMercado> getAll() {
        return null;
    }

    @Override
    @Transactional
    public void save(AreasMercado area) {
        entityManager.persist(area);
    }

    @Override
    public void update(AreasMercado area) {

    }

    @Override
    public void delete(Integer id) {

    }
}
