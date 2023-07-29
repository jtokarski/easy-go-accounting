package org.defendev.easygo.domain.iam.api;


import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Privilege {
    own(Set.of()),
    write(Set.of(own)),
    read(Set.of(write, own)),
    preview(Set.of(read, write, own));

    private final Set<Privilege> containedIn;

    Privilege(Set<Privilege> containedIn) {
        this.containedIn = Stream.concat(Stream.of(this), containedIn.stream()).collect(Collectors.toSet());
    }

    public Set<Privilege> getContainedIn() {
        return containedIn;
    }

}
