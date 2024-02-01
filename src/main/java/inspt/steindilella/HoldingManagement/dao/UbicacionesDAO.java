package inspt.steindilella.HoldingManagement.dao;

import inspt.steindilella.HoldingManagement.entity.Ciudad;
import inspt.steindilella.HoldingManagement.entity.Pais;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UbicacionesDAO implements UbicacionesDAOInterface {

    private EntityManager entityManager;

    @Autowired
    public UbicacionesDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(Pais pais, Ciudad ciudad) {
        entityManager.persist(pais);
        entityManager.persist(ciudad);
    }

    @Override
    public Pais getPaisById(Integer id) {
        return null;
    }

    @Override
    public Ciudad getCiudadById(Integer id) {
        return null;
    }

    @Override
    public List<Pais> getAllPaises() {
        return null;
    }

    @Override
    public List<Ciudad> getAllCiudades() {
        return null;
    }

    @Override
    public void updatePais(Pais pais) {

    }

    @Override
    public void updateCiudad(Ciudad ciudad) {

    }

    @Override
    public void deleteCiudad(Ciudad ciudad) {

    }
}
