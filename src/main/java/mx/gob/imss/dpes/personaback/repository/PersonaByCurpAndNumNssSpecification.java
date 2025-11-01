package mx.gob.imss.dpes.personaback.repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.Getter;
import lombok.Setter;
import mx.gob.imss.dpes.baseback.persistence.BaseSpecification;
import mx.gob.imss.dpes.personaback.entity.McltPersona;
import mx.gob.imss.dpes.personaback.entity.McltPersona_;

/**
 * @author luisr.rodriguez
 */
public class PersonaByCurpAndNumNssSpecification extends BaseSpecification<McltPersona> {
    @Getter @Setter private String numNss;
    @Getter @Setter private String curp;
    
    public PersonaByCurpAndNumNssSpecification() {
        this.numNss = "";  
        this.curp = "";
    }
    
    public PersonaByCurpAndNumNssSpecification(String numNss,String curp) {
        this.numNss = numNss;  
        this.curp = curp;
    }

    @Override
    public Predicate toPredicate(Root<McltPersona> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        if((curp == null || curp.isEmpty()) 
                && (numNss != null && !numNss.isEmpty())){
            return cb.equal(root.get(McltPersona_.NUM_NSS), 
                            this.numNss);
        }else if((curp != null && !curp.isEmpty()) 
                && (numNss == null || numNss.isEmpty())){
            return cb.equal(root.get(McltPersona_.cveCurp), 
                            this.curp);
        }else{
            return cb.and(
                    cb.equal(root.get(McltPersona_.cveCurp), 
                            this.curp), 
                    cb.equal(root.get(McltPersona_.NUM_NSS), 
                            this.numNss));
        }
    }
}
