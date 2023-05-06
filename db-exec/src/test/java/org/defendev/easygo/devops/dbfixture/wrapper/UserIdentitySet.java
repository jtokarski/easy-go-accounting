package org.defendev.easygo.devops.dbfixture.wrapper;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.defendev.easygo.domain.useridentity.model.UserIdentity;

import java.util.List;



@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlRootElement(name = "userIdentitySet")
public class UserIdentitySet {

    @XmlElement(name = "userIdentity")
    private List<UserIdentity> userIdentities;

    public List<UserIdentity> getUserIdentities() {
        return userIdentities;
    }

    public void setUserIdentities(List<UserIdentity> userIdentities) {
        this.userIdentities = userIdentities;
    }
}
