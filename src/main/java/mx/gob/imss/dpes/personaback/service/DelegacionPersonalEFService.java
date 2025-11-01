/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.personaback.service;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import javax.interceptor.Interceptors;
import javax.ws.rs.ext.Provider;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.baseback.service.BaseCRUDService;
import mx.gob.imss.dpes.common.exception.UnknowException;
import mx.gob.imss.dpes.personaback.entity.MclcDelegacion;
import mx.gob.imss.dpes.personaback.entity.McltDelegacionPersonalEF;
import mx.gob.imss.dpes.personaback.entity.McltPersonaEF;
import mx.gob.imss.dpes.personaback.repository.DelegacionPersonaEFRepository;
import mx.gob.imss.dpes.personaback.repository.DelegacionRepository;
import mx.gob.imss.dpes.support.config.CustomSpringBeanAutowiringInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * @author gabriel.rios
 */
@Provider
@Interceptors(CustomSpringBeanAutowiringInterceptor.class)
public class DelegacionPersonalEFService extends BaseCRUDService<McltDelegacionPersonalEF, McltDelegacionPersonalEF, Long, Long> {

    private static Integer ISACTIVO = 1;
    private static Integer ISNOTACTIVO = 0;
    @Autowired
    private DelegacionPersonaEFRepository repository;
    
     @Autowired
    private DelegacionRepository delegacionRepository;

    public LinkedList<McltDelegacionPersonalEF> execute(List<McltDelegacionPersonalEF> requestList) throws
            BusinessException {
        LinkedList<McltDelegacionPersonalEF> savedList = new LinkedList();
        McltDelegacionPersonalEF delegacionPersonalEF = requestList.get(0);
        if(delegacionPersonalEF.getCvePersonalEF()==null){
            log.log(Level.SEVERE, ">>> DelegacionPersonalEFService.execute  CvePersonalEF NULL");
            throw new UnknowException();
        }
        log.log(Level.INFO, ">>> DelegacionPersonalEFService.execute  requestList.get(0)=delegacionPersonalEF=" + delegacionPersonalEF);
        List<McltDelegacionPersonalEF> actualDelegacionList = repository.findByCvePersonalEFAndIsActivo(delegacionPersonalEF.getCvePersonalEF(), this.ISACTIVO);
        log.log(Level.INFO, ">>> DelegacionPersonalEFService.execute  actualDelegacionList=" + actualDelegacionList);
        if(actualDelegacionList!=null){
            requestList = getToSaveDelegacionList(requestList,actualDelegacionList);
        }
        try {
            for(McltDelegacionPersonalEF request : requestList) {
                McltDelegacionPersonalEF saved = null;
                if(request.getId()!=null && request.getId()>0){
                    saved = repository.findById(request.getId());
                    if(saved!=null){
                        saved.setIsActivo(request.getIsActivo());
                        request = saved;
                    }
                }
                saved = save(request);
                savedList.add(saved);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.log(Level.SEVERE, ">>> ERROR DelegacionPersonalEFService.execute  message=" + e.getMessage());
            throw new UnknowException();
        }
        return savedList;
    }
    
    public LinkedList<McltDelegacionPersonalEF> execute(McltPersonaEF persona) throws
            BusinessException {
        List<McltDelegacionPersonalEF> requestList = persona.getDelegaciones();
        for(McltDelegacionPersonalEF request:requestList){
            request.setCveEntidadFinanciera(persona.getCveEntidadFinanciera());
            request.setCvePersonalEF(persona.getId());
            request.setIsActivo(ISACTIVO);
            request.setId(null);
        }
        return this.execute(requestList);
    }

    @Override
    public JpaSpecificationExecutor<McltDelegacionPersonalEF> getRepository() {
        return repository;
    }

    @Override
    public JpaRepository<McltDelegacionPersonalEF, Long> getJpaRepository() {
        return repository;
    }

    List<McltDelegacionPersonalEF> getToSaveDelegacionList(List<McltDelegacionPersonalEF> requestList, List<McltDelegacionPersonalEF> actualDelegacionList) {
        List<McltDelegacionPersonalEF> saveDelegacionList = new LinkedList();
        List<McltDelegacionPersonalEF> toSaveListTmp = new LinkedList();
        toSaveListTmp.addAll(requestList);
        boolean isFound;
        int i = -1;
        for (McltDelegacionPersonalEF actualDelegacionPersonalEF : actualDelegacionList) {
            i++;
            int j = -1;
            isFound = false;
            log.log(Level.INFO, "       >>>["+i+"]  DelegacionPersonalEFService.getToSaveDelegacionList  actualDelegacionPersonalEF=" + actualDelegacionPersonalEF);
            for(McltDelegacionPersonalEF request : requestList) {
                j++;
                log.log(Level.INFO, "           >>>["+i+","+j+"]  DelegacionPersonalEFService.getToSaveDelegacionList  request=" +request);
                if (actualDelegacionPersonalEF.getCveDelegacion().compareTo(request.getCveDelegacion()) == 0) {
                    log.log(Level.INFO, "               >>>  DelegacionPersonalEFService.getToSaveDelegacionList FOUND! actualDelegacionPersonalEF="+actualDelegacionPersonalEF);
                    if(request.getIsActivo()==ISACTIVO){
                        log.log(Level.INFO, "               >>>  DelegacionPersonalEFService.getToSaveDelegacionList ISACTIVO!  toRemove=>request="+request);
                        toSaveListTmp.remove(request);
                        isFound = true;
                    }
                    break;
                }
            }
            if (!isFound) {
                log.log(Level.INFO, "   >>>["+i+"]DelegacionPersonalEFService.getToSaveDelegacionList !isFound actualDelegacionPersonalEF=" + actualDelegacionPersonalEF);
                actualDelegacionPersonalEF.setIsActivo(ISNOTACTIVO);
                saveDelegacionList.add(actualDelegacionPersonalEF);
            }

        }
        saveDelegacionList.addAll(toSaveListTmp);
        i = -1;
        log.log(Level.INFO, ">>><<<<DelegacionPersonalEFService.getToSaveDelegacionList saveDelegacionList.size=" + saveDelegacionList.size());
        for(McltDelegacionPersonalEF toSave : saveDelegacionList) {
            log.log(Level.INFO, ">>><<<<["+i+"]DelegacionPersonalEFService.getToSaveDelegacionList toSave=" + toSave);
                
        }

        return saveDelegacionList;
    }
    
    public LinkedList<McltDelegacionPersonalEF> getActualDelegacionList(Long cvePersonalEF){
        log.log(Level.INFO, "   >>>DelegacionPersonalEFService.getActualDelegacionList cvePersonalEF=" + cvePersonalEF);
        LinkedList<McltDelegacionPersonalEF> actualDelegacionList = new LinkedList();
        if(cvePersonalEF!=null && cvePersonalEF>0){
            List<McltDelegacionPersonalEF> delegacionPersonalList = repository.findByCvePersonalEFAndIsActivo(cvePersonalEF, this.ISACTIVO);
            for(McltDelegacionPersonalEF delegacionPersonal:delegacionPersonalList){
                MclcDelegacion delegacion = delegacionRepository.findById(delegacionPersonal.getCveDelegacion());
                delegacionPersonal.setDescDelegacion(delegacion!=null?delegacion.getDescDelegacion():null);
            }
            actualDelegacionList.addAll(delegacionPersonalList);
        }
        return actualDelegacionList;
    }

}
