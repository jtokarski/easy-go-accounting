package org.defendev.easygo.domain.iam.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import org.defendev.common.domain.HasId;
import org.defendev.common.domain.iam.Privilege;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.defendev.common.stream.Streams.stream;



@XmlAccessorType(value = XmlAccessType.FIELD)
@Table(name = "UserIdentity")
@Entity
public class UserIdentity implements HasId<Long> {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Id
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "oidcProvider")
    private String oidcProvider;

    @Column(name = "oidcSub")
    private String oidcSub;

    @XmlElementWrapper(name = "ownershipUnits")
    @XmlElement(name = "joinUserIdentityOwnershipUnit")
    @OneToMany(mappedBy = "userIdentity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JoinUserIdentityOwnershipUnit> ownershipUnits = new ArrayList<>();

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<JoinUserIdentityOwnershipUnit> getOwnershipUnits() {
        return ownershipUnits;
    }

    public void setOwnershipUnits(List<JoinUserIdentityOwnershipUnit> ownershipUnits) {
        this.ownershipUnits = ownershipUnits;
    }

    public Map<Privilege, Set<Long>> getPrivilegeToOwnershipUnit() {
        return stream(ownershipUnits).collect(Collectors.groupingBy(
            JoinUserIdentityOwnershipUnit::getPrivilege,
            Collectors.mapping(join -> join.getOwnershipUnit().getId(), Collectors.toSet())
        ));
    }

}
