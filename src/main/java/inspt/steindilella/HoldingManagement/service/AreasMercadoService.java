package inspt.steindilella.HoldingManagement.service;

import inspt.steindilella.HoldingManagement.dao.AreasMercadoDAOInterface;
import inspt.steindilella.HoldingManagement.entity.AreasMercado;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AreasMercadoService implements AreasMercadoServiceInterface {

    private AreasMercadoDAOInterface areasDAO;

    @Override
    public AreasMercado getById(Integer id) {
        return areasDAO.getById(id);
    }

    @Override
    public List<AreasMercado> getAll() {
        return areasDAO.getAll();
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
