package org.defendev.easygo.domain.iam.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.defendev.common.domain.iam.Privilege;


@Embeddable
public class CommonPrivilege {

    @Column(name = "subjectKey", nullable = false)
    @Enumerated(EnumType.STRING)
    private CommonPrivilegeSubject subject;

    @Column(name = "privilegeKey", nullable = false)
    @Enumerated(EnumType.STRING)
    private Privilege privilege;

    public CommonPrivilegeSubject getSubject() {
        return subject;
    }

    public void setSubject(CommonPrivilegeSubject subject) {
        this.subject = subject;
    }

    public Privilege getPrivilege() {
        return privilege;
    }

    public void setPrivilege(Privilege privilege) {
        this.privilege = privilege;
    }
}
