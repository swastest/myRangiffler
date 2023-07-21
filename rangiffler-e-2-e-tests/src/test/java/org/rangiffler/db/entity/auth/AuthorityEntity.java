package org.rangiffler.db.entity.auth;

import lombok.*;

import java.util.Objects;
import java.util.UUID;

@ToString
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorityEntity {
    private UUID id;
    private Authority authority;

    private UUID userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorityEntity that = (AuthorityEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(authority, that.authority) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, authority, userId);
    }
}
