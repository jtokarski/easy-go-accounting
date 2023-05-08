package org.defendev.easygo.devops.dbfixture.wrapper;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.defendev.easygo.domain.useridentity.model.OwnershipUnit;

import java.util.List;



@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlRootElement(name = "ownershipUnitSet")
public class OwnershipUnitSet {

    @XmlElement(name = "ownershipUnit")
    private List<OwnershipUnit> ownershipUnits;

    public List<OwnershipUnit> getOwnershipUnits() {
        return ownershipUnits;
    }

    public void setOwnershipUnits(List<OwnershipUnit> ownershipUnits) {
        this.ownershipUnits = ownershipUnits;
    }
}
