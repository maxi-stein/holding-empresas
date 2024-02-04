package inspt.steindilella.HoldingManagement.service;

import inspt.steindilella.HoldingManagement.dao.AreasMercadoDAOInterface;
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
    AreasMercadoDAOInterface areaDAO;

    @Autowired
    public EmpresaService(EmpresaDAOInterface empresaDAO, EmpleadosDAOInterface empleadoDAO, AreasMercadoDAOInterface areaDAO) {
        this.empresaDAO = empresaDAO;
        this.empleadoDAO = empleadoDAO;
        this.areaDAO = areaDAO;
    }

    @Override
    public Empresa getById(Integer id) {
        return empresaDAO.getById(id);
    }

    @Override
    public Empresa getEmpresaByVendedorId(Integer id) {
        return empresaDAO.getEmpresaByVendedorId(id);
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
            if (!empresa.getAsesores().contains(asesorEmpresa)){
                empresa.agregarAsesor(asesorEmpresa);
                empresaDAO.save(asesorEmpresa);
            }else{
                //todo: manejar la excepcion si el asesor ya trabaja para la empresa
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

    @Override
    @Transactional
    public void agregarAreaMercado(AreasMercado areaMercado, Integer id) {
        //cargo la empresa
        Empresa empresa = empresaDAO.getById(id);

        //verifico que exista la empresa
        if(empresa != null){

            //agrego el area de mercado a la empresa
            empresa.agregarAreaMercado(areaMercado);
            //actualizo la empresa en la bbdd.
            empresaDAO.update(empresa);
        }
        else{
            //todo: manejar exception si no existe la empresa
        }
    }

    @Override
    @Transactional
    public void quitarAreaMercado(AreasMercado areaMercado, Integer id) {
        //cargo la empresa
        Empresa empresa = empresaDAO.getById(id);
        AreasMercado areaDesvinculada = null;

        //verifico que exista la empresa y que contenga el area
        if(empresa != null){
            //itero la coleccion de areas hasta encontrar el area en cuestion
            Set<AreasMercado> listadoAreas = empresa.getAreasMercados();

            for(AreasMercado element : listadoAreas){
                if(element.getId().equals(areaMercado.getId())){
                    System.out.println("Se encontro el area");
                    areaDesvinculada = element;
                }
            }

            //quito el area de mercado a la empresa
            empresa.quitarAreaMercado(areaDesvinculada);
            //actualizo la empresa en la bbdd.
            empresaDAO.update(empresa);


        }
        else{
            //todo: manejar exception si no existe la empresa
            System.out.println(empresaDAO.getAreasMercadoPorEmpresa(id).contains(areaMercado));
        }
    }
}
