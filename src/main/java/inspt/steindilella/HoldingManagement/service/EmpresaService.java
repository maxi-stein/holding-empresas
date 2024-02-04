package inspt.steindilella.HoldingManagement.service;

import inspt.steindilella.HoldingManagement.dao.EmpleadosDAOInterface;
import inspt.steindilella.HoldingManagement.dao.EmpresaDAOInterface;
import inspt.steindilella.HoldingManagement.entity.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Service
public class EmpresaService implements EmpresaServiceInterface{

    EmpresaDAOInterface empresaDAO;
    EmpleadosDAOInterface empleadoDAO;

    @Autowired
    public EmpresaService(EmpresaDAOInterface empresaDAO, EmpleadosDAOInterface empleadoDAO) {
        this.empresaDAO = empresaDAO;
        this.empleadoDAO = empleadoDAO;
    }

    @Override
    public Empresa getById(Integer id) {
        return empresaDAO.getById(id);
    }

    @Override
    public Set<Empresa> getAll() {
        return empresaDAO.getAll();
    }

    @Override
    public Set<AreasMercado> getAreasMercadoPorEmpresa(Integer id) {
        return empresaDAO.getAreasMercadoPorEmpresa(id);
    }

    @Override
    public Set<Empleado> getVendedoresPorEmpresa(Integer id) {
        return empresaDAO.getVendedoresPorEmpresa(id);
    }

    @Override
    public Set<Ciudad> getCiudadesPorEmpresa(Integer id) {
        return empresaDAO.getCiudadesPorEmpresa(id);
    }

    @Override
    public Set<Asesor> getAsesoresPorEmpresa(Integer id) {
        return empresaDAO.getAsesoresPorEmpresa(empresaDAO.getById(id));
    }

    @Override
    @Transactional
    public void agregarVendedor(Vendedor vendedor, Integer id) {
        //cargo la empresa
        Empresa empresa = empresaDAO.getById(id);

        //verifico que exista la empresa
        if(empresa != null){

            //cargo sus vendedores y se los seteo
            Set<Empleado> vendedores = empresaDAO.getVendedoresPorEmpresa(id);
            empresa.setVendedores(vendedores);

            //verifico que el empleado no trabaje en otra empresa
            if(vendedor.tieneEmpresaAsignada()){
                //todo: manejar exception
                System.out.println("El vendedor no puede trabajar en mas de una empresa. ¿Desea desvincularlo de su empresa actual?");
            }

            empresa.agregarVendedor(vendedor);

            empresaDAO.update(empresa);
        }
        else{
            //todo: manejar exception si no existe la empresa
        }
    }

    @Override
    @Transactional
    public void desvincularVendedor(Vendedor vendedor, Integer id) {
        Empresa empresa = empresaDAO.getById(id);

        //verifico que exista la empresa
        if(empresa != null){

            //cargo sus vendedores y se los seteo
            Set<Empleado> vendedores = empresaDAO.getVendedoresPorEmpresa(id);
            empresa.setVendedores(vendedores);

            //verifico que el empleado trabaje en la empresa
            if(!Objects.equals(vendedor.getEmpresa().getId(), id) && !empresa.getVendedores().contains(vendedor)){

                //todo: manejar exception
                System.out.println("El vendedor no puede ser desvinculado de la empresa ya que no trabaja en la misma");
            }

            empresa.desvincularVendedor(vendedor);

            empleadoDAO.update(vendedor);
        }
        else{
            //todo: manejar exception si no existe la empresa
        }
    }

    @Override
    @Transactional
    public void agregarAsesor(Asesor asesor, LocalDate fechaInicio, Integer idEmpresa) {
        Empresa empresa = empresaDAO.getById(idEmpresa);

        //verifico que exista la empresa
        if(empresa != null){
            //cargo sus asesores y se los seteo
            Set<AsesorEmpresa> asesores = empresaDAO.getAsesoresPorEmpresaConFechaInicio(empresa);
            empresa.setAsesores(asesores);

            //creo la id embebida
            AsesorEmpresaId idEmbebida = new AsesorEmpresaId(empresa.getId(),asesor.getId());

            //creo la instancia de AsesorEmpresa con la id embebida
            AsesorEmpresa asesorEmpresa = new AsesorEmpresa(idEmbebida,asesor,empresa,fechaInicio);

            //verifico que el asesor no trabaje en la empresa
           /*for(AsesorEmpresa ase : empresa.getAsesores()){
                if(ase.getAsesor().getId().equals(asesor.getId())){
                    //todo: manejar exception
                    System.out.println("El asesor ya asesora la empresa!");
                }
            }*/

            //verifico que el asesor no trabaje en la empresa
            if (!empresa.getAsesores().contains(asesorEmpresa)){
                empresa.agregarAsesor(asesorEmpresa);
                empresaDAO.save(asesorEmpresa);
            }else{
                //todo: manejar la excepcion
            }

        }
        else{
            //todo: manejar exception si no existe la empresa
        }

    }

    @Override
    @Transactional
    public void desvincularAsesor(Asesor asesor,Integer idEmpresa) {
        Empresa empresa = empresaDAO.getById(idEmpresa);

        //verifico que exista la empresa
        if(empresa != null){

            //obtengo la lista de asesores
            Set<AsesorEmpresa> asesores = empresa.getAsesores();
            AsesorEmpresa relacionEliminar = null;

            //encuentro el asesor a eliminar
            for(AsesorEmpresa element : asesores){
                if(element.getAsesor().getId().equals(asesor.getId())){
                    relacionEliminar = element;
                }
            }

            //si no se encuentra -> error
            if(relacionEliminar == null){
                //todo:manejar exception
                System.out.println("El asesor no trabaja para la empresa.");
            }

            empresaDAO.delete(relacionEliminar);
        }
        else{
            //todo: manejar exception si no existe la empresa
        }


    }

    @Override
    public void save(Empresa empresa) {
        empresaDAO.save(empresa);
    }

    @Override
    public void update(Empresa empresa) {
        empresaDAO.update(empresa);
    }

    @Override
    public void delete(Integer id) {
        empresaDAO.delete(id);
    }
}
