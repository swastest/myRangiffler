package org.rangiffler.db.entity.auth;

import lombok.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@ToString
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthEntity {
    private UUID id;
    private String username;
    private String password;
    private Boolean enabled;
    private Boolean accountNonExpired;
    private Boolean accountNonLocked;
    private Boolean credentialsNonExpired;
    private List<AuthorityEntity> authorities;

    public UUID convertSetId(byte[] uuidFromDb) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(uuidFromDb);
        byteBuffer.order(ByteOrder.BIG_ENDIAN);
        long mostSignificantBits = byteBuffer.getLong();
        long leastSignificantBits = byteBuffer.getLong();
        UUID uuid = new UUID(mostSignificantBits, leastSignificantBits);
        this.id = uuid;
        return uuid;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAuthEntity that = (UserAuthEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(username, that.username) && Objects.equals(password, that.password) && Objects.equals(enabled, that.enabled) && Objects.equals(accountNonExpired, that.accountNonExpired) && Objects.equals(accountNonLocked, that.accountNonLocked) && Objects.equals(credentialsNonExpired, that.credentialsNonExpired);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, enabled, accountNonExpired, accountNonLocked, credentialsNonExpired);
    }
}
