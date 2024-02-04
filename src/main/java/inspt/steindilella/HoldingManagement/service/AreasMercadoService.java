package inspt.steindilella.HoldingManagement.service;

import inspt.steindilella.HoldingManagement.dao.AreasMercadoDAOInterface;
import inspt.steindilella.HoldingManagement.entity.AreasMercado;
import inspt.steindilella.HoldingManagement.entity.Empleado;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AreasMercadoService implements AreasMercadoServiceInterface {

    private AreasMercadoDAOInterface areasDAO;
    @Autowired
    public AreasMercadoService(AreasMercadoDAOInterface areasDAO) {
        this.areasDAO = areasDAO;
    }

    @Override
    public AreasMercado getById(Integer id) {
        return areasDAO.getById(id);
    }

    @Override
    public Set<AreasMercado> getAll() {
        return areasDAO.getAll();
    }

    @Override
    public Set<Empleado> getAsesoresPorArea(Integer id) {
        return areasDAO.getAsesoresPorArea(id);
    }

    @Override
    @Transactional
    public void save(AreasMercado area) {
        areasDAO.save(area);
    }

    @Override
    @Transactional
    public void update(AreasMercado area) {
        areasDAO.update(area);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        areasDAO.delete(id);
    }
}
